package com.example.meepmeep1;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class RightMedium {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(39, -63, 270))



                                //traj 1

                                .setReversed(true)

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








                .splineToSplineHeading(new Pose2d(-32, 9.24, Math.toRadians(0)), Math.toRadians(180))

                .splineToLinearHeading(new Pose2d(-31, -11.7, Math.toRadians(0)), Math.toRadians(0))

                .lineToLinearHeading(new Pose2d(-62, 12, Math.toRadians(0)))











                .lineToLinearHeading(new Pose2d(-54, -12,Math.toRadians(0)))


                                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
