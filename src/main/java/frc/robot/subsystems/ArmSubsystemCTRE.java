package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;

//our imports
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.CanbusName;
import frc.robot.util.*;

//CTRE Imports
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.DeviceIdentifier;



public class ArmSubsystemCTRE extends SubsystemBase{
    private final TalonFX m_arm = new TalonFX(ArmConstants.kArmMotor);
    private final TalonFX m_armFollower = new TalonFX(ArmConstants.kArmMotor2);
    private final CANcoder cancoder;

  private final TalonFXConfigurator motorConfig = new TalonFXConfigurator(new DeviceIdentifier(ArmConstants.kArmMotor, "TalonFX", CanbusName.armCANBus));

  double armAbsolutePosition;

  public ArmSubsystemCTRE(){
    m_arm.stopMotor();
    cancoder = new CANcoder(ArmConstants.kArmCANcoder, CanbusName.armCANBus);

    motorConfig.apply(new FeedbackConfigs().withFusedCANcoder(cancoder));

    m_armFollower.setControl(new Follower(ArmConstants.kArmMotor, true));
  }

  public void setArmPosition(double armPosition){
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
