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

                                .splineToLinearHeading(new Pose2d(-35, -60, Math.toRadians(270)), Math.toRadians(0))


                                .lineToLinearHeading(new Pose2d(-35, 23.5, Math.toRadians(270)))

                                .splineToLinearHeading(new Pose2d(-48, 23.5, Math.toRadians(0)), Math.toRadians(330))

                                .splineToLinearHeading(new Pose2d(-25.5, 23.2, Math.toRadians(0)), Math.toRadians(0))
                                /*.splineToLinearHeading(new Pose2d(35, -60, Math.toRadians(270)), Math.toRadians(0))

                                //.setVelConstraint(slowMode)
                                .lineToLinearHeading(new Pose2d(35, -27, Math.toRadians(270)))

                                //.setVelConstraint(normalMode)
                                .splineToSplineHeading(new Pose2d(35, -9, Math.toRadians(250)), Math.toRadians(218))
                                .splineToSplineHeading(new Pose2d(25, -21, Math.toRadians(235)), Math.toRadians(235))



                                //traj 2

                                .setReversed(true)
                                //.splineToSplineHeading(new Pose2d(56, -12, Math.toRadians(180)), Math.toRadians(0))
                                .splineToSplineHeading(new Pose2d(60.8, -12, Math.toRadians(180)), Math.toRadians(0))


                                //traj 3

                                .setReversed(false)
                                .splineToLinearHeading(new Pose2d(44, -14.5, Math.toRadians(180)), Math.toRadians(180))
                                .splineToSplineHeading(new Pose2d(25.5, -24, Math.toRadians(225)), Math.toRadians(225))


                                //traj 4

                                .setReversed(true)
                                .splineToSplineHeading(new Pose2d(46, -13, Math.toRadians(180)), Math.toRadians(0))
                                .splineToSplineHeading(new Pose2d(60.8, -13, Math.toRadians(180)), Math.toRadians(0))

                                //traj 3

                                .setReversed(false)
                                .splineToLinearHeading(new Pose2d(44, -14.5, Math.toRadians(180)), Math.toRadians(180))
                                .splineToSplineHeading(new Pose2d(25.5, -24, Math.toRadians(225)), Math.toRadians(225))


                                //traj 5

                                .setReversed(true)
                                .splineToSplineHeading(new Pose2d(56, -13.5, Math.toRadians(180)), Math.toRadians(0))
                                .splineToSplineHeading(new Pose2d(60.8, -13.5, Math.toRadians(180)), Math.toRadians(0))


                                //traj 3

                                .setReversed(false)
                                .splineToLinearHeading(new Pose2d(44, -14.5, Math.toRadians(180)), Math.toRadians(180))
                                .splineToSplineHeading(new Pose2d(25.5, -24, Math.toRadians(225)), Math.toRadians(225))
*/


                                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
