package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Echelle;

public class EchelleMonter extends PIDCommand{
    // Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html

  /** Creates a new Limelight1mCommand. */
  Drivetrain drivetrain;
  public static boolean stop = false;
  public Echelle echelle;

  public EchelleMonter(Echelle echelle, double target) {
    
    super(
        // The controller that the command will use
        new PIDController(1.8, 0.2, 0.01),
        ()-> 0,
        // This should return the measurement
        // This should return the setpoint (can also be a constant)
        () -> target,
        // This uses the output
        output -> {
         // System.out.println(output);
          // Use the output here
        echelle.Monte(output);
          stop = false;
            if(echelle.getEncoderValue() >= target-2 && echelle.getEncoderValue() <= target+2){
              stop = true;
              echelle.Monte(0.1);
            }
          
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    this.echelle = echelle;
    addRequirements(echelle);
     
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //drivetrain.driveSwerve(0, 0, 0);
    //limelight.setLEDMode(1);
    return stop;
  }
}
