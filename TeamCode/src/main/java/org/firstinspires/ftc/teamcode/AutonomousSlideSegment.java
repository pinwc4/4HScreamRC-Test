package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class AutonomousSlideSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private DcMotorEx dcmSlider1;

    private Servo srvBucketServo;

    private int intSlideSpeed;

    private double dblServoPosition;

    private static final double NORMAL_POWER = 0.75;

    private static final double TICKSTOINCHES = 14.6;

    private Telemetry telemetry;

    private boolean bolinitialized;
    private boolean boldistReached;
    private double disttoTravelin;

    private double dblRuntime;
    private double dblStartTime = 0;



    public AutonomousSlideSegment(int intSlideSpeed, double dblServoPosition, DcMotorEx dcmSlider1, Servo srvBucketServo, Telemetry telemetry) {
        //add appropriate comment for conversions

        this.intSlideSpeed = intSlideSpeed;

        this.dblServoPosition = dblServoPosition;

        this.telemetry = telemetry;

        this.dcmSlider1 = dcmSlider1;

        this.srvBucketServo = srvBucketServo;

        bolinitialized = false;
        boldistReached = false;
    }

    private void init() {
        //dcmSlider1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //dcmSlider1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider1.setDirection(DcMotor.Direction.FORWARD);

        bolinitialized = true;

    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        dcmSlider1.setTargetPosition(intSlideSpeed);
        dcmSlider1.setPower(0.5);
        dcmSlider1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (dcmSlider1.getCurrentPosition() > intSlideSpeed - 100) {
            srvBucketServo.setPosition(dblServoPosition);
        }

        if(dcmSlider1.getCurrentPosition() > intSlideSpeed -10 && dcmSlider1.getCurrentPosition() < intSlideSpeed + 10){
            boldistReached = true;
        }

        telemetry.addData("front left motor position", dcmSlider1.getCurrentPosition());
        telemetry.addData("front left motor", dblServoPosition);

        telemetry.update();
    }

    public boolean segmentComplete() {
        return boldistReached;
    }
}

