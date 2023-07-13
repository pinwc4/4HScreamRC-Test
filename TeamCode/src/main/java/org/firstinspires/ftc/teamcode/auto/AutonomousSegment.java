package org.firstinspires.ftc.teamcode.auto;

public abstract class AutonomousSegment {

    private AutonomousSegment atsNextSegment;

    public AutonomousSegment() {
        super();
    }

    public void setNextSegment(AutonomousSegment atsNextSegment){
        this.atsNextSegment = atsNextSegment;
    }

    public AutonomousSegment getNextSegment(){
        return atsNextSegment;
    }

    public abstract void runSegment();

    public abstract boolean segmentComplete();
}