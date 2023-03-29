package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Echelle;
import frc.util.Range;

public class EchelleGoToStageCommand extends PIDCommand{
    // Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html

  public static double target;
  public static boolean finished = false;

  public EchelleGoToStageCommand(Echelle echelle, boolean auto) {
    
        super(
        // The controller that the command will use
        new PIDController(0.3, 0, 0),
        ()-> echelle.getEncoderValue(),
        // This should return the measurement
        // This should return the setpoint (can also be a constant)
        () -> target,
        // This uses the output
        output -> {
          if (echelle.getStage() > 0) {
            if (echelle.getStage() > 1) {
              target = 15000;
            }else {
              target = 6000;
            }
            if (Range.inRange(target - 500, target + 500, echelle.getEncoderValue())) {
              echelle.monte(0.1);
              if (auto) {
                finished = true;
              }
            }else {
              echelle.monte(output);
            }
            
          }else {
            target = 0;
            echelle.monte(0);
          }
        });


    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    addRequirements(echelle);
     
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
