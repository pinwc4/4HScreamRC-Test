package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public class AutonomousDistanceSidewaysSegment extends AutonomousSegment {
    private double desiredEncoderTicks;
    private double sensorDist;

    private DcMotor dcmFRMotor;
    private DcMotor dcmFLMotor;
    private DcMotor dcmBRMotor;
    private DcMotor dcmBLMotor;

    private DistanceSensor snsDistanceLeft;
    private DistanceSensor snsDistanceRight;

    private double dblFrontLeftMotorValue;
    private double dblFrontRightMotorValue;
    private double dblBackLeftMotorValue;
    private double dblBackRightMotorValue;

    private double dblFrontLeftMotorPower;
    private double dblFrontRightMotorPower;
    private double dblBackLeftMotorPower;
    private double dblBackRightMotorPower;

    private static final double NORMAL_POWER = 0.75;
    private static final double REDUCED_POWER = 0.6;
    private double dblDesiredHeading = 0;
    private static final double HEADING_ERROR_RANGE = 3;
    private static final double CORRECTION_AGGRESSION = 0.07;
    static final double INCREMENT   = 0.0125;


    Orientation angles;
    BNO055IMU imu;

    private Telemetry telemetry;

    private boolean bolinitialized;
    private boolean boldistReached;


    public AutonomousDistanceSidewaysSegment(double distToTravelin, double sensorDist, double dblDesiredHeading, DcMotor dcmFLMotor, DcMotor dcmFRMotor, DcMotor dcmBLMotor, DcMotor dcmBRMotor, Telemetry telemetry, BNO055IMU imu, DistanceSensor snsDistanceLeft, DistanceSensor snsDistanceRight) {
        // add approptiate comment for conversions
        desiredEncoderTicks = distToTravelin * 68;

        this.sensorDist = sensorDist;

        this.imu = imu;

        this.telemetry = telemetry;

        this.dcmFLMotor = dcmFLMotor;
        this.dcmFRMotor = dcmFRMotor;
        this.dcmBLMotor = dcmBLMotor;
        this.dcmBRMotor = dcmBRMotor;

        this.dblDesiredHeading = dblDesiredHeading;

        this.snsDistanceLeft = snsDistanceLeft;
        this.snsDistanceRight = snsDistanceRight;

        bolinitialized = false;
        boldistReached = false;
    }

    private void init() {
        dcmFLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmFLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bolinitialized = true;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double dblHeading = angles.firstAngle;

        double dblHeadingDifference = dblHeading - dblDesiredHeading;

        double dblHeadingCorrection = dblHeadingDifference*CORRECTION_AGGRESSION;

        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.


        //left
        if (desiredEncoderTicks > 0) {

            if(dcmFLMotor.getCurrentPosition() < (desiredEncoderTicks/2)){
                dblFrontLeftMotorValue += INCREMENT;
                dblFrontRightMotorValue += INCREMENT;
                dblBackLeftMotorValue += INCREMENT;
                dblBackRightMotorValue += INCREMENT;
            } else if(dcmFLMotor.getCurrentPosition() > (desiredEncoderTicks/2)){
                dblFrontLeftMotorValue -= INCREMENT;
                dblFrontRightMotorValue -= INCREMENT;
                dblBackLeftMotorValue -= INCREMENT;
                dblBackRightMotorValue -= INCREMENT;
            }

            if (dblFrontLeftMotorValue < NORMAL_POWER) {
                dblFrontLeftMotorPower = dblFrontLeftMotorValue;
                dblFrontRightMotorPower = dblFrontRightMotorValue;
                dblBackLeftMotorPower = dblBackLeftMotorValue;
                dblBackRightMotorPower = dblBackRightMotorValue;
            }
            if (dblFrontLeftMotorValue >= NORMAL_POWER) {
                dblFrontLeftMotorPower = NORMAL_POWER;
                dblFrontRightMotorPower = NORMAL_POWER;
                dblBackLeftMotorPower = NORMAL_POWER;
                dblBackRightMotorPower = NORMAL_POWER;
            }

            if(dblHeading > dblDesiredHeading){
                dblFrontRightMotorPower -= (dblHeadingCorrection);
                dblFrontLeftMotorPower -= (dblHeadingCorrection);
            }else if(dblHeading < dblDesiredHeading){
                dblBackRightMotorPower -= (-dblHeadingCorrection);
                dblBackLeftMotorPower -= (-dblHeadingCorrection);
            }

            if (snsDistanceLeft.getDistance(DistanceUnit.INCH) < sensorDist){
                boldistReached = true;
            }

            telemetry.addLine();

            //right
        } else if (desiredEncoderTicks < 0) {

            if(dcmFLMotor.getCurrentPosition() > (desiredEncoderTicks/2)){
                dblFrontLeftMotorValue -= INCREMENT;
                dblFrontRightMotorValue -= INCREMENT;
                dblBackLeftMotorValue -= INCREMENT;
                dblBackRightMotorValue -= INCREMENT;
            } else if(dcmFLMotor.getCurrentPosition() < (desiredEncoderTicks/2)){
                dblFrontLeftMotorValue += INCREMENT;
                dblFrontRightMotorValue += INCREMENT;
                dblBackLeftMotorValue += INCREMENT;
                dblBackRightMotorValue += INCREMENT;
            }

            if (dblFrontLeftMotorValue > -NORMAL_POWER) {
                dblFrontLeftMotorPower = dblFrontLeftMotorValue;
                dblFrontRightMotorPower = dblFrontRightMotorValue;
                dblBackLeftMotorPower = dblBackLeftMotorValue;
                dblBackRightMotorPower = dblBackRightMotorValue;
            }
            if (dblFrontLeftMotorValue <= -NORMAL_POWER) {
                dblFrontLeftMotorPower = -NORMAL_POWER;
                dblFrontRightMotorPower = -NORMAL_POWER;
                dblBackLeftMotorPower = -NORMAL_POWER;
                dblBackRightMotorPower = -NORMAL_POWER;
            }

            if(dblHeading < dblDesiredHeading){
                dblFrontRightMotorPower -= (dblHeadingCorrection);
                dblFrontLeftMotorPower -= (dblHeadingCorrection);
            }else if(dblHeading > dblDesiredHeading){
                dblBackRightMotorPower -= (-dblHeadingCorrection);
                dblBackLeftMotorPower -= (-dblHeadingCorrection);
            }

            if (snsDistanceRight.getDistance(DistanceUnit.INCH) < sensorDist){
                boldistReached = true;
            }

        } else {

            dblFrontLeftMotorPower = 0;
            dblFrontRightMotorPower = 0;
            dblBackLeftMotorPower = 0;
            dblBackRightMotorPower = 0;
        }


        dcmFLMotor.setPower(dblFrontLeftMotorPower);
        dcmFRMotor.setPower(dblFrontRightMotorPower);
        dcmBLMotor.setPower(-dblBackLeftMotorPower);
        dcmBRMotor.setPower(-dblBackRightMotorPower);

        telemetry.addData("heading", dblHeading);
        telemetry.addData("heading correction", dblHeadingCorrection);
        telemetry.addData("distance", dcmFLMotor.getCurrentPosition());
        telemetry.addData("FLMotor Power", dcmFLMotor.getPower());
        telemetry.addData("FRMotor Power", dcmFRMotor.getPower());
        telemetry.addData("front right motor power", dblFrontRightMotorPower);
        telemetry.addData("front right motor value", dblFrontRightMotorValue);

        telemetry.update();

    }

        public boolean segmentComplete () {
            return boldistReached;
        }

}


