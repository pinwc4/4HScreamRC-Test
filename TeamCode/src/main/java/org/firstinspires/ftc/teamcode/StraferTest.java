package org.firstinspires.ftc.teamcode;

import android.hardware.Sensor;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "StraferTest")


public class StraferTest extends OpMode {

    private DcMotor dcmFrontLeftMotor;
    private DcMotor dcmFrontRightMotor;
    private DcMotor dcmBackLeftMotor;
    private DcMotor dcmBackRightMotor;

    public void init(){
        dcmFrontLeftMotor = hardwareMap.dcMotor.get("MotorFL");
        dcmFrontRightMotor = hardwareMap.dcMotor.get("MotorFR");
        dcmBackLeftMotor = hardwareMap.dcMotor.get("MotorBL");
        dcmBackRightMotor = hardwareMap.dcMotor.get("MotorBR");

        dcmFrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmFrontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmBackLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmBackRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmFrontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmFrontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmBackLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmBackRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void loop(){

        telemetry.addData("FL", dcmFrontLeftMotor.getCurrentPosition());
        telemetry.addData("FR", dcmFrontRightMotor.getCurrentPosition());
        telemetry.addData("BL", dcmBackLeftMotor.getCurrentPosition());
        telemetry.addData("BR", dcmBackRightMotor.getCurrentPosition());

        telemetry.update();
    }
}

