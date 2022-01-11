package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class FreightAttachment extends Object {


    private Gamepad gmpGamepad1;
    private Gamepad gmpGamepad2;
    private Telemetry telTelemetry;
    private DcMotorEx dcmSlider1;
    private DcMotorEx dcmMagnetArm;

    private DcMotor dcmIntake0;

    private Servo srvBucketServo;
    private Servo srvMagnetSwitch;
    private Servo srvMagnetOrientation;

    private CRServo dcmCarouselSpinner1;

    private TouchSensor snsTestTouch;

    private boolean bolXWasPressed = false;
    private boolean bolRBumperWasPressed = false;
    private boolean bolLBumperWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;
    private boolean bolDPadDownWasPressed = false;
    private boolean bolDPadUpWasPressed = false;

    private boolean bolGMAToggle = false;
    private boolean bolGMBToggle = false;
    private boolean bolGMYToggle = false;
    private boolean bolSVToggle = false;
    private boolean bolMOToggle = false;
    private boolean bolMPToggle = false;
    private boolean bolMCToggle = false;
    private boolean bolAPToggle = false;

    private boolean bolGMBToggle = false;
    private boolean bolGMYToggle = false;
    private boolean bolSVToggle = false;
    private boolean bolAWasPressed = false;
    private boolean bolCarouselToggle = false;

    private boolean bolStickMoved = false;

    private double dblCarouselSpeed;
    private double dblCapDist;
    private double dblSlideSpeed;
    private double dblMagnetArmSpeed;
    private int intSlideSpeed;
    private int intMagnetArmSpeed;
    private int intMagnetArmSpeed2;

    private double dblCarouselSpeedToggle;
    private static final double SERVO_MOVE_INTERVAL = 0.005;

    public FreightAttachment(Gamepad gmpGamepad1, Gamepad gmpGamepad2, HardwareMap hmpHardwareMap, Telemetry telTelemetry) {

        this.gmpGamepad1 = gmpGamepad1;
        this.gmpGamepad2 = gmpGamepad2;
        this.telTelemetry = telTelemetry;

        dcmIntake0 = hmpHardwareMap.get(DcMotor.class, "MotorIM");


        dcmCarouselSpinner1 = hmpHardwareMap.crservo.get("SpinnerMotor");

        dcmSlider1 = hmpHardwareMap.get(DcMotorEx.class, "MotorGM");
        dcmSlider1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSlider1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider1.setDirection(DcMotor.Direction.FORWARD);

        srvBucketServo = hmpHardwareMap.servo.get("BucketServo");
        srvBucketServo.setPosition(0.85);


        dcmMagnetArm = hmpHardwareMap.get(DcMotorEx.class, "MagnetArm");
        dcmMagnetArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmMagnetArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmMagnetArm.setDirection(DcMotor.Direction.FORWARD);

        srvMagnetOrientation = hmpHardwareMap.servo.get("ServoMO");

        srvMagnetSwitch = hmpHardwareMap.servo.get("ServoMS");

    }



    public void moveAttachments() {
/*
        double dblCarouselSpeed = 0.5 + dblCarouselSpeedToggle;

        if (gmpGamepad1.a && !bolAWasPressed) {
            bolAWasPressed = true;
            bolCarouselToggle = !bolCarouselToggle;
        } else if (!gmpGamepad1.a) {
            bolAWasPressed = false;
        }

        if (bolCarouselToggle) {
            dcmCarouselSpinner1.setPower(dblCarouselSpeed);
        } else {
            dcmCarouselSpinner1.setPower(0);
        }


        if (gmpGamepad1.right_bumper && bolRBumperWasPressed == false){
            bolRBumperWasPressed = true;
        } if (bolRBumperWasPressed == true && !gmpGamepad1.right_bumper) {
            dblCarouselSpeedToggle = dblCarouselSpeedToggle + 0.01;
            bolRBumperWasPressed = false;
        }
        if (gmpGamepad1.left_bumper && bolLBumperWasPressed == false){
            bolLBumperWasPressed = true;
        } if (bolLBumperWasPressed == true && !gmpGamepad1.left_bumper) {
            dblCarouselSpeedToggle = dblCarouselSpeedToggle - 0.01;
            bolLBumperWasPressed = false;
        }


 */


        dcmCarouselSpinner1.setPower(dblCarouselSpeed);



        if(gmpGamepad1.a){
            dblCarouselSpeed = dblCarouselSpeed + 0.01;
        } else if(gmpGamepad1.b){
            dblCarouselSpeed = dblCarouselSpeed - 0.01;
        }else{
            dblCarouselSpeed = 0;
        }


        if (gmpGamepad1.right_trigger>0) {
            dcmIntake0.setPower(-1);
        } else if(gmpGamepad1.left_trigger>0){
            dcmIntake0.setPower(1);
        }else {
            dcmIntake0.setPower(0);
        }



        if (gmpGamepad2.x && !bolXWasPressed) {
            bolXWasPressed = true;
            bolSVToggle = !bolSVToggle;
            if (bolSVToggle) {
                srvBucketServo.setPosition(0.15);
            } else {
                srvBucketServo.setPosition(0.85);
            }
        } else if (!gmpGamepad2.x && bolXWasPressed) {
            bolXWasPressed = false;
        }


        if(gmpGamepad2.left_stick_y != 0) {
            dblSlideSpeed = dblSlideSpeed + -gmpGamepad2.left_stick_y * 6;

            intSlideSpeed = (int) dblSlideSpeed;
        }


            if (gmpGamepad2.a && !bolAWasPressed) {
                bolAWasPressed = true;
                bolGMAToggle = !bolGMAToggle;
                if (bolGMAToggle) {
                    intSlideSpeed = 250;

                } else {
                    intSlideSpeed = 0;

                    srvBucketServo.setPosition(0.85);
                }

            } else if (!gmpGamepad2.a && bolAWasPressed) {
                bolAWasPressed = false;
            }

            if (gmpGamepad2.b && !bolBWasPressed) {
                bolBWasPressed = true;
                bolGMBToggle = !bolGMBToggle;
                if (bolGMBToggle) {
                    intSlideSpeed = 380;

                } else {
                    intSlideSpeed = 0;
                    srvBucketServo.setPosition(0.85);
                }

            } else if (!gmpGamepad2.b && bolBWasPressed) {
                bolBWasPressed = false;
            }

            if (gmpGamepad2.y && !bolYWasPressed) {
                bolYWasPressed = true;
                bolGMYToggle = !bolGMYToggle;
                if (bolGMYToggle) {
                    intSlideSpeed = 540;

                } else {
                    intSlideSpeed = 0;
                    srvBucketServo.setPosition(0.85);
                }

            } else if (!gmpGamepad2.y && bolYWasPressed) {
                bolYWasPressed = false;
            }


            dcmSlider1.setTargetPosition(intSlideSpeed);
            dcmSlider1.setPower(0.5);
            dcmSlider1.setMode(DcMotor.RunMode.RUN_TO_POSITION);



            if (dcmSlider1.getCurrentPosition() > 250 - 100 && bolGMAToggle) {
                srvBucketServo.setPosition(0.15);
            }
            if (dcmSlider1.getCurrentPosition() > 380 - 100 && bolGMBToggle) {
                srvBucketServo.setPosition(0.15);
            }
            if (dcmSlider1.getCurrentPosition() > 540 - 100 && bolGMYToggle) {
                srvBucketServo.setPosition(0.15);
            }







            //Magnet Code



        //Magnet Switch Toggle
        if (gmpGamepad2.left_bumper && !bolLBumperWasPressed) {
            bolLBumperWasPressed = true;
            bolMOToggle = !bolMOToggle;
            if (bolMOToggle) {
                srvMagnetSwitch.setPosition(0.2);
                srvMagnetSwitch.setPosition(0.8);
            }
            //difference is 2/3 (180)
        } else if (!gmpGamepad2.left_bumper && bolLBumperWasPressed) {
            bolLBumperWasPressed = false;
            }
            if (dcmSlider1.getCurrentPosition() > 540 - 100 && bolGMYToggle) {
                srvBucketServo.setPosition(0.15);
            }






        telTelemetry.addData("sliderpower", dcmSlider1.getPower());
        telTelemetry.addData("slidespeed", intSlideSpeed);
        telTelemetry.addData("slidespeed", dblCarouselSpeed);

       //Manual Arm Control

        if(gmpGamepad2.right_stick_y != 0) {
            dblMagnetArmSpeed = dblMagnetArmSpeed + (-gmpGamepad2.right_stick_y*12);

            intMagnetArmSpeed2 = (int) dblMagnetArmSpeed;
        }





        //Arm Preset Delivery
        if (gmpGamepad2.right_bumper && !bolRBumperWasPressed) {
            bolRBumperWasPressed = true;
            bolMPToggle = !bolMPToggle;
            if (bolMPToggle) {
                intMagnetArmSpeed = 3100;
                srvMagnetSwitch.setPosition(0.2);

            } else {
                intMagnetArmSpeed = 0;
            }

        } else if (!gmpGamepad2.right_bumper && bolRBumperWasPressed) {
            bolRBumperWasPressed = false;
        }




        //Arm Preset Capping
        if (gmpGamepad2.dpad_down && !bolDPadDownWasPressed) {
            bolDPadDownWasPressed = true;
            bolMCToggle = !bolMCToggle;
            if (bolMCToggle) {
                dblCapDist=0.4;
            } else {
                dblCapDist=0;
            }
            //difference is 2/3 (180)
        } else if (!gmpGamepad2.dpad_down && bolDPadDownWasPressed) {
            bolDPadDownWasPressed = false;
        }else{
            srvMagnetOrientation.setPosition((dcmMagnetArm.getCurrentPosition()/3700f+dblCapDist));
        }




        //Pick Up TSE
        if (gmpGamepad2.dpad_up && !bolDPadUpWasPressed) {
            bolDPadUpWasPressed = true;
            bolAPToggle = !bolAPToggle;
            if (bolAPToggle) {
                intMagnetArmSpeed = 2000;

            } else {
                intMagnetArmSpeed = 0;
            }

        } else if (!gmpGamepad2.dpad_up && bolDPadUpWasPressed) {
            bolDPadUpWasPressed = false;
        }


        dcmMagnetArm.setTargetPosition(intMagnetArmSpeed+intMagnetArmSpeed2);
        dcmMagnetArm.setPower(0.5);
        dcmMagnetArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);




        telTelemetry.addData("sliderposition", dcmSlider1.getCurrentPosition());
        telTelemetry.addData("MagnetArm", dcmMagnetArm.getCurrentPosition());
        telTelemetry.addData("sliderpower", dcmSlider1.getPower());
        telTelemetry.addData("slidespeed", intSlideSpeed);



        telTelemetry.addData("Orientation", srvMagnetOrientation.getPosition());
        telTelemetry.update();


    }


}
