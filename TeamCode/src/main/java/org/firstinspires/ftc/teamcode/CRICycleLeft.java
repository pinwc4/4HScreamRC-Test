package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "CycleLeft")


public class CRICycleLeft extends LinearOpMode {

    private SleeveDetectionLeft sleeveDetection;
    private OpenCvCamera camera;

    // Name of the Webcam to be set in the config
    private String webcamName = "Webcam 1";
    @Override

    public void runOpMode() throws InterruptedException{


        MecanumVelocityConstraint slowestMode = new MecanumVelocityConstraint(25, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint slowMode = new MecanumVelocityConstraint(30, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint normalMode = new MecanumVelocityConstraint(70, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());


        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);

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


        int intColorLevel = 2;
        int intConeStack1 = -90;
        int intCycleCounter = 0;




        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(-39, -63, Math.toRadians(270));

        drive.setPoseEstimate(startPose);


        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(startPose)

                .setReversed(true)
                .setVelConstraint(normalMode)

               .addTemporalMarker(1.5, () -> {
                    attachment.moveSlidesH();
                })

                .splineToLinearHeading(new Pose2d(-37, -60, Math.toRadians(270)), Math.toRadians(80))

                .splineToSplineHeading(new Pose2d(-37, -18, Math.toRadians(270)), Math.toRadians(90))

                .splineToSplineHeading(new Pose2d(-40, -2, Math.toRadians(360)), Math.toRadians(85))

                .splineToLinearHeading(new Pose2d(-24, -2, Math.toRadians(360)), Math.toRadians(0))

                .addTemporalMarker(() -> attachment.moveV4BOut())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveGrabber())
                .waitSeconds(0.25)
                .addTemporalMarker(() -> attachment.moveV4BIn())

                //.splineToSplineHeading(new Pose2d(-32, -11.7, Math.toRadians(0)), Math.toRadians(180))

                .lineToLinearHeading(new Pose2d(-33, -11.7, Math.toRadians(0)))

                .build();





        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)

                .addTemporalMarker(0.25, () -> {
                    attachment.movePickUpPosition();
                })

                .lineToLinearHeading(new Pose2d(-62, -10, Math.toRadians(0)))

                .build();




        TrajectorySequence traj3 = drive.trajectorySequenceBuilder(traj2.end())

                /*.addTemporalMarker(0.20, () -> {
                    attachment.moveStackCenter();
                })


*/
                .addTemporalMarker(0.25, () -> {
                    attachment.moveSlidesH();
                })

                .setReversed(false)

                .splineToLinearHeading(new Pose2d(-61.5, -10, Math.toRadians(0)), Math.toRadians(0))
                .splineToSplineHeading(new Pose2d(-26, -5, Math.toRadians(45)), Math.toRadians(45))

                .addTemporalMarker(() -> attachment.moveV4BOut())
                .waitSeconds(0.075)
                .addTemporalMarker(() -> attachment.moveGrabber())
                .waitSeconds(0.25)
                .addTemporalMarker(() -> attachment.moveV4BIn())

                .lineToLinearHeading(new Pose2d(-46, -12,Math.toRadians(0)))
                .build();




        TrajectorySequence traj4 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-60.8, -9, Math.toRadians(0)), Math.toRadians(180))

                .build();



        TrajectorySequence traj5 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-59, -8.5, Math.toRadians(360)), Math.toRadians(180))


                .build();



        //PARKING

        TrajectorySequence park1 = drive.trajectorySequenceBuilder(traj3.end())

                .setReversed(true)
                .addTemporalMarker(0.25, () -> {
                    attachment.moveSlidesDown();
                })
                .lineToLinearHeading(new Pose2d(-36, -13, Math.toRadians(0)))

                .build();

        TrajectorySequence park2 = drive.trajectorySequenceBuilder(traj3.end())

                .setReversed(true)
                .addTemporalMarker(0.25, () -> {
                    attachment.moveSlidesDown();
                })
                .lineToLinearHeading(new Pose2d(-36, -13, Math.toRadians(0)))
                .setReversed(false)
                .lineToLinearHeading(new Pose2d(-12, -12, Math.toRadians(0)))

                .build();


        TrajectorySequence park3 = drive.trajectorySequenceBuilder(traj3.end())

                .setReversed(true)
                .addTemporalMarker(0.25, () -> {
                    attachment.moveSlidesDown();
                })
                .lineToLinearHeading(new Pose2d(-36, -13, Math.toRadians(0)))
                .setReversed(false)
                .lineToLinearHeading(new Pose2d(12, -12, Math.toRadians(0)))


                .build();





        waitForStart();

        String strColorLevel = String.valueOf(sleeveDetection.getPosition());

        resetRuntime();

        attachment.moveV4B();

        drive.followTrajectorySequence(traj1);
        drive.update();


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


       //WHILE LOOP ENDS

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
