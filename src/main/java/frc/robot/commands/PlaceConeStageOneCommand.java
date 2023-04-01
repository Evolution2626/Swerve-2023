// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ChariotDeplierCommand.Action;
import frc.robot.commands.SwitchPistonPinceCommand.Mode;
import frc.robot.subsystems.Chariot;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PlaceConeStageOneCommand extends SequentialCommandGroup {
  /** Creates a new PlaceConeStageOneCommand. */
  
  public PlaceConeStageOneCommand(Pince pince, Chariot chariot, Echelle echelle) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addRequirements(pince, echelle, chariot);
    addCommands(
      new EchelleUpdateStageCommand(echelle, 1),
      new EchelleGoToStageCommand(echelle, true),
      new ChariotDeplierCommand(chariot, Action.SORTIR),
      //new WaitAutonomousTimerCommand(0.5),
      new SwitchPistonPinceCommand(pince, Mode.OUVRE),
      new WaitAutonomousTimerCommand(0.5),
      new ChariotDeplierCommand(chariot, Action.RENTRER),
      new EchelleUpdateStageCommand(echelle, -1),
      new EchelleGoToStageCommand(echelle, true),
      new WaitAutonomousTimerCommand(0.01)
      );
  }
}
