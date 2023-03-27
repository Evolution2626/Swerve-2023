// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.sql.Driver;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
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
  private static boolean stop = false;

  public LimelightXCommand(Drivetrain drivetrain, Limelight limelight, double target) {
    
    super(
        // The controller that the command will use
        new PIDController(1.8, 0.2, 0.01),
        // This should return the measurement
        () -> limelight.getRobotPosition()[1],
        // This should return the setpoint (can also be a constant)
        () -> target,
        // This uses the output
        output -> {
         // System.out.println(output);
          // Use the output here

          // red: 1,2,3,4
         // blue: 5,6,7,8

          limelight.setLEDMode(3);
          stop = false;
          if(limelight.getIsTargetFound()){
            if(limelight.getRobotPosition()[1] >= target-0.2 && limelight.getRobotPosition()[1] <= target+0.2){
              stop = true;
              drivetrain.driveSwerve(0, 0, 0, false);
            }else{
              for(int i = 0; i <= Math.round(limelight.getTagID().length); i++ ){
                if(DriverStation.getAlliance() == Alliance.Red){
                  drivetrain.driveSwerve(-output,0, 0, false);
                }
                else if(DriverStation.getAlliance() == Alliance.Blue){
                  drivetrain.driveSwerve(output,0, 0, false);
                } 
              }
            }
          }else{
            stop = true;
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
