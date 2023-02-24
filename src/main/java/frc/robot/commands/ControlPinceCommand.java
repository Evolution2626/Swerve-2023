// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Pince;

public class ControlPinceCommand extends CommandBase {
  private CommandXboxController controller;
  private Pince pince;
  /** Creates a new ControlPinceCommand. */
  public ControlPinceCommand(CommandXboxController controller, Pince pince ) {
    addRequirements(pince);
    // Use addRequirements() here to declare subsystem dependencies.
    this.controller = controller;
    this.pince = pince;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   
    if(controller.a().getAsBoolean()){
      pince.pinceOuvert();
    }

    if(controller.b().getAsBoolean()){
      pince.pinceFerme();
    }

  
    pince.setMoteurSpeed(controller.getRightTriggerAxis());

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
