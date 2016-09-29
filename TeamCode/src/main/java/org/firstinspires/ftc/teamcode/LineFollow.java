package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "LineFollow")

public class LineFollow extends OpMode{

    DcMotor FrontLeft;
    DcMotor FrontRight;
    DcMotor BackLeft;
    DcMotor BackRight;

    ColorSensor colorSensor;

    int LeftRight = 1;
    int redVal = 10;

    @Override
    public void init() {

        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");

        colorSensor = hardwareMap.colorSensor.get("Color1");
        colorSensor.enableLed(true);
    }

    @Override
    public void loop() {

        while(colorSensor.red() >= redVal){
            moveRobot(0,.5);
        }

        while(colorSensor.red() < redVal){
            rotateRobot(.5 * LeftRight);
        }

        LeftRight *= -1;

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
