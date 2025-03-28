// Imports
package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;

//import static edu.wpi.first.units.Units.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//CTRE Imports
import com.ctre.phoenix6.hardware.TalonFXS;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorArrangementValue;
import com.ctre.phoenix6.configs.TalonFXSConfiguration;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.configs.MotorOutputConfigs;

//Constants imports
import frc.robot.Constants.AlgaeConstants;
import frc.robot.Constants.CanbusName;


public class AlgaeSubsystemCTRE extends SubsystemBase{
    //Physical Devices
    TalonFXS m_Rev = new TalonFXS(AlgaeConstants.kAlgaeRevMotorID, CanbusName.rioCANBus);
    TalonFXS m_Rev2  = new TalonFXS(AlgaeConstants.kAlgaeRev2MotorID, CanbusName.rioCANBus);
    TalonFXS m_Kick = new TalonFXS(AlgaeConstants.kAlgaeKickMotorID, CanbusName.rioCANBus);

    //Configs
    TalonFXSConfiguration configs = new TalonFXSConfiguration();
    TalonFXSConfiguration configs2 = new TalonFXSConfiguration();
    TalonFXSConfiguration kickConfigs = new TalonFXSConfiguration();

    CurrentLimitsConfigs revCurrentLimitConfigs = new CurrentLimitsConfigs();
    CurrentLimitsConfigs rev2CurrentLimitConfigs = new CurrentLimitsConfigs();
    CurrentLimitsConfigs kickCurrentLimitsConfigs = new CurrentLimitsConfigs();
    Slot0Configs slot0;

    MotorOutputConfigs invertConfig = new MotorOutputConfigs();

    final VelocityVoltage m_request;

    public AlgaeSubsystemCTRE (){
        //PID Values
        slot0 = configs.Slot0;
        slot0.kP = AlgaeConstants.kAlgaePIDControllerP;
        slot0.kI = AlgaeConstants.kAlgaePIDControllerI;
        slot0.kD = AlgaeConstants.kAlgaePIDControllerD;
        //slot0.kS = AlgaeConstants.kAlgaePIDControllerS;
        //slot0.kV = AlgaeConstants.kAlgaePIDControllerV;

        m_request = new VelocityVoltage(AlgaeConstants.kAlgaeVoltage).withSlot(0);

        //Config applications below

        //Current Limits
        revCurrentLimitConfigs.withStatorCurrentLimit(AlgaeConstants.kAlgaeRevMotorStatorCurrentLimit);
        revCurrentLimitConfigs.withSupplyCurrentLimit(AlgaeConstants.kAlgaeRevMotorSupplyCurrentLimit);

        rev2CurrentLimitConfigs.withStatorCurrentLimit(AlgaeConstants.kAlgaeRev2MotorStatorCurrentLimit);
        rev2CurrentLimitConfigs.withSupplyCurrentLimit(AlgaeConstants.kAlgaeRev2MotorSupplyCurrentLimit);

        kickCurrentLimitsConfigs.withStatorCurrentLimit(AlgaeConstants.kAlgaeKickMotorStatorCurrentLimit);
        kickCurrentLimitsConfigs.withSupplyCurrentLimit(AlgaeConstants.kAlgaeKickMotorSupplyCurrentLimit);

        //Applications to rev1 Configs
        configs.withCurrentLimits(revCurrentLimitConfigs);
        m_Rev.getConfigurator().apply(configs);
        //m_Rev.setSafetyEnabled(true);//Enabling safety
        
        //Applications to rev2 Configs
        configs2.withCurrentLimits(rev2CurrentLimitConfigs);
        invertConfig.Inverted = InvertedValue.Clockwise_Positive;
        configs.withMotorOutput(invertConfig);
        m_Rev2.getConfigurator().apply(configs);
        //   m_Rev2.setControl(new Follower(AlgaeConstants.kAlgaeRevMotorID, true));---------------
        //m_Rev2.setSafetyEnabled(true);

        kickConfigs.withCurrentLimits(kickCurrentLimitsConfigs);
        kickConfigs.Commutation.MotorArrangement = MotorArrangementValue.Minion_JST;
        m_Kick.getConfigurator().apply(kickConfigs);
        //m_Kick.setSafetyEnabled(true);

    }


    public void algaeGroundIntake(){
        m_Rev.set(AlgaeConstants.kAlgaeGroundIntake);
        m_Rev2.set(AlgaeConstants.kAlgaeGroundIntake);
    }

