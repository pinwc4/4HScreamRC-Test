package org.firstinspires.ftc.teamcode.ChassisAndControl;

import com.qualcomm.robotcore.hardware.Gamepad;

public class DroneControlScheme extends ChassisControlScheme {

    private Gamepad gmpGamepad1;
    private Gamepad gmpGamepad2;
    private boolean bolStickWasPressed = false;
    private boolean bolSDToggle = false;


    private ChassisMoveParameters cmpMoveParameters;

    public DroneControlScheme(Gamepad gmpGamepad1, Gamepad gmpGamepad2) {
        this.gmpGamepad1 = gmpGamepad1;
        this.gmpGamepad2 = gmpGamepad2;
        cmpMoveParameters = new ChassisMoveParameters();

    }

    public ChassisMoveParameters getCmpMoveParameters() {
        return cmpMoveParameters;
    }


    @Override
    public void updateControls() {

        if (gmpGamepad1.left_stick_button && !bolStickWasPressed) {
            bolStickWasPressed = true;
            bolSDToggle = !bolSDToggle;
        } else if (!gmpGamepad1.left_stick_button) {
            bolStickWasPressed = false;
        }
        if (bolSDToggle) {
            cmpMoveParameters.setXDirection(gmpGamepad1.right_stick_x / 2);
            cmpMoveParameters.setYDirection(gmpGamepad1.right_stick_y / 2);
            cmpMoveParameters.setCenterPivot(-gmpGamepad1.left_stick_x / 3);
        } else {
            cmpMoveParameters.setXDirection(gmpGamepad1.right_stick_x);
            cmpMoveParameters.setYDirection(gmpGamepad1.right_stick_y);
            cmpMoveParameters.setCenterPivot(-gmpGamepad1.left_stick_x / 2);
            cmpMoveParameters.setXButton(gmpGamepad1.x);
            cmpMoveParameters.setXButton2(gmpGamepad2.x);
            cmpMoveParameters.setRBButton(gmpGamepad1.right_bumper);
            cmpMoveParameters.setLBButton(gmpGamepad2.left_bumper);
        }
    }
}

