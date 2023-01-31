package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;


//start robot with camera facing toward the center of the field and as close to the Skybridge as possible

@Autonomous(name = "PowerMoveTest")

public class AutoPowerMoveTest extends OpMode {

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;

    private Servo srvGrabber;
    private CRServo dcmCSMotor;

    private DcMotorEx dcmSlider1;

    private Servo srvV4B;

    private long dblWaitParameter;

    VuforiaLocalizer vuforia;
    TFObjectDetector tfod;
    BNO055IMU imu;

    private int intTfodMonitorViewId;

    private AutonomousSegment atsCurrentSegment;

    private AutonomousScanForSleeve ascSleeve;

    private static final String TFOD_MODEL_ASSET = "model_20221110_195128.tflite";

    private static final String[] LABELS = {
/*
            "Bulb",
            "Bolt",
            "Green Tag",
            "Panel",
            "Purple Tag",
            "Yellow Tag"
 */
            "Green tag",
            "Purple tag",
            "Yellow tag"
    };

    private static final String VUFORIA_KEY =
            "AftxbQr/////AAABmQvhWKx8Ok8fiwDdGWphaPsqGLOjlBaX5uyms1XLxF4mODVA3S3WH8/rgWV4hXAMV0cFMid2OOnFgiki5bwjmVNnyd9aPQnt8F6ajQN2epL1407zvC01z+TK5Dm3fLomAJMdnNbq31l+QlnDED9GMsftphNqNchUnI/QtDPU0/qzPohbEfui6sXzOoc6H+iJcg2gyrDwcen544nIDVH7tDEx72xjaSdYf96f7i6vAm6UyXlegp0HjvBFd3aqyhVC6PcG+HS+PHW2iFwsTL5PZ5Gme9oK6rjzfHxAoyHgxZoMdHuCbD59mZAJcwyGTELLXxOWXCuDGhyZP/nxnQEK+Hckg3D8QeY921oQLaKmru3M";


    public String strSecondNumber;

    //this function will move forward the specified distance, use negative values on Distance only to go backwards, always keep Speed positive


    public void ResetChassisEncoder(){
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void init(){
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");

        srvGrabber = hardwareMap.servo.get("Grabber");

        srvV4B = hardwareMap.servo.get("V4B");

        /*
        dcmSlider1 = hardwareMap.get(DcMotorEx.class, "MotorGM");
        dcmSlider1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSlider1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider1.setDirection(DcMotor.Direction.FORWARD);

        dcmCSMotor = hardwareMap.crservo.get("SpinnerMotor");

        srvBucketServo = hardwareMap.servo.get("BucketServo");
        srvBucketServo.setPosition(0.85);

        dcmFrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmFrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

         */

        initVuforia();
        initTfod();
        initImu();

        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(2, 9.0/16.0);
        }


        //initalize autonomous segments

        /*
        Negative values = left and backward
        Positive vales = right and forward
        */

        ascSleeve = new AutonomousScanForSleeve(telemetry, tfod, vuforia);


    }

    public void init_loop() {
        ascSleeve.runSegment();
    }

    public void start(){

        double dblStartRuntime = getRuntime();
        double dblScanTime = 0;

/*
        while(!ascSleeve.segmentComplete() && dblScanTime < 7){
            ascSleeve.runSegment();
            dblScanTime = getRuntime() - dblStartRuntime;
        }

 */

        strSecondNumber = ascSleeve.getStrNumber();

        AutonomousSegment atsNextSegment;

        AutonomousSegment atsNewSegment;

        atsCurrentSegment = new AutonomousGrabberSegment(1, srvGrabber);

        atsNextSegment = atsCurrentSegment;

        atsNewSegment = new AutonomousV4BSegment(0.5, srvV4B);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;

        atsNewSegment = new AutonomousStrafeForwardSegment(1, 0, leftFront, rightFront, leftRear, rightRear, telemetry, imu);
        atsNextSegment.setNextSegment(atsNewSegment);
        atsNextSegment = atsNewSegment;


        if (strSecondNumber.equals("Green tag")) {

            atsNewSegment = new AutonomousStrafeSidewaysSegment(-17, 0, leftFront, rightFront, leftRear, rightRear, telemetry, imu);
            atsNextSegment.setNextSegment(atsNewSegment);
            atsNextSegment = atsNewSegment;

            atsNewSegment = new AutonomousStrafeForwardSegment(27, 0, leftFront, rightFront, leftRear, rightRear, telemetry, imu);
            atsNextSegment.setNextSegment(atsNewSegment);
            atsNextSegment = atsNewSegment;


        } else if (strSecondNumber.equals("Purple tag")) {

            atsNewSegment = new AutonomousStrafeSidewaysSegment(25, 0, leftFront, rightFront, leftRear, rightRear, telemetry, imu);
            atsNextSegment.setNextSegment(atsNewSegment);
            atsNextSegment = atsNewSegment;

            atsNewSegment = new AutonomousStrafeForwardSegment(27, 0, leftFront, rightFront, leftRear, rightRear, telemetry, imu);
            atsNextSegment.setNextSegment(atsNewSegment);
            atsNextSegment = atsNewSegment;


        } else {

            atsNewSegment = new AutonomousStrafeSidewaysSegment(2, 0, leftFront, rightFront, leftRear, rightRear, telemetry, imu);
            atsNextSegment.setNextSegment(atsNewSegment);
            atsNextSegment = atsNewSegment;

            atsNewSegment = new AutonomousStrafeForwardSegment(27, 0, leftFront, rightFront, leftRear, rightRear, telemetry, imu);
            atsNextSegment.setNextSegment(atsNewSegment);
            atsNextSegment = atsNewSegment;

        }




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

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.35f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
        // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
        // tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);
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
