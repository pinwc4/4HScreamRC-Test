package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PowerAttachment extends Object {


    private Gamepad gmpGamepad1;
    private Gamepad gmpGamepad2;
    private Telemetry telTelemetry;

    private DcMotorEx dcmSlider;

    private Servo srvGrabber;
    private Servo srvV4B;

    private int intNumSameRecognitions = 0;

    private static final int PICKUPHEIGHT = -200;
    private static final double GRABBERCLOSED = 0.65;
    private static final double CENTERANGLE = 0.5;
    private static final double ANGLEMODIFIERLOW = 0.4565;
    private static final double ANGLEMODIFIERHIGH = 0.1;
    private double dblV4BAngleHigh = 0.6; //0.3755
    private double dblV4BAngleLow = 0.0435; //0.9365

    private double dblSlidePosition;
    private int intSlidePosition = 0;

    private boolean bolAWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;
    private boolean bolXWasPressed = false;
    private boolean bolDPUWasPressed = false;
    private boolean bolRBWasPressed = false;

    private boolean bolGRB1Toggle = false;
    private boolean bolGRB2Toggle = false;
    private boolean bolGRB3Toggle = false;

    private boolean bolSideToggle = false;
    private boolean bolCLToggle = false;
    private boolean bolGMAToggle = false;
    private boolean bolGMBToggle = false;
    private boolean bolGMYToggle = false;
    private boolean bolRBToggle = false;
    private boolean bolTToggle = false;

    private boolean bolDRToggle = false;


    public PowerAttachment(Gamepad gmpGamepad1, Gamepad gmpGamepad2, HardwareMap hmpHardwareMap, Telemetry telTelemetry) {

        this.gmpGamepad1 = gmpGamepad1;
        this.gmpGamepad2 = gmpGamepad2;
        this.telTelemetry = telTelemetry;




        dcmSlider = hmpHardwareMap.get(DcMotorEx.class, "Slider");
        dcmSlider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSlider.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider.setDirection(DcMotor.Direction.FORWARD);



        srvV4B = hmpHardwareMap.servo.get("V4B");
        srvV4B.setPosition(0.5);


        srvGrabber = hmpHardwareMap.servo.get("Grabber");
        srvGrabber.setPosition(0.65);



    }




    public void moveAttachments() {


// FLIPPER
       // dcmFlipper.setPower(gmpGamepad2.right_stick_y/2);


// GRABBER

        if ((gmpGamepad2.x || gmpGamepad1.right_bumper)  && !bolXWasPressed) {
            bolXWasPressed = true;
            bolCLToggle = !bolCLToggle;
            if (bolCLToggle) {

                if(bolDRToggle = true){
                    bolGRB1Toggle = true;
                    bolDRToggle = false;
                }else{
                    intSlidePosition = -400;
                    srvV4B.setPosition(dblV4BAngleLow);

                    bolGRB1Toggle = true;
                }

            } else {
                srvGrabber.setPosition(0);//0.85
            }
        } else if ((!gmpGamepad2.x || !gmpGamepad1.right_bumper) && bolXWasPressed) {
            bolXWasPressed = false;
        }

        if(dcmSlider.getCurrentPosition() < PICKUPHEIGHT + 10 && bolGRB1Toggle){

            intSlidePosition = 0;
            bolGRB1Toggle = false;
            bolGRB2Toggle = true;
        }

        if(dcmSlider.getCurrentPosition() > -20 && bolGRB2Toggle){

            srvGrabber.setPosition(GRABBERCLOSED);
            bolGRB2Toggle = false;
            bolGRB3Toggle = true;
        }

        if(dcmSlider.getCurrentPosition() > -4 && bolGRB3Toggle){

            srvV4B.setPosition(CENTERANGLE);
            bolGRB3Toggle = false;
        }

/*
        if(intNumSameRecognitions < 300){

            intNumSameRecognitions++;
            telTelemetry.addLine("One");
        }
        else {
            intSlidePosition = 0;
            srvGrabber.setPosition(0.65);//0.6
            telTelemetry.addLine("Two");
        }

 */


// CARRYING POSITION
        if (gmpGamepad2.right_bumper && !bolRBWasPressed) {
            bolRBWasPressed = true;
            bolRBToggle = !bolRBToggle;
            if (bolRBToggle) {
                srvV4B.setPosition(CENTERANGLE);
            } else{
                srvV4B.setPosition(dblV4BAngleLow);
            }
        } else if (!gmpGamepad2.right_bumper && bolRBWasPressed) {
            bolRBWasPressed = false;
        }


       //MANUAL CONTROL
        if(gmpGamepad2.left_stick_y != 0) {
            dblSlidePosition = dblSlidePosition + gmpGamepad2.left_stick_y * 1.5;

            intSlidePosition = (int) dblSlidePosition;
        }


// HEADING SWITCH
        if (gmpGamepad2.dpad_up && !bolDPUWasPressed) {
            bolDPUWasPressed = true;
            bolSideToggle = !bolSideToggle;
            if (bolSideToggle) {
                dblV4BAngleHigh = CENTERANGLE - ANGLEMODIFIERHIGH;
                dblV4BAngleLow = CENTERANGLE + ANGLEMODIFIERLOW;
                telTelemetry.addLine("front");
            } else {
                dblV4BAngleHigh = CENTERANGLE + ANGLEMODIFIERHIGH;
                dblV4BAngleLow = CENTERANGLE - ANGLEMODIFIERLOW;
                telTelemetry.addLine("back");
            }
        } else if (!gmpGamepad2.dpad_up && bolDPUWasPressed) {
            bolDPUWasPressed = false;
        }


// V4B And Slides presets
        if (gmpGamepad2.a && !bolAWasPressed) {
            bolAWasPressed = true;
            bolGMAToggle = !bolGMAToggle;
            if (bolGMAToggle) {
                intSlidePosition = -150;
            }else {
                bolTToggle = true;
                srvV4B.setPosition(dblV4BAngleLow);
                bolDRToggle = true;
            }
        } else if (!gmpGamepad2.a && bolAWasPressed) {
            bolAWasPressed = false;
        }


        if (gmpGamepad2.b && !bolBWasPressed) {
            bolBWasPressed = true;
            bolGMBToggle = !bolGMBToggle;
            if (bolGMBToggle) {
                intSlidePosition = -380;
            } else {
                bolTToggle = true;
                srvV4B.setPosition(dblV4BAngleLow);
                bolDRToggle = true;
            }

        } else if (!gmpGamepad2.b && bolBWasPressed) {
            bolBWasPressed = false;
        }

        if (gmpGamepad2.y && !bolYWasPressed) {
            bolYWasPressed = true;
            bolGMYToggle = !bolGMYToggle;
            if (bolGMYToggle) {
                intSlidePosition = -725;
            } else {
                bolTToggle = true;
                srvV4B.setPosition(dblV4BAngleLow);
                bolDRToggle = true;
            }

        } else if (!gmpGamepad2.y && bolYWasPressed) {
            bolYWasPressed = false;
        }


        if(bolTToggle){
            if(intNumSameRecognitions < 15){

                intNumSameRecognitions++;
                telTelemetry.addLine("One");
            }
            else {
                intSlidePosition = PICKUPHEIGHT;
                intNumSameRecognitions = 0;
                telTelemetry.addLine("Two");
                bolTToggle = false;
            }
        }




        dcmSlider.setTargetPosition(intSlidePosition);
        dcmSlider.setPower(0.5);
        dcmSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        if (dcmSlider.getCurrentPosition() < -100 && bolGMAToggle) {
            srvV4B.setPosition(dblV4BAngleHigh);
        }
        if (dcmSlider.getCurrentPosition() < -300 && bolGMBToggle) {
            srvV4B.setPosition(dblV4BAngleHigh);
        }
        if (dcmSlider.getCurrentPosition() < -700 && bolGMYToggle) {
            srvV4B.setPosition(dblV4BAngleHigh);
        }



        telTelemetry.addData("Slider", dcmSlider.getCurrentPosition());
        telTelemetry.addData("intSlider", intSlidePosition);
        telTelemetry.addData("Grabber", srvGrabber.getPosition());
        telTelemetry.addData("V4B", srvV4B.getPosition());

    }


}
