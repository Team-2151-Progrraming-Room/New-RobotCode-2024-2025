// Imports
package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;

import static edu.wpi.first.units.Units.*;

import java.io.ObjectInputFilter.Config;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.util.sendable.SendableBuilder;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXSConfiguration;
import com.ctre.phoenix6.hardware.TalonFXS;
import com.ctre.phoenix6.controls.VelocityVoltage;

import frc.robot.Robot;
import frc.robot.Constants.AlgaeConstants;

// Main Class
public class AlgaeSubsystemCTRE extends SubsystemBase{
    TalonFXS m_Rev = new TalonFXS(AlgaeConstants.kAlgaeRevMotorID);
    TalonFXS m_Rev2  = new TalonFXS(AlgaeConstants.kAlgaeRev2MotorID);
    TalonFXS m_Kick = new TalonFXS(AlgaeConstants.kAlgaeKickMotorID);

    TalonFXSConfiguration configs = new TalonFXSConfiguration();
    
    Slot0Configs slot0;
    
    // Sub Class
    public AlgaeSubsystemCTRE (){
        slot0 = configs.Slot0;
        slot0.kS = AlgaeConstants.kAlgaePIDControllerS;
        slot0.kV = AlgaeConstants.kAlgaePIDControllerV;
        slot0.kP = AlgaeConstants.kAlgaePIDControllerP;
        slot0.kI = AlgaeConstants.kAlgaePIDControllerI;
        slot0.kD = AlgaeConstants.kAlgaePIDControllerD;


        m_Rev.getConfigurator().apply(configs);
        m_Rev.setInverted(true);
        m_Rev2.getConfigurator().apply(configs);

        m_request = new VelocityVoltage(AlgaeConstants.kAlgaeVoltage).withSlot(0);
    }

    final VelocityVoltage m_request;

    public void algaeIntake(){
        m_Rev.set(AlgaeConstants.kAlgaeIntake);
        m_Rev2.set(AlgaeConstants.kAlgaeIntake);
        
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
}