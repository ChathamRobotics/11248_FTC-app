package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
/**
 * //Cougars ControllerOpMode
 */
@TeleOp(name = "ControllerOpMode", group = "General")
@Disabled

public class ControllerOpMode extends OpMode {

    public static float DEFAULT_SPEED = .7f;

    private DcMotor topLeft;
    private DcMotor topRight;
    private DcMotor bottomLeft;
    private DcMotor bottomRight;

    /**
     *This method will be called on INIT press.
     */
    @Override
    public void init() {
        topLeft = hardwareMap.dcMotor.get("frontLeft");
        topRight = hardwareMap.dcMotor.get("frontRight");
        bottomLeft = hardwareMap.dcMotor.get("backLeft");
        bottomRight = hardwareMap.dcMotor.get("backRight");
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
        float angle = (float)Math.atan2(gamepad1.right_stick_y,gamepad1.left_stick_x)*180;
        float speed = gamepad1.left_stick_y;
        topLeft.setPower(calculatePower(true,speed,angle));
        topRight.setPower(-1*calculatePower(false,speed,angle));
        bottomLeft.setPower(calculatePower(false,speed,angle));
        bottomRight.setPower(-1*calculatePower(true,speed,angle));

        //Finds speed to rotate robot in case of shoulder button being pressed.
        float rotateSpeed = DEFAULT_SPEED;
        if(gamepad1.x)
            rotateSpeed = 1;

        //Rotates robot clockwise (left shoulder)
        if(gamepad1.left_bumper) {
            topLeft.setPower(rotateSpeed);
            topRight.setPower(rotateSpeed);
            bottomLeft.setPower(rotateSpeed);
            bottomRight.setPower(rotateSpeed);
        }

        //Rotates robot counter-clockwise (right shoulder)
        if(gamepad1.right_bumper) {
            topLeft.setPower(-rotateSpeed);
            topRight.setPower(-rotateSpeed);
            bottomLeft.setPower(-rotateSpeed);
            bottomRight.setPower(-rotateSpeed);
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