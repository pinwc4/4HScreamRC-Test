package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;

@Autonomous(name = "RoadRunnerTest")

public class RoadRunnerTest extends LinearOpMode {
    @Override
    public void runOpMode() {


        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);


        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(-39, -63, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        Trajectory traj1 = drive.trajectoryBuilder(startPose)
                .splineToLinearHeading(new Pose2d(-34, -60, Math.toRadians(270)), Math.toRadians(0))

                .build();

        Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                .lineToLinearHeading(new Pose2d(-35, -9, Math.toRadians(270)))
                .build();


        Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
                .splineToLinearHeading(new Pose2d(-27, -19, Math.toRadians(315)), Math.toRadians(-20))
                .build();

        Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
                .lineToLinearHeading(new Pose2d(-36, -11, Math.toRadians(315)))
                .build();

        Trajectory traj5 = drive.trajectoryBuilder(traj4.end())
                .splineToLinearHeading(new Pose2d(-52, -11, Math.toRadians(360)), Math.toRadians(-220))
                .build();

        Trajectory traj6 = drive.trajectoryBuilder(traj5.end())
                .lineToLinearHeading(new Pose2d(-62, -11, Math.toRadians(360)))
                .build();

        Trajectory traj7 = drive.trajectoryBuilder(traj6.end())
                .lineToLinearHeading(new Pose2d(-40, -11, Math.toRadians(360)))
                .build();

        Trajectory traj8 = drive.trajectoryBuilder(traj7.end())
                .splineToLinearHeading(new Pose2d(-27, -19, Math.toRadians(315)), Math.toRadians(0))
                .build();

        waitForStart();

        while(isStarted()){

            drive.followTrajectory(traj1);
            drive.followTrajectory(traj2);
            attachment.moveSlidesM();
            drive.followTrajectory(traj3);
            attachment.moveDropOff();
            drive.followTrajectory(traj4);
            attachment.movePickUpPosition();
            drive.followTrajectory(traj5);
            drive.followTrajectory(traj6);
            drive.followTrajectory(traj7);
            drive.followTrajectory(traj8);

            stop();


        }

    }


}
