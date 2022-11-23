package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "DriverRobotControl_DRIVER_CONTROL")

public class DriverRobotControl extends OpMode {

    private ChassisControlScheme ccsChassisControlScheme;
    private Chassis chsChassis;
    private DR4BAttachment atcDR4BAttachment;
    private LauncherAttechment latLauncherAttachment;
    private LEDpattarn ledLedPattern;
    private FreightAttachment frtFreightAttechment;
    private PowerAttachment pwrPowerAttachment;
    private PowerLights pwrPowerLights;

    private TouchSensor snsChassis;
    private TouchSensor snsControlScheme;
    private TouchSensor snsAttachment;

    private boolean bolTest=false;


    @Override
    public void init() {

        //snsChassis = hardwareMap.touchSensor.get("TestTouch");
/*
        ccsChassisControlScheme = new DroneControlScheme(gamepad1);
        chsChassis = new MecanumChassis(hardwareMap);
        chsChassis.setCmpMoveParameters(((DroneControlScheme) ccsChassisControlScheme).getCmpMoveParameters());
        //atcDR4BAttachment = new DR4BAttachment(gamepad1, gamepad2, hardwareMap, telemetry);
        latLauncherAttachment = new LauncherAttechment(gamepad1, gamepad2, hardwareMap, telemetry);
        frtFreightAttechment = new FreightAttachment(gamepad1, gamepad2, hardwareMap, telemetry);



 */


            ccsChassisControlScheme = new DroneControlScheme(gamepad1);
            chsChassis = new MecanumChassis(hardwareMap);
            chsChassis.setCmpMoveParameters(((DroneControlScheme) ccsChassisControlScheme).getCmpMoveParameters());




            pwrPowerAttachment = new PowerAttachment(gamepad1, gamepad2, hardwareMap, telemetry);
           //ledLedPattern = new LEDpattarn(gamepad1, hardwareMap, telemetry);
    }

    @Override
    public void loop() {


        //frtFreightAttechment.moveAttachments();

        pwrPowerAttachment.moveAttachments();
        ccsChassisControlScheme.updateControls();
        chsChassis.moveChassis();
        //ledLedPattern.lights(getRuntime());
        /*
        if(!bolTest) {
            latLauncherAttachment.moveAttachments();
        }
        if(!bolTest) {

        }
        if(bolTest) {
            frtFreightAttechment.moveAttachments();
        }


         */




    }
}


