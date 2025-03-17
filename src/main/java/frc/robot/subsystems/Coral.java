package frc.robot.subsystems;


//import static edu.wpi.first.units.Units.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//our imports
//import frc.robot.util.*;

//CTRE Imports
import com.ctre.phoenix6.hardware.TalonFXS;
import com.ctre.phoenix6.configs.TalonFXSConfiguration;
import com.ctre.phoenix6.signals.MotorArrangementValue;
//import com.ctre.phoenix6.hardware.TalonFXS;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;


// our robot constants

//import frc.robot.Robot;
import frc.robot.Constants.CoralConstants;
import frc.robot.Constants.CanbusName;
//import frc.robot.util.*;


public class Coral extends SubsystemBase{

    private final TalonFXS m_CoralMotor = new TalonFXS(CoralConstants.kCoralMotor, CanbusName.rioCANBus);
    private final TalonFXSConfiguration configs = new TalonFXSConfiguration();
    private final CurrentLimitsConfigs coralLimitConfigs = new CurrentLimitsConfigs();

    public Coral(){
        m_CoralMotor.stopMotor();

        //Current Limit Configs
        coralLimitConfigs.withStatorCurrentLimit(CoralConstants.kCoralStatorCurrentLimit);
        coralLimitConfigs.withSupplyCurrentLimit(CoralConstants.kCoralSupplyCurrentLimit);

        //Config applications
        //configs.MotorOutput.ConnectedMotorValue = ConnectedMotorValue.Minion_JST;
        configs.Commutation.MotorArrangement = MotorArrangementValue.Minion_JST;
        //configs.withCurrentLimits(coralLimitConfigs); //Current have this commented out so that the temp current limits don't get applied

        m_CoralMotor.getConfigurator().apply(configs);
        m_CoralMotor.setSafetyEnabled(true);
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
    public Command coralMotorDepositCommand(){
        return run(
            () -> {
                coralMotorOn();
            });
    }

    public Command coralMotorIntakeCommand(){
        return run(
            () -> {
                m_CoralMotor.set(CoralConstants.kCorlaMotorIntakeSpeed);
            });
    }

    public Command coralMotorOffCommand(){
        return runOnce(
            () -> {
                coralMotorOff();
            });
    }
}
