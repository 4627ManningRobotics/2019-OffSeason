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
	
	private double angle, max, startTime;
	private boolean isfinished;
	
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
    	while(Sensors.gyro.getAngle() >= 0.02 || Sensors.gyro.getAngle() <= -0.02) {} // give the gyro a second to zero
		this.isfinished = false;
		this.startTime = System.currentTimeMillis();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double angle_distance = this.angle - Sensors.gyro.getAngle();
		
		//scale the angle-distance, offset the value, and constrain the value 
		double leftSpeed = Utilities.constrain(Utilities.AddSub(Utilities.scale(
				angle_distance, RobotMap.ANGLE_SPEED_SCALAR), RobotMap.TURN_BASE_RATE), -this.max, this.max); 
		double rightSpeed = -leftSpeed;
		
		Robot.driveTrain.setRightMotor(rightSpeed);
		Robot.driveTrain.setLeftMotor(leftSpeed);
		
		if(Utilities.within(Sensors.gyro.getAngle(), this.angle - RobotMap.GYRO_GAY, this.angle + RobotMap.GYRO_GAY)) {
			if(System.currentTimeMillis() >= this.startTime + RobotMap.TURN_WAIT) {
				this.isfinished = true;
			}else {
				this.startTime = System.currentTimeMillis();
			}
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isfinished;
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
