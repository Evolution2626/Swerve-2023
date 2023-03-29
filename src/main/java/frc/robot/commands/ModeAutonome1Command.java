// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pince;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ModeAutonome1Command extends SequentialCommandGroup {

  Drivetrain drivetrain;
  Limelight limelight;
  Pince pince;
  Echelle echelle;

/*
  place le bloc de debut(6 pts a 5pts), sort de la zone(3 pts) et vas sur la plateform (12 pts). pts 21/20
*/
  /**  a new ModeAutonome1Command. */
  public ModeAutonome1Command(Drivetrain drivetrain, Limelight limelight, Pince pince, Echelle echelle ) {
    this.drivetrain = drivetrain;
    this.limelight = limelight;
    this.pince = pince;
    this.echelle = echelle;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    if(DriverStation.getAlliance() == Alliance.Red){
      addCommands(new LimelightXYCommand(drivetrain, limelight, 5.57, -2.96));
      addCommands(new GyroRotationCommand(drivetrain));
      //addCommands(new StageEchelleCommand(echelle, 0.2,2));
      addCommands(new SwitchPistonPinceCommand(pince));
      //addCommands(new StageEchelleCommand(echelle, 0.2,0));
      addCommands(new LimelightXYCommand(drivetrain, limelight, 1.56, -2.96));
      addCommands(new GyroRotationCommand(drivetrain));
      addCommands(new LimelightXYCommand(drivetrain, limelight, 1.56, -4.51));
      addCommands(new GyroRotationCommand(drivetrain));
      addCommands(new LimelightXYCommand(drivetrain, limelight, 4, -4.51));
      addCommands(new GyroRotationCommand(drivetrain));
    }else if(DriverStation.getAlliance() == Alliance.Blue){
      addCommands(new LimelightXYCommand(drivetrain, limelight, -5.57, -2.96));
      addCommands(new GyroRotationCommand(drivetrain));
      //addCommands(new StageEchelleCommand(echelle, 0.2,2));
      addCommands(new SwitchPistonPinceCommand(pince));
     //addCommands(new StageEchelleCommand(echelle, 0.2,0));
      addCommands(new LimelightXYCommand(drivetrain, limelight, -1.56, -2.96));
      addCommands(new GyroRotationCommand(drivetrain));
      addCommands(new LimelightXYCommand(drivetrain, limelight, -1.56, -4.51));
      addCommands(new GyroRotationCommand(drivetrain));
      addCommands(new LimelightXYCommand(drivetrain, limelight, -4, -4.51));
      addCommands(new GyroRotationCommand(drivetrain));
    }
  }
}

   
