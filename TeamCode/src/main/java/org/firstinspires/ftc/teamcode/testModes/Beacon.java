package org.firstinspires.ftc.teamcode.testModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Tony_Air on 10/6/16.
 */
public class Beacon extends LinearOpMode{

    DcMotor FrontLeft;
    DcMotor FrontRight;
    DcMotor BackLeft;
    DcMotor BackRight;

    ColorSensor colorSensor;


    @Override
    public void runOpMode() throws InterruptedException {

        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");

        colorSensor = hardwareMap.colorSensor.get("Color1");
        colorSensor.enableLed(true);

        waitForStart();

        while(opModeIsActive()){

            moveRobot(0,.5);

            wait(3000);


        }
    }

    private void moveRobot(double x, double y){

        double FL,FR,BL,BR;
        double angle = 0;
        double r = 0;

        if (x != 0) {
            angle = Math.atan(y / x);

        }else if(y > 0){//if it's 90 degrees use PI/2
            angle = Math.PI/2;

        }else if(y < 0){
            angle = (3 * Math.PI)/2;
        }

        r = Math.sqrt( (x*x) + (y*y) ) ;//get the radius (hypotenuse)
        angle += (Math.PI/4);//take our angle and shift it 90 deg (PI/4)


        // BUG FIX atan() assumes x is always positive and angle in standard position
        // add PI to go to quadrant 2 or 3
        if(x<0){
            angle += Math.PI;
        }

        FL = BR =  Math.sin(angle) * r; //takes new angle and radius and converts them into the motor values
        FR = BL = Math.cos(angle) * r;

        FrontLeft.setPower(FL);
        FrontRight.setPower(FR);
        BackLeft.setPower(-BL);
        BackRight.setPower(-BR);


    }

    private void rotateRobot(double rotat){

        FrontLeft.setPower(rotat);
        FrontRight.setPower(rotat);
        BackLeft.setPower(rotat);
        BackRight.setPower(rotat);

    }
}
