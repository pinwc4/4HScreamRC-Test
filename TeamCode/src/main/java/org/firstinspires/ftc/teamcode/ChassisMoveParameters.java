package org.firstinspires.ftc.teamcode;

public class ChassisMoveParameters extends Object {

    private float fltThrottle = 0;
    private float fltDiffThrottle = 0;
    private float fltCenterPivot = 0;
    private float fltXDirection = 0;
    private float fltYDirection = 0;
    private boolean bolXButton = false;


    public float getThrottle() {
        return fltThrottle;
    }

    public float getDiffThrottle() {
        return fltDiffThrottle;
    }

    public float getCenterPivot() {
        return fltCenterPivot;
    }

    public float getYDirection() { return fltYDirection; }

    public float getXDirection() { return fltXDirection; }

    public boolean getXButton() { return bolXButton; }



    public void setThrottle(float fltThrottle) {
        this.fltThrottle = fltThrottle;
    }

    public void setDiffThrottle(float fltDiffThrottle) {
        this.fltDiffThrottle = fltDiffThrottle;
    }

    public void setCenterPivot(float fltCenterPivot) {
        this.fltCenterPivot = fltCenterPivot;
    }

    public void setYDirection(float fltYDirection) { this.fltYDirection = fltYDirection; }

    public void setXDirection(float fltXDirection) { this.fltXDirection = fltXDirection; }

    public void setXButton(boolean bolXButton) {this.bolXButton = bolXButton; }

}
