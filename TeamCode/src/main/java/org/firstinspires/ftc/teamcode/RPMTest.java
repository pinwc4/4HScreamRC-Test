package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "RPM Test")
@Disabled

public class RPMTest extends OpMode {

    private DcMotorEx dcmLauncherMotor;

    private double dblLauncherMotor = 0;

    private double dblLoopStartTime = 0;
    private double dblLoopPreviousStartTime = 0;
    private double dblLoopDifference;
    private double dblEncoderStartPosition = 0;
    private double dblEncoderPreviousStartPosition = 0;
    private double dblEncoderDifference = 0;

    private double dblTicksPerSecond;
    private double dblRotationsPerMinute;

    private double dblMotorSetSpeed = 100;

    private boolean bolDPadDownWasPressed = false;
    private boolean bolDPadUpWasPressed = false;
    private boolean bolRBumperWasPressed = false;
    private boolean bolLBumperWasPressed = false;


    public void init(){

        dcmLauncherMotor = hardwareMap.get(DcMotorEx.class, "MotorLM");
        dcmLauncherMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        dcmLauncherMotor.setDirection(DcMotorEx.Direction.REVERSE);



        dblMotorSetSpeed = 2800;

        dblLauncherMotor = 0;

    }

    public void loop(){
/*

*/
        dcmLauncherMotor.setVelocity(dblMotorSetSpeed);

        if (gamepad2.dpad_up && bolDPadUpWasPressed == false){
            bolDPadUpWasPressed = true;
        } if (bolDPadUpWasPressed == true && !gamepad2.dpad_up) {
            dblMotorSetSpeed = dblMotorSetSpeed + 100;
            bolDPadUpWasPressed = false;
        }
        if (gamepad2.dpad_down && bolDPadDownWasPressed == false){
            bolDPadDownWasPressed = true;
        } if (bolDPadDownWasPressed == true && !gamepad2.dpad_down) {
            dblMotorSetSpeed = dblMotorSetSpeed - 100;
            bolDPadDownWasPressed = false;
        }

        if (gamepad2.right_bumper && bolRBumperWasPressed == false){
            bolRBumperWasPressed = true;
        } if (bolRBumperWasPressed == true && !gamepad2.right_bumper) {
            dblMotorSetSpeed = dblMotorSetSpeed + 10;
            bolRBumperWasPressed = false;
        }
        if (gamepad2.left_bumper && bolLBumperWasPressed == false){
            bolLBumperWasPressed = true;
        } if (bolLBumperWasPressed == true && !gamepad2.left_bumper) {
            dblMotorSetSpeed = dblMotorSetSpeed - 10;
            bolLBumperWasPressed = false;
        }

        telemetry.addData("Motor Set Speed", dblMotorSetSpeed);
        telemetry.addData("Velocity", dcmLauncherMotor.getVelocity());
        telemetry.addData("Direction", dcmLauncherMotor.getDirection());
        telemetry.addData("Direction", dcmLauncherMotor.getCurrentPosition());
//        telemetry.addData("MotorPower", dcmLauncherMotor.getMotorPower());
        telemetry.update();
    }


}

