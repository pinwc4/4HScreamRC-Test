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
                        drive.trajectorySequenceBuilder(new Pose2d(39, -63, 270))


                                .splineToLinearHeading(new Pose2d(35, -60, Math.toRadians(270)), Math.toRadians(0))

                                .lineToLinearHeading(new Pose2d(35, -27, Math.toRadians(270)))

                                .splineToSplineHeading(new Pose2d(35, -9, Math.toRadians(250)), Math.toRadians(218))
                                .splineToLinearHeading(new Pose2d(28, -19, Math.toRadians(225)), Math.toRadians(230))


                                .setReversed(true)

                                .lineToLinearHeading(new Pose2d(56, -11.75, Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(60.8, -11.75, Math.toRadians(180)))

                                .setReversed(false)
                                .lineToLinearHeading(new Pose2d(56, -11, Math.toRadians(180)))
                                .splineToLinearHeading(new Pose2d(27, -21, Math.toRadians(145)), Math.toRadians(-20))
                                /*



                                .setReversed(true)
                                .lineToLinearHeading(new Pose2d(-56, -11, Math.toRadians(360)))
                                .lineToLinearHeading(new Pose2d(-60.8, -11.3, Math.toRadians(360)))


                                .setReversed(true)
                                .lineToLinearHeading(new Pose2d(-56, -11, Math.toRadians(360)))
                                .lineToLinearHeading(new Pose2d(-60.8, -10.9, Math.toRadians(360)))


                                .setReversed(true)
                                .lineToLinearHeading(new Pose2d(-36, -12, Math.toRadians(270)))


                                 */



                                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
