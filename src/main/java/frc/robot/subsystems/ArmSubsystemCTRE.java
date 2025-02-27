package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;

//our imports
import frc.robot.Robot;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.CanbusName;
import frc.robot.util.*;

//CTRE Imports
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.configs.CANcoderConfiguration;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.TimedRobot;


public class ArmSubsystemCTRE extends SubsystemBase{
    private final TalonFX m_arm = new TalonFX(ArmConstants.kArmMotor);
    private final TalonFX m_armFollower = new TalonFX(ArmConstants.kArmMotor2);
    private final CANcoder cancoder;

  /* Be able to switch which control request to use based on a button press */
  /* Start at position 0, use slot 0 */
  private final PositionVoltage m_positionVoltage;
  /* Start at position 0, use slot 1 */
  private final PositionTorqueCurrentFOC m_positionTorque;

  private final StatusSignal<Angle> armAbsolutePosition;

  private final CANcoderConfiguration canConfig;

  public ArmSubsystemCTRE(){
    m_arm.stopMotor();
    cancoder = new CANcoder(ArmConstants.kArmCANcoder, CanbusName.armCANBus);
    m_positionVoltage = new PositionVoltage(0).withSlot(0);
    m_positionTorque = new PositionTorqueCurrentFOC(0).withSlot(1);

    armAbsolutePosition = cancoder.getPosition();

    m_armFollower.setControl(new Follower(m_arm.getDeviceID(), true));

    m_arm.setPosition(0);
    canConfig = new CANcoderConfiguration();
  }

  public void setPosition(double armPosition){
    double position = armPosition * ArmConstants.kArmCANCoderConversionFactor;
    m_arm.setPosition(position);
  }

  public void stopArmMotor(){
    m_arm.stopMotor();
  }

  public void armManualUp(){
    m_arm.set(ArmConstants.kArmSpeedUp);
  }

  public void armManualDown(){
    m_arm.set(ArmConstants.kArmSpeedDown);
  }

  public StatusSignal<Angle> getPosition(){

    return armAbsolutePosition;
  }

  //commands

  public Command armManualUpCommand(){
    return runOnce(
      () -> {armManualUp();}
    );
  }

  public Command armManualDownCommand(){
    return runOnce(
      () -> {armManualDown();}
    );
  }
}
    

