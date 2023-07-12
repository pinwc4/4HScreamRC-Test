package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.teamcode.common.powerplay.SleeveDetection;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;


@Autonomous(name = "Signal Sleeve Test Left")
public class VisionTestLeft extends LinearOpMode {

    private SleeveDetectionLeft sleeveDetection;
    private OpenCvCamera camera;
    
    // Name of the Webcam to be set in the config
    private String webcamName = "Webcam 1";

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetectionLeft();
        camera.setPipeline(sleeveDetection);



        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(320,240, OpenCvCameraRotation.SIDEWAYS_RIGHT);
            }

            @Override
            public void onError(int errorCode) {}
        });

        while (!isStarted()) {
            telemetry.addData("ROTATION: ", sleeveDetection.getPosition());
            telemetry.update();
        }

        waitForStart();





            MecanumVelocityConstraint slowestMode = new MecanumVelocityConstraint(25, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
            MecanumVelocityConstraint slowMode = new MecanumVelocityConstraint(30, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
            MecanumVelocityConstraint normalMode = new MecanumVelocityConstraint(70, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
            //MecanumVelocityConstraint expoMode = new

            String strColorLevel = String.valueOf(sleeveDetection.getPosition());
            int intConeStack1 = -90;
            int intCycleCounter = 0;

            SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
            RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);


            // We want to start the bot at x: 10, y: -8, heading: 90 degrees
            Pose2d startPose = new Pose2d(-58, 29, Math.toRadians(0));

            drive.setPoseEstimate(startPose);


        //Navigate to parking spot 1
        TrajectorySequence park1 = drive.trajectorySequenceBuilder(new Pose2d(-58.00, 29.00, Math.toRadians(0.00)))
                .setReversed(true)
                .setVelConstraint(normalMode)
                .splineToConstantHeading(new Vector2d(-30, 17.00), Math.toRadians(0.00))
                .build();

        //Navigate to parking spot 2
        TrajectorySequence park2 = drive.trajectorySequenceBuilder(new Pose2d(-58.00, 29.00, Math.toRadians(0.00)))
                .setReversed(true)
                .setVelConstraint(normalMode)
                .splineToConstantHeading(new Vector2d(-30.00, 17.00), Math.toRadians(0.00))
                .splineTo(new Vector2d(-6.00, 17.00), Math.toRadians(0.00))
                .build();

        //Navigate to parking spot 3
        TrajectorySequence park3 = drive.trajectorySequenceBuilder(new Pose2d(-58.00, 29.00, Math.toRadians(0.00)))
                .setReversed(true)
                .setVelConstraint(normalMode)
                .splineToConstantHeading(new Vector2d(-30.00, 17.00), Math.toRadians(0.00))
                .splineTo(new Vector2d(-6.00, 17.00), Math.toRadians(0.00))
                .splineTo(new Vector2d(16.00, 19.00), Math.toRadians(0.00))
                .build();


            attachment.moveWallSpacerIn();

            if(strColorLevel == "CENTER"){
                drive.followTrajectorySequence(park1);
                drive.update();
            } else if (strColorLevel == "RIGHT"){
                drive.followTrajectorySequence(park2);
                drive.update();
            } else {
                drive.followTrajectorySequence(park3);
                drive.update();
            }





        }
    }