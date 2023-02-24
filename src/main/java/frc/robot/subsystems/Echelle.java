// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Echelle extends SubsystemBase {

  private CANSparkMax monteur;
  private CANSparkMax replieur;
  private CANSparkMax avanceur;

  /** Creates a new Echelle. */
  public Echelle() {
  
    this.monteur = new CANSparkMax(Constants.CAN.MONTEUR, MotorType.kBrushless);
    this.replieur = new CANSparkMax(Constants.CAN.REPLIEUR, MotorType.kBrushless);
    this.avanceur = new CANSparkMax(Constants.CAN.AVANCEUR, MotorType.kBrushless);

    monteur.setInverted(false);
    replieur.setInverted(false);
    avanceur.setInverted(false);

    monteur.setIdleMode(IdleMode.kBrake);
    replieur.setIdleMode(IdleMode.kBrake);
    avanceur.setIdleMode(IdleMode.kBrake);
  }

  public void Avance(double valeur) {
    
    avanceur.set(valeur);

  }

  public void Replie(double valeur) {

    replieur.set(valeur);
  
  }

  public void Monte(double valeur) {
  
    monteur.set(valeur);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
