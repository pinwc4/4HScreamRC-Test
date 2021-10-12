package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

@Autonomous(name = "StraferTestPID")


public class StraferTestPID extends OpMode {

    private DcMotor dcmFrontLeftMotor;
    private DcMotor dcmFrontRightMotor;
    private DcMotor dcmBackLeftMotor;
    private DcMotor dcmBackRightMotor;

    private double dblFrontLeftMotorPower;
    private double dblFrontRightMotorPower;
    private double dblBackLeftMotorPower;
    private double dblBackRightMotorPower;

    private double previousHeading = 0; //Outside of method
    private double integratedHeading = 0;



    private static final double NORMAL_POWER = 0.75;
    private double dblDesiredHeading = 0;
    private static final double CORRECTION_AGGRESSION = 0.25;

    Orientation angles;

    BNO055IMU imu;



    public void init(){

        initImu();

        dcmFrontLeftMotor = hardwareMap.dcMotor.get("MotorFL");
        dcmFrontRightMotor = hardwareMap.dcMotor.get("MotorFR");
        dcmBackLeftMotor = hardwareMap.dcMotor.get("MotorBL");
        dcmBackRightMotor = hardwareMap.dcMotor.get("MotorBR");
    }

    public void loop(){
/*
        private double getIntegratedHeading() {
            double currentHeading = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
            double deltaHeading = currentHeading - previousHeading;

            if (deltaHeading < -180) {
                deltaHeading += 360;
            } else if (deltaHeading >= 180) {
                deltaHeading -= 360;
            }

            integratedHeading += deltaHeading;
            previousHeading = currentHeading;

            return integratedHeading;
        }
*/
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double dblHeading = angles.firstAngle;
/*


        double dblHeadingCorrection = dblHeading*CORRECTION_AGGRESSION;


        dblFrontLeftMotorPower = NORMAL_POWER;
        dblFrontRightMotorPower = NORMAL_POWER;
        dblBackLeftMotorPower = NORMAL_POWER;
        dblBackRightMotorPower = NORMAL_POWER;


        if(dblHeading < dblDesiredHeading){
            dblFrontLeftMotorPower = NORMAL_POWER + (-dblHeadingCorrection);
            dblBackLeftMotorPower = NORMAL_POWER + (-dblHeadingCorrection);
        }else if(dblHeading > dblDesiredHeading){
            dblFrontRightMotorPower = (NORMAL_POWER + dblHeadingCorrection);
            dblBackRightMotorPower = (NORMAL_POWER + dblHeadingCorrection);
        }

        dcmFrontLeftMotor.setPower(dblFrontLeftMotorPower);
        dcmFrontRightMotor.setPower(-dblFrontRightMotorPower);
        dcmBackLeftMotor.setPower(dblBackLeftMotorPower);
        dcmBackRightMotor.setPower(-dblBackRightMotorPower);
*/
        telemetry.addData("Heading", angles.firstAngle);
        telemetry.addData("Heading", dblHeading);
        //telemetry.addData("Correction", dblHeadingCorrection);
        telemetry.addData("FRMotor", dblFrontRightMotorPower);
        telemetry.addData("BRMotor", dblBackRightMotorPower);
        telemetry.addData("FLMotor", dblFrontLeftMotorPower);
        telemetry.addData("BLMotor", dblBackLeftMotorPower);

        telemetry.update();
    }
    public void initImu(){
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Start the logging of measured acceleration
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
    }
}

