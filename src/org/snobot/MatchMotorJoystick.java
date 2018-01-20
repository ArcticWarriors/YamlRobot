package org.snobot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class MatchMotorJoystick {

	private Joystick mJoystick;
	private SpeedController mSpeedController;
	
	int mAxisNumber;
	
	public void Update()
	{
		double speed=mJoystick.getRawAxis(mAxisNumber);
		mSpeedController.set(speed);
		
	}
	public MatchMotorJoystick (Joystick aJoystick, SpeedController aSpeedController)
	{
		mJoystick=aJoystick;
		
		mSpeedController=aSpeedController;
		
	}
}
