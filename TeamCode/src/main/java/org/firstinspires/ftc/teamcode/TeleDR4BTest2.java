package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleDR4BTest2")

@Disabled

public class TeleDR4BTest2 extends OpMode {

    //private CRServo srvLAServo; //Left Arm
    private CRServo srvRAServo; //Right Arm

    private Servo srvLSServo; // Left Slide
    private Servo srvRSServo; //Right Slide
    private double dblLSServoPos = 0.50;
    private double dblRSServoPos = 0.50;

    private Servo srvRCServo;
    private Servo srvCRServo; //Claw Rotatinator Servo
    private double dblCRServoPos = 0.50;

    private boolean bolCCToggle = false;
    private boolean bolXWasPressed = false;
    private boolean bolAWasPressed = false;

    private static final double TOP_SERVO_GRABBING_POSITION = 0.69;
    private static final double BOTTOM_SERVO_GRABBING_POSITION = 0.37;
    private static final double SERVO_MOVE_INTERVAL = 0.005;
    private static final double SLIDE_SERVO_MOVE_INTERVAL = 0.005;
    private static final double SLIDE_SERVO_OUT_POSITION = 1;

    public void init() {
        //srvLAServo = hardwareMap.crservo.get("ServoLA");
        srvRAServo = hardwareMap.crservo.get("ServoRA");

        srvLSServo = hardwareMap.servo.get("ServoLS");
        srvRSServo = hardwareMap.servo.get("ServoRS");

        srvRCServo = hardwareMap.servo.get("ServoRC");
        srvCRServo = hardwareMap.servo.get("ServoCR");
    }

    public void loop() {

       // srvLAServo.setPower(gamepad2.left_stick_y);
        srvRAServo.setPower(gamepad2.left_stick_y);

        if (gamepad2.a && !bolAWasPressed) {
            bolAWasPressed = true;
            bolCCToggle = !bolCCToggle;
            if (bolCCToggle) {
                srvLSServo.setPosition(SLIDE_SERVO_OUT_POSITION);
                srvRSServo.setPosition(SLIDE_SERVO_OUT_POSITION);
            } else {
                srvLSServo.setPosition(0);
                srvRSServo.setPosition(0);
            }
        } else if (!gamepad2.a) {
            bolAWasPressed = false;
        }

        //X Claw
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

       /*
        if(gamepad2.right_stick_y > 0){
            dblLSServoPos = dblLSServoPos + SLIDE_SERVO_MOVE_INTERVAL;
            dblRSServoPos = dblRSServoPos + SLIDE_SERVO_MOVE_INTERVAL;
        } else if(gamepad2.right_stick_y > 0){
            dblCRServoPos = dblCRServoPos - SLIDE_SERVO_MOVE_INTERVAL;
            dblRSServoPos = dblRSServoPos - SLIDE_SERVO_MOVE_INTERVAL;
        }
        */

        telemetry.update();

    }
}