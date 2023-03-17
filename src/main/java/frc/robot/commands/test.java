// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class test extends CommandBase {
  private Drivetrain drivetrain;
  private Limelight limelight;
  private double rangeX;
  private double rangeY;
  private double rangeR;
  private boolean isInverted;

  /** Creates a new test. */
  public test( Drivetrain drivetrain, Limelight limelight, double rangeY, double rangeX, double rangeR,boolean isInverted) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain, limelight);
    this.drivetrain = drivetrain;
    this.limelight = limelight;
    this.rangeR = rangeR;
    this.rangeY = rangeY;
    this.rangeX = rangeX;
    this.isInverted = isInverted;
  
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Commands.run(new LimelightYCommand(drivetrain, limelight, rangeY /** a changer */, isInverted));
    new LimelightYCommand(drivetrain, limelight, rangeY /** a changer */, isInverted);
    new LimelightXCommand(drivetrain, limelight,  rangeX /** a changer */, isInverted);
    new GyroRotationCommand(drivetrain, rangeR);
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
