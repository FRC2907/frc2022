package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

  public static RobotContainer m_robotContainer = new RobotContainer();
  public static Autonomous m_autonomous = new Autonomous();
  private Timer autoTime;
  private Timer teleOpTime;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    autoTime = new Timer();
    teleOpTime = new Timer();

    /*
    should not need
    m_robotContainer.getLimelightWrapper().setLED(true);
    m_robotContainer.getCommand("DRIVECOMMAND").schedule();
    m_robotContainer.getCommand("INTAKECOMMAND").schedule();
    m_robotContainer.getCommand("AIMCOMMAND").schedule();
    m_robotContainer.getCommand("SHOOTERCOMMAND").schedule();
    m_autonomous.scheduleAuto();
    */

    m_autonomous.putAutonomous();
    SmartDashboard.putData( CommandScheduler.getInstance() );
    SmartDashboard.updateValues();
  }


  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    SmartDashboard.putNumber("tx ", m_robotContainer.getLimelightWrapper().getDegree("tx"));
    SmartDashboard.putNumber("ty ", m_robotContainer.getLimelightWrapper().getDegree("ty"));

    SmartDashboard.putNumber("Rotation ", m_robotContainer.getGyro().getYaw());
    SmartDashboard.putNumber("Time ", Timer.getFPGATimestamp());
    SmartDashboard.putBoolean("Auto is used? ", m_autonomous.isUse() );

    SmartDashboard.updateValues();

    // m_autonomous.putAutonomous();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autonomous.choosePicked().schedule();
    m_robotContainer.getLimelightWrapper().setLED(true);
    autoTime.reset();
    autoTime.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    SmartDashboard.putNumber("Auto Time", autoTime.get() );
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    m_autonomous.setUse(false);
    m_autonomous.cancelAuto();
    m_robotContainer.getGyro().reset();

    m_robotContainer.getLimelightWrapper().setLED(false);

    m_robotContainer.getCommand("DRIVECOMMAND").schedule();
    m_robotContainer.getCommand("INTAKECOMMAND").schedule();
    m_robotContainer.getCommand("AIMCOMMAND").schedule();
    m_robotContainer.getCommand("SHOOTERCOMMAND").schedule();
    m_robotContainer.getCommand("HANGERCOMMAND").schedule();

    teleOpTime.reset();
    teleOpTime.start();

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("TeleOp Time", teleOpTime.get() );
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    CommandScheduler.getInstance().cancelAll();
    m_autonomous.setUse(false);
    m_autonomous.cancelAuto();
    m_robotContainer.getLimelightWrapper().setLED(false);
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }
}
