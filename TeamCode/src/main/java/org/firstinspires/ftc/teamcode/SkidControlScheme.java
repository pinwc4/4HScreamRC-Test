package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class SkidControlScheme extends ChassisControlScheme {
    
    private Gamepad gmpGamepad1;

    private ChassisMoveParameters cmpMoveParameters;

    private boolean bolXWasPressed = false;
    private boolean bolReverseSwitch = false;
    

    public SkidControlScheme(Gamepad gmpGamepad){
        gmpGamepad1 = gmpGamepad;
        
        cmpMoveParameters = new ChassisMoveParameters();
        
    }

    public ChassisMoveParameters getCmpMoveParameters() {
        return cmpMoveParameters;
    }

    @Override
    public void updateControls() {
        
        if (gmpGamepad1.x && !bolXWasPressed) {
            bolXWasPressed = true;
            bolReverseSwitch = !bolReverseSwitch;
        } else if (!gmpGamepad1.x) {
            bolXWasPressed = false;
        }

        if (bolReverseSwitch) {
            if (gmpGamepad1.right_trigger > 0){
                cmpMoveParameters.setThrottle(gmpGamepad1.right_trigger);
            } else if (gmpGamepad1.left_trigger >= 0) {
                cmpMoveParameters.setThrottle(-gmpGamepad1.left_trigger);
            }
        } else {
            if (gmpGamepad1.right_trigger > 0) {
                cmpMoveParameters.setThrottle(-gmpGamepad1.right_trigger);
            } else if (gmpGamepad1.left_trigger >= 0) {
                cmpMoveParameters.setThrottle(gmpGamepad1.left_trigger);
            }
        }

        cmpMoveParameters.setDiffThrottle(gmpGamepad1.left_stick_x);
        cmpMoveParameters.setCenterPivot(gmpGamepad1.right_stick_x);

    }
}

