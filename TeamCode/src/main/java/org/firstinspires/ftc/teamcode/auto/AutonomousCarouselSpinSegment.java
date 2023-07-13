package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.robotcore.hardware.CRServo;


public class AutonomousCarouselSpinSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private CRServo dcmCSMotor;

    private double dblIntakePower;
    private boolean bolPowerSet = false;


    private boolean bolinitialized;


    public AutonomousCarouselSpinSegment(double dblIntakePower, CRServo dcmCSMotor) {

        this.dcmCSMotor = dcmCSMotor;

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

        dcmCSMotor.setPower(-dblIntakePower);

        bolPowerSet = true;
    }

    public boolean segmentComplete() {
        return bolPowerSet;
    }
}

