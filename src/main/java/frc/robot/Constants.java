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

package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

public static class CoralConstants {
  public static final int kCoralMotor = 35;

  public static final double kCoralMotorSpeed = 0.5;

  public static final String canbusName = "rio";

  public static final int kCoralStatorCurrentLimit = 10;//10 Amps should be good for stator according to Mr. Zog. Regardless, might be temporary.
  public static final int kCoralSupplyCurrentLimit = 10;//Supply should at the very least the same value as stator, given that it determines
                                                        //how much current can be drawn from the battery.
}
public static class OperatorConstants {
  public static final int kDriverControllerPort = 0;
}
}
