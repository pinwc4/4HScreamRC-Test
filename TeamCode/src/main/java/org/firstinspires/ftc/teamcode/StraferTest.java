package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "StraferTest")


public class StraferTest extends OpMode {

    private DcMotor dcmCarouselMotor1;
   // private DcMotor dcmFrontRightMotor;
  //  private DcMotor dcmBackLeftMotor;
  //  private DcMotor dcmBackRightMotor;

    private static final double TICKSTOINCHES = (537.6 * 1) / (Math.PI * 3.77953);

    private boolean bolGSToggle = false;
    private boolean bolAWasPressed = false;
    private boolean bolCarouselToggle = false;
    private boolean bolXWasPressed = false;
    private boolean bolRBumperWasPressed = false;
    private boolean bolLBumperWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;

    private double dblCarouselSpeed;

    private double dblCarouselSpeedToggle;

    public void init(){
        dcmCarouselMotor1 = hardwareMap.dcMotor.get("MotorFL");
      //  dcmFrontRightMotor = hardwareMap.dcMotor.get("MotorFR");
     //   dcmBackLeftMotor = hardwareMap.dcMotor.get("MotorBL");
     //   dcmBackRightMotor = hardwareMap.dcMotor.get("MotorBR");
    }

    public void loop(){
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

        double dblCarouselSpeed = 0 + dblCarouselSpeedToggle;

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

        telemetry.addData("FLMotor", dcmCarouselMotor1.getPower());

        telemetry.update();
    }
}

