// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Chariot extends SubsystemBase {
  /** Creates a new Replieur. */
  private CANSparkMax replieur;
  private TalonSRX avanceur;
  private DigitalInput chariotLimit1;
  private DigitalInput chariotLimit2;


  public Chariot() {
    replieur = new CANSparkMax(Constants.CAN.REPLIEUR, MotorType.kBrushless);
    replieur.setInverted(false);

    chariotLimit1 = new DigitalInput(Constants.DIGITAL.CHARIOT_LIMIT1);
    chariotLimit2 = new DigitalInput(Constants.DIGITAL.CHARIOT_LIMIT2);

    avanceur = new TalonSRX(Constants.CAN.AVANCEUR);
    avanceur.setInverted(false);

    replieur.setIdleMode(IdleMode.kBrake);


  }

  public void avance(double valeur) {
    if ((getChariotLimit1() && valeur < 0) || (getChariotLimit2() && valeur > 0)) {
      avanceur.set(TalonSRXControlMode.PercentOutput, 0);
    } else {
      avanceur.set(TalonSRXControlMode.PercentOutput, valeur);
    }

  }

  public boolean getChariotLimit1(){
    return chariotLimit1.get();
  }

  public boolean getChariotLimit2(){
    return chariotLimit2.get();
  }
  public void replie(double valeur) {
    replieur.set(valeur);
  }

  public double getReplieurEncodeurPosition(){
    return replieur.getEncoder().getPosition();
  }

  public void resetEncoder(){
    replieur.getEncoder().setPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("ChariotEncoder", getReplieurEncodeurPosition());
    SmartDashboard.putBoolean("ChariotLimite1", getChariotLimit1());
    SmartDashboard.putBoolean("ChariotLimite2", getChariotLimit2());
  }
}
