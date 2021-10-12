package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SkystoneAttachment extends Object {

    private Gamepad gmpGamepad1;
    private Gamepad gmpGamepad2;
    private Telemetry telTelemetry;

    private DcMotor dcmRAMotor;
    private Servo srvRCServo;
    private Servo srvCRServo; //Claw Rotator Servo
    private Servo srvLFGServo;
    private Servo srvRFGServo;
    private double dblCRServoPos = 0.50;

    private DcMotor dcmFTMotor;
    private DcMotor dcmSTMotor;

    private boolean bolCCToggle = false;
    private boolean bolXWasPressed = false;
    private boolean bolRWasPressed = false;

    //Preset Position variables
    private boolean bolRunPresetPos = false;
    private boolean bolGoTravel = false;
    private boolean bolGoPickup = false;
    private boolean bolGoPlacing = false;

    private static final int TRAVEL_POSITION = 0;
    private static final int PICKUP_POSITION = -325;
    private static final int PLACING_POSITION = 3100;
    private double dblArmPower = 0;
    private static final double PRESET_POSITIONS_POWER = 1;
    private static final int PRESET_POSITIONS_ERROR_ROOM = 50;
    private static final double TOP_SERVO_GRABBING_POSITION = 0.69;
    private static final double BOTTOM_SERVO_GRABBING_POSITION = 0.37;
    private static final double SERVO_MOVE_INTERVAL = 0.005;
    private static final double LFG_GRABBING_POSITION = 1;
    private static final double RFG_GRABBING_POSITION = -1;

    public SkystoneAttachment(Gamepad gmpGamepad1, Gamepad gmpGamepad2, HardwareMap hmpHardwareMap, Telemetry telTelemetry) {

        this.gmpGamepad1 = gmpGamepad1;
        this.gmpGamepad2 = gmpGamepad2;
        this.telTelemetry = telTelemetry;

        //the intake mechanism is now called "The Twin Tortellini Spinner"
        //FT is First Tortellini, ST is Second Tortellini
        dcmFTMotor = hmpHardwareMap.dcMotor.get("MotorFT");
        dcmSTMotor = hmpHardwareMap.dcMotor.get("MotorST");

        //The Claw is called "The Rigatoni Claw"
        //RC = Rigatoni Claw, RA = Rigatoni Arm
        dcmRAMotor = hmpHardwareMap.dcMotor.get("MotorRA");

        dcmRAMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        srvRCServo = hmpHardwareMap.servo.get("ServoRC");
        srvCRServo = hmpHardwareMap.servo.get("ServoCR");

        srvLFGServo = hmpHardwareMap.servo.get("ServoLFG");
        srvRFGServo = hmpHardwareMap.servo.get("ServoRFG");
    }

    public void moveAttachments() {

        dblArmPower = 0;

        if(gmpGamepad2.dpad_up){
            dcmRAMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcmRAMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        if (gmpGamepad1.right_trigger > 0) {
            dcmFTMotor.setPower(-gmpGamepad1.right_trigger);
            dcmSTMotor.setPower(gmpGamepad1.right_trigger);
        } else {
            dcmFTMotor.setPower(gmpGamepad1.left_trigger);
            dcmSTMotor.setPower(-gmpGamepad1.left_trigger);
        }

        if (gmpGamepad1.right_bumper && !bolRWasPressed) {
            bolRWasPressed = true;
            bolCCToggle = !bolCCToggle;
            if (bolCCToggle) {
                srvLFGServo.setPosition(LFG_GRABBING_POSITION);
                srvRFGServo.setPosition(RFG_GRABBING_POSITION);
            } else {
                srvLFGServo.setPosition(-0.8);
                srvRFGServo.setPosition(0.8);
            }
        } else if (!gmpGamepad1.right_bumper) {
            bolRWasPressed = false;
        }

        if (gmpGamepad2.x && !bolXWasPressed) {
            bolXWasPressed = true;
            bolCCToggle = !bolCCToggle;
            if (bolCCToggle) {
                dblCRServoPos = TOP_SERVO_GRABBING_POSITION;
                srvRCServo.setPosition(BOTTOM_SERVO_GRABBING_POSITION);
            } else {
                dblCRServoPos = dblCRServoPos - 0.19;
                srvRCServo.setPosition(0);
            }
        } else if (!gmpGamepad2.x) {
            bolXWasPressed = false;
        }

        if(gmpGamepad2.right_trigger > 0){
            dblCRServoPos = dblCRServoPos + SERVO_MOVE_INTERVAL;
        } else if(gmpGamepad2.left_trigger > 0){
            dblCRServoPos = dblCRServoPos - SERVO_MOVE_INTERVAL;
        }

        srvCRServo.setPosition(dblCRServoPos);

        //Preset Positions code


        if(gmpGamepad2.a && !bolGoTravel && !bolRunPresetPos){
            bolRunPresetPos = true;
            bolGoTravel = true;
            dblCRServoPos = 0.5;
        }else if(gmpGamepad2.a && bolGoTravel && !bolRunPresetPos){
            bolRunPresetPos = true;
            bolGoPickup = true;
            bolGoTravel = false;
        }


        if(gmpGamepad2.y){
            bolRunPresetPos = true;
            bolGoPlacing = true;
        }


        if (gmpGamepad2.left_stick_y > 0 || gmpGamepad2.left_stick_y < 0) {
            dblArmPower = gmpGamepad2.left_stick_y;
            bolRunPresetPos = false;
            bolGoTravel = false;
            bolGoPickup = false;
            bolGoPlacing = false;
        }

        if(bolRunPresetPos){
            if(bolGoTravel){
                if(dcmRAMotor.getCurrentPosition() < (TRAVEL_POSITION - PRESET_POSITIONS_ERROR_ROOM)){
                    dblArmPower = -PRESET_POSITIONS_POWER;
                }else if(dcmRAMotor.getCurrentPosition() > (TRAVEL_POSITION + PRESET_POSITIONS_ERROR_ROOM)){
                    dblArmPower = PRESET_POSITIONS_POWER;
                }else {
                    dblArmPower = 0;
                    bolRunPresetPos = false;
                }
            }else if(bolGoPickup){
                if(dcmRAMotor.getCurrentPosition() < (PICKUP_POSITION - PRESET_POSITIONS_ERROR_ROOM)){
                    dblArmPower = -PRESET_POSITIONS_POWER;
                }else if(dcmRAMotor.getCurrentPosition() > (PICKUP_POSITION + PRESET_POSITIONS_ERROR_ROOM)){
                    dblArmPower = PRESET_POSITIONS_POWER;
                }else {
                    dblArmPower = 0;
                    bolRunPresetPos = false;
                    bolGoPickup = false;
                    bolGoTravel = false;
                }
            }else if (bolGoPlacing){
                if(dcmRAMotor.getCurrentPosition() < (PLACING_POSITION - PRESET_POSITIONS_ERROR_ROOM)){
                    dblArmPower = -PRESET_POSITIONS_POWER;
                }else if(dcmRAMotor.getCurrentPosition() > (PLACING_POSITION + PRESET_POSITIONS_ERROR_ROOM)){
                    dblArmPower = PRESET_POSITIONS_POWER;
                }else {
                    dblArmPower = 0;
                    bolRunPresetPos = false;
                    bolGoPickup = false;
                    bolGoTravel = false;
                }
            }
        }


        dcmRAMotor.setPower(dblArmPower);
        telTelemetry.addData("ARM POSITION", dcmRAMotor.getCurrentPosition());
        telTelemetry.addData("SERVO POSITION", dblCRServoPos);
        telTelemetry.update();

    }

}
