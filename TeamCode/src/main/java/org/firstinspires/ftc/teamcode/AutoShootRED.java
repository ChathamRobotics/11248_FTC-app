package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Team 11248 Shooter Autonomous
 */
@Autonomous(name = "AutoShootRED")
public class AutoShootRED extends LinearOpMode{

    /**
     * The robot being controlled.
     */
    private Robot11248 robot;

    public static double LIFT_UP = 0;
    public static double LIFT_DOWN = 1;

    //Time spent driving forward in milliseconds
    //private long timeDriving = 3000;

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor[] motors = new DcMotor[8];
        Servo[] servos = new Servo[1];
        for(int i = 0; i < motors.length; i++)
            motors[i] = hardwareMap.dcMotor.get(Robot11248.MOTOR_LIST[i]);
        for(int i = 0; i < servos.length; i++)
            servos[i] = hardwareMap.servo.get(Robot11248.SERVO_LIST[i]);
        robot = new Robot11248(motors,servos,telemetry);

        robot.moveLiftArmUp();

        waitForStart();

        while (opModeIsActive()) {

            sleep(10000);

            //drive(1,.5);
            robot.driveold(0,.8,0,false);
            sleep(1500);

            //drive(0,0);
            robot.driveold(0,0,0,false);
            sleep(1000);

            robot.shooterOn();
            sleep(1500);

            robot.conveyorOn();
            sleep(5000);

            robot.conveyorOff();
            robot.shooterOff();

            robot.driveold(0,1,0,false);
            sleep(1500);

            robot.driveold(0,0,0,false);
            break;

        }
    }

}
