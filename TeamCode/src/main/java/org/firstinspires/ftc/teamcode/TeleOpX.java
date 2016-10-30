package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;


@TeleOp(name = "TeleOpX")
//@Disabled

/**
 * FTC Team 11248 TeleOp Code.
 * //Cougars
 */
public class TeleOpX extends OpMode {

    /**
     * The robot being controlled.
     */
    private Robot11248 robot;

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
        double x = -gamepad1.left_stick_x; //X is inverted
        double y = gamepad1.left_stick_y;
        double rotation = gamepad1.right_stick_x;

        robot.drive(x,y,rotation); //set wheel motors

        //Sets liftarm up and down
        if(gamepad1.a)
            robot.switchLiftArm();

        //Sets shooter paddle up and down
        if(gamepad1.right_bumper)
            robot.switchPaddle();

        //Turns on/off shooter
        if(gamepad1.left_bumper)
           robot.shooterOn();
        else
           robot.shooterOff();

        //Sets arm motor to whatever right trigger is
        if(gamepad1.right_trigger > 0)
            robot.setLiftSpeed(gamepad1.right_trigger);
        else if(gamepad1.left_trigger > 0)
            robot.setLiftSpeed(-gamepad1.left_trigger);
        else
            robot.setLiftSpeed(0);

        //Sets angle shift based on dpad
        if(gamepad1.dpad_up)
            robot.setAngleShift(0);
        if(gamepad1.dpad_left)
            robot.setAngleShift(Robot11248.RIGHT_ANGLE);
        if(gamepad1.dpad_down)
            robot.setAngleShift(2*Robot11248.RIGHT_ANGLE);
        if(gamepad1.dpad_right)
            robot.setAngleShift(3*Robot11248.RIGHT_ANGLE);
    }
}