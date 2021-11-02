package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
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


public class AutonomousScanForDucks extends AutonomousSegment {

    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };

    private int intNumSameRecognitions = 0;

    public String strName = "None";
    public float strNumber = 0;
    public float strSecondNumber = 0;


    private boolean bolGoForward;
    private boolean bolGoBackwards;
    private boolean bolSkystoneFound;
    private boolean bolSkystoneCentered;
    private boolean bolEndSegment;

    private WebcamName wcnWebcamName;

    private int intTfodMonitorViewId;

    private int Seconds;

    private Telemetry telemetry;

    BNO055IMU imu;

    Orientation angles;

    private static final double MINIMUM_SAME_RECOGNITIONS = 50;

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


    public AutonomousScanForDucks( Telemetry telemetry, TFObjectDetector tfod, VuforiaLocalizer vuforia) {
        //add appropriate comment for conversions


        this.telemetry = telemetry;


        this.tfod = tfod;
        this.vuforia = vuforia;

        bolinitialized = false;
        bolEndSegment = false;


    }

    public void runSegment() {

        if (tfod != null) {

            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                float strNewNumber = 0;
                String strNewName = "no duck";
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());
                    i++;
                    strNewNumber = recognition.getLeft();
                    strNewName = recognition.getLabel();
                }

                if (strNewName.equals("Duck") && strNewNumber > 400) {
                    strNumber = 1;
                    telemetry.addLine("Level 1");
                } else if (strNewName.equals("Duck") && strNewNumber < 400) {
                    strNumber = 2;
                    telemetry.addLine("Level 2");
                } else {
                    strNumber = 3;
                    telemetry.addLine("Level 3");
                }
                telemetry.addData("recognition v", strNewNumber);

                if(strSecondNumber == strNumber){
                    intNumSameRecognitions++;
                    if(intNumSameRecognitions > MINIMUM_SAME_RECOGNITIONS){
                        bolEndSegment = true;
                    }
                }
                else {
                    intNumSameRecognitions = 0;
                    strSecondNumber = strNumber;
                    bolEndSegment = false;
                }



                telemetry.update();
            }
        }
    }

        public boolean segmentComplete () {
            return bolEndSegment;
        }

    public float getStrSecondNumber() {
        return strSecondNumber;
    }
}