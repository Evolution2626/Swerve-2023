// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.util.Range;

public class SwerveDriveCommand extends CommandBase {
  /** Creates a new DrivetrainDri. */
  private Drivetrain drivetrain;
  private XboxController controller;

  public SwerveDriveCommand(Drivetrain drivetrain, XboxController controller) {
    this.controller = controller;
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speedX = Math.pow(controller.getLeftX(), 3);
    double speedY = Math.pow(controller.getLeftY(), 3);
    double speedR = Math.pow(controller.getRightX(), 3);

    speedX = Range.threshold(0.1, speedX);
    speedY = Range.threshold(0.1, speedY);
    speedR = Range.threshold(0.1, speedR);

    drivetrain.driveSwerve(speedX, speedY, speedR);
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