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
  
  public static final class DriveTrainConstants{
    public static final int Pigeon = 10;

    public static final int FRMotor1 = 1;
    public static final int FRMotor2 = 2;
    public static final int FRCanCoder = 3;

    public static final int FLMotor1 = 4;
    public static final int FLMotor2 = 5;
    public static final int FLCanCoder = 6;

    public static final int BRMotor1 = 11;
    public static final int BRMotor2 = 12;
    public static final int BRCanCoder = 13;

    public static final int BLMotor1 = 14;
    public static final int BLMotor2 = 15;
    public static final int BLCanCoder = 16;

  }


  public static class CanbusName{
    public static final String armCANBus = "rio";
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class ClimbLockConstants {
    public static final int kClimbLockCanRioId      = 50;

    /* Unused, encoder was not used.
    public static final int kClimbLockEncoderPpr    = 7; // the motor returns 7 pulses per rotation
    public static final int kClimbLockGearRatio     = 188; // 188:1 gear ratio
    public static final int kClimbLockDegreesToLock = 80; // how far does the output shaft need to turn to engage the locks
    public static final int kClimbLockFullyClosedEncoderCount = (int) (((double) kClimbLockDegreesToLock / (double) 360) *
                                                                        (double) kClimbLockGearRatio *
                                                                        (double) kClimbLockEncoderPpr);
    */

    public static final double kClimbLockPowerClose = 0.50; // speed we want to close at - this operation is fine to do in open loop
    public static final double kClimbLockPowerStall = 0.25; // leave the motor at this power level once closed to hold it

    public static final int kClimbLockCloseCurrentStatorLimit  = 20; // Stator amps - while moving
    public static final int kClimbLockCloseCurrentSupplyLimit  = 20; // Supply amps - while moving

    public static final int kClimbLockStallCurrentStatorLimit = 20; // Stator amps - when we've closed and trying to stay locked
    public static final int kClimbLockStallCurrentSupplyLimit = 20;// Supply amps - when we've closed.
    //For the  am-2765 DC Motor, stall current should be 15-20 amps


    public static final int kClimbLockCurrentStallPoint = 20;//Constant used to determine that the lock has engaged the cage.
    //If the current of the motor goes over this point, then current has jumped up because the motor is trying to move through the cage.
    //Therefore, we can conclude that the climb lock has engaged the case, and the current limit should decrease.

    //Everything is set to 20 amps for now after testing this on the test board, it seems like it snap shut correctly at values under 20
    //so everything is set to 20 for now.
  }
}
