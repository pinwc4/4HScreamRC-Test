package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;



@Autonomous(name = "CRI Trans Wait Left")



public class CRITransWaitLeft extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {

        MecanumVelocityConstraint slowestMode = new MecanumVelocityConstraint(25, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint slowMode = new MecanumVelocityConstraint(30, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint normalMode = new MecanumVelocityConstraint(70, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());

        int intColorLevel = 1;
        int intConeStack1 = -90;
        int intCycleCounter = 0;

        RevBlinkinLedDriver lights;
        lights = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        waitForStart();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);


        Pose2d startPose = new Pose2d(-39, -63, Math.toRadians(270));
        drive.setPoseEstimate(startPose);

        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);


        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(startPose)



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
                .lineToLinearHeading(new Pose2d(-55, 11, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(-62, 11, Math.toRadians(0)))

                .setVelConstraint(normalMode)

                .build();







        //WAITS TO CYCLE


        TrajectorySequence wait1 = drive.trajectorySequenceBuilder(traj2.end())

                .splineToSplineHeading(new Pose2d(-30, 13, Math.toRadians(325)), Math.toRadians(0))

                .build();


        TrajectorySequence wait2 = drive.trajectorySequenceBuilder(traj2.end())

                //old
                .splineToSplineHeading(new Pose2d(-30, 13, Math.toRadians(325)), Math.toRadians(0))


                .build();






        //MOVES TO POLE


        TrajectorySequence poleMove1 = drive.trajectorySequenceBuilder(wait1.end())

                .addTemporalMarker(0.25, () -> {
                    attachment.moveSlidesH();
                })

                .splineToLinearHeading(new Pose2d(-27, 2, Math.toRadians(325)), Math.toRadians(-20))

                .addTemporalMarker(() -> attachment.moveV4BOut())
                .waitSeconds(0.075)
                .addTemporalMarker(() -> attachment.moveGrabber())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveV4BIn())

                .build();


        TrajectorySequence poleMove2 = drive.trajectorySequenceBuilder(wait2.end())

                .splineToLinearHeading(new Pose2d(-27, 4, Math.toRadians(325)), Math.toRadians(-20))


                .build();





        //PARKS BASED ON SIGNAL SLEEVE


        TrajectorySequence park1 = drive.trajectorySequenceBuilder(poleMove1.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-35, 10, Math.toRadians(360)))


                .build();


        TrajectorySequence park2 = drive.trajectorySequenceBuilder(poleMove2.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-36, 4, Math.toRadians(270)))


                .build();


        TrajectorySequence park3 = drive.trajectorySequenceBuilder(poleMove2.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-36, 4, Math.toRadians(360)))
                .setReversed(false)
                .lineToLinearHeading(new Pose2d(-12, 4, Math.toRadians(360)))


                .build();







        // SEGMENTS



        attachment.moveV4B();

        drive.followTrajectorySequence(traj1);

        drive.followTrajectorySequence(traj2);

        attachment.movePickUpCone2();

        drive.update();


        if(intColorLevel == 1){

            drive.followTrajectorySequence(wait1);

            drive.update();

        }


        else if (intColorLevel == 2){

            drive.followTrajectorySequence(park2);

            drive.update();

        }

        else {

            drive.followTrajectorySequence(park3);

            drive.update();

        }


        while (getRuntime() < 25){
            Thread.sleep(100);
        }

        drive.followTrajectorySequence(poleMove1);
        drive.followTrajectorySequence(park1);

    }
}