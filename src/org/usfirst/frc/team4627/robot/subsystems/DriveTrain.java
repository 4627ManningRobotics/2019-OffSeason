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

import com.ctre.phoenix.motorcontrol.ControlMode;
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
	}
	
	
	public void initDefaultCommand() {
		super.setDefaultCommand(new DriverControls());
	}
	
	public void setLeftMotor(double motorSetting) {
		motorSetting = Utilities.scale(motorSetting, RobotMap.DRIVER_MAX_SPEED);
    	this.leftMotor1.set(ControlMode.PercentOutput, motorSetting);
    	this.leftMotor2.set(ControlMode.PercentOutput, motorSetting);
    	
    	if(this.leftMotor1.getOutputCurrent() > RobotMap.DRIVE_CURRENT_LIMIT) {
    		this.leftMotor1.set(ControlMode.Current, RobotMap.DRIVE_CURRENT_LIMIT);
    	}
    	if(this.leftMotor2.getOutputCurrent() > RobotMap.DRIVE_CURRENT_LIMIT) {
    		this.leftMotor2.set(ControlMode.Current, RobotMap.DRIVE_CURRENT_LIMIT);
    	}
    }
    
    public void setRightMotor(double motorSetting) {
		motorSetting = Utilities.scale(motorSetting, RobotMap.DRIVER_MAX_SPEED);
    	this.rightMotor1.set(ControlMode.PercentOutput, -motorSetting); //reverse setting 
    	this.rightMotor2.set(ControlMode.PercentOutput, -motorSetting);

    	if(this.rightMotor1.getOutputCurrent() > RobotMap.DRIVE_CURRENT_LIMIT) {
			this.rightMotor1.set(ControlMode.Current, RobotMap.DRIVE_CURRENT_LIMIT);
		}
		if(this.rightMotor2.getOutputCurrent() > RobotMap.DRIVE_CURRENT_LIMIT) {
			this.rightMotor2.set(ControlMode.Current, RobotMap.DRIVE_CURRENT_LIMIT);
		}
    }
}
