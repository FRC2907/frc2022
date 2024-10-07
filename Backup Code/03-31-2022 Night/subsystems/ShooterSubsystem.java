package frc.robot.subsystems;
import frc.robot.Constants;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class ShooterSubsystem extends SubsystemBase {
    
    private WPI_TalonFX shooterMotor;
    private double ty;
    private double range;
    private double falconMotorVel;

    public ShooterSubsystem() {
        initializeMotor();
 
    }

    public void initialize() {
        ty = Robot.m_robotContainer.getLimelightWrapper().getDegree("ty");
        range = 0.0;
        falconMotorVel = 0.0;
    }

    public void periodic() {
        SmartDashboard.putNumber("SM Current", shooterMotor.getStatorCurrent());
    }

    private void initializeMotor() {
        shooterMotor = new WPI_TalonFX(Constants.SHOOTER_MOTOR);
        TalonFXConfiguration configs = new TalonFXConfiguration();
		configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;
	    shooterMotor.configFactoryDefault();
		shooterMotor.configAllSettings(configs);
		shooterMotor.set(ControlMode.PercentOutput, 0); //disabled motors first
    }

    public WPI_TalonFX getMotor() {
        return this.shooterMotor;
    }

    private double calcRange() {
        return (Constants.UPPER_HUB_HEIGHT - Constants.LIMELIGHT_HEIGHT) / Math.tan( ( ty + Constants.LIMELIGHT_DEGREE) * ( Math.PI / 180 ) ); 
    }

    public double calcMotorVel() {
        range = calcRange();
        double HEIGHTDIFF = Constants.UPPER_HUB_HEIGHT - Constants.SHOOTER_HEIGHT;
        //tangential velocity of the shooter wheel in m/s
        double tangentialVelocity = range * Math.sqrt( Constants.g / ( ( range * Math.tan(Constants.SHOOTER_DEGREE * (Math.PI / 180) )) - HEIGHTDIFF ) ) / ( Math.sqrt(2) * Math.cos(Constants.SHOOTER_DEGREE * (Math.PI / 180) ) );
        //Rotation per 100 ms of the cargo
        double RPMS = tangentialVelocity * Constants.SHOOTER_WHEEL_GEAR_RATIO * Constants.SHOOTER_WHEEL_RADIUS * 10;
        //falcon's raw sensor unit velocity
        falconMotorVel = RPMS * Constants.FALCON_MOTOR_CONSTANT;

        return falconMotorVel;
    }

}
