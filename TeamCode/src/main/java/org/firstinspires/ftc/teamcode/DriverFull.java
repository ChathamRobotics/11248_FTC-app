package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Team 11248 TeleOp for real robot.
 */
@TeleOp(name = "DrivingFull", group = "General")
public class DriverFull extends DriverOmni {

    public Robot11248 robot;

    @Override
    public void init() {
        //Initializes all sensors and motors
        DcMotor[] motors = new DcMotor[8];
        Servo[] servos = new Servo[1];
        for(int i = 0; i < motors.length; i++)
            motors[i] = hardwareMap.dcMotor.get(Robot11248.MOTOR_LIST[i]);
        for(int i = 0; i < servos.length; i++)
            servos[i] = hardwareMap.servo.get(Robot11248.SERVO_LIST[i]);
        robot = new Robot11248(motors,servos,telemetry);

    }

    @Override
    public void loop() {

        //Controls Wheels
        if (gamepad1.dpad_up)
            robot.setOffsetAngle(0);
        else if (gamepad1.dpad_left)
            robot.setOffsetAngle(Robot11248.RIGHT_ANGLE);
        else if (gamepad1.dpad_down)
            robot.setOffsetAngle(2 * Robot11248.RIGHT_ANGLE);
        else if (gamepad1.dpad_right)
            robot.setOffsetAngle(3 * Robot11248.RIGHT_ANGLE);

        robot.driveold(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x, true);


        //Sets liftarm up and down
        if (gamepad1.a)
            robot.moveLiftArmDown();
        else
            robot.moveLiftArmUp();

        //Turns on/off shooter/ collector

        boolean shooterOn = false;
        boolean shooterBack = false;
        boolean conveyorOn = false;
        boolean conveyorBack = false;


        if (gamepad1.left_bumper)
            shooterOn = true;

        else if (gamepad1.right_bumper)
            shooterBack = true;

        if (gamepad1.x)
            conveyorOn = true;

        else if (gamepad1.b){
            conveyorBack = true;
            shooterBack = true;
        }

        if(conveyorOn)
            robot.conveyorOn();
        else if (conveyorBack)
            robot.conveyorBack();
        else
            robot.conveyorOff();

        if (shooterBack)
            robot.shooterBack(.3);
        else if (shooterOn)
            robot.shooterOn();
        else
            robot.shooterOff();

        if (gamepad1.y) {
            while (gamepad1.y) {}
            if(robot.SLOW)
                robot.SLOW = false;
            else
                robot.SLOW = true;
        }

        //Sets arm motor to whatever right trigger is
        if (gamepad1.right_trigger > 0)
            robot.setLiftSpeed(gamepad1.right_trigger);
        else if (gamepad1.left_trigger > 0)
            robot.setLiftSpeed(-gamepad1.left_trigger);
        else
            robot.setLiftSpeed(0);

    }
}
