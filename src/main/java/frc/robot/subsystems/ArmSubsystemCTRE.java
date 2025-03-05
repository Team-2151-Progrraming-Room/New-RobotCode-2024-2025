package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;

//our imports
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.CanbusName;
import frc.robot.util.*;

//CTRE Imports
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.configs.FeedbackConfigs;



public class ArmSubsystemCTRE extends SubsystemBase{
    private final TalonFX m_arm = new TalonFX(ArmConstants.kArmMotor);
    private final TalonFX m_armFollower = new TalonFX(ArmConstants.kArmMotor2);
    private final CANcoder cancoder;

    private final MotionMagicVoltage motionMagicControl = new MotionMagicVoltage(0);

  //private final TalonFXConfigurator motorConfig = new TalonFXConfigurator(new DeviceIdentifier(ArmConstants.kArmMotor, "TalonFX", CanbusName.armCANBus));
  private final TalonFXConfiguration armConfig = new TalonFXConfiguration();

  double armAbsolutePosition;

  public ArmSubsystemCTRE(){
    m_arm.stopMotor();
    cancoder = new CANcoder(ArmConstants.kArmCANcoder, CanbusName.armCANBus);

    armConfig.Slot0.kP = ArmConstants.kArmPIDControllerP;
        armConfig.Slot0.kI = ArmConstants.kArmPIDControllerI;
        armConfig.Slot0.kD = ArmConstants.kArmPIDControllerD;
        armConfig.Slot0.kS = ArmConstants.kArmPIDControllerS;
        armConfig.Slot0.kV = ArmConstants.kArmPIDControllerV;
        armConfig.Slot0.kA = ArmConstants.kArmPIDControllerA;

    armConfig.MotionMagic.MotionMagicCruiseVelocity = 80; // Units: rotations/sec
    armConfig.MotionMagic.MotionMagicAcceleration = 160; // Units: rotations/sec^2
    armConfig.MotionMagic.MotionMagicJerk = 1600;

    m_arm.getConfigurator().apply(new FeedbackConfigs().withFusedCANcoder(cancoder));
    m_arm.getConfigurator().apply(armConfig);

    m_armFollower.setControl(new Follower(ArmConstants.kArmMotor, true));
  }

  public void setArmPosition(double armPosition){
    double position = armPosition * ArmConstants.kArmCANCoderConversionFactor;
    m_arm.setControl(motionMagicControl.withPosition(position));
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

  public double getPosition(){

    armAbsolutePosition = cancoder.getAbsolutePosition().getValueAsDouble();
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

  public Command armStopCommand(){
    return runOnce(
      () -> {stopArmMotor();}
    );
  }

  public Command setArmPositionCommand(double degree){
    return runOnce(
      () -> {setArmPosition(degree);}
    );
  }
}
