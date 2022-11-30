package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "SensorTest")


public class SensorTest extends OpMode {

    private double dblDistanceSense;
    private int intColorLevel;
    private ColorSensor snsColor;
    private boolean bolSDToggle;

    public void init(){

        snsColor = hardwareMap.get(ColorSensor.class, "sensor_color");

    }

    public void loop(){

        if (snsColor instanceof DistanceSensor) {
            dblDistanceSense = ((DistanceSensor) snsColor).getDistance(DistanceUnit.CM);
        }

        if(dblDistanceSense < 3){

            if(snsColor.red() > (snsColor.green())){
                intColorLevel = 1;
            } else if(snsColor.blue() > (snsColor.green())){
                intColorLevel = 3;
            } else {
                intColorLevel = 2;
            }

        } else{
            intColorLevel = 0;
        }




        telemetry.addData("Red", snsColor.red());
        telemetry.addData("Green", snsColor.green());
        telemetry.addData("Blue", snsColor.blue());
        telemetry.addData("Distance", dblDistanceSense);

        telemetry.addData("ColorLevel", intColorLevel);

        telemetry.update();
    }
}

