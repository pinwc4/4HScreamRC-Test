package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "StraferTest")


public class StraferTest extends OpMode {

    private DcMotor dcmFrontLeftMotor;
    private DcMotor dcmFrontRightMotor;
    private DcMotor dcmBackLeftMotor;
    private DcMotor dcmBackRightMotor;

    private static final double TICKSTOINCHES = (537.6 * 1) / (Math.PI * 3.77953);


    public void init(){
        dcmFrontLeftMotor = hardwareMap.dcMotor.get("MotorFL");
        dcmFrontRightMotor = hardwareMap.dcMotor.get("MotorFR");
        dcmBackLeftMotor = hardwareMap.dcMotor.get("MotorBL");
        dcmBackRightMotor = hardwareMap.dcMotor.get("MotorBR");
    }

    public void loop(){
        if (dcmFrontLeftMotor.getCurrentPosition() < (72 * TICKSTOINCHES)) {
            //dcmFrontLeftMotor.setPower(-0.25);
            dcmFrontRightMotor.setPower(0.25);
            //dcmBackLeftMotor.setPower(-0.25);
            //dcmBackRightMotor.setPower(0.25);
        }else{
            dcmFrontLeftMotor.setPower(0);
            dcmFrontRightMotor.setPower(0);
            dcmBackLeftMotor.setPower(0);
            dcmBackRightMotor.setPower(0);

        }

        telemetry.addData("FLMotor", dcmFrontLeftMotor.getCurrentPosition());

        telemetry.update();
    }
}

