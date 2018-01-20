package org.snobot;

import java.io.File;
import java.io.FileReader;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class YamlRobot extends IterativeRobot
{
    private static final String sCONFIG_FILE;

    private MatchMotorJoystick mGuy1;
    private MatchMotorJoystick mGuy2;

    static
    {
        if (RobotBase.isReal())
        {
            sCONFIG_FILE = "/home/lvuser/yaml_robot.yml";
        }
        else
        {
            sCONFIG_FILE = "test_files/yaml_robot.yml";
        }
    }


    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
//        try
//        {
//            Yaml yaml = new Yaml();
//            Map<String, Object> config = (Map<String, Object>) yaml.load(new FileReader(new File(sCONFIG_FILE)));
//            System.out.println(config);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
    	Joystick joystick = new Joystick(0);
    	SpeedController sc1 = new Talon(0);
    	SpeedController sc2 = new Talon(1);
    	mGuy1 = new MatchMotorJoystick(joystick, sc1);
    	mGuy2 = new MatchMotorJoystick(joystick, sc2);
    }

    public void autonomousInit()
    {

    }

    public void autonomousPeriodic()
    {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
    	mGuy1.Update();
    	mGuy2.Update();
    }
}

