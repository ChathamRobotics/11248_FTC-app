package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Robot Class for team 11248
 */

public class Robot11248 extends OmniWheelDriver {

    //Angles
    public static final double RIGHT_ANGLE = Math.PI/2;

    //Driving constants
//    public static final double MAX_TURN = .30;
//    public static final double MAX_SPEED = .70;
    public static final double SHOOTER_SPEED = 1;

    //Servo constants
    public static final double LIFT_UP = 0;
    public static final double LIFT_DOWN = 1;

    //Motors, Sensors, Telemetry
    private DcMotor shooterL, shooterR, lift, conveyor;
    private Servo liftArm;
    private Telemetry telemetry;
    private boolean isLiftArmUp = false;

    //hardware map
    public static final String[] MOTOR_LIST =
            {"FrontLeft","FrontRight","BackLeft","BackRight","ShooterL","ShooterR","Lift","Conveyor"};

    public static final String[] SERVO_LIST =
            {"servo1"};

    /**
     * Initializes using a list of motors.
     * @param motors
     * @param servos
     * @param telemetry
     */
    public Robot11248(DcMotor[] motors, Servo[] servos, Telemetry telemetry) {
        this(motors[0],motors[1],motors[2],motors[3],motors[4],motors[5],
                motors[6],motors[7],servos[0],telemetry);
    }

    /**
     * Creates a model of the robot and initializes sensors, motors, and telemetry
     * @param frontLeft - wheel motor
     * @param frontRight - wheel motor
     * @param backLeft - wheel motor
     * @param backRight - wheel motor
     * @param shooterL - shooter motor
     * @param shooterR - shooter motor
     * @param conveyor - conveyor motor
     * @param lift - lift motor
     * @param liftArm - lift release servo
     * @param telemetry
     */
    public Robot11248(DcMotor frontLeft,DcMotor frontRight,DcMotor backLeft,DcMotor backRight,
                      DcMotor shooterL,DcMotor shooterR,DcMotor lift, DcMotor conveyor,
                      Servo liftArm, Telemetry telemetry) {
        super(frontLeft, frontRight, backLeft, backRight, telemetry);
        this.shooterL = shooterL;
        this.shooterR = shooterR;
        this.lift = lift;
        this.liftArm = liftArm;
        this.conveyor = conveyor;

        //this.shooterL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //this.shooterR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Initializes the robot. (In this case it just sets servo positions to default)
     */
    public void init() {
        moveLiftArmUp();
    }

    public void moveLiftArmUp() {
        liftArm.setPosition(LIFT_UP);
        isLiftArmUp = true;
    }

    public void moveLiftArmDown() {
        liftArm.setPosition(LIFT_DOWN);
        isLiftArmUp = false;
    }

    public void switchLiftArm() {
        if(isLiftArmUp)
            moveLiftArmDown();
        else
            moveLiftArmUp();
    }

    public void shooterOn(double SHOOTER_SPEED) {
        shooterL.setPower(SHOOTER_SPEED);
        shooterR.setPower(-SHOOTER_SPEED);
    }

    public void  shooterOn(){
        shooterOn(SHOOTER_SPEED);
    }

    public void shooterOff() {
        shooterL.setPower(0);
        shooterR.setPower(0);
    }

    public void shooterBack(double SHOOTER_SPEED) {
        shooterL.setPower(-SHOOTER_SPEED);
        shooterR.setPower(SHOOTER_SPEED);
    }

    public void shooterBack (){
        shooterBack(SHOOTER_SPEED);
    }

    public void setLiftSpeed(double speed) {
        if(speed > 1)
            speed = 1;
        if(speed < -1)
            speed = -1;
        lift.setPower(speed);
    }

    public void conveyorOn(){
        conveyor.setPower(-1);
    }

    public void conveyorBack(){
        conveyor.setPower(1);
    }

    public void conveyorOff(){
        conveyor.setPower(0);
    }
}
