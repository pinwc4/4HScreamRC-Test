package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "AutoCloseNavigateRight")

public class AutoCloseNavigateRight extends OpMode {

    private DcMotor dcmFrontLeftMotor;
    private DcMotor dcmFrontRightMotor;
    private DcMotor dcmBackLeftMotor;
    private DcMotor dcmBackRightMotor;

    private boolean bolGoLeft = false;

    private static final double DBL_SQRT_OF_2 = Math.sqrt(2);

    public void init(){
        dcmFrontLeftMotor = hardwareMap.dcMotor.get("MotorFL");
        dcmFrontRightMotor = hardwareMap.dcMotor.get("MotorFR");
        dcmBackLeftMotor = hardwareMap.dcMotor.get("MotorBL");
        dcmBackRightMotor = hardwareMap.dcMotor.get("MotorBR");
        dcmFrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmFrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        dcmFrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmFrontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void loop(){

        //STRAFING
        //ticks per inch = 65
        //5 * 65 = 325, 325 ticks per 5 inches
        //just multiply the distance you want to go by 65

        //go forward 1 inch so it doesn't hit the wall
        if(dcmFrontLeftMotor.getCurrentPosition() > -65 && !bolGoLeft){
            dcmFrontLeftMotor.setPower(1);
            dcmBackLeftMotor.setPower(1);
            dcmFrontRightMotor.setPower(-1);
            dcmBackRightMotor.setPower(-1);
        } else{
            dcmFrontLeftMotor.setPower(0);
            dcmBackLeftMotor.setPower(0);
            dcmFrontRightMotor.setPower(0);
            dcmBackRightMotor.setPower(0);
            bolGoLeft = true;
        }

        if(dcmFrontLeftMotor.getCurrentPosition() > -975 && bolGoLeft){
            dcmFrontLeftMotor.setPower(1);
            dcmBackLeftMotor.setPower(-1);
            dcmFrontRightMotor.setPower(1);
            dcmBackRightMotor.setPower(-1);
        } else{
            dcmFrontLeftMotor.setPower(0);
            dcmBackLeftMotor.setPower(0);
            dcmFrontRightMotor.setPower(0);
            dcmBackRightMotor.setPower(0);
        }

        telemetry.addData("MOTOR POSITION", dcmFrontLeftMotor.getCurrentPosition());

    }
}
