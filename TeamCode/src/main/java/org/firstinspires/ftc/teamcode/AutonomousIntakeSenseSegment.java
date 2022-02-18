package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;


public class AutonomousIntakeSenseSegment extends AutonomousSegment {
    private double desiredEncoderTicks;

    private ColorSensor snsColor;

    private double dblRGBValue;
    private boolean bolPowerSet = false;


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
            bolPowerSet = true;
        }


    }

    public boolean segmentComplete() {
        return bolPowerSet;
    }
}

