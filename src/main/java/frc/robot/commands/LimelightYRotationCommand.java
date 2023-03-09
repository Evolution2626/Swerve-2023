// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class LimelightYRotationCommand extends CommandBase {

  Drivetrain drivetrain;
  Limelight limelight;
  PIDController pidRotation;
  PIDController pidY;


  /** Creates a new LimelightYRotationCommand. */
  public LimelightYRotationCommand(Drivetrain drivetrain, Limelight limelight) {
    // (
        // The controller that the command will use
       pidRotation = new PIDController(0.2, 0, 0);
       pidY = new PIDController(0.5, 0, 0);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    limelight.setLEDMode(3);

    pidRotation.reset();
    pidY.reset();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //pas sure si getRobotPosition marche, on veut que cela donne la distance relative a la cible et non au terrain

    double distanceY = limelight.getRobotPosition()[1];
    double distanceX = limelight.getRobotPosition()[0];
    double distanceR = limelight.getRobotPosition()[5];
    double magnitude = Math.sqrt(Math.pow(distanceX, 2)+Math.pow(distanceY, 2));
    double speed = pidY.calculate(0, magnitude);
    double rotation = pidRotation.calculate(0, distanceR);
    drivetrain.driveSwerve(0, speed, rotation, false);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    drivetrain.driveSwerve(0, 0, 0, false);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    limelight.setLEDMode(1);

    return false;
  }
}
