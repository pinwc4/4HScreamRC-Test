package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.robotcore.hardware.Servo;


public class AutonomousV4BSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private Servo srvV4B;

    private static final double GS_GRABBING_POSITION = 1;
    private static final double GS_RELEASE_POSITION = -1;

    private double dblGrabbingDirection;
    private boolean bolPowerSet = false;


    private boolean bolinitialized;


    public AutonomousV4BSegment(double dblGrabbingPosition, Servo srvV4B) {

        this.srvV4B = srvV4B;

        this.dblGrabbingDirection = dblGrabbingPosition;

        bolinitialized = false;
    }

    private void init() {
        bolinitialized = true;
    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        srvV4B.setPosition(dblGrabbingDirection);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(srvV4B.getPosition() == dblGrabbingDirection) {
            bolPowerSet = true;
        }

    }

    public boolean segmentComplete() {
        return bolPowerSet;
    }
}

