
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.util.Range;

public class LimelightXYRCommand extends CommandBase {

  Drivetrain drivetrain;
  Limelight limelight;
  PIDController pidRotation;
  PIDController pid;
  double rangeX;
  double rangeY;

  public static boolean stop = false;


  /** Creates a new LimelightYRotationCommand. */
  public LimelightXYRCommand(Drivetrain drivetrain, Limelight limelight, double rangeX, double rangeY) {
    // (
      this.limelight = limelight;
      this.drivetrain = drivetrain;
        // The controller that the command will use
       //pidRotation = new PIDController(0.2, 0, 0);
       pid = new PIDController(0.5, 0, 0);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    limelight.setLEDMode(3);

    //pidRotation.reset();
    pid.reset();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    stop = false;
    //pas sure si getRobotPosition marche, on veut que cela donne la distance relative a la cible et non au terrain
    
    double positionY = limelight.getRobotPosition()[0];
    double positionX = limelight.getRobotPosition()[1];

    double distanceX = 0;
    double distanceY = 0;

    if(isInverted()){
      distanceX = (rangeX - positionX) * -1;
      distanceY = (rangeY - positionY) * -1;
    }else{
      distanceX = rangeX - positionX;
      distanceY = rangeY - positionY;
    }

    if(Math.abs(distanceX) > Math.abs(distanceY)){
      distanceX = distanceX/Math.abs(distanceX);
      distanceY = distanceY/Math.abs(distanceX);
    }else{
      distanceX = distanceX/Math.abs(distanceY);
      distanceY = distanceY/Math.abs(distanceY);
    }

    System.out.println("Distance x:" + distanceX);
    System.out.println("Distance y:" + distanceY);


    //double distanceR = limelight.getRobotPosition()[5];
    double magnitude = Math.sqrt(Math.pow(positionX-rangeX, 2)+Math.pow(positionY-rangeY, 2));
    double speed = pid.calculate(magnitude, 0);

    System.out.println("speed factor:" + speed);

    //double rotation = pidRotation.calculate(0, distanceR);
    double rotation = 0.0;
    if(limelight.getIsTargetFound()){
      if(limelight.getRobotPosition()[1] >= rangeX-0.05 && limelight.getRobotPosition()[1] <= rangeX+0.05 && limelight.getRobotPosition()[0] >= rangeY-0.05 && limelight.getRobotPosition()[0] <= rangeY+0.05){
        stop = true;
        drivetrain.driveSwerve(0, 0, 0, false);
      }else{
        drivetrain.driveSwerve(Range.coerce(1, speed) * distanceX, Range.coerce(1, speed) * distanceY, rotation, false);
      }
    }else{
      stop = true;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    drivetrain.driveSwerve(0, 0, 0, false);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    limelight.setLEDMode(1);

    return stop;
  }

  public boolean isInverted(){
    double degree = limelight.getRobotPosition()[5];

    if(degree < 90 && -90 > degree){
      return true;
    }else{
      return false;
    }
  }
}
