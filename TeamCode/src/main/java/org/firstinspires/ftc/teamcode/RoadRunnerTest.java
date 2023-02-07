package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "RoadRunnerTest")

public class RoadRunnerTest extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException{



        MecanumVelocityConstraint slowMode = new MecanumVelocityConstraint(30, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint normalMode = new MecanumVelocityConstraint(52, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());

        int intColorLevel = 0;
        int intConeStack1 = -95;
        int intCycleCounter = 0;

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);


        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(-39, -63, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(startPose)


                .addTemporalMarker(1.5, () -> {
                    attachment.moveSlidesM();
                })
                .addTemporalMarker(1, () -> {
                    attachment.senseSleeve();
                })

                .splineToLinearHeading(new Pose2d(-34, -60, Math.toRadians(270)), Math.toRadians(0))
                .setVelConstraint(slowMode)
                .lineToLinearHeading(new Pose2d(-35, -9, Math.toRadians(270)))
                .setVelConstraint(normalMode)
                .splineToLinearHeading(new Pose2d(-27, -19, Math.toRadians(315)), Math.toRadians(-20))

                .build();

        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-56, -11, Math.toRadians(360)))
                .lineToLinearHeading(new Pose2d(-60, -11, Math.toRadians(360)))
                .waitSeconds(0.5)

                .build();

        TrajectorySequence traj3 = drive.trajectorySequenceBuilder(traj2.end())

                .addTemporalMarker(0.5, () -> {
                    attachment.moveSlidesM();
                })
                .setReversed(false)
                .lineToLinearHeading(new Pose2d(-56, -11, Math.toRadians(360)))
                .splineToLinearHeading(new Pose2d(-27, -21, Math.toRadians(325)), Math.toRadians(-20))

                .build();




        TrajectorySequence traj4 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-56, -11, Math.toRadians(360)))
                .lineToLinearHeading(new Pose2d(-60, -10, Math.toRadians(360)))
                .waitSeconds(0.5)

                .build();

        TrajectorySequence traj5 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-56, -11, Math.toRadians(360)))
                .lineToLinearHeading(new Pose2d(-60, -9, Math.toRadians(360)))
                .waitSeconds(0.5)

                .build();

        TrajectorySequence traj6 = drive.trajectorySequenceBuilder(traj3.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-36, -12, Math.toRadians(270)))


                .build();


        waitForStart();

        resetRuntime();

        drive.followTrajectorySequence(traj1);
        drive.update();

        attachment.moveJoeTest();

        intColorLevel = attachment.getintColorLevel();

        if(intColorLevel > 0){
            stop();
        }


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

            intConeStack1 = intConeStack1 + 40;//52
            intCycleCounter++;

        }




    }


}
