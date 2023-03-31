// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Pince;

public class PinceTournerCommand extends InstantCommand {
  /** Creates a new PinceTournerCommand. */
  //private double speed;
  //private Pince pince;

  public PinceTournerCommand(double speed, Pince pince) {
    // Use addRequirements() here to declare subsystem dependencies.
    //this.pince = pince;
    //this.speed = speed;
    addRequirements(pince);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //pince.setMoteurSpeed(speed);
  }
}
