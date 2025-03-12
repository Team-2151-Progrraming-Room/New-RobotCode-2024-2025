// ClimbLock
//
// Controls the latching used to grab onto the cage for a low climb
//
// Setup using a DC Motor via a Talon FXS.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.util.sendable.SendableBuilder;

// SparkMax imports - these come from REV Robotics
/*Old imports, will delete later
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
*/

//CTRE Imports

import com.ctre.phoenix6.hardware.TalonFXS;
import com.ctre.phoenix6.signals.BrushedMotorWiringValue;
import com.ctre.phoenix6.signals.ExternalFeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.MotorArrangementValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.TalonFXSConfiguration;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.ExternalFeedbackConfigs;
/* 
import com.ctre.phoenix6.configs.Slot0Configs;    Unused, will delete later
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.Follower;*/

//Climb Lock Constants
import frc.robot.Constants.ClimbLockConstants;

public class ClimbLockSubsystem extends SubsystemBase {
  //Hardware and configurations for them.
  private TalonFXS m_climbLock;
  private TalonFXSConfiguration m_climbLockConfiguration;
  private CurrentLimitsConfigs m_climbLockCurrentLimitsConfigs;
  private ExternalFeedbackConfigs m_climbLockEncoderConfig;
  
  /* Old Rev Imports, will delete later
  private RelativeEncoder m_oldEncoder;
  private SparkMaxConfig m_oldConfig = new SparkMaxConfig();
  private SparkMax m_oldMotor;
  */

  public ClimbLockSubsystem() {

    System.out.print("Initializing ClimbLockSubsystem...  ");
    System.out.print("Using a DC Motor and a TalonFXS!");

    m_climbLock = new TalonFXS(ClimbLockConstants.kClimbLockCanRioId);
    
    //Talon FXS Configuration
    m_climbLockConfiguration = new TalonFXSConfiguration();
    m_climbLockConfiguration.Commutation.MotorArrangement = MotorArrangementValue.Brushed_DC;
    m_climbLockConfiguration.Commutation.BrushedMotorWiring = BrushedMotorWiringValue.Leads_A_and_B;
    //Not using Advanced Hall Support, seems like a minor upgrade, but it is something worth noting
    
    
    //Initial Current Limit Configuration
    //This current config changes later when the lock moves.
    m_climbLockCurrentLimitsConfigs = new CurrentLimitsConfigs();
    m_climbLockCurrentLimitsConfigs.withStatorCurrentLimit(ClimbLockConstants.kClimbLockCloseCurrentStatorLimit);//Closed values
    m_climbLockCurrentLimitsConfigs.withSupplyCurrentLimit(ClimbLockConstants.kClimbLockCloseCurrentSupplyLimit);
    
    
    //Encoder configuration
    m_climbLockEncoderConfig = new ExternalFeedbackConfigs();
    m_climbLockEncoderConfig.withExternalFeedbackSensorSource(ExternalFeedbackSensorSourceValue.Quadrature);
    m_climbLockEncoderConfig.withRotorToSensorRatio(ClimbLockConstants.kClimbLockEncoderPpr);


    //Config application
    m_climbLockConfiguration.withCurrentLimits(m_climbLockCurrentLimitsConfigs);
    m_climbLockConfiguration.withExternalFeedback(m_climbLockEncoderConfig);
    m_climbLock.getConfigurator().apply(m_climbLockConfiguration);
    
    m_climbLock.setNeutralMode(NeutralModeValue.Brake);//Set the motor to brake mode, will resist movement when the motor
                                                      //isn't running.

    
    //There does not appear to be a method to zero a remote quadiature encoder,
    //which should (hopefully) not matter since it's a relative encoder either way.
    
    System.out.println("Done.");
    
    
    /*Old code, will delete later
    m_oldEncoder.setPosition(0);    // we assume we're in the start position with the locks positioned to be fully open
    m_oldMotor.configure(m_oldConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_oldMotor = new SparkMax(ClimbLockConstants.kClimbLockCanRioId, MotorType.kBrushless);   // CHANGE WHEN GOING BRUSHED!!
    m_oldConfig
    //.smartCurrentLimit(ClimbLockConstants.kClimbLockCloseCurrentLimit)
    .idleMode(IdleMode.kBrake);                  // use brake mode to help keep us secure as much as we can
    */
  }



  public Command climbLockSecureCageCommand() {

    // lock the cage to the arm in preparation for climbing

    // we start the lock motor moving and since know how far to rotate the cage hooks we continue until they reach their target,
    // we just let it move while checking
    //
    // once locked, we maintain a stall force to prevent the cage locks from coming loose as long as we can

    System.out.println("Locking cage...");

    m_climbLock.set(ClimbLockConstants.kClimbLockPowerClose); // start the closing action

    return run(() -> climbLockEngaged());                     // returns true when closed - leaves the motor stalled
  }



  private boolean climbLockEngaged() {
    //Debug
    System.out.println("Current cage lock position is " + m_climbLock.getPosition().getValueAsDouble());
    System.out.println("Current stator current value is " + m_climbLock.getStatorCurrent().getValueAsDouble());
    System.out.println("Current supply current value is " + m_climbLock.getSupplyCurrent().getValueAsDouble());

    // check the encoder position to see if we've reached out limit

    if (m_climbLock.getPosition().getValueAsDouble() > ClimbLockConstants.kClimbLockFullyClosedEncoderCount) {

      System.out.println("LOCKED!!!");

      // we're closed so we'll set our new current limit, let the motor stall at our stall power level and return true

      //m_oldConfig.smartCurrentLimit(ClimbLockConstants.kClimbLockStallCurrentLimit);
      m_climbLockCurrentLimitsConfigs.withStatorCurrentLimit(ClimbLockConstants.kClimbLockStallCurrentStatorLimit);
      m_climbLockCurrentLimitsConfigs.withSupplyCurrentLimit(ClimbLockConstants.kClimbLockStallCurrentSupplyLimit);
      
      m_climbLock.getConfigurator().apply(m_climbLockCurrentLimitsConfigs);
      m_climbLock.getConfigurator().refresh(m_climbLockCurrentLimitsConfigs);

      m_climbLock.set(ClimbLockConstants.kClimbLockPowerStall);


      /* 
      m_oldMotor.configure(m_oldConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
      m_oldMotor.set(ClimbLockConstants.kClimbLockPowerStall);
      */

      return true;
    }

    // keep it going until we've closed - even if we don't fully close, we'll keep trying
    //
    // if we get some sort of partial close and don't reach our encoder target, we'll get saved by the current limit

    return false;
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }



  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
