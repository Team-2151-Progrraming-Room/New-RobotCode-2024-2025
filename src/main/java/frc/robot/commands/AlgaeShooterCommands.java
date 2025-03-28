// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import java.util.function.BooleanSupplier;

import frc.robot.subsystems.AlgaeSubsystemCTRE;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.LEDSubsystem;


import frc.robot.Constants.*;

public class AlgaeShooterCommands extends Command{

AlgaeSubsystemCTRE m_algaeSubsystem;
ArmSubsystem m_armSubsystem;
LEDSubsystem m_ledSubsystem;

BooleanSupplier m_atShootSpeedCheck;
BooleanSupplier m_atL3IntakeSpeedCheck;
BooleanSupplier m_atArmPosition;

public AlgaeShooterCommands(AlgaeSubsystemCTRE AlgaeSystem, ArmSubsystem armSubsystem, LEDSubsystem leds, BooleanSupplier shootSpeedCheck, BooleanSupplier l3IntakeSpeedCheck, BooleanSupplier armCheck){

    m_algaeSubsystem = AlgaeSystem;
    m_armSubsystem = armSubsystem;
    m_ledSubsystem = leds;

    m_atShootSpeedCheck = shootSpeedCheck;
    m_atL3IntakeSpeedCheck = l3IntakeSpeedCheck;
    m_atArmPosition = armCheck;

    addRequirements(AlgaeSystem);
    addRequirements(armSubsystem);
    addRequirements(leds);
}

public Command getShootCommand(){

    return Commands.sequence(

            m_algaeSubsystem.RevMotorsSHOOTCommand(),
                Commands.deadline(

                    Commands.waitUntil(m_atShootSpeedCheck),

                    Commands.sequence(
                        m_ledSubsystem.LedPreShootInitCommand(),
                        m_ledSubsystem.LedPreShootCommand()
                    )
            ),

            m_algaeSubsystem.KickMotorShootONCommand(),
            m_ledSubsystem.LedShootCommand(),
            Commands.waitSeconds(AlgaeConstants.kShooterWaitTime),

            Commands.parallel(
                m_ledSubsystem.LedPostShootCommand().withTimeout(LEDConstants.kLedPostShootTime),
                m_algaeSubsystem.allMotorsOFFCommand()
            )
    );

}
public Command getDepositCommand(double depositPosition){
    return Commands.sequence(
        m_armSubsystem.setArmPositionCommand(depositPosition),
        Commands.waitUntil(m_atArmPosition),
        m_ledSubsystem.setLedReefColorCommand(depositPosition),

        m_algaeSubsystem.algaeDumpCommand().withTimeout(AlgaeConstants.kDepositShooterWaitTime),
        m_algaeSubsystem.allMotorsOFFCommand()

    );
}

public Command getGroundIntakeCommand(double armPosition){
    return Commands.sequence(
        m_armSubsystem.setArmPositionCommand(armPosition),
        Commands.waitUntil(m_atArmPosition),
        m_ledSubsystem.setLedReefColorCommand(armPosition),

        m_algaeSubsystem.algaeGroundIntakeCommand()

    );
}

public Command getL2IntakeCommand(double armPosition){
    return Commands.sequence(
        m_armSubsystem.setArmPositionCommand(armPosition),
        Commands.waitUntil(m_atArmPosition),
        m_ledSubsystem.setLedReefColorCommand(armPosition),

        m_algaeSubsystem.algaeL2IntakeCommand(),
        Commands.waitSeconds(2.5),
        m_algaeSubsystem.allMotorsOFFCommand()

    );
}
public Command getL3IntakeCommand(double armPosition){
    return Commands.sequence(
        m_armSubsystem.setArmPositionCommand(armPosition),
        Commands.waitUntil(m_atArmPosition),
        m_ledSubsystem.setLedReefColorCommand(armPosition),

        m_algaeSubsystem.algaeL3IntakeCommand(),
        Commands.waitUntil(m_atL3IntakeSpeedCheck),
        Commands.waitSeconds(2.5),
        m_algaeSubsystem.allMotorsOFFCommand()
    );
}
}
