package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Cycle Right")

public class CRICycleRight extends LinearOpMode {

    private SleeveDetectionRight sleeveDetection;
    private OpenCvCamera camera;

    // Name of the Webcam to be set in the config
    private String webcamName = "Webcam 1";
    @Override

    public void runOpMode() throws InterruptedException{

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetectionRight();
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

        MecanumVelocityConstraint slowMode = new MecanumVelocityConstraint(25, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint normalMode = new MecanumVelocityConstraint(50, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        //MecanumVelocityConstraint expoMode = new

        int intColorLevel = 0;
        int intConeStack1 = -90;
        int intCycleCounter = 0;

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);

        waitForStart();
        String strColorLevel = String.valueOf(sleeveDetection.getPosition());


        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(39, -63, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(startPose)


                .addTemporalMarker(1.5, () -> {
                    attachment.moveSlidesM();
                })
                .addTemporalMarker(2, () -> {
                    attachment.senseSleeve();

                })

                .addTemporalMarker(0.92, 0.1, () -> {
                    attachment.moveV4BOut();
                })

                .addTemporalMarker(0.95, 1.5, () -> {
                    attachment.moveGrabber();
                })

                .addTemporalMarker(0.99, 4, () -> {
                    attachment.moveV4BIn();
                })

                .splineToLinearHeading(new Pose2d(35, -60, Math.toRadians(270)), Math.toRadians(0))
                .setVelConstraint(slowMode)
                .lineToLinearHeading(new Pose2d(35, -27, Math.toRadians(270)))
                .setVelConstraint(normalMode)
                .splineToSplineHeading(new Pose2d(35, -9, Math.toRadians(248)), 230)
                .splineToLinearHeading(new Pose2d(26, -19, Math.toRadians(225)), Math.toRadians(20))

                .build();

        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(56, -11.75, Math.toRadians(180)))
                .lineToLinearHeading(new Pose2d(60.8, -11.75, Math.toRadians(180)))

                .build();

        TrajectorySequence traj3 = drive.trajectorySequenceBuilder(traj2.end())

                .addTemporalMarker(0.25, () -> {
                    attachment.moveSlidesM();
                })

                .addTemporalMarker(0.85, 0.1, () -> {
                    attachment.moveV4BOut();
                })

                .addTemporalMarker(0.93, 2, () -> {
                    attachment.moveGrabber();
                })

                .addTemporalMarker(0.99, 4, () -> {
                    attachment.moveV4BIn();
                })

                .setReversed(false)
                .lineToLinearHeading(new Pose2d(56, -11, Math.toRadians(180)))
                .splineToLinearHeading(new Pose2d(27, -21, Math.toRadians(225)), Math.toRadians(20))

                .build();




        TrajectorySequence traj4 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-56, -11, Math.toRadians(360)))
                .lineToLinearHeading(new Pose2d(-60.8, -11.3, Math.toRadians(360)))

                .build();

        TrajectorySequence traj5 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-56, -11, Math.toRadians(360)))
                .lineToLinearHeading(new Pose2d(-60.8, -10.9, Math.toRadians(360)))

                .build();

        TrajectorySequence park1 = drive.trajectorySequenceBuilder(traj3.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-34, -9, Math.toRadians(360)))


                .build();

        TrajectorySequence park2 = drive.trajectorySequenceBuilder(traj3.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-12, -9, Math.toRadians(270)))


                .build();


        TrajectorySequence park3 = drive.trajectorySequenceBuilder(traj3.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-36, -12, Math.toRadians(360)))
                .setReversed(false)
                .lineToLinearHeading(new Pose2d(12, -9, Math.toRadians(360)))


                .build();


        waitForStart();

        resetRuntime();

        attachment.moveV4B();

        drive.followTrajectorySequence(traj1);
        drive.update();

        intColorLevel = attachment.getintColorLevel();



        while (getRuntime() < 23) {


            if(intCycleCounter == 0){
                drive.followTrajectorySequenceAsync(traj2);
                while (drive.isBusy() && opModeIsActive()) {
                    attachment.movePickUpPosition();
                    attachment.moveWallSpacerOut();
                    drive.update();
                }
            } else if(intCycleCounter == 1){
                drive.followTrajectorySequenceAsync(traj4);
                while (drive.isBusy() && opModeIsActive()) {
                    attachment.movePickUpPosition();
                    attachment.moveWallSpacerOut();
                    drive.update();
                }
            } else if(intCycleCounter == 2){
                drive.followTrajectorySequenceAsync(traj5);
                while (drive.isBusy() && opModeIsActive()) {
                    attachment.movePickUpPosition();
                    attachment.moveWallSpacerOut();
                    drive.update();
                }
            }

            attachment.moveStackCenter();

            attachment.movePickUpCone2();

            drive.followTrajectorySequence(traj3);
            drive.update();

            intConeStack1 = intConeStack1 + 52;//52
            intCycleCounter++;

        }


        attachment.moveSlidesDown();

        attachment.moveWallSpacerIn();

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
