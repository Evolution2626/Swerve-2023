// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Echelle;

public class BougerBrasCommand extends CommandBase {
  Echelle echelle;
  double avance;
  double replie;
  double monte;
  
  /** Creates a new DeployerBrasCommand. */
  public BougerBrasCommand(Echelle echelle, double avance, double replie, double monte) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.echelle = echelle;
    this.avance = avance;
    this.replie = replie;
    this.monte = monte;
  }
 
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    echelle.Avance(avance);
    echelle.Monte(monte);
    echelle.Replie(replie);

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
