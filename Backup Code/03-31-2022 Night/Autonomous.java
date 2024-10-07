package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class Autonomous {

    private Command m_autoTimed;
    private Command m_autoPositioned;

    private boolean inUse;
    private SendableChooser<Command> chooser;

    public Autonomous () {
        chooser = new SendableChooser<Command>();
        m_autoTimed = Robot.m_robotContainer.getCommand("AUTOTIMED");
        m_autoPositioned = Robot.m_robotContainer.getCommand("AUTOPOSITIONED");
        inUse = false;
    }

    public void scheduleAuto() {
        m_autoTimed.schedule();
        m_autoPositioned.schedule();
    }

    public void putAutonomous() {
        chooser.setDefaultOption("Auto Positioned ", m_autoPositioned);
        chooser.addOption("Auto Timed ", m_autoTimed);
    
        SmartDashboard.putData("Auto Mode", chooser);
    }

    public Command choosePicked() {
        inUse = true;
        return chooser.getSelected();
    }

    public void setUse(boolean option) {
        inUse = option;
    }

    public boolean isUse() {
        return inUse;
    }

    public void cancelAuto() {
        if (!inUse) { chooser.getSelected().cancel(); }
    }
}

