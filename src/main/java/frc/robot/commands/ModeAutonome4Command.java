// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pince;

//

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ModeAutonome4Command extends SequentialCommandGroup {

  Drivetrain drivetrain;
  Limelight limelight;
  Pince pince;
  Echelle echelle;

  /** Creates a new ModeAutonome1Command. */
  public ModeAutonome4Command() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new LimelightRotationCommand(drivetrain, limelight,   0 /** a changer */));//trois premier serve a se placer
    addCommands(new LimelightYCommand(drivetrain, limelight,   0 /** a changer */, true));
    addCommands(new LimelightXCommand(drivetrain, limelight,   0 /** a changer */, true));
    addCommands(new BougerBrasCommand(echelle, 0,0,0));// deploie le bras
    pince.pinceFerme();// ouvre la pince    
    addCommands(new BougerBrasCommand(echelle, 0,0,0));// replie le bras
    addCommands(new LimelightYCommand(drivetrain, limelight,   0 /** a changer */, true));//se place pour monter sur la plateform
    addCommands(new LimelightXCommand(drivetrain, limelight,   0 /** a changer */, true));// vas sur la plateform
 }
}
   
