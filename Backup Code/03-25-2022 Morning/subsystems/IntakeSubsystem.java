package frc.robot.subsystems;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class IntakeSubsystem extends SubsystemBase {

    private PWMSparkMax intake_motor;
    private PWMSparkMax hopper_motor;

    public IntakeSubsystem() {
        initializeMotor();
    }

    public void periodic() {

    }

    private void initializeMotor() {
        intake_motor = new PWMSparkMax(Constants.INTAKE_MOTOR);
        hopper_motor = new PWMSparkMax(Constants.HANGER_MOTOR);
    }

    public PWMSparkMax getMotor(String option) {
        if ( option.equals("INTAKEMOTOR") ) { return this.intake_motor; }
        else if ( option.equals("HOPPERMOTOR") ) { return this.hopper_motor; }  
        else { return null; }
    }
    
}
