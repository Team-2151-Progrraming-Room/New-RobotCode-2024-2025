package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimbLockSubsystem;


import frc.robot.Constants.*;

import static edu.wpi.first.units.Units.*;

public class ClimbPositionLockCommand extends Command{

ArmSubsystem m_armSubsystem;
LEDSubsystem m_ledSubsystem;
ClimbLockSubsystem m_climbSubsystem;

public ClimbPositionLockCommand(ArmSubsystem arm, LEDSubsystem led){

}

}
