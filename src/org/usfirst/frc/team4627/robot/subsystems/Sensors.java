package org.usfirst.frc.team4627.robot.subsystems;

import org.usfirst.frc.team4627.robot.commands.ContinuousPrint;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.SerialPort;

/**
 *
 */
public class Sensors extends Subsystem {

	public static final AHRS gyro = new AHRS(SerialPort.Port.kUSB);

    public void initDefaultCommand() {
        super.setDefaultCommand(new ContinuousPrint());
    }
}

