/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4627.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4627.robot.Robot;
import org.usfirst.frc.team4627.robot.RobotMap;
import org.usfirst.frc.team4627.robot.Utilities;
import org.usfirst.frc.team4627.robot.subsystems.Sensors;

/**
 * An example command.  You can replace me with your own command.
 */
public class TurnToAngleButBetter extends Command {
	
	private double angle, max;
	
	/*
	 * 
	 */
	public TurnToAngleButBetter(double target, double maxSpeed) { //positive angles are clockwise, negatives are counterclockwise
		// Use requires() here to declare subsystem dependencies
		super.requires(Robot.driveTrain);
		
		target = Utilities.constrain(target, -180, 180); // make sure the robot isn't turning more than 180.
		
		this.angle = target;
		this.max = maxSpeed;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.driveTrain.setLeftMotor(0);
		Robot.driveTrain.setRightMotor(0);

		Sensors.gyro.zeroYaw();
    	while(Sensors.gyro.isCalibrating()) {} // give the gyro a second to zero
		
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double leftSpeed = Utilities.constrain(Utilities.scale(this.angle, RobotMap.ANGLE_SPEED_SCALAR), this.max); 
		double rightSpeed = -leftSpeed;
		
		Robot.driveTrain.setRightMotor(rightSpeed);
		Robot.driveTrain.setLeftMotor(leftSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
