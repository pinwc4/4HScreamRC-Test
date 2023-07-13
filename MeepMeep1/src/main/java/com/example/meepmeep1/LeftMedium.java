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







                                //robot moves out
                                .splineToLinearHeading(new Pose2d(-37, -60, Math.toRadians(270)), Math.toRadians(80))

                                //robot moves out
                                .splineToSplineHeading(new Pose2d(-36, 15, Math.toRadians(270)), Math.toRadians(90))

                                //robot moves to cone
                                .splineToSplineHeading(new Pose2d(-57.5, 23.5, Math.toRadians(0)), Math.toRadians(170))

                                //robot moves sideways
                                .splineToLinearHeading(new Pose2d(-67.8, 11, Math.toRadians(0)), Math.toRadians(270))







                .lineToLinearHeading(new Pose2d(-64, 11, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(-67.8, 11, Math.toRadians(0)))









        //WAITS TO CYCLE





                .splineToSplineHeading(new Pose2d(-30, 13, Math.toRadians(325)), Math.toRadians(0))








        //MOVES TO POLE




                .splineToLinearHeading(new Pose2d(-27, 4, Math.toRadians(325)), Math.toRadians(-20))











        //PARKS BASED ON SIGNAL SLEEVE




                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-30, 5, Math.toRadians(360)))




                .build());








        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
