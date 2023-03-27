// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Echelle;

public class EchelleControlCommand extends CommandBase {
  /** Creates a new EchelleControlCommand. */
  private Echelle echelle;
  private CommandXboxController controller;

  public EchelleControlCommand(Echelle echelle, CommandXboxController controller) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.controller = controller;
    this.echelle = echelle;
    addRequirements(echelle);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    echelle.Monte((Math.pow(controller.getRightTriggerAxis(), 3)));
    echelle.Replie((Math.pow(controller.getRightX(), 3)));
    echelle.Avance((Math.pow(controller.getLeftX(), 3))/3);
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
