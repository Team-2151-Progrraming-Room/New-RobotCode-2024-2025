// ClimbLock
//
// Controls the latching used to grab onto the cage for a low climb
//
// Setup using a DC Motor via a Talon FXS.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.util.sendable.SendableBuilder;

//CTRE Imports

import com.ctre.phoenix6.hardware.TalonFXS;
import com.ctre.phoenix6.signals.BrushedMotorWiringValue;
import com.ctre.phoenix6.signals.MotorArrangementValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.TalonFXSConfiguration;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;

//Climb Lock Constants
import frc.robot.Constants.ClimbLockConstants;
import frc.robot.Constants.CanbusName;

public class ClimbLockSubsystem extends SubsystemBase {
  //Hardware and configurations for them.
  private TalonFXS m_climbLock;
  private TalonFXSConfiguration m_climbLockConfiguration;
  private CurrentLimitsConfigs m_climbLockCurrentLimitsConfigs;

  public ClimbLockSubsystem() {

    System.out.print("Initializing ClimbLockSubsystem...  ");
    System.out.print("Using a DC Motor and a TalonFXS!");

    m_climbLock = new TalonFXS(ClimbLockConstants.kClimbLockCanRioId, CanbusName.rioCANBus);

    //Talon FXS Configuration
    m_climbLockConfiguration = new TalonFXSConfiguration();
    m_climbLockConfiguration.Commutation.MotorArrangement = MotorArrangementValue.Brushed_DC;
    m_climbLockConfiguration.Commutation.BrushedMotorWiring = BrushedMotorWiringValue.Leads_A_and_C;
    //Not using Advanced Hall Support, seems like a minor upgrade, but it is something worth noting


    //Initial Current Limit Configuration
    //This current config changes later when the lock moves.
    m_climbLockCurrentLimitsConfigs = new CurrentLimitsConfigs();
    m_climbLockCurrentLimitsConfigs.withStatorCurrentLimit(ClimbLockConstants.kClimbLockCloseCurrentStatorLimit);//Closed values
    m_climbLockCurrentLimitsConfigs.withSupplyCurrentLimit(ClimbLockConstants.kClimbLockCloseCurrentSupplyLimit);

    //Config application
    m_climbLockConfiguration.withCurrentLimits(m_climbLockCurrentLimitsConfigs);
    m_climbLock.getConfigurator().apply(m_climbLockConfiguration);

    m_climbLock.setNeutralMode(NeutralModeValue.Brake);//Set the motor to brake mode, will resist movement when the motor
                                                      //isn't running.

    m_climbLock.setSafetyEnabled(true);//Sets safety.

    System.out.println("Done.");
  }


  //Method that is the first step of the climbLockSecureCageCommand, gets the motor to start moving.
  public void climbLockStartUp(){

    // we start the lock motor moving and we just let it move at the same speed the entire time.
    //
    // once locked, we maintain a stall force to prevent the cage locks from coming loose as long as we can
    //This is done by lowering the current, which will cause the motor to move slower.

    System.out.println("Locking cage...");

    m_climbLock.set(ClimbLockConstants.kClimbLockPowerClose); // start the closing action
  }

  //Method that checks to see if the climbLock has engaged onto the bars of the cage.
  //Checks to see if the current of the motor has rised above a certain current, which means that it the motor is acting
  //against the bars of the cage.
  public boolean climbLockEngageCheck() {

    if (m_climbLock.getStatorCurrent().getValueAsDouble() > ClimbLockConstants.kClimbLockCurrentStallPoint) {
      System.out.println("Check Ran True");
      return true;
    } else {
      System.out.println("Check Ran False");
      return false;
    }
  }

  // Method that is used to lower the current limits when the climbLock is engaged against the bars of the cage.
  public void climbLocking(){
    //Debug
    System.out.println("Current stator current value is " + m_climbLock.getStatorCurrent().getValueAsDouble());
    System.out.println("Current supply current value is " + m_climbLock.getSupplyCurrent().getValueAsDouble());

    //check to see if the cage lock has locked onto the bars of the cage by seeing if the current jumps up.
    if(climbLockEngageCheck() == true){
      System.out.println("LOCKED!!!");

      // we're closed so we'll set our new current limit, let the motor stall at our stall power level and return true
      m_climbLockCurrentLimitsConfigs.withStatorCurrentLimit(ClimbLockConstants.kClimbLockStallCurrentStatorLimit);
      m_climbLockCurrentLimitsConfigs.withSupplyCurrentLimit(ClimbLockConstants.kClimbLockStallCurrentSupplyLimit);

      m_climbLock.getConfigurator().apply(m_climbLockCurrentLimitsConfigs);
      m_climbLock.getConfigurator().refresh(m_climbLockCurrentLimitsConfigs);

    }
    // keep it motor going at the same speed -- the current limit lowering will slow it down.
  }

}