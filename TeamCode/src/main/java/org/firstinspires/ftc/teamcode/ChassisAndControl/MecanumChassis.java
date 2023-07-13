package org.firstinspires.ftc.teamcode.ChassisAndControl;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumChassis extends Chassis {

    private HoloMechDrive hmdHoloMechDrive;

    private ChassisMoveParameters cmpMoveParameters;


    public MecanumChassis(HardwareMap hmpHardwareMap) {
        hmdHoloMechDrive = new HoloMechDrive(hmpHardwareMap,1);
    }

    @Override
    public void setCmpMoveParameters(ChassisMoveParameters cmpMoveParameters) {
        hmdHoloMechDrive.setCmpMoveParameters(cmpMoveParameters);
        this.cmpMoveParameters = cmpMoveParameters;
    }

    @Override
    public void moveChassis() {
        hmdHoloMechDrive.moveChassis();
    }

}
