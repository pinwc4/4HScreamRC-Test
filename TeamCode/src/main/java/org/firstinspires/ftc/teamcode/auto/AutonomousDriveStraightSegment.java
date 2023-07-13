package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class AutonomousDriveStraightSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private DcMotor dcmLeftMotor;
    private DcMotor dcmRightMotor;
    private Telemetry telemetry;

    private boolean bolinitialized;
    private boolean boldistReached;


    public AutonomousDriveStraightSegment(double distToTravelcm, DcMotor leftSide, DcMotor rightSide, Telemetry telemetry) {
        // TODO: Convert this to a named constant
        // 19.18 variable is the amount of encoder ticks per cm
        //Encoder ticks per revolution = 28
        //Gear ratio = 13.7:1
        //Circumference of wheel = 20mm
        //28 * 13.7 = 383.6 / 20 = 19.18
        desiredEncoderTicks = distToTravelcm * 19.18;

        dcmLeftMotor = leftSide;
        dcmRightMotor = rightSide;
        this.telemetry = telemetry;

        bolinitialized = false;
        boldistReached = false;
    }

    private void init() {
        dcmLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bolinitialized = true;
    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        if (dcmLeftMotor.getCurrentPosition() > -desiredEncoderTicks && !boldistReached) {
            dcmLeftMotor.setPower(0.5);
            dcmRightMotor.setPower(-0.5);
        } else {
            boldistReached = true;
            dcmLeftMotor.setPower(0);
            dcmRightMotor.setPower(0);
        }

        if (telemetry != null) {
            telemetry.addData("DESIRED ENCODER TICKS", desiredEncoderTicks);
            telemetry.addData("LEFT MOTOR ENCODER POSITION", dcmLeftMotor.getCurrentPosition());
            telemetry.addData("LEFT MOTOR POWER", dcmRightMotor.getPower());
            telemetry.addData("RIGHT MOTOR POWER", dcmRightMotor.getPower());
            telemetry.addData("DISTANCE REACHED?", boldistReached);
            telemetry.update();
        }
    }

    public boolean segmentComplete() {
        return boldistReached;
    }
}

