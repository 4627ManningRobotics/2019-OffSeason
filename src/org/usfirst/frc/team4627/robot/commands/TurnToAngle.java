package org.usfirst.frc.team4627.robot.commands;

import org.usfirst.frc.team4627.robot.Robot;
import org.usfirst.frc.team4627.robot.RobotMap;
import org.usfirst.frc.team4627.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.command.Command;
public class TurnToAngle extends Command {
	
		public double angleWanted, leftSpeed, rightSpeed;
		public boolean isDone;
		
	    public TurnToAngle(double wantedAngle, double speed) {
	        // Use requires() here to declare subsystem dependencies
	        // eg. requires(chassis);
	    	this.leftSpeed = speed;
	    	this.rightSpeed = speed;
	    	this.angleWanted = wantedAngle;
	    	requires(Robot.driveTrain);
	    }
	    
	    public TurnToAngle(double wantedAngle, double left, double right, double i) {
	        // Use requires() here to declare subsystem dependencies
	        // eg. requires(chassis);
	    	this.leftSpeed = left;
	    	this.rightSpeed = right;
	    	this.angleWanted = wantedAngle;
	    	requires(Robot.driveTrain);
	    }

	    // Called just before this Command runs the first time
	    protected void initialize() {
	    	this.isDone  = false;
			Robot.driveTrain.setLeftMotor(0);
			Robot.driveTrain.setRightMotor(0);
	    	Sensors.gyro.zeroYaw();
	    	while(Sensors.gyro.getAngle() < -0.02 || Sensors.gyro.getAngle() > 0.02) { // while not zeroed
	    		
	    	}
	    }

	    // Called repeatedly when this Command is scheduled to run
	    protected void execute() {
	    	double angle = Sensors.gyro.getAngle();
	    		double maxAngle = this.angleWanted + RobotMap.GYRO_GAY;
	    		double minAngle = this.angleWanted - RobotMap.GYRO_GAY;
	    		if(angle < minAngle && this.angleWanted > 0) {
	    			Robot.driveTrain.setLeftMotor(this.leftSpeed);
	    			Robot.driveTrain.setRightMotor(-this.rightSpeed);
	    		} else if(angle > maxAngle && this.angleWanted < 0) {
	   				Robot.driveTrain.setLeftMotor(-this.leftSpeed);
	   				Robot.driveTrain.setRightMotor(this.rightSpeed);
	   			} else {
	   				Robot.driveTrain.setLeftMotor(0);
	   				Robot.driveTrain.setRightMotor(0);
	   				this.isDone = true; 
	   			}
	    }
	    

	    // Make this return true when this Command no longer needs to run execute()
	    protected boolean isFinished() {
	    	return this.isDone;
	    }

	    // Called once after isFinished returns true
	    protected void end() {
	    	Robot.driveTrain.setLeftMotor(0);
	    	Robot.driveTrain.setRightMotor(0);
	    }

	    // Called when another command which requires one or more of the same
	    //whoever finds this code...Good Job! :D
	    // subsystems is scheduled to run
	    protected void interrupted() {
	    	end();
	    }
	}
