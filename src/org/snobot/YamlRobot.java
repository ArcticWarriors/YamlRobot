package org.snobot;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

public class YamlRobot extends IterativeRobot
{
    private static final String sCONFIG_FILE;

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

    private List<IYamlUpdater> mUpdaters;
    private Joystick mJoystick;

    public void robotInit()
    {
        mUpdaters = new ArrayList<>();
        mJoystick = new Joystick(0);
        try
        {
            Yaml yaml = new Yaml();
            Map<String, Object> contents = (Map<String, Object>) yaml.load(new FileReader(new File(sCONFIG_FILE)));

            List<Object> config = (List<Object>) contents.get("config");

            System.out.println(config);

            for (Object config2 : config)
            {
                Map<String, Object> updaterConfig = (Map<String, Object>) config2;
                String type = updaterConfig.get("type").toString();
                if (type.equals("MomentarySolenoid"))
                {
                    parseMomentarySolenoid(updaterConfig);
                }
                else if (type.equals("MotorWithButton"))
                {
                    parseMotorWithButton(updaterConfig);
                }
                else if (type.equals("MatchMotorJoystick"))
                {
                    parseMatchMotorJoystick(updaterConfig);
                }
                else
                {
                    System.err.println("Unknown type " + type);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void parseMomentarySolenoid(Map<String, Object> aConfig)
    {
        int solenoidPort = (Integer) aConfig.get("solenoid_port");
        int buttonNumber = (Integer) aConfig.get("button_number");
        Solenoid solenoid = new Solenoid(solenoidPort);

        mUpdaters.add(new MomentarySolenoid(mJoystick, solenoid, buttonNumber));
    }

    private void parseMotorWithButton(Map<String, Object> aConfig)
    {
        int speedController = (Integer) aConfig.get("speed_controller");
        int buttonNumber = (Integer) aConfig.get("button_number");
        int speed = (Integer) aConfig.get("speed");
        String motorType = (String) aConfig.get("motor_type");
        SpeedController motor = createSpeedController(motorType, speedController);

        mUpdaters.add(new MotorWithButton(mJoystick, motor, buttonNumber, speed));
    }

    private void parseMatchMotorJoystick(Map<String, Object> aConfig)
    {
        int speedController = (Integer) aConfig.get("speed_controller");
        int axisNumber = (Integer) aConfig.get("axis_number");
        String motorType = (String) aConfig.get("motor_type");
        SpeedController motor = createSpeedController(motorType, speedController);

        mUpdaters.add(new MatchMotorJoystick(mJoystick, motor, axisNumber));
    }

    public void autonomousInit()
    {

    }

    public void autonomousPeriodic()
    {

    }

    public void teleopPeriodic()
    {
        for (IYamlUpdater updater : mUpdaters)
        {
            updater.update();
        }
    }

    public SpeedController createSpeedController(String aMotorType, int aMotorPort)
    {
        String motorType = aMotorType;
        SpeedController speedController = null;
        if (motorType.equals("Talon"))
        {
            speedController = new Talon(aMotorPort);
        }
        else if (motorType.equals("Victor"))
        {
            speedController = new Victor(aMotorPort);
        }
        else if (motorType.equals("Jaguar"))
        {
            speedController = new Jaguar(aMotorPort);
        }
        else if (motorType.equals("VictorSP"))
        {
            speedController = new VictorSP(aMotorPort);
        }
        else
        {
            throw new RuntimeException("Unknown speed controller " + motorType);
        }
        return speedController;
    }
}
