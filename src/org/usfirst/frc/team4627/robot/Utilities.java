/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4627.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class Utilities {
	
	public static double constrain(double value, double min, double max) {
		if(value < min) {
			return min;
		}else if(value > max) {
			return max;
		}
		return value;
	}
	
	public static double constrain(double value, double max) {
		if(value < 0) {
			return 0;
		}else if(value > max) {
			return max;
		}
		return value;
	}
	
	public static double withinValue(double value, double min, double max) {
		if(value < max && value > min) {
			return value;
		}else {
			return 0;
		}
	}
	
	public static double notWithinValue(double value, double min, double max) {
		if(value < max && value > min) {
			return 0;
		}else {
			return value;
		}
	}
	
	public static boolean within(double value, double min, double max) {
		return (value < max && value > min);
	}
	
	public static boolean notWithin(double value, double min, double max) {
		return (value > max || value < min);
	}
	
	public static double scale(double value, double scalar) {
		return value * scalar;
	}
	
}
