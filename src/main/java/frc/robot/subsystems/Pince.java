// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;



public class Pince extends SubsystemBase {
  /** Creates a new Pince. */
  private CANSparkMax moteurGobbeur;
  private DoubleSolenoid piston;



  public Pince() {

    piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.PCM.PISTON_PINCE_FORWARD, Constants.PCM.PISTON_PINCE_REVERSE);
    moteurGobbeur = new CANSparkMax(Constants.CAN.GOBBEUR, MotorType.kBrushless);
}

public void pinceOuvert() {

  piston.set(DoubleSolenoid.Value.kReverse);

}

public void pinceFerme() {

  piston.set(DoubleSolenoid.Value.kForward);

}

public void setMoteurSpeed(double speed) {

  moteurGobbeur.set(speed);

}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
