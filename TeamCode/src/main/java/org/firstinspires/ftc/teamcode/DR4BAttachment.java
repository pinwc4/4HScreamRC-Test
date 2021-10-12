package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.TouchSensorMultiplexer;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorDigitalTouch;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DR4BAttachment extends Object {

    private Gamepad gmpGamepad1;
    private Gamepad gmpGamepad2;
    private Telemetry telTelemetry;

    private CRServo srvRAServo; //Right Arm

    private Servo srvLSServo; //Left Slide
    private CRServo srvRSServo; //Right Slide

    private Servo srvRCServo;
    private Servo srvCRServo; //Claw Rotatinator Servo
    private Servo srvLFGServo;
    private Servo srvRFGServo;

    private double dblCRServoPos = 0.50;
    private double dblRCServoPos = 0.50;

    private CRServo dcmITMotor;

    private TouchSensor snsISensor;


    private boolean bolCCToggle = false;
    private boolean bolCXToggle = false;
    private boolean bolCYToggle = false;
    private boolean bolXWasPressed = false;
    private boolean bolRWasPressed = false;
    private boolean bolAWasPressed = false;
    private boolean bolYWasPressed = false;
    private boolean bolLeftStickWasPressed = false;
    private boolean bolLSToggle = false;

    private double dblLSServoPos = 0.50;
    private double dblRSServoPos = 0.50;

    private static final double SLIDE_SERVO_OUT_POSITION = 1;
    private static final double TOP_SERVO_X_GRABBING_POSITION = 0.69;
    private static final double TOP_SERVO_Y_GRABBING_POSITION = 0.94; // 36?
    private static final double BOTTOM_SERVO_X_GRABBING_POSITION = 0.67; //0.37
    private static final double BOTTOM_SERVO_Y_GRABBING_POSITION = 0.42; // 1.0?
    private static final double SERVO_MOVE_INTERVAL = 0.005;
    private static final double SLIDE_SERVO_MOVE_INTERVAL = 0.005;
    private static final double LFG_GRABBING_POSITION = 1;
    private static final double RFG_GRABBING_POSITION = -1;

    public DR4BAttachment(Gamepad gmpGamepad1, Gamepad gmpGamepad2, HardwareMap hmpHardwareMap, Telemetry telTelemetry) {

        this.gmpGamepad1 = gmpGamepad1;
        this.gmpGamepad2 = gmpGamepad2;
        this.telTelemetry = telTelemetry;

        srvRAServo = hmpHardwareMap.crservo.get("ServoRA");

        //srvLSServo = hmpHardwareMap.servo.get("ServoLS");
        srvRSServo = hmpHardwareMap.crservo.get("ServoRS");

        //the intake mechanism is now called "The Twin Tortellini Spinner"
        //FT is First Tortellini, ST is Second Tortellini
        dcmITMotor = hmpHardwareMap.crservo.get("MotorIT");


        //RC = Rigatoni Claw
        srvRCServo = hmpHardwareMap.servo.get("ServoRC");
        srvCRServo = hmpHardwareMap.servo.get("ServoCR");

        srvRFGServo = hmpHardwareMap.servo.get("ServoRFG");

        snsISensor = hmpHardwareMap.touchSensor.get("IntakeSensor");
    }

    public void moveAttachments() {

        if (gmpGamepad1.right_bumper && !bolRWasPressed) {
            bolRWasPressed = true;
            bolCCToggle = !bolCCToggle;
            if (bolCCToggle) {
                srvRFGServo.setPosition(RFG_GRABBING_POSITION);
            } else {
                srvRFGServo.setPosition(0.8);
            }
        } else if (!gmpGamepad1.right_bumper) {
            bolRWasPressed = false;
        }

        if (gmpGamepad2.a && !bolLeftStickWasPressed) {
            bolLeftStickWasPressed = true;
            bolLSToggle = !bolLSToggle;
        } else if (!gmpGamepad1.left_stick_button) {
            bolLeftStickWasPressed = false;
        }
        if (bolLSToggle) {
            srvRAServo.setPower(-gmpGamepad2.left_stick_y / 4);
        } else {
            srvRAServo.setPower(-gmpGamepad2.left_stick_y);
        }

        srvRSServo.setPower(-gmpGamepad2.right_stick_y);

        telTelemetry.addData("SensorPressed", snsISensor.isPressed());

        //Intake
        if(snsISensor.isPressed()){
            dcmITMotor.setPower(0);
        }
        else if (gmpGamepad1.right_trigger > 0) {
            dcmITMotor.setPower(gmpGamepad1.right_trigger);
        } else {
            dcmITMotor.setPower(-gmpGamepad1.left_trigger);
        }

        //X Claw
        if (gmpGamepad2.x && !bolXWasPressed) {
            bolXWasPressed = true;
            bolCCToggle = !bolCCToggle;
            if (bolCCToggle) {
                dblCRServoPos = TOP_SERVO_X_GRABBING_POSITION;
                dblRCServoPos = BOTTOM_SERVO_X_GRABBING_POSITION;
            } else {
                dblCRServoPos = dblCRServoPos - 0.19;
                dblRCServoPos = 0.50;
            }
        }

        if (gmpGamepad2.y && !bolYWasPressed && bolCCToggle) {
            bolYWasPressed = true;
            bolCYToggle = !bolCYToggle;
            if (bolCYToggle) {
                dblCRServoPos = TOP_SERVO_Y_GRABBING_POSITION;
                dblRCServoPos = BOTTOM_SERVO_Y_GRABBING_POSITION;
            } else {
                dblCRServoPos = TOP_SERVO_X_GRABBING_POSITION;
                dblRCServoPos = BOTTOM_SERVO_X_GRABBING_POSITION;
            }
        }

        if (!gmpGamepad2.y) {
            bolYWasPressed = false;

        } if (!gmpGamepad2.x) {
            bolXWasPressed = false;

            if (gmpGamepad2.right_trigger > 0) {
                dblCRServoPos = dblCRServoPos + SERVO_MOVE_INTERVAL;
                dblRCServoPos = dblRCServoPos - SERVO_MOVE_INTERVAL;
            } else if (gmpGamepad2.left_trigger > 0) {
                dblCRServoPos = dblCRServoPos - SERVO_MOVE_INTERVAL;
                dblRCServoPos = dblRCServoPos + SERVO_MOVE_INTERVAL;
            }

            srvCRServo.setPosition(dblCRServoPos);
            srvRCServo.setPosition(dblRCServoPos);


         /*
        if (gmpGamepad2.a && !bolAWasPressed) {
            bolAWasPressed = true;
            bolCXToggle = !bolCXToggle;
            if (bolCXToggle) {
                //srvLSServo.setPosition(SLIDE_SERVO_OUT_POSITION);
                srvRSServo.setPosition(SLIDE_SERVO_OUT_POSITION);
            } else {
                //srvLSServo.setPosition(0);
                srvRSServo.setPosition(0);
            }
        } else if (!gmpGamepad2.a) {
            bolAWasPressed = false;
        }


        if (gmpGamepad2.right_stick_y > 0) {
            dblRSServoPos = dblRSServoPos + SLIDE_SERVO_MOVE_INTERVAL;
        } else if (gmpGamepad2.right_stick_y < 0) {
            dblRSServoPos = dblRSServoPos - SLIDE_SERVO_MOVE_INTERVAL;
        }

         */


        /*
        else if (gmpGamepad2.y && !bolYWasPressed) {
            bolYWasPressed = true;
            bolCYToggle = !bolCYToggle;
            if (bolCYToggle) {
                dblCRServoPos = TOP_SERVO_X_GRABBING_POSITION + 90;
            }
        }else if (gmpGamepad2.x && bolYWasPressed){
            bolCXToggle = !bolCXToggle;
            if(bolCXToggle){
                dblCRServoPos = TOP_SERVO_X_GRABBING_POSITION;
            }

         */
        //}


            telTelemetry.addData("SERVO POSITION", dblCRServoPos);
            telTelemetry.addData("2nd SERVO POSITION", srvRCServo.getPosition());
            telTelemetry.update();

        }

    }
}
