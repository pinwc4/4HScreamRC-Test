package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class AutonomousSlideSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private DcMotorEx dcmSlider;

    private Servo srvV4B;

    private int intSlidePosition;

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

        this.intSlidePosition = intSlideSpeed;

        this.dblServoPosition = dblServoPosition;

        this.telemetry = telemetry;

        this.dcmSlider = dcmSlider1;

        this.srvV4B = srvBucketServo;

        bolinitialized = false;
        boldistReached = false;
    }

    private void init() {
        //dcmSlider1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //dcmSlider1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider.setDirection(DcMotor.Direction.FORWARD);

        bolinitialized = true;

    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        dcmSlider.setTargetPosition(intSlidePosition);
        dcmSlider.setPower(0.5);
        dcmSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (dcmSlider.getCurrentPosition() > intSlidePosition - 100) {
            srvV4B.setPosition(dblServoPosition);
        }

        if(dcmSlider.getCurrentPosition() > intSlidePosition -10 && dcmSlider.getCurrentPosition() < intSlidePosition + 10){
            boldistReached = true;
        }

        telemetry.addData("front left motor position", dcmSlider.getCurrentPosition());
        telemetry.addData("front left motor", dblServoPosition);

        telemetry.update();
    }

    public boolean segmentComplete() {
        return boldistReached;
    }
}

