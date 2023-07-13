package org.firstinspires.ftc.teamcode.ChassisAndControl;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Gobilda120mmChassis extends Chassis {

    private DcMotor dcmFLMotor;
    private DcMotor dcmFRMotor;
    private DcMotor dcmBLMotor;
    private DcMotor dcmBRMotor;

    private ChassisMoveParameters cmpMoveParameters;

    public Gobilda120mmChassis(HardwareMap hmpHardwareMap) {
        dcmFLMotor = hmpHardwareMap.dcMotor.get("FLMotor");
        dcmFRMotor = hmpHardwareMap.dcMotor.get("FRMotor");
        dcmBLMotor = hmpHardwareMap.dcMotor.get("BLMotor");
        dcmBRMotor = hmpHardwareMap.dcMotor.get("BRMotor");

        dcmFLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmFRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcmBRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setCmpMoveParameters(ChassisMoveParameters cmpMoveParameters) {
        this.cmpMoveParameters = cmpMoveParameters;

    }

    @Override
    public void moveChassis() {

        float fltThrottle = cmpMoveParameters.getThrottle();
        float fltDiffThrottle = cmpMoveParameters.getDiffThrottle();
        float fltCenterPivot = cmpMoveParameters.getCenterPivot();

        float fltFLMotorPower = 0;
        float fltFRMotorPower = 0;
        float fltBLMotorPower = 0;
        float fltBRMotorPower = 0;

        if (fltDiffThrottle > 0) {
            fltFRMotorPower = fltThrottle * (1 - fltDiffThrottle);
            fltBRMotorPower = fltThrottle * (1 - fltDiffThrottle);
            fltFLMotorPower = fltThrottle;
            fltBLMotorPower = fltThrottle;
        } else {
            fltFLMotorPower = fltThrottle * (1 + fltDiffThrottle);
            fltBLMotorPower = fltThrottle * (1 + fltDiffThrottle);
            fltFRMotorPower = fltThrottle;
            fltBRMotorPower = fltThrottle;
        }
        if (fltCenterPivot == 0){
            dcmFLMotor.setPower(-fltFLMotorPower);
            dcmFRMotor.setPower((fltFRMotorPower));
            dcmBLMotor.setPower(-fltBLMotorPower);
            dcmBRMotor.setPower((fltBRMotorPower));
        } else {
            dcmFLMotor.setPower(fltCenterPivot);
            dcmFRMotor.setPower(fltCenterPivot);
            dcmBLMotor.setPower(fltCenterPivot);
            dcmBRMotor.setPower(fltCenterPivot);
        }
    }
}
