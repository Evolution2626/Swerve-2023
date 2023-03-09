// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.ControlBougerBrasCommand;
import frc.robot.commands.ControlPinceCommand;
import frc.robot.commands.LimelightRotationCommand;
import frc.robot.commands.LimelightXCommand;
import frc.robot.commands.LimelightYCommand;
import frc.robot.commands.ResetGryoCommand;
import frc.robot.commands.SwerveDriveCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pince;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  
  private static final Drivetrain drivetrain = new Drivetrain();
  private static final Limelight limelight = new Limelight();
  //private static final Echelle echelle = new Echelle();
  private static final CommandXboxController controller = new CommandXboxController(Constants.USB.DRIVER_CONTROLLER);
  private static final CommandXboxController controller2 = new CommandXboxController(Constants.USB.DRIVER_CONTROLLERCOPILOT);
  private static final Pince pince = new Pince();

  // Replace with CommandPS4Controller or CommandJoystick if needed

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    
    drivetrain.setDefaultCommand(new SwerveDriveCommand(drivetrain, controller));
    configureBindings();

    pince.setDefaultCommand(new ControlPinceCommand(controller2, pince));

    //echelle.setDefaultCommand(new ControlBougerBrasCommand(echelle, controller2));
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    controller.x().whileTrue(new LimelightXCommand(drivetrain, limelight, -2.82, true));
    controller.y().whileTrue(new LimelightYCommand(drivetrain, limelight, 5, false));
    controller.rightBumper().whileTrue(new LimelightRotationCommand(drivetrain, limelight, 0));
    controller.a().onTrue(new ResetGryoCommand(drivetrain));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
