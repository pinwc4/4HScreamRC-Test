package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

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
