// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ChariotSortirCommand;
import frc.robot.commands.EchelleGoToStageCommand;
import frc.robot.commands.EchelleUpdateStageCommand;
import frc.robot.commands.ModeAutonome1Command;
import frc.robot.commands.ModeAutonome3Command;
import frc.robot.commands.ModeAutonome4Command;
import frc.robot.commands.ModeAutonome5Command;
import frc.robot.commands.ModeAutonome6Command;
import frc.robot.commands.PinceTournerCommand;
import frc.robot.commands.ResetGryoCommand;
import frc.robot.commands.SwerveDriveCommand;
import frc.robot.commands.SwitchPistonPinceCommand;
import frc.robot.subsystems.Chariot;
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
  private static final Echelle echelle = new Echelle();
  private static final Chariot chariot = new Chariot();
  private static final CommandXboxController controller = new CommandXboxController(Constants.USB.DRIVER_CONTROLLER);
  private static final CommandXboxController controller2 = new CommandXboxController(Constants.USB.DRIVER_CONTROLLERCOPILOT);
  private static final Pince pince = new Pince();

  private SendableChooser<Command> autoChooser = new SendableChooser<>();
  
  // Replace with CommandPS4Controller or CommandJoystick if needed

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings

    drivetrain.setDefaultCommand(new SwerveDriveCommand(drivetrain, controller));
    configureBindings();

    chariot.setDefaultCommand(new ChariotSortirCommand(controller2, chariot));
    echelle.setDefaultCommand(new EchelleGoToStageCommand(echelle, false));

    autoChooser.addOption("PlaceBlocSortPlatforme", new ModeAutonome1Command(drivetrain, limelight, pince, echelle));
    autoChooser.addOption("Sort", new ModeAutonome3Command(drivetrain, limelight, pince, echelle));
    autoChooser.addOption("PlaceBlocPlateforme", new ModeAutonome4Command(drivetrain, limelight, pince, echelle));
    autoChooser.addOption("Plateforme", new ModeAutonome5Command(drivetrain, limelight, pince, echelle));
    autoChooser.addOption("PlaceBlocSort", new ModeAutonome6Command(drivetrain, limelight, pince, echelle));

    SmartDashboard.putData("Auto Chooser", autoChooser);
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

    controller.a().onTrue(new ResetGryoCommand(drivetrain));

    controller2.povUp().onTrue(new EchelleUpdateStageCommand(echelle, 1));
    controller2.povDown().onTrue(new EchelleUpdateStageCommand(echelle, -1));

    controller2.a().onTrue(new SwitchPistonPinceCommand(pince));
    controller2.b().onTrue(new PinceTournerCommand(-1, pince));
    controller2.x().onTrue(new PinceTournerCommand(1, pince));
    controller2.y().onTrue(new PinceTournerCommand(0, pince));



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
    return autoChooser.getSelected();
  }
}
