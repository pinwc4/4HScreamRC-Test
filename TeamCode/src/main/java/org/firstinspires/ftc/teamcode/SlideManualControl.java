package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "SlideManualControl")


public class SlideManualControl extends OpMode {

    private DcMotor dcmSlider;
    private Servo srvV4B;

    public void init() {

        srvV4B = hardwareMap.servo.get("V4B");

        dcmSlider = hardwareMap.get(DcMotorEx.class, "Slider");
        dcmSlider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSlider.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider.setDirection(DcMotor.Direction.FORWARD);

    }

    public void loop(){

        dcmSlider.setPower(-gamepad2.right_stick_y/8);

        if (gamepad2.right_bumper){
            srvV4B.setPosition(0.51);
        }
        telemetry.update();
    }
}

