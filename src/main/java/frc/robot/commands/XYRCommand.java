// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class XYRCommand extends SequentialCommandGroup {
  Drivetrain drivetrain;
  Limelight limelight;
  /** Creates a new XYRCommand. */
  public XYRCommand(Drivetrain drivetrain, Limelight limelight,double rangeX, double rangeY, double rangeR) {
    this.limelight = limelight;
    this.drivetrain = drivetrain;
    addRequirements(drivetrain, limelight);
    // Add your commands in the addCommands() call, e.g.
    addCommands(new GyroRotationCommand(drivetrain, rangeR));
    for(int i =0; i < 4; i++){
    addCommands(new LimelightYCommand(drivetrain, limelight, rangeY /** a changer */));
    addCommands(new GyroRotationCommand(drivetrain, rangeR));
    addCommands(new LimelightXCommand(drivetrain, limelight, rangeX /** a changer */ ));
    addCommands(new GyroRotationCommand(drivetrain, rangeR));
    addCommands(new LimelightYCommand(drivetrain, limelight, rangeY /** a changer */));
    addCommands(new GyroRotationCommand(drivetrain, rangeR));
   
   if(limelight.getIsTargetFound()&&limelight.getRobotPosition()[0] >= rangeY-0.2 && limelight.getRobotPosition()[0] <= rangeY+0.2 && limelight.getRobotPosition()[1] >= rangeX-0.2 && limelight.getRobotPosition()[1] <= rangeX+0.2 && drivetrain.getGyroAngle() >= rangeR-1 && drivetrain.getGyroAngle() <= rangeR+1)
   {
    break;
   } 
    }
  }
}
