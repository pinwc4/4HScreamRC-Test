package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
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

    private DigitalChannel lteDirectionC1;
    private DigitalChannel lteDirectionC2;
    private DigitalChannel lteDirectionC3;
    private DigitalChannel lteDirectionC4;

    private int intNumSameRecognitions = 0;

    private static final double CENTERANGLE = 0.5;
    private static final double ANGLEMODIFIERLOW = 0.452;
    private static final double ANGLEMODIFIERHIGH = 0.15;
    private static final double ANGLEMODIFIERLOWEST = 0.451;
    private double dblV4BAngleHigh = 0.65; //0.3755
    private double dblV4BAngleLow = 0.048;
    private double dblV4BAngleLowest = 0.951;//0.9365

    private double dblSlidePosition;
    private int intSlidePosition = 0;

    private double dblServoPosition = 0.5;

    private boolean bolAWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;
    private boolean bolX2WasPressed = false;
    private boolean bolLBWasPressed = false;
    private boolean bolRBWasPressed = false;
    private boolean bolX1WasPressed = false;

    private boolean bolGRB1Toggle = false;
    private boolean bolGRB2Toggle = false;
    private boolean bolGRB3Toggle = false;
    private boolean bolHToggle = false;

    private boolean bolSideToggle = false;
    private boolean bolCLToggle = true;
    private boolean bolGMAToggle = false;
    private boolean bolGMBToggle = false;
    private boolean bolGMYToggle = false;
    private boolean bolRBToggle = false;
    private boolean bolTToggle = false;
    private boolean bolCHS = false;

    public PowerAttachment(Gamepad gmpGamepad1, Gamepad gmpGamepad2, HardwareMap hmpHardwareMap, Telemetry telTelemetry) {

        this.gmpGamepad1 = gmpGamepad1;
        this.gmpGamepad2 = gmpGamepad2;
        this.telTelemetry = telTelemetry;




        dcmSlider = hmpHardwareMap.get(DcMotorEx.class, "Slider");
        dcmSlider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSlider.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider.setDirection(DcMotor.Direction.FORWARD);



        srvV4B = hmpHardwareMap.servo.get("V4B");
        srvV4B.setPosition(CENTERANGLE);


        srvGrabber = hmpHardwareMap.servo.get("Grabber");
        srvGrabber.setPosition(0.4);

        lteDirectionC1 = hmpHardwareMap.get(DigitalChannel.class, "green1");
        lteDirectionC2 = hmpHardwareMap.get(DigitalChannel.class, "red1");
        lteDirectionC3 = hmpHardwareMap.get(DigitalChannel.class, "green2");
        lteDirectionC4 = hmpHardwareMap.get(DigitalChannel.class, "red2");

        lteDirectionC1.setMode(DigitalChannel.Mode.OUTPUT);
        lteDirectionC2.setMode(DigitalChannel.Mode.OUTPUT);
        lteDirectionC3.setMode(DigitalChannel.Mode.OUTPUT);
        lteDirectionC4.setMode(DigitalChannel.Mode.OUTPUT);

        lteDirectionC1.setState(false);
        lteDirectionC4.setState(false);
        lteDirectionC2.setState(true);
        lteDirectionC3.setState(true);

    }




    public void moveAttachments() {


// FLIPPER
        // dcmFlipper.setPower(gmpGamepad2.right_stick_y/2);


// GRABBER

        if (gmpGamepad1.right_bumper && !bolX2WasPressed) {
            bolX2WasPressed = true;
            bolCLToggle = !bolCLToggle;
            if (bolCLToggle) {

                intSlidePosition = -150;
                dblServoPosition = dblV4BAngleLow;

                bolGRB1Toggle = true;

            } else {
                srvGrabber.setPosition(0);//0.85
            }
        } else if (!gmpGamepad1.right_bumper && bolX2WasPressed) {
            bolX2WasPressed = false;
        }

        if(dcmSlider.getCurrentPosition() < -140 && bolGRB1Toggle){

            intSlidePosition = 0;
            bolGRB1Toggle = false;
            bolGRB2Toggle = true;
        }

        if(dcmSlider.getCurrentPosition() > -20 && bolGRB2Toggle){

            srvGrabber.setPosition(0.4);
            bolGRB2Toggle = false;
            bolGRB3Toggle = true;
        }

        if(dcmSlider.getCurrentPosition() > -2 && bolGRB3Toggle){

            intSlidePosition = -150;
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
                intSlidePosition = -150;
                dblServoPosition = CENTERANGLE;
            } else{
                intSlidePosition = 0;
                dblServoPosition = dblV4BAngleLowest;
            }
        } else if (!gmpGamepad2.right_bumper && bolRBWasPressed) {
            bolRBWasPressed = false;
        }


        //MANUAL CONTROL
        if(gmpGamepad2.left_stick_y != 0) {
            dblSlidePosition = dblSlidePosition + gmpGamepad2.left_stick_y * 1.5;

            intSlidePosition = (int) dblSlidePosition;
        }



