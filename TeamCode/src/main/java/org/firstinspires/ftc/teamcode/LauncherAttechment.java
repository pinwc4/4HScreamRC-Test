package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LauncherAttechment extends Object {

    private Gamepad gmpGamepad1;
    private Gamepad gmpGamepad2;
    private Telemetry telTelemetry;

    private DcMotorEx dcmLauncherMotor;
    private DcMotor dcmIntakeMotor;
    private DcMotor dcmGrabberMotor;

    private Servo srvLauncherServo;
    private Servo srvGrabberServo;

    private double dblMotorModifySpeed = 0;

    private boolean bolXWasPressed = false;
    private boolean bolLauncherToggle = false;

    private boolean bolDPadDownWasPressed = false;
    private boolean bolDPadUpWasPressed = false;
    private boolean bolRBumperWasPressed = false;
    private boolean bolLBumperWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;

    private boolean bolGMToggle = false;
    private boolean bolGSToggle = false;
    private boolean bolAWasPressed = false;
    private boolean bolLauncherSRVToggle = false;


    public LauncherAttechment(Gamepad gmpGamepad1, Gamepad gmpGamepad2, HardwareMap hmpHardwareMap, Telemetry telTelemetry) {

        this.gmpGamepad1 = gmpGamepad1;
        this.gmpGamepad2 = gmpGamepad2;
        this.telTelemetry = telTelemetry;


        dcmLauncherMotor.setDirection(DcMotorEx.Direction.REVERSE);

        dcmGrabberMotor = hmpHardwareMap.get(DcMotor.class, "MotorGM");
        dcmGrabberMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmGrabberMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmGrabberMotor.setDirection(DcMotor.Direction.FORWARD);

        dcmIntakeMotor = hmpHardwareMap.get(DcMotor.class, "MotorIM");
        dcmIntakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        srvLauncherServo = hmpHardwareMap.servo.get("LauncherServo");

        srvGrabberServo = hmpHardwareMap.servo.get("GrabberServo");

        srvGrabberServo.setPosition(0);

    }

    public static enum PIDModes {
        Relaxed(new PIDFCoefficients(100, 1.5f, 1f, 0f));

        final PIDFCoefficients PIDValues;

        PIDModes (PIDFCoefficients PIDValues){
            this.PIDValues = PIDValues;
        }
        public PIDFCoefficients getPID(){
            return PIDValues;
        }
    }

    public void setShooterPIDMode (LauncherAttechment.PIDModes mode){
        dcmLauncherMotor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, mode.getPID());
    }


    public void moveAttachments() {

        double dblMotorSetSpeed = 2000 + dblMotorModifySpeed;

        setShooterPIDMode(PIDModes.Relaxed);

        if (gmpGamepad2.x && !bolXWasPressed) {
            bolXWasPressed = true;
            bolLauncherToggle = !bolLauncherToggle;
        } else if (!gmpGamepad2.x) {
            bolXWasPressed = false;
        }

        if (bolLauncherToggle) {
            dcmLauncherMotor.setVelocity(dblMotorSetSpeed);
        } else {
            dcmLauncherMotor.setPower(0);
        }

        if (gmpGamepad2.dpad_up && bolDPadUpWasPressed == false){
           // bolDPadUpWasPressed = true;
        } if (bolDPadUpWasPressed == true && !gmpGamepad2.dpad_up) {
            dblMotorModifySpeed = dblMotorModifySpeed + 500;
            bolDPadUpWasPressed = false;
        }
        if (gmpGamepad2.dpad_down && bolDPadDownWasPressed == false){
            bolDPadDownWasPressed = true;
        } if (bolDPadDownWasPressed == true && !gmpGamepad2.dpad_down) {
            dblMotorModifySpeed = dblMotorModifySpeed - 500;
            bolDPadDownWasPressed = false;
        }

        if (gmpGamepad2.right_bumper && bolRBumperWasPressed == false){
            bolRBumperWasPressed = true;
        } if (bolRBumperWasPressed == true && !gmpGamepad2.right_bumper) {
            dblMotorModifySpeed = dblMotorModifySpeed + 500;
            bolRBumperWasPressed = false;
        }
        if (gmpGamepad2.left_bumper && bolLBumperWasPressed == false){
            bolLBumperWasPressed = true;
        } if (bolLBumperWasPressed == true && !gmpGamepad2.left_bumper) {
            dblMotorModifySpeed = dblMotorModifySpeed - 500;
            bolLBumperWasPressed = false;
        }


        if(gmpGamepad2.a){
            srvLauncherServo.setPosition(1);
        }else{
            srvLauncherServo.setPosition(0);
        }

/*        if (gmpGamepad2.a && !bolAWasPressed) {
            bolAWasPressed = true;
            bolLauncherSRVToggle = !bolLauncherSRVToggle;
            if (bolLauncherSRVToggle) {
                srvLauncherServo.setPosition(1);
            } else {
                srvLauncherServo.setPosition(0);
            }
        } else if (!gmpGamepad2.right_bumper) {
            bolAWasPressed = false;
        }

*/

   //    dcmGrabberMotor.setPower(gmpGamepad2.right_stick_y);

        if (gmpGamepad2.b && !bolBWasPressed) {
            bolBWasPressed = true;
            bolGMToggle = !bolGMToggle;
            if (bolGMToggle) {
                dcmGrabberMotor.setTargetPosition(1200);
                dcmGrabberMotor.setPower(0.5);
                dcmGrabberMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                srvGrabberServo.setPosition(1);
            } else {
                dcmGrabberMotor.setTargetPosition(0);
                dcmGrabberMotor.setPower(0.5);
            }
        } else if (!gmpGamepad2.b) {
            bolBWasPressed = false;
        }
/*
        if(gmpGamepad2.y){
            srvGrabberServo.setPosition(1);
        }else if(srvGrabberServo.getPosition() == 1){
            srvGrabberServo.setPosition(0);
        }
*/




/*
        if(dcmGrabberMotor.getCurrentPosition() == 1200) {
            dcmGrabberMotor.setPower(0);
        }
*/





        if (gmpGamepad1.right_trigger > 0) {
            dcmIntakeMotor.setPower(gmpGamepad1.right_trigger);
        } else {
            dcmIntakeMotor.setPower(-gmpGamepad1.left_trigger);
        }


        telTelemetry.addData("Motor Set Speed", dblMotorSetSpeed);
        telTelemetry.addData("Motor Modify Speed", dblMotorModifySpeed);
        telTelemetry.addData("Grabber Position", dcmGrabberMotor.getCurrentPosition());
        telTelemetry.addData("Velocity", dcmLauncherMotor.getVelocity());
        telTelemetry.addData("Velocity Coefficient", dcmLauncherMotor.getPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER));
        telTelemetry.addData("Grabber", dcmGrabberMotor.getCurrentPosition());
        telTelemetry.update();


    }


}
