// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Echelle;

public class ControlSortieEchelleCommand extends CommandBase {
  Echelle echelle;
  CommandXboxController controller;
  StageEchelleCommand stage;
  /** Creates a new ControlBougerBrasCommand. */
  public ControlSortieEchelleCommand(Echelle echelle, CommandXboxController controller, StageEchelleCommand stage) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.echelle = echelle;
    this.controller = controller;
    this.stage = stage;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    echelle.Avance(controller.getLeftY());
    echelle.Replie(controller.getRightTriggerAxis() - controller.getLeftTriggerAxis());
     

    
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
