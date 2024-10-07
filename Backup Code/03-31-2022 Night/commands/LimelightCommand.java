package frc.robot.commands;
import frc.robot.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class LimelightCommand extends CommandBase {

	private boolean isFinished;

	@Override
	public void initialize() {
		isFinished = false;
	}

	@Override
	public boolean isFinished() {
		if(!isFinished){
			Robot.m_robotContainer.getLimelightWrapper().setLED(isFinished);
			isFinished = true;
			return false;
		} else {
			Robot.m_robotContainer.getLimelightWrapper().setLED(isFinished);
			return true;
		}
	}

	@Override
	public void end(boolean interrupted) {}

}
