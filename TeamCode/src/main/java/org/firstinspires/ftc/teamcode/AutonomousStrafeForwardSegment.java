package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public class AutonomousStrafeForwardSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private DcMotor dcmFLMotor;
    private DcMotor dcmFRMotor;
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

    private static final double NORMAL_POWER = 0.8;
    private static final double REDUCED_POWER = 0.525;
    private double dblDesiredHeading = 0;
    private static final double CORRECTION_AGGRESSION = 0.05;
    static final double INCREMENT   = 0.02;

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


    public AutonomousStrafeForwardSegment(double distToTravelin, double dblDesiredHeading, DcMotor dcmFLMotor, DcMotor dcmFRMotor, DcMotor dcmBLMotor, DcMotor dcmBRMotor, Telemetry telemetry, BNO055IMU imu) {
        //add appropriate comment for conversions
        desiredEncoderTicks = (distToTravelin * TICKSTOINCHES);

        this.telemetry = telemetry;

        this.imu = imu;

        this.dcmFLMotor = dcmFLMotor;
        this.dcmFRMotor = dcmFRMotor;
        this.dcmBLMotor = dcmBLMotor;
        this.dcmBRMotor = dcmBRMotor;

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
        dcmFRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmBLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmBRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmFLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmFRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmBLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmBRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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

        double dblMotorPosition = (-dcmFLMotor.getCurrentPosition() + dcmFRMotor.getCurrentPosition() + -dcmBLMotor.getCurrentPosition() + dcmBRMotor.getCurrentPosition())/4;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double dblHeading = angles.firstAngle;

        double dblHeadingDifference = dblHeading - dblDesiredHeading;

        double dblHeadingCorrection = dblHeadingDifference*CORRECTION_AGGRESSION;

        double dblRampDown = desiredEncoderTicks * 0.2945;

        if (dblMotorPosition < (desiredEncoderTicks) && desiredEncoderTicks > 0){

            telemetry.addLine("forwards");

            if(dblMotorPosition < ((desiredEncoderTicks - dblRampDown)/2) && bolRampUp){
                dblFrontLeftMotorValue += INCREMENT;
                dblFrontRightMotorValue += INCREMENT;
                dblBackLeftMotorValue += INCREMENT;
                dblBackRightMotorValue += INCREMENT;
            } else if(dblMotorPosition > ((desiredEncoderTicks - dblRampDown)/2)){
                dblFrontLeftMotorValue -= INCREMENT;
                dblFrontRightMotorValue -= INCREMENT;
                dblBackLeftMotorValue -= INCREMENT;
                dblBackRightMotorValue -= INCREMENT;

                if(dblFrontLeftMotorValue < 0 || dblFrontLeftMotorValue == 0){
                    dblFrontLeftMotorPower = 0;
                    dblFrontRightMotorPower = 0;
                    dblBackLeftMotorPower = 0;
                    dblBackRightMotorPower = 0;
                    boldistReached = true;
                }

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
                dblFrontLeftMotorPower += (dblHeadingCorrection);
                dblBackLeftMotorPower += (dblHeadingCorrection);
            }else if(dblHeading < dblDesiredHeading){
                dblFrontRightMotorPower += (-dblHeadingCorrection);
                dblBackRightMotorPower += (-dblHeadingCorrection);
            }


            telemetry.addLine();

        } else if(dblMotorPosition > (desiredEncoderTicks) && desiredEncoderTicks < 0){

            telemetry.addLine("backwards");

            if(dblMotorPosition > ((desiredEncoderTicks + dblRampDown)/2)){
                dblFrontLeftMotorValue -= INCREMENT;
                dblFrontRightMotorValue -= INCREMENT;
                dblBackLeftMotorValue -= INCREMENT;
                dblBackRightMotorValue -= INCREMENT;
            } else if(dblMotorPosition < ((desiredEncoderTicks + dblRampDown)/2)){
                dblFrontLeftMotorValue += INCREMENT;
                dblFrontRightMotorValue += INCREMENT;
                dblBackLeftMotorValue += INCREMENT;
                dblBackRightMotorValue += INCREMENT;

                if(dblFrontLeftMotorValue < 0 || dblFrontLeftMotorValue == 0){
                    dblFrontLeftMotorPower = 0;
                    dblFrontRightMotorPower = 0;
                    dblBackLeftMotorPower = 0;
                    dblBackRightMotorPower = 0;

                    boldistReached = true;
                }
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
                dblFrontLeftMotorPower += (dblHeadingCorrection);
                dblBackLeftMotorPower += (dblHeadingCorrection);
            }else if(dblHeading > dblDesiredHeading){
                dblFrontRightMotorPower += (-dblHeadingCorrection);
                dblBackRightMotorPower += (-dblHeadingCorrection);
            }
/*
            if(dblHeading < dblDesiredHeading){
                dblFrontRightMotorValue = -(NORMAL_POWER + (-dblHeadingCorrection));
                dblBackRightMotorValue = -(NORMAL_POWER + (-dblHeadingCorrection));
            }else if(dblHeading > dblDesiredHeading){
                dblFrontLeftMotorValue = -(NORMAL_POWER + (dblHeadingCorrection));
                dblBackLeftMotorValue = -(NORMAL_POWER + (dblHeadingCorrection));
            }
*/

        }else{

            dblFrontLeftMotorPower = 0;
            dblFrontRightMotorPower = 0;
            dblBackLeftMotorPower = 0;
            dblBackRightMotorPower = 0;

            boldistReached = true;
        }

        //1358.29

        dcmFLMotor.setPower(-dblFrontLeftMotorPower);
        dcmFRMotor.setPower(dblFrontRightMotorPower);
        dcmBLMotor.setPower(-dblBackLeftMotorPower);
        dcmBRMotor.setPower(dblBackRightMotorPower);

        //telemetry.addData("first",angles.firstAngle);
        //telemetry.addData("HEADING", dblDesiredHeading);
        telemetry.addData("position", dblMotorPosition);

        telemetry.addData("FL", dcmFLMotor.getCurrentPosition());
        telemetry.addData("FR", dcmFRMotor.getCurrentPosition());
        telemetry.addData("BL", dcmBLMotor.getCurrentPosition());
        telemetry.addData("BR", dcmBRMotor.getCurrentPosition());

        /*
        telemetry.addData("Value", dblFrontLeftMotorValue);
        telemetry.addData("Power", dblFrontLeftMotorPower);
        telemetry.addData("Motor Power", dcmFLMotor.getPower());

        telemetry.addData("Desired", desiredEncoderTicks);
        telemetry.addData("RD", dblRampDown);

         */

        telemetry.update();
    }

    public boolean segmentComplete() {
        return boldistReached;
    }
}

