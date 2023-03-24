package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;

public class GyroRotationCommand extends PIDCommand{
    
  Drivetrain drivetrain;
  
  private static boolean stop = false;
  /** Creates a new LimelightRotationCommand. */
  public GyroRotationCommand(Drivetrain drivetrain, int goal) {
    super(
      // The controller that the command will use
      new PIDController(0.4, 0.25, 0.01),
      // This should return the measurement
      () -> drivetrain.getGyroAngle(),
      // This should return the setpoint (can also be a constant)
      () -> goal,
      // This uses the output
      output -> {
        stop = false;
        
        if(drivetrain.getGyroAngle() >= goal-1.5 && drivetrain.getGyroAngle() <= goal+1.5){
          stop = true;
          drivetrain.driveSwerve(0, 0, 0, false);
        }else{
          drivetrain.driveSwerve( 0, 0, output, false);
        }
       
      });
  // Use addRequirements() here to declare subsystem dependencies.
  // Configure additional PID options by calling `getController` here.
  //this.limelight = limelight;
  this.drivetrain = drivetrain;
  addRequirements(drivetrain);
   
}

// Returns true when the command should end.
@Override
public boolean isFinished() {
  //drivetrain.driveSwerve(0, 0, 0);
  //limelight.setLEDMode(1);
  

  return stop;
}
        // Use the output here
    
  // Use addRequirements() here to declare subsystem dependencies.
  // Configure additional PID options by calling `getController` here.
}



