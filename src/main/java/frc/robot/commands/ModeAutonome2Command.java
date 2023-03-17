// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pince;
// Devant april tag 3, place deux objets(15 points)
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ModeAutonome2Command extends SequentialCommandGroup {
  /** Creates a new ModeAutonome2Command. */
  Drivetrain drivetrain;
  Limelight limelight;
  Pince pince;
  Echelle echelle;
  
  public ModeAutonome2Command() {

      addCommands(new LimelightRotationCommand(drivetrain, limelight, 0 /** a changer */));//trois premier serve a se placer
      addCommands(new LimelightYCommand(drivetrain, limelight,   1 /** a changer */));
      addCommands(new LimelightXCommand(drivetrain, limelight,   1.5 /** a changer */));
      addCommands(new BougerBrasCommand(echelle, 0,0,0));// deploie le bras
      addCommands(new PinceCommand(true));    // ouvre la pince  
      addCommands(new LimelightXCommand(drivetrain, limelight,   6.6 /** a changer */));//recule hors de la zone de depart
      addCommands(new LimelightRotationCommand(drivetrain, limelight,   0/** a changer */)); 
      addCommands(new BougerBrasCommand(echelle, 0, 0, 0));//descendre le bras
      addCommands(new PinceCommand(false)); // ferme la pince 
      addCommands(new BougerBrasCommand(echelle, 0, 0, 0)); //monte la pince
      addCommands(new LimelightRotationCommand(drivetrain, limelight,   0)); //tourne robot
      addCommands(new LimelightXCommand(drivetrain, limelight,   1.5/** a changer */)); //va vers truc a scorer
      addCommands(new LimelightYCommand(drivetrain, limelight,   1/** a changer */)); //stabilise
      addCommands(new PinceCommand(true));// ouvre la pince
   }
  }
