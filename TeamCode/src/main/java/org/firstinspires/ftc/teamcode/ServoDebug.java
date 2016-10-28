package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Tony_Air on 12/11/15.
 */

@TeleOp(name = "SERVO")

public class ServoDebug extends OpMode {

    Servo servo1, servo2, servo3, servo4;

    int servo = 1;
    int servoPosition = 0;
    double increment = .05;

    @Override
    public void init() {

        servo1 = hardwareMap.servo.get("servo1");
        servo2 = hardwareMap.servo.get("servo2");
        servo3 = hardwareMap.servo.get("servo3");
        servo4 = hardwareMap.servo.get("servo4");



    }

    @Override
    public void loop() {

        int x = 1;

        if(gamepad1.a) {
            x++;
        }
        if(x == 5){
            x=1;
        }

        while(gamepad1.dpad_down && servoPosition >= 0){
            servoPosition -= increment;
        }
        while(gamepad1.dpad_up && servoPosition <= 1){
            servoPosition += increment;
        }


        switch (servo){
            case 1:
                servo1.setPosition(servoPosition);
                break;

            case 2:
                servo2.setPosition(servoPosition);
                break;

            case 3:
                servo3.setPosition(servoPosition);
                break;

            case 4:
                servo4.setPosition(servoPosition);
                break;


        }

        telemetry.addData("01:", "Servo: " + x);
        telemetry.addData("02:", "Position: " + servoPosition);


    }

}
