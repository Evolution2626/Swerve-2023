// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Echelle;

public class EchelleUpdateStageCommand extends InstantCommand {
  /** Creates a new EchelleControlCommand. */
  private Echelle echelle;
  private int variation;

  public EchelleUpdateStageCommand(Echelle echelle, int variation) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.echelle = echelle;
    this.variation = variation;
    
    addRequirements(echelle);    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    echelle.setStage(variation);
  }

}
