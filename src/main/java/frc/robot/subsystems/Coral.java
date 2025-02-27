package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.util.sendable.SendableBuilder;

//our imports
import frc.robot.Robot;
import frc.robot.util.*;

//CTRE Imports
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFXS;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.configs.CANcoderConfiguration;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.TimedRobot;

// our robot constants

//import frc.robot.Robot;
import frc.robot.Constants.CoralConstants;
//import frc.robot.util.*;

import java.util.function.DoubleSupplier;

public class Coral extends SubsystemBase{

    private final TalonFXS m_CoralMotor = new TalonFXS(CoralConstants.kCoralMotor);

    public Coral(){
        m_CoralMotor.stopMotor();
    }

    //methods to turn motor on/off
    public void coralMotorOn(){
        m_CoralMotor.set(CoralConstants.kCoralMotorSpeed);
    }

    public void coralMotorOff(){
        double m_coralRpmTarget = 0.0;

        m_CoralMotor.set(m_coralRpmTarget);
    }

    //Commands
    public Command coralMotorOnCommand(){
        return runOnce(
            () -> {
                coralMotorOn();
            });
    }

    public Command coralMotorOffCommand(){
        return runOnce(
            () -> {
                coralMotorOff();
            });
    }
}