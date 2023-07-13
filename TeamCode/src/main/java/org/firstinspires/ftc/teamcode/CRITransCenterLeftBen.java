package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "CRITransCenterLeftBenE")

public class CRITransCenterLeftBen extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {

        MecanumVelocityConstraint slowestMode = new MecanumVelocityConstraint(25, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint slowMode = new MecanumVelocityConstraint(30, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint normalMode = new MecanumVelocityConstraint(70, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());

        RevBlinkinLedDriver lights;
        lights = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        waitForStart();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);


        Pose2d startPose = new Pose2d(16, -64, Math.toRadians(270));
        drive.setPoseEstimate(startPose);

        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);


        TrajectorySequence yellow1 = drive.trajectorySequenceBuilder(new Pose2d(16.00, -64.00, Math.toRadians(270.00)))
                .setReversed(true)
                .setVelConstraint(normalMode)
                .splineToSplineHeading(new Pose2d(12.00, -36.00, Math.toRadians(270.00)), Math.toRadians(90.00))
                .splineTo(new Vector2d(12.00, -4.00), Math.toRadians(90.00))
                .splineToSplineHeading(new Pose2d(7.00, 11.00, Math.toRadians(178.66)), Math.toRadians(180.00))
                .splineTo(new Vector2d(-29.00, 11.00), Math.toRadians(180.00))
                .splineToConstantHeading(new Vector2d(-37.00, 19.00), Math.toRadians(180.00))
                .splineTo(new Vector2d(-50.00, 19.00), Math.toRadians(180.00))
                .splineTo(new Vector2d(-62.00, 19.00), Math.toRadians(180.00))
                .build();

        //Navigate to parking spot 1

        TrajectorySequence park1 = drive.trajectorySequenceBuilder(yellow1.end())
                .setReversed(true)
                .setVelConstraint(normalMode)
                .splineToConstantHeading(new Vector2d(-33, 10.00), Math.toRadians(0.00))
                .build();

//Navigate to parking spot 2

        TrajectorySequence park2 = drive.trajectorySequenceBuilder(yellow1.end())
                .setReversed(true)
                .setVelConstraint(normalMode)
                .splineToConstantHeading(new Vector2d(-30.00, 10.00), Math.toRadians(0.00))
                .splineTo(new Vector2d(-8, 9.00), Math.toRadians(0.00))
                .build();


        //Navigate to parking spot 3

        TrajectorySequence park3 = drive.trajectorySequenceBuilder(yellow1.end())
                .setReversed(true)
                .setVelConstraint(normalMode)
                .splineToConstantHeading(new Vector2d(-30.00, 10.00), Math.toRadians(0.00))
                .splineTo(new Vector2d(-6.00, 9.00), Math.toRadians(0.00))
                .splineTo(new Vector2d(14.00, 8.00), Math.toRadians(0.00))
                .build();





        drive.followTrajectorySequence(yellow1);
        drive.update();

        Thread.sleep(5000);

        drive.followTrajectorySequence(park1);
        drive.update();

    }
}

