package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "SlideAndOdometryManualControl")


public class SlideAndOdometryManualControl extends OpMode {

    private DcMotor dcmSlider;
    private Servo srvV4B;
    private Servo srvOdometryPod1;
    private Servo srvOdometryPod2;
    private Servo srvOdometryPod3;

    private boolean bolYWasPressed = false;
    private boolean bolXWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYToggle = true;
    private boolean bolXToggle = true;
    private boolean bolBToggle = true;

    public void init() {



        srvOdometryPod1 = hardwareMap.servo.get("OdometryPod1");
        srvOdometryPod2 = hardwareMap.servo.get("OdometryPod2");
        srvOdometryPod3 = hardwareMap.servo.get("OdometryPod3");
        srvV4B = hardwareMap.servo.get("V4B");

        dcmSlider = hardwareMap.get(DcMotorEx.class, "Slider");
        dcmSlider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSlider.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider.setDirection(DcMotor.Direction.FORWARD);

    }

    public void loop(){

        dcmSlider.setPower(gamepad2.right_stick_y/2);

        if (gamepad2.right_bumper){
            srvV4B.setPosition(0.51);
        }

        if (gamepad1.y && !bolYWasPressed) {
            bolYWasPressed = true;
            bolYToggle = !bolYToggle;
            if (bolYToggle) {
                srvOdometryPod1.setPosition(0);
            } else {
                srvOdometryPod1.setPosition(1);
            }
        } else if (!gamepad1.y && bolYWasPressed) {
            bolYWasPressed = false;
            srvOdometryPod1.setPosition(0.5);
        }


        if (gamepad1.x && !bolXWasPressed) {
            bolXWasPressed = true;
            bolXToggle = !bolXToggle;
            if (bolXToggle) {
                srvOdometryPod2.setPosition(0);
            } else {
                srvOdometryPod2.setPosition(1);
            }
        } else if (!gamepad1.x && bolXWasPressed) {
            bolXWasPressed = false;
            srvOdometryPod2.setPosition(0.5);
        }


        if (gamepad1.b && !bolBWasPressed) {
            bolBWasPressed = true;
            bolBToggle = !bolBToggle;
            if (bolBToggle) {
                srvOdometryPod3.setPosition(0);
            } else {
                srvOdometryPod3.setPosition(1);
            }
        } else if (!gamepad1.b && bolBWasPressed) {
            bolBWasPressed = false;
            srvOdometryPod3.setPosition(0.5);
        }

        telemetry.update();
    }
}

