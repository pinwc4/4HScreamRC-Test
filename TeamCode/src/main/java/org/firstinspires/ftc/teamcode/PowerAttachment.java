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
    private Servo srvOdometryPod1;
    private Servo srvOdometryPod2;
    private Servo srvOdometryPod3;

    private TouchSensor digitalTouchRS;
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
    private int intNumSameRecognitions6 = 0;
    private int intNumSameRecognitions7 = 0;
    private int intNumSameRecognitions8 = 0;
    private int intNumSameRecognitions9 = 0;
    private int intNumSameRecognitions10 = 0;
    private int intNumSameRecognitions11 = 0;


    private static final double CENTERANGLE = 0.5;
    private static final double ANGLEMODIFIERLOW = 1;//;
    private static final double ANGLEMODIFIERHIGH = 0.175;
    private static final double ANGLEMODIFIERLOWEST = 0.451;
    private static final double ANGLEMODIFIERLOWSTACK = 0.42;
    private double dblV4BAngleHigh = 0.675; //0.3755
    private double dblV4BAngleLow = 0;//0.035;
    private double dblV4BAngleLowStack = 0.08;
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
    private boolean bolRSWasPressed = false;
    private boolean bolLSGRBWasPressed = false;
    private boolean bolA1WasPressed = false;

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
    private boolean bolT6Toggle = false;
    private boolean bolSTToggle = false;
    private boolean bolSHToggle = false;
    private boolean bolLFToggle = false;
    private boolean bolCHS = false;
    private boolean bolLS2WasPressed = false;
    private boolean bolOutToggle = false;
    private boolean bolConeToggle = true;
    private boolean bolGrabToggle = false;
    private boolean bolGrabbingToggle = false;
    private boolean bolDropToggle = false;
    private boolean bolDropingToggle = false;
    private boolean bolDropingToggle2 = false;
    private boolean bolLSGrabbingToggle = true;
    private boolean bolA1Toggle = true;
    private int intZeroReference;

    private String strStateTest = "init";
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

        srvOdometryPod1 = hmpHardwareMap.servo.get("OdometryPod1");
        srvOdometryPod2 = hmpHardwareMap.servo.get("OdometryPod2");
        srvOdometryPod3 = hmpHardwareMap.servo.get("OdometryPod3");

        lteDirectionV4B1 = hmpHardwareMap.get(DcMotorEx.class, "lteV4B1");
        lteDirectionV4B2 = hmpHardwareMap.get(DcMotorEx.class, "lteV4B2");
        lteDirectionCHS = hmpHardwareMap.get(DcMotorEx.class, "lteCHS");

        digitalTouchGRB = hmpHardwareMap.get(DigitalChannel.class, "sensor_digitalGRB");
        digitalTouchGRB.setMode(DigitalChannel.Mode.INPUT);

        digitalTouchRS = hmpHardwareMap.get(TouchSensor.class, "sensor_digital");

        snsDistanceStackLeft = hmpHardwareMap.get(ColorSensor.class, "DistanceLeft");
        snsDistanceStackRight = hmpHardwareMap.get(ColorSensor.class, "DistanceRight");

      //  lteDirection.setMode(DcMotorEx.);


        lteDirectionV4B1.setPower(-65);
        lteDirectionV4B2.setPower(-65);
        lteDirectionCHS.setPower(100);

    }




    public void moveAttachments() {

        //RESET
        if (gmpGamepad2.left_trigger > 0.75 & gmpGamepad2.right_trigger > 0.75) {
            dblServoPosition = CENTERANGLE;
            bolRBToggle = false;
            bolSTToggle = false;
            bolGMAToggle = false;
            bolGMBToggle = false;
            bolGMYToggle = false;
            intSlidePosition = 17;
            bolResetToggle = true;
        }

        if (digitalTouchRS.isPressed() == true & bolResetToggle == true) {
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
        if (digitalTouchGRB.getState()) {
            telTelemetry.addData("digitalTouchGRB", "Is Not Pressed");
        } else {
            telTelemetry.addData("digitalTouchGRB", "Is Pressed");
        }

        if (digitalTouchRS.isPressed()) {
            telTelemetry.addData("Digital Touch", "Is Pressed");
        } else {
            telTelemetry.addData("Digital Touch", "Is Not Pressed");
        }

        dblDistanceSensorLeft = ((DistanceSensor) snsDistanceStackLeft).getDistance(DistanceUnit.CM);
        dblDistanceSensorRight = ((DistanceSensor) snsDistanceStackRight).getDistance(DistanceUnit.CM);


// GRABBER
        //CONCEPT LIMIT SWITCH GRABBING

        /*
        digitalTouchGRB.getState() == true; NOT ACTIVATED
        digitalTouchRS.isPressed() == false; NOT ACTIVATED
        srvGrabber.setPosition(0); NOT ACTIVATED
         */



        if (digitalTouchGRB.getState() == false) {
            if (intNumSameRecognitions10 < 10) {
                intNumSameRecognitions10++;
                lteDirectionV4B1.setPower(0);
                lteDirectionV4B2.setPower(0);
            } else {
                if (bolSideToggle) {
                    lteDirectionV4B1.setPower(65);
                    lteDirectionV4B2.setPower(65);
                } else {
                    lteDirectionV4B1.setPower(-65);
                    lteDirectionV4B2.setPower(-65);
                }
            }
        }

        if (bolLSGrabbingToggle) {

            if (gmpGamepad1.right_bumper && !bolRB1WasPressed) {
                bolRB1WasPressed = true;
                strStateTest = "pressed";
                if (digitalTouchGRB.getState() == true) {
                    bolGrabToggle = true;
                    bolDropToggle = false;
                    strStateTest = "grab toggle";
                } else {
                    bolDropToggle = true;
                    bolGrabToggle = false;
                    strStateTest = "drop toggle";
                }
            } else if (!gmpGamepad1.right_bumper && bolRB1WasPressed) {
                bolRB1WasPressed = false;
            }

            if (bolGrabToggle) {
                if (bolSTToggle) {
                    dblServoPosition = dblV4BAngleLowStack;
                } else {
                    dblServoPosition = dblV4BAngleLow;
                }
                intSlidePosition = 17;
            }

            if (bolGrabToggle && digitalTouchGRB.getState() == false) {
                if (intNumSameRecognitions9 < 8) {
                    intNumSameRecognitions9++;
                } else {
                    intNumSameRecognitions9 = 0;
                    srvGrabber.setPosition(1);
                    bolGrabbingToggle = true;
                }
            }

            if (bolGrabbingToggle) {
                if (intNumSameRecognitions6 < 5) {
                    intNumSameRecognitions6++;
                } else {
                    intNumSameRecognitions6 = 0;
                    if (bolSTToggle) {
                        intSlidePosition = -350;
                    } else {
                        intSlidePosition = -160;
                    }
                    dblServoPosition = CENTERANGLE;
                    bolGrabToggle = false;
                    bolGrabbingToggle = false;
                }
            }

            if (digitalTouchGRB.getState() == true && digitalTouchRS.isPressed() == true && !bolRSWasPressed && bolGrabToggle) {
                bolRSWasPressed = true;
                if (bolSTToggle) {
                    intSlidePosition = -350;
                    dblServoPosition = dblV4BAngleLowStack;
                } else {
                    intSlidePosition = -160;
                    dblServoPosition = dblV4BAngleLow;
                }
                bolGrabToggle = false;
            } else if (digitalTouchRS.isPressed() == true && bolRSWasPressed) {
                bolRSWasPressed = false;
            }

            if (bolDropToggle) {
                if (dblServoPosition == dblV4BAngleLowest) {
                    srvGrabber.setPosition(0);
                    bolDropToggle = false;
                } else {
                    dblServoPosition = dblV4BAngleHigh;
                    bolDropingToggle = true;
                }
            }

            if (bolDropingToggle) {
                if (intNumSameRecognitions7 < 13) {
                    intNumSameRecognitions7++;
                } else {
                    intNumSameRecognitions7 = 0;
                    srvGrabber.setPosition(0);
                    bolDropToggle = false;
                    if (dblServoPosition == dblV4BAngleLowest) {
                        bolDropingToggle = false;
                    } else {
                        bolDropingToggle2 = true;
                        bolDropingToggle = false;
                    }
                }
            }
            if (bolDropingToggle2) {
                if (intNumSameRecognitions8 < 8) {
                    intNumSameRecognitions8++;
                } else {
                    intNumSameRecognitions8 = 0;
                    dblServoPosition = CENTERANGLE;
                    bolDropingToggle2 = false;
                }
            }

            if (digitalTouchGRB.getState() == true) {
                srvGrabber.setPosition(0);
                intNumSameRecognitions10 = 0;
            }
        }

        //GRAB WITHOUT LIMIT SWITCH

        if (!bolLSGrabbingToggle) {

            if (gmpGamepad1.right_bumper && !bolRB1WasPressed) {
                bolRB1WasPressed = true;
                bolCLToggle = !bolCLToggle;
                if (bolCLToggle) {

                    if (bolSTToggle) {
                        dblServoPosition = dblV4BAngleLowStack;
                        bolT5Toggle = true;
                    } else {
                        intSlidePosition = -160;
                        dblServoPosition = dblV4BAngleLow;
                        bolGRB1Toggle = true;
                    }

                } else {
                    if (bolSTToggle) {

                        dblServoPosition = dblV4BAngleHigh;
                        bolT3Toggle = true;
                        intConeStack1 = intConeStack1 + 52;

                    } else {
                        if (dblServoPosition == dblV4BAngleLowest) {
                            srvGrabber.setPosition(0);
                        } else {
                            dblServoPosition = dblV4BAngleHigh;
                            bolT3Toggle = true;
                        }


                    }
                }
            } else if (!gmpGamepad1.right_bumper && bolRB1WasPressed) {
                bolRB1WasPressed = false;
            }


            if (bolT5Toggle) {
                if (intNumSameRecognitions5 < 20) {
                    intNumSameRecognitions5++;
                } else {

                    intNumSameRecognitions5 = 0;
                    bolT5Toggle = false;
                    intSlidePosition = intConeStack1;
                    bolSGRB1Toggle = true;
                }
            }


            if (bolT3Toggle) {
                if (intNumSameRecognitions3 < 15) {
                    intNumSameRecognitions3++;
                } else {
                    srvGrabber.setPosition(0);
                    intNumSameRecognitions3 = 0;
                    bolT3Toggle = false;
                    bolT4Toggle = true;

                }
            }

            if (bolT4Toggle) {
                if (intNumSameRecognitions3 < 15) {
                    intNumSameRecognitions3++;
                } else {
                    dblServoPosition = CENTERANGLE;
                    intNumSameRecognitions3 = 0;
                    bolT4Toggle = false;
                }
            }


            // GROUND PICK UP CHAIN

            if (dcmSlider.getCurrentPosition() < -140 && bolGRB1Toggle) {

                intSlidePosition = 0;
                bolGRB1Toggle = false;
                bolGRB2Toggle = true;
            }

            if (dcmSlider.getCurrentPosition() > -15 && bolGRB2Toggle) {


                srvGrabber.setPosition(1);
                bolGRB2Toggle = false;
                bolGRB3Toggle = true;

            }

            if (dcmSlider.getCurrentPosition() > -5 && bolGRB3Toggle) {

                intSlidePosition = -160;
                bolGRB3Toggle = false;
                bolGRB4Toggle = true;


            }

            if (dcmSlider.getCurrentPosition() < -85 && bolGRB4Toggle) { //-100

                dblServoPosition = CENTERANGLE;
                bolGRB4Toggle = false;

            }

            if (dcmSlider.getCurrentPosition() < -85 && bolGRB5Toggle) { //-100

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

                intSlidePosition = -160;

                dblServoPosition = dblV4BAngleLow;

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
                if (bolOutToggle) {
                    intSlidePosition = -155;
                }else {
                    intSlidePosition = -105;
                }
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
                if (bolOutToggle) {
                    intSlidePosition = -540;
                }else {
                    intSlidePosition = -482;
                }
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
                if (bolOutToggle) {
                    intSlidePosition = -920;
                }else {
                    intSlidePosition = -855;
                }
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
            bolLSGrabbingToggle = !bolLSGrabbingToggle;
        }else if (!gmpGamepad1.y && bolY1WasPressed){
            bolY1WasPressed = false;
        }

        if (!bolLSGrabbingToggle) {
            bolGrabToggle = false;
            bolGrabbingToggle = false;
            bolDropToggle = false;
            bolDropingToggle = false;
        } else {
            bolCLToggle = false;
            bolT2Toggle = false;
            bolT3Toggle = false;
            bolT4Toggle = false;
            bolT5Toggle = false;
            bolT6Toggle = false;
            bolGRB1Toggle = false;
            bolGRB2Toggle = false;
            bolGRB3Toggle = false;
            bolGRB4Toggle = false;
            bolGRB5Toggle = false;
        }



        if(bolTToggle){
            if(intNumSameRecognitions < 5){
                intNumSameRecognitions++;
                telTelemetry.addLine("One");
            }
            else {

                if(bolSTToggle){
                    intSlidePosition = -350;
                }else {
                    intSlidePosition = -160;
                }
                    intNumSameRecognitions = 0;
                    telTelemetry.addLine("Two");
                    bolTToggle = false;
                    //bolRBToggle = false;

            }
        }
//ODOMETRY SERVO OPERATION

        if (gmpGamepad1.a && !bolA1WasPressed) {
            bolA1WasPressed = true;
            bolA1Toggle = !bolA1Toggle;
            if (bolA1Toggle) {
                srvOdometryPod1.setPosition(0);
                srvOdometryPod2.setPosition(0);
                srvOdometryPod3.setPosition(0);
            } else {
                srvOdometryPod1.setPosition(1);
                srvOdometryPod2.setPosition(1);
                srvOdometryPod3.setPosition(1);
            }
        } else if (!gmpGamepad1.a && bolA1WasPressed) {
            bolA1WasPressed = false;
            srvOdometryPod1.setPosition(0.5);
            srvOdometryPod2.setPosition(0.5);
            srvOdometryPod3.setPosition(0.5);
        }

        if (!gmpGamepad1.a) {
            srvOdometryPod1.setPosition(0.5);
            srvOdometryPod2.setPosition(0.5);
            srvOdometryPod3.setPosition(0.5);
        }

        /*
        if (gmpGamepad1.a && !bolA1WasPressed) {
            bolA1WasPressed = true;
            bolA1Toggle = !bolA1Toggle;
            if (bolA1Toggle) {
                if (intNumSameRecognitions11 < 20) {
                    intNumSameRecognitions11 ++;
                    srvOdometryPod1.setPosition(0);
                    srvOdometryPod2.setPosition(0);
                    srvOdometryPod3.setPosition(0);
                } else {
                    srvOdometryPod1.setPosition(0.5);
                    srvOdometryPod2.setPosition(0.5);
                    srvOdometryPod3.setPosition(0.5);
                    intNumSameRecognitions11 = 0;
                }
            } else {
                if (intNumSameRecognitions11 < 20) {
                    intNumSameRecognitions11++;
                    srvOdometryPod1.setPosition(1);
                    srvOdometryPod2.setPosition(1);
                    srvOdometryPod3.setPosition(1);
                } else {
                    srvOdometryPod1.setPosition(0.5);
                    srvOdometryPod2.setPosition(0.5);
                    srvOdometryPod3.setPosition(0.5);
                    intNumSameRecognitions11 = 0;
                }
            }
        } else if (!gmpGamepad1.a && bolA1WasPressed) {
            intNumSameRecognitions11 = 0;
            srvOdometryPod1.setPosition(0.5);
            srvOdometryPod2.setPosition(0.5);
            srvOdometryPod3.setPosition(0.5);
            bolA1WasPressed = false;
        }
         */



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


        if (digitalTouchRS.isPressed() == true && !bolLS2WasPressed) {
            bolLS2WasPressed = true;
            intZeroReference = dcmSlider.getCurrentPosition();
        } else if (digitalTouchRS.isPressed() == false && bolLS2WasPressed) {
            bolLS2WasPressed = false;
        }






        //telTelemetry.addData("ZeroReference", intZeroReference);
        telTelemetry.addData("Slider", dcmSlider.getCurrentPosition());
        //telTelemetry.addData("intSlider", intSlidePosition);
        telTelemetry.addData("Grabber", srvGrabber.getPosition());
        telTelemetry.addData("State", strStateTest);
        //telTelemetry.addData("V4B", srvV4B.getPosition());
        //telTelemetry.addData("Left", dblDistanceSensorLeft);
        //telTelemetry.addData("Right", dblDistanceSensorRight);

        }


}
