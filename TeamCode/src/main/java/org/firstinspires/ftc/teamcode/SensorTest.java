package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "SensorTest")


public class SensorTest extends OpMode {

    private ColorSensor snsColor;
    private boolean bolSDToggle;

    public void init(){

        snsColor = hardwareMap.get(ColorSensor.class, "sensor_color");

    }

    public void loop(){

        if(snsColor.red() > (snsColor.green()/2)){
            bolSDToggle = true;
        } else {
            bolSDToggle = false;
        }

        telemetry.addData("Red", snsColor.red());
        telemetry.addData("Green", snsColor.green());
        telemetry.addData("Blue", snsColor.blue());

        telemetry.addData("Toggle", bolSDToggle);

        telemetry.update();
    }
}

