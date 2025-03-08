// ClimbLock
//
// Controls the latching used to grab onto the cage for a low climb
//
// setup to use a SparkMAX controller

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.util.sendable.SendableBuilder;

// SparkMax imports - these come from REV Robotics

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

// our robot constants

import frc.robot.Constants.ClimbLockConstants;



public class ClimbLockSubsystem extends SubsystemBase {

  private SparkMax m_climbLockMotor;

  private SparkMaxConfig m_controllerConfig = new SparkMaxConfig();

  private RelativeEncoder m_climbLockEncoder;



  public ClimbLockSubsystem() {

    System.out.print("Initializing ClimbLockSubsystem...  ");
    System.out.print("BRUSHLESS...  ");

    m_climbLockMotor = new SparkMax(ClimbLockConstants.kClimbLockCanRioId, MotorType.kBrushless);   // CHANGE WHEN GOING BRUSHED!!

    m_controllerConfig
      .smartCurrentLimit(ClimbLockConstants.kClimbLockCloseCurrentLimit)
      .idleMode(IdleMode.kBrake);                  // use brake mode to help keep us secure as much as we can

    m_climbLockMotor.configure(m_controllerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    m_climbLockEncoder = m_climbLockMotor.getEncoder();

    m_climbLockEncoder.setPosition(0);    // we assume we're in the start position with the locks positioned to be fully open

    System.out.println("Done.");
  }



  public Command climbLockSecureCageCommand() {

    // lock the cage to the arm in preparation for climbing

    // we start the lock motor moving and since know how far to rotate the cage hooks we continue until they reach their target,
    // we just let it move while checking
    //
    // once locked, we maintain a stall force to prevent the cage locks from coming loose as long as we can

    System.out.println("Locking cage...");

    m_climbLockMotor.set(ClimbLockConstants.kClimbLockPowerClose);     // start the closing action

    return run(() -> climbLockEngaged());                              // returns true when closed - leaves the motor stalled
  }



  private boolean climbLockEngaged() {

    System.out.print("Current cage lock position is ");
    System.out.println(m_climbLockEncoder.getPosition());

    // check the encoder position to see if we've reached out limit

    if (m_climbLockEncoder.getPosition() > ClimbLockConstants.kClimbLockFullyClosedEncoderCount) {

      System.out.println("LOCKED!!!");

      // we're closed so we'll set our new current limit, let the motor stall at our stall power level and return true

      m_controllerConfig.smartCurrentLimit(ClimbLockConstants.kClimbLockStallCurrentLimit);
      m_climbLockMotor.configure(m_controllerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      m_climbLockMotor.set(ClimbLockConstants.kClimbLockPowerStall);

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
