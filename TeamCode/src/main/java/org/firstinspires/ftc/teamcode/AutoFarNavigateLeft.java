package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "AutoFarNavigateLEFT")

public class AutoFarNavigateLeft extends OpMode {

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
        if(dcmFrontLeftMotor.getCurrentPosition() > -1534 && !bolGoLeft){
            dcmFrontLeftMotor.setPower(1);
            dcmBackLeftMotor.setPower(1);
            dcmFrontRightMotor.setPower(-1);
            dcmBackRightMotor.setPower(-1);
        } else if (!bolGoLeft){
            dcmFrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcmFrontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            bolGoLeft = true;
        }

        if(dcmFrontLeftMotor.getCurrentPosition() < 975 && bolGoLeft){
            dcmFrontLeftMotor.setPower(-1);
            dcmBackLeftMotor.setPower(1);
            dcmFrontRightMotor.setPower(-1);
            dcmBackRightMotor.setPower(1);
        } else{
            dcmFrontLeftMotor.setPower(0);
            dcmBackLeftMotor.setPower(0);
            dcmFrontRightMotor.setPower(0);
            dcmBackRightMotor.setPower(0);
        }

    }
}


