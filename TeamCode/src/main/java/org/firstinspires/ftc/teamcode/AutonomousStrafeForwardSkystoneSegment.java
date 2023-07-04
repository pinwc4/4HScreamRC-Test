package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;


public class AutonomousStrafeForwardSkystoneSegment extends AutonomousSegment {

    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_STONE = "Stone";
    private static final String LABEL_SKYSTONE = "Skystone";


    private boolean bolGoForward;
    private boolean bolGoBackwards;

    private WebcamName wcnWebcamName;
    private int intTfodMonitorViewId;

    private int Direction;

    private DcMotor dcmFLMotor;
    private DcMotor dcmFRMotor;
    private DcMotor dcmBRMotor;
    private DcMotor dcmBLMotor;

    private Telemetry telemetry;

    private boolean bolinitialized;
    private boolean boldistReached;

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;

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


    public AutonomousStrafeForwardSkystoneSegment(int Direction, DcMotor dcmFLMotor, DcMotor dcmFRMotor, DcMotor dcmBLMotor, DcMotor dcmBRMotor, Telemetry telemetry, TFObjectDetector tfod, VuforiaLocalizer vuforia) {
        //add appropriate comment for conversions

        this.Direction = Direction;

        this.telemetry = telemetry;

        this.dcmFLMotor = dcmFLMotor;
        this.dcmFRMotor = dcmFRMotor;
        this.dcmBLMotor = dcmBLMotor;
        this.dcmBRMotor = dcmBRMotor;

        this.tfod = tfod;
        this.vuforia = vuforia;

        this.wcnWebcamName = wcnWebcamName;

        bolinitialized = false;
        boldistReached = false;
        bolGoForward = false;
    }

    public void runSegment() {

        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                for (Recognition recognition : updatedRecognitions) {
                    if (recognition.getLabel().equals(LABEL_SKYSTONE)) {
                        bolGoForward = true;
                    }
                }
                if (bolGoForward) {
                    dcmFLMotor.setPower(0);
                    dcmBLMotor.setPower(0);
                    dcmFRMotor.setPower(0);
                    dcmBRMotor.setPower(0);
                }else{
                    dcmFLMotor.setPower(-0.15 * Direction);
                    dcmBLMotor.setPower(-0.15 * Direction);
                    dcmFRMotor.setPower(0.15 * Direction);
                    dcmBRMotor.setPower(0.15 * Direction);
                }

                telemetry.addData("# Object Detected", updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());
                }
                telemetry.addLine("STRAFE FORWARD SEGMENT");
                telemetry.update();
            }
        }

    }

    public boolean segmentComplete () {
        return bolGoForward;
    }
}



