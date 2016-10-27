package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Timer;
import java.util.TimerTask;

@Autonomous(name = "AutonomousX")
//@Disabled

/**
 * FTC Team 11248 Autonomous Code
 * //Cougars
 */
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
        DcMotor[] motors = new DcMotor[7];
        Servo[] servos = new Servo[2];
        for(int i = 0; i < motors.length; i++)
            motors[i] = hardwareMap.dcMotor.get(Robot11248.MOTOR_LIST[i]);
        for(int i = 0; i < servos.length; i++)
            servos[i] = hardwareMap.servo.get(Robot11248.SERVO_LIST[i]);
        robot = new Robot11248(motors,servos,telemetry);
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
