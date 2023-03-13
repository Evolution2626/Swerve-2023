// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pince;
//Devant april tag 1 ou 3(3 points) SECOURS SEULEMEMT
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ModeAutonome3Command extends SequentialCommandGroup {
  /** Creates a new ModeAutonome2Command. */
  Drivetrain drivetrain;
  Limelight limelight;
  Pince pince;
  Echelle echelle;
  
  public ModeAutonome3Command() {

      addCommands(new LimelightRotationCommand(drivetrain, limelight,   0 /** a changer */));//trois premier serve a sortir
      addCommands(new LimelightYCommand(drivetrain, limelight,   1 /** a changer */, true));
      addCommands(new LimelightXCommand(drivetrain, limelight,   6.6 /** a changer */, true));
   

   }
  }