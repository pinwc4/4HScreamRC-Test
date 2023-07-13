package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.robotcore.hardware.Servo;


public class AutonomousMagnetServoSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private Servo srvMSServo;

    private double dblServoPosition;
    private boolean bolPowerSet = false;


    private boolean bolinitialized;


    public AutonomousMagnetServoSegment(double dblServoPosition, Servo srvMSServo) {

        this.srvMSServo = srvMSServo;

        this.dblServoPosition = dblServoPosition;

        bolinitialized = false;
    }

    private void init() {
        bolinitialized = true;
    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }

        srvMSServo.setPosition(dblServoPosition);

        bolPowerSet = true;
    }

    public boolean segmentComplete() {
        return bolPowerSet;
    }
}

