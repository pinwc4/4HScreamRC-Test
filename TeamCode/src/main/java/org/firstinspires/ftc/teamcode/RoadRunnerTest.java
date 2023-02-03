package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "RoadRunnerTest")

public class RoadRunnerTest extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException{


        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);


        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(-39, -63, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(startPose)
                .addTemporalMarker(1.5, () -> {
                    attachment.moveSlidesM();
                })
                .splineToLinearHeading(new Pose2d(-34, -60, Math.toRadians(270)), Math.toRadians(0))
                .lineToLinearHeading(new Pose2d(-35, -9, Math.toRadians(270)))
                .splineToLinearHeading(new Pose2d(-27, -19, Math.toRadians(315)), Math.toRadians(-20))

                .build();

        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-58, -11, Math.toRadians(360)))
                .lineToLinearHeading(new Pose2d(-61, -11, Math.toRadians(360)))

                .build();

        /*
        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())
                .lineToLinearHeading(new Pose2d(-35, -9, Math.toRadians(270)))
                .build();

        TrajectorySequence traj3 = drive.trajectorySequenceBuilder(traj2.end())
                .splineToLinearHeading(new Pose2d(-27, -19, Math.toRadians(315)), Math.toRadians(-20))
                .build();

        TrajectorySequence traj4 = drive.trajectorySequenceBuilder(traj3.end())

                .lineToLinearHeading(new Pose2d(-36, -11, Math.toRadians(315)))
                .build();

        TrajectorySequence traj5 = drive.trajectorySequenceBuilder(traj4.end())
                .splineToLinearHeading(new Pose2d(-52, -11, Math.toRadians(360)), Math.toRadians(-220))
                .build();

        TrajectorySequence traj6 = drive.trajectorySequenceBuilder(traj5.end())
                .lineToLinearHeading(new Pose2d(-62, -11, Math.toRadians(360)))
                .build();

        TrajectorySequence traj7 = drive.trajectorySequenceBuilder(traj6.end())
                .lineToLinearHeading(new Pose2d(-40, -11, Math.toRadians(360)))
                .build();

        TrajectorySequence traj8 = drive.trajectorySequenceBuilder(traj7.end())
                .splineToLinearHeading(new Pose2d(-27, -19, Math.toRadians(315)), Math.toRadians(0))
                .build();

         */

        waitForStart();


        drive.followTrajectorySequence(traj1);

        attachment.moveJoeTest();

        drive.followTrajectorySequenceAsync(traj2);

        while (drive.isBusy() && opModeIsActive()){
            attachment.movePickUpPosition();
            drive.update();
        }


            /*
            drive.followTrajectorySequence(traj2); //Forward
            attachment.moveSlidesM(); //
            drive.followTrajectorySequence(traj3);
            attachment.moveDropOff();
            drive.followTrajectorySequence(traj4);
            attachment.movePickUpPosition();
            drive.followTrajectorySequence(traj5);
            drive.followTrajectorySequence(traj6);
            drive.followTrajectorySequence(traj7);
            drive.followTrajectorySequence(traj8);

             */






    }


}
