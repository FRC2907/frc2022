package frc.robot.commands.Autonomous;
import frc.robot.*;
import frc.robot.subsystems.ArcadeDrive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoTimed extends CommandBase {
    
    private final ArcadeDrive m_arcadeDrive;  //drive subsystem

    private Timer time;
    private double RP_MS_instant; //per 100 millisec
    private double distance; 
    private double r_distance;
    private int counter;
    private double turn;
    private double move;
    private double shooterMotorSpeed;

    private final double SET_MOVE = -0.5;
    private final double SET_TURN = 0.0;
    private final double STOP_TIME = 3.0;
    private final double YAW_ADJUST = 5;


    public AutoTimed(ArcadeDrive AD) {
        m_arcadeDrive = AD;
        addRequirements(m_arcadeDrive);
    }

    @Override
    public void initialize() {
        RP_MS_instant = 0.0;
        distance = 0.0;
        r_distance = 0.0;
        counter = 0;
        turn = 0.0;
        move = 0.0;
        shooterMotorSpeed = 0.0;

        time = new Timer();
        time.reset();
        time.start();

    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        RP_MS_instant = -1 * Robot.m_robotContainer.getArcadeDrive().getMotor("RM").getSelectedSensorVelocity() / Constants.FALCON_MOTOR_CONSTANT / Constants.DRIVE_WHEEL_GEAR_RATIO;
        distance = -1 * Robot.m_robotContainer.getArcadeDrive().getMotor("RM").getSelectedSensorPosition() / Constants.FALCON_MOTOR_CONSTANT / Constants.DRIVE_WHEEL_GEAR_RATIO * Constants.DRIVE_WHEEL_CIRCUMFERENCE;
        counter++;
        if (counter % 10 == 0) {
            r_distance += RP_MS_instant;
        }

        keepStraight();
        
        if (time.get() <= 2) {
            shooterMotorSpeed = 0.8;
        }
        else if (time.get() <= STOP_TIME){
            shooterMotorSpeed = 0.0;
            move = SET_MOVE;
        } else {
            shooterMotorSpeed = 0.0;
            move = 0.0;
            time.stop();
        }

        Robot.m_robotContainer.getShooterSubsystem().getMotor().set(shooterMotorSpeed);
        Robot.m_robotContainer.getArcadeDrive().manualDrive(move, turn); 

        SmartDashboard.putNumber("Distance ", distance);
        SmartDashboard.putNumber("r_Distance ", r_distance);


    }

    private void keepStraight() {
        if (Robot.m_robotContainer.getGyro().getYaw() > YAW_ADJUST) {
            turn = -1 * SET_TURN;
        }
        else if (Robot.m_robotContainer.getGyro().getYaw() < -1 * YAW_ADJUST) {
            turn = SET_TURN;
        }
        else  { turn = 0.0; }
    }

    @Override
    public void end(final boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() { return false; }
}

