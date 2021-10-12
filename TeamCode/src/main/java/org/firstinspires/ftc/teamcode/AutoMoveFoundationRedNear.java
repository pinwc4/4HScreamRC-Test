package org.firstinspires.ftc.teamcode;

//this program will move the block on the edge over to the building zone

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;


//start robot with camera facing toward the center of the field and as close to the Skybridge as possible

@Autonomous(name = "AutoMoveFoundationRedNear")
@Disabled
public class AutoMoveFoundationRedNear extends OpMode {

    private DcMotor dcmFrontLeftMotor;
    private DcMotor dcmFrontRightMotor;
    private DcMotor dcmBackLeftMotor;
    private DcMotor dcmBackRightMotor;

    private Servo srvLFGServo;
    private Servo srvRFGServo;

    private DcMotor dcmFTMotor;
    private DcMotor dcmSTMotor;

    VuforiaLocalizer vuforia;
    TFObjectDetector tfod;
    BNO055IMU imu;

    private MeasuredDistance msdMeasuredSkystoneDistance = new MeasuredDistance();

    private int intTfodMonitorViewId;

    private AutonomousSegment atsCurrentSegment;

    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_STONE = "Stone";
    private static final String LABEL_SKYSTONE = "Skystone";

    //this function will move forward the specified distance, use negative values on Distance only to go backwards, always keep Speed positive


    public void ResetChassisEncoder(){
        dcmFrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmFrontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void init(){
        dcmFrontLeftMotor = hardwareMap.dcMotor.get("MotorFL");
        dcmFrontRightMotor = hardwareMap.dcMotor.get("MotorFR");
        dcmBackLeftMotor = hardwareMap.dcMotor.get("MotorBL");
        dcmBackRightMotor = hardwareMap.dcMotor.get("MotorBR");
        dcmFTMotor = hardwareMap.dcMotor.get("MotorFT");
        dcmSTMotor = hardwareMap.dcMotor.get("MotorST");
        dcmFrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmFrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        srvLFGServo = hardwareMap.servo.get("ServoLFG");
        srvRFGServo = hardwareMap.servo.get("ServoRFG");

        initCameraObjects();
        initImu();

        //initalize autonomous segments

        /*
        Negative values = left and backward
        Positive vales = right and forward
        */

        AutonomousSegment atsNextSegment;

        AutonomousSegment atsNewSegment;

        atsCurrentSegment = new AutonomousStrafeForwardSegment(12, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);

        atsNextSegment = atsCurrentSegment;

        atsNewSegment = new AutonomousStrafeSidewaysSegment(30, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeForwardSegment(24, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousFoundationGrabSegment(1, srvLFGServo, srvRFGServo);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeForwardSegment(4, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeTurnSegment(-90, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeForwardSegment(28, -90, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeSidewaysSegment(5, -90, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeSidewaysSegment(-10, -90, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousFoundationGrabSegment(-1, srvLFGServo, srvRFGServo);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeForwardSegment(2, -90, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeForwardSegment(-21, -90, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeSidewaysSegment(26, -90, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeForwardSegment(-20, -90, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
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

    public void initCameraObjects(){

        //initalize Vuforia
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "AftxbQr/////AAABmQvhWKx8Ok8fiwDdGWphaPsqGLOjlBaX5uyms1XLxF4mODVA3S3WH8/rgWV4hXAMV0cFMid2OOnFgiki5bwjmVNnyd9aPQnt8F6ajQN2epL1407zvC01z+TK5Dm3fLomAJMdnNbq31l+QlnDED9GMsftphNqNchUnI/QtDPU0/qzPohbEfui6sXzOoc6H+iJcg2gyrDwcen544nIDVH7tDEx72xjaSdYf96f7i6vAm6UyXlegp0HjvBFd3aqyhVC6PcG+HS+PHW2iFwsTL5PZ5Gme9oK6rjzfHxAoyHgxZoMdHuCbD59mZAJcwyGTELLXxOWXCuDGhyZP/nxnQEK+Hckg3D8QeY921oQLaKmru3M";

        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);



        //initalize Tensor Flow

        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(intTfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_STONE, LABEL_SKYSTONE);

        if (tfod != null) {
            tfod.activate();
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