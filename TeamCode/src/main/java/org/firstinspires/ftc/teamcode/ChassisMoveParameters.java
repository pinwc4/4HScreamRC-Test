package org.firstinspires.ftc.teamcode;

public class ChassisMoveParameters extends Object {

    private float fltThrottle = 0;
    private float fltDiffThrottle = 0;
    private float fltCenterPivot = 0;
    private float fltXDirection = 0;
    private float fltYDirection = 0;
    private boolean bolXButton = false;
    private boolean bolXButton2 = false;
    private boolean bolRBButton = false;
    private boolean bolLBButton = false;


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

    public boolean getXButton2() { return bolXButton2; }

    public boolean getRBButton() { return bolRBButton; }

    public boolean getLBButton() { return bolLBButton; }



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

    public void setXButton2(boolean bolXButton2) {this.bolXButton2 = bolXButton2; }

    public void setRBButton(boolean bolRBButton) {this.bolRBButton = bolRBButton; }

    public void setLBButton(boolean bolLBButton) {this.bolLBButton = bolLBButton; }
}
