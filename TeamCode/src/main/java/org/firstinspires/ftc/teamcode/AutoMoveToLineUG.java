package org.firstinspires.ftc.teamcode;

//this program will move the block on the edge over to the building zone

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;


//start robot with camera facing toward the center of the field and as close to the Skybridge as possible

//@Autonomous(name = "AutoMoveToLineUG")


public class AutoMoveToLineUG extends OpMode {

    private DcMotor dcmFrontLeftMotor;
    private DcMotor dcmFrontRightMotor;
    private DcMotor dcmBackLeftMotor;
    private DcMotor dcmBackRightMotor;

    private DcMotor dcmFTMotor;
    private DcMotor dcmSTMotor;

    private DistanceSensor snsDistanceBack;
    private DistanceSensor snsDistanceLeft;
    private DistanceSensor snsDistanceRight;

    BNO055IMU imu;

    private MeasuredDistance msdMeasuredSkystoneDistance = new MeasuredDistance();



    private AutonomousSegment atsCurrentSegment;


    //this function will move forward the specified distance, use negative values on Distance only to go backwards, always keep Speed positive


    public void ResetChassisEncoder(){
        dcmFrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmFrontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmBackLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmBackRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmFrontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmFrontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmBackLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmBackLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void init(){
        dcmFrontLeftMotor = hardwareMap.dcMotor.get("MotorFL");
        dcmFrontRightMotor = hardwareMap.dcMotor.get("MotorFR");
        dcmBackLeftMotor = hardwareMap.dcMotor.get("MotorBL");
        dcmBackRightMotor = hardwareMap.dcMotor.get("MotorBR");
        dcmFrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmFrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        initImu();

        //initalize autonomous segments

        /*
        Negative values = left and backward
        Positive vales = right and forward
        */

        AutonomousSegment atsNextSegment;

        AutonomousSegment atsNewSegment;

        atsCurrentSegment = new AutonomousStrafeForwardSegment( 10, 0,  dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);

        atsNextSegment = atsCurrentSegment;


        atsNewSegment = new AutonomousStrafeSidewaysSegment(10, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
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