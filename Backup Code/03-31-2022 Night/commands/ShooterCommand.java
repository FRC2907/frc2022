package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Joystick;

public class ShooterCommand extends CommandBase{
    
    private final Joystick maniGP;
    private double falconMotorVel;

    public ShooterCommand(Joystick JS) {
        maniGP = JS;
    }

    public void initialize() {
        falconMotorVel = 0.0;
    }

    public void execute() {
        
        if (maniGP.getRawButton(Constants.SHOOTER_BTN)) {
            falconMotorVel = 0.75; //tested the best for behind tarmac
        } else if (maniGP.getY() > 0.3) {
            falconMotorVel = maniGP.getY();
        } else {
            falconMotorVel = 0.0;
        }

        if (maniGP.getRawAxis(4) > 0.5) {
            falconMotorVel = 0.8;
        } else if (maniGP.getRawAxis(4) < -0.5) {
            falconMotorVel = 0.7;
        }

        SmartDashboard.putNumber("shooter motor", falconMotorVel);
        Robot.m_robotContainer.getShooterSubsystem().getMotor().set(falconMotorVel);

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(final boolean interrupted) {
        Robot.m_robotContainer.getShooterSubsystem().getMotor().stopMotor();
     }
    
    // Returns true when the command should end.
    @Override
    public boolean isFinished() { return false; }
    
    @Override 
    public boolean runsWhenDisabled() { return false; }
    
}
