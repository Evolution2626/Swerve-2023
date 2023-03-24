// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pince;
//Devant le april tag 1 (21 points)


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ModeAutonome5Command extends SequentialCommandGroup {

  Drivetrain drivetrain;
  Limelight limelight;
  Pince pince;
  Echelle echelle;
//vas sur la plateform (12 pts). pts 12

  /** Creates a new ModeAutonome1Command. */
  public ModeAutonome5Command(Drivetrain drivetrain, Limelight limelight, Pince pince, Echelle echelle ) {
    this.drivetrain = drivetrain;
    this.limelight = limelight;
    this.pince = pince;
    this.echelle = echelle;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new LimelightXYCommand(drivetrain, limelight,5.27, -1.5));
    addCommands(new GyroRotationCommand(drivetrain));
    addCommands(new LimelightXYCommand(drivetrain, limelight, 3.5, -1.5));
    addCommands(new GyroRotationCommand(drivetrain));


  }
}

   
