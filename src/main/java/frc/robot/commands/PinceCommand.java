// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pince;

public class PinceCommand extends CommandBase {
  boolean ouvert;

  Pince pince;
  /** Creates a new PinceCommand. */
  public PinceCommand(Pince pince, boolean ouvert) {
    this.ouvert = ouvert;
    this.pince = pince;
    
    addRequirements(pince);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(ouvert){
      pince.pinceOuvert();
    }else{
      pince.pinceFerme();
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
