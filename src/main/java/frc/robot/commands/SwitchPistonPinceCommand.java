// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Pince;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SwitchPistonPinceCommand extends InstantCommand {
  private Pince pince;
  private DoubleSolenoid.Value value;
  public SwitchPistonPinceCommand(Pince pince, DoubleSolenoid.Value value) {
    this.pince = pince;
    this.value = value;
    addRequirements(pince);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pince.setPiston(value);
  }
}
