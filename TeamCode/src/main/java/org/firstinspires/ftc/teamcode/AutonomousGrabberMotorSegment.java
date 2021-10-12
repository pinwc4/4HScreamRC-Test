package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public class AutonomousGrabberMotorSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private DcMotor dcmGrabberMotor;


    private double dblGrabberMotorPower;

    private static final double NORMAL_POWER = 0.75;

    private static final double TICKSTOINCHES = 14.6;

    private Telemetry telemetry;

    private boolean bolinitialized;
    private boolean boldistReached;
    private double disttoTravelin;


    public AutonomousGrabberMotorSegment(double distToTravelin, DcMotor dcmGrabberMotor, Telemetry telemetry) {
        //add appropriate comment for conversions
        this.disttoTravelin = distToTravelin;

        desiredEncoderTicks = (distToTravelin * TICKSTOINCHES);

        this.telemetry = telemetry;

        this.dcmGrabberMotor = dcmGrabberMotor;


        bolinitialized = false;
        boldistReached = false;
    }

    private void init() {
        dcmGrabberMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmGrabberMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        bolinitialized = true;

    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }


        if (dcmGrabberMotor.getCurrentPosition() < (desiredEncoderTicks - 50)){

            dblGrabberMotorPower = NORMAL_POWER;


        } else if(dcmGrabberMotor.getCurrentPosition() > (desiredEncoderTicks + 50)){

            dblGrabberMotorPower = -NORMAL_POWER;

        }else{

            dblGrabberMotorPower = 0;

            boldistReached = true;
        }

        dcmGrabberMotor.setPower(-dblGrabberMotorPower);

        telemetry.addData("front left motor position", dcmGrabberMotor.getCurrentPosition());
        telemetry.addData("front left motor", dblGrabberMotorPower);

        telemetry.update();
    }

    public boolean segmentComplete() {
        return boldistReached;
    }
}

