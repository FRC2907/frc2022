package frc.robot.commands.Autonomous;
import frc.robot.*;
import frc.robot.subsystems.ArcadeDrive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoPositioned extends CommandBase {
    
    private final ArcadeDrive m_arcadeDrive;

    private Timer time;
    private double distance;
    private double r_distance;
    private double RP_MS_instant; //per 100 millisec
    private int counter;
    private double turn;
    private double move;
    private double shooterMotorSpeed;

    private final double SET_MOVE = 0.4;
    private final double SET_TURN = 0.0;
    private final double YAW_ADJUST = 5;
    private final double STOP_TIME = 10;
    private final double STOP_DISTANCE = 0.9; 


    public AutoPositioned(ArcadeDrive AD) {
        m_arcadeDrive = AD;
        addRequirements(m_arcadeDrive);
    }

    @Override
    public void initialize() {
        distance = 0.0;
        r_distance = 0.0;
        RP_MS_instant = 0.0;
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
        distance = -1 * Robot.m_robotContainer.getArcadeDrive().getMotor("RM").getSelectedSensorPosition() / Constants.FALCON_MOTOR_CONSTANT / Constants.DRIVE_WHEEL_GEAR_RATIO * Constants.DRIVE_WHEEL_CIRCUMFERENCE;
        RP_MS_instant = -1 * Robot.m_robotContainer.getArcadeDrive().getMotor("RM").getSelectedSensorVelocity() / Constants.FALCON_MOTOR_CONSTANT / Constants.DRIVE_WHEEL_GEAR_RATIO;
        counter++;
        if (counter % 10 == 0) {
            r_distance += RP_MS_instant;
        }
        
        keepStraight();

        if (distance < STOP_DISTANCE) { //move off of tarmac
            move = SET_MOVE;
        } else if (time.get() < 4) {
            Robot.m_robotContainer.getShooterSubsystem().getMotor().set(0.75);
        }
        else {
            Robot.m_robotContainer.getArcadeDrive().breakMotor();
            move = 0.0;
        }


        //if auto is over
        if (time.get() > STOP_TIME) {move = 0.0; turn = 0.0; shooterMotorSpeed = 0.0; }

        Robot.m_robotContainer.getShooterSubsystem().getMotor().set(shooterMotorSpeed);
        
        Robot.m_robotContainer.getArcadeDrive().manualDrive(move, turn);

        SmartDashboard.putNumber("Distance ", distance);


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
