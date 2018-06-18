package org.usfirst.frc.team4627.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4627.robot.Robot;
import org.usfirst.frc.team4627.robot.RobotMap;
import org.usfirst.frc.team4627.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 *
 */
public class PIDTurnToAngle extends Command {

	private PIDController PID;
	private boolean isfinished;
	private long startTime;
	
    private class PIDOut implements PIDOutput{
    	public void pidWrite(double output){
    		Robot.driveTrain.setRightMotor(-output);
    		Robot.driveTrain.setLeftMotor(output);
    	}
    }
    
    public PIDTurnToAngle(double angle) {
    	
        super.requires(Robot.driveTrain);
    	this.PID = new PIDController(RobotMap.TURN_P, RobotMap.TURN_I, RobotMap.TURN_D, Sensors.gyro, new PIDOut());
    	this.PID.setOutputRange(-1, 1);
    	this.PID.setAbsoluteTolerance(RobotMap.GYRO_GAY);
    	this.PID.setSetpoint(angle);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.PID.reset();
    	
    	Robot.driveTrain.setLeftMotor(0);
    	Robot.driveTrain.setRightMotor(0);

    	Sensors.gyro.zeroYaw();
    	while(Sensors.gyro.getAngle() >= 0.02 || Sensors.gyro.getAngle() <= -0.02) {} // give the gyro a second to zero
    	this.isfinished = false;
		this.startTime = System.currentTimeMillis();
    	this.PID.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(this.PID.onTarget()){
    		if(System.currentTimeMillis() >= this.startTime + RobotMap.TURN_WAIT) {
				this.isfinished = true;
			}else {
				this.startTime = System.currentTimeMillis();
			}
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isfinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	this.PID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
    
}