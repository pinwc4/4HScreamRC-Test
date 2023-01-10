package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class RoadRunner extends OpMode {

    private Servo srvGrabber;

    public AutonomousAttachments atcAutonomousAttachments;

    public void init(){

        srvGrabber.setPosition(0);

        atcAutonomousAttachments = new AutonomousAttachments(srvGrabber, hardwareMap, telemetry);
    }

    public void loop(){

        atcAutonomousAttachments.grabberGrab();

    }

}
