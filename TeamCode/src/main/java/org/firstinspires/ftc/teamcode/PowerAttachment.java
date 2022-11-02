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

   // private DcMotor dcmFlipper;

    private DcMotorEx dcmSliderLeft;
    private DcMotorEx dcmSliderRight;

    // private Servo srvLeftClaw;

   // private Servo srvFlipper;

    private Servo srvGrabber;
    private Servo srvV4B;


    private double dblSlideSpeed;
    private int intSlideSpeed;

    private boolean bolAWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;
    private boolean bolXWasPressed = false;

    private boolean bolRBWasPressed = false;

    private boolean bolCLToggle = false;
    private boolean bolGMAToggle = false;
    private boolean bolGMBToggle = false;
    private boolean bolGMYToggle = false;
    private boolean bolRBToggle = false;

    public PowerAttachment(Gamepad gmpGamepad1, Gamepad gmpGamepad2, HardwareMap hmpHardwareMap, Telemetry telTelemetry) {

        this.gmpGamepad1 = gmpGamepad1;
        this.gmpGamepad2 = gmpGamepad2;
        this.telTelemetry = telTelemetry;


       // dcmFlipper = hmpHardwareMap.get(DcMotor.class, "Flipper");

        dcmSliderLeft = hmpHardwareMap.get(DcMotorEx.class, "SliderLeft");
        dcmSliderLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSliderLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSliderLeft.setDirection(DcMotor.Direction.FORWARD);

        dcmSliderRight = hmpHardwareMap.get(DcMotorEx.class, "SliderRight");
        dcmSliderRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSliderRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSliderRight.setDirection(DcMotor.Direction.FORWARD);


        srvGrabber = hmpHardwareMap.servo.get("Grabber");
        srvGrabber.setPosition(0);



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


       // dcmFlipper.setPower(gmpGamepad2.right_stick_y/2);

        if (gmpGamepad2.x && !bolXWasPressed) {
            bolXWasPressed = true;
            bolCLToggle = !bolCLToggle;
            if (bolCLToggle) {
                srvGrabber.setPosition(0);//0.6
            } else {
                srvGrabber.setPosition(0.65);//0.85
            }
        } else if (!gmpGamepad2.x && bolXWasPressed) {
            bolXWasPressed = false;
        }





        if (gmpGamepad2.right_bumper && !bolRBWasPressed) {
            bolRBWasPressed = true;
            bolRBToggle = !bolRBToggle;
            if (bolRBToggle) {
                srvV4B.setPosition(0.5);
            } else {
                srvV4B.setPosition(0.1);
            }
        } else if (!gmpGamepad2.right_bumper && bolRBWasPressed) {
            bolRBWasPressed = false;
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
                srvV4B.setPosition(0.1);

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
                srvV4B.setPosition(0.35);
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
                srvV4B.setPosition(0.35);
            } else {
                intSlideSpeed = 0;
            }

        } else if (!gmpGamepad2.y && bolYWasPressed) {
            bolYWasPressed = false;
        }

        dcmSliderLeft.setTargetPosition(intSlideSpeed);
        dcmSliderLeft.setPower(0.75);
        dcmSliderLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        dcmSliderRight.setTargetPosition(intSlideSpeed);
        dcmSliderRight.setPower(0.75);
        dcmSliderRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);





        //telTelemetry.addData("Slider", dcmSlider.getCurrentPosition());
        telTelemetry.addData("Grabber", srvGrabber);

    }


}
