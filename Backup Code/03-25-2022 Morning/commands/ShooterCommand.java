package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

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

        if ( maniGP.getRawButton(Constants.SHOOTER_BTN) ) {
            //not sure if it will work
            falconMotorVel = Robot.m_robotContainer.getShooterSubsystem().calcMotorVel();
        } else { 
            falconMotorVel = 0.0; 
        }

        Robot.m_robotContainer.getShooterSubsystem().getMotor().set(ControlMode.Velocity, falconMotorVel);
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
