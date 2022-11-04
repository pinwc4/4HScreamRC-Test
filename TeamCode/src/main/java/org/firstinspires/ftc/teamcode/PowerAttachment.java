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

    private DcMotorEx dcmSlider;
    private DcMotorEx dcmSliderRight;

    // private Servo srvLeftClaw;

   // private Servo srvFlipper;

    private Servo srvGrabber;
    private Servo srvV4B;

    private static final double CENTERANGLE = 0.5;
    private double dblAngleModifierLow = 0.437;
    private double dblAngleModifierHigh = 0.165;
    private double dblVR4BAngleHigh = 0.165;
    private double dblVR4BAngleLow = 0.165;

    private double dblSlideSpeed;
    private int intSlideSpeed;

    private boolean bolAWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;
    private boolean bolXWasPressed = false;
    private boolean bolDPUWasPressed = false;
    private boolean bolRBWasPressed = false;

    private boolean bolSideToggle = false;
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

        dcmSlider = hmpHardwareMap.get(DcMotorEx.class, "Slider");
        dcmSlider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSlider.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider.setDirection(DcMotor.Direction.FORWARD);


        srvV4B = hmpHardwareMap.servo.get("V4B");
        srvV4B.setPosition(0);


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

// FLIPPER
       // dcmFlipper.setPower(gmpGamepad2.right_stick_y/2);



;
// GRABBER
        if (gmpGamepad2.x && !bolXWasPressed) {
            bolXWasPressed = true;
            bolCLToggle = !bolCLToggle;
            if (bolCLToggle) {
                intSlideSpeed = -50;
                srvV4B.setPosition(dblVR4BAngleLow);

                if(intSlideSpeed == -50){
                    intSlideSpeed = 0;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    srvGrabber.setPosition(0.65);//0.6
                }

            } else {
                srvGrabber.setPosition(0);//0.85
            }
        } else if (!gmpGamepad2.x && bolXWasPressed) {
            bolXWasPressed = false;
        }




// CARRYING POSITION
        if (gmpGamepad2.right_bumper && !bolRBWasPressed) {
            bolRBWasPressed = true;
            bolRBToggle = !bolRBToggle;
            if (bolRBToggle) {
                srvV4B.setPosition(CENTERANGLE);
            } else{
                srvV4B.setPosition(dblVR4BAngleLow);
            }
        } else if (!gmpGamepad2.right_bumper && bolRBWasPressed) {
            bolRBWasPressed = false;
        }


       //MANUAL CONTROL
        if(gmpGamepad2.left_stick_y != 0) {
            dblSlideSpeed = dblSlideSpeed + gmpGamepad2.left_stick_y * 1.5;

            intSlideSpeed = (int) dblSlideSpeed;
        }


// HEADING SWITCH
        if (gmpGamepad2.dpad_up && !bolDPUWasPressed) {
            bolDPUWasPressed = true;
            bolSideToggle = !bolSideToggle;
            if (bolSideToggle) {
                dblVR4BAngleHigh = CENTERANGLE + dblAngleModifierHigh;
                dblVR4BAngleLow = CENTERANGLE + dblAngleModifierLow;
                telTelemetry.addLine("front");
            } else {
                dblVR4BAngleHigh = CENTERANGLE - dblAngleModifierHigh;
                dblVR4BAngleLow = CENTERANGLE - dblAngleModifierLow;
                telTelemetry.addLine("back");
            }
        } else if (!gmpGamepad2.dpad_up && bolDPUWasPressed) {
            bolDPUWasPressed = false;
        }



// ARM PRESETS
        if (gmpGamepad2.a && !bolAWasPressed) {
            bolAWasPressed = true;
            bolGMAToggle = !bolGMAToggle;
            if (bolGMAToggle) {
                intSlideSpeed = -200;
            }else {
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

        dcmSlider.setTargetPosition(intSlideSpeed);
        dcmSlider.setPower(0.75);
        dcmSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);







        //telTelemetry.addData("Slider", dcmSlider.getCurrentPosition());
        telTelemetry.addData("Grabber", srvGrabber);

    }


}
