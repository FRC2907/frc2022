package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class AimCommand extends CommandBase {

    private final Joystick driverGP;
    private double turn;

    public AimCommand(Joystick js) {
        driverGP = js;
    }
    public void initialize() {
        turn = 0.0;
    }

    public void execute() {
/*
        if ( driverGP.getRawButton(Constants.AIM_BTN) ) { 
            //turn = Robot.m_robotContainer.getAimSubsystem().calcAimDegree();
            Robot.m_robotContainer.getAimSubsystem().aimMove();
         //motors are reversed, same power means one side drives forward and the other drive backwards (to turn)
            //Robot.m_robotContainer.getArcadeDrive().manualDrive(0, turn);
        } 

        SmartDashboard.putNumber("aim t", turn);
*/
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(final boolean interrupted) {}
    
    // Returns true when the command should end.
    @Override
    public boolean isFinished() { return false; }
        
    @Override 
    public boolean runsWhenDisabled() { return false; }
       
    
}
