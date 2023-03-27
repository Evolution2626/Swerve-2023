// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.util.Range;

public class Echelle extends SubsystemBase {

  private TalonSRX monteur2;
  private TalonSRX monteur1;
  private CANSparkMax replieur;
  private TalonSRX avanceur;

  private DigitalInput stage1;
  private DigitalInput stage2;
  private DigitalInput stage3;

  private DigitalInput rentrer; 
  private DigitalInput sorti;

  /** Creates a new Echelle. */
  public Echelle() {
  
    stage1 = new DigitalInput(Constants.DIGITAL.STAGE1);
    stage2 = new DigitalInput(Constants.DIGITAL.STAGE2);
    stage3 = new DigitalInput(Constants.DIGITAL.STAGE3);

    rentrer = new DigitalInput(Constants.DIGITAL.RENTRER);
    sorti = new DigitalInput(Constants.DIGITAL.SORTI);

    monteur2 = new TalonSRX(Constants.CAN.MONTEUR2);
    monteur1 = new TalonSRX(Constants.CAN.MONTEUR1);
    replieur = new CANSparkMax(Constants.CAN.REPLIEUR, MotorType.kBrushless);
    avanceur = new TalonSRX(Constants.CAN.AVANCEUR);

    monteur1.setInverted(false);
    monteur2.setInverted(false);
    replieur.setInverted(true);
    avanceur.setInverted(false);

    //monteur1.configOpenloopRamp(0.5);
    //monteur2.configOpenloopRamp(0.5);

    monteur1.configContinuousCurrentLimit(15);
    monteur2.configContinuousCurrentLimit(15);

    monteur1.configPeakCurrentLimit(15);
    monteur2.configPeakCurrentLimit(15);


    monteur1.setNeutralMode(NeutralMode.Brake);
    monteur2.setNeutralMode(NeutralMode.Brake);


    monteur2.follow(monteur1);
    monteur1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

   
  }

  public void Avance(double valeur) {
    
    avanceur.set(TalonSRXControlMode.PercentOutput, valeur);

  }

  public void Replie(double valeur) {

    replieur.set(valeur);
  
  }

  public void Monte(double valeur) {
  
    monteur1.set(TalonSRXControlMode.PercentOutput, valeur);
    //monteur2.set(TalonSRXControlMode.PercentOutput, valeur);

  }

  public double getEncoderValue(){
    return -monteur1.getSelectedSensorPosition();
  }

  public boolean getSensorValue(int stage) {
    boolean stages[] = new boolean[2];

    stages[0] = stage1.get();
    stages[1] = stage2.get();
    stages[2] = stage3.get();

    return stages[stage];
  }

  public void resetEncoderValue(){
    monteur1.setSelectedSensorPosition(0);
  }

  public double getCapteurValue(){
    return avanceur.getSelectedSensorPosition();
  }

  public boolean getCapteurActiver(int capteur0){

    boolean echelleX[] = new boolean[2];

    echelleX[0] = rentrer.get();
    echelleX[1] = sorti.get();

    return echelleX[capteur0];

  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
