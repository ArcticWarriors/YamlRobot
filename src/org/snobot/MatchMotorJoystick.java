package org.snobot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class MatchMotorJoystick implements IYamlUpdater
{
    private Joystick mJoystick;
    private SpeedController mSpeedController;
    int mAxisNumber;
	
    public void update()
	{
		double speed=mJoystick.getRawAxis(mAxisNumber);
		mSpeedController.set(speed);
		
	}

    public MatchMotorJoystick(Joystick aJoystick, SpeedController aSpeedController, int aAxisNumber)
	{
		mJoystick=aJoystick;
		
		mSpeedController=aSpeedController;
		
        mAxisNumber = aAxisNumber;

	}
}
