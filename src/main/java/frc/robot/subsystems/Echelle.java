// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;



import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.util.Range;

public class Echelle extends SubsystemBase {

  private TalonSRX monteur1;

  private DigitalInput echelleLimit;

  private int desiredStage;

  /** Creates a new Echelle. */
  public Echelle() {
  
    
    echelleLimit = new DigitalInput(Constants.DIGITAL.ECHELLE_LIMIT);

    desiredStage = 0;

    monteur1 = new TalonSRX(Constants.CAN.MONTEUR1);
    
    monteur1.setInverted(false);
    monteur1.configContinuousCurrentLimit(15);
    monteur1.configPeakCurrentLimit(15);
    monteur1.setNeutralMode(NeutralMode.Brake);

    monteur1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
   
  }

  public void setStage(double variation){
    desiredStage += variation;
    desiredStage = (int)Range.coerce(0, 2, desiredStage);
  }

  public int getStage(){
    return desiredStage;

  }

  public boolean getEchelleLimit(){
    return echelleLimit.get();
  }

  public void monte(double valeur) {
  
    monteur1.set(TalonSRXControlMode.PercentOutput, Range.coerce(0,1, valeur));

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
    if (getEchelleLimit()) {
      resetEncoderValue();
    }
    SmartDashboard.putNumber("DesiredStage", desiredStage);
    SmartDashboard.putBoolean("Limite Echelle", getEchelleLimit());
    SmartDashboard.putNumber("Echelle position", getEncoderValue());
  }
}
