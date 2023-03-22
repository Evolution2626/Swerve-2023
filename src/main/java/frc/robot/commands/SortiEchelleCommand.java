// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Echelle;

public class SortiEchelleCommand extends CommandBase {
  Echelle echelle;
  int capteur;
  double avanceur;
  /** Creates a new SortiEchelleCommand. */
  public SortiEchelleCommand(Echelle echelle, int capteur, double avanceur) {
    this.echelle = echelle;
    this.capteur = capteur;
    this.avanceur = avanceur;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(echelle.getCapteurActiver(0) == true && capteur == 1){
      while(echelle.getCapteurActiver(1) == false){
        echelle.Avance(avanceur);
      }
    }
    
    if(avanceur < 0) {

      echelle.Replie(-1);;

    } else if(avanceur > 0) {

      echelle.Replie(1);

    } else {

      echelle.Replie(0);

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
