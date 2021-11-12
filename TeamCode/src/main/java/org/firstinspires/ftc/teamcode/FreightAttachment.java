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

    private DcMotor dcmIntake0;

    private Servo srvBucketServo;

    private CRServo dcmCarouselSpinner1;

    private TouchSensor snsTestTouch;

    private boolean bolXWasPressed = false;
    private boolean bolRBumperWasPressed = false;
    private boolean bolLBumperWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;

    private boolean bolGMAToggle = false;
    private boolean bolGMBToggle = false;
    private boolean bolGMYToggle = false;
    private boolean bolSVToggle = false;
    private boolean bolAWasPressed = false;
    private boolean bolCarouselToggle = false;

    private boolean bolStickMoved = false;

    private double dblCarouselSpeed;
    private double dblSlideSpeed;
    private int intSlideSpeed;

    private double dblCarouselSpeedToggle;

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






        telTelemetry.addData("TouchStatus", snsTestTouch);
        telTelemetry.addData("dblmotorspeed", dblCarouselSpeedToggle);
        telTelemetry.addData("Intakemotorspeed", dcmIntake0.getPower());
        telTelemetry.addData("sliderposition", dcmSlider1.getCurrentPosition());
        telTelemetry.addData("sliderpower", dcmSlider1.getPower());
        telTelemetry.addData("slidespeed", intSlideSpeed);
        telTelemetry.addData("slidespeed", dblCarouselSpeed);

        telTelemetry.update();


    }


}
