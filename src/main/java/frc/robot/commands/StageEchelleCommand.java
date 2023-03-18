// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Echelle;

public class StageEchelleCommand extends CommandBase {
  /** Creates a new StageEchelleCommand. */
  Echelle echelle;
  double monte;
  int stage;
  private DigitalInput stage1;
  private DigitalInput stage2;
  private DigitalInput stage3;


  public StageEchelleCommand(Echelle echelle,double monte,int stage) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.monte = monte;
    this.echelle = echelle;
    this.stage = stage;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   
   if(echelle.getSensorValue(0) == true && stage == 1){
      while(echelle.getSensorValue(1) == false){
        echelle.Monte(monte);
      }
       }

    if(echelle.getSensorValue(1) == true && stage == 2){
      while(echelle.getSensorValue(2) == false){
          echelle.Monte(monte);
       }
        }
     if(echelle.getSensorValue(2) == true && stage == 1){
       while(echelle.getSensorValue(1) == false){
           echelle.Monte(-monte);
       }
        }

     if(echelle.getSensorValue(1) == true && stage == 0){
        while(echelle.getSensorValue(0) == false){
           echelle.Monte(-monte);
        }
          }

          if(echelle.getSensorValue(0) == true && stage == 2 ){
            while(echelle.getSensorValue(2) == false){
               echelle.Monte(monte);
            }
              }

              if(echelle.getSensorValue(2) == true && stage == 0 ){
                while(echelle.getSensorValue(0) == false){
                   echelle.Monte(-monte);
                }
                  }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
