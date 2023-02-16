// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Echelle extends SubsystemBase {

  private TalonSRX monteur;
  private TalonSRX replieur;
  private TalonSRX deplieur;

  /** Creates a new Echelle. */
  public Echelle(TalonSRX monteur, TalonSRX replieur, TalonSRX deplieur) {
  
    this.monteur = monteur;
    this.replieur = replieur;
    this.deplieur = deplieur;

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
