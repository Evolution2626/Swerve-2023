package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * Add your docs here.
 */
public class WaitAutonomousTimerCommand extends WaitCommand{
    private double time;
    public WaitAutonomousTimerCommand(double time){
        super(0);
        this.time = time;
    }

    @Override
    public boolean isFinished(){
        return m_timer.hasElapsed(time);
    }
}