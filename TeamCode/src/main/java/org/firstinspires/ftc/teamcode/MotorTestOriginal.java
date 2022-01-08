package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp(name = "MotorTestOriginal")


public class MotorTestOriginal extends OpMode {

    private DcMotorEx dcmArmMotor;

    private static final double TICKSTOINCHES = (537.6 * 1) / (Math.PI * 3.77953);

    private boolean bolGSToggle = false;
    private boolean bolAWasPressed = false;
    private boolean bolCarouselToggle = false;
    private boolean bolXWasPressed = false;
    private boolean bolRBumperWasPressed = false;
    private boolean bolLBumperWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;

    private int intTargetPosition;

    public void init(){

        dcmArmMotor = hardwareMap.get(DcMotorEx.class, "ArmMotor");
        dcmArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmArmMotor.setDirection(DcMotor.Direction.FORWARD);

    }

    public static enum PIDModes {
        Relaxed(new PIDFCoefficients(12, 0.1f, 0.666f, 0f));

        final PIDFCoefficients PIDValues;

        PIDModes (PIDFCoefficients PIDValues){
            this.PIDValues = PIDValues;
        }
        public PIDFCoefficients getPID(){
            return PIDValues;
        }
    }

    public void setShooterPIDMode (MotorTestOriginal.PIDModes mode){
        dcmArmMotor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, mode.getPID());
    }

    public void loop(){

        //dcmArmMotor.setPower(gamepad1.left_stick_y);

        if(gamepad1.a){
            intTargetPosition = 2800;
        }
        if(gamepad1.b){
            intTargetPosition = 0;
        }

        dcmArmMotor.setTargetPosition(intTargetPosition);
        dcmArmMotor.setPower(1);
        dcmArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Position", dcmArmMotor.getCurrentPosition());
        telemetry.addData("Power", dcmArmMotor.getPower());
        telemetry.addData("PIDF", dcmArmMotor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));

        telemetry.update();
    }
}

