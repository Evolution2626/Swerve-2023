// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chariot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ChariotDeplierCommand extends CommandBase {
  private Chariot chariot;

  enum Action {
    SORTIR,
    RENTRER
  }

  Action action;
  boolean finishedAvanceur;
  boolean finishedReplieur;

  public ChariotDeplierCommand(Chariot chariot, Action action) {
    this.chariot = chariot;
    this.action = action;
    addRequirements(chariot);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (action == Action.SORTIR) {
      chariot.resetEncoder();
    }

    double avance = -0.5;
    avance = (action == Action.RENTRER) ? avance *= -1 : avance;
    chariot.avance(avance);

    double replie = 0.229;
    replie = (action == Action.RENTRER) ? replie *= -1 : replie;
    chariot.replie(replie);
      }
  @Override
  public void execute(){
    finishedAvanceur = (action == Action.RENTRER) ? chariot.getChariotLimit2() : chariot.getChariotLimit1();
    finishedReplieur = (action == Action.RENTRER) ? chariot.getReplieurEncodeurPosition() < 2 : chariot.getReplieurEncodeurPosition() > 13;
  }

  @Override
  public void end(boolean interrupted) {
    chariot.avance(0);
    chariot.replie(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    return (finishedReplieur && finishedAvanceur);
  }
}
