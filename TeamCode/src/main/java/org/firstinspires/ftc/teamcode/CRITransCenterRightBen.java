package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "CRITransCenterRightBenE")

public class CRITransCenterRightBen extends LinearOpMode {

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

        MecanumVelocityConstraint slowestMode = new MecanumVelocityConstraint(25, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint slowMode = new MecanumVelocityConstraint(30, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint normalMode = new MecanumVelocityConstraint(70, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());

        RevBlinkinLedDriver lights;
        lights = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        waitForStart();

        String strColorLevel = String.valueOf(sleeveDetection.getPosition());

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);


        Pose2d startPose = new Pose2d(16, 64, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);


        TrajectorySequence yellow1 = drive.trajectorySequenceBuilder(new Pose2d(16.00, 64.00, Math.toRadians(90.00)))
                .setReversed(true)
                .setVelConstraint(normalMode)
                .splineToSplineHeading(new Pose2d(12.00, 36.00, Math.toRadians(90.00)), Math.toRadians(270.00))
                .splineTo(new Vector2d(12.00, 4.00), Math.toRadians(270.00))
                .splineToSplineHeading(new Pose2d(7.00, -9.00, Math.toRadians(181.34)), Math.toRadians(180.00))
                .splineTo(new Vector2d(-29.00, -9.00), Math.toRadians(180.00))
                .splineToConstantHeading(new Vector2d(-37.00, -18), Math.toRadians(180.00))
                .splineTo(new Vector2d(-50.00, -18), Math.toRadians(180.00))
                .splineTo(new Vector2d(-66.00, -18), Math.toRadians(180.00))
                .build();


        //Navigate to parking spot 1

        TrajectorySequence park1 = drive.trajectorySequenceBuilder(yellow1.end())
                .setReversed(true)
                .setVelConstraint(normalMode)
                .splineToConstantHeading(new Vector2d(-35, -9), Math.toRadians(360.00))
                .build();

//Navigate to parking spot 2

        TrajectorySequence park2 = drive.trajectorySequenceBuilder(yellow1.end())
                .setReversed(true)
                .setVelConstraint(normalMode)
                .splineToConstantHeading(new Vector2d(-35.00, -9), Math.toRadians(360))
                .splineTo(new Vector2d(-12, -9.00), Math.toRadians(360))
                .build();


        //Navigate to parking spot 3

        TrajectorySequence park3 = drive.trajectorySequenceBuilder(yellow1.end())
                .setReversed(true)
                .setVelConstraint(normalMode)
                .splineToConstantHeading(new Vector2d(-35.00, -9.00), Math.toRadians(0.00))
                .splineTo(new Vector2d(-12, -10), Math.toRadians(0.00))
                .splineTo(new Vector2d(10 , -12.00), Math.toRadians(0.00))
                .build();


        drive.followTrajectorySequence(yellow1);
        drive.update();


        if(strColorLevel == "LEFT"){
            drive.followTrajectorySequence(park1);
            drive.update();
        } else if (strColorLevel == "CENTER"){
            drive.followTrajectorySequence(park2);
            drive.update();
        } else {
            drive.followTrajectorySequence(park3);
            drive.update();
        }

    }
}