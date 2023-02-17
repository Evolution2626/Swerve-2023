// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Echelle extends SubsystemBase {

  private TalonSRX monteur;
  private TalonSRX replieur;
  private TalonSRX avanceur;

  /** Creates a new Echelle. */
  public Echelle(TalonSRX monteur, TalonSRX replieur, TalonSRX avanceur) {
  
    this.monteur = monteur;
    this.replieur = replieur;
    this.avanceur = avanceur;

  }

  public void Avance(double valeur) {
    
    avanceur.set(TalonSRXControlMode.PercentOutput, valeur);

  }

  public void Replie(double valeur) {

    replieur.set(TalonSRXControlMode.PercentOutput, valeur);
  
  }

  public void Monte(double valeur) {
  
    monteur.set(TalonSRXControlMode.PercentOutput, valeur);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
