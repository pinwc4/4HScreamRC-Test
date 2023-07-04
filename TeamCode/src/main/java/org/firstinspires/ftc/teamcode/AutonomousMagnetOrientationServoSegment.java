package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.Servo;


public class AutonomousMagnetOrientationServoSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private Servo srvMOServo;

    private double dblServoPosition;
    private boolean bolPowerSet = false;


    private boolean bolinitialized;


    public AutonomousMagnetOrientationServoSegment(double dblServoPosition, Servo srvMOServo) {

        this.srvMOServo = srvMOServo;

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

        srvMOServo.setPosition(dblServoPosition);

        bolPowerSet = true;
    }

    public boolean segmentComplete() {
        return bolPowerSet;
    }
}

