package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutonomousAttachments extends Object {

    private Servo srvGrabber;

    public AutonomousAttachments(Servo srvGrabber, HardwareMap hmpHardwareMap, Telemetry telTelemetry) {

        this.srvGrabber = srvGrabber;

    }

    public void grabberGrab(){

        srvGrabber.setPosition(1);

    }

    public void grabberOpen(){

        srvGrabber.setPosition(0);

    }

}
