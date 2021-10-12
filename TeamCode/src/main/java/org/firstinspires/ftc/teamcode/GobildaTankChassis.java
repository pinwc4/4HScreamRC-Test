package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class GobildaTankChassis extends Chassis {

    private DcMotor dcmLeftMotor;
    private DcMotor dcmRightMotor;

    private ChassisMoveParameters cmpMoveParameters;

    public GobildaTankChassis(HardwareMap hmpHardwareMap) {
        dcmLeftMotor = hmpHardwareMap.dcMotor.get("MotorLD");
        dcmRightMotor = hmpHardwareMap.dcMotor.get("MotorRD");
        dcmLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setCmpMoveParameters(ChassisMoveParameters cmpMoveParameters) {
        this.cmpMoveParameters = cmpMoveParameters;

    }

    @Override
    public void moveChassis() {

        float fltThrottle = cmpMoveParameters.getThrottle();
        float fltDiffThrottle = cmpMoveParameters.getDiffThrottle();
        float fltCenterPivot = cmpMoveParameters.getCenterPivot();

        float fltLeftMotorPower = 0;
        float fltRightMotorPower = 0;

        if (fltDiffThrottle > 0) {
            fltRightMotorPower = fltThrottle * (1 - fltDiffThrottle);
            fltLeftMotorPower = fltThrottle;
        } else {
            fltLeftMotorPower = fltThrottle * (1 + fltDiffThrottle);
            fltRightMotorPower = fltThrottle;
        }
        if (fltCenterPivot == 0){
            dcmLeftMotor.setPower(-fltLeftMotorPower);
            dcmRightMotor.setPower((fltRightMotorPower));
        } else {
            dcmLeftMotor.setPower(fltCenterPivot);
            dcmRightMotor.setPower(fltCenterPivot);
        }
    }
}
