package com.example.meepmeep1;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class LeftMedium {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-39, -63, 270))


                                //traj 1

                                .splineToLinearHeading(new Pose2d(-35, -60, Math.toRadians(270)), Math.toRadians(0))

                                //.setVelConstraint(slowestMode)
                                .lineToLinearHeading(new Pose2d(-35, -27, Math.toRadians(270)))

                                //.setVelConstraint(normalMode)
                                .splineToSplineHeading(new Pose2d(-35, -9, Math.toRadians(290)), Math.toRadians(322))
                                .splineToSplineHeading(new Pose2d(-25.75, -20.5, Math.toRadians(305)), Math.toRadians(305))



                                //traj 2

                                .setReversed(true)
                                .splineToSplineHeading(new Pose2d(-46, -13, Math.toRadians(360)), Math.toRadians(180))
                                //.setVelConstraint(slowestMode)
                                .splineToSplineHeading(new Pose2d(-60.8, -13, Math.toRadians(360)), Math.toRadians(180))
                                //.setVelConstraint(normalMode)


                                //traj 3

                                .setReversed(false)
                                .splineToLinearHeading(new Pose2d(-48, -14.5, Math.toRadians(360)), Math.toRadians(360))
                                .splineToSplineHeading(new Pose2d(-27.25, -24.75, Math.toRadians(330)), Math.toRadians(330))


                                //traj 4

                                .setReversed(true)
                                .splineToSplineHeading(new Pose2d(-46, -12.25, Math.toRadians(360)), Math.toRadians(180))
                                //.setVelConstraint(slowestMode)
                                .splineToSplineHeading(new Pose2d(-60.8, -12.25, Math.toRadians(360)), Math.toRadians(180))
                                //.setVelConstraint(normalMode)

                                //traj 3

                                .setReversed(false)
                                .splineToLinearHeading(new Pose2d(-48, -14.5, Math.toRadians(360)), Math.toRadians(360))
                                .splineToSplineHeading(new Pose2d(-27.25, -24.75, Math.toRadians(330)), Math.toRadians(330))


                                //traj 5

                                .setReversed(true)
                                .splineToSplineHeading(new Pose2d(-46, -11.5, Math.toRadians(360)), Math.toRadians(180))
                                //.setVelConstraint(slowestMode)
                                .splineToSplineHeading(new Pose2d(-59, -11.5, Math.toRadians(360)), Math.toRadians(180))
                                //.setVelConstraint(normalMode)


                                //traj 3

                                .setReversed(false)
                                .splineToLinearHeading(new Pose2d(-48, -14.5, Math.toRadians(360)), Math.toRadians(360))
                                .splineToSplineHeading(new Pose2d(-27.25, -24.75, Math.toRadians(330)), Math.toRadians(330))



                                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
