package org.firstinspires.ftc.teamcode;

//this program will move the block on the edge over to the building zone

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

@Autonomous(name = "AutoMoveWobbleGoalRedRootNeg")

public class AutoMoveWobbleGoalRedRootNeg extends OpMode {

    private DcMotor dcmFrontLeftMotor;
    private DcMotor dcmFrontRightMotor;
    private DcMotor dcmBackLeftMotor;
    private DcMotor dcmBackRightMotor;

    private DcMotor dcmLauncherMotor;
    private DcMotor dcmGrabberMotor;

    private Servo srvGrabberServo;
    private Servo srvLauncherServo;

    private long dblWaitParameter;

    VuforiaLocalizer vuforia;
    TFObjectDetector tfod;
    BNO055IMU imu;

    private MeasuredDistance msdMeasuredSkystoneDistance = new MeasuredDistance();

    private int intTfodMonitorViewId;

    private AutonomousSegment atsCurrentSegment;

    private AutonomousScanForRings asrScan;

    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    public String strNumber;

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

        dcmLauncherMotor = hardwareMap.get(DcMotor.class, "MotorLM");
        dcmLauncherMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmLauncherMotor.setDirection(DcMotor.Direction.REVERSE);

        dcmGrabberMotor = hardwareMap.get(DcMotor.class, "MotorGM");

        srvGrabberServo = hardwareMap.servo.get("GrabberServo");
        srvLauncherServo = hardwareMap.servo.get("LauncherServo");

        dcmFrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmFrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        initCameraObjects();
        initImu();

        srvGrabberServo.setPosition(0);

        //initalize autonomous segments

        /*
        Negative values = left and backward
        Positive vales = right and forward
        */

        asrScan = new AutonomousScanForRings(9, 0,  dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, tfod, vuforia, msdMeasuredSkystoneDistance, imu);


    }

    public void init_loop() {
            asrScan.runSegment();
    }

    public void start(){

        double dblStartRuntime = getRuntime();
        double dblScanTime = 0;

        while(!asrScan.segmentComplete() && dblScanTime < 7){
            asrScan.runSegment();
            dblScanTime = getRuntime() - dblStartRuntime;
        }

        strNumber = asrScan.getStrNumber();

        AutonomousSegment atsNextSegment;

        AutonomousSegment atsNewSegment;

        atsCurrentSegment = new AutonomousStrafeForwardSegment(10,0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);

        atsNextSegment = atsCurrentSegment;

        atsNewSegment = new AutonomousGrabberSegment(0, srvGrabberServo);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeSidewaysSegment(18, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousSpinLauncherSegment( 1800, dcmLauncherMotor, telemetry);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeForwardSegment(42, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousSpinLauncherSegment( 1900, dcmLauncherMotor, telemetry);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousShootLauncherSegment( 0.5, 2200, srvLauncherServo, telemetry);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousShootLauncherSegment(0, 0, srvLauncherServo, telemetry);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeForwardSegment(10, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;
/*
        atsNewSegment = new AutonomousSpinLauncherSegment( 1800, dcmLauncherMotor, telemetry);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeSidewaysSegment(-7, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousShootLauncherSegment( 0.5, 1000,  srvLauncherServo, telemetry);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousShootLauncherSegment(0, 0, srvLauncherServo, telemetry);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousSpinLauncherSegment( 1900, dcmLauncherMotor, telemetry);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeSidewaysSegment(-9, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousShootLauncherSegment( 0.5, 1000,  srvLauncherServo, telemetry);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousShootLauncherSegment(0, 0, srvLauncherServo, telemetry);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;




        if (strNumber == "Quad") {

                atsNewSegment = new AutonomousStrafeSidewaysSegment(-28, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousStrafeForwardSegment(40, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousGrabberMotorSegment(-55, dcmGrabberMotor, telemetry);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousShootLauncherSegment(0, 500, srvLauncherServo, telemetry);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousGrabberSegment(1, srvGrabberServo);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousGrabberMotorSegment(40, dcmGrabberMotor, telemetry);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousStrafeForwardSegment(-27, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousGrabberMotorSegment(-15, dcmGrabberMotor, telemetry);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;


        } else if (strNumber == "Single") {

                atsNewSegment = new AutonomousStrafeSidewaysSegment(-10, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousStrafeForwardSegment(22, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousGrabberMotorSegment(-55, dcmGrabberMotor, telemetry);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousShootLauncherSegment(0, 500, srvLauncherServo, telemetry);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousGrabberSegment(1, srvGrabberServo);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousGrabberMotorSegment(40, dcmGrabberMotor, telemetry);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousStrafeForwardSegment(-5, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousGrabberMotorSegment(-15, dcmGrabberMotor, telemetry);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

        } else {

                atsNewSegment = new AutonomousStrafeSidewaysSegment(-22, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousStrafeForwardSegment(-2, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousGrabberMotorSegment(-55, dcmGrabberMotor, telemetry);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousShootLauncherSegment(0, 500, srvLauncherServo, telemetry);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousGrabberSegment(1, srvGrabberServo);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousGrabberMotorSegment(40, dcmGrabberMotor, telemetry);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousGrabberSegment(0, srvGrabberServo);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousStrafeForwardSegment(8, 0, dcmFrontLeftMotor, dcmFrontRightMotor, dcmBackLeftMotor, dcmBackRightMotor, telemetry, imu);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

                atsNewSegment = new AutonomousGrabberMotorSegment(-15, dcmGrabberMotor, telemetry);
                atsNextSegment.setNextSegment(atsNewSegment);
                atsNextSegment = atsNewSegment;

        }

 */

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

        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);

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