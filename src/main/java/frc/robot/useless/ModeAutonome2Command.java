// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.useless;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.GyroRotationCommand;
import frc.robot.commands.LimelightXYCommand;
import frc.robot.commands.PinceCommand;
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
  
  public ModeAutonome2Command(Drivetrain drivetrain, Limelight limelight, Pince pince, Echelle echelle) {

    this.drivetrain = drivetrain;
    this.limelight = limelight;
    this.pince = pince;
    this.echelle = echelle;

      addCommands(new LimelightXYCommand(drivetrain, limelight, 1.5, 1));
      addCommands(new GyroRotationCommand(drivetrain));
      addCommands(new StageEchelleCommand(echelle, 0.2,2));// deploie le bras
      addCommands(new PinceCommand(pince, true));    // ouvre la pince  
      addCommands(new LimelightXYCommand(drivetrain, limelight, 6.6, 1));
      addCommands(new GyroRotationCommand(drivetrain));
      addCommands(new StageEchelleCommand(echelle, 0.2,0));// replie le bras
      addCommands(new PinceCommand(pince, false)); // ferme la pince 
      addCommands(new StageEchelleCommand(echelle, 0.2,2));// deploie le bras
      addCommands(new LimelightXYCommand(drivetrain, limelight, 1.5, 1));
      addCommands(new GyroRotationCommand(drivetrain));
      addCommands(new PinceCommand(pince, true));// ouvre la pince
   }
  }
