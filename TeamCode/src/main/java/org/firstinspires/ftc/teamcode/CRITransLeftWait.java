package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "Transformer Wait")

public class CRITransLeftWait extends LinearOpMode {
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

                .addTemporalMarker(2.5, () -> attachment.movePickUpPositionGround())

                //robot moves out
                .splineToLinearHeading(new Pose2d(-37, -60, Math.toRadians(270)), Math.toRadians(80))

                //robot moves out
                .splineToSplineHeading(new Pose2d(-36, 15, Math.toRadians(270)), Math.toRadians(90))

                //robot moves to cone
                .splineToSplineHeading(new Pose2d(-57.5, 23.5, Math.toRadians(0)), Math.toRadians(170))

                //robot moves sideways
                .splineToLinearHeading(new Pose2d(-67.8, 11, Math.toRadians(0)), Math.toRadians(270))



                .build();


        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())

                .setVelConstraint(slowestMode)
                .lineToLinearHeading(new Pose2d(-64, 11, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(-67.8, 11, Math.toRadians(0)))

                .setVelConstraint(normalMode)

                .build();





        attachment.moveV4B();
        drive.followTrajectorySequence(traj1);
        drive.followTrajectorySequence(traj2);
        attachment.movePickUpCone2();
        drive.update();



    }
}

