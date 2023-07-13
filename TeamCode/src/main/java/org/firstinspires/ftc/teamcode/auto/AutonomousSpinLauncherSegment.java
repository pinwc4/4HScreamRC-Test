package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class AutonomousSpinLauncherSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private DcMotor dcmLauncherMotor;

    private static final double GS_GRABBING_POSITION = 1;
    private static final double GS_RELEASE_POSITION = -1;

    private double dblLauncherVelocity;
    private boolean bolPowerSet = false;

    private Telemetry telemetry;

    private boolean bolinitialized;


    public AutonomousSpinLauncherSegment(double dblLauncherVelocity, DcMotor dcmLauncherMotor, Telemetry telemetry) {

        this.telemetry = telemetry;
        this.dcmLauncherMotor = dcmLauncherMotor;

        this.dblLauncherVelocity = dblLauncherVelocity;

        bolinitialized = false;
    }

    private void init() {
        bolinitialized = true;
    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        dcmLauncherMotor.setPower(dblLauncherVelocity);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            bolPowerSet =true;



        telemetry.addData("speeeed", dcmLauncherMotor.getPower());
        //telemetry.addData("speeed", dcmLauncherMotor.getVelocity());
    }

    public boolean segmentComplete() {
        return bolPowerSet;
    }
}

