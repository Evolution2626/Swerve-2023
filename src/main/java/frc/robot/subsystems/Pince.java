// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;



public class Pince extends SubsystemBase {
  /** Creates a new Pince. */
  private VictorSPX moteurGobbeur;
  private DoubleSolenoid piston;

  public Pince() {

    piston = new DoubleSolenoid(49, PneumaticsModuleType.REVPH, Constants.PCM.PISTON_PINCE_FORWARD, Constants.PCM.PISTON_PINCE_REVERSE);
    moteurGobbeur = new VictorSPX(Constants.CAN.GOBBEUR);
  }

  public void setPiston(DoubleSolenoid.Value value) {
    piston.set(value);

  }



  public void setMoteurSpeed(double valeur) {

    moteurGobbeur.set(VictorSPXControlMode.PercentOutput, valeur);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putString("PistonPince", piston.get().toString());
  }
}
