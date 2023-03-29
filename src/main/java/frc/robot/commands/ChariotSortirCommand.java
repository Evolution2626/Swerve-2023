// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Chariot;

public class ChariotSortirCommand extends CommandBase {
  /** Creates a new ChariotSortirCommand. */
  private Chariot chariot;
  private CommandXboxController controller;
  public ChariotSortirCommand(CommandXboxController controller, Chariot chariot) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.controller = controller;
    this.chariot = chariot;
    addRequirements(chariot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double avance = controller.getRightTriggerAxis();
    avance = controller.rightBumper().getAsBoolean() ? avance *= -1 : avance;
    double replie = controller.getLeftTriggerAxis();
    replie = controller.leftBumper().getAsBoolean() ? replie *= -1 : replie;

    chariot.avance(Math.pow(-avance, 3)/2);
    chariot.replie(Math.pow(replie, 3)/4.375);
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
