package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


public class AutonomousIntakeSpinSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private DcMotorEx dcmITMotor;

    private double dblIntakePower;
    private boolean bolPowerSet = false;


    private boolean bolinitialized;


    public AutonomousIntakeSpinSegment(double dblIntakePower, DcMotorEx dcmITMotor) {

        this.dcmITMotor = dcmITMotor;

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

        dcmITMotor.setPower(dblIntakePower);

        bolPowerSet = true;
    }

    public boolean segmentComplete() {
        return bolPowerSet;
    }
}

