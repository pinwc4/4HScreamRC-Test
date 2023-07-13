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
                        drive.trajectorySequenceBuilder(new Pose2d(-39, 63, Math.toRadians(90)))


                                .splineToLinearHeading(new Pose2d(-37.00, 60.00, Math.toRadians(90.00)), Math.toRadians(280.00))
                                .splineToSplineHeading(new Pose2d(-36.00, -15.00, Math.toRadians(90.00)), Math.toRadians(270.00))
                                .splineToSplineHeading(new Pose2d(-57.50, -23.50, Math.toRadians(360.00)), Math.toRadians(190.00))
                                .splineToLinearHeading(new Pose2d(-67.80, -11.00, Math.toRadians(360.00)), Math.toRadians(90.00))

                                .lineToLinearHeading(new Pose2d(-55.00, -11.00, Math.toRadians(360.00)))
                                .lineToLinearHeading(new Pose2d(-62.00, -11.00, Math.toRadians(360.00)))

                                .splineToSplineHeading(new Pose2d(-30.00, -13.00, Math.toRadians(35.00)), Math.toRadians(360.00))

                                .splineToLinearHeading(new Pose2d(-27.00, -0.00, Math.toRadians(40.00)), Math.toRadians(380.00))

                                .lineToLinearHeading(new Pose2d(12.00, -8.00, Math.toRadians(180.00)))


                                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
