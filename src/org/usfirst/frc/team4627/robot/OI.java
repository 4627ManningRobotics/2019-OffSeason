package org.usfirst.frc.team4627.robot;


import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


import org.usfirst.frc.team4627.robot.commands.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	
	XboxController driverController = new XboxController(RobotMap.DRIVER_CONTROLLER);
	XboxController operatorController = new XboxController(RobotMap.OPERATOR_CONTROLLER);
	
	Button dButtonA = new JoystickButton(this.driverController, RobotMap.BUTTON_A);
	Button dButtonB = new JoystickButton(this.driverController, RobotMap.BUTTON_B);
	Button dButtonX = new JoystickButton(this.driverController, RobotMap.BUTTON_X);
	Button dButtonY = new JoystickButton(this.driverController, RobotMap.BUTTON_Y);
	Button dStartButton = new JoystickButton(this.driverController, RobotMap.START_BUTTON);
	
	Button oButtonA = new JoystickButton(this.operatorController, RobotMap.BUTTON_A);
	Button oButtonB = new JoystickButton(this.operatorController, RobotMap.BUTTON_B);
	Button oButtonY = new JoystickButton(this.operatorController, RobotMap.BUTTON_Y);
	Button oButtonX = new JoystickButton(this.operatorController, RobotMap.BUTTON_X);
	Button oButtonBack = new JoystickButton(this.operatorController, RobotMap.BACK_BUTTON);
	Button oButtonStart = new JoystickButton(this.operatorController, RobotMap.START_BUTTON);
	Button oButtonRightStick = new JoystickButton(this.operatorController, RobotMap.RIGHT_STICK_BUTTON);

	public boolean getOperatorButton(int axis) {
		return this.operatorController.getRawButtonPressed(axis);
	}
	
	public boolean getDriverButton(int axis) {
		return this.driverController.getRawButtonPressed(axis);
	}
	
	public double getOperatorRawAxis(int axis) {
		return Utilities.constrain(this.operatorController.getRawAxis(axis), -RobotMap.CONTROLLER_SAFEZONE, RobotMap.CONTROLLER_SAFEZONE);
	}
	
	public double getDriverRawAxis(int axis) {
		return Utilities.constrain(this.driverController.getRawAxis(axis), -RobotMap.CONTROLLER_SAFEZONE, RobotMap.CONTROLLER_SAFEZONE);
	}

	public OI () {
		
	}


}