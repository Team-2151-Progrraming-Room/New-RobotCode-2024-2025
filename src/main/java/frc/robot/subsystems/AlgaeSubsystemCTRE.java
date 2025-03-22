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
import com.ctre.phoenix6.controls.Follower;
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
    TalonFXSConfiguration kickConfigs = new TalonFXSConfiguration();
    MotorOutputConfigs invertConfig = new MotorOutputConfigs();

    CurrentLimitsConfigs revCurrentLimitConfigs = new CurrentLimitsConfigs();
    CurrentLimitsConfigs rev2CurrentLimitConfigs = new CurrentLimitsConfigs();
    CurrentLimitsConfigs kickCurrentLimitsConfigs = new CurrentLimitsConfigs();
    Slot0Configs slot0;

    final VelocityVoltage m_request;

    public AlgaeSubsystemCTRE (){
        //Motion magic thing
        m_request = new VelocityVoltage(AlgaeConstants.kAlgaeVoltage).withSlot(0);
        //PID Values
        slot0 = configs.Slot0;
        slot0.kS = AlgaeConstants.kAlgaePIDControllerS;
        slot0.kV = AlgaeConstants.kAlgaePIDControllerV;
        slot0.kP = AlgaeConstants.kAlgaePIDControllerP;
        slot0.kI = AlgaeConstants.kAlgaePIDControllerI;
        slot0.kD = AlgaeConstants.kAlgaePIDControllerD;

        //Config applications below
        
        //Current Limits
        revCurrentLimitConfigs.withStatorCurrentLimit(AlgaeConstants.kAlgaeRevMotorStatorCurrentLimit);
        revCurrentLimitConfigs.withSupplyCurrentLimit(AlgaeConstants.kAlgaeRevMotorSupplyCurrentLimit);
        
        rev2CurrentLimitConfigs.withStatorCurrentLimit(AlgaeConstants.kAlgaeRev2MotorStatorCurrentLimit);
        rev2CurrentLimitConfigs.withSupplyCurrentLimit(AlgaeConstants.kAlgaeRev2MotorSupplyCurrentLimit);

        kickCurrentLimitsConfigs.withStatorCurrentLimit(AlgaeConstants.kAlgaeKickMotorStatorCurrentLimit);
        kickCurrentLimitsConfigs.withSupplyCurrentLimit(AlgaeConstants.kAlgaeKickMotorSupplyCurrentLimit);

        //Not applying currentLimits yet as the constants are random

        //configs.withCurrentLimits(revCurrentLimitConfigs);
        m_Rev.getConfigurator().apply(configs);
        m_Rev.setSafetyEnabled(true);//Enabling safety

        invertConfig.Inverted = InvertedValue.CounterClockwise_Positive;
        configs.withMotorOutput(invertConfig);
        m_Rev2.getConfigurator().apply(configs);
        m_Rev2.setSafetyEnabled(true);


        //kickConfigs.withCurrentLimits(kickCurrentLimitsConfigs); Current Limit application, commented out for now.
        kickConfigs.Commutation.MotorArrangement = MotorArrangementValue.Minion_JST;
        m_Kick.getConfigurator().apply(kickConfigs);
        m_Kick.setSafetyEnabled(true);

    }


    public void algaeIntake(){
        m_Rev.set(AlgaeConstants.kAlgaeIntake);
        m_Rev2.set(AlgaeConstants.kAlgaeIntake);    
    }

    public void algaeGroundIntake(){
        m_Rev.set(AlgaeConstants.kAlgaeIntake);
        m_Rev2.set(AlgaeConstants.kAlgae2Intake);
    }

    public void algaeDump(){
        m_Rev.set(AlgaeConstants.kAlgaeDump);
        m_Rev2.set(AlgaeConstants.kAlgaeDump);
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
        m_Rev2.setControl(m_request.withVelocity(AlgaeConstants.kAlgaeRevVelocity).withFeedForward(AlgaeConstants.kAlgaeFeed));
    }

    public void KickMotorON(){
        m_Kick.set(AlgaeConstants.kAlgaeKickMotorON);
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

        if (MathUtil.isNear(AlgaeConstants.kAlgaeRevVelocity, getRevVelocity(), AlgaeConstants.kAlgaeSpeedTolerance) 
        && MathUtil.isNear(AlgaeConstants.kAlgaeRevVelocity, getRev2Velocity(), AlgaeConstants.kAlgaeSpeedTolerance)) 
        {
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
        return runOnce(
            () -> {RevMotorsSHOOT();}
        );
    }

    public Command KickMotorONCommand(){
        return runOnce(
            () -> {KickMotorON();}
        );
    }

    public Command algaeIntakeCommand(){
        return runOnce(
            () -> {algaeIntake();}
        );
    }

    public Command algaeGroundIntakeCommand(){
        return runOnce(
            () -> {algaeGroundIntake();}
        );
    }


    public Command algaeDumpCommand(){
        return runOnce(
            () -> {algaeDump();}
        );
    }
}
