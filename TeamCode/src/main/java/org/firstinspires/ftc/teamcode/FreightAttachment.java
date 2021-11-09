package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class FreightAttachment extends Object {


    private Gamepad gmpGamepad1;
    private Gamepad gmpGamepad2;
    private Telemetry telTelemetry;

    private DcMotor dcmIntake0;

    //private CRServo dcmCarouselSpinner1;

    private TouchSensor snsTestTouch;

    private boolean bolXWasPressed = false;
    private boolean bolRBumperWasPressed = false;
    private boolean bolLBumperWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;

    private boolean bolGSToggle = false;
    private boolean bolAWasPressed = false;
    private boolean bolCarouselToggle = false;

    private double dblCarouselSpeed;

    private double dblCarouselSpeedToggle;

    public FreightAttachment(Gamepad gmpGamepad1, Gamepad gmpGamepad2, HardwareMap hmpHardwareMap, Telemetry telTelemetry) {

        this.gmpGamepad1 = gmpGamepad1;
        this.gmpGamepad2 = gmpGamepad2;
        this.telTelemetry = telTelemetry;

        dcmIntake0 = hmpHardwareMap.get(DcMotor.class, "MotorIM");
        dcmIntake0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //dcmCarouselSpinner1 = hmpHardwareMap.crservo.get("SpinnerMotor");

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

      //  dcmCarouselSpinner1.setPower(dblCarouselSpeed);




        if(gmpGamepad1.a){
            dblCarouselSpeed = dblCarouselSpeed + 0.0015;
        } else{
            dblCarouselSpeed = 0;
        }

        if(gmpGamepad1.b){
            dblCarouselSpeed = dblCarouselSpeed + 0.001;
        } else{
            dblCarouselSpeed = 0;
        }


        if (gmpGamepad1.right_trigger>0) {
            dcmIntake0.setPower(1);
        } else {
            dcmIntake0.setPower(0);
        }

        if (gmpGamepad1.left_trigger>0) {
            dcmIntake0.setPower(-1);
        } else {
            dcmIntake0.setPower(0);
        }



        telTelemetry.addData("TouchStatus", snsTestTouch);
        telTelemetry.addData("dblmotorspeed", dblCarouselSpeedToggle);
       // telTelemetry.addData("motorspeed", dcmCarouselSpinner1);
        telTelemetry.addData("Intakemotorspeed", dcmIntake0);

        telTelemetry.update();


    }


}
