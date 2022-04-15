package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;


//start robot with camera facing toward the center of the field and as close to the Skybridge as possible

@Autonomous(name = "AutoSwitchCaseTest")


public class SwitchCaseTest extends OpMode {

    private DcMotor dcmFrontLeftMotor;
    private DcMotor dcmFrontRightMotor;
    private DcMotor dcmBackLeftMotor;
    private DcMotor dcmBackRightMotor;

    private Servo srvBucketServo;
    private CRServo dcmCSMotor;

    private DcMotorEx dcmSlider1;

    private DcMotorEx dcmMagnetArm;

    private DigitalChannel lteGreen;
    private DigitalChannel lteRed;

    private DistanceSensor snsDistanceLeft;
    private DistanceSensor snsDistanceRight;

    private DistanceSensor snsDistanceBack;

    private long dblWaitParameter;

    private int intCounter;

    private int programOrder;

    VuforiaLocalizer vuforia;
    TFObjectDetector tfod;
    BNO055IMU imu;

    private static final double NORMAL_POWER = 0.8;



    private int intTfodMonitorViewId;

    private AutonomousSegment atsCurrentSegment;

    private AutonomousScanForDucks ascDucks;

    private static final String TFOD_MODEL_ASSET = "V2model_20211204_165120.tflite";
    private static final String[] LABELS = {
            "Green TSE"
    };

    private static final String VUFORIA_KEY =
            "AftxbQr/////AAABmQvhWKx8Ok8fiwDdGWphaPsqGLOjlBaX5uyms1XLxF4mODVA3S3WH8/rgWV4hXAMV0cFMid2OOnFgiki5bwjmVNnyd9aPQnt8F6ajQN2epL1407zvC01z+TK5Dm3fLomAJMdnNbq31l+QlnDED9GMsftphNqNchUnI/QtDPU0/qzPohbEfui6sXzOoc6H+iJcg2gyrDwcen544nIDVH7tDEx72xjaSdYf96f7i6vAm6UyXlegp0HjvBFd3aqyhVC6PcG+HS+PHW2iFwsTL5PZ5Gme9oK6rjzfHxAoyHgxZoMdHuCbD59mZAJcwyGTELLXxOWXCuDGhyZP/nxnQEK+Hckg3D8QeY921oQLaKmru3M";


    public float strSecondNumber;

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

        dcmSlider1 = hardwareMap.get(DcMotorEx.class, "MotorGM");
        dcmSlider1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSlider1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmSlider1.setDirection(DcMotor.Direction.FORWARD);

        dcmMagnetArm = hardwareMap.get(DcMotorEx.class, "MagnetArm");
        dcmMagnetArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmMagnetArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmMagnetArm.setDirection(DcMotor.Direction.FORWARD);

        dcmCSMotor = hardwareMap.crservo.get("SpinnerMotor");

        srvBucketServo = hardwareMap.servo.get("BucketServo");
        srvBucketServo.setPosition(0.85);

        snsDistanceLeft = hardwareMap.get(DistanceSensor.class, "DistanceLeft");
        snsDistanceRight = hardwareMap.get(DistanceSensor.class, "DistanceRight");
        snsDistanceBack = hardwareMap.get(DistanceSensor.class, "DistanceBack");

        lteRed = hardwareMap.get(DigitalChannel.class, "red");
        lteGreen = hardwareMap.get(DigitalChannel.class, "green");

        lteGreen.setMode(DigitalChannel.Mode.OUTPUT);
        lteRed.setMode(DigitalChannel.Mode.OUTPUT);

        dcmFrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmFrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
            tfod.setZoom(2.5, 16.0/9.0);
        }


    }


    public void loop(){

        switch (programOrder){

            case 0:

                break;
            case 1:


                /*dcmFrontLeftMotor.setPower(-NORMAL_POWER);
                dcmFrontRightMotor.setPower(NORMAL_POWER);
                dcmBackLeftMotor.setPower(-NORMAL_POWER);
                dcmBackRightMotor.setPower(NORMAL_POWER);

                programOrder++;
                break;



            case 1:
                if(dcmFrontLeftMotor.getCurrentPosition() >= 2500){
                    dcmFrontLeftMotor.setPower(0);
                    dcmFrontRightMotor.setPower(0);
                    dcmBackLeftMotor.setPower(0);
                    dcmBackRightMotor.setPower(0);

                    programOrder++;
                }
                break;

            case 2:
                dcmMagnetArm.setTargetPosition(1000);
                dcmMagnetArm.setPower(0.9);
                dcmMagnetArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                programOrder++;
                break;

            case 3:

                dcmFrontLeftMotor.setPower(NORMAL_POWER);
                dcmFrontRightMotor.setPower(-NORMAL_POWER);
                dcmBackLeftMotor.setPower(NORMAL_POWER);
                dcmBackRightMotor.setPower(-NORMAL_POWER);

                programOrder++;
                break;

            case 4:
                if(dcmFrontLeftMotor.getCurrentPosition() <= 1500){
                    dcmMagnetArm.setTargetPosition(0);
                    dcmMagnetArm.setPower(0.9);
                    dcmMagnetArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    programOrder++;

                }

                break;

            case 5:
                if(dcmFrontLeftMotor.getCurrentPosition() <= 1000){
                    dcmFrontLeftMotor.setPower(0);
                    dcmFrontRightMotor.setPower(0);
                    dcmBackLeftMotor.setPower(0);
                    dcmBackRightMotor.setPower(0);

                    programOrder++;
                }
                break;

                 */






        }

        telemetry.addData("PO", programOrder);
        telemetry.addData("MA", dcmMagnetArm.getCurrentPosition());
        telemetry.addData("FLMotor", dcmFrontLeftMotor.getCurrentPosition());

    }


    private void initVuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
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
