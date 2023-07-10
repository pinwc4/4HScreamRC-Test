package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "Center Left")

public class criCenterLeft extends LinearOpMode {

    @Override

    public void runOpMode() throws InterruptedException {
        int intColorLevel = 0;
        int intConeStack1 = -90;
        int intCycleCounter = 0;

        RevBlinkinLedDriver lights;
        lights = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        waitForStart();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);


        Pose2d startPose = new Pose2d(12, -63, Math.toRadians(270));
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
                .splineToLinearHeading(new Pose2d(23, 0.5, Math.toRadians(270)), Math.toRadians(0))

                .addTemporalMarker(() -> attachment.moveV4BOut())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveGrabber())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveV4BOut())

                .splineToLinearHeading(new Pose2d(-62.8, -12, Math.toRadians(180)), Math.toRadians(0))

                .build();
        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())

                .addTemporalMarker(0.25, () -> {
                    attachment.moveSlidesH();
                })


                .setReversed(false)
                .splineToLinearHeading(new Pose2d(-48, -12, Math.toRadians(360)), Math.toRadians(360))
                .splineToSplineHeading(new Pose2d(-27.75, -23, Math.toRadians(330)), Math.toRadians(340)) //225
                .addTemporalMarker(() -> attachment.moveV4BOut())
                .waitSeconds(0.075)
                .addTemporalMarker(() -> attachment.moveGrabber())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveV4BIn())

                .build();




        TrajectorySequence traj3 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-46, -13, Math.toRadians(360)), Math.toRadians(180))

                .splineToSplineHeading(new Pose2d(-60.8, -11, Math.toRadians(360)), Math.toRadians(180))


                .build();

        TrajectorySequence traj4 = drive.trajectorySequenceBuilder(traj1.end())

                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-46, -12, Math.toRadians(360)), Math.toRadians(180))

                .splineToSplineHeading(new Pose2d(-59, -10.5, Math.toRadians(360)), Math.toRadians(180))


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

                .splineToSplineHeading(new Pose2d(-58, -13, Math.toRadians(360)), Math.toRadians(180))



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
        drive.update();

       // intColorLevel = attachment.getintColorLevel();



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
                drive.followTrajectorySequenceAsync(traj4);
                while (drive.isBusy() && opModeIsActive()) {
                    attachment.movePickUpPosition();
                    attachment.moveWallSpacerOut();
                    drive.update();
                }
            }

            attachment.moveStackCenter();

            attachment.movePickUpCone2();

            drive.followTrajectorySequence(traj3);
            drive.update();

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
