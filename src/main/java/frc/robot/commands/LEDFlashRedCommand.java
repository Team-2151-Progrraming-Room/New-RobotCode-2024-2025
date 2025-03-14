// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LEDSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class LEDFlashRedCommand extends Command {

  private static final int m_paceFactor              = 15;
  private static int m_paceCount;
  private int rVal;
  private int gVal;
  private int bVal;

  LEDSubsystem m_LEDSubsystem;
  /** Creates a new LEDFlashRed. */
  public LEDFlashRedCommand(LEDSubsystem leds) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_LEDSubsystem = leds;
    addRequirements(leds);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_paceCount = m_paceFactor;
    rVal = 0;
    gVal = 0;
    bVal = 0;
    m_LEDSubsystem.setAllLedsRGBCommand(rVal, gVal, bVal);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_paceCount--;

        if (m_paceCount > 0 ) {
            return;                     // don't do anyting - yet...
        }

        m_paceCount = m_paceFactor;     // reset counter and do our thing

        //change r value 0 to 255, flashing the LEDs on and off
        if(rVal == 0){
          rVal = 255;
        } else if(rVal == 255){
          rVal = 0;
        }

        m_LEDSubsystem.setAllLedsRGB(rVal, gVal, bVal);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
