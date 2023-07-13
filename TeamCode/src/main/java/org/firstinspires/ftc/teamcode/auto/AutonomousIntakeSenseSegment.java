package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.robotcore.hardware.ColorSensor;


public class AutonomousIntakeSenseSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private ColorSensor snsColor;

    private int intNumSameRecognitions = 0;

    private double dblRGBValue;
    private boolean bolPowerSet = false;

    private static final double MINIMUM_SAME_RECOGNITIONS = 15;


    private boolean bolinitialized;


    public AutonomousIntakeSenseSegment(ColorSensor snsColor) {

        this.snsColor = snsColor;

       // this.dblRGBValue = dblRGBValue;

        bolinitialized = false;
    }

    private void init() {
        bolinitialized = true;
    }

    public void runSegment() {
        if (!bolinitialized) {
            init();
        }


        if(snsColor.red() > (snsColor.green()/2)){
            intNumSameRecognitions++;

            if(intNumSameRecognitions > MINIMUM_SAME_RECOGNITIONS){
                bolPowerSet = true;
            }

        }
        else {
            intNumSameRecognitions = 0;
        }


    }

    public boolean segmentComplete() {
        return bolPowerSet;
    }
}

