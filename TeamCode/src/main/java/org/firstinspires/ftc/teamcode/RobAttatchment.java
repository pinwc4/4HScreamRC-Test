package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobAttatchment extends Object {

    private Gamepad gmpGamepad1;
    private Gamepad gmpGamepad2;
    private Telemetry telTelemetry;

    private DcMotor dcmIntakeMotor0;
    private DcMotor dcmSmallArmMotor1;
    private DcMotor dcmLargeArmMotor2;

    private Servo srvSorterServo0;

    private boolean bolXWasPressed = false;

    public RobAttatchment(Gamepad gmpGamepad1, Gamepad gmpGamepad2, HardwareMap hmpHardwareMap, Telemetry telTelemetry) {

        this.gmpGamepad1 = gmpGamepad1;
        this.gmpGamepad2 = gmpGamepad2;
        this.telTelemetry = telTelemetry;

        dcmIntakeMotor0 = hmpHardwareMap.dcMotor.get("IntakeMotor0");
        dcmSmallArmMotor1 = hmpHardwareMap.dcMotor.get("SmallArmMotor1");
        dcmLargeArmMotor2 = hmpHardwareMap.dcMotor.get("LargeArmMotor2");
    }

    public void moveAttachments() {

        dcmLargeArmMotor2.setPower((gmpGamepad2.left_stick_y));
        dcmSmallArmMotor1.setPower((gmpGamepad2.right_stick_y));

        dcmIntakeMotor0.setPower((gmpGamepad2.right_trigger));
        dcmIntakeMotor0.setPower((gmpGamepad2.left_trigger));

        if(gmpGamepad2.x && !bolXWasPressed){
            srvSorterServo0.setPosition(0);
            bolXWasPressed = true;
        }
        if (gmpGamepad2.x && bolXWasPressed){
            srvSorterServo0.setPosition(1);
            bolXWasPressed = false;
        }

    }

}
