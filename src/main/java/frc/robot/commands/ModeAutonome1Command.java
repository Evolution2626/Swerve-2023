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
public class ModeAutonome1Command extends SequentialCommandGroup {

  Drivetrain drivetrain;
  Limelight limelight;
  Pince pince;
  Echelle echelle;

  /** Creates a new ModeAutonome1Command. */
  public ModeAutonome1Command() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new LimelightRotationCommand(drivetrain, limelight, 0 /** a changer */));//trois premier serve a se placer
    addCommands(new LimelightYCommand(drivetrain, limelight, 4.4 /** a changer */, true));
    addCommands(new LimelightXCommand(drivetrain, limelight,  1.8 /** a changer */, true));
    addCommands(new BougerBrasCommand(echelle, 0,0,0));// deploie le bras
    addCommands(new PinceCommand(true));// ouvre la pince
    addCommands(new BougerBrasCommand(echelle, 0,0,0));// replie le bras
    addCommands(new LimelightXCommand(drivetrain, limelight,  6.1 /** a changer */, true));//recule hors de la zone de depart
    addCommands(new LimelightYCommand(drivetrain, limelight,  3.2 /** a changer */, true));//se place pour monter sur la plateform
    addCommands(new LimelightXCommand(drivetrain, limelight,  3.7 /** a changer */, true));// vas sur la plateform
 }
}

   
