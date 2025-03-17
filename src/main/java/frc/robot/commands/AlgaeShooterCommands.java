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
import frc.robot.subsystems.ArmSubsystemCTRE;


import frc.robot.Constants.*;

public class AlgaeShooterCommands extends Command{

AlgaeSubsystemCTRE m_algaeSubsystem;
ArmSubsystemCTRE m_armSubsystem;

BooleanSupplier m_atSpeedCheck;
BooleanSupplier m_atArmPosition;

//Shooting Command (maybe not be needed)

public AlgaeShooterCommands(AlgaeSubsystemCTRE AlgaeSystem, ArmSubsystemCTRE armSubsystem, BooleanSupplier speedCheck, BooleanSupplier armCheck){

    m_algaeSubsystem = AlgaeSystem;
    m_armSubsystem = armSubsystem;
    m_atSpeedCheck = speedCheck;
    m_atArmPosition = armCheck;

    addRequirements(AlgaeSystem);
    addRequirements(armSubsystem);
}


public Command getShootCommand(){

    return Commands.sequence(

            m_algaeSubsystem.RevMotorsSHOOTCommand(),
            Commands.waitUntil(m_atSpeedCheck),

            m_algaeSubsystem.KickMotorONCommand(),
            Commands.waitSeconds(AlgaeConstants.kLongShooterWaitTime),
            m_algaeSubsystem.allMotorsOFFCommand()
    );

}

// Dumping Command

public Command getDumpCommand(){

    return Commands.sequence(

            m_algaeSubsystem.algaeDumpCommand(),
            m_algaeSubsystem.KickMotorONCommand(),
            Commands.waitSeconds(AlgaeConstants.kLongShooterWaitTime),

            m_algaeSubsystem.allMotorsOFFCommand()
    );

}

public Command getDepositCommand(int depositPosition){
    return Commands.sequence(
        m_armSubsystem.setArmPositionCommand(depositPosition),
        Commands.waitUntil(m_atArmPosition),
        getDumpCommand()
    );
}

/*public Command getAlgaeIntakeCommand(int intakePosition){
    return Commands.sequence(
        m_armSubsystem.setArmPositionCommand(intakePosition),
        Commands.waitUntil(m_atArmPosition),
        m_algaeSubsystem.algaeIntakeCommand(),
        Commands.waitSeconds(5),
        m_algaeSubsystem.allMotorsOFFCommand()
    );
}*/

public Command getIntakeCommand(int armPosition){
    return Commands.sequence(
        m_armSubsystem.setArmPositionCommand(armPosition),
        Commands.waitUntil(m_atArmPosition),
        m_algaeSubsystem.algaeIntakeCommand(),
        Commands.waitSeconds(2.5),
        m_algaeSubsystem.allMotorsOFFCommand()
    );
}
}
