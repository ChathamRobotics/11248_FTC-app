package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import static java.lang.Thread.sleep;

@Autonomous(name = "RadAutonomous")
//@Disabled

public class RadAutonomous extends OpMode {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    ColorSensor colorSensorL;
    ColorSensor colorSensorR;

    double FL,FR,BR,BL;
    double threshold = .001;

    double DP_angle = 0;

    double x,y, angle, r;

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

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        colorSensorL = hardwareMap.colorSensor.get("Color1");
        colorSensorL.enableLed(true);
        colorSensorR = hardwareMap.colorSensor.get("Color2");
        colorSensorR.enableLed(true);
    }

    private boolean isWhite(int red, int green, int blue) {
        double lum = 0.2126*red + 0.7152*green + 0.0722*blue;
        return lum < 128;
    }

    public void power(double x, double y) {
        if(Math.abs(x) < threshold){
            x = 0;
        }

        if(Math.abs(y) >= threshold) {
            y = -y; //Y AXIS INVERTED
        } else {y=0;}

        if (x != 0) {
            angle = Math.atan(y / x);

        }else if(y > 0){//if it's 90 degrees use PI/2
            angle = Math.PI/2;

        }else if(y < 0){
            angle = (3 * Math.PI)/2;
        }

        r = Math.sqrt( (x*x) + (y*y) ) ;//get the radius (hypotenuse)
        angle += (Math.PI/4);//take our angle and shift it 90 deg (PI/4)

        FL = BR =  Math.sin(angle) * MAXSPEED * r; //takes new angle and radius and converts them into the motor values
        FR = BL = Math.cos(angle) * MAXSPEED * r;

        if(FL<=1 & FR<=1 & BR <=1 & BL<=1) {// Prevent fatal error
            frontLeft.setPower(FL); // -rot fl br y
            frontRight.setPower(FR); // -
            backLeft.setPower(-BL); // +
            backRight.setPower(-BR); //+
        }
    }

    public void loop() {
        power(1,1);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        power(0,0);
    }



    public void input(float x, float y, float rotat) {

    }
}
