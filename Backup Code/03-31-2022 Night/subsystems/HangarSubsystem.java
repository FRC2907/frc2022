package frc.robot.subsystems;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class HangarSubsystem extends SubsystemBase {
    private WPI_TalonFX hangerMotor;

    public HangarSubsystem() {
        initializeMotor();
    }

    public void periodic() {
        SmartDashboard.putNumber("HM Current", hangerMotor.getStatorCurrent());
    }

    private void initializeMotor() {
        hangerMotor = new WPI_TalonFX(Constants.HANGAR_MOTOR);
        TalonFXConfiguration configs = new TalonFXConfiguration();
		configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;
	    hangerMotor.configFactoryDefault();
		hangerMotor.configAllSettings(configs);
		hangerMotor.set(ControlMode.PercentOutput, 0); //disabled motors first
    }

    public WPI_TalonFX getMotor() {
        return this.hangerMotor;
    }
    
}
