package com.example.meepmeep1;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class RedLeft {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-39, -63, Math.toRadians(270)))

                                .splineToLinearHeading(new Pose2d(-34, -60, Math.toRadians(270)), Math.toRadians(0))
                                .lineToLinearHeading(new Pose2d(-35, -9, Math.toRadians(270)))
                                .splineToLinearHeading(new Pose2d(-27, -19, Math.toRadians(325)), Math.toRadians(-20))

                                .setReversed(true)

                                //.lineToLinearHeading(new Pose2d(-36, -11, Math.toRadians(315)))
                                .lineToLinearHeading(new Pose2d(-56, -11, Math.toRadians(360)))
                                .lineToLinearHeading(new Pose2d(-60.5, -11, Math.toRadians(360)))

                                .setReversed(false)
                                .lineToLinearHeading(new Pose2d(-56, -11, Math.toRadians(360)))
                                .splineToLinearHeading(new Pose2d(-27, -21, Math.toRadians(325)), Math.toRadians(-20))

                                /*
                                .splineToLinearHeading(new Pose2d(-34, -60, Math.toRadians(270)), Math.toRadians(0))
                                .lineToLinearHeading(new Pose2d(-35, -9, Math.toRadians(270)))
                                .splineToLinearHeading(new Pose2d(-27, -19, Math.toRadians(315)), Math.toRadians(-20))

                                .lineToLinearHeading(new Pose2d(-36, -11, Math.toRadians(315)))
                                .splineToLinearHeading(new Pose2d(-52, -11, Math.toRadians(360)), Math.toRadians(-220))
                                .lineToLinearHeading(new Pose2d(-62, -11, Math.toRadians(360)))

                                .lineToLinearHeading(new Pose2d(-40, -11, Math.toRadians(360)))
                                .splineToLinearHeading(new Pose2d(-27, -19, Math.toRadians(315)), Math.toRadians(0))

                                .lineToLinearHeading(new Pose2d(-36, -11, Math.toRadians(315)))
                                .splineToLinearHeading(new Pose2d(-52, -11, Math.toRadians(360)), Math.toRadians(-220))
                                .lineToLinearHeading(new Pose2d(-62, -11, Math.toRadians(360)))

                                .lineToLinearHeading(new Pose2d(-40, -11, Math.toRadians(360)))
                                .splineToLinearHeading(new Pose2d(-27, -19, Math.toRadians(315)), Math.toRadians(0))

                                .lineToLinearHeading(new Pose2d(-36, -11, Math.toRadians(315)))
                                .splineToLinearHeading(new Pose2d(-52, -11, Math.toRadians(360)), Math.toRadians(-220))
                                .lineToLinearHeading(new Pose2d(-62, -11, Math.toRadians(360)))

                                .lineToLinearHeading(new Pose2d(-40, -11, Math.toRadians(360)))
                                .splineToLinearHeading(new Pose2d(-27, -19, Math.toRadians(315)), Math.toRadians(0))

                                 */
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}

