// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DrivetrainReturnToZeroCommand extends CommandBase {
  /** Creates a new DrivetrainReturnToZeroCommand. */
  private Drivetrain drivetrain;
  public DrivetrainReturnToZeroCommand(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!drivetrain.getDigitalInputs(0)) {
      drivetrain.setRotationMotorSpeed(0, 0.2);
    }else{
      drivetrain.setRotationMotorSpeed(0, 0);
      drivetrain.resetEncoders(0);
    }
    if (!drivetrain.getDigitalInputs(1)) {
      drivetrain.setRotationMotorSpeed(1, 0.2);
    }else{
      drivetrain.setRotationMotorSpeed(1, 0);
      drivetrain.resetEncoders(1);
    }
    if (!drivetrain.getDigitalInputs(2)) {
      drivetrain.setRotationMotorSpeed(2, 0.2);
    }else{
      drivetrain.setRotationMotorSpeed(2, 0);
      drivetrain.resetEncoders(2);
    }
    if (!drivetrain.getDigitalInputs(3)) {
      drivetrain.setRotationMotorSpeed(3, 0.2);
    }else{
      drivetrain.setRotationMotorSpeed(3, 0);
      drivetrain.resetEncoders(3);
    }
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
