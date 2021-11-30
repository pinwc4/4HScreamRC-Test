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

    //private DcMotor dcmCarouselMotor1;
   // private DcMotor dcmFrontRightMotor;
  //  private DcMotor dcmBackLeftMotor;
  //  private DcMotor dcmBackRightMotor;
    private TouchSensor snsTouchTest;
    private Sensor snsSense;
    private DigitalChannel snsDigital;
    private AnalogInput snsAnalog;

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

        snsTouchTest = hardwareMap.touchSensor.get("TestTouch");
        //snsSense = hardwareMap.get(Sensor.class, "Sense");
        //nsDigital = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        snsAnalog = hardwareMap.analogInput.get("Analog");
    }

    public void loop(){



        telemetry.addData("TestTouchValue", snsTouchTest.getValue());
        telemetry.addData("TestTouch", snsTouchTest.isPressed());

        //telemetry.addData("TestTouchValue", snsSense.getPower());
        telemetry.addData("Analog", snsAnalog.getVoltage());

        telemetry.update();
    }
}

