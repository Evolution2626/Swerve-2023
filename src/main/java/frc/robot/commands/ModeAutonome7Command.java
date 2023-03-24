// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pince;
import frc.robot.useless.StageEchelleCommand;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ModeAutonome7Command extends SequentialCommandGroup {
  /** Creates a new ModeAutonome7Command. */
  Drivetrain drivetrain;
  Limelight limelight;
  Pince pince;
  Echelle echelle;

  public ModeAutonome7Command(Drivetrain drivetrain, Limelight limelight, Pince pince, Echelle echelle) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    this.drivetrain = drivetrain;
    this.limelight = limelight;
    this.pince = pince;
    this.echelle = echelle;


    addCommands(new LimelightXYCommand(drivetrain, limelight, 1.5, 1));
      addCommands(new GyroRotationCommand(drivetrain,0));
      addCommands(new StageEchelleCommand(echelle, 0.2,2));// deploie le bras
      addCommands(new PinceCommand(true));    // ouvre la pince  
      addCommands(new LimelightXYCommand(drivetrain, limelight, 6.6, 1));
      addCommands(new GyroRotationCommand(drivetrain, 180));
      addCommands(new StageEchelleCommand(echelle, 0.2,0));// replie le bras
      addCommands(new PinceCommand(false)); // ferme la pince 
  }
}
