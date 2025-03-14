package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import java.util.function.BooleanSupplier;

import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ArmSubsystem;


import frc.robot.Constants.*;

import static edu.wpi.first.units.Units.*;

public class
ShootArmPositionCommand extends Command{
    ArmSubsystem m_arm;
    LEDSubsystem m_led;
    BooleanSupplier m_armPositionCheck;

    public ShootArmPositionCommand(ArmSubsystem arm, LEDSubsystem led, BooleanSupplier armCheck){

        m_arm = arm;
        m_led = led;
        m_armPositionCheck = armCheck;

        addRequirements(arm);
        addRequirements(led);
    }

    public Command getShootPositionCommand(double shootPosition){
        return Commands.sequence(
            m_arm.setArmPositionCommand(shootPosition),
            Commands.waitUntil(m_armPositionCheck),
            m_led.LedShootCommand()
            );
    }

}
