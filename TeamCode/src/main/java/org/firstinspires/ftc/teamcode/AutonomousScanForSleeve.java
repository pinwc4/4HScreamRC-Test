package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;


public class AutonomousScanForSleeve extends AutonomousSegment {

    private static final String TFOD_MODEL_ASSET = "model_20221027_191633.tflite";

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


    //These are new labels for PowerPlay personal sleeve
    private int intNumSameRecognitions = 0;

    public String strNumber = "None";
    public String strNewNumber = "None";

    private boolean bolEndSegment;

    private WebcamName wcnWebcamName;

    private int intTfodMonitorViewId;

    private double desiredEncoderTicks;

    private DcMotor dcmFLMotor;
    private DcMotor dcmFRMotor;
    private DcMotor dcmBRMotor;
    private DcMotor dcmBLMotor;


    private double dblDesiredHeading = 0;

    private static final double TICKSTOINCHES = (537.6 * 1) / (Math.PI * 3.77953);

    private MeasuredDistance msdMeasuredSkystoneDistance;

    private int Seconds;

    private Telemetry telemetry;

    BNO055IMU imu;

    Orientation angles;


    private boolean bolinitialized;
    private boolean boldistReached;
    double distToTravelin;

    private static final String VUFORIA_KEY =
            "AftxbQr/////AAABmQvhWKx8Ok8fiwDdGWphaPsqGLOjlBaX5uyms1XLxF4mODVA3S3WH8/rgWV4hXAMV0cFMid2OOnFgiki5bwjmVNnyd9aPQnt8F6ajQN2epL1407zvC01z+TK5Dm3fLomAJMdnNbq31l+QlnDED9GMsftphNqNchUnI/QtDPU0/qzPohbEfui6sXzOoc6H+iJcg2gyrDwcen544nIDVH7tDEx72xjaSdYf96f7i6vAm6UyXlegp0HjvBFd3aqyhVC6PcG+HS+PHW2iFwsTL5PZ5Gme9oK6rjzfHxAoyHgxZoMdHuCbD59mZAJcwyGTELLXxOWXCuDGhyZP/nxnQEK+Hckg3D8QeY921oQLaKmru3M";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;


    public AutonomousScanForSleeve( Telemetry telemetry, TFObjectDetector tfod, VuforiaLocalizer vuforia) {
        //add appropriate comment for conversions


        this.telemetry = telemetry;


        this.tfod = tfod;
        this.vuforia = vuforia;

        bolinitialized = false;
        bolEndSegment = false;


    }

    public void runSegment() {

        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Objects Detected", updatedRecognitions.size());

                // step through the list of recognitions and display image position/size information for each one
                // Note: "Image number" refers to the randomized image orientation/number
                for (Recognition recognition : updatedRecognitions) {
                    double col = (recognition.getLeft() + recognition.getRight()) / 2 ;
                    double row = (recognition.getTop()  + recognition.getBottom()) / 2 ;
                    double width  = Math.abs(recognition.getRight() - recognition.getLeft()) ;
                    double height = Math.abs(recognition.getTop()  - recognition.getBottom()) ;

                    telemetry.addData(""," ");
                    telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100 );
                    telemetry.addData("- Position (Row/Col)","%.0f / %.0f", row, col);
                    telemetry.addData("- Size (Width/Height)","%.0f / %.0f", width, height);

                    strNewNumber.equals(recognition.getLabel());
                }
                if (strNewNumber.equals("Green tag")) {
                    telemetry.addLine("G");
                } else if (strNewNumber.equals("Yellow tag")) {
                    telemetry.addLine("Y");
                } else {
                    telemetry.addLine("P");
                }
                telemetry.addData("recognition v", strNewNumber);


                if(strNumber.equals(strNewNumber)){
                    intNumSameRecognitions++;
                    if(intNumSameRecognitions > 50){ //add constant
                        bolEndSegment = true;
                    }
                }
                else {
                    intNumSameRecognitions = 0;
                    strNumber = strNewNumber;
                    bolEndSegment = false;
                }
                telemetry.update();
            }
        }
    }

        public boolean segmentComplete () {
            return bolEndSegment;
        }

    public String getStrNumber() {
        return strNumber;
    }
}