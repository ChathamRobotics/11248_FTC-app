package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "TeleOp2")
//@Disabled //Uncomment to remove from shown OpModes

public class TeleOp2 extends OpMode {

    public static double MAX_TURN = .30;
    public static double MAX_SPEED = .70;
    public static double CONVEYOR_SPEED = .9;
    public static double THRESHOLD = .001;

    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public DcMotor conveyor, arm;

    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("FrontLeft");
        frontRight = hardwareMap.dcMotor.get("FrontRight");
        backLeft = hardwareMap.dcMotor.get("BackLeft");
        backRight = hardwareMap.dcMotor.get("BackRight");

        conveyor = hardwareMap.dcMotor.get("Conveyor");
        arm = hardwareMap.dcMotor.get("Arm");
    }

    @Override
    public void loop() {
        //Initialize values.
        double x,y,rotat, angle, r;
        double FL,FR,BR,BL;
        double DP_angle = 0;

        //## GET VALUES ##

        // Gets values for x,y and rotation from the joysticks
        // Uses THRESHOLD for debouncing

        if(Math.abs(gamepad1.left_stick_x) >= THRESHOLD){
            x = gamepad1.left_stick_x;
        } else {x=0;}

        if(Math.abs(gamepad1.left_stick_y) >= THRESHOLD) {
            y = -gamepad1.left_stick_y; //Y AXIS INVERTED
        } else {y=0;}

        if(Math.abs(gamepad1.right_stick_x) >= THRESHOLD){
            rotat = gamepad1.right_stick_x * MAX_TURN; //turn reduction factor
        } else {rotat=0;}

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
        angle += (Math.PI/4);//take our angle and shift it 90 deg (PI/4)

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

        //Turns on conveyor when left bumper is on, sets speed to CONVEYOR_SPEED
        if(gamepad1.left_bumper) {
            conveyor.setPower(CONVEYOR_SPEED);
        }
        else {
            conveyor.setPower(0);
        }

        //Sets arm motor to whatever right trigger is at (maxes out at MAX_SPEED)
        if(Math.abs(gamepad1.right_trigger) >= THRESHOLD) {
            arm.setPower(gamepad1.right_trigger > MAX_SPEED ? MAX_SPEED :gamepad1.right_trigger);
        }

        addTelemetry(angle,rotat,x,y,FL,FR,BR,BL,r);
    }

    public void addTelemetry(double... values) {
        String[] list = {"angle","rotat","x","y","FL","FR","BL","BR","r"};
        for(int i = 0; i < values.length; i++) {
            telemetry.addData(i+":", list[i] + " :" + values[i]);
        }
    }
}