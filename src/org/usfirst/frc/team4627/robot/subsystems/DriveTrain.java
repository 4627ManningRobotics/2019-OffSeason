/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4627.robot.subsystems;

import org.usfirst.frc.team4627.robot.RobotMap;
import org.usfirst.frc.team4627.robot.Utilities;
import org.usfirst.frc.team4627.robot.commands.DriverControls;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	private final TalonSRX leftMotor1 = new TalonSRX(RobotMap.LEFT_MOTOR_1);
	private final TalonSRX leftMotor2 = new TalonSRX(RobotMap.LEFT_MOTOR_2);
	private final TalonSRX rightMotor1 = new TalonSRX(RobotMap.RIGHT_MOTOR_1);
	private final TalonSRX rightMotor2 = new TalonSRX(RobotMap.RIGHT_MOTOR_2);
	
	public DriveTrain() {
		// configure the time it takes for the motors to reach max speed
		this.leftMotor1.configOpenloopRamp(RobotMap.RAMP_RATE, 0); 
		this.leftMotor2.configOpenloopRamp(RobotMap.RAMP_RATE, 0); 
		this.rightMotor1.configOpenloopRamp(RobotMap.RAMP_RATE, 0); 
		this.rightMotor2.configOpenloopRamp(RobotMap.RAMP_RATE, 0); 
		
		//current limiting
		
		this.leftMotor1.configContinuousCurrentLimit(RobotMap.DRIVE_CURRENT_LIMIT, RobotMap.DRIVE_CURRENT_TIMEOUT);
		this.leftMotor2.configContinuousCurrentLimit(RobotMap.DRIVE_CURRENT_LIMIT, RobotMap.DRIVE_CURRENT_TIMEOUT);
		this.rightMotor1.configContinuousCurrentLimit(RobotMap.DRIVE_CURRENT_LIMIT, RobotMap.DRIVE_CURRENT_TIMEOUT);
		this.rightMotor2.configContinuousCurrentLimit(RobotMap.DRIVE_CURRENT_LIMIT, RobotMap.DRIVE_CURRENT_TIMEOUT);
		
		this.leftMotor1.configPeakCurrentLimit(0, RobotMap.DRIVE_CURRENT_TIMEOUT);
		this.leftMotor2.configPeakCurrentLimit(0, RobotMap.DRIVE_CURRENT_TIMEOUT);
		this.rightMotor1.configPeakCurrentLimit(0, RobotMap.DRIVE_CURRENT_TIMEOUT);
		this.rightMotor2.configPeakCurrentLimit(0, RobotMap.DRIVE_CURRENT_TIMEOUT);
		
		this.leftMotor1.enableCurrentLimit(true);
		this.leftMotor2.enableCurrentLimit(true);
		this.rightMotor1.enableCurrentLimit(true);
		this.rightMotor2.enableCurrentLimit(true);
	}
	
	
	public void initDefaultCommand() {
		super.setDefaultCommand(new DriverControls());
	}
	
	public void setLeftMotor(double motorSetting) {
		motorSetting = Utilities.scale(motorSetting, RobotMap.DRIVER_MAX_SPEED);
    	leftMotor1.set(leftMotor1.getControlMode(), motorSetting);
    	leftMotor2.set(leftMotor2.getControlMode(), motorSetting);
    }
    
    public void setRightMotor(double motorSetting) {
		motorSetting = Utilities.scale(motorSetting, RobotMap.DRIVER_MAX_SPEED);
    	rightMotor1.set(rightMotor1.getControlMode(), -motorSetting); //reverse setting 
    	rightMotor2.set(rightMotor2.getControlMode(), -motorSetting);
    }
}
