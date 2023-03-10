// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Echelle extends SubsystemBase {

  private TalonSRX monteur2;
  private TalonSRX monteur1;
  private VictorSPX replieur;
  private VictorSPX avanceur;

  /** Creates a new Echelle. */
  public Echelle() {
  
    monteur2= new TalonSRX(Constants.CAN.MONTEUR2);
    monteur1 = new TalonSRX(Constants.CAN.MONTEUR1);
    replieur = new VictorSPX(Constants.CAN.REPLIEUR);
    avanceur = new VictorSPX(Constants.CAN.AVANCEUR);

    monteur1.setInverted(false);
    monteur2.setInverted(false);
    replieur.setInverted(false);
    avanceur.setInverted(false);

    monteur2.follow(monteur1);
    monteur1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

   
  }

  public void Avance(double valeur) {
    
    avanceur.set(VictorSPXControlMode.PercentOutput, valeur);

  }

  public void Replie(double valeur) {

    replieur.set(VictorSPXControlMode.PercentOutput, valeur);
  
  }

  public void Monte(double valeur) {
  
    monteur1.set(TalonSRXControlMode.PercentOutput, valeur);
    //monteur2.set(TalonSRXControlMode.PercentOutput, valeur);

  }
  public double getEncoderValue(){
    return -monteur1.getSelectedSensorPosition();
}

public void resetEncoderValue(){
    monteur1.setSelectedSensorPosition(0);
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
