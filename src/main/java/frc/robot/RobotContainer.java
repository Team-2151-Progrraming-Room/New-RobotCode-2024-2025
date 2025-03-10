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
import frc.robot.Constants.*;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import java.util.function.BooleanSupplier;

//our subsystems
import frc.robot.subsystems.ArmSubsystemCTRE;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import frc.robot.subsystems.AlgaeSubsystemCTRE;

//out commands
import frc.robot.commands.AlgaeShooterCommands;
import frc.robot.commands.DriveCommands;
import frc.robot.generated.TunerConstants;

import frc.robot.subsystems.ArmSubsystemCTRE;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private final ArmSubsystemCTRE arm = new ArmSubsystemCTRE();
  private final AlgaeSubsystemCTRE algae = new AlgaeSubsystemCTRE();
   private final Drive drive;

  //boolean supplier
  BooleanSupplier m_dynamicAtShootSpeed = () -> algae.atShooterSpeed();

  //commands
  private final AlgaeShooterCommands algaeCommands = new AlgaeShooterCommands(algae, arm, m_dynamicAtShootSpeed, ArmConstants.kArmPositionProcessor, ArmConstants.kArmPositionGroundAlgae);
  private final Command m_algaeShootCommand = algaeCommands.getShootCommand();
  private final Command m_algaeDumpCommand = algaeCommands.getDumpCommand();
  private final Command m_algaeProcessorDepositCommand = algaeCommands.getDepositCommand();
  private final Command m_algaeIntakeCommand = algaeCommands.getAlgaeIntakeCommand();
  private final Command m_L2Command = algaeCommands.getReefIntakeCommand(ArmConstants.kArmPositionLowAlgae);
  private final Command m_L3Command = algaeCommands.getReefIntakeCommand(ArmConstants.kArmPositionHighAlgae);
  // Controller
  private final CommandXboxController controller = new CommandXboxController(0);
  private final Joystick buttonBoard = new Joystick(1);


  public final JoystickButton shootButton;
  public final JoystickButton depositButton;
  public final JoystickButton algaeIntakeButton;
  private final JoystickButton dumpButton;

  private final JoystickButton climbPositionDownButton;
  private final JoystickButton L2AlgaePositionButton;
  private final JoystickButton L3AlgaePositionButton;
  private final JoystickButton shootPositionButton;
  private final JoystickButton climbPositionUpButton;
  private final JoystickButton manualUpButton;
  private final JoystickButton manualDownButton;


  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    shootButton = new JoystickButton(buttonBoard, 4);
    depositButton = new JoystickButton(buttonBoard, 2);//add wait until arm position
    dumpButton = new JoystickButton(buttonBoard, 1);
    algaeIntakeButton = new JoystickButton(buttonBoard, 3);//add waint until arm position


    climbPositionDownButton = new JoystickButton(buttonBoard, 11);
    L2AlgaePositionButton = new JoystickButton(buttonBoard, 8);//add wait until arm position
    L3AlgaePositionButton = new JoystickButton(buttonBoard, 9);//add wait until arm position
    shootPositionButton = new JoystickButton(buttonBoard, 5);
    climbPositionUpButton = new JoystickButton(buttonBoard, 10);//combine with lock
    manualUpButton = new JoystickButton(buttonBoard, 7);
    manualDownButton = new JoystickButton(buttonBoard, 6);

    switch (Constants.currentMode) {
      case REAL:
        // Real robot, instantiate hardware IO implementations
        drive =
            new Drive(
                new GyroIOPigeon2(),
                new ModuleIOTalonFX(TunerConstants.FrontLeft),
                new ModuleIOTalonFX(TunerConstants.FrontRight),
                new ModuleIOTalonFX(TunerConstants.BackLeft),
                new ModuleIOTalonFX(TunerConstants.BackRight));
        break;

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSim(TunerConstants.FrontLeft),
                new ModuleIOSim(TunerConstants.FrontRight),
                new ModuleIOSim(TunerConstants.BackLeft),
                new ModuleIOSim(TunerConstants.BackRight));
        break;

      default:
        // Replayed robot, disable IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {});
        break;
    }


    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    drive.setDefaultCommand(
        DriveCommands.joystickDrive(
            drive,
            () -> -controller.getLeftY(),
            () -> -controller.getLeftX(),
            () -> -controller.getRightX()));

    // Lock to 0° when A button is held
    controller
        .a()
        .whileTrue(
            DriveCommands.joystickDriveAtAngle(
                drive,
                () -> -controller.getLeftY(),
                () -> -controller.getLeftX(),
                () -> new Rotation2d()));

    // Switch to X pattern when X button is pressed
    controller.x().onTrue(Commands.runOnce(drive::stopWithX, drive));

    // Reset gyro to 0° when B button is pressed
    controller
        .b()
        .onTrue(
            Commands.runOnce(
                    () ->
                        drive.setPose(
                            new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
                    drive)
                .ignoringDisable(true));

    manualUpButton.whileTrue(arm.armManualUpCommand()).whileFalse(arm.armStopCommand());
    manualDownButton.whileTrue(arm.armManualDownCommand()).whileFalse(arm.armStopCommand());

    shootButton.onTrue(m_algaeShootCommand);
    algaeIntakeButton.onTrue(m_algaeIntakeCommand);
    depositButton.onTrue(m_algaeProcessorDepositCommand);
    dumpButton.whileTrue(m_algaeDumpCommand).whileFalse(algae.allMotorsOFFCommand());

    L2AlgaePositionButton.onTrue(m_L2Command);
    L3AlgaePositionButton.onTrue(m_L3Command);
    shootPositionButton.onTrue(arm.setArmPositionCommand(ArmConstants.kArmPositionShoot));
    climbPositionDownButton.onTrue(arm.setArmPositionCommand(ArmConstants.kArmPositionGroundAlgae));
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.get();
  }
}
