package org.snobot;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Solenoid;

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
    //
    // private IYamlUpdater solenoid1;
    // private IYamlUpdater solenoid2;
    // private IYamlUpdater mMotor1;
    // private IYamlUpdater mMotor2;


    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
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
                else if (type.equals("MomentarySolenoid"))
                {
                    parseMomentarySolenoid(updaterConfig);
                }
                else
                {
                    System.err.println("Unknown type " + type);
                }
            }

            // System.out.println(config);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        // Joystick joystick = new Joystick(0);
        // Solenoid sol1 = new Solenoid(0);
        // Solenoid sol2 = new Solenoid(1);
        // Victor mot1 = new Victor(1);
        // Victor mot2 = new Victor(2);
        //

        // mUpdaters.add(new MomentarySolenoid(joystick, sol1, 3));
        // mUpdaters.add(new MomentarySolenoid(joystick, sol2, 6));
        // mUpdaters.add(new MotorWithButton(joystick, mot1, 4, -.5));
        // mUpdaters.add(new MotorWithButton(joystick, mot2, 7, .3));
    }

    private void parseMomentarySolenoid(Map<String, Object> aConfig)
    {
        int solenoidPort = (Integer) aConfig.get("solenoid_port");
        int buttonNumber = (Integer) aConfig.get("button_number");
        Solenoid solenoid = new Solenoid(solenoidPort);

        mUpdaters.add(new MomentarySolenoid(mJoystick, solenoid, buttonNumber));
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
        // System.out.print("TELE");
        for (IYamlUpdater updater : mUpdaters)
        {
            updater.update();
        }
        // System.out.println();
        // solenoid1.update();
        // solenoid2.update();
        // mMotor1.update();
        // mMotor2.update();


    }
}

