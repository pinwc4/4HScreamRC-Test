package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.MeasuredDistance;


public class AutonomousDistanceForwardSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private DcMotor dcmFLMotor;
    private DcMotor dcmFRMotor;
    private DcMotor dcmBRMotor;
    private DcMotor dcmBLMotor;

    private DistanceSensor snsDistanceBack;

    private double dblFrontLeftMotorValue;
    private double dblFrontRightMotorValue;
    private double dblBackLeftMotorValue;
    private double dblBackRightMotorValue;

    private double dblFrontLeftMotorPower;
    private double dblFrontRightMotorPower;
    private double dblBackLeftMotorPower;
    private double dblBackRightMotorPower;

    private double distSense;

    private static final double NORMAL_POWER = 0.3;
    private static final double REDUCED_POWER = 0.525;
    private double dblDesiredHeading = 0;
    private static final double CORRECTION_AGGRESSION = 0.03;
    static final double INCREMENT   = 0.01;

    private static final double TICKSTOINCHES = (384.5 * 1) / (Math.PI * 3.77953);
    //                                           537.6 ppr
    Orientation angles;

    private MeasuredDistance msdMeasuredDistance = null;

    private Telemetry telemetry;

    BNO055IMU imu;

    private boolean bolinitialized;
    private boolean boldistReached;

    private boolean bolRampUp = true;

    private double disttoTravelin;


    public AutonomousDistanceForwardSegment(double distSense,double distToTravelin, double dblDesiredHeading, DcMotor dcmFLMotor, DcMotor dcmFRMotor, DcMotor dcmBLMotor, DcMotor dcmBRMotor, Telemetry telemetry, BNO055IMU imu, DistanceSensor snsDistanceBack) {
        //add appropriate comment for conversions
        this.distSense = distSense;

        desiredEncoderTicks = (distToTravelin * TICKSTOINCHES);

        this.telemetry = telemetry;

        this.imu = imu;

        this.dcmFLMotor = dcmFLMotor;
        this.dcmFRMotor = dcmFRMotor;
        this.dcmBLMotor = dcmBLMotor;
        this.dcmBRMotor = dcmBRMotor;

        this.dblDesiredHeading = dblDesiredHeading;

        this.snsDistanceBack = snsDistanceBack;

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

        if (msdMeasuredDistance != null){
            desiredEncoderTicks = desiredEncoderTicks + msdMeasuredDistance.getDistance();
        }

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        bolinitialized = true;

    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double dblHeading = angles.firstAngle;

        double dblHeadingDifference = dblHeading - dblDesiredHeading;

        double dblHeadingCorrection = dblHeadingDifference*CORRECTION_AGGRESSION;

        double dblRampDown = desiredEncoderTicks * 0.2945;

        if(desiredEncoderTicks > 0){
            dblFrontLeftMotorPower = NORMAL_POWER;
            dblFrontRightMotorPower = NORMAL_POWER;
            dblBackLeftMotorPower = NORMAL_POWER;
            dblBackRightMotorPower = NORMAL_POWER;

            if(dblHeading > dblDesiredHeading){
                dblFrontLeftMotorPower += (dblHeadingCorrection);
                dblBackLeftMotorPower += (dblHeadingCorrection);
            }else if(dblHeading < dblDesiredHeading){
                dblFrontRightMotorPower += (-dblHeadingCorrection);
                dblBackRightMotorPower += (-dblHeadingCorrection);
            }



            if (snsDistanceBack.getDistance(DistanceUnit.INCH) > distSense){

                dblFrontLeftMotorPower = 0;
                dblFrontRightMotorPower = 0;
                dblBackLeftMotorPower = 0;
                dblBackRightMotorPower = 0;

                boldistReached = true;
            }
        } else {
            dblFrontLeftMotorPower = -NORMAL_POWER;
            dblFrontRightMotorPower = -NORMAL_POWER;
            dblBackLeftMotorPower = -NORMAL_POWER;
            dblBackRightMotorPower = -NORMAL_POWER;

            if(dblHeading > dblDesiredHeading){
                dblFrontLeftMotorPower += (dblHeadingCorrection);
                dblBackLeftMotorPower += (dblHeadingCorrection);
            }else if(dblHeading < dblDesiredHeading){
                dblFrontRightMotorPower += (-dblHeadingCorrection);
                dblBackRightMotorPower += (-dblHeadingCorrection);
            }



            if (snsDistanceBack.getDistance(DistanceUnit.INCH) < distSense){

                dblFrontLeftMotorPower = 0;
                dblFrontRightMotorPower = 0;
                dblBackLeftMotorPower = 0;
                dblBackRightMotorPower = 0;

                boldistReached = true;
            }
        }




        dcmFLMotor.setPower(-dblFrontLeftMotorPower);
        dcmFRMotor.setPower(dblFrontRightMotorPower);
        dcmBLMotor.setPower(-dblBackLeftMotorPower);
        dcmBRMotor.setPower(dblBackRightMotorPower);

            telemetry.addLine();




        telemetry.addData("Distance", snsDistanceBack.getDistance(DistanceUnit.INCH));



        telemetry.update();
    }

    public boolean segmentComplete() {
        return boldistReached;
    }
}

