package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.MeasuredDistance;


public class AutonomousStrafeForwardIntakeWaitSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private DcMotor dcmFLMotor;
    private DcMotor dcmFRMotor;
    private DcMotor dcmBRMotor;
    private DcMotor dcmBLMotor;

    private DcMotorEx dcmIntake0;

    private double dblFrontLeftMotorValue;
    private double dblFrontRightMotorValue;
    private double dblBackLeftMotorValue;
    private double dblBackRightMotorValue;

    private double dblFrontLeftMotorPower;
    private double dblFrontRightMotorPower;
    private double dblBackLeftMotorPower;
    private double dblBackRightMotorPower;

    private double dblRuntime;
    private double dblStartTime = 0;

    private static final double NORMAL_POWER = 0.3;
    private static final double REDUCED_POWER = 0.525;
    private double dblDesiredHeading = 0;
    private static final double CORRECTION_AGGRESSION = 0.10;
    static final double INCREMENT   = 0.02;

    private static final double TICKSTOINCHES = (537.6 * 1) / (Math.PI * 3.77953);

    Orientation angles;

    private MeasuredDistance msdMeasuredDistance = null;

    private Telemetry telemetry;

    BNO055IMU imu;

    private boolean bolinitialized;
    private boolean boldistReached;
    private double disttoTravelin;
    private boolean boldododo;


    public AutonomousStrafeForwardIntakeWaitSegment(double distToTravelin, double dblDesiredHeading, DcMotor dcmFLMotor, DcMotor dcmFRMotor, DcMotor dcmBLMotor, DcMotor dcmBRMotor, DcMotorEx dcmIntake0, Telemetry telemetry, BNO055IMU imu) {
        //add appropriate comment for conversions
        desiredEncoderTicks = (distToTravelin);

        this.telemetry = telemetry;

        this.imu = imu;

        this.dcmFLMotor = dcmFLMotor;
        this.dcmFRMotor = dcmFRMotor;
        this.dcmBLMotor = dcmBLMotor;
        this.dcmBRMotor = dcmBRMotor;

        this.dcmIntake0 = dcmIntake0;

        this.dblDesiredHeading = dblDesiredHeading;

        bolinitialized = false;
        boldistReached = false;
    }
/*
    public AutonomousStrafeForwardSegment(double distToTravelin, double dblDesiredHeading,  DcMotor dcmFLMotor, DcMotor dcmFRMotor, DcMotor dcmBLMotor, DcMotor dcmBRMotor, Telemetry telemetry, MeasuredDistance msdMeasuredDistance, BNO055IMU imu) {

        this.imu = imu;

        this.disttoTravelin = distToTravelin;
        desiredEncoderTicks = (distToTravelin * TICKSTOINCHES);

        this.dcmFLMotor = dcmFLMotor;
        this.dcmFRMotor = dcmFRMotor;
        this.dcmBLMotor = dcmBLMotor;
        this.dcmBRMotor = dcmBRMotor;
        this.telemetry = telemetry;
        this.msdMeasuredDistance = msdMeasuredDistance;

        this.dblDesiredHeading = dblDesiredHeading;

    }

 */
    private void init() {
        dcmFLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmFLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        dcmIntake0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmIntake0.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        bolinitialized = true;

    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        if(dcmIntake0.getVelocity() > 500) {
            boldododo = true;
        }
        if(boldododo) {
            if (dcmIntake0.getVelocity() < -450) {

                dcmFLMotor.setPower(-desiredEncoderTicks);
                dcmFRMotor.setPower(desiredEncoderTicks);
                dcmBLMotor.setPower(-desiredEncoderTicks);
                dcmBRMotor.setPower(desiredEncoderTicks);

            } else {

                dcmFLMotor.setPower(0);
                dcmFRMotor.setPower(0);
                dcmBLMotor.setPower(0);
                dcmBRMotor.setPower(0);

                boldistReached = true;

            }
        }

        telemetry.addData("first",angles.firstAngle);
        telemetry.addData("second", angles.secondAngle);
        telemetry.addData("third", angles.thirdAngle);
        telemetry.addData("HEADING", dblDesiredHeading);
        telemetry.addData("front left motor POSITION", dcmFLMotor.getCurrentPosition());
        telemetry.addData("front left motor power motor", dcmFLMotor.getPower());
        telemetry.addData("front left motor power motor", dcmFRMotor.getPower());
        telemetry.addData("front left motor power motor", dcmBLMotor.getPower());
        //telemetry.addData("front left motor value", dblFrontLeftMotorValue);
        //telemetry.addData("front right motor power dbl", dblFrontLeftMotorPower);
        //telemetry.addData("second", angles.secondAngle);
        //telemetry.addData("third", angles.thirdAngle);

        telemetry.update();
    }

    public boolean segmentComplete() {
        return boldistReached;
    }
}

