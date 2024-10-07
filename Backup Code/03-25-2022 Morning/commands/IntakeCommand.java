package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Joystick;

public class IntakeCommand extends CommandBase {
    
    private final Joystick maniGP;

    private double intakeMotorPower;
    private double hopperMotorPower;

    public IntakeCommand(Joystick  JS) {
        maniGP = JS;
    }

    public void initialize() {
        intakeMotorPower = 0.0;
        hopperMotorPower = 0.0;
    }

    public void execute() {

        if ( maniGP.getRawButton(Constants.INTAKE_BTN) ) { intakeMotorPower = 0.5; }
        else { intakeMotorPower = 0.0; }

        if ( maniGP.getRawButton(Constants.HOPPER_MOTOR) ) { hopperMotorPower = 0.5; } 
        else { hopperMotorPower = 0.0; }

        Robot.m_robotContainer.getIntakeSubsystem().getMotor("INTAKEMOTOR").set(intakeMotorPower);
        Robot.m_robotContainer.getIntakeSubsystem().getMotor("HOPPERMOTOR").set(hopperMotorPower);

    }
    
    // Called once the command ends or is interrupted.
    @Override
    public void end(final boolean interrupted) {
        Robot.m_robotContainer.getIntakeSubsystem().getMotor("INTAKEMOTOR").stopMotor();
        Robot.m_robotContainer.getIntakeSubsystem().getMotor("HOPPERMOTOR").stopMotor();
     }
            
    // Returns true when the command should end.
    @Override
    public boolean isFinished() { return false; }
            
    @Override 
    public boolean runsWhenDisabled() { return false; }
}
           
