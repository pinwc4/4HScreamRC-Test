package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorDigitalTouch;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PowerAttachment extends Object {


    private Gamepad gmpGamepad1;
    private Gamepad gmpGamepad2;
    private Telemetry telTelemetry;

    private DcMotorEx dcmSlider;

    private Servo srvGrabber;
    private Servo srvV4B;

    private SensorDigitalTouch tsLSGrabber;

    private DcMotorEx lteDirectionV4B;
    private DcMotorEx lteDirectionCHS;

    private int intNumSameRecognitions = 0;
    private int intNumSameRecognitions2 = 0;
    private int intNumSameRecognitions3 = 0;

    private static final double CENTERANGLE = 0.51;
    private static final double ANGLEMODIFIERLOW = 0.467;
    private static final double ANGLEMODIFIERHIGH = 0.175;
    private static final double ANGLEMODIFIERLOWEST = 0.451;
    private static final double ANGLEMODIFIERLOWSTACK = 0.37;
    private double dblV4BAngleHigh = 0.675; //0.3755
    private double dblV4BAngleLow = 0.043;
    private double dblV4BAngleLowStack = 0.13;
    private double dblV4BAngleLowest = 0.951;//0.9365

    private double dblSlidePosition;
    private int intSlidePosition = 0;

    private double dblServoPosition = 0.5;

    private int intConeStack1 = -115;
    private int intConeStack2 = -150;

    private boolean bolAWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;
    private boolean bolX2WasPressed = false;
    private boolean bolLBWasPressed = false;
    private boolean bolRB2WasPressed = false;
    private boolean bolRB1WasPressed = false;
    private boolean bolX1WasPressed = false;
    private boolean bolLB1WasPressed = false;
    private boolean bolDPadDownWasPressed = false;
    private boolean bolDPadUpWasPressed = false;

    private boolean bolGRB1Toggle = false;
    private boolean bolGRB2Toggle = false;
    private boolean bolGRB3Toggle = false;
    private boolean bolGRB4Toggle = false;

    private boolean bolSGRB1Toggle = false;
    private boolean bolSGRB2Toggle = false;
    private boolean bolSGRB3Toggle = false;


    private boolean bolRSBWasPressed = false;
    private boolean bolSideToggle = false;
    private boolean bolCLToggle = true;
    private boolean bolGMAToggle = false;
    private boolean bolGMBToggle = false;
    private boolean bolGMYToggle = false;
    private boolean bolRBToggle = false;
    private boolean bolTToggle = false;
    private boolean bolT2Toggle = false;
    private boolean bolT3Toggle = false;
    private boolean bolT4Toggle = false;
    private boolean bolSTToggle = false;
    private boolean bolSHToggle = false;
    private boolean bolLFToggle = false;
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
        srvGrabber.setPosition(1);


        lteDirectionV4B = hmpHardwareMap.get(DcMotorEx.class, "lteV4B");
        lteDirectionCHS = hmpHardwareMap.get(DcMotorEx.class, "lteCHS");


      //  lteDirection.setMode(DcMotorEx.);


        lteDirectionV4B.setPower(100);
        lteDirectionCHS.setPower(100);

    }




    public void moveAttachments() {


// FLIPPER
        // dcmFlipper.setPower(gmpGamepad2.right_stick_y/2);


// GRABBER

        if (gmpGamepad2.right_stick_button && !bolRSBWasPressed) {
            bolRSBWasPressed = true;
            bolSTToggle = !bolSTToggle;

        } else if (!gmpGamepad1.left_bumper && bolLB1WasPressed) {
            bolLB1WasPressed = false;
        }


        if (gmpGamepad1.right_bumper && !bolRB1WasPressed) {
            bolRB1WasPressed = true;
            bolCLToggle = !bolCLToggle;
            if (bolCLToggle) {

                if (bolSTToggle) {
                    intSlidePosition = intConeStack1;
                    dblServoPosition = dblV4BAngleLowStack;
                    bolSGRB1Toggle = true;
                } else {
                    intSlidePosition = -150;
                    dblServoPosition = dblV4BAngleLow;
                    bolGRB1Toggle = true;
                }

            } else {
                if (bolSTToggle) {
                    dblServoPosition = dblV4BAngleHigh;
                    srvGrabber.setPosition(0);//0.85
                    intConeStack1 = intConeStack1 + 52;
                } else {
                    dblServoPosition = dblV4BAngleHigh;
                    bolT3Toggle = true;
                }
            }
        } else if (!gmpGamepad1.right_bumper && bolRB1WasPressed) {
            bolRB1WasPressed = false;
        }

        if(bolT3Toggle){
            if(intNumSameRecognitions3 < 25){
                intNumSameRecognitions3++;
            }
            else {
                srvGrabber.setPosition(0);//0.85
                intNumSameRecognitions3 = 0;
                bolT4Toggle = true;
                bolT3Toggle = false;
            }
        }



        if(bolT4Toggle){
            if(intNumSameRecognitions3 < 25){
                intNumSameRecognitions3++;
            }
            else {
                dblServoPosition = CENTERANGLE;
                intNumSameRecognitions3 = 0;
                bolT4Toggle = false;
            }
        }




        // GROUND PICK UP CHAIN

        if(dcmSlider.getCurrentPosition() < -140 && bolGRB1Toggle){

            intSlidePosition = 0;
            bolGRB1Toggle = false;
            bolGRB2Toggle = true;
        }

        if(dcmSlider.getCurrentPosition() > -15 && bolGRB2Toggle){

            srvGrabber.setPosition(1);
            bolGRB2Toggle = false;
            bolGRB3Toggle = true;
        }

        if(dcmSlider.getCurrentPosition() > -3 && bolGRB3Toggle){

            intSlidePosition = -150;
            bolGRB3Toggle = false;
            bolGRB4Toggle = true;
        }

        if(dcmSlider.getCurrentPosition() < -120 && bolGRB4Toggle){

            dblServoPosition = CENTERANGLE;
            bolGRB4Toggle = false;

        }




        //STACK PICK UP CHAIN

        if(dcmSlider.getCurrentPosition() > intConeStack1 - 20 && bolSGRB1Toggle){

            srvGrabber.setPosition(1);
            bolSGRB1Toggle = false;
            bolSGRB2Toggle = true;

            bolT2Toggle = true;
        }

        if(bolT2Toggle){
            if(intNumSameRecognitions2 < 20){
                intNumSameRecognitions2++;
                telTelemetry.addLine("One");
            }
            else {
                intSlidePosition = -350;
                intNumSameRecognitions2 = 0;
                telTelemetry.addLine("Two");
                bolT2Toggle = false;
                bolSGRB2Toggle = true;
                dblServoPosition = CENTERANGLE;
            }
        }




        // STACK PICK-UP


        if (gmpGamepad2.x && !bolX2WasPressed) {
            bolX2WasPressed = true;
            bolSHToggle = !bolSHToggle;
            if (bolSHToggle) {

                intSlidePosition = -350;

                dblServoPosition = dblV4BAngleLowStack;

            } else {

                intSlidePosition = -150;

            }
        } else if (!gmpGamepad2.x && bolX2WasPressed) {
            bolX2WasPressed = false;
        }






        if (gmpGamepad2.dpad_up && bolDPadUpWasPressed == false){
            // bolDPadUpWasPressed = true;
        } if (bolDPadUpWasPressed == true && !gmpGamepad2.dpad_up) {
            intConeStack1 = intConeStack1 - 52;
            bolDPadUpWasPressed = false;
        }
        if (gmpGamepad2.dpad_down && bolDPadDownWasPressed == false){
            bolDPadDownWasPressed = true;
        } if (bolDPadDownWasPressed == true && !gmpGamepad2.dpad_down) {
            intConeStack1 = intConeStack1 + 52;
            bolDPadDownWasPressed = false;
        }




// CARRYING POSITION
        if (gmpGamepad2.right_bumper && !bolRB2WasPressed) {
            bolRB2WasPressed = true;
            bolRBToggle = !bolRBToggle;
            if (bolRBToggle) {
                intSlidePosition = -150;
                dblServoPosition = dblV4BAngleLow;
            } else{
                intSlidePosition = 0;
                dblServoPosition = dblV4BAngleLowest;
            }
        } else if (!gmpGamepad2.right_bumper && bolRB2WasPressed) {
            bolRB2WasPressed = false;
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

            if (bolCHS == false) {

                lteDirectionCHS.setPower(100);

            } else {

                lteDirectionCHS.setPower(-100);

            }




//   CHASSIS V4B SWITCH

        if (gmpGamepad2.left_bumper && !bolLBWasPressed) {
            bolLBWasPressed = true;
            bolSideToggle = !bolSideToggle;
            if (bolSideToggle) {
                dblV4BAngleHigh = CENTERANGLE - ANGLEMODIFIERHIGH;
                dblV4BAngleLow = CENTERANGLE + ANGLEMODIFIERLOW;
                dblV4BAngleLowStack = CENTERANGLE + ANGLEMODIFIERLOWSTACK;
                telTelemetry.addLine("NOT WIRE");
                lteDirectionV4B.setPower(100);
            } else {
                dblV4BAngleHigh = CENTERANGLE + ANGLEMODIFIERHIGH;
                dblV4BAngleLow = CENTERANGLE - ANGLEMODIFIERLOW;
                dblV4BAngleLowest = CENTERANGLE - ANGLEMODIFIERLOWEST;
                dblV4BAngleLowStack = CENTERANGLE - ANGLEMODIFIERLOWSTACK;
                telTelemetry.addLine("WIRE");
                lteDirectionV4B.setPower(-100);
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
                intSlidePosition = -80;
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
                intSlidePosition = -830;
                //srvV4B.setPosition(CENTERANGLE);
            } else {
                bolTToggle = true;

                if(bolSTToggle){
                    dblServoPosition = dblV4BAngleLowStack;
                } else {
                    dblServoPosition = dblV4BAngleLow;
                }
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

                if(bolSTToggle){
                    intSlidePosition = -150;
                }else {
                    intSlidePosition = -150;
                }
                    intNumSameRecognitions = 0;
                    telTelemetry.addLine("Two");
                    bolTToggle = false;
                    bolRBToggle = false;

            }
        }




        dcmSlider.setTargetPosition(intSlidePosition);
        dcmSlider.setPower(0.85);
        dcmSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        srvV4B.setPosition(dblServoPosition);


        /*

        if (dcmSlider.getCurrentPosition() < -50 && bolGMAToggle) {
            dblServoPosition = dblV4BAngleHigh;
        }

        if (dcmSlider.getCurrentPosition() < -20 && bolGMBToggle) {
            dblServoPosition = CENTERANGLE;

            if (dcmSlider.getCurrentPosition() < -300 && bolGMBToggle) {
                dblServoPosition = dblV4BAngleHigh;
            }
        }

        if (dcmSlider.getCurrentPosition() < -50 && bolGMYToggle) {
            dblServoPosition = CENTERANGLE;

            if (dcmSlider.getCurrentPosition() < -700 && bolGMYToggle) {
                dblServoPosition = dblV4BAngleHigh;
            }
        }

         */





        telTelemetry.addData("bolCHS", bolCHS);
        telTelemetry.addData("Slider", dcmSlider.getCurrentPosition());
        telTelemetry.addData("intSlider", intSlidePosition);
        telTelemetry.addData("Grabber", srvGrabber.getPosition());
        telTelemetry.addData("V4B", srvV4B.getPosition());
        }


}
