package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by FIRST on 6/3/2016.
 */

//DOESN'T WORK

@Autonomous(name = "Omni: Matrix")
@Disabled //Uncomment to remove from shown OpModes

public class Omni extends OpMode {

    DcMotor FrontLeft;
    DcMotor FrontRight;
    DcMotor BackLeft;
    DcMotor BackRight;

    double x,y,rotat;
    double FL,FR,BR,BL;

    double MAXTTURN = .2;
    double MAXSPEED = .8;


    public double getLeftSpeed (double x, double y){
        double r = 1/Math.sqrt(2);
        return (x*r)+(y*r);
    }
    public double getRightSpeed (double x, double y){
        double r = 1/Math.sqrt(2);
        return -(x*r)+(y*r);
    }

    @Override
    public void init() {

        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");

    }

    @Override
    public void loop() {

        x = gamepad1.left_stick_x;
        y = gamepad1.left_stick_y;
        rotat = gamepad1.right_stick_x;

        BR = FL = getLeftSpeed(x,y) * MAXSPEED;
        BR = FL = getRightSpeed(x,y) * MAXSPEED;

        FL += rotat * MAXTTURN;
        BL += rotat * MAXTTURN;
        FR += rotat * MAXTTURN;
        BR += rotat * MAXTTURN;

        FrontLeft.setPower(FL);
        FrontRight.setPower(FR);
        BackLeft.setPower(BL);
        BackRight.setPower(BR);

    }
}
