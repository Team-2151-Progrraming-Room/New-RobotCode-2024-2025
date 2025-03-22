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

  public static final class AlgaeConstants {
    //Id Values
    public static final int kAlgaeRevMotorID = 31;
    public static final int kAlgaeRev2MotorID = 32;
    public static final int kAlgaeKickMotorID = 33;


    //PID and FF Constants
    public static final double kAlgaePIDControllerP = .5;
    public static final double kAlgaePIDControllerI = 0;
    public static final double kAlgaePIDControllerD = 0;
    public static final int kAlgaePIDControllerS = 0;
    public static final int kAlgaePIDControllerV = 0;
    public static final int kAlgaeFeed = 0;//force to overcome gravity


    public static final int kAlgaeEncoderConversionFactor = 1000;//used to convert rpm to velocity

    public static final int kAlgaeRevVelocity = 60;
    //public static final int kAlgaeRev2Velocity = 10;

    public static final int kAlgaeSpeedTolerance = 2;

    //actions that don't require pid control
    public static final double kAlgaeKickMotorON = .25;
    public static final double kAlgaeIntake = 0.5;
    public static final double kAlgae2Intake = 0.75;
    public static final double kAlgaeDump = -0.5;

    public static final int kAlgaeVoltage = 0;

    public static final double kShortShooterWaitTime = 0.25;
    public static final double kLongShooterWaitTime = 1;

    //Current Limits
    public static final int kAlgaeRevMotorStatorCurrentLimit = 35;//Unsure what amps is good for a kraken x44,
    public static final int kAlgaeRevMotorSupplyCurrentLimit = 35;//so setting it to 35 seems to be a good temporary value.

    public static final int kAlgaeRev2MotorStatorCurrentLimit = 35;
    public static final int kAlgaeRev2MotorSupplyCurrentLimit = 35;//Supply should be at the very least the same value as stator
                                                                  //as supply determines how many amps can be drawn from the battery.

    public static final int kAlgaeKickMotorStatorCurrentLimit = 10;//10 should be a good value for a minion.
    public static final int kAlgaeKickMotorSupplyCurrentLimit = 10;

  }

  public static class CanbusName{
    public static final String rioCANBus = "rio";
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class ArmConstants{
    public static final int kArmMotor = 20;
    public static final int kFollowerMotor = 21;
    public static final int kArmCANcoder = 23;

    public static final double kArmSpeedUp = 0.5;
    public static final double kArmSpeedDown = -0.5;

    //pid configurations
    public static final double kArmPIDControllerP = 10;
    public static final double kArmPIDControllerI = 0;
    public static final double kArmPIDControllerD = 0;
    public static final int kArmPIDControllerS = 0;
    public static final int kArmPIDControllerA = 0;
    public static final int kArmPIDControllerV = 0;

    public static final double kArmCANCoderConversionFactor = 1.0/360;//cancoder absolute position uses a range of -0.5 to 0.5
    public static final double kArmPositionTolerance = .015;//in rotations of arm motor

    public static final double kMotionMagicCruiseVelocity = 80; // Units: rotations/sec
    public static final double kMotionMagicAcceleration = 160; // Units: rotations/sec^2
    public static final double kMotionMagicJerk = 1600;

    //arm positions in degrees
    public static final int kArmPositionGroundAlgae = 0;
    public static final int kArmPositionLowAlgae = 60;
    public static final int kArmPositionProcessor = 25;
    public static final int kArmPositionHighAlgae= 45;
    public static final int kArmPositionShoot = 90;
    public static final int kArmPositionClimb = 45;

    //Current Limits
    public static final int kArmMotorCurrentStatorLimit = 40;//40 amps is good for stator current of kraken (x60s)
    public static final int kArmMotorCurrentSupplyLimit = 40;//Temp value, but supply limit should be atleast the value of the stator.
    public static final int kFollowerMotorCurrentStatorLimit = 40;
    public static final int kFollowerMotorCurrentSupplyLimit = 40;

  }

public static class CoralConstants {
  public static final int kCoralMotor = 35;

  public static final double kCoralMotorSpeed = 0.5;
  public static final double kCorlaMotorIntakeSpeed = -0.5;

  public static final int kCoralStatorCurrentLimit = 10;//10 Amps should be good for stator according to Mr. Zog. Regardless, might be temporary.
  public static final int kCoralSupplyCurrentLimit = 10;//Supply should at the very least the same value as stator, given that it determines
                                                        //how much current can be drawn from the battery.
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
