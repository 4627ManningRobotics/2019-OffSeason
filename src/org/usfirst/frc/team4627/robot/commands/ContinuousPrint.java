package org.usfirst.frc.team4627.robot.commands;

import org.usfirst.frc.team4627.robot.Robot;
import org.usfirst.frc.team4627.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ContinuousPrint extends Command {

	private int i;
	
    public ContinuousPrint() {
    	super.requires(Robot.sensors);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.i = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(i % 25 == 0) {
    		System.out.println("Gyro: " + Sensors.gyro.getAngle());
    		System.out.println("Wrist: " + Robot.wrist.calculateAngle());
    		this.i = 0;
    	}
    	this.i++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
