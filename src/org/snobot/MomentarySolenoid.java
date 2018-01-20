package org.snobot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

public class MomentarySolenoid implements IYamlUpdater
{
	
	private Joystick mJoystick1;
	private Solenoid mSolenoid;
	private int mButtonNumber;
	
	public void update() 
	{
		if (mJoystick1.getRawButton(mButtonNumber))
		{
			mSolenoid.set(true);
		}
		else 
		{
			mSolenoid.set(false);
		}
		
	}
	public MomentarySolenoid(Joystick aJoystick, Solenoid aSolenoid, int aButtonNumber) 
	{
		mJoystick1=aJoystick;
		mSolenoid=aSolenoid;
		mButtonNumber=aButtonNumber;
	}
}	