package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    //gamepads
    public static final int DRIVER_GAMEPAD = 0; //Driving gamepad
    public static final int MANI_GAMEPAD = 1; //manipulating gamepad

    //square is one and rotates counterclockwise
    //button control during teleop DRIVEGAMEPAD
    public static final int RESET_GYRO_BTN = 1; //reset orientation back to 0
    public static final int LIMELIGHT_CONTROL_BTN = 2; //turn on limelight
    public static final int AIM_BTN = 3; //aim towards goal
    public static final int SET_POINT_BTN1 = 5; //set orientation to 0 degree (turn back to 0)
    public static final int SET_POINT_BTN2 = 6; // set orientation to 180 degree (turn to 180)
    public static final int AUTO_DRIVE_ON_BTN = 7; //turn auto drive on
    public static final int AUTO_DRIVE_OFF_BTN = 8; //turn auto drive off

    //button control during teleop MANIGAMEPAD
    public static final int INTAKE_BTN = 5;
    public static final int HOPPER_BTN = 6;
    public static final int SHOOTER_BTN = 7;
    public static final int HANGER_BTN = 8;

    //motors on power distribution panel
    public static final int LEFTMASTER = 3;//Master Left (Front)
    public static final int LEFTSLAVE = 2;//Slave Left (Back)
    public static final int RIGHTMASTER = 1;//Master Right (Front)
    public static final int RIGHTSLAVE = 0;//Slave Right (Back)

    public static final int SHOOTER_MOTOR = 12; 
    //public static final int INTAKE_MOTOR = 13; 
    //public static final int HOPPER_MOTOR = 14;
    public static final int HANGER_MOTOR = 15; 

    //motors on PWM Roborio
    public static final int INTAKE_MOTOR = 0;
    public static final int HOPPER_MOTOR = 1;

    //Limelight pipeline Number
    public static final int REFLECTIVE_TAPE_MODE = 0;
    public static final int BLUE_CARGO_MODE = 1;
    public static final int RED_CARGO_MODE = 2;

    //speed for driving
    public static double GSPEED = 1.0; 

    //Wheels 
    public static final double FALCON_MOTOR_CONSTANT = 2048;
	public static final double DRIVE_WHEEL_GEAR_RATIO = 10.71; //?
    public static final double SHOOTER_WHEEL_GEAR_RATIO = 3;
	public static final double DRIVE_WHEEL_DIAMETER = 0.1524; //meter
	public static final double DRIVE_WHEEL_CIRCUMFERENCE = DRIVE_WHEEL_DIAMETER * Math.PI;
    public static final double SHOOTER_WHEEL_RADIUS = 0.1143;
    public static final double SHOOTER_WHEEL_CIRCUMFERENCE = SHOOTER_WHEEL_RADIUS * 2 * Math.PI;

    //aim and shoot calculation constants
    public static final double g = 9.81;
    public static final double SHOOTER_DEGREE = 52; 
    public static final double LIMELIGHT_DEGREE = 0; 
    public static final double SHOOTER_HEIGHT = 0; //where we shoot it from
    public static final double UPPER_HUB_HEIGHT = 2.6416; //height of the goal
    public static final double LIMELIGHT_HEIGHT = 0; //limelight mounted height

}