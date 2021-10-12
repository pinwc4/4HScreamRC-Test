package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.Servo;


public class AutonomousGrabberSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private Servo srvGrabberServo;

    private static final double GS_GRABBING_POSITION = 1;
    private static final double GS_RELEASE_POSITION = -1;

    private double dblGrabbingDirection;
    private boolean bolPowerSet = false;


    private boolean bolinitialized;


    public AutonomousGrabberSegment(double dblGrabbingPosition, Servo srvGrabberServo) {

        this.srvGrabberServo = srvGrabberServo;

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

        srvGrabberServo.setPosition(dblGrabbingDirection);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(srvGrabberServo.getPosition() == dblGrabbingDirection) {
            bolPowerSet = true;
        }

    }

    public boolean segmentComplete() {
        return bolPowerSet;
    }
}

