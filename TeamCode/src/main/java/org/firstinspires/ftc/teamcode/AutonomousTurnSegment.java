package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class AutonomousTurnSegment extends AutonomousSegment {
    private double dblDesiredEncoderTicks;

    private DcMotor dcmLeftMotor;
    private DcMotor dcmRightMotor;
    private Telemetry tel;

    private boolean bolInitialized;
    private boolean bolDistReached;


    public AutonomousTurnSegment(double dblTurnDist, DcMotor dcmLeftMotor, DcMotor dcmRightMotor, Telemetry tel) {
        dblDesiredEncoderTicks = dblTurnDist;

        this.dcmLeftMotor = dcmLeftMotor;
        this.dcmRightMotor = dcmRightMotor;
        this.tel = tel;

        bolInitialized = false;
        bolDistReached = false;
    }

    private void init() {
        dcmLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bolInitialized = true;
    }

    public void runSegment() {
        if (!bolInitialized || bolDistReached) {
            init();
        }


        if (dcmLeftMotor.getCurrentPosition() > (-dblDesiredEncoderTicks) + 10 && !bolDistReached) {
            dcmLeftMotor.setPower(0.75);
            dcmRightMotor.setPower(0.75);

        } else if (dcmLeftMotor.getCurrentPosition() < (-dblDesiredEncoderTicks) - 10 && !bolDistReached) {
            dcmLeftMotor.setPower(-0.75);
            dcmRightMotor.setPower(-0.75);
        } else {
            bolDistReached = true;
            dcmLeftMotor.setPower(0);
            dcmRightMotor.setPower(0);
        }

        if (tel != null) {
            tel.addData("DESIRED ENCODER TICKS", dblDesiredEncoderTicks);
            tel.addData("LEFT MOTOR ENCODER POSITION", dcmLeftMotor.getCurrentPosition());
            tel.addData("LEFT MOTOR POWER", dcmLeftMotor.getPower());
            tel.addData("RIGHT MOTOR POWER", dcmRightMotor.getPower());
            tel.addData("DISTANCE REACHED?", bolDistReached);
            tel.update();
        }
    }

    public boolean segmentComplete() {
        return bolDistReached;
    }
}
