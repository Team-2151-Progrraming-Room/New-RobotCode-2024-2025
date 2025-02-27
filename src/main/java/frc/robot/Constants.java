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

  public static final class ArmConstants{
    public static final int kArmMotor = 20;
    public static final int kArmMotor2 = 21;
    public static final int kLockMotor = 22;
    public static final int kArmCANcoder = 23;

    public static final double kArmSpeedUp = 0.5;
    public static final double kArmSpeedDown = -0.5;

    public static final int kArmMotorCurrentLimit = 40;
    public static final int kLockMotorCurrentLimit = 40;

    public static final int kArmPIDControllerP = 0;
    public static final int kArmPIDControllerI = 0;
    public static final int kArmPIDControllerD = 0;
    public static final int kArmPIDControllerFF = 0;
    public static final int kArmPIDControllerIZone = 0;
    public static final int kArmPIDControllerOutputRange = 5;
    public static final int kArmPIDControllerOutputMin = 5;
    public static final int kArmPIDControllerOutputMax = 5;

    public static final double kArmCANCoderConversionFactor = 45.5111111;

    //arm positions in degrees 
    public static final int kArmPositionGroundAlgae = 110;
    public static final int kArmPositionLowAlgae = 15;
    public static final int kArmPositionProcessor = 110;
    public static final int kArmPositionHighAlgae = 45;
    public static final int kPositionShoot = 110;
    public static final int kArmPositionClimb = 110;
    
  }
  
  public static final class AlgaeConstants {

    public static final int kAlgaeRevMotorID = 31;
    public static final int kAlgaeRev2MotorID = 32;
    public static final int kAlgaeKickMotorID = 33;

    public static final int kAlgaeRevMotorCurrentLimit = 40;
    public static final int kAlgaeRev2MotorCurrentLimit = 40;
    public static final int kAlgaeKickMotorCurrentLimit = 40;

    //PID Constants
    public static final int kAlgaePIDControllerS = 0;
    public static final int kAlgaePIDControllerV = 0;
    public static final int kAlgaePIDControllerP = 0;
    public static final int kAlgaePIDControllerI = 0;
    public static final int kAlgaePIDControllerD = 0;
    public static final int kAlgaeFeed = 0;//force to overcome gravity
    

    public static final int kAlgaeEncoderConversionFactor = 1000;//used to convert rpm to velocity

    public static final int kAlgaeRevVelocity = 10;
    public static final int kAlgaeRev2Velocity = 10;

    public static final int kAlgaeSpeedTolerance = 2;
    
    //actions that don't require pid control
    public static final double kAlgaeKickMotorON = .75;
    public static final double kAlgaeIntake = 0.5;
    
    public static final int kAlgaeVoltage = 0;

    public static final double kShortShooterWaitTime = 0.25;
    public static final double kLongShooterWaitTime = 0.5;
  }

  public static class CanbusName{
    public static final String armCANBus = "rio";
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}
