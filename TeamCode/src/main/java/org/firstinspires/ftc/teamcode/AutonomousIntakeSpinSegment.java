package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class AutonomousIntakeSpinSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private DcMotor dcmFTMotor;
    private DcMotor dcmSTMotor;

    private double dblIntakePower;
    private boolean bolPowerSet = false;


    private boolean bolinitialized;


    public AutonomousIntakeSpinSegment(double dblIntakePower, DcMotor dcmFTMotor, DcMotor dcmSTMotor) {

        this.dcmFTMotor = dcmFTMotor;
        this.dcmSTMotor = dcmSTMotor;

        this.dblIntakePower = dblIntakePower;

        bolinitialized = false;
    }

    private void init() {
        bolinitialized = true;
    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        dcmFTMotor.setPower(-dblIntakePower);
        dcmSTMotor.setPower(dblIntakePower);

        bolPowerSet = true;
    }

    public boolean segmentComplete() {
        return bolPowerSet;
    }
}

