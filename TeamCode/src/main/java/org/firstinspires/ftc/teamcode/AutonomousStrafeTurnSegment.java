package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public class AutonomousStrafeTurnSegment extends AutonomousSegment {
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
    private static final double TURN_POWER = 0.15;
    private static final double REDUCED_POWER = 0.35;
    private double dblDesiredHeading = 0;
    private static final double HEADING_ERROR_RANGE = 3;

    Orientation angles;

    private MeasuredDistance msdMeasuredDistance = null;

    private Telemetry telemetry;

    BNO055IMU imu;

    private boolean bolinitialized;
    private boolean bolHeadingReached = false;
    private double disttoTravelin;


    public AutonomousStrafeTurnSegment(double dblDesiredHeading, DcMotor dcmFLMotor, DcMotor dcmFRMotor, DcMotor dcmBLMotor, DcMotor dcmBRMotor, Telemetry telemetry, BNO055IMU imu) {

        this.dblDesiredHeading = dblDesiredHeading;

        this.telemetry = telemetry;

        this.imu = imu;

        this.dcmFLMotor = dcmFLMotor;
        this.dcmFRMotor = dcmFRMotor;
        this.dcmBLMotor = dcmBLMotor;
        this.dcmBRMotor = dcmBRMotor;

        bolinitialized = false;

    }

    private void init() {
        dcmFLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmFLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if (msdMeasuredDistance != null){
            desiredEncoderTicks = desiredEncoderTicks + msdMeasuredDistance.getDistance();
        }

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        bolHeadingReached = false;

        bolinitialized = true;

    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double dblHeading = angles.firstAngle;

        dblFrontLeftMotorPower = NORMAL_POWER;
        dblFrontRightMotorPower = NORMAL_POWER;
        dblBackLeftMotorPower = NORMAL_POWER;
        dblBackRightMotorPower = NORMAL_POWER;

        //right turn
        if(dblHeading > (dblDesiredHeading + 5)){
            dblFrontLeftMotorPower = TURN_POWER;
            dblBackLeftMotorPower = TURN_POWER;
        }
        //left turn
        else if(dblHeading < (dblDesiredHeading - 5)){
            dblFrontRightMotorPower = TURN_POWER;
            dblBackRightMotorPower = TURN_POWER;
        }
        else{
            bolHeadingReached = true;
        }

        telemetry.addLine();


        dcmFLMotor.setPower(-dblFrontLeftMotorPower);
        dcmFRMotor.setPower(dblFrontRightMotorPower);
        dcmBLMotor.setPower(-dblBackLeftMotorPower);
        dcmBRMotor.setPower(dblBackRightMotorPower);

        telemetry.addData("first", dblHeading);
        telemetry.update();
    }

    public boolean segmentComplete() {
        return bolHeadingReached;
    }
}

