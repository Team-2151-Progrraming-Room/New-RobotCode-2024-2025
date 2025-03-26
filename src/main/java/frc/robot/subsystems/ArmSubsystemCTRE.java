package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;

//our imports
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.CanbusName;
//import frc.robot.util.*;
import edu.wpi.first.math.MathUtil;

//CTRE Imports
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;


public class ArmSubsystemCTRE extends SubsystemBase{
  //physical devices
  private final TalonFX m_arm = new TalonFX(ArmConstants.kArmMotor);
  private final TalonFX m_armFollower = new TalonFX(ArmConstants.kFollowerMotor);
  private final CANcoder cancoder;

  //configurations
  private final TalonFXConfiguration armConfig = new TalonFXConfiguration();
  private final TalonFXConfiguration followerConfig = new TalonFXConfiguration();

  private final FeedbackConfigs feedback = new FeedbackConfigs();

  private final CurrentLimitsConfigs m_armCurrentConfig = new CurrentLimitsConfigs();
  private final CurrentLimitsConfigs m_armFollowerCurrentConfigs = new CurrentLimitsConfigs();
  //Might want a different current limit on the armFollower.

  //motion magic
  private final MotionMagicVoltage motionMagicControl = new MotionMagicVoltage(0);
  private double position;


  public ArmSubsystemCTRE(){

    m_arm.stopMotor();
    m_armFollower.stopMotor();

    cancoder = new CANcoder(ArmConstants.kArmCANcoder, CanbusName.rioCANBus);

    //PID values for the motors
        armConfig.Slot0.kP = ArmConstants.kArmPIDControllerP;
        armConfig.Slot0.kI = ArmConstants.kArmPIDControllerI;
        armConfig.Slot0.kD = ArmConstants.kArmPIDControllerD;
        //armConfig.Slot0.kV = ArmConstants.kArmPIDControllerV;
        //armConfig.Slot0.kA = ArmConstants.kArmPIDControllerA;
        //armConfig.Slot0.kS = ArmConstants.kArmPIDControllerS;

    //Motion Magic Config
    armConfig.MotionMagic.MotionMagicCruiseVelocity = ArmConstants.kMotionMagicCruiseVelocity;
    armConfig.MotionMagic.MotionMagicAcceleration = ArmConstants.kMotionMagicAcceleration;
    armConfig.MotionMagic.MotionMagicJerk = ArmConstants.kMotionMagicJerk;

    //CanCoder Config
    feedback.FeedbackRemoteSensorID = ArmConstants.kArmCANcoder;
    feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;

    //Current Limit Config
    m_armCurrentConfig.withStatorCurrentLimit(ArmConstants.kArmMotorCurrentStatorLimit);
    m_armCurrentConfig.withSupplyCurrentLimit(ArmConstants.kArmMotorCurrentSupplyLimit);

    m_armFollowerCurrentConfigs.withStatorCurrentLimit(ArmConstants.kFollowerMotorCurrentStatorLimit);
    m_armFollowerCurrentConfigs.withSupplyCurrentLimit(ArmConstants.kFollowerMotorCurrentSupplyLimit);


    //Config Application

    armConfig.withFeedback(feedback);
    armConfig.withCurrentLimits(m_armCurrentConfig);
    m_arm.getConfigurator().apply(armConfig);
    //m_arm.setSafetyEnabled(true);//Turns on safety.
    m_arm.setNeutralMode(NeutralModeValue.Brake);

    followerConfig.withCurrentLimits(m_armFollowerCurrentConfigs);
    m_armFollower.getConfigurator().apply(followerConfig);
    //m_armFollower.setSafetyEnabled(true);
    m_armFollower.setNeutralMode(NeutralModeValue.Brake);
    m_armFollower.setControl(new Follower(ArmConstants.kArmMotor, true));
  }

  public void setArmPosition(double armPosition){
    position = armPosition * ArmConstants.kArmCANCoderConversionFactor;
    System.out.println("Processed armPos: " + position);
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

    return cancoder.getAbsolutePosition().getValueAsDouble();
  }

  public boolean atArmPosition() {

    if (MathUtil.isNear(position, getPosition(), ArmConstants.kArmPositionTolerance)){
      return true;
    }
    return false;
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
