package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


/**
 * Created by tonytesoriero on 9/13/16.
 */

@Autonomous(name = "Template: Autonomous", group = "Red")

//@Disabled //Uncomment to remove from shown OpModes

public class TestAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {


        /*
        INIT CODE
         */

        waitForStart();

        while (opModeIsActive()){

            /*
            AUTONOMOUS CODE
             */

            idle(); //Stops the robot, waits for stop button
        }
    }
}
