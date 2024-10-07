package frc.robot.subsystems;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Servo;

public class IntakeSubsystem extends SubsystemBase {

    private PWMSparkMax intake_motor;
    private Servo intake_servo;

    public IntakeSubsystem() {
        initializeMotor();
        intake_servo = new Servo(Constants.INTAKE_SERVO);
    }

    public void periodic() {
        SmartDashboard.putNumber("Servo angle", intake_servo.getAngle());
    }

    private void initializeMotor() {
        intake_motor = new PWMSparkMax(Constants.INTAKE_MOTOR);
    }

    public PWMSparkMax getMotor() {
        return this.intake_motor; 
    }

    public Servo getServo() {
        return this.intake_servo;
    }
    
}
