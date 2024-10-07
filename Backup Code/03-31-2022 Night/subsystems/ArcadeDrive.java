package frc.robot.subsystems;
import frc.robot.Constants;
import frc.robot.*;

import java.util.*;

import edu.wpi.first.wpilibj.motorcontrol.*;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class ArcadeDrive extends SubsystemBase {

	private WPI_TalonFX leftMaster;
	private WPI_TalonFX leftSlave;
    private WPI_TalonFX rightMaster;
    private WPI_TalonFX rightSlave;

    private List<WPI_TalonFX> motorList;

	private DifferentialDrive drive;
	private MotorControllerGroup rightM;
	private MotorControllerGroup leftM;
	private final SlewRateLimiter filter;

	private static final double ACCELLIMIT = 1.5;
	private final float AIM_Kp = -0.05f;
    private final float AIM_MIN_POWER = 0.05f;


	public ArcadeDrive() {

		initializeMotor();
		
		filter = new SlewRateLimiter(ACCELLIMIT);
		drive = new DifferentialDrive(leftM, rightM);
		drive.feed();


	}

	public void manualDrive(double move, double turn) {
		double forward = filter.calculate(move * Constants.GSPEED);
		drive.arcadeDrive(forward, turn);
	}

	@Override
	public void periodic() {
		
		//for checking current
		SmartDashboard.putNumber("RM current", rightMaster.getStatorCurrent());
		SmartDashboard.putNumber("LM current", leftMaster.getStatorCurrent());
		SmartDashboard.putNumber("RS current", rightSlave.getStatorCurrent());
		SmartDashboard.putNumber("LS current", leftSlave.getStatorCurrent());
	
		
	}
	
	private void initializeMotor() {

		leftMaster = new WPI_TalonFX(Constants.LEFTMASTER);
		leftSlave = new WPI_TalonFX(Constants.LEFTSLAVE);
		rightMaster = new WPI_TalonFX(Constants.RIGHTMASTER);
		rightSlave = new WPI_TalonFX(Constants.RIGHTSLAVE);

		rightM = new MotorControllerGroup(rightMaster, rightSlave);
		leftM = new MotorControllerGroup(leftMaster, leftSlave);
		rightM.setInverted(true);

		//create a list of motors for manipulating
		motorList = new ArrayList<WPI_TalonFX>();
		motorList.add(rightMaster);
		motorList.add(leftMaster);
		motorList.add(rightSlave);
		motorList.add(leftSlave);
		configureMotor(motorList);
	}

	private void configureMotor(List<WPI_TalonFX> motors) {
		TalonFXConfiguration configs = new TalonFXConfiguration();
		configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;

		for (WPI_TalonFX  m : motors) {
			m.configFactoryDefault();
			m.configAllSettings(configs);
			m.setSelectedSensorPosition(0);
			m.set(ControlMode.PercentOutput, 0); //disabled motors first
		}
	}

	public List<WPI_TalonFX> getMotorList() {
		return motorList;
	}

	//accessor methods
	public WPI_TalonFX getMotor(String option) {

		if (option.equals("RM")) { return this.rightMaster; }
		else if (option.equals("LM")) { return this.leftMaster; }
		else if (option.equals("RS")) { return this.rightSlave; }
		else if (option.equals("LS")) { return this.leftSlave; }

		return null;
	}

	public void breakMotor() {
		for (WPI_TalonFX m : motorList) {
			m.setNeutralMode(NeutralMode.Brake);
		}
	}

	public double autoAim() {

        double tx = Robot.m_robotContainer.getLimelightWrapper().getDegree("tx");
        double power = 0.0;
    
        float heading_error = (float) -tx;
        float steering_adjust = 0.0f;

        if (tx > 1.0) { steering_adjust = AIM_Kp * heading_error - AIM_MIN_POWER; }
        else if (tx < 1.0) { steering_adjust = AIM_Kp * heading_error + AIM_MIN_POWER; }
            
        power = steering_adjust;
    
        return power;
    }

}