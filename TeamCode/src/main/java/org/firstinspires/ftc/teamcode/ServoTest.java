package org.firstinspires.ftc.teamcode;

import android.hardware.Sensor;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "ServoTest")


public class ServoTest extends OpMode {

    //private DcMotor dcmCarouselMotor1;
   // private DcMotor dcmFrontRightMotor;
  //  private DcMotor dcmBackLeftMotor;
  //  private DcMotor dcmBackRightMotor;
    private Servo srvArmServo;

    private static final double TICKSTOINCHES = (537.6 * 1) / (Math.PI * 3.77953);

    private boolean bolGSToggle = false;
    private boolean bolAWasPressed = false;
    private boolean bolCarouselToggle = false;
    private boolean bolXWasPressed = false;
    private boolean bolRBumperWasPressed = false;
    private boolean bolLBumperWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;

    private double dblCarouselSpeed;

    private double dblCarouselSpeedToggle;

    public void init(){
       // dcmCarouselMotor1 = hardwareMap.dcMotor.get("MotorFL");
      //  dcmFrontRightMotor = hardwareMap.dcMotor.get("MotorFR");
     //   dcmBackLeftMotor = hardwareMap.dcMotor.get("MotorBL");
     //   dcmBackRightMotor = hardwareMap.dcMotor.get("MotorBR");

        srvArmServo = hardwareMap.servo.get("ArmServo");
    }

    public void loop(){


        if(gamepad1.a){
            srvArmServo.setPosition(0.85);
        }
        if(gamepad1.b){
            srvArmServo.setPosition(0.15);
        }


        telemetry.addData("Servo", srvArmServo.getPosition());

        telemetry.update();
    }
}

