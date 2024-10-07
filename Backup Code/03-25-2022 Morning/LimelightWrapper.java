package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightWrapper {
	private NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
	private NetworkTableEntry tx = limelight.getEntry("tx");//target x position in camera
	private NetworkTableEntry ty = limelight.getEntry("ty");//target y position in camera
	private NetworkTableEntry ta = limelight.getEntry("ta");//target area position in camera

	// if true is passed in it will turn on leds
	public void setLED(boolean on) {

		// 0 is on 1 is off
		if (on) {
            //set pipeline number
            limelight.getEntry("pipeline").setNumber(Constants.REFLECTIVE_TAPE_MODE);
			limelight.getEntry("ledMode").setNumber(0);
		} else {
			limelight.getEntry("ledMode").setNumber(1);
		}
	}

    public NetworkTable getLimeLight() {
        return this.limelight;
    }

    public double getDegree(String option) {
        if (option.equals("tx"))
            return this.tx.getDouble(0.0);
        else if (option.equals("ty"))
            return this.ty.getDouble(0.0);
        else if (option.equals("ta"))
            return this.ta.getDouble(0.0);
        else 
            return 0;
    }

    public void setPipeline(String option) {
        if (option.equals("RT")) {
            limelight.getEntry("pipeline").setNumber(Constants.REFLECTIVE_TAPE_MODE);
        } else if (option.equals("BC")) {
            limelight.getEntry("pipeline").setNumber(Constants.BLUE_CARGO_MODE);
        } else { //REDCARGO
            limelight.getEntry("pipeline").setNumber(Constants.RED_CARGO_MODE);
        }
    }
}

