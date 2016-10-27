package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 *
 */
public class Robot11248 {
    //Angles
    public static final double RIGHT_ANGLE = Math.PI/2;
    public static final double OMNI_ANGLE_SHIFT = RIGHT_ANGLE/2;

    //Driving constants
    public static final double MAX_TURN = .30;
    public static final double MAX_SPEED = .70;
    public static final double CONVEYOR_SPEED = .9;

    //Servo constants
    public static final double PADDLE_DOWN = .45;
    public static final double PADDLE_UP = .85;
    public static final double LIFT_UP = 0;
    public static final double LIFT_DOWN = 1;

    //hardware map
    public static final String[] MOTOR_LIST =
            {"FrontLeft","FrontRight","BackLeft","BackRight","ShooterL","ShooterR","Lift"};

    public static final String[] SERVO_LIST =
            {"servo1","servo2"};

    //Motors, Sensors, Telemetry
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private DcMotor shooterL, shooterR, lift;
    private Servo paddle,liftArm;
    private Telemetry telemetry;
    private boolean isLiftArmUp = false;
    private boolean isPaddleUp = false;

    /**
     * Current angle shift.
     */
    private double angleShift = 0;

    /**
     * Initializes using a list of motors.
     * @param motors
     * @param servos
     * @param telemetry
     */
    public Robot11248(DcMotor[] motors, Servo[] servos, Telemetry telemetry) {
        this(motors[0],motors[1],motors[2],motors[3],motors[4],motors[5],
                motors[6],servos[0],servos[1],telemetry);
    }

    /**
     * Creates a model of the robot and initializes sensors, motors, and telemetry
     * @param frontLeft - wheel motor
     * @param frontRight - wheel motor
     * @param backLeft - wheel motor
     * @param backRight - wheel motor
     * @param shooterL - shooter motor
     * @param shooterR - shooter motor
     * @param lift - lift motor
     * @param paddle - shooter paddle servo
     * @param liftArm - lift release servo
     * @param telemetry
     */
    public Robot11248(DcMotor frontLeft,DcMotor frontRight,DcMotor backLeft,DcMotor backRight,
                      DcMotor shooterL,DcMotor shooterR,DcMotor lift,
                      Servo paddle, Servo liftArm, Telemetry telemetry) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.shooterL = shooterL;
        this.shooterR = shooterR;
        this.lift = lift;
        this.paddle = paddle;
        this.liftArm = liftArm;
        this.telemetry = telemetry;
    }

    /**
     * Initializes the robot. (In this case it just sets servo positions to default)
     */
    public void init() {
        liftArm.setPosition(LIFT_UP);
        paddle.setPosition(PADDLE_DOWN);
    }

    /**
     * Sets the angle shift of the robot driving.
     * @param shift - the angle in radians
     */
    public void setAngleShift(double shift) {
        angleShift = shift;
    }

    /**
     * Controls shooter paddle
     */
    public void movePaddleUp() {
        paddle.setPosition(PADDLE_UP);
        isPaddleUp = true;
    }

    /**
     * Controls shooter paddle
     */
    public void movePaddleDown() {
        paddle.setPosition(PADDLE_DOWN);
        isPaddleUp = false;
    }

    public void moveLiftArmUp() {
        liftArm.setPosition(LIFT_UP);
        isLiftArmUp = true;
    }

    public void moveLiftArmDown() {
        liftArm.setPosition(LIFT_DOWN);
        isLiftArmUp = false;
    }

    public void switchPaddle() {
        if(isPaddleUp)
            movePaddleDown();
        else
            movePaddleUp();
    }

    public void switchLiftArm() {
        if(isLiftArmUp)
            moveLiftArmDown();
        else
            moveLiftArmUp();
    }

    public void shooterOn() {
        shooterL.setPower(CONVEYOR_SPEED);
        shooterR.setPower(-CONVEYOR_SPEED);
    }

    public void shooterOff() {
        shooterL.setPower(0);
        shooterR.setPower(0);
    }

    public void setLiftSpeed(double speed) {
        if(speed > 1)
            speed = 1;
        if(speed < -1)
            speed = -1;
        lift.setPower(speed);
    }

    /**
     * Main robot driving procedure.
     * @param x - x value (-1,1)
     * @param y - y value (-1,1)
     * @param rotation - rotation value (-1,1)
     */
    public void drive(double x, double y, double rotation) {
        double angle, magnitude;

        //rectangular to polar form
        angle = x != 0 && y != 0 ? Math.atan2(y,x) : 0;
        magnitude = Math.sqrt( (x*x) + (y*y) ) ;

        //Turns angle by 45 degrees (Pi/4 radians) to accommodate omni wheel axis
        angle += (OMNI_ANGLE_SHIFT);

        frontLeft.setPower(getMotorSpeed(true,false,magnitude,rotation,angle));
        frontRight.setPower(getMotorSpeed(true,true,magnitude,rotation,angle));
        backLeft.setPower(getMotorSpeed(false,false,magnitude,rotation,angle));
        backRight.setPower(getMotorSpeed(false,true,magnitude,rotation,angle));
    }

    /**
     * Calculates the speed of the motor depending on its position and other inputs.
     * @param isFront
     * @param isRight
     * @param magnitude
     * @param rotation
     * @param angle
     * @return
     */
    public double getMotorSpeed(boolean isFront, boolean isRight, double magnitude, double rotation, double angle) {
        double power;
        rotation*=MAX_TURN; //Scales rotation

        //Back Right + Front Left and Front Right + Back Left
        power = magnitude * (isFront && !isRight || !isFront && isRight ? Math.sin(angle):Math.cos(angle));

        //Adds rotation
        power += isFront ? -rotation: rotation;

        //Reverses power if it is a back motor
        if(!isFront) power -= rotation;

        //Prevents motor controller crash
        if(power > 1)
            power = 1;
        else if(power < -1)
            power = -1;

        return power*MAX_SPEED; //Scales power
    }

    /**
     * Adds the telemetry data from the given values.
     * @param values
     */
    public void displayTelemetry(double... values) {
        String[] list = {"angle","rotat","x","y","FL","FR","BL","BR","r"};
        if(values.length <= list.length)
        for(int i = 0; i < list.length; i++) {
            telemetry.addData(i+":", list[i] + " :" + values[i]);
        }
    }
}
