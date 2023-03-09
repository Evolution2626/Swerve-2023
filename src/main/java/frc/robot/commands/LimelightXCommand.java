// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class LimelightXCommand extends PIDCommand {
  /** Creates a new Limelight1mCommand. */
  Drivetrain drivetrain;
  Limelight limelight;
  public static boolean stop = false;

  public LimelightXCommand(Drivetrain drivetrain, Limelight limelight, double range, boolean inverted) {
    
    super(
        // The controller that the command will use
        new PIDController(0.3, 0, 0),
        // This should return the measurement
        () -> limelight.getRobotPosition()[0],
        // This should return the setpoint (can also be a constant)
        () -> range,
        // This uses the output
        output -> {
          // Use the output here
          limelight.setLEDMode(3);
          stop = false;
          
          if(limelight.getRobotPosition()[1] >= range-0.05 && limelight.getRobotPosition()[1] <= range+0.05){
            stop = true;
            drivetrain.driveSwerve(0, 0, 0, false);
          }else{
            if(inverted){
              drivetrain.driveSwerve( -output,0, 0, false);
            }
            else{
              drivetrain.driveSwerve(output,0, 0, false);
            } 

          }
          
          
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    this.limelight = limelight;
    this.drivetrain = drivetrain;
    addRequirements(drivetrain, limelight);
     
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //drivetrain.driveSwerve(0, 0, 0);
    //limelight.setLEDMode(1);
    return stop;
  }
}
