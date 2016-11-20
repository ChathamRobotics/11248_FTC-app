package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by tonytesoriero on 10/29/16.
 */
@Autonomous(name = "AutoShoot")
@Disabled
public class AutoShoot extends LinearOpMode {

    public static double MAX_SPEED = .70;
    public static double CONVEYOR_SPEED = .9;

    public static double ARM_DOWN = .2;
    public static double ARM_MID = .45;
    public static double ARM_UP = 1;


    double x,y,rotat, angle, r; //u spelt rotateshun rong u dummy
    double FL,FR,BR,BL;
    double DP_angle = Math.PI;

    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public DcMotor shooterL, shooterR, lift;
    public Servo arm,liftServo, liftServo2;


    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.dcMotor.get("FrontLeft");
        frontRight = hardwareMap.dcMotor.get("FrontRight");
        backLeft = hardwareMap.dcMotor.get("BackLeft");
        backRight = hardwareMap.dcMotor.get("BackRight");

        shooterL = hardwareMap.dcMotor.get("ShooterL");
        shooterR = hardwareMap.dcMotor.get("ShooterR");
        lift = hardwareMap.dcMotor.get("Lift");
        liftServo2 = hardwareMap.servo.get("servo3");
        liftServo =  hardwareMap.servo.get("servo2");
        arm = hardwareMap.servo.get("servo1");


        arm.setPosition(ARM_DOWN);
        liftServo.setPosition(ARM_DOWN);
        liftServo2.setPosition(ARM_UP);


        waitForStart();

        while (opModeIsActive()) {

            sleep(10000);

            drive(.5,0,0);
            sleep(2000);

            drive(0,0,0);
            sleep(500);

            shooterL.setPower(CONVEYOR_SPEED);
            shooterR.setPower(-CONVEYOR_SPEED);

            sleep(2000);
            arm.setPosition(ARM_MID);

            sleep(3000);
            arm.setPosition(ARM_UP);

            sleep(5000);
            shooterL.setPower(0);
            shooterR.setPower(0);

            drive(.5,0,0);
            sleep(5000);

            drive(0,0,0);

            break;

        }
    }

    public void drive(double x, double y, double rotat){

        //## CALCULATE VALUES ##

        // Takes regular x,y coordinates and converts them into polar (angle radius) cooridnates
        // Then turns angle by 90 degrees (Pi/4) to accommodate omni wheel axis

        // if x is 0, atan comes out undefined instead of PI/2 or 3PI/2
        if (x != 0) {
            angle = Math.atan(y / x);

        }else if(y > 0){//if it's 90 degrees use PI/2
            angle = Math.PI/2;

        }else{
            angle = (3 * Math.PI)/2;
        }

        r = Math.sqrt( (x*x) + (y*y) ) ;//get the radius (hypotenuse)
        //angle += (Math.PI/4);//take our angle and shift it 90 deg (PI/4)

        // BUG FIX atan() assumes x is always positive and angle in standard position
        // add PI to go to quadrant 2 or 3
        if(x<0){
            angle += Math.PI;
        }

        if(gamepad1.dpad_up){DP_angle = 0;}
        if(gamepad1.dpad_left){DP_angle = Math.PI/2;}
        if(gamepad1.dpad_down){DP_angle = Math.PI;}
        if(gamepad1.dpad_right){DP_angle = (3*Math.PI)/2;}


        FL = BR =  Math.sin(angle + DP_angle) * MAX_SPEED * r; //takes new angle and radius and converts them into the motor values
        FR = BL = Math.cos(angle + DP_angle) * MAX_SPEED * r;

        FL -= rotat; // implements rotation
        FR -= rotat;
        BL += rotat;
        BR += rotat;

        if(FL<=1 & FR<=1 & BR <=1 & BL<=1) {// Prevent fatal error
            frontLeft.setPower(FL); // -rot fl br y
            frontRight.setPower(FR); // -
            backLeft.setPower(-BL); // +
            backRight.setPower(-BR); //+
        }
    }
}
