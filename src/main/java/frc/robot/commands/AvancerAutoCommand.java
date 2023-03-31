// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.subsystems.Drivetrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AvancerAutoCommand extends ParallelRaceGroup {
  /** Creates a new AvancerAutoCommand. */
  private Drivetrain drivetrain;
  public AvancerAutoCommand(Drivetrain drivetrain, double time, double x, double y, double r) {
    this.drivetrain = drivetrain;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new WaitAutonomousTimerCommand(time),
      new avancerCommand(x, y, r));
  }

  private class avancerCommand extends CommandBase{
    double x;
    double y;
    double r;
    public avancerCommand(double x, double y, double r){
      this.x = x;
      this.y = y;
      this.r = r;
      
      addRequirements(drivetrain);
    }
    @Override
    public void initialize() {
      drivetrain.resetGyroAngle();
    
    }
    @Override
    public void execute() {
      drivetrain.driveSwerve(x, y, r, true);
    
    }
  }
}
