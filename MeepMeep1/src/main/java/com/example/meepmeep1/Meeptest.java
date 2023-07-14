package com.example.meepmeep1;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Meeptest {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, 63, Math.toRadians(90)))


                                .splineToLinearHeading(new Pose2d(23.00, -0.50, Math.toRadians(90.00)), Math.toRadians(360.00))
                                .splineToLinearHeading(new Pose2d(-62.80, 12.00, Math.toRadians(180.00)), Math.toRadians(360.00))
                                .splineToSplineHeading(new Pose2d(-46.00, 13.00, Math.toRadians(0.00)), Math.toRadians(180.00))
                                .splineToSplineHeading(new Pose2d(-60.80, 11.00, Math.toRadians(0.00)), Math.toRadians(180.00))
                                .splineToSplineHeading(new Pose2d(-46.00, 13.00, Math.toRadians(0.00)), Math.toRadians(180.00))
                                .splineToSplineHeading(new Pose2d(-60.80, 11.00, Math.toRadians(0.00)), Math.toRadians(180.00))
                                .splineToSplineHeading(new Pose2d(-46.00, 13.00, Math.toRadians(0.00)), Math.toRadians(180.00))
                                .splineToSplineHeading(new Pose2d(-60.80, 11.00, Math.toRadians(0.00)), Math.toRadians(180.00))
                                .lineToLinearHeading(new Pose2d(-36.00, 14.00, Math.toRadians(90.00)))
                                .splineToSplineHeading(new Pose2d(-46.00, 13.00, Math.toRadians(0.00)), Math.toRadians(180.00))
                                .splineToSplineHeading(new Pose2d(-58.00, 13.00, Math.toRadians(0.00)), Math.toRadians(180.00))


                                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
