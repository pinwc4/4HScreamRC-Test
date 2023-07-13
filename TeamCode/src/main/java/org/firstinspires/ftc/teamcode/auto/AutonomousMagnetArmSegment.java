package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class AutonomousMagnetArmSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private DcMotorEx dcmMagnetArm;

    private Servo srvBucketServo;

    private int intMASpeed;

    private double dblServoPosition;

    private static final double NORMAL_POWER = 0.75;

    private static final double TICKSTOINCHES = 14.6;

    private Telemetry telemetry;

    private boolean bolinitialized;
    private boolean boldistReached;
    private double disttoTravelin;

    private double dblRuntime;
    private double dblStartTime = 0;



    public AutonomousMagnetArmSegment(int intMASpeed, DcMotorEx dcmMagnetArm, Telemetry telemetry) {
        //add appropriate comment for conversions

        this.intMASpeed = intMASpeed;

        this.telemetry = telemetry;

        this.dcmMagnetArm = dcmMagnetArm;

        bolinitialized = false;
        boldistReached = false;
    }

    private void init() {
        //dcmSlider1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //dcmSlider1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmMagnetArm.setDirection(DcMotor.Direction.FORWARD);

        bolinitialized = true;

    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        dcmMagnetArm.setTargetPosition(intMASpeed);
        dcmMagnetArm.setPower(0.9);
        dcmMagnetArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if(dcmMagnetArm.getCurrentPosition() > intMASpeed -10 && dcmMagnetArm.getCurrentPosition() < intMASpeed + 10){
            boldistReached = true;
        }

        telemetry.addData("front left motor position", dcmMagnetArm.getCurrentPosition());
        telemetry.addData("front left motor", dblServoPosition);

        telemetry.update();
    }

    public boolean segmentComplete() {
        return boldistReached;
    }
}

