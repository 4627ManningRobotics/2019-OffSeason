/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4627.robot;

import fullyconnectednetwork.NN;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;

	public static final int DRIVER_CONTROLLER = 0;
	public static final int OPERATOR_CONTROLLER = 1;

	public static final int BUTTON_A = 1;
	public static final int BUTTON_B = 2;
	public static final int BUTTON_X = 3;
	public static final int BUTTON_Y = 4;
	public static final int LEFT_BUMPER = 5;
	public static final int RIGHT_BUMPER = 6;
	public static final int BACK_BUTTON = 7;
	public static final int START_BUTTON = 8;
	public static final int LEFT_STICK_BUTTON = 9;
	public static final int RIGHT_STICK_BUTTON = 10;
	
	public static final int LEFT_STICK_X = 0;
	public static final int LEFT_STICK_Y = 1;
	public static final int RIGHT_STICK_Y = 5;
	public static final int RIGHT_STICK_X = 4;
	
	public static final int LEFT_TRIGGER = 2;
	public static final int RIGHT_TRIGGER = 3;
	
	public static final int LEFT_MOTOR_1 = 5;
	public static final int LEFT_MOTOR_2 = 6;
	public static final int RIGHT_MOTOR_1 = 3;
	public static final int RIGHT_MOTOR_2 = 4;
	public static final int WRIST_MOTOR = 0;
	
	//rates and scalars
	public static final double RAMP_RATE = 0.4;
	public static final double ANGLE_SPEED_SCALAR = 0.01;
	public static final double DRIVER_MAX_SPEED = 1.0;
	public static final double WRIST_MAX_SPEED = 1.0;
	public static final double MANUAL_WRIST_SCALING = 5;
	public static double TURNING_RATE = 0.7;
	
	//positions
	public static final double WRIST_DOWN_STOW = 35; // degree positions
	public static final double WRIST_UP_STOW = 287;

	//offsets
	public static final double TURN_BASE_RATE = 0.1;
	
	//times
	public static final double TURN_WAIT = 250; // 1/4 seconds 
	
	//constrainors
	public static final double CONTROLLER_SAFEZONE = 0.1;
	public static final double GYRO_GAY = 0; //this is Sara's contribution
	public static final int DRIVE_CURRENT_LIMIT = 27; //amps
	public static final int DRIVE_CURRENT_TIMEOUT = 10; //ms
	public static final double WRIST_TOLERANCE_LEVEL = 3;
	
	//PID values
	public static final double TURN_P = 0.01;
	public static final double TURN_I = 0.0002;
	public static final double TURN_D = 0;
	public static final double WRIST_P = 0.0075;
	public static final double WRIST_I = 0.00007;
	public static final double WRIST_D = 0.000012;
	
	//Neural Networks
	public static final NN TurningNetwork = new NN("/home/lvuser/Saves/turnNetSave.txt", "/home/lvuser/Saves/turnSetSave.txt");
	
}
