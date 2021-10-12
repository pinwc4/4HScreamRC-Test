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


public class AutonomousLocateSkystoneSegment extends AutonomousSegment {

    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_STONE = "Stone";
    private static final String LABEL_SKYSTONE = "Skystone";


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

    private double dblFrontLeftMotorPower = 0;
    private double dblFrontRightMotorPower = 0;
    private double dblBackLeftMotorPower = 0;
    private double dblBackRightMotorPower = 0;

    private static final double NORMAL_POWER = 0.5;
    private static final double REDUCED_POWER = 0.35;
    private double dblDesiredHeading = 0;
    private static final double HEADING_ERROR_RANGE = 3;

    private MeasuredDistance msdMeasuredSkystoneDistance;

    private double Direction;

    private Telemetry telemetry;

    BNO055IMU imu;

    Orientation angles;


    private boolean bolinitialized;
    private boolean boldistReached;

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


    public AutonomousLocateSkystoneSegment(double Direction, double dblDesiredHeading, DcMotor dcmFLMotor, DcMotor dcmFRMotor, DcMotor dcmBLMotor, DcMotor dcmBRMotor, Telemetry telemetry, TFObjectDetector tfod, VuforiaLocalizer vuforia, MeasuredDistance msdMeasuredSkystoneDistance, BNO055IMU imu) {
        //add appropriate comment for conversions

        this.Direction = Direction;

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
        bolSkystoneFound = false;
        bolEndSegment = false;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

    }

    public void runSegment() {

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double dblHeading = angles.firstAngle;

        bolGoForward = false;
        bolGoBackwards = false;
        bolSkystoneCentered = false;
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                for (Recognition recognition : updatedRecognitions) {
                    if (recognition.getLabel().equals(LABEL_SKYSTONE)) {
                        bolSkystoneFound = true;
                        if (recognition.getLeft() < 125) {
                            bolGoForward = true;
                        } else if (recognition.getLeft() > 175) {
                            bolGoBackwards = true;
                        } else {
                            bolSkystoneCentered = true;
                        }
                    }
                }

                if(bolSkystoneFound){
                    if(bolGoForward){
                        dcmFLMotor.setPower(-0.15 * Direction);
                        dcmBLMotor.setPower(-0.15 * Direction);
                        dcmFRMotor.setPower(0.15 * Direction);
                        dcmBRMotor.setPower(0.15 * Direction);
                        telemetry.addLine("GO FORWARD");
                    } else if (bolGoBackwards) {
                        dblFrontLeftMotorPower = NORMAL_POWER;
                        dblFrontRightMotorPower = NORMAL_POWER;
                        dblBackLeftMotorPower = NORMAL_POWER;
                        dblBackRightMotorPower = NORMAL_POWER;

                        if(dblHeading < (dblDesiredHeading - HEADING_ERROR_RANGE)){
                            dblFrontLeftMotorPower = REDUCED_POWER;
                            dblBackLeftMotorPower = REDUCED_POWER;
                        }else if(dblHeading > (dblDesiredHeading + HEADING_ERROR_RANGE)){
                            dblFrontRightMotorPower = REDUCED_POWER;
                            dblBackRightMotorPower = REDUCED_POWER;
                        }
                        telemetry.addLine("GO BACKWARD");
                    }else if (bolSkystoneCentered){
                        dcmFLMotor.setPower(0);
                        dcmFRMotor.setPower(0);
                        dcmBLMotor.setPower(0);
                        dcmBRMotor.setPower(0);
                        bolEndSegment = true;
                        msdMeasuredSkystoneDistance.setDistance(dcmFLMotor.getCurrentPosition());
                        telemetry.addLine("END SEGMENT");
                    }else{
                        dcmFLMotor.setPower(0.15);
                        dcmBLMotor.setPower(0.15);
                        dcmFRMotor.setPower(-0.15);
                        dcmBRMotor.setPower(-0.15);
                        telemetry.addLine("HELP I LOST THE STONE");
                    }
                }else {
                    dcmFLMotor.setPower(-0.15);
                    dcmBLMotor.setPower(-0.15);
                    dcmFRMotor.setPower(0.15);
                    dcmBRMotor.setPower(0.15);
                    telemetry.addLine("LOOKING FOR THE STONE");
                }

                telemetry.addData("END SEGMENT VALUE", bolEndSegment);


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
                telemetry.update();
            }
        }

    }

        public boolean segmentComplete () {
            return bolEndSegment;
        }
    }