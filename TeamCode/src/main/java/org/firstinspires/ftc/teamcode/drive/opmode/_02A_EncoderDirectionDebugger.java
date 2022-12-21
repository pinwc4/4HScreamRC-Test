package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Use this opmode to test the dead wheel encoders.
 */

//@Disabled
@TeleOp(group = "drive")
public class _02A_EncoderDirectionDebugger extends LinearOpMode {

    // Using DcMotor instead of Encoder so we can call STOP_AND_RESET_ENCODER
    private DcMotor leftEncoder, rightEncoder, centerEncoder;

    @Override
    public void runOpMode() {
        initHardware();
        while(!isStarted()) {
            sensorTelemetry();
        }
        waitForStart();
        while(opModeIsActive()) {
            sensorTelemetry();
        }
    }

    public void initHardware() {
        initEncoders();
    }

    public void initEncoders() {
        encoderHardwareMap();
        encoderDirection();
        stopAndResetEncoder();
    }

    public void encoderHardwareMap() {
        leftEncoder = hardwareMap.get(DcMotor.class, "MotorBL");
        rightEncoder = hardwareMap.get(DcMotor.class, "MotorBR");
        centerEncoder = hardwareMap.get(DcMotor.class, "MotorFL");
    }

    public void encoderDirection() {
        leftEncoder.setDirection(DcMotor.Direction.FORWARD);
        rightEncoder.setDirection(DcMotor.Direction.REVERSE);
        centerEncoder.setDirection(DcMotor.Direction.FORWARD);
    }

    public void stopAndResetEncoder() {
        leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        centerEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void sensorTelemetry() {
        telemetry.clearAll();
        telemetry.addData("leftEncoder: Forward is (+)", leftEncoder.getCurrentPosition());
        telemetry.addData("rightEncoder: Forward is (+)", rightEncoder.getCurrentPosition());
        telemetry.addLine("");
        telemetry.addLine("Use localizationTest to set centerEncoder direction.");
        telemetry.addData("centerEncoder", centerEncoder.getCurrentPosition());

        telemetry.update();
    }

}
