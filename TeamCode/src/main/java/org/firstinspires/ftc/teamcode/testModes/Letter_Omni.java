package org.firstinspires.ftc.teamcode.testModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "Omni: Letter")
@Disabled //Uncomment to remove from shown OpModes

/**
 * Created by FIRST on 6/17/2016.
 */
public class Letter_Omni extends LinearOpMode{

    DcMotor FrontLeft;
    DcMotor FrontRight;
    DcMotor BackLeft;
    DcMotor BackRight;

    @Override
    public void runOpMode() throws InterruptedException {
        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");

        forward_backward(.10);
        Thread.sleep(1000);
        forward_backward(0);

        left_right(.10);
        Thread.sleep(500);
        forward_backward(0);

        left_right(-.10);
        Thread.sleep(500);
        forward_backward(0);

    }

    private void forward_backward(double speed){
        FrontLeft.setPower(speed);
        FrontRight.setPower(speed);
        BackLeft.setPower(-speed);
        BackRight.setPower(-speed);

    }

    private void left_right(double speed){ // pos is left
        FrontLeft.setPower(-speed);
        FrontRight.setPower(speed);
        BackLeft.setPower(-speed);
        BackRight.setPower(speed);


    }
}
