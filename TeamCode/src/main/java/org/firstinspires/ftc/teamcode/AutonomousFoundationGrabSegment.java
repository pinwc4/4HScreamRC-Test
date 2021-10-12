package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


public class AutonomousFoundationGrabSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private Servo srvLFGServo;
    private Servo srvRFGServo;

    private static final double LFG_GRABBING_POSITION = 1;
    private static final double RFG_GRABBING_POSITION = -1;

    private double dblGrabbingDirection;
    private boolean bolPowerSet = false;


    private boolean bolinitialized;


    public AutonomousFoundationGrabSegment(double dblGrabbingDirection, Servo srvLFGServo, Servo srvRFGServo) {

        this.srvLFGServo = srvLFGServo;
        this.srvRFGServo = srvRFGServo;

        this.dblGrabbingDirection = dblGrabbingDirection;

        bolinitialized = false;
    }

    private void init() {
        bolinitialized = true;
    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        if(dblGrabbingDirection > 0) {
            srvLFGServo.setPosition(LFG_GRABBING_POSITION);
            srvRFGServo.setPosition(RFG_GRABBING_POSITION);

            bolPowerSet = true;
        }
        if(dblGrabbingDirection < 0) {
            srvLFGServo.setPosition(-0.8);
            srvRFGServo.setPosition(0.8);

            bolPowerSet = true;
        }
    }

    public boolean segmentComplete() {
        return bolPowerSet;
    }
}

