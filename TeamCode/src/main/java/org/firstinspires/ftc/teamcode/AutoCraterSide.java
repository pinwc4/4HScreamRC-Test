package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "AutoCraterSide", group = "Concept")

@Disabled

public class AutoCraterSide extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private AutonomousDriveStraightSegment DriveStraight50cm;
    private AutonomousDriveStraightSegment DriveStraight20cm;
    private AutonomousDriveStraightSegment DriveStraight20cm2;

    private AutonomousTurnSegment TurnLeft1;
    private AutonomousTurnSegment TurnRight1;


    private DcMotor dcmLeftMotor;
    private DcMotor dcmRightMotor;

    private DcMotor dcmSecondArmMotor;
    private DcMotor dcmWinchMotor;

    private int intGoldWithinBoundary = 0;
    private int intSilverWithinBoundary = 0;
    private int intCraterGoldFilter = 410;
    private boolean bolGoLeft = false;
    private boolean bolGoRight = false;
    private boolean bolGoCenter = false;

    private boolean bolFirstMove = false;
    private boolean bolSecondMove = false;
    private boolean bolThirdMove = false;
    private boolean bolFourthMove = false;
    private boolean bolDoneMoving = false;

    private String GoldPos = "Nothing";

    //330, 380
    //100, 160


    private static final String VUFORIA_KEY = "AftxbQr/////AAABmQvhWKx8Ok8fiwDdGWphaPsqGLOjlBaX5uyms1XLxF4mODVA3S3WH8/rgWV4hXAMV0cFMid2OOnFgiki5bwjmVNnyd9aPQnt8F6ajQN2epL1407zvC01z+TK5Dm3fLomAJMdnNbq31l+QlnDED9GMsftphNqNchUnI/QtDPU0/qzPohbEfui6sXzOoc6H+iJcg2gyrDwcen544nIDVH7tDEx72xjaSdYf96f7i6vAm6UyXlegp0HjvBFd3aqyhVC6PcG+HS+PHW2iFwsTL5PZ5Gme9oK6rjzfHxAoyHgxZoMdHuCbD59mZAJcwyGTELLXxOWXCuDGhyZP/nxnQEK+Hckg3D8QeY921oQLaKmru3M";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;


    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
    
    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        dcmLeftMotor = hardwareMap.dcMotor.get("MotorLD");
        dcmRightMotor = hardwareMap.dcMotor.get("MotorRD");
        dcmLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        dcmSecondArmMotor = hardwareMap.dcMotor.get("MotorUA");
        dcmSecondArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmSecondArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmSecondArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        dcmWinchMotor = hardwareMap.dcMotor.get("MotorLW");
        dcmWinchMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Create segments for autonomous script
        DriveStraight50cm = new AutonomousDriveStraightSegment(50, dcmLeftMotor, dcmRightMotor, telemetry);
        DriveStraight20cm = new AutonomousDriveStraightSegment(20, dcmLeftMotor, dcmRightMotor, telemetry);
        DriveStraight20cm2 = new AutonomousDriveStraightSegment(20, dcmLeftMotor, dcmRightMotor, telemetry);


        TurnLeft1 = new AutonomousTurnSegment(-225, dcmLeftMotor, dcmRightMotor, telemetry);
        TurnRight1 = new AutonomousTurnSegment(225, dcmLeftMotor, dcmRightMotor, telemetry);
