package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "TeleOp2")
//@Disabled //Uncomment to remove from shown OpModes

public class TeleOp2 extends OpMode {

    public static double MAX_TURN = .30;
    public static double MAX_SPEED = .70;
    public static double CONVEYOR_SPEED = .9;
    public static double THRESHOLD = .001;

    public static double ARM_DOWN = .2;
    public static double ARM_MID = .45;
    public static double ARM_UP = 1;

    public static double LIFT_UP = 0;
    public static double LIFT_DOWN = 1;

    double x,y,rotat, angle, r; //u spelt rotateshun rong u dummy
    double FL,FR,BR,BL;
    double DP_angle = Math.PI;

    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public DcMotor shooterL, shooterR, lift;
    public Servo arm,liftServo, liftServo2;

    private boolean liftUp = false;
    private boolean armUp = false;

    @Override
    public void init() {
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

        liftServo.setPosition(LIFT_DOWN);
        liftServo2.setPosition(LIFT_UP);
        arm.setPosition(ARM_DOWN);
    }

    @Override
    public void loop() {

        //## GET VALUES ##

        // Gets values for x,y and rotation from the joysticks
        // Uses THRESHOLD for debouncing

       // if(Math.abs(gamepad1.left_stick_x) >= THRESHOLD){
            x = -gamepad1.left_stick_x;
       // } else {x=0;}

       // if(Math.abs(gamepad1.left_stick_y) >= THRESHOLD) {
            y = gamepad1.left_stick_y; //Y AXIS INVERTED
        //} else {y=0;}

       // if(Math.abs(gamepad1.right_stick_x) >= THRESHOLD){
            rotat = gamepad1.right_stick_x * MAX_TURN; //turn reduction factor
        //} else {rotat=0;}

        drive(x,y,rotat, true); //set wheel motors


        //Sets beacon paddle up and down
        if(gamepad1.a){
            liftServo.setPosition(LIFT_UP);
            liftServo2.setPosition(LIFT_DOWN);
        }else{
            liftServo.setPosition(LIFT_DOWN);
            liftServo2.setPosition(LIFT_UP);
        }

        //Turns on shooter by setting both shooter motors to SHOOTER_SPEED.
        if(gamepad1.left_bumper) {
            shooterL.setPower(CONVEYOR_SPEED);
            shooterR.setPower(-CONVEYOR_SPEED);
        }
        else {
            shooterL.setPower(0);
            shooterR.setPower(0);
        }

        //Switches position when right bumper pressed
        if(gamepad1.right_bumper) {
            arm.setPosition(ARM_UP);
        } else if(gamepad1.x){
            arm.setPosition(ARM_MID);
        } else {
            arm.setPosition(ARM_DOWN);
        }



        //Sets arm motor to whatever right trigger is at (maxes out at MAX_SPEED)

        if(gamepad1.right_trigger>0) {
            lift.setPower(gamepad1.right_trigger);

            //Sets arm motor to whatever left trigger is at (maxes out at MAX_SPEED)
        } else if(gamepad1.left_trigger>0) {
            lift.setPower(-gamepad1.left_trigger);
        }else{
            lift.setPower(0);
        }

        addTelemetry(angle,rotat,x,y,FL,FR,BR,BL,r);
    }

   public void drive(double x, double y, double rotat, boolean smooth){

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


       if(smooth) r = r*r; //Using a function on variable r will smooth out the slow values but still give full range

       //TODO: r = -(4/3*r-2)/((4/3*r)*(4/3*r)); Cooler more impressive function


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

    public void addTelemetry(double... values) {
        String[] list = {"angle","rotat","x","y","FL","FR","BL","BR","r"};
        for(int i = 0; i < values.length; i++) {
            telemetry.addData(i+":", list[i] + " :" + values[i]);
        }
    }
}