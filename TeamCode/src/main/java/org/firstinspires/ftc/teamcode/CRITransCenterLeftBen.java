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


        TrajectorySequence untitled0 = drive.trajectorySequenceBuilder(new Pose2d(16.00, -64.00, Math.toRadians(270.00)))
                .setReversed(true)
                .setVelConstraint(normalMode)
                .splineToSplineHeading(new Pose2d(12.00, -36.00, Math.toRadians(270.00)), Math.toRadians(90.00))
                .splineTo(new Vector2d(12.00, -4.00), Math.toRadians(90.00))
                .splineTo(new Vector2d(7.00, 11.00), Math.toRadians(178.66))
                .splineTo(new Vector2d(-27.00, 11.00), Math.toRadians(180.00))
                .splineTo(new Vector2d(-41.00, 29.00), Math.toRadians(180.00))
                .splineTo(new Vector2d(-50.00, 29.00), Math.toRadians(180.00))
                .splineTo(new Vector2d(-58.00, 29.00), Math.toRadians(180.00))
                .build();









        drive.followTrajectorySequence(untitled0);
        drive.update();



    }
}

