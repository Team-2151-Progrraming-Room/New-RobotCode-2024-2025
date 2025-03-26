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
    public static final double kAlgaeL3Velocity = 30;
    //public static final int kAlgaeRev2Velocity = 10;

    public static final int kAlgaeSpeedTolerance = 2;

    //actions that don't require pid control
    public static final double kAlgaeKickMotorON = -.25;//spins clockwise to kick algae into rev motors
    public static final double kAlgaeGroundIntake = -1;//top algae motor is main, bottom is follower
    public static final double kAlgaeL2Intake = 0.5;//Temp Values
    public static final double kAlgaeDump = 0.5;

    public static final int kAlgaeVoltage = 0;

    public static final double kDepositShooterWaitTime = 2;
    public static final double kShooterWaitTime = 1;

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
    public static final int kArmCANcoder = 22;

    public static final double kArmSpeedUp = 0.2;
    public static final double kArmSpeedDown = -0.2;

    //pid configurations
    public static final double kArmPIDControllerP = 120;
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
    public static final double kArmPositionGroundAlgae = 3;
    public static final double kArmPositionLowAlgae = 21.96;
    public static final double kArmPositionProcessor = 13.68;
    public static final double kArmPositionHighAlgae = 86.4;
    public static final double kArmPositionShoot = 86.4;
    public static final int kArmPositionClimb = 45;//Temp Value, probably won't use climb subsystem.

    //Current Limits
    public static final int kArmMotorCurrentStatorLimit = 40;//40 amps is good for stator current of kraken (x60s)
    public static final int kArmMotorCurrentSupplyLimit = 40;//Temp value, but supply limit should be atleast the value of the stator.
    public static final int kFollowerMotorCurrentStatorLimit = 40;
    public static final int kFollowerMotorCurrentSupplyLimit = 40;

  }

}