    public void algaeL2Intake(){
        m_Rev.set(AlgaeConstants.kAlgaeL2Intake);
        m_Rev2.set(AlgaeConstants.kAlgaeL2Intake);
    }
    public void algaeL3Intake(){
        m_Rev.setControl(m_request.withVelocity(AlgaeConstants.kAlgaeL3Velocity).withFeedForward(AlgaeConstants.kAlgaeFeed));
        m_Rev2.setControl(m_request.withVelocity(AlgaeConstants.kAlgaeL3Velocity).withFeedForward(AlgaeConstants.kAlgaeFeed));
    }

    public void algaeDump(){
        m_Rev.set(AlgaeConstants.kAlgaeDump);
        m_Rev2.set(AlgaeConstants.kAlgaeDump);
        m_Kick.set(AlgaeConstants.kAlgaeKickMotorON);

    }

    // For the Motors to turn OFF!
    public void RevMotorsOFF(){
        m_Rev.stopMotor();
        m_Rev2.stopMotor();
    }

    public void KickMotorOFF(){
        m_Kick.stopMotor();
    }

    public void allMotorsOFF(){
        m_Rev.stopMotor();
        m_Rev2.stopMotor();
        m_Kick.stopMotor();
    }

    // For the Motors to turn ON!
    public void RevMotorsSHOOT(){
        m_Rev.setControl(m_request.withVelocity(AlgaeConstants.kAlgaeRevVelocity).withFeedForward(AlgaeConstants.kAlgaeFeed));
        m_Rev2.setControl(m_request.withVelocity(AlgaeConstants.kAlgaeRev2Velocity).withFeedForward(AlgaeConstants.kAlgaeFeed));
    }

    public void KickMotorON(){
        m_Kick.set(AlgaeConstants.kAlgaeKickMotorON);
    }

    public void KickMotorShootON(){
        m_Kick.set(AlgaeConstants.kAlgaeKickShootON);
    }

    public double getRevVelocity(){
        double velocity = m_Rev.getVelocity().getValueAsDouble();
        System.out.println(velocity);
        return velocity;
    }

    public double getRev2Velocity(){
        double velocity = m_Rev2.getVelocity().getValueAsDouble();
        System.out.println(velocity);
        return velocity;
    }

    public boolean atShooterSpeed() {

        if (MathUtil.isNear(AlgaeConstants.kAlgaeRevVelocity, getRevVelocity(), AlgaeConstants.kAlgaeSpeedTolerance) && MathUtil.isNear(AlgaeConstants.kAlgaeRev2Velocity, getRev2Velocity(), AlgaeConstants.kAlgaeSpeedTolerance)) {
          return true;
        }
        return false;
      }

    public boolean atL3Speed(){
        if (MathUtil.isNear(AlgaeConstants.kAlgaeL3Velocity, getRevVelocity(), AlgaeConstants.kAlgaeSpeedTolerance /*&& MathUtil.isNear(AlgaeConstants.kAlgaeRev2Velocity, getRev2Velocity(), AlgaeConstants.kAlgaeSpeedTolerance)*/)) {
            return true;
          }
        return false;
    }
    //Commands

    public Command RevMotorOFFCommand(){
        return runOnce(
            () -> {RevMotorsOFF();}
        );
    }

    public Command KickMotorOFFCommand(){
        return runOnce(
            () -> {KickMotorOFF();}
        );
    }

    public Command allMotorsOFFCommand(){
        return runOnce(
            () -> {allMotorsOFF();}
        );
    }

    public Command RevMotorsSHOOTCommand(){
        System.out.println("rev motors on command running");
        return runOnce(
            () -> {RevMotorsSHOOT();}
        );
    }

    public Command KickMotorONCommand(){
        return runOnce(
            () -> {KickMotorON();}
        );
    }

    public Command KickMotorShootONCommand(){
        return runOnce(
            () -> {KickMotorShootON();}
        );
    }

    public Command algaeGroundIntakeCommand(){
        return runOnce(
            () -> {algaeGroundIntake();}
        );
    }

    public Command algaeL2IntakeCommand(){
        return runOnce(
            () -> {algaeL2Intake();}
        );
    }
    public Command algaeL3IntakeCommand(){
        return runOnce(
            () -> {algaeL3Intake();}
        );
    }

    public Command algaeDumpCommand(){
        return run(
            () -> {algaeDump();}
        );
    }
}
