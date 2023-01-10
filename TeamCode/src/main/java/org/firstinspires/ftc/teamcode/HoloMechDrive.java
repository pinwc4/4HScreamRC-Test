package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HoloMechDrive {


    private DcMotor dcmFrontLeftMotor;
    private DcMotor dcmFrontRightMotor;
    private DcMotor dcmBackLeftMotor;
    private DcMotor dcmBackRightMotor;

    private static final double DBL_SQRT_OF_2 = Math.sqrt(2);
    private float fltXReverse;

    private boolean bolXWasPressed = false;
    private boolean bolDirectionToggle = false;


    private ChassisMoveParameters cmpMoveParameters;

    public HoloMechDrive(HardwareMap hmpHardwareMap, float fltXReverse) {
        dcmFrontLeftMotor = hmpHardwareMap.dcMotor.get("leftFront");
        dcmFrontRightMotor = hmpHardwareMap.dcMotor.get("rightFront");
        dcmBackLeftMotor = hmpHardwareMap.dcMotor.get("leftRear");
        dcmBackRightMotor = hmpHardwareMap.dcMotor.get("rightRear");
        dcmFrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmFrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.fltXReverse = fltXReverse;
    }

    public void setCmpMoveParameters(ChassisMoveParameters cmpMoveParameters) {
        this.cmpMoveParameters = cmpMoveParameters;
    }

    public void moveChassis() {

        float fltThrottle = cmpMoveParameters.getThrottle();
        float fltDiffThrottle = cmpMoveParameters.getDiffThrottle();
        float fltCenterPivot = cmpMoveParameters.getCenterPivot();
        float fltXDirection = cmpMoveParameters.getXDirection() * fltXReverse;
        float fltYDirection = cmpMoveParameters.getYDirection();
        boolean bolXButton = cmpMoveParameters.getXButton();

        if (bolDirectionToggle) {
            fltXDirection = fltXDirection * -1;
            fltYDirection = fltYDirection * -1;
        } else {
            fltXDirection = fltXDirection * 1;
            fltYDirection = fltYDirection * 1;
        }

        double dblFlMotorPower2 = (-(((-fltXDirection) + fltYDirection) + -(fltCenterPivot * 2)));
        double dblFrMotorPower2 = ((fltYDirection - (-fltXDirection)) - -(fltCenterPivot * 2));
        double dblBlMotorPower2 = (-((fltYDirection - (-fltXDirection)) + -(fltCenterPivot * 2)));
        double dblBrMotorPower2 = (((-fltXDirection) + fltYDirection) - -(fltCenterPivot * 2));

        double dblFlMotorPower = (Math.abs(dblFlMotorPower2))*(dblFlMotorPower2);
        double dblFrMotorPower = (Math.abs(dblFrMotorPower2))*(dblFrMotorPower2);
        double dblBlMotorPower = (Math.abs(dblBlMotorPower2))*(dblBlMotorPower2);
        double dblBrMotorPower = (Math.abs(dblBrMotorPower2))*(dblBrMotorPower2);

        if (bolXButton && !bolXWasPressed) {
            bolXWasPressed = true;
            bolDirectionToggle = !bolDirectionToggle;
        } else if (!bolXButton) {
            bolXWasPressed = false;
        }

            dcmFrontLeftMotor.setPower(dblFlMotorPower);
            dcmFrontRightMotor.setPower(dblFrMotorPower);
            dcmBackLeftMotor.setPower(dblBlMotorPower);
            dcmBackRightMotor.setPower(dblBrMotorPower);

    }
}