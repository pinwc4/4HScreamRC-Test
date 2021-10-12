package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class RedWhiteLEDsTest extends OpMode {
    private Servo lights;

    public void init(){
        lights = hardwareMap.servo.get("RedWhite");
    }

    public void loop(){
        //if (lights){

        //}



    }

}
