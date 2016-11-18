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

    DcMotor conveyor;
    @Override
    public void init() {
        //Initializes all sensors and motors
        DcMotor[] motors = new DcMotor[7];
        Servo[] servos = new Servo[1];
        for(int i = 0; i < motors.length; i++)
            motors[i] = hardwareMap.dcMotor.get(Robot11248.MOTOR_LIST[i]);
        for(int i = 0; i < servos.length; i++)
            servos[i] = hardwareMap.servo.get(Robot11248.SERVO_LIST[i]);
        robot = new Robot11248(motors,servos,telemetry);

        conveyor = hardwareMap.dcMotor.get("Conveyor");
    }

    @Override
    public void loop() {
        //Controls Wheels
        super.loop();

        //Sets liftarm up and down
        if (gamepad1.a)
            robot.switchLiftArm();

        //Turns on/off shooter
        if (gamepad1.left_bumper)
            robot.shooterOn();
        else if (gamepad1.right_bumper)
            robot.shooterBack();
        else
            robot.shooterOff();



        if (gamepad1.x)
            conveyor.setPower(-1);
        else if (gamepad1.b)
            conveyor.setPower(1);
        else
            conveyor.setPower(0);

        //Sets arm motor to whatever right trigger is
        if (gamepad1.right_trigger > 0)
            robot.setLiftSpeed(gamepad1.right_trigger);
        else if (gamepad1.left_trigger > 0)
            robot.setLiftSpeed(-gamepad1.left_trigger);
        else
            robot.setLiftSpeed(0);
    }
}
