package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class HangarCommand extends CommandBase {

    private final Joystick maniGP;
    private double motorPower;

    public HangarCommand(Joystick JS) {
        maniGP = JS;
    }

    public void initialize() {
        motorPower = 0.0;
    }

    public void execute() {
        if ( maniGP.getRawButton(Constants.HANGAR_BTN) ) { motorPower = 0.5; } 
        else { motorPower = 0.0; }

        Robot.m_robotContainer.getHangarSubsystem().getMotor().set(motorPower);
    }


    // Called once the command ends or is interrupted.
    @Override
    public void end(final boolean interrupted) {
        Robot.m_robotContainer.getHangarSubsystem().getMotor().stopMotor();
    }
    
    // Returns true when the command should end.
    @Override
    public boolean isFinished() { return false; }
        
    @Override 
    public boolean runsWhenDisabled() { return false; }
 
    
}
