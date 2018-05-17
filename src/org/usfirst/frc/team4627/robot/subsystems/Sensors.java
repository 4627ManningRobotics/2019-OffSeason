package org.usfirst.frc.team4627.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.SerialPort;

/**
 *
 */
public class Sensors extends Subsystem {

	public static final AHRS gyro = new AHRS(SerialPort.Port.kUSB);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

