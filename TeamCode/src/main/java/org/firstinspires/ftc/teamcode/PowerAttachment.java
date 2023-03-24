package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class PowerAttachment extends Object {


    private Gamepad gmpGamepad1;
    private Gamepad gmpGamepad2;
    private Telemetry telTelemetry;

    private DcMotorEx dcmSlider;

    private Servo srvGrabber;
    private Servo srvV4B;
    private Servo srvConeRighter;
    private Servo srvWallSpacer;

    private TouchSensor digitalTouch;
    private DigitalChannel digitalTouchGRB;
    private ColorSensor snsDistanceStackLeft;
    private ColorSensor snsDistanceStackRight;

    private DcMotorEx lteDirectionV4B1;
    private DcMotorEx lteDirectionV4B2;
    private DcMotorEx lteDirectionCHS;

    private int intNumSameRecognitions = 0;
    private int intNumSameRecognitions2 = 0;
    private int intNumSameRecognitions3 = 0;
    private int intNumSameRecognitions5 = 0;

    private static final double CENTERANGLE = 0.5;
    private static final double ANGLEMODIFIERLOW = 0.467;
    private static final double ANGLEMODIFIERHIGH = 0.175;
    private static final double ANGLEMODIFIERLOWEST = 0.451;
    private static final double ANGLEMODIFIERLOWSTACK = 0.37;
    private double dblV4BAngleHigh = 0.675; //0.3755
    private double dblV4BAngleLow = 0.043;
    private double dblV4BAngleLowStack = 0.13;
    private double dblV4BAngleLowest = 0.951;//0.9365

    private double dblDistanceSensorLeft;
    private double dblDistanceSensorRight;

    private double dblSlidePosition;
    private int intSlidePosition = 0;

    private double dblServoPosition = 0.5;


    private int intConeStack1 = -115;
    private int intConeStack2 = -150;

    private boolean bolY1WasPressed = false;
    private boolean bolAWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolY2WasPressed = false;
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
    private boolean bolGRB5Toggle = false;

    private boolean bolSGRB1Toggle = false;
    private boolean bolSGRB2Toggle = false;
    private boolean bolSGRB3Toggle = false;


    private boolean bolResetToggle = false;
    private boolean bolSideToggle = false;
    private boolean bolCLToggle = false;
    private boolean bolGMAToggle = false;
    private boolean bolGMBToggle = false;
    private boolean bolGMYToggle = false;
    private boolean bolRBToggle = false;
    private boolean bolTToggle = false;
    private boolean bolT2Toggle = false;
    private boolean bolT3Toggle = false;
    private boolean bolT4Toggle = false;
    private boolean bolT5Toggle = false;
    private boolean bolSTToggle = false;
    private boolean bolSHToggle = false;
    private boolean bolLFToggle = false;
    private boolean bolCHS = false;
    private boolean bolLS2WasPressed = false;
    private boolean bolOutToggle = true;
    private int intZeroReference;
    //private boolean  bolDWNToggle = false;

    public PowerAttachment(Gamepad gmpGamepad1, Gamepad gmpGamepad2, HardwareMap hmpHardwareMap, Telemetry telTelemetry) {

        this.gmpGamepad1 = gmpGamepad1;
        this.gmpGamepad2 = gmpGamepad2;
        this.telTelemetry = telTelemetry;



        dcmSlider = hmpHardwareMap.get(DcMotorEx.class, "Slider");
        dcmSlider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSlider.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider.setDirection(DcMotor.Direction.FORWARD);

        srvConeRighter = hmpHardwareMap.servo.get("ConeRighter");
        srvConeRighter.setPosition(0);
        srvWallSpacer = hmpHardwareMap.servo.get("WallSpacer");
        srvWallSpacer.setPosition(0);


        srvV4B = hmpHardwareMap.servo.get("V4B");
        srvV4B.setPosition(CENTERANGLE);


        srvGrabber = hmpHardwareMap.servo.get("Grabber");
        srvGrabber.setPosition(0);


        lteDirectionV4B1 = hmpHardwareMap.get(DcMotorEx.class, "lteV4B1");
        lteDirectionV4B2 = hmpHardwareMap.get(DcMotorEx.class, "lteV4B2");
        lteDirectionCHS = hmpHardwareMap.get(DcMotorEx.class, "lteCHS");

        digitalTouchGRB = hmpHardwareMap.get(DigitalChannel.class, "sensor_digitalGRB");
        digitalTouchGRB.setMode(DigitalChannel.Mode.INPUT);

        digitalTouch = hmpHardwareMap.get(TouchSensor.class, "sensor_digital");

        snsDistanceStackLeft = hmpHardwareMap.get(ColorSensor.class, "DistanceLeft");
        snsDistanceStackRight = hmpHardwareMap.get(ColorSensor.class, "DistanceRight");

      //  lteDirection.setMode(DcMotorEx.);


        lteDirectionV4B1.setPower(-65);
        lteDirectionV4B2.setPower(-65);
        lteDirectionCHS.setPower(100);

    }




    public void moveAttachments() {

        //RESET
        if (gmpGamepad2.left_trigger > 0.75 & gmpGamepad2.right_trigger > 0.75){
                dblServoPosition = CENTERANGLE;
                bolRBToggle = false;
                bolSTToggle = false;
                bolGMAToggle = false;
                bolGMBToggle = false;
                bolGMYToggle = false;
                intSlidePosition = 0;
                bolResetToggle = true;
        }

        if (digitalTouch.isPressed() == true & bolResetToggle == true){
            intSlidePosition = dcmSlider.getCurrentPosition();
            dcmSlider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            intSlidePosition = -150;
            dblServoPosition = dblV4BAngleLow;
            bolResetToggle = false;
        }


// FLIPPER

        srvConeRighter.setPosition(gmpGamepad1.right_trigger);

/*
        if(bolSTToggle){
            srvWallSpacer.setPosition(0.67);
        }else {
            srvWallSpacer.setPosition(0);
        }
*/

        if (digitalTouch.isPressed()) {
            telTelemetry.addData("Digital Touch", "Is Pressed");
        } else {
            telTelemetry.addData("Digital Touch", "Is Not Pressed");
        }

        dblDistanceSensorLeft = ( (DistanceSensor) snsDistanceStackLeft).getDistance(DistanceUnit.CM);
        dblDistanceSensorRight = ( (DistanceSensor) snsDistanceStackRight).getDistance(DistanceUnit.CM);




// GRABBER



        if (gmpGamepad1.right_bumper && !bolRB1WasPressed) {
            bolRB1WasPressed = true;
            bolCLToggle = !bolCLToggle;
            if (bolCLToggle) {

                if (bolSTToggle) {
                    dblServoPosition = dblV4BAngleLowStack;
                    bolT5Toggle = true;
                } else {
                    intSlidePosition = -150;
                    dblServoPosition = dblV4BAngleLow;
                    bolGRB1Toggle = true;
                }

            } else {
                if (bolSTToggle) {

                        dblServoPosition = dblV4BAngleHigh;
                        bolT3Toggle = true;
                        intConeStack1 = intConeStack1 + 52;

                } else {
                    if(dblServoPosition == dblV4BAngleLowest){
                        srvGrabber.setPosition(0);
                    } else{
                        dblServoPosition = dblV4BAngleHigh;
                        bolT3Toggle = true;
                    }




                }
            }
        } else if (!gmpGamepad1.right_bumper && bolRB1WasPressed) {
            bolRB1WasPressed = false;
        }


        if(bolT5Toggle){
            if(intNumSameRecognitions5 < 20){
                intNumSameRecognitions5++;
            }
            else {

                intNumSameRecognitions5 = 0;
                bolT5Toggle = false;
                intSlidePosition = intConeStack1;
                bolSGRB1Toggle = true;
            }
        }


        if(bolT3Toggle){
            if(intNumSameRecognitions3 < 15){
                intNumSameRecognitions3++;
            }
            else {
                srvGrabber.setPosition(0);
                intNumSameRecognitions3 = 0;
                bolT3Toggle = false;
                bolT4Toggle = true;

            }
        }

        if(bolT4Toggle){
            if(intNumSameRecognitions3 < 15){
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

        if(dcmSlider.getCurrentPosition() > -5 && bolGRB3Toggle){

                intSlidePosition = -150;
                bolGRB3Toggle = false;
                bolGRB4Toggle = true;


        }

        if(dcmSlider.getCurrentPosition() < -85 && bolGRB4Toggle){ //-100

                dblServoPosition = CENTERANGLE;
                bolGRB4Toggle = false;

            }

            if(dcmSlider.getCurrentPosition() < -85 && bolGRB5Toggle){ //-100

                    srvGrabber.setPosition(0);
                    bolCLToggle = false;
                    dblServoPosition = dblV4BAngleLow;
                    bolGRB5Toggle = false;


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

                bolSTToggle = true;

            } else {

                intSlidePosition = -150;

                bolSTToggle = false;

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
                intSlidePosition = 0;
                dblServoPosition = dblV4BAngleLowest;
                //bolDWNToggle = true;
            } else{
                intSlidePosition = -150;
                dblServoPosition = dblV4BAngleLow;
                //bolDWNToggle = false;
            }
        } else if (!gmpGamepad2.right_bumper && bolRB2WasPressed) {
            bolRB2WasPressed = false;
        }

/*
        //MANUAL CONTROL
        if(gmpGamepad2.left_stick_y != 0) {
            dblSlidePosition = dblSlidePosition + gmpGamepad2.left_stick_y * 1.5;

            intSlidePosition = (int) dblSlidePosition;
        }

 */



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
                dblV4BAngleLowest = CENTERANGLE - ANGLEMODIFIERLOWEST;
                dblV4BAngleLowStack = CENTERANGLE + ANGLEMODIFIERLOWSTACK;
                telTelemetry.addLine("NOT WIRE");
                lteDirectionV4B1.setPower(65);
                lteDirectionV4B2.setPower(65);
            } else {
                dblV4BAngleHigh = CENTERANGLE + ANGLEMODIFIERHIGH;
                dblV4BAngleLow = CENTERANGLE - ANGLEMODIFIERLOW;
                dblV4BAngleLowest = CENTERANGLE + ANGLEMODIFIERLOWEST;
                dblV4BAngleLowStack = CENTERANGLE - ANGLEMODIFIERLOWSTACK;
                telTelemetry.addLine("WIRE");
                lteDirectionV4B1.setPower(-65);
                lteDirectionV4B2.setPower(-65);
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
                intSlidePosition = -100;
                if (bolOutToggle){
                    dblServoPosition = dblV4BAngleHigh;
                }else{
                    dblServoPosition = CENTERANGLE;
                }
            }else {
                bolTToggle = true;

                if(bolSTToggle){
                    dblServoPosition = dblV4BAngleLowStack;
                } else {
                    dblServoPosition = dblV4BAngleLow;
                }

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
                intSlidePosition = -470;
                if (bolOutToggle){
                    dblServoPosition = dblV4BAngleHigh;
                }else{
                    dblServoPosition = CENTERANGLE;
                }
            } else {
                bolTToggle = true;
                if(bolSTToggle){
                    dblServoPosition = dblV4BAngleLowStack;
                } else {
                    dblServoPosition = dblV4BAngleLow;
                }
            }

        } else if (!gmpGamepad2.b && bolBWasPressed) {
            bolBWasPressed = false;
        }

        if (gmpGamepad2.y && !bolY2WasPressed) {
            bolY2WasPressed = true;
            bolGMAToggle = false;
            bolGMBToggle =false;
            bolGMYToggle = !bolGMYToggle;
            if (bolGMYToggle) {
                intSlidePosition = -850;
                if (bolOutToggle){
                    dblServoPosition = dblV4BAngleHigh;
                }else{
                    dblServoPosition = CENTERANGLE;
                }
            } else {
                bolTToggle = true;

                if(bolSTToggle){
                    dblServoPosition = dblV4BAngleLowStack;
                } else {
                    dblServoPosition = dblV4BAngleLow;
                }
            }
        } else if (!gmpGamepad2.y && bolY2WasPressed) {
            bolY2WasPressed = false;
        }




        if (gmpGamepad1.y && !bolY1WasPressed){
            bolY1WasPressed = true;
            bolOutToggle = !bolOutToggle;
        }else if (!gmpGamepad1.y && bolY1WasPressed){
            bolY1WasPressed = false;
        }



        if(bolTToggle){
            if(intNumSameRecognitions < 15){
                intNumSameRecognitions++;
                telTelemetry.addLine("One");
            }
            else {

                if(bolSTToggle){
                    intSlidePosition = -350;
                }else {
                    intSlidePosition = -150;
                }
                    intNumSameRecognitions = 0;
                    telTelemetry.addLine("Two");
                    bolTToggle = false;
                    //bolRBToggle = false;

            }
        }




        dcmSlider.setTargetPosition(intSlidePosition);
        dcmSlider.setPower(0.65);
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


        if (digitalTouch.isPressed() == true && !bolLS2WasPressed) {
            bolLS2WasPressed = true;
            intZeroReference = dcmSlider.getCurrentPosition();
        } else if (digitalTouch.isPressed() == false && bolLS2WasPressed) {
            bolLS2WasPressed = false;
        }






        telTelemetry.addData("ZeroReference", intZeroReference);
        telTelemetry.addData("Slider", dcmSlider.getCurrentPosition());
        telTelemetry.addData("intSlider", intSlidePosition);
        telTelemetry.addData("Grabber", srvGrabber.getPosition());
        telTelemetry.addData("V4B", srvV4B.getPosition());
        telTelemetry.addData("Left", dblDistanceSensorLeft);
        telTelemetry.addData("Right", dblDistanceSensorRight);
        }


}
