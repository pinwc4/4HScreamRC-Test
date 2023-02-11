package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "RIGHT SIDE Road Runner")

public class RoadRunnerRIGHTSIDE extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException{



        MecanumVelocityConstraint slowMode = new MecanumVelocityConstraint(20, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint normalMode = new MecanumVelocityConstraint(50, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());

        int intColorLevel = 0;
        int intConeStack1 = -90;
        int intCycleCounter = 0;

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);


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

                .splineToLinearHeading(new Pose2d(35, -60, Math.toRadians(270)), Math.toRadians(0))
                .setVelConstraint(slowMode)
                .lineToLinearHeading(new Pose2d(35, -9, Math.toRadians(270)))
                .setVelConstraint(normalMode)
                .splineToLinearHeading(new Pose2d(28, -20, Math.toRadians(225)), Math.toRadians(20))

                .build();

        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(56, -12, Math.toRadians(180)))
                .lineToLinearHeading(new Pose2d(60, -12, Math.toRadians(180)))
                .waitSeconds(0.25)

                .build();

        TrajectorySequence traj3 = drive.trajectorySequenceBuilder(traj2.end())

                .addTemporalMarker(0.5, () -> {
                    attachment.moveSlidesM();
                })
                .setReversed(false)
                .lineToLinearHeading(new Pose2d(56, -11, Math.toRadians(180)))
                .splineToLinearHeading(new Pose2d(30, -21, Math.toRadians(225)), Math.toRadians(20))

                .build();




        TrajectorySequence traj4 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(56, -12.5, Math.toRadians(180)))
                .lineToLinearHeading(new Pose2d(60, -12.5, Math.toRadians(180)))
                .waitSeconds(0.25)

                .build();

        TrajectorySequence traj5 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(56, -13, Math.toRadians(180)))
                .lineToLinearHeading(new Pose2d(60 -13, Math.toRadians(180)))
                .waitSeconds(0.25)

                .build();

        TrajectorySequence park2 = drive.trajectorySequenceBuilder(traj3.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(36, -12, Math.toRadians(270)))


                .build();

        TrajectorySequence park1 = drive.trajectorySequenceBuilder(traj3.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(36, -12, Math.toRadians(180)))
                .lineToLinearHeading(new Pose2d(12, -12, Math.toRadians(180)))


                .build();

        TrajectorySequence park3 = drive.trajectorySequenceBuilder(traj3.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(36, -12, Math.toRadians(180)))
                .setReversed(false)
                .lineToLinearHeading(new Pose2d(57, -12, Math.toRadians(180)))


                .build();


        waitForStart();

        resetRuntime();

        drive.followTrajectorySequence(traj1);
        drive.update();

        attachment.moveJoeTest();

        intColorLevel = attachment.getintColorLevel();


        while (getRuntime() < 23) {


            if(intCycleCounter == 0){
                drive.followTrajectorySequenceAsync(traj2);
                while (drive.isBusy() && opModeIsActive()) {
                    attachment.movePickUpPosition();
                    drive.update();
                }
            } else if(intCycleCounter == 1){
                drive.followTrajectorySequenceAsync(traj4);
                while (drive.isBusy() && opModeIsActive()) {
                    attachment.movePickUpPosition();
                    drive.update();
                }
            } else if(intCycleCounter == 2){
                drive.followTrajectorySequenceAsync(traj5);
                while (drive.isBusy() && opModeIsActive()) {
                    attachment.movePickUpPosition();
                    drive.update();
                }
            }


            attachment.movePickUpCone(intConeStack1);

            drive.followTrajectorySequence(traj3);
            drive.update();

            attachment.moveJoeTest();

            intConeStack1 = intConeStack1 + 52;//52
            intCycleCounter++;

        }



        attachment.moveSlidesDown();

        if(intColorLevel == 1){
            drive.followTrajectorySequence(park1);
            drive.update();
        } else if (intColorLevel == 2){
            drive.followTrajectorySequence(park2);
            drive.update();
        } else {
            drive.followTrajectorySequence(park3);
            drive.update();
        }




    }


}
