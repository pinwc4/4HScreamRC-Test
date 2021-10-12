package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class HolonomicChassis extends Chassis {

    private HoloMechDrive hmdHoloMechDrive;

    public HolonomicChassis(HardwareMap hmpHardwareMap) {
        hmdHoloMechDrive = new HoloMechDrive(hmpHardwareMap,-1);
    }

    @Override
    public void setCmpMoveParameters(ChassisMoveParameters cmpMoveParameters) {
        hmdHoloMechDrive.setCmpMoveParameters(cmpMoveParameters);

    }

    @Override
    public void moveChassis() {
        hmdHoloMechDrive.moveChassis();
    }
}
