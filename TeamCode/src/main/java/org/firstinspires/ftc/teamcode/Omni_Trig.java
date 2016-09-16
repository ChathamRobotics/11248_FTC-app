package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;


@Autonomous(name = "Omni: Trig")
//@Disabled //Uncomment to remove from shown OpModes

public class Omni_Trig extends OpMode {

    DcMotor FrontLeft;
    DcMotor FrontRight;
    DcMotor BackLeft;
    DcMotor BackRight;

    double MAXTTURN = .20;
    double MAXSPEED = .70;

    double threshold = .001;

    double x,y,rotat, angle, r;
    double FL,FR,BR,BL;

    @Override
    public void init() {

        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");

    }

    @Override
    public void loop() {

        //## GET VALUES ##

        //Gets values for x,y and rotation from the joysticks
        //uses threshold for debouncing

        if(Math.abs(gamepad1.left_stick_x) >= threshold){
            x = gamepad1.left_stick_x;
        } else {x=0;}

        if(Math.abs(gamepad1.left_stick_y) >= threshold) {
            y = -gamepad1.left_stick_y; //Y AXIS INVERTED
        } else {y=0;}

        if(Math.abs(gamepad1.right_stick_x) >= threshold){
            rotat = gamepad1.right_stick_x * MAXTTURN; //turn reduction factor
        } else {rotat=0;}


        //## CALCULATE VALUES ##

        //Takes regular x,y coordinates and converts them into polar (angle radius) cooridnates
        //Then turns angle by 90 degrees (Pi/4) to accomidate omni wheel axis


        //if x is 0, atan comes out undefined instead of PI/2 or 3PI/2
        if (x != 0) {
            angle = Math.atan(y / x);

        }else if(y >= 0){//if it's 90 degrees use PI/2
            angle = Math.PI/2;

        }else if(y < 0){
            angle = (3 * Math.PI)/2;
        }

        r = Math.sqrt( (x*x) + (y*y) ) ;//get the radius (hypotenuse)
        angle -= (Math.PI/4);//take our angle and shift it 90 deg (PI/4)


        // BUG FIX atan() assumes x is always positive and angle in standard position
        // add PI to go to quadrant 2 or 3
        if(x<0){
            angle += Math.PI;
        }

        FL = BR =  Math.sin(angle) * MAXSPEED * r; //takes new angle and raduis and converts them into the motor values
        FR = BL = Math.cos(angle) * MAXSPEED * r;

        FL -= rotat; // implements rotation
        FR -= rotat;
        BL += rotat;
        BR += rotat;

        if(FL<=1 & FR<=1 & BR <=1 & BL<=1) {// Prevent fatal error
            FrontLeft.setPower(FL); // -rot fl br y
            FrontRight.setPower(FR); // -
            BackLeft.setPower(-BL); // +
            BackRight.setPower(-BR); //+
        }

        telemetry.addData("01:", "angle: " + angle);
        telemetry.addData("02:", "rotat: " + rotat);
        telemetry.addData("03:", "x: " + x);
        telemetry.addData("04:", "y: " + y);
        telemetry.addData("05:", "FL: " + FL);
        telemetry.addData("06:", "FR: " + FR);
        telemetry.addData("07:", "BL: " + BR);
        telemetry.addData("08:", "BL: " + BL);
        telemetry.addData("09:", "r: " + r);


    }
}
