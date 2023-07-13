package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "Transformer Cycle Wait")
@Disabled
public class CRITransCycleWait extends LinearOpMode {
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


                .setReversed(true)
                .addTemporalMarker(1.5, () -> {
                    attachment.moveSlidesM();
                })

                .addTemporalMarker(2, () -> {
                    attachment.senseSleeve();
                })
                .splineToLinearHeading(new Pose2d(-35, -60, Math.toRadians(270)), Math.toRadians(0))

                .lineToLinearHeading(new Pose2d(-35, 23.5, Math.toRadians(270)))

                .splineToLinearHeading(new Pose2d(-48, 23.5, Math.toRadians(0)), Math.toRadians(330))

                        .lineToLinearHeading(new Pose2d(-25.5, 23.2, Math.toRadians(0)))
                .addTemporalMarker(() -> attachment.moveV4BOut())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveGrabber())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveV4BOut())

                .lineToLinearHeading(new Pose2d(-62.8, 23.5, Math.toRadians(0)))

                .addTemporalMarker(7.14,() -> attachment.movePickUpPositionGround())
                .build();
        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())
                .lineToLinearHeading(new Pose2d(-57, 23.5, Math.toRadians(0)))

                .build();


        attachment.moveV4B();
        drive.followTrajectorySequence(traj1);
        attachment.movePickUpCone2();
        drive.followTrajectorySequence(traj2);
        drive.update();



    }
}

