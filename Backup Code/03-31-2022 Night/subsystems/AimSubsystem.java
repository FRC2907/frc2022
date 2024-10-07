package frc.robot.subsystems;
import frc.robot.Robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AimSubsystem extends SubsystemBase {

    private final double KP = -0.1;
    private final double MINIMUMPOWER = 0.1;

    private double steeringAdjust;
    private double headingError;
    private double motorPower;
    private double tx;

    public void initialize() {
        steeringAdjust = 0.0;
        headingError = 0.0;
        motorPower = 0.0;
        tx =  Robot.m_robotContainer.getLimelightWrapper().getDegree("tx");
    }

	public double calcAimDegree() {
		if (tx > 3) { motorPower = 0.09; }
		else if (tx < -3) { motorPower = -0.09; }
		else { motorPower = 0.0; }
		return motorPower;
	}

    public double aim() {
		motorPower = 0.0;
        headingError = -1 * tx;

        if (tx > 1.0) { steeringAdjust = KP * headingError - MINIMUMPOWER; }
        else if (tx < 1.0) { steeringAdjust = KP * headingError + MINIMUMPOWER; }

        motorPower += steeringAdjust;

        return motorPower;
    }
    public void move() {
        Robot.m_robotContainer.getArcadeDrive().manualDrive(0, aim());
       // SmartDashboard.putNumber("ll aim", aim());
    }

    public void aimMove() {
        Robot.m_robotContainer.getArcadeDrive().manualDrive(0, calcAimDegree());
       // SmartDashboard.putNumber("am aim", calcAimDegree());

    }

}
