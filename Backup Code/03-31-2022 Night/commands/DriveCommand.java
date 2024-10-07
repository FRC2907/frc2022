package frc.robot.commands;
import frc.robot.subsystems.*;
import frc.robot.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveCommand extends CommandBase {

    private final ArcadeDrive m_arcadeDrive;
    private final Joystick driverGP;

    private double move;
    private double turn;
    private double yawValue; //z orientation
    private boolean isAutoDrive;

    private final double DEADBAND = 0.15;
    private final double SET_MOVE = 0.4;
    private final double SPEED_DIFF = 0.1;
    private final double SET_RIGHT_TURN = 0.4;
    private final double SET_LEFT_TURN = -1 * SET_RIGHT_TURN;
    
    private final float AIM_Kp = -0.05f;
    private final float AIM_MIN_POWER = 0.05f;


    public DriveCommand(ArcadeDrive AD, Joystick JS) {
        driverGP = JS;
        m_arcadeDrive = AD;
        addRequirements( m_arcadeDrive );
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        move = 0.0;
        turn = 0.0;
        isAutoDrive = false;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        yawValue = Robot.m_robotContainer.getGyro().getYaw();
        move = -1 * driverGP.getY();
        turn = driverGP.getZ();

        if ( driverGP.getRawButton(Constants.AUTO_DRIVE_OFF_BTN) ) { isAutoDrive = false; } 
        else if ( driverGP.getRawButton(Constants.AUTO_DRIVE_ON_BTN) ) { isAutoDrive = true; }
 
        if (isAutoDrive) { autoDrive(); }

        if ( driverGP.getRawButton(Constants.RESET_GYRO_BTN) ) {
            Robot.m_robotContainer.getGyro().reset();
        }

        if (driverGP.getRawButton(Constants.AIM_BTN)) {
            autoAim();
        }

        setOrientation();

        //deadband
        if (Math.abs(move) < DEADBAND) move = 0.0;
        if (Math.abs(turn) < DEADBAND) turn = 0.0;

        Robot.m_robotContainer.getArcadeDrive().manualDrive(move, turn);

        if (driverGP.getRawButton(Constants.BREAK_BTN)) {
            for (WPI_TalonFX m : Robot.m_robotContainer.getArcadeDrive().getMotorList()) {
                m.setNeutralMode(NeutralMode.Brake);
            }
        }

        SmartDashboard.putNumber("Turn ", -1 * turn);
        SmartDashboard.putNumber("Move ", -1 * move);
        
    }

    private void autoDrive() {
        if (driverGP.getY() < -0.75) { move = SET_MOVE + SPEED_DIFF; }
        else if (driverGP.getY() > 0.75) { move = SET_MOVE - SPEED_DIFF; }
        else { move = SET_MOVE; }
        //can take out this?? so turn is controlled by joystick
        if (driverGP.getZ() > 0.75) { turn = SET_RIGHT_TURN; }
        else if (driverGP.getZ() < -0.75) { turn = SET_LEFT_TURN; }
        else { turn = 0.0; }
    }

    //navigate robot set direction error +- 4 degree should decrease 
    private void setOrientation() {

        if (driverGP.getRawButton(Constants.SET_POINT_BTN1)) {
            if (yawValue > 4) {
                turn = SET_LEFT_TURN;
            } else if (yawValue < -4) {
                turn = SET_RIGHT_TURN;
            } else {
                Robot.m_robotContainer.getArcadeDrive().breakMotor();
            }
        }
        else if (driverGP.getRawButton(Constants.SET_POINT_BTN2)) {
            if (yawValue < 176 && yawValue > 0) {
                turn = SET_RIGHT_TURN;
            } else if (yawValue > -176 && yawValue < 0) {
                turn = SET_LEFT_TURN;
            } else {
                Robot.m_robotContainer.getArcadeDrive().breakMotor();
            }
        }
    }



    private void autoAim() {

        double tx = Robot.m_robotContainer.getLimelightWrapper().getDegree("tx");
        double power = 0.0;
    
        float heading_error = (float) -tx;
        float steering_adjust = 0.0f;

        if (tx > 1.0) { steering_adjust = AIM_Kp * heading_error - AIM_MIN_POWER; }
        else if (tx < 1.0) { steering_adjust = AIM_Kp * heading_error + AIM_MIN_POWER; }
            
        power = steering_adjust;
    
        turn = power;
    }




    // Called once the command ends or is interrupted.
    @Override
    public void end(final boolean interrupted) { 
        for (WPI_TalonFX m : Robot.m_robotContainer.getArcadeDrive().getMotorList()) {
            m.stopMotor();
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() { return false; }

    @Override 
    public boolean runsWhenDisabled() { return false; }

}