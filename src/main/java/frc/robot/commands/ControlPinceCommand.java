// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pince;

public class ControlPinceCommand extends CommandBase {
  private XboxController xboxController;
  private Pince pince;
  /** Creates a new ControlPinceCommand. */
  public ControlPinceCommand(XboxController xboxController, Pince pince  ) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.xboxController = xboxController;
    this.pince = pince;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

if(xboxController.getAButtonReleased()){
      OuvertPinceCommand.pinceOuvert();
    }

    if(xboxController.getBButtonReleased()){
      FermerPinceCommand.pinceFerme();
    }

  
    pince.setMoteurSpeed(xboxController.getRightTriggerAxis());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
