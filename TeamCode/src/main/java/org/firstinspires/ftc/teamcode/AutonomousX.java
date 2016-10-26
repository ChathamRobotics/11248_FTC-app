package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.Timer;
import java.util.TimerTask;

/**
 * FTC Team 11248 Autonomous Code
 * //Cougars
 */
@Autonomous(name = "AutonomousX")

public class AutonomousX extends OpMode {
    /**
     * The robot being controlled.
     */
    private Robot11248 robot;

    //Time spent driving forward in milliseconds
    private long timeDriving = 3000;

    @Override
    public void init() {
        //Initializes all sensors and motors
        robot = new Robot11248(hardwareMap.dcMotor.get("FrontLeft"),
                hardwareMap.dcMotor.get("FrontRight"), hardwareMap.dcMotor.get("BackLeft"),
                hardwareMap.dcMotor.get("BackRight"), hardwareMap.dcMotor.get("ShooterL"),
                hardwareMap.dcMotor.get("ShooterR"), hardwareMap.dcMotor.get("Lift"),
                hardwareMap.servo.get("servo2"), hardwareMap.servo.get("servo1"),telemetry);
    }

    @Override
    public void loop() {
        robot.drive(0,1,0);
    }

    @Override
    public void start(){
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                robot.drive(0,0,0);
            }
        }, timeDriving);
    }
}
