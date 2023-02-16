package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Pince;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class OuvertPinceCommand extends InstantCommand {
  private Pince pince;

  public OuvertPinceCommand(Pince pince) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.pince = pince;
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pince.pinceOuvert();
  }

  public static void pinceOuvert() {
  }
}