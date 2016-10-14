package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode; //OpMode Class Import
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;        // For Motors
import com.qualcomm.robotcore.hardware.Servo;


/*
    This sets the name of you progam which you will see on the phone to choose from

    Use @TeleOp for teleop programs
    Use @Autonomous for autonomous programs
*/
@TeleOp(name = "Tutorial")

@Disabled //@Disabled prevents this program from being listed on your robot
            // Comment out for a file to appear on the driver station app


/*
     Use OpMode as the parent class
    This give you methods init() and loop() to put your code in

*/
public class Tutorial extends OpMode{

    //Declare all variables and objects here

    boolean a;
    float x,y;

    //DcMotor is the object for Motors
    //Only declare names here
    DcMotor Left, Right;

    //Servo is the object for servos
    Servo servo1;




    /*
            ## INIT ##

    All code in the init() will be run once before the loop
    This code should be for initializing your motors and servos
         Or any prep code for autonomous
     */
    @Override
    public void init() {

        /*
            When the phone is connected to your robot,
            you will configure your controllers and label each motor, servo, or other components a name

            hardwareMap.______.get() searches through your robot to find the component name you ask for

        */


        //Searches for a motor named "Left" in your config and sets it to your variable
        Left = hardwareMap.dcMotor.get("Left");
        Right = hardwareMap.dcMotor.get("Right");

        servo1 = hardwareMap.servo.get("Servo1");


    }


    /*
            ## LOOP ##

        This is where you put your controlling code
        This code will be carried out repetitively throughout TeleOp

        In here you can take values from your controllers and program each value to control a component
    */


    @Override
    public void loop() {

        /*
        gamepad1.(the value you want)  or gamepad2.______ returns the input from the controller

        */



        /*
            You can take the values from a joystick and set it to a variable to use later

            Analog controls like joysticks and triggers return floats
            Digital controls like buttons, d_pad buttons, and bumpers return booleans
        */

        x = gamepad1.left_stick_x; //Float
        y = gamepad1.left_stick_y;

        a = gamepad2.a; //Boolean



        /*

            (You're motor).setPower  sets the power of your motor

            this takes a double value from -1 to 1

         */

        Left.setPower(y - x);
        Right.setPower(-y - x);



        /*
        (You're servo).setPosition uses a double from -1 to 1 to set its position
         */
        servo1.setPosition(1);

    }
}
