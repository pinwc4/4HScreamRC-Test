package org.firstinspires.ftc.teamcode.auto;


import org.firstinspires.ftc.robotcore.external.Telemetry;


public class AutonomousWaitSegment extends AutonomousSegment {


    private boolean bolPowerSet = false;

    private long dblWaitParameter;

    private long runtime;

    private Telemetry telemetry;
    private double dblRuntime;
    private double dblStartTime = 0;

    private boolean bolinitialized;


    public AutonomousWaitSegment(long dblWaitParameter, Telemetry telemetry) {

        this.dblWaitParameter = dblWaitParameter;
        this.telemetry = telemetry;
        this.dblRuntime = dblRuntime;


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

        try {
            Thread.sleep(dblWaitParameter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            bolPowerSet = true;


     //   telemetry.addData("Position", srvLauncherServo.getPosition());

    }

    public boolean segmentComplete() {
        return bolPowerSet;
    }
}

