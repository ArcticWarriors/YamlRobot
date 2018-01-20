package org.snobot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class MotorWithButton implements IYamlUpdater
{
    private Joystick mJoystick1;
    private SpeedController mTestMotor1;
    private int mButtonNumber;
    private double mSpeed;

    public void update()
    {
        if (mJoystick1.getRawButton(mButtonNumber))
        {
            mTestMotor1.set(mSpeed);
        }
        else
        {
            mTestMotor1.set(0);
        }
    }

    public MotorWithButton(Joystick aJoystick, SpeedController aSpeedController, int aButtonNumber, double aSpeed)
    {
        mJoystick1 = aJoystick;
        mTestMotor1 = aSpeedController;
        mButtonNumber = aButtonNumber;
        mSpeed = aSpeed;
    }
}
