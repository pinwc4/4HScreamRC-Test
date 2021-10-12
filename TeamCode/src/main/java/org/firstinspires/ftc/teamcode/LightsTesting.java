package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "LightsTest")

public class LightsTesting extends OpMode {


    private LEDpattarn ledLedPattern;


    @Override
    public void init() {

        ledLedPattern = new LEDpattarn(gamepad1, hardwareMap, telemetry);

    }

    @Override
    public void loop() {

        ledLedPattern.lights(getRuntime());

    }
}


