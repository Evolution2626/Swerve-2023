// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pince;
import frc.robot.useless.XYRCommand;
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
  
  public ModeAutonome3Command(Drivetrain drivetrain, Limelight limelight, Pince pince, Echelle echelle)
  {

    this.drivetrain = drivetrain;
    this.limelight = limelight;
    this.pince = pince;
    this.echelle = echelle;
      addCommands(new LimelightXYCommand(drivetrain, limelight, 6.6, 1));
      addCommands(new GyroRotationCommand(drivetrain));


   }
  }