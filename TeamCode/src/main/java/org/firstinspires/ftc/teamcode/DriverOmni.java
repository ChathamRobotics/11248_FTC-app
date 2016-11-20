package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


import java.util.Map;

/**
 * Team 11248 TeleOp for test robot.
 */
@TeleOp(name = "DrivingOmni", group = "General")
@Disabled
public class DriverOmni extends OpMode {
    /*
     * Config
     */
    private double maxSpeedModifier = .7;
    private double minSpeedModifier = .1;

    private OmniWheelDriver robot;

    /*
     * initializes robot
     */
    @Override
    public void init() {
        robot = new OmniWheelDriver(
                hardwareMap.dcMotor.get("FrontLeft"),
                hardwareMap.dcMotor.get("FrontRight"),
                hardwareMap.dcMotor.get("BackLeft"),
                hardwareMap.dcMotor.get("BackRight"),
                telemetry
        );
    }

    /*
     * Starts robot
     */
    @Override
    public void start() {
        super.start();
    }

    /*
     * Called continuously while opmode is active
     */
    @Override
    public void loop() {
        // Drive
        //Sets angle shift based on dpad
        if (gamepad1.dpad_up)
            robot.setOffsetAngle(0);
        else if (gamepad1.dpad_left)
            robot.setOffsetAngle(Robot11248.RIGHT_ANGLE);
        else if (gamepad1.dpad_down)
            robot.setOffsetAngle(2 * Robot11248.RIGHT_ANGLE);
        else if (gamepad1.dpad_right)
            robot.setOffsetAngle(3 * Robot11248.RIGHT_ANGLE);

        robot.driveold(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x,true);

        telemetryData();
    }

    /*
     * Called when opmode is stopped
     */
    @Override
    public void stop() {
        super.stop();

        // Stop all motors
        for (Map.Entry<String, DcMotor> entry : hardwareMap.dcMotor.entrySet()) {
            entry.getValue().setPower(0);
        }
    }

    private void telemetryData() {
        // For each motor
        for (Map.Entry<String, DcMotor> entry : hardwareMap.dcMotor.entrySet()) {
            telemetry.addData("Motor Power", entry.getKey() + "="
                    + entry.getValue().getController().getMotorPower(entry.getValue().getPortNumber()));
        }
    }
}