//   CHASSIS HEADING SWITCH

        if (gmpGamepad1.x && !bolX1WasPressed) {
            bolX1WasPressed = true;
            bolCHS = !bolCHS;
        } else if (!gmpGamepad1.x) {
            bolX1WasPressed = false;
        }
/*
            if (bolCHS == false) {
                lteDirectionC1.setState(true);
                lteDirectionC4.setState(true);
                lteDirectionC2.setState(false);
                lteDirectionC3.setState(false);
            } else {
                lteDirectionC2.setState(true);
                lteDirectionC3.setState(true);
                lteDirectionC1.setState(false);
                lteDirectionC4.setState(false);
            }
 */



//   CHASSIS V4B SWITCH

        if (gmpGamepad2.left_bumper && !bolLBWasPressed) {
            bolLBWasPressed = true;
            bolSideToggle = !bolSideToggle;
            if (bolSideToggle) {
                dblV4BAngleHigh = CENTERANGLE - ANGLEMODIFIERHIGH;
                dblV4BAngleLow = CENTERANGLE + ANGLEMODIFIERLOW;
                dblV4BAngleLowest = CENTERANGLE + ANGLEMODIFIERLOWEST;
                telTelemetry.addLine("front");
                lteDirectionC1.setState(true);
                lteDirectionC4.setState(true);
                lteDirectionC2.setState(false);
                lteDirectionC3.setState(false);
            } else {
                dblV4BAngleHigh = CENTERANGLE + ANGLEMODIFIERHIGH;
                dblV4BAngleLow = CENTERANGLE - ANGLEMODIFIERLOW;
                dblV4BAngleLowest = CENTERANGLE - ANGLEMODIFIERLOWEST;
                telTelemetry.addLine("back");
                lteDirectionC1.setState(false);
                lteDirectionC4.setState(false);
                lteDirectionC2.setState(true);
                lteDirectionC3.setState(true);
            }
        } else if (!gmpGamepad2.left_bumper && bolLBWasPressed) {
            bolLBWasPressed = false;
        }


// ARM PRESETS
        if (gmpGamepad2.a && !bolAWasPressed) {
            bolAWasPressed = true;
            bolGMBToggle = false;
            bolGMYToggle = false;
            bolGMAToggle = !bolGMAToggle;
            if (bolGMAToggle) {
                intSlidePosition = -150;
                //srvV4B.setPosition(CENTERANGLE);
            }else {
                bolTToggle = true;
                dblServoPosition = dblV4BAngleLow;
            }
        } else if (!gmpGamepad2.a && bolAWasPressed) {
            bolAWasPressed = false;
        }


        if (gmpGamepad2.b && !bolBWasPressed) {
            bolBWasPressed = true;
            bolGMAToggle = false;
            bolGMYToggle = false;
            bolGMBToggle = !bolGMBToggle;
            if (bolGMBToggle) {
                intSlidePosition = -450;
                //srvV4B.setPosition(CENTERANGLE);
            } else {
                bolTToggle = true;
                dblServoPosition = dblV4BAngleLow;
            }

        } else if (!gmpGamepad2.b && bolBWasPressed) {
            bolBWasPressed = false;
        }

        if (gmpGamepad2.y && !bolYWasPressed) {
            bolYWasPressed = true;
            bolGMAToggle = false;
            bolGMBToggle =false;
            bolGMYToggle = !bolGMYToggle;
            if (bolGMYToggle) {
                intSlidePosition = -900;
                //srvV4B.setPosition(CENTERANGLE);
            } else {
                bolTToggle = true;
                dblServoPosition = dblV4BAngleLow;
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
                intSlidePosition = -150;
                intNumSameRecognitions = 0;
                telTelemetry.addLine("Two");
                bolTToggle = false;
                bolRBToggle = false;
            }
        }




        dcmSlider.setTargetPosition(intSlidePosition);
        dcmSlider.setPower(0.65);
        dcmSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        srvV4B.setPosition(dblServoPosition);


        if (dcmSlider.getCurrentPosition() < -100 && bolGMAToggle) {
            dblServoPosition = dblV4BAngleHigh;
        }
        if (dcmSlider.getCurrentPosition() < -300 && bolGMBToggle) {
            dblServoPosition = dblV4BAngleHigh;
        }
        if (dcmSlider.getCurrentPosition() < -750 && bolGMYToggle) {
            dblServoPosition = dblV4BAngleHigh;
        }





        telTelemetry.addData("bolCHS", bolCHS);
        telTelemetry.addData("Slider", dcmSlider.getCurrentPosition());
        telTelemetry.addData("intSlider", intSlidePosition);
        telTelemetry.addData("Grabber", srvGrabber.getPosition());
        telTelemetry.addData("V4B", srvV4B.getPosition());
        }


}
