package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HoloMechDrive {


    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;


    private DigitalChannel digitalTouchGRB;

    private double dblFlMotorPower;
    private double dblFrMotorPower;
    private double dblBlMotorPower;
    private double dblBrMotorPower;

    private double dblFlMotorPower2;
    private double dblFrMotorPower2;
    private double dblBlMotorPower2;
    private double dblBrMotorPower2;

    private double dblMotorInstance;

    private int intDirection = 1;

    private static final double DBL_SQRT_OF_2 = Math.sqrt(2);
    private float fltXReverse;

    private static final double NORMAL_POWER = 0.25;

    private boolean bolXWasPressed = false;
    private boolean bolX2WasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolLBWasPressed = false;
    private boolean bolYWasPressed = false;

    private boolean bolDirectionToggle = false;
    private boolean bolDirection = false;
    private boolean bolBackwardsToggle = false;
    private boolean bolSideToggle = false;
    private boolean bolSTToggle = false;
    private boolean bolSHToggle = false;
    private boolean bolFalse = false;
    private boolean bolYTogglePressed = false;
    private boolean bolLSGrabbingToggle = true;


    private ChassisMoveParameters cmpMoveParameters;

    public HoloMechDrive(HardwareMap hmpHardwareMap, float fltXReverse) {
        leftFront = hmpHardwareMap.dcMotor.get("leftFront");
        rightFront = hmpHardwareMap.dcMotor.get("rightFront");
        leftRear = hmpHardwareMap.dcMotor.get("leftRear");
        rightRear = hmpHardwareMap.dcMotor.get("rightRear");
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.fltXReverse = fltXReverse;
    }

    public void setCmpMoveParameters(ChassisMoveParameters cmpMoveParameters) {
        this.cmpMoveParameters = cmpMoveParameters;
    }

    public void moveChassis() {

        double dblMotorPosition = leftRear.getCurrentPosition();

        float fltThrottle = cmpMoveParameters.getThrottle();
        float fltDiffThrottle = cmpMoveParameters.getDiffThrottle();
        float fltCenterPivot = cmpMoveParameters.getCenterPivot();
        float fltXDirection = cmpMoveParameters.getXDirection() * fltXReverse;
        float fltYDirection = cmpMoveParameters.getYDirection();
        boolean bolXButton = cmpMoveParameters.getXButton();
        boolean bolXButton2 = cmpMoveParameters.getXButton2();
        boolean bolRBButton = cmpMoveParameters.getRBButton();
        boolean bolLBButton = cmpMoveParameters.getLBButton();

        if (bolDirectionToggle) {
            fltXDirection = fltXDirection * -1;
            fltYDirection = fltYDirection * -1;
        } else {
            fltXDirection = fltXDirection * 1;
            fltYDirection = fltYDirection * 1;
        }

        dblFlMotorPower2 = (-(((-fltXDirection) + fltYDirection) + -(fltCenterPivot * 2)));
        dblFrMotorPower2 = ((fltYDirection - (-fltXDirection)) - -(fltCenterPivot * 2));
        dblBlMotorPower2 = (-((fltYDirection - (-fltXDirection)) + -(fltCenterPivot * 2)));
        dblBrMotorPower2 = (((-fltXDirection) + fltYDirection) - -(fltCenterPivot * 2));

        dblFlMotorPower = (Math.abs(dblFlMotorPower2))*(dblFlMotorPower2);
        dblFrMotorPower = (Math.abs(dblFrMotorPower2))*(dblFrMotorPower2);
        dblBlMotorPower = (Math.abs(dblBlMotorPower2))*(dblBlMotorPower2);
        dblBrMotorPower = (Math.abs(dblBrMotorPower2))*(dblBrMotorPower2);

        if (bolLBButton && !bolLBWasPressed) {
            bolLBWasPressed = true;
            bolSideToggle = !bolSideToggle;
            if (bolSideToggle) {

                bolDirection = true;
            } else {

                bolDirection = false;
            }
        } else if (!bolLBButton && bolLBWasPressed) {
            bolLBWasPressed = false;
        }

        if (bolXButton && !bolXWasPressed) {
            bolXWasPressed = true;
            bolDirectionToggle = !bolDirectionToggle;
        } else if (!bolXButton) {
            bolXWasPressed = false;
        }

        if (bolXButton2 && !bolX2WasPressed) {
            bolX2WasPressed = true;
            bolSTToggle = !bolSTToggle;
        } else if (!bolXButton2) {
            bolX2WasPressed = false;
        }


        // Stack Pick-up

        if (bolYWasPressed && !bolYTogglePressed) {
            bolYTogglePressed = true;
            bolLSGrabbingToggle = !bolLSGrabbingToggle;
        } else if (!bolYWasPressed && bolYTogglePressed) {
            bolYTogglePressed = false;
        }

        if (bolLSGrabbingToggle) {
            if (digitalTouchGRB.getState() == true) {
                bolSHToggle = true;
            } else {
                bolSHToggle = false;
            }
        } else {
            if (bolRBButton && !bolBWasPressed && bolSTToggle) {
                bolBWasPressed = true;
            } else if (!bolRBButton) {
                bolBWasPressed = false;
            }
        }

        if (bolRBButton && !bolBWasPressed && bolSTToggle) {
            bolBWasPressed = true;
            if (bolSHToggle) {
                bolBackwardsToggle = true;
                dblMotorInstance = dblMotorPosition;
            }
        } else if (!bolRBButton) {
            bolBWasPressed = false;
        }



        if(bolBackwardsToggle) {

            if (!bolDirection){

                dblFlMotorPower = NORMAL_POWER;
                dblFrMotorPower = -NORMAL_POWER;
                dblBlMotorPower = NORMAL_POWER;
                dblBrMotorPower = -NORMAL_POWER;

                if(dblMotorPosition > dblMotorInstance + 700){
                    bolBackwardsToggle = false;
                }

                if(dblFlMotorPower2 > 0){
                    bolBackwardsToggle = false;
                }

            } else {

                dblFlMotorPower = -NORMAL_POWER;
                dblFrMotorPower = NORMAL_POWER;
                dblBlMotorPower = -NORMAL_POWER;
                dblBrMotorPower = NORMAL_POWER;

                if(dblMotorPosition < dblMotorInstance - 700){
                    bolBackwardsToggle = false;
                }

                if(dblFlMotorPower2 > 0){
                    bolBackwardsToggle = false;
                }

            }

        }






            leftFront.setPower(dblFlMotorPower);
            rightFront.setPower(dblFrMotorPower);
            leftRear.setPower(dblBlMotorPower);
            rightRear.setPower(dblBrMotorPower);



    }
}