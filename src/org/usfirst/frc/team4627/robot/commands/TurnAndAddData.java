package org.usfirst.frc.team4627.robot.commands;

import org.usfirst.frc.team4627.robot.Robot;
import org.usfirst.frc.team4627.robot.subsystems.Sensors;

import TrainSet.TrainSet;
import edu.wpi.first.wpilibj.command.Command;

import fullyconnectednetwork.NN;
import fullyconnectednetwork.Network;

/**
 *
 */
public class TurnAndAddData extends Command {
	
	public NN theNet;
	public boolean isFin;
	
	private Command c;
	private double speed;
	private double degree;
	private final long timeInterval = 1000; // one second to wait for the robot to stop turning
	private boolean hasJustFinished; // true only after first completion
	private boolean lastState;
	private long finishedTime;
	
    public TurnAndAddData() {
    	this.isFin = false;
    	// having any requirements might conflict with the code
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.hasJustFinished = false;
    	this.speed = 2;
    	this.degree = 1;
    	
    	this.theNet = new NN(Network.ZERO_TO_ONE, 180.0, new int[] {2,3,1});
		try {
			this.theNet.saveNet("/home/lvuser/Saves/turnNetSaveTest.txt"); // create the files
			System.out.println(this.theNet.set.size());
			this.theNet.saveSet("/home/lvuser/Saves/turnSetSaveTest.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("you done goofed: " + e);
		}//create a network save for later

		this.theNet.set.addData(new double[] {0.0, 0.0}, new double[] {0.0});
		this.c = new TurnToAngle(this.degree * 5, this.speed / 5);
		this.c.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println(this.speed + " " + this.degree);
    	
    	if(c.isCompleted()) {
    		if(this.hasJustFinished) { // runs once
    			if(this.degree == 36 && this.speed == 5) {
    				this.isFin = true;
    				return;
    			}
    		
    			if(this.degree == 36) {
    				this.speed++;
    				this.degree = 1;
    			}else {
    				this.degree++;
    			}
    			this.finishedTime = System.currentTimeMillis();
    			this.hasJustFinished = false;
    		}else {
    			if(this.lastState == false) {
    				this.hasJustFinished = true;
    			}
    		}
    	}
    	
    	
    	if(this.finishedTime + this.timeInterval <= System.currentTimeMillis() && this.c.isCompleted() && !this.hasJustFinished) {
			this.theNet.addData(new double[] {Sensors.gyro.getAngle(), this.speed / 5}, new double[] {this.degree * 5});
    		this.c = new TurnToAngle(this.degree * 5, this.speed / 5);
    		this.c.start();
    	}
    	
    	this.lastState = this.c.isCompleted();
    	
    	/*
    	Command c;
    	int timeInterval = 1000; // time interval in milliseconds, one seconds
    	double[][] in = new double[180][2]; // 36-5 degree intervals * 5 speeds
    	double[] out = new double[180];
    	
    	for(int speed = 1; speed <= 5; speed++) {
    		for(int degree = 1; degree <= 36; degree++) {
    			
    			int index = (speed - 1) * 36 + (degree - 1);
    			
    			c = (Command) new TurnToAngle(degree * 5, speed / 5, 3); // start turning
    			c.start();
    			System.out.println("Turning...");
    			while(c.isRunning()) {
    				
    			}
    			
    			long time = System.currentTimeMillis();
    			while(time + timeInterval > System.currentTimeMillis()) {
    				//wait
    			}
    			System.out.println("Done!");
    			in[index] = new double[] {Robot.sensors.getGyroAngle(), speed / 5};
    			out[index] = degree * 5;
    			NN.addTrainDataToFile(in[index], new double[] {out[index]}, "/home/lvuser/Saves/turnSetSaveTest.txt");
    		}
    	}
    	this.isFin = true;
    	*/
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isFin;
    }

    // Called once after isFinished returns true
    protected void end() {
    	this.theNet.saveSet("/home/lvuser/Saves/turnSetSaveTest.txt");
    	Robot.driveTrain.setLeftMotor(0);
    	Robot.driveTrain.setRightMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
