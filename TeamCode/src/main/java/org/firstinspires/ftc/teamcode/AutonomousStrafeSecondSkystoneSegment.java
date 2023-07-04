package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public class AutonomousStrafeSecondSkystoneSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private DcMotor dcmFLMotor;
    private DcMotor dcmFRMotor;
    private DcMotor dcmBRMotor;
    private DcMotor dcmBLMotor;

    private double dblFrontLeftMotorPower;
    private double dblFrontRightMotorPower;
    private double dblBackLeftMotorPower;
    private double dblBackRightMotorPower;

    private static final double NORMAL_POWER = 0.5;
    private static final double REDUCED_POWER = 0.35;
    private double dblDesiredHeading = 0;
    private static final double HEADING_ERROR_RANGE = 3;

    Orientation angles;

    private MeasuredDistance msdMeasuredDistance = null;

    private Telemetry telemetry;

    BNO055IMU imu;

    private boolean bolinitialized;
    private boolean boldistReached;
    private double disttoTravelin;


    public AutonomousStrafeSecondSkystoneSegment(double distToTravelin, double dblDesiredHeading, DcMotor dcmFLMotor, DcMotor dcmFRMotor, DcMotor dcmBLMotor, DcMotor dcmBRMotor, Telemetry telemetry, BNO055IMU imu) {
        //add appropriate comment for conversions
        desiredEncoderTicks = (distToTravelin * 60);

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

    public AutonomousStrafeSecondSkystoneSegment(double distToTravelin, double dblDesiredHeading, DcMotor dcmFLMotor, DcMotor dcmFRMotor, DcMotor dcmBLMotor, DcMotor dcmBRMotor, Telemetry telemetry, MeasuredDistance msdMeasuredDistance, BNO055IMU imu) {

        this.imu = imu;

        this.disttoTravelin = distToTravelin;
        desiredEncoderTicks = (distToTravelin * 60);

        this.dcmFLMotor = dcmFLMotor;
        this.dcmFRMotor = dcmFRMotor;
        this.dcmBLMotor = dcmBLMotor;
        this.dcmBRMotor = dcmBRMotor;
        this.telemetry = telemetry;
        this.msdMeasuredDistance = msdMeasuredDistance;

        this.dblDesiredHeading = dblDesiredHeading;

    }
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

        if (dcmFLMotor.getCurrentPosition() > (-desiredEncoderTicks + 50)){

            dblFrontLeftMotorPower = NORMAL_POWER;
            dblFrontRightMotorPower = NORMAL_POWER;
            dblBackLeftMotorPower = NORMAL_POWER;
            dblBackRightMotorPower = NORMAL_POWER;

            if(dblHeading < (dblDesiredHeading - HEADING_ERROR_RANGE)){
                dblFrontLeftMotorPower = REDUCED_POWER;
                dblBackLeftMotorPower = REDUCED_POWER;
            }else if(dblHeading > (dblDesiredHeading + HEADING_ERROR_RANGE)){
                dblFrontRightMotorPower = REDUCED_POWER;
                dblBackRightMotorPower = REDUCED_POWER;
            }

            telemetry.addLine();

        } else if(dcmFLMotor.getCurrentPosition() < (-desiredEncoderTicks - 50)){

            dblFrontLeftMotorPower = -NORMAL_POWER;
            dblFrontRightMotorPower = -NORMAL_POWER;
            dblBackLeftMotorPower = -NORMAL_POWER;
            dblBackRightMotorPower = -NORMAL_POWER;

            if(dblHeading < (dblDesiredHeading - HEADING_ERROR_RANGE)){
                dblFrontRightMotorPower = -REDUCED_POWER;
                dblBackRightMotorPower = -REDUCED_POWER;
            }else if(dblHeading > (dblDesiredHeading + HEADING_ERROR_RANGE)){
                dblFrontLeftMotorPower = -REDUCED_POWER;
                dblBackLeftMotorPower = -REDUCED_POWER;
            }

        }else{

            dblFrontLeftMotorPower = 0;
            dblFrontRightMotorPower = 0;
            dblBackLeftMotorPower = 0;
            dblBackRightMotorPower = 0;

            boldistReached = true;
        }

        dcmFLMotor.setPower(dblFrontLeftMotorPower);
        dcmFRMotor.setPower(-dblFrontRightMotorPower);
        dcmBLMotor.setPower(dblBackLeftMotorPower);
        dcmBRMotor.setPower(-dblBackRightMotorPower);

        telemetry.addData("first", dblHeading);
        telemetry.addData("second", angles.secondAngle);
        telemetry.addData("third", angles.thirdAngle);

        telemetry.update();
    }

    public boolean segmentComplete() {
        return boldistReached;
    }
}

