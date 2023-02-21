package com.example.meepmeep1;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BlueLeft {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(39, -63, Math.toRadians(270)))
                                .splineToLinearHeading(new Pose2d(35, -60, Math.toRadians(270)), Math.toRadians(46))
                                .lineToLinearHeading(new Pose2d(35, -9, Math.toRadians(270)))
                                .splineToLinearHeading(new Pose2d(28, -20, Math.toRadians(225)), Math.toRadians(46))

                                .setReversed(true)
                                .lineToLinearHeading(new Pose2d(56, -12, Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(60, -12, Math.toRadians(180)))
                                .waitSeconds(0.25)

                                .setReversed(false)
                                .lineToLinearHeading(new Pose2d(56, -11, Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(30, -21, Math.toRadians(225)))

                                .setReversed(true)
                                .lineToLinearHeading(new Pose2d(56, -12.5, Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(60, -12.5, Math.toRadians(180)))
                                .waitSeconds(0.25)

                                .setReversed(false)
                                .lineToLinearHeading(new Pose2d(56, -11, Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(30, -21, Math.toRadians(225)))

                                .setReversed(true)
                                .lineToLinearHeading(new Pose2d(56, -13, Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(60 -13, Math.toRadians(180)))
                                .waitSeconds(0.25)

                                .setReversed(false)
                                .lineToLinearHeading(new Pose2d(56, -11, Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(30, -21, Math.toRadians(225)))

                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}

