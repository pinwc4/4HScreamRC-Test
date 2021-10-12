package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
@TeleOp(name = "DoubleReverseFourBar")

@Disabled
public class DoubleReverseFourBar extends OpMode {


    private DcMotor dcmFLMotor;

    public void init() {

        dcmFLMotor = hardwareMap.dcMotor.get("MotorFL");

        dcmFLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void loop(){

    dcmFLMotor.setPower(gamepad1.right_stick_y*0.6);

    }
}
