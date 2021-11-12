package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "PresetTest")


public class PresetTest extends OpMode {


    private DcMotor dcmCarouselMotor1;
    // private DcMotor dcmFrontRightMotor;
    //  private DcMotor dcmBackLeftMotor;
    //  private DcMotor dcmBackRightMotor;

    private static final double TICKSTOINCHES = (537.6 * 1) / (Math.PI * 3.77953);


    private boolean bolGMAToggle = false;
    private boolean bolGMBToggle = false;
    private boolean bolGMYToggle = false;
    private boolean bolAWasPressed = false;
    private boolean bolCarouselToggle = false;
    private boolean bolXWasPressed = false;
    private boolean bolRBumperWasPressed = false;
    private boolean bolLBumperWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;

    private double dblCarouselSpeed;

    private double dblCarouselSpeedToggle;

    private DcMotor dcmSlider1;
    private Servo srvBucketServo;

    public void init() {
        //dcmCarouselMotor1 = hardwareMap.dcMotor.get("MotorFL");
        //  dcmFrontRightMotor = hardwareMap.dcMotor.get("MotorFR");
        //   dcmBackLeftMotor = hardwareMap.dcMotor.get("MotorBL");
        //   dcmBackRightMotor = hardwareMap.dcMotor.get("MotorBR");
        dcmSlider1 = hardwareMap.get(DcMotor.class, "MotorGM");
        dcmSlider1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSlider1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider1.setDirection(DcMotor.Direction.FORWARD);

        srvBucketServo = hardwareMap.servo.get("BucketServo");
        srvBucketServo.setPosition(0);
    }

    public void loop() {
     /*
        if (dcmCarouselMotor.getCurrentPosition() < (72 * TICKSTOINCHES)) {
            //dcmFrontLeftMotor.setPower(-0.25);
            //dcmFrontRightMotor.setPower(0.25);
            //dcmBackLeftMotor.setPower(-0.25);
            //dcmBackRightMotor.setPower(0.25);
        }else{
            dcmCarouselMotor.setPower(0);
            //dcmFrontRightMotor.setPower(0);
            //dcmBackLeftMotor.setPower(0);
            //dcmBackRightMotor

        }

      */
        if (gamepad2.a && !bolAWasPressed) {
            bolAWasPressed = true;
            bolGMAToggle = !bolGMAToggle;
            if (bolGMAToggle) {
                dcmSlider1.setTargetPosition(400);
                dcmSlider1.setPower(0.5);
                dcmSlider1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            } else {
                dcmSlider1.setTargetPosition(0);
                dcmSlider1.setPower(0.5);
                srvBucketServo.setPosition(0);
            }

        } else if (!gamepad2.a && bolAWasPressed) {
            bolAWasPressed = false;
        }

        if (gamepad2.b && !bolBWasPressed) {
            bolBWasPressed = true;
            bolGMBToggle = !bolGMBToggle;
            if (bolGMBToggle) {
                dcmSlider1.setTargetPosition(800);
                dcmSlider1.setPower(0.5);
                dcmSlider1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                if(dcmSlider1.getCurrentPosition() > 800 - 50) {
                    srvBucketServo.setPosition(1);
                }
            } else {
                dcmSlider1.setTargetPosition(0);
                dcmSlider1.setPower(0.5);
                srvBucketServo.setPosition(0);
            }

        } else if (!gamepad2.b && bolBWasPressed) {
            bolBWasPressed = false;
        }

        if (gamepad2.y && !bolYWasPressed) {
            bolYWasPressed = true;
            bolGMYToggle = !bolGMYToggle;
            if (bolGMYToggle) {
                dcmSlider1.setTargetPosition(1200);
                dcmSlider1.setPower(0.5);
                dcmSlider1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                if(dcmSlider1.getCurrentPosition() > 1200 - 100) {
                    srvBucketServo.setPosition(1);
                }
            } else {
                dcmSlider1.setTargetPosition(0);
                dcmSlider1.setPower(0.5);
                srvBucketServo.setPosition(0);
            }

        } else if (!gamepad2.y && bolYWasPressed) {
            bolYWasPressed = false;
        }

        if(dcmSlider1.getCurrentPosition() > 400 - 100 && bolGMAToggle) {
            srvBucketServo.setPosition(1);
            telemetry.addLine("random");
        }
        if(dcmSlider1.getCurrentPosition() > 800 - 100 && bolGMBToggle) {
            srvBucketServo.setPosition(1);
            telemetry.addLine("random");
        }
        if(dcmSlider1.getCurrentPosition() > 1200 - 100 && bolGMYToggle) {
            srvBucketServo.setPosition(1);
            telemetry.addLine("random");
        }


        /*double dblCarouselSpeed = 0 + dblCarouselSpeedToggle;

        if (gamepad1.a && !bolAWasPressed) {
            bolAWasPressed = true;
            bolCarouselToggle = !bolCarouselToggle;
        } else if (!gamepad1.a) {
            bolAWasPressed = false;
        }

        if (bolCarouselToggle) {
            dcmCarouselMotor1.setPower(dblCarouselSpeed);
        } else {
            dcmCarouselMotor1.setPower(0);
        }


        if (gamepad1.right_bumper && bolRBumperWasPressed == false){
            bolRBumperWasPressed = true;
        } if (bolRBumperWasPressed == true && !gamepad1.right_bumper) {
            dblCarouselSpeedToggle = dblCarouselSpeedToggle + 0.01;
            bolRBumperWasPressed = false;
        }
        if (gamepad1.left_bumper && bolLBumperWasPressed == false){
            bolLBumperWasPressed = true;
        } if (bolLBumperWasPressed == true && !gamepad1.left_bumper) {
            dblCarouselSpeedToggle = dblCarouselSpeedToggle - 0.01;
            bolLBumperWasPressed = false;
        }

         */

        telemetry.addData("SMotor",dcmSlider1.getCurrentPosition());

        telemetry.update();

    }
}



