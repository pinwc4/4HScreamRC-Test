package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "SensorTest")


public class SensorTest extends OpMode {

    private double dblDistanceSense;
    private int intColorLevel;
    private ColorSensor snsColor1;
    private ColorSensor snsColor2;
    private boolean bolSDToggle;

    public void init(){

        snsColor1 = hardwareMap.get(ColorSensor.class, "color_sensor1");
        snsColor2 = hardwareMap.get(ColorSensor.class, "color_sensor2");

    }

    public void loop(){

        if (snsColor2 instanceof DistanceSensor) {
            dblDistanceSense = ((DistanceSensor) snsColor2).getDistance(DistanceUnit.CM);
        }

        if(dblDistanceSense < 3){

            if(snsColor2.red() > (snsColor2.blue())){
                intColorLevel = 1;
            } else if(snsColor2.blue() > (snsColor2.green())){
                intColorLevel = 3;
            } else {
                intColorLevel = 2;
            }

        } else{
            intColorLevel = 0;
        }




        telemetry.addData("Red", snsColor2.red());
        telemetry.addData("Green", snsColor2.green());
        telemetry.addData("Blue", snsColor2.blue());
        telemetry.addData("Distance", dblDistanceSense);

        telemetry.addData("ColorLevel", intColorLevel);

        telemetry.update();
    }
}

