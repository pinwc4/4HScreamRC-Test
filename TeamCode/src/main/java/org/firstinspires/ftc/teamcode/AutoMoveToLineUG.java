package org.firstinspires.ftc.teamcode;

//this program will move the block on the edge over to the building zone

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;


//start robot with camera facing toward the center of the field and as close to the Skybridge as possible

@Autonomous(name = "AutoMoveToLineUG")

public class AutoMoveToLineUG extends OpMode {

    private DcMotorEx dcmFrontLeftMotor;
    private DcMotorEx dcmFrontRightMotor;
    private DcMotorEx dcmBackLeftMotor;
    private DcMotorEx dcmBackRightMotor;

    private DcMotor dcmFTMotor;
    private DcMotor dcmSTMotor;


    BNO055IMU imu;

    private MeasuredDistance msdMeasuredSkystoneDistance = new MeasuredDistance();



    private AutonomousSegment atsCurrentSegment;


    //this function will move forward the specified distance, use negative values on Distance only to go backwards, always keep Speed positive


    public void ResetChassisEncoder(){
        dcmFrontLeftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        dcmFrontLeftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        dcmFrontRightMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        dcmFrontRightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        dcmBackLeftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        dcmBackLeftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        dcmBackRightMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        dcmBackRightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void init(){
        dcmFrontLeftMotor = hardwareMap.get(DcMotorEx.class, "MotorFL");
        dcmFrontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dcmFrontRightMotor = hardwareMap.get(DcMotorEx.class, "MotorFL");
        dcmFrontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dcmBackLeftMotor = hardwareMap.get(DcMotorEx.class, "MotorFL");
        dcmBackLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dcmBackRightMotor = hardwareMap.get(DcMotorEx.class, "MotorFL");
        dcmBackRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        dcmFrontLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        dcmFrontRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        dcmBackLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        dcmBackRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);


        initImu();

        //initalize autonomous segments

        /*
        Negative values = left and backward
        Positive vales = right and forward
        */

        AutonomousSegment atsNextSegment;

        AutonomousSegment atsNewSegment;

        atsCurrentSegment = new AutonomousStrafeForwardSegment( 67, 0,  dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);

        atsNextSegment = atsCurrentSegment;

        atsNewSegment = new AutonomousStrafeSidewaysSegment(0, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;
    }


    public void loop(){

        if (!atsCurrentSegment.segmentComplete()) {
            atsCurrentSegment.runSegment();
        } else if (atsCurrentSegment.segmentComplete()) {
            ResetChassisEncoder();
            atsCurrentSegment = atsCurrentSegment.getNextSegment();
            if (atsCurrentSegment == null){
                requestOpModeStop();
            }
        }


    }


    public void initImu(){
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Start the logging of measured acceleration
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
    }
}