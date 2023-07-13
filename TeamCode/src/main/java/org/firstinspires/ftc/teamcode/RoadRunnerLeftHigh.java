package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

@Disabled
class RoadRunnerLeftHigh extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException{


        MecanumVelocityConstraint slowestMode = new MecanumVelocityConstraint(25, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint slowMode = new MecanumVelocityConstraint(30, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint normalMode = new MecanumVelocityConstraint(70, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        //MecanumVelocityConstraint expoMode = new

        int intColorLevel = 0;
        int intConeStack1 = -90;
        int intCycleCounter = 0;

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);

attachment.moveGrabber();
        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(-39, -63, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(startPose)

                .setReversed(true)
               .addTemporalMarker(1.5, () -> {
                    attachment.moveSlidesH();
                })
               // .addTemporalMarker(2, () -> {
                  //  attachment.senseSleeve();

                //})

                /*
                .addTemporalMarker(0.92, 0.1, () -> {
                    attachment.moveV4BOut();
                })

                .addTemporalMarker(0.95, 1.5, () -> {
                    attachment.moveGrabber();
                })

                .addTemporalMarker(0.99, 4, () -> {
                    attachment.moveV4BIn();
                })

                 */

                .splineToLinearHeading(new Pose2d(-35, -60, Math.toRadians(270)), Math.toRadians(0))

                .lineToLinearHeading(new Pose2d(-36.04, 24, Math.toRadians(270)))



                .splineToLinearHeading(new Pose2d(-23.71, 3.13, Math.toRadians(290)), Math.toRadians(300))

                .addTemporalMarker(() -> attachment.moveV4BOutHigh())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveGrabber())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveV4BIn())

                .build();

        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-32, 9.24, Math.toRadians(0)), Math.toRadians(180))

                .splineToLinearHeading(new Pose2d(-31, -11.7, Math.toRadians(0)), Math.toRadians(0))

                .lineToLinearHeading(new Pose2d(-62, 12, Math.toRadians(0)))
                .build();




        TrajectorySequence traj3 = drive.trajectorySequenceBuilder(traj2.end())

                //.addTemporalMarker(0.20, () -> {
                 //   attachment.moveStackCenter();
              //  })
                .addTemporalMarker(0.25, () -> {
                    attachment.movePickUpPosition();
                })


                .setReversed(false)

                .addTemporalMarker(() -> attachment.moveV4BOutHigh())
                .waitSeconds(0.075)
                .addTemporalMarker(() -> attachment.moveGrabber())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveV4BIn())

                .lineToLinearHeading(new Pose2d(-54, -12,Math.toRadians(0)))
                .build();




        TrajectorySequence traj4 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-48, -12, Math.toRadians(0)), Math.toRadians(180))

                .splineToSplineHeading(new Pose2d(-25.61, -1.25, Math.toRadians(222)), Math.toRadians(222-1.25))

                .build();

        TrajectorySequence traj5 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-46, -12, Math.toRadians(360)), Math.toRadians(180))
                .setVelConstraint(slowestMode)
                .splineToSplineHeading(new Pose2d(-59, -10.5, Math.toRadians(360)), Math.toRadians(180))
                .setVelConstraint(normalMode)

                .build();

        TrajectorySequence park2 = drive.trajectorySequenceBuilder(traj3.end())

                .addTemporalMarker(0.25, () -> {
                    attachment.moveSlidesDown();
                })

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-36, -14, Math.toRadians(270)))


                .build();

        TrajectorySequence park1 = drive.trajectorySequenceBuilder(traj3.end())

                .addTemporalMarker(0.25, () -> {
                    attachment.moveSlidesDown();
                })

                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-46, -13, Math.toRadians(360)), Math.toRadians(180))
                .setVelConstraint(slowestMode)
                .splineToSplineHeading(new Pose2d(-58, -13, Math.toRadians(360)), Math.toRadians(180))
                .setVelConstraint(normalMode)


                .build();

        TrajectorySequence park3 = drive.trajectorySequenceBuilder(traj3.end())

                .addTemporalMarker(0.25, () -> {
                    attachment.moveSlidesDown();
                })

                .setReversed(true)

                .lineToLinearHeading(new Pose2d(-40, -13.5, Math.toRadians(360)))
                .setReversed(false)
                .lineToLinearHeading(new Pose2d(-12, -13.5, Math.toRadians(360)))



                .build();


        waitForStart();

        resetRuntime();

        attachment.moveV4B();



        drive.followTrajectorySequence(traj1);

        drive.followTrajectorySequence(traj2);

        attachment.movePickUpCone2();

        attachment.moveWallSpacerOut();

        attachment.moveStackCenter();

        drive.followTrajectorySequence(traj3);

        drive.update();

        //intColorLevel = attachment.getintColorLevel();



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








            intConeStack1 = intConeStack1 + 52;//52
            intCycleCounter++;

        }


        attachment.moveWallSpacerIn();

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
