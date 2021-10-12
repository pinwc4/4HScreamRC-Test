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


public class AutonomousScanForRings extends AutonomousSegment {

    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private int intNumSameRecognitions = 0;

    public String strNumber = "None";


    private boolean bolGoForward;
    private boolean bolGoBackwards;
    private boolean bolSkystoneFound;
    private boolean bolSkystoneCentered;
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


    public AutonomousScanForRings(double distToTravelin, double dblDesiredHeading, DcMotor dcmFLMotor, DcMotor dcmFRMotor, DcMotor dcmBLMotor, DcMotor dcmBRMotor, Telemetry telemetry, TFObjectDetector tfod, VuforiaLocalizer vuforia, MeasuredDistance msdMeasuredSkystoneDistance, BNO055IMU imu) {
        //add appropriate comment for conversions

        desiredEncoderTicks = (distToTravelin * TICKSTOINCHES);

        this.telemetry = telemetry;

        this.dblDesiredHeading = dblDesiredHeading;

        this.dcmFLMotor = dcmFLMotor;
        this.dcmFRMotor = dcmFRMotor;
        this.dcmBLMotor = dcmBLMotor;
        this.dcmBRMotor = dcmBRMotor;

        this.msdMeasuredSkystoneDistance = msdMeasuredSkystoneDistance;

        this.tfod = tfod;
        this.vuforia = vuforia;

        bolinitialized = false;
        bolEndSegment = false;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

    }

    public void runSegment() {

        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.

            tfod.setZoom(2.5, 1.78);

            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                String strNewNumber = "None";
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());
                    strNewNumber = recognition.getLabel();
                }
                if (strNewNumber.equals("Quad")) {
                    telemetry.addLine("C");
                } else if (strNewNumber.equals("Single")) {
                    telemetry.addLine("B");
                } else {
                    telemetry.addLine("A");
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