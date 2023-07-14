package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;


@Autonomous(name = "CRI Trans Wait Right")



public class CRITransWaitRight extends LinearOpMode {

    private SleeveDetectionRight sleeveDetection;
    private OpenCvCamera camera;

    // Name of the Webcam to be set in the config
    private String webcamName = "Webcam 1";
    @Override

    public void runOpMode() throws InterruptedException {



        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetectionRight();
        camera.setPipeline(sleeveDetection);



        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(320,240, OpenCvCameraRotation.SIDEWAYS_RIGHT);
            }

            @Override
            public void onError(int errorCode) {}
        });

        while (!isStarted()) {
            telemetry.addData("ROTATION: ", sleeveDetection.getPosition());
            telemetry.update();
        }

        MecanumVelocityConstraint slowestMode = new MecanumVelocityConstraint(25, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint slowMode = new MecanumVelocityConstraint(30, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());
        MecanumVelocityConstraint normalMode = new MecanumVelocityConstraint(70, DriveConstants.getTrackWidth(), DriveConstants.getWheelBase());

        int intColorLevel = 2;
        int intConeStack1 = -90;
        int intCycleCounter = 0;

        RevBlinkinLedDriver lights;
        lights = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        waitForStart();

        Double dblStartRuntime = getRuntime();

        String strColorLevel = String.valueOf(sleeveDetection.getPosition());


        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerAttachment attachment = new RoadRunnerAttachment(hardwareMap, telemetry);


        Pose2d startPose = new Pose2d(-39, 63, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);


        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(startPose)



                .addTemporalMarker(2.5, () -> attachment.movePickUpPositionGround())

                .splineToLinearHeading(new Pose2d(-37.5, 60.00, Math.toRadians(90.00)), Math.toRadians(280.00))
                .splineToSplineHeading(new Pose2d(-37.5, -15.00, Math.toRadians(90.00)), Math.toRadians(270.00))
                .splineToSplineHeading(new Pose2d(-57.5, -21, Math.toRadians(360.00)), Math.toRadians(190.00))
                .splineToLinearHeading(new Pose2d(-67.8, -11.00, Math.toRadians(360.00)), Math.toRadians(90.00))


                .build();


        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())

                .setVelConstraint(slowestMode)

                .lineToLinearHeading(new Pose2d(-60, -11.00, Math.toRadians(360.00)))
                .lineToLinearHeading(new Pose2d(-67.00, -11.00, Math.toRadians(360.00)))

                .setVelConstraint(normalMode)

                .build();







        //WAITS TO CYCLE


        TrajectorySequence wait1 = drive.trajectorySequenceBuilder(traj2.end())

                .splineToSplineHeading(new Pose2d(-30.00, -13.00, Math.toRadians(35.00)), Math.toRadians(360.00))

                .build();


        TrajectorySequence wait2 = drive.trajectorySequenceBuilder(traj2.end())

                //old
                .splineToSplineHeading(new Pose2d(-30.00, -13.00, Math.toRadians(35.00)), Math.toRadians(360.00))


                .build();






        //MOVES TO POLE


        TrajectorySequence poleMove1 = drive.trajectorySequenceBuilder(wait1.end())

                .addTemporalMarker(0.25, () -> {
                    attachment.moveSlidesH();
                })

                .splineToLinearHeading(new Pose2d(-27.00, -0.00, Math.toRadians(40.00)), Math.toRadians(380.00))

                .addTemporalMarker(() -> attachment.moveV4BOut())
                .waitSeconds(0.075)
                .addTemporalMarker(() -> attachment.moveGrabber())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveV4BIn())

                .build();


        TrajectorySequence poleMove2 = drive.trajectorySequenceBuilder(wait2.end())

                .addTemporalMarker(0.25, () -> {
                    attachment.moveSlidesH();
                })

                .splineToLinearHeading(new Pose2d(-27.00, -0.00, Math.toRadians(40.00)), Math.toRadians(380.00))

                .addTemporalMarker(() -> attachment.moveV4BOut())
                .waitSeconds(0.075)
                .addTemporalMarker(() -> attachment.moveGrabber())
                .waitSeconds(0.15)
                .addTemporalMarker(() -> attachment.moveV4BIn())



                .build();





        //PARKS BASED ON SIGNAL SLEEVE


        TrajectorySequence park1 = drive.trajectorySequenceBuilder(poleMove1.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-35.00, -10.00, Math.toRadians(0.00)))


                .build();


        TrajectorySequence park2 = drive.trajectorySequenceBuilder(poleMove2.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-12.00, -8.00, Math.toRadians(180.00)))

                .build();


        TrajectorySequence park3 = drive.trajectorySequenceBuilder(poleMove2.end())

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(12.00, -8.00, Math.toRadians(180.00)))



                .build();







        // SEGMENTS



        attachment.moveV4B();

        drive.followTrajectorySequence(traj1);

        drive.followTrajectorySequence(traj2);

        attachment.movePickUpCone2();

        drive.update();


        if(strColorLevel == "LEFT"){

            drive.followTrajectorySequence(wait1);

            drive.update();

        }


        else if (strColorLevel == "CENTER"){

            drive.followTrajectorySequence(wait2);

            drive.update();

        }

        else{
            drive.followTrajectorySequence(wait2);

            drive.update();
        }



        while ((getRuntime() - dblStartRuntime) < 25){
            Thread.sleep(100);
        }

        drive.followTrajectorySequence(poleMove2);

        if(strColorLevel == "LEFT"){
            drive.followTrajectorySequence(park1);
            drive.update();
        } else if (strColorLevel == "CENTER"){
            drive.followTrajectorySequence(park2);
            drive.update();
        } else {
            drive.followTrajectorySequence(park3);
            drive.update();
        }

        attachment.moveSlidesDown();
        Thread.sleep(2000);
    }
}