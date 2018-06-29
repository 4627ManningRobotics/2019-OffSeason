package org.usfirst.frc.team4627.robot.commands;

import org.usfirst.frc.team4627.robot.Robot;
import org.usfirst.frc.team4627.robot.RobotMap;
import org.usfirst.frc.team4627.robot.Utilities;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WristControls extends Command {

    public WristControls() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.wrist.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	double rightJoyX = Robot.oi.getOperatorRawAxis(RobotMap.RIGHT_STICK_X);
    	double scaledDPadX = rightJoyX * RobotMap.MANUAL_WRIST_SCALING;
    	double newWristSetpoint= Robot.wrist.getSetpoint() + scaledDPadX;
    	newWristSetpoint = Utilities.constrain(newWristSetpoint, RobotMap.WRIST_DOWN_STOW, RobotMap.WRIST_UP_STOW);
    	Robot.wrist.setSetpoint( newWristSetpoint );
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.wrist.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
