package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class AutonomousShootLauncherSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private Servo srvLauncherServo;

    private static final double GS_GRABBING_POSITION = 1;
    private static final double GS_RELEASE_POSITION = -1;

    private double dblShootLauncher;
    private boolean bolPowerSet = false;

    private long dblWaitParameter;

    private long runtime;

    private Telemetry telemetry;
    private double dblRuntime;
    private double dblStartTime = 0;

    private boolean bolinitialized;


    public AutonomousShootLauncherSegment(double dblShootLauncher, long dblWaitParameter, Servo srvLauncherServo, Telemetry telemetry) {

        this.dblWaitParameter = dblWaitParameter;
        this.telemetry = telemetry;
        this.srvLauncherServo = srvLauncherServo;
        this.dblRuntime = dblRuntime;

        this.dblShootLauncher = dblShootLauncher;

        bolinitialized = false;
    }

    private void init() {
        bolinitialized = true;
    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        if(dblStartTime == 0){
            dblStartTime = dblRuntime;
        }

        srvLauncherServo.setPosition(dblShootLauncher);

        try {
            Thread.sleep(dblWaitParameter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            bolPowerSet = true;


        telemetry.addData("Position", srvLauncherServo.getPosition());

    }

    public boolean segmentComplete() {
        return bolPowerSet;
    }
}

