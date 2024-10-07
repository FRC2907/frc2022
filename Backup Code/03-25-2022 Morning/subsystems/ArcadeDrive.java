package frc.robot.subsystems;
import frc.robot.Constants;

import java.util.*;

import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.InvertType;

public class ArcadeDrive extends SubsystemBase {

	private WPI_TalonFX leftMaster;
	private WPI_TalonFX leftSlave;
    private WPI_TalonFX rightMaster;
    private WPI_TalonFX rightSlave;

    private List<WPI_TalonFX> motorList;

	private DifferentialDrive drive;

	public ArcadeDrive() {

		initializeMotor();

		drive = new DifferentialDrive(rightMaster, leftMaster);
	}

	public void manualDrive(double move, double turn) {
		drive.arcadeDrive(move * Constants.GSPEED, turn * Constants.GSPEED);
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


		rightSlave.follow(rightMaster);
		leftSlave.follow(leftMaster);
		leftMaster.setInverted(true);
		rightSlave.setInverted(InvertType.FollowMaster);
	    leftSlave.setInverted(InvertType.FollowMaster);


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

}