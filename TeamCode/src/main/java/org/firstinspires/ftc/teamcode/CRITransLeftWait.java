package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "Transformer wait")

public class CRITransLeftWait extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        RevBlinkinLedDriver lights;
        lights = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        waitForStart();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);


        Pose2d startPose = new Pose2d(-39, -63, Math.toRadians(270));
        drive.setPoseEstimate(startPose);

        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(startPose)


               // .setReversed(true)

                /*
                .addTemporalMarker(1.5, () -> {
                    attachment.moveSlidesM();
                })

                .addTemporalMarker(2, () -> {
                    attachment.senseSleeve();
                })

                 */

                //.addTemporalMarker(4, () -> attachment.movePickUpPositionGround())

                //robot moves out
                .splineToLinearHeading(new Pose2d(-35, -60, Math.toRadians(270)), Math.toRadians(80))

                //robot moves out
                .splineToSplineHeading(new Pose2d(-34, 19, Math.toRadians(270)), Math.toRadians(90))

                //robot moves to cone
                .splineToSplineHeading(new Pose2d(-59.5, 22, Math.toRadians(0)), Math.toRadians(180))

                //robot moves sideways
                .lineToLinearHeading(new Pose2d(-62.8, 12.5, Math.toRadians(0)))

                .build();

/*
        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())

                //.splineToLinearHeading(new Pose2d(-48, 23.5, Math.toRadians(0)), Math.toRadians(330))


                       // .lineToLinearHeading(new Pose2d(-25.5, 23.2, Math.toRadians(0)))
                .addTemporalMarker(() -> attachment.moveV4BOut())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveGrabber())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveV4BOut())







                .build();


 */


        attachment.moveV4B();
        drive.followTrajectorySequence(traj1);
       // attachment.movePickUpCone2();
        //drive.followTrajectorySequence(traj2);
        drive.update();



    }
}

