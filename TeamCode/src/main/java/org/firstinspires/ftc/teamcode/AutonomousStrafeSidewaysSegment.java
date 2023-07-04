package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public class AutonomousStrafeSidewaysSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private DcMotor dcmFRMotor;
    private DcMotor dcmFLMotor;
    private DcMotor dcmBRMotor;
    private DcMotor dcmBLMotor;

    private double dblFrontLeftMotorValue;
    private double dblFrontRightMotorValue;
    private double dblBackLeftMotorValue;
    private double dblBackRightMotorValue;

    private double dblFrontLeftMotorPower;
    private double dblFrontRightMotorPower;
    private double dblBackLeftMotorPower;
    private double dblBackRightMotorPower;

    private static final double NORMAL_POWER = 0.4;
    private static final double REDUCED_POWER = 0.6;
    private double dblDesiredHeading = 0;
    private static final double HEADING_ERROR_RANGE = 3;
    private static final double CORRECTION_AGGRESSION = 0.04;
    static final double INCREMENT   = 0.02;


    Orientation angles;
    BNO055IMU imu;

    private Telemetry telemetry;

    private boolean bolinitialized;
    private boolean boldistReached;

    private static final double TICKSTOINCHES = 1906.25; //0.689 * 2 * Math.PI / 8192;


    public AutonomousStrafeSidewaysSegment(double distToTravelin, double dblDesiredHeading, DcMotor dcmFLMotor, DcMotor dcmFRMotor, DcMotor dcmBLMotor, DcMotor dcmBRMotor, Telemetry telemetry, BNO055IMU imu) {
        // add approptiate comment for conversions
        desiredEncoderTicks = distToTravelin * TICKSTOINCHES;

        this.imu = imu;

        this.telemetry = telemetry;

        this.dcmFLMotor = dcmFLMotor;
        this.dcmFRMotor = dcmFRMotor;
        this.dcmBLMotor = dcmBLMotor;
        this.dcmBRMotor = dcmBRMotor;

        this.dblDesiredHeading = dblDesiredHeading;

        bolinitialized = false;
        boldistReached = false;
    }

    private void init() {
        dcmFLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmFRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmBLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmBRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmFLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmFRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmBLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmBRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bolinitialized = true;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        double dblMotorPosition = -dcmFLMotor.getCurrentPosition();

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double dblHeading = angles.firstAngle;

        double dblHeadingDifference = dblHeading - dblDesiredHeading;

        double dblHeadingCorrection = dblHeadingDifference*CORRECTION_AGGRESSION;

        double dblRampDown = desiredEncoderTicks * 0.2945;

        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.

        if(dblMotorPosition < (desiredEncoderTicks) && desiredEncoderTicks > 0){
            dblFrontLeftMotorPower = NORMAL_POWER;
            dblFrontRightMotorPower = NORMAL_POWER;
            dblBackLeftMotorPower = NORMAL_POWER;
            dblBackRightMotorPower = NORMAL_POWER;

            if(dblHeading > dblDesiredHeading){
                dblFrontRightMotorPower -= (dblHeadingCorrection);
                dblFrontLeftMotorPower -= (dblHeadingCorrection);
            }else if(dblHeading < dblDesiredHeading){
                dblBackRightMotorPower -= (-dblHeadingCorrection);
                dblBackLeftMotorPower -= (-dblHeadingCorrection);
            }

        } else if(dblMotorPosition > (desiredEncoderTicks) && desiredEncoderTicks < 0){
            dblFrontLeftMotorPower = -NORMAL_POWER;
            dblFrontRightMotorPower = -NORMAL_POWER;
            dblBackLeftMotorPower = -NORMAL_POWER;
            dblBackRightMotorPower = -NORMAL_POWER;

            if(dblHeading < dblDesiredHeading){
                dblFrontRightMotorPower -= (dblHeadingCorrection);
                dblFrontLeftMotorPower -= (dblHeadingCorrection);
            }else if(dblHeading > dblDesiredHeading){
                dblBackRightMotorPower -= (-dblHeadingCorrection);
                dblBackLeftMotorPower -= (-dblHeadingCorrection);
            }

        } else{

            dblFrontLeftMotorPower = 0;
            dblFrontRightMotorPower = 0;
            dblBackLeftMotorPower = 0;
            dblBackRightMotorPower = 0;

            boldistReached = true;
        }





        dcmFLMotor.setPower(dblFrontLeftMotorPower);
        dcmFRMotor.setPower(dblFrontRightMotorPower);
        dcmBLMotor.setPower(-dblBackLeftMotorPower);
        dcmBRMotor.setPower(-dblBackRightMotorPower);

        /*
        telemetry.addData("heading", dblHeading);
        telemetry.addData("heading correction", dblHeadingCorrection);
        telemetry.addData("distance", dcmFLMotor.getCurrentPosition());
        telemetry.addData("FLMotor Power", dcmFLMotor.getPower());
        telemetry.addData("FRMotor Power", dcmFRMotor.getPower());
        telemetry.addData("front right motor power", dblFrontRightMotorPower);
        telemetry.addData("front right motor value", dblFrontRightMotorValue);
         */

        telemetry.addData("position", dblMotorPosition);

        telemetry.addData("desired", desiredEncoderTicks);

        telemetry.addData("FL", dcmFLMotor.getCurrentPosition());
        telemetry.addData("FR", dcmFRMotor.getCurrentPosition());
        telemetry.addData("BL", dcmBLMotor.getCurrentPosition());
        telemetry.addData("BR", dcmBRMotor.getCurrentPosition());

        telemetry.update();

    }

        public boolean segmentComplete () {
            return boldistReached;
        }

}


