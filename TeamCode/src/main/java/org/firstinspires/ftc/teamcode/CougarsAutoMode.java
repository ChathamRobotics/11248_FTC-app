package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * autonomus
 */

@Autonomous(name = "Automode", group = "General")

public class CougarsAutoMode extends LinearOpMode {
    final int COLORRED = 10;
        // Hardware
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    ColorSensor beconSensor;
    ColorSensor lineSensor;
    Servo leftBeconServo;
    Servo rightBeconServo;


    /*
     * Runs robot
     */
    @Override
    public void runOpMode() throws InterruptedException {
        // Init
        frontLeft = hardwareMap.dcMotor.get("FrontLeft");
        frontRight = hardwareMap.dcMotor.get("FrontRight");
        backLeft = hardwareMap.dcMotor.get("BackLeft");
        backRight = hardwareMap.dcMotor.get("BackRight");
//        beconSensor = hardwareMap.colorSensor.get(beconSensorName);
        lineSensor = hardwareMap.colorSensor.get("lineSensor");
//        leftBeconServo = hardwareMap.servo.get(leftBeconServoName);
//        leftBeconServo = hardwareMap.servo.get(leftBeconServoName);

 

        // Wait for start call
        waitForStart();

        while (opModeIsActive()){

            while(!foundine()) {
                moveForward(.5);
            }
            turnLeft(.5);
            moveForward(.5);
            readBeacon();
        }
    }

    private void readBeacon() {
    }

    private void turnLeft(double rotationalSpeed) {
        frontLeft.setPower(rotationalSpeed);
        frontRight.setPower(-rotationalSpeed);
        backLeft.setPower(rotationalSpeed);
        backRight.setPower(-rotationalSpeed);
    }

    private boolean foundine() {
        if(lineSensor.red() == COLORRED)
            return true;
        else
            return false;
    }

    private void moveForward(double speed) {
        
        frontLeft.setPower(speed);
        frontRight.setPower(-speed);
        backLeft.setPower(speed);
        backRight.setPower(-speed);
        
    }
    
}