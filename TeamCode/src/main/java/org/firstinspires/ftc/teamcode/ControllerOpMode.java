package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * //Cougars ControllerOpMode
 */
@TeleOp(name = "ControllerOpMode", group = "General")
//@Disabled

public class ControllerOpMode extends OpMode {

    public static float DEFAULT_SPEED = .7f;

    private DcMotor topLeft;
    private DcMotor topRight;
    private DcMotor bottomLeft;
    private DcMotor bottomRight;

    /**
     * This method will be called on INIT press.
     */
    @Override
    public void init() {
        topLeft = hardwareMap.dcMotor.get("FrontLeft");
        topRight = hardwareMap.dcMotor.get("FrontRight");
        bottomLeft = hardwareMap.dcMotor.get("BackLeft");
        bottomRight = hardwareMap.dcMotor.get("BackRight");
    }

    /**
     * Called when the PLAY button is pressed.
     */
    @Override
    public void start(){

    }

    /**
     * Calculates the power of a motor set given:
     * @param set1 - whetehr or not it is set 1
     * @param scale - how much power to move.
     * @param deg - what degree to move in.
     * @return the power for the motor.
     */
    public float calculatePower(boolean set1, float scale, float deg) {
        double power = 0;
        if(set1)
            power = scale*Math.sin(deg);
        else
            power = scale*Math.cos(deg);
        //If X is not being held then speed will scale to DEFAULT_SPEED of maximum (1).
        if(!gamepad1.x)
            power*=DEFAULT_SPEED;
        return (float)power;
    }

    /**
     * Called repeatedly while op mode is running.
     * !!This is the main program loop!!
     */
    @Override
    public void loop() {
        //Sets power of motors based on right stick rotation when left stick is moved.
        topLeft.setPower(calculatePower(true,gamepad1.left_stick_x,gamepad1.right_stick_x*180));
        topRight.setPower(calculatePower(false,gamepad1.left_stick_y,gamepad1.right_stick_x*180));
        bottomLeft.setPower(calculatePower(false,gamepad1.left_stick_y,gamepad1.right_stick_x*180));
        bottomRight.setPower(calculatePower(true,gamepad1.left_stick_x,gamepad1.right_stick_x*180));

        //Finds speed to rotate robot in case of shoulder button being pressed.
        float speed = DEFAULT_SPEED;
        if(gamepad1.x)
            speed = 1;

        //Rotates robot clockwise (left shoulder)
        if(gamepad1.left_bumper) {
            topLeft.setPower(speed);
            topRight.setPower(speed);
            bottomLeft.setPower(speed);
            bottomRight.setPower(speed);
        }

        //Rotates robot counter-clockwise (right shoulder)
        if(gamepad1.right_bumper) {
            topLeft.setPower(-speed);
            topRight.setPower(-speed);
            bottomLeft.setPower(-speed);
            bottomRight.setPower(-speed);
        }
    }

    /**
     * Called when op mode is stopped. "Un-initialization"
     */
    @Override
    public void stop(){
        topLeft.setPower(0);
        topRight.setPower(0);
        bottomLeft.setPower(0);
        bottomRight.setPower(0);
    }
}