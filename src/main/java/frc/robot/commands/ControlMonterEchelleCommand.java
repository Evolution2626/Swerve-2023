package frc.robot.commands;



import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Echelle;

public class ControlMonterEchelleCommand extends CommandBase {
  /** Creates a new StageEchelleCommand. */
  Echelle echelle;
  double monte;
  int stage;
  XboxController controller; 


  public ControlMonterEchelleCommand(Echelle echelle,double monte,int stage, XboxController controller) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.monte = monte;
    this.echelle = echelle;
    this.stage = stage;
    this.controller = controller;
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

    if(controller.povDown(null).getAsBoolean()){
      stage = stage-1;
    }
     
    if(controller.povUp(null).getAsBoolean()){
      stage = stage+1;
    }

    if(stage > 2){
      stage = 2;
    }

    if(stage < 0){
      stage = 0;
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
