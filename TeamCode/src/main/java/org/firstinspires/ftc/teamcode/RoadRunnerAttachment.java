package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorDigitalTouch;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RoadRunnerAttachment extends Object {


    private Gamepad gmpGamepad1;
    private Gamepad gmpGamepad2;
    private Telemetry telTelemetry;

    private DcMotorEx dcmSlider;

    private Servo srvGrabber;
    private Servo srvV4B;

    private SensorDigitalTouch tsLSGrabber;

    private DcMotorEx lteDirectionV4B;
    private DcMotorEx lteDirectionCHS;

    private ColorSensor snsColor2;

    private int intNumSameRecognitions = 0;
    private int intNumSameRecognitions2 = 0;
    private int intNumSameRecognitions3 = 0;


    public int intColorLevel = 0;

    private static final double CENTERANGLE = 0.51;
    private static final double ANGLEMODIFIERLOW = 0.467;
    private static final double ANGLEMODIFIERHIGH = 0.175;
    private static final double ANGLEMODIFIERLOWEST = 0.451;
    private static final double ANGLEMODIFIERLOWSTACK = 0.37;
    private double dblV4BAngleHigh = 0.675; //0.3755
    private double dblV4BAngleLow = 0.043;
    private double dblV4BAngleLowStack = 0.13;
    private double dblV4BAngleLowest = 0.951;//0.9365

    private double dblSlidePosition;
    private int intSlidePosition = 0;

    private double dblServoPosition = 0.5;

    private int intConeStack1;



    public RoadRunnerAttachment(HardwareMap hmpHardwareMap, Telemetry telTelemetry) {


        this.telTelemetry = telTelemetry;




        dcmSlider = hmpHardwareMap.get(DcMotorEx.class, "Slider");
        dcmSlider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSlider.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider.setDirection(DcMotor.Direction.FORWARD);



        srvV4B = hmpHardwareMap.servo.get("V4B");
        srvV4B.setPosition(CENTERANGLE);


        srvGrabber = hmpHardwareMap.servo.get("Grabber");
        srvGrabber.setPosition(1);

        snsColor2 = hmpHardwareMap.get(ColorSensor.class, "color_sensor2");



    }


    public void senseSleeve(){
        if(snsColor2.red() > (snsColor2.blue())){
            intColorLevel = 1;
        } else if(snsColor2.blue() > (snsColor2.green())){
            intColorLevel = 3;
        } else {
            intColorLevel = 2;
        }


    }

    public void moveSlidesM(){
        srvV4B.setPosition(CENTERANGLE);
        dcmSlider.setTargetPosition(-470);
        dcmSlider.setPower(0.85);
        dcmSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void moveSlidesDown(){
        srvV4B.setPosition(CENTERANGLE);
        dcmSlider.setTargetPosition(0);
        dcmSlider.setPower(0.85);
        dcmSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }



    public void moveJoeTest() throws InterruptedException{

        srvV4B.setPosition(0.325);
        Thread.sleep(400);
        srvGrabber.setPosition(0);
        Thread.sleep(400);
        srvV4B.setPosition(CENTERANGLE);
    }

    public void movePickUpPosition(){

        dcmSlider.setTargetPosition(-350);
        dcmSlider.setPower(0.85);
        dcmSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        srvV4B.setPosition(0.87);


    }

    public void movePickUpCone(int intConeStack1) throws InterruptedException{

        this.intConeStack1 = intConeStack1;

        dcmSlider.setTargetPosition(intConeStack1);
        dcmSlider.setPower(0.85);
        dcmSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Thread.sleep(400);

        srvGrabber.setPosition(1);

        Thread.sleep(200);

        dcmSlider.setTargetPosition(-350);
        dcmSlider.setPower(0.85);
        dcmSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Thread.sleep(100);


    }

    public int getintColorLevel() {
        return intColorLevel;
    }





}