/*
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
*/
        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();
        resetStartTime();

        while (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive() && GoldPos == "Nothing") {

                intGoldWithinBoundary = 0;
                intSilverWithinBoundary = 0;
                GoldPos = "Nothing";

                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        int goldMineralX = -1;
                        int silverMineral1X = -1;
                        int silverMineral2X = -1;
                        for (Recognition recognition : updatedRecognitions) {

                            //I added "&& recognition.getTop() > intCraterGoldFilter" to only allow blocks that are below the specified height
                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL) && recognition.getRight() < intCraterGoldFilter) {
                                intGoldWithinBoundary++;
                                goldMineralX = (int) recognition.getTop();
                                telemetry.addData("GoldLeft", recognition.getTop());
                                telemetry.addData("GoldRight", recognition.getBottom());
                                telemetry.addData("GoldBottom", recognition.getLeft());
                                telemetry.addData("GoldTop", recognition.getRight());
                            } else if (silverMineral1X == -1 && recognition.getRight() < intCraterGoldFilter) {
                                intSilverWithinBoundary++;
                                silverMineral1X = (int) recognition.getTop();
                            } else if (recognition.getRight() < intCraterGoldFilter) {
                                intSilverWithinBoundary++;
                                silverMineral2X = (int) recognition.getTop();
                            }
                        }


                            if (intSilverWithinBoundary == 2) {
                                telemetry.addLine("Gold Position: bolGoLeft");
                                GoldPos = "bolGoLeft";
                                resetStartTime();
                            } else if (intGoldWithinBoundary == 1 && goldMineralX > 250) {
                                telemetry.addLine("Gold Position: bolGoRight");
                                GoldPos = "bolGoRight";
                                resetStartTime();
                            } else if (intGoldWithinBoundary == 1 && goldMineralX < 250) {
                                telemetry.addLine("Gold Position: bolGoCenter");
                                GoldPos = "bolGoCenter";
                                resetStartTime();
                            } else {
                                if (getRuntime() > 5) {
                                    telemetry.addLine("Gold Position: Unknown");
                                    GoldPos = "Unknown";
                                    resetStartTime();
                                }
                            }


                        //intGoldWithinBoundary tells me how many gold minerals are below the specified height
                        telemetry.addData("Gold in boundary", intGoldWithinBoundary);
                        telemetry.addData("Silver in boundary", intSilverWithinBoundary);
                        telemetry.update();
                    }
                }
            }

            dcmWinchMotor.setPower(-1);

            if(getRuntime() > 6) {
                if (GoldPos == "bolGoLeft" && !bolGoLeft) {
                    bolGoLeft = true;
                    bolFirstMove = true;
                } else if (GoldPos == "bolGoRight" && !bolGoRight) {
                    bolGoRight = true;
                    bolFirstMove = true;
                } else if (GoldPos == "bolGoCenter" && !bolGoCenter) {
                    bolGoCenter = true;
                    bolFirstMove = true;
                } else if (GoldPos == "Unknown" && getRuntime() > 5 && !bolGoLeft) {
                    bolGoLeft = true;
                    bolFirstMove = true;
                    telemetry.addLine("Unable to detect gold mineral position");
                }
            }



            //bolGoLeft scenario
            //Drive 20cm
            if(bolGoLeft && !DriveStraight20cm.segmentComplete() && bolFirstMove){
                DriveStraight20cm.runSegment();
                telemetry.addLine("Left1");
            } else if(bolGoLeft && DriveStraight20cm.segmentComplete() && bolFirstMove){
                bolFirstMove = false;
                bolSecondMove = true;
                resetStartTime();
            }
            //Turn left
            if(bolGoLeft && !TurnLeft1.segmentComplete() && bolSecondMove && getRuntime() > 1){
                TurnLeft1.runSegment();
                telemetry.addLine("Left2");
            } else if(bolGoLeft && TurnLeft1.segmentComplete() && bolSecondMove){
                bolSecondMove = false;
                bolThirdMove = true;
                resetStartTime();
            }
            //Drive 20cm
            if(bolGoLeft && !DriveStraight20cm2.segmentComplete() && bolThirdMove && getRuntime() > 1){
                DriveStraight20cm2.runSegment();
                telemetry.addLine("Left3");
            } else if(bolGoLeft && DriveStraight20cm2.segmentComplete() && bolThirdMove){
                bolThirdMove = false;
                bolFourthMove = true;
                resetStartTime();
            }
            //Turn right
            if(bolGoLeft && !TurnRight1.segmentComplete() && bolFourthMove && getRuntime() > 1){
                TurnRight1.runSegment();
                telemetry.addLine("Left4");
            } else if(bolGoLeft && TurnRight1.segmentComplete() && bolFourthMove){
                bolFourthMove = false;
                bolDoneMoving = true;
            }



            //bolGoRight scenario
            //Drive 20cm
            if(bolGoRight && !DriveStraight20cm.segmentComplete() && bolFirstMove){
                DriveStraight20cm.runSegment();
                telemetry.addLine("Right1");
            } else if(bolGoRight && DriveStraight20cm.segmentComplete() && bolFirstMove){
                bolFirstMove = false;
                bolSecondMove = true;
                resetStartTime();
            }
            //Turn left
            if(bolGoRight && !TurnRight1.segmentComplete() && bolSecondMove && getRuntime() > 1){
                TurnRight1.runSegment();
                telemetry.addLine("Right2");
            } else if(bolGoRight && TurnRight1.segmentComplete() && bolSecondMove){
                bolSecondMove = false;
                bolThirdMove = true;
                resetStartTime();
            }
            //Drive 20cm
            if(bolGoRight && !DriveStraight20cm2.segmentComplete() && bolThirdMove && getRuntime() > 1){
                DriveStraight20cm2.runSegment();
                telemetry.addLine("Right3");
            } else if(bolGoRight && DriveStraight20cm2.segmentComplete() && bolThirdMove){
                bolThirdMove = false;
                bolFourthMove = true;
                resetStartTime();
            }
            //Turn left
            if(bolGoRight && !TurnLeft1.segmentComplete() && bolFourthMove && getRuntime() > 1){
                TurnLeft1.runSegment();
                telemetry.addLine("Right4");
            } else if(bolGoRight && TurnLeft1.segmentComplete() && bolFourthMove){
                bolFourthMove = false;
                bolDoneMoving = true;
            }


            //bolGoCenter scenario
            if(bolGoCenter && !DriveStraight50cm.segmentComplete() && bolFirstMove){
                DriveStraight50cm.runSegment();
                telemetry.addLine("bolGoCenter");
            } else if(bolGoCenter && DriveStraight50cm.segmentComplete() && bolFirstMove){
                bolFirstMove = false;
                bolDoneMoving = true;
            }



            if(bolDoneMoving && dcmSecondArmMotor.getCurrentPosition() > -2750){
                dcmSecondArmMotor.setPower(0.5);
                telemetry.addLine("Lowering Arm");
            } else{
                dcmSecondArmMotor.setPower(0);
            }
        }

        dcmWinchMotor.setPower(0);

        if (tfod != null) {
            tfod.shutdown();
        }
    }
}