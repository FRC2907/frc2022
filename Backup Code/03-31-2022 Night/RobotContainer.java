package frc.robot;
import frc.robot.commands.*;
import frc.robot.commands.Autonomous.*;
import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;

import com.kauailabs.navx.frc.AHRS;

public class RobotContainer {

    private Joystick m_driverGamePad;
    private Joystick m_maniGamePad;
    private JoystickButton limelightBtn;

    private AHRS gyro;

    private ArcadeDrive m_arcadeDrive;
    private LimelightWrapper m_limelightWrapper;
    private IntakeSubsystem m_intakeSubsystem;
    private AimSubsystem m_aimSubsystem;
    private ShooterSubsystem m_shooterSubsystem;
    private HangarSubsystem m_hangarSubsystem;

    private Command m_driveCommand;
    private Command m_intakeCommand;
    private Command m_shooterCommand;
    private Command m_limeLightCommand;
    private Command m_aimCommand;
    private Command m_hangarCommand;

    private Command m_autoTimed;
    private Command m_autoPositioned;

    public RobotContainer() {

        initializeComponents();
        initializeSubsystems();
        initializeCommands();

        m_arcadeDrive.setDefaultCommand(m_driveCommand);

        //configureButtonBindings();

    }	

    /*
    pressed to turn on limelight
    */
    /*
	private void configureButtonBindings() {
		limelightBtn.whenPressed(m_limeLightCommand);
	}
    */
    /*
    initialize components/commands/subsystems for the robot
    */
    private void initializeComponents() {
        m_driverGamePad = new Joystick(Constants.DRIVER_GAMEPAD);
        m_maniGamePad = new Joystick(Constants.MANI_GAMEPAD);
        //limelightBtn = new JoystickButton(m_driverGamePad, Constants.LIMELIGHT_CONTROL_BTN);

        try {
            gyro = new AHRS();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initializeSubsystems() {
        m_arcadeDrive = new ArcadeDrive();
        m_limelightWrapper = new LimelightWrapper();
        m_intakeSubsystem = new IntakeSubsystem();
        m_aimSubsystem = new AimSubsystem();
        m_shooterSubsystem = new ShooterSubsystem();
        m_hangarSubsystem = new HangarSubsystem();
    }

    private void initializeCommands() {
        m_driveCommand = new DriveCommand(m_arcadeDrive, m_driverGamePad);
        m_limeLightCommand = new LimelightCommand();

        //needs to change these for mani gamepad
        m_intakeCommand = new IntakeCommand(m_maniGamePad);
        m_aimCommand = new AimCommand(m_driverGamePad);
        m_shooterCommand = new ShooterCommand(m_maniGamePad);
        m_hangarCommand = new HangarCommand(m_maniGamePad);
        
        m_autoTimed = new AutoTimed(m_arcadeDrive);
        m_autoPositioned = new AutoPositioned(m_arcadeDrive);
    }

    /*
    Accessor methods, use for retrieving components/subsystems from other classes
    */
    public AHRS getGyro() { return gyro; }

    public JoystickButton getLimeLightBtn() {
        return limelightBtn;
    }

    public Joystick getGamepad(String option) { 
        if ( option.equals("DRIVER_GP") ) 
            return m_driverGamePad;
        else if ( option.equals("MANI_GP") )
            return m_maniGamePad;
        else 
            return null;
    }

    public ArcadeDrive getArcadeDrive() { return m_arcadeDrive; }
    public LimelightWrapper getLimelightWrapper() { return m_limelightWrapper; }
    public IntakeSubsystem getIntakeSubsystem() { return m_intakeSubsystem; }
    public AimSubsystem getAimSubsystem() { return m_aimSubsystem; }
    public ShooterSubsystem getShooterSubsystem() { return m_shooterSubsystem; }
    public HangarSubsystem getHangarSubsystem() { return m_hangarSubsystem; }

    public Command getCommand(String option) {
        if ( option.equals("DRIVECOMMAND") )
            return m_driveCommand;
        else if ( option.equals("AUTOTIMED") )
            return m_autoTimed;
        else if ( option.equals("AUTOPOSITIONED") )
            return m_autoPositioned;
        else if ( option.equals("INTAKECOMMAND") )
           return m_intakeCommand;
        else if ( option.equals("SHOOTERCOMMAND") )
            return m_shooterCommand;   
        else if ( option.equals("LIMELIGHTCOMMAND") )
            return m_limeLightCommand;
        else if ( option.equals("AIMCOMMAND") )
            return m_aimCommand;
        else if ( option.equals("HANGARCOMMAND") )
            return m_hangarCommand;
        else 
            return null;
    }

}
