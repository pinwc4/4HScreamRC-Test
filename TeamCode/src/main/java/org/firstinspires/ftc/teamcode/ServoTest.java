package org.firstinspires.ftc.teamcode;

import android.hardware.Sensor;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "ServoTest")


public class ServoTest extends OpMode {

    private DcMotor dcmBenElven;

    private int intPreston;

    private double dblOwen;

    private boolean bolZach = true;


    public void init() {


    }

    public void loop() {

        if(gamepad1.left_stick_y == 1){
            dcmBenElven.setPower(1);
        }
        else{
            dcmBenElven.setPower(0);
        }



    }
}

