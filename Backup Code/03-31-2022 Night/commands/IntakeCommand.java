package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Joystick;

public class IntakeCommand extends CommandBase {
    
    private final Joystick maniGP;

    private double intakeMotorPower;

    public IntakeCommand(Joystick  JS) {
        maniGP = JS;
    }

    public void initialize() {
        intakeMotorPower = 0.0;
    }

    public void execute() {

        if (maniGP.getRawButton(Constants.INTAKE_ON_BTN)) {
            Robot.m_robotContainer.getIntakeSubsystem().getServo().set(1);
        } else if (maniGP.getRawButton(Constants.INTAKE_OFF_BTN) ){
            Robot.m_robotContainer.getIntakeSubsystem().getServo().set(0);
        }

        if ( maniGP.getRawButton(Constants.INTAKE_MOTOR_BTN) ) { intakeMotorPower = 0.5; }
        else { intakeMotorPower = 0.0; }


        Robot.m_robotContainer.getIntakeSubsystem().getMotor().set(intakeMotorPower);

    }
    
    // Called once the command ends or is interrupted.
    @Override
    public void end(final boolean interrupted) {
        Robot.m_robotContainer.getIntakeSubsystem().getMotor().stopMotor();
     }
            
    // Returns true when the command should end.
    @Override
    public boolean isFinished() { return false; }
            
    @Override 
    public boolean runsWhenDisabled() { return false; }
}
           
