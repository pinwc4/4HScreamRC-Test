package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleDR4BTest")

@Disabled

public class TeleDR4BTest extends OpMode {

   // private CRServo srvLAServo; //Left Arm
    private CRServo srvRAServo; //Right Arm

    private CRServo srvLSServo; //Left Slide
    private CRServo srvRSServo; //Right Slide

    private CRServo dcmITMotor;

    private Servo srvRCServo;
    private Servo srvCRServo; //Claw Rotatinator Servo
    private double dblCRServoPos = 0.50;

    private boolean bolCCToggle = false;
    private boolean bolXWasPressed = false;

    private static final double TOP_SERVO_GRABBING_POSITION = 0.69;
    private static final double BOTTOM_SERVO_GRABBING_POSITION = 0.37;
    private static final double SERVO_MOVE_INTERVAL = 0.005;

    public void init() {
        //srvLAServo = hardwareMap.crservo.get("ServoLA");
        srvRAServo = hardwareMap.crservo.get("ServoRA");

        srvLSServo = hardwareMap.crservo.get("ServoLS");
        srvRSServo = hardwareMap.crservo.get("ServoRS");

        srvRCServo = hardwareMap.servo.get("ServoRC");
        srvCRServo = hardwareMap.servo.get("ServoCR");

        dcmITMotor = hardwareMap.crservo.get("MotorIT");
    }

    public void loop() {

       // srvLAServo.setPower(gamepad2.left_stick_y);
        srvRAServo.setPower(gamepad2.left_stick_y);

        srvLSServo.setPower(gamepad2.right_stick_y);
        srvRSServo.setPower(gamepad2.right_stick_y);

        dcmITMotor.setPower(gamepad1.right_stick_y);

        if (gamepad2.x && !bolXWasPressed) {
            bolXWasPressed = true;
            bolCCToggle = !bolCCToggle;
            if (bolCCToggle) {
                dblCRServoPos = TOP_SERVO_GRABBING_POSITION;
                srvRCServo.setPosition(BOTTOM_SERVO_GRABBING_POSITION);
            } else {
                dblCRServoPos = dblCRServoPos - 0.19;
                srvRCServo.setPosition(0);
            }
        } else if (!gamepad2.x) {
            bolXWasPressed = false;
        }
        if(gamepad2.right_trigger > 0){
            dblCRServoPos = dblCRServoPos + SERVO_MOVE_INTERVAL;
        } else if(gamepad2.left_trigger > 0){
            dblCRServoPos = dblCRServoPos - SERVO_MOVE_INTERVAL;
        }

        srvCRServo.setPosition(dblCRServoPos);

        //telemetry.addData("SERVO POWER LEFT", srvLAServo.getPower());
        telemetry.addData("SERVO POWER RIGHT", srvRAServo.getPower());

        telemetry.addData("SLIDE POWER LEFT", srvLSServo.getPower());
        telemetry.addData("SLIDE POWER RIGHT", srvRSServo.getPower());

        telemetry.update();

    }
}