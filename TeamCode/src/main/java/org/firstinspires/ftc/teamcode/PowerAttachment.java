package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PowerAttachment extends Object {


    private Gamepad gmpGamepad1;
    private Gamepad gmpGamepad2;
    private Telemetry telTelemetry;

    private DcMotor dcmFlipper;

    private DcMotorEx dcmSlider;

    private Servo srvLeftClaw;
    private Servo srvRightClaw;

    private double dblSlideSpeed;
    private int intSlideSpeed;

    private boolean bolAWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;
    private boolean bolXWasPressed = false;

    private boolean bolCLToggle = false;
    private boolean bolGMAToggle = false;
    private boolean bolGMBToggle = false;
    private boolean bolGMYToggle = false;


    public PowerAttachment(Gamepad gmpGamepad1, Gamepad gmpGamepad2, HardwareMap hmpHardwareMap, Telemetry telTelemetry) {

        this.gmpGamepad1 = gmpGamepad1;
        this.gmpGamepad2 = gmpGamepad2;
        this.telTelemetry = telTelemetry;

        dcmFlipper = hmpHardwareMap.get(DcMotor.class, "Flipper");

        dcmSlider = hmpHardwareMap.get(DcMotorEx.class, "Slider");
        dcmSlider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSlider.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider.setDirection(DcMotor.Direction.FORWARD);

        srvLeftClaw = hmpHardwareMap.servo.get("LeftClaw");
        srvLeftClaw.setPosition(1);

        srvRightClaw = hmpHardwareMap.servo.get("RightClaw");
        srvRightClaw.setPosition(0);



    }

    public static enum PIDModes {
        Relaxed(new PIDFCoefficients(10 , 0.1f, 0f, 0.2f));

        final PIDFCoefficients PIDValues;

        PIDModes (PIDFCoefficients PIDValues){
            this.PIDValues = PIDValues;
        }
        public PIDFCoefficients getPID(){
            return PIDValues;
        }
    }




    public void moveAttachments() {

        dcmFlipper.setPower(gmpGamepad2.right_stick_y/2);

        if (gmpGamepad2.x && !bolXWasPressed) {
            bolXWasPressed = true;
            bolCLToggle = !bolCLToggle;
            if (bolCLToggle) {
                srvLeftClaw.setPosition(1);//0.15
                srvRightClaw.setPosition(0);//0.15
            } else {
                srvLeftClaw.setPosition(0.8);//0.85
                srvRightClaw.setPosition(0.2);//0.85
            }
        } else if (!gmpGamepad2.x && bolXWasPressed) {
            bolXWasPressed = false;
        }


        if(gmpGamepad2.left_stick_y != 0) {
            dblSlideSpeed = dblSlideSpeed + gmpGamepad2.left_stick_y * 1.5;

            intSlideSpeed = (int) dblSlideSpeed;
        }



        if (gmpGamepad2.a && !bolAWasPressed) {
            bolAWasPressed = true;
            bolGMAToggle = !bolGMAToggle;
            if (bolGMAToggle) {
                intSlideSpeed = -250;

            } else {
                intSlideSpeed = 0;
            }

        } else if (!gmpGamepad2.a && bolAWasPressed) {
            bolAWasPressed = false;
        }

        if (gmpGamepad2.b && !bolBWasPressed) {
            bolBWasPressed = true;
            bolGMBToggle = !bolGMBToggle;
            if (bolGMBToggle) {
                intSlideSpeed = -380;

            } else {
                intSlideSpeed = 0;
            }

        } else if (!gmpGamepad2.b && bolBWasPressed) {
            bolBWasPressed = false;
        }

        if (gmpGamepad2.y && !bolYWasPressed) {
            bolYWasPressed = true;
            bolGMYToggle = !bolGMYToggle;
            if (bolGMYToggle) {
                intSlideSpeed = -620;

            } else {
                intSlideSpeed = 0;
            }

        } else if (!gmpGamepad2.y && bolYWasPressed) {
            bolYWasPressed = false;
        }


        dcmSlider.setTargetPosition(intSlideSpeed);
        dcmSlider.setPower(0.5);
        dcmSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telTelemetry.addData("Slider", dcmSlider.getCurrentPosition());
        telTelemetry.addData("LeftClaw", srvLeftClaw);
        telTelemetry.addData("RightClaw", srvRightClaw);

    }


}
