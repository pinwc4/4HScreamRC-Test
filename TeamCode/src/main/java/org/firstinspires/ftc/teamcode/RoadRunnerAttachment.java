package org.firstinspires.ftc.teamcode;

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

    private int intNumSameRecognitions = 0;
    private int intNumSameRecognitions2 = 0;
    private int intNumSameRecognitions3 = 0;

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

    private int intConeStack1 = -115;
    private int intConeStack2 = -150;

    private boolean bolAWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;
    private boolean bolX2WasPressed = false;
    private boolean bolLBWasPressed = false;
    private boolean bolRB2WasPressed = false;
    private boolean bolRB1WasPressed = false;
    private boolean bolX1WasPressed = false;
    private boolean bolLB1WasPressed = false;
    private boolean bolDPadDownWasPressed = false;
    private boolean bolDPadUpWasPressed = false;

    private boolean bolGRB1Toggle = false;
    private boolean bolGRB2Toggle = false;
    private boolean bolGRB3Toggle = false;
    private boolean bolGRB4Toggle = false;

    private boolean bolSGRB1Toggle = false;
    private boolean bolSGRB2Toggle = false;
    private boolean bolSGRB3Toggle = false;


    private boolean bolSideToggle = false;
    private boolean bolCLToggle = true;
    private boolean bolGMAToggle = false;
    private boolean bolGMBToggle = false;
    private boolean bolGMYToggle = false;
    private boolean bolRBToggle = false;
    private boolean bolTToggle = false;
    private boolean bolT2Toggle = false;
    private boolean bolT3Toggle = false;
    private boolean bolT4Toggle = false;
    private boolean bolSTToggle = false;
    private boolean bolSHToggle = false;
    private boolean bolLFToggle = false;
    private boolean bolCHS = false;

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



    }



    public void moveSlidesM(){
        dcmSlider.setTargetPosition(-450);
        dcmSlider.setPower(0.85);
        dcmSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }



    public void moveJoeTest() throws InterruptedException{

        srvV4B.setPosition(0.325);
        Thread.sleep(500);
        srvGrabber.setPosition(0);
        Thread.sleep(500);
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

        intSlidePosition = intConeStack1;
        Thread.sleep(500);
        srvGrabber.setPosition(1);
        Thread.sleep(500);
        intSlidePosition = -350;

        /*
        bolSGRB1Toggle = true;

        if(dcmSlider.getCurrentPosition() > intConeStack1 - 20 && bolSGRB1Toggle){

            srvGrabber.setPosition(1);
            bolSGRB1Toggle = false;
            bolSGRB2Toggle = true;

            bolT2Toggle = true;
        }

        if(bolT2Toggle){
            if(intNumSameRecognitions2 < 20){
                intNumSameRecognitions2++;
                telTelemetry.addLine("One");
            }
            else {
                intSlidePosition = -350;
                intNumSameRecognitions2 = 0;
                telTelemetry.addLine("Two");
                bolT2Toggle = false;
                bolSGRB2Toggle = true;
                dblServoPosition = CENTERANGLE;
            }
        }

         */

    }













}
