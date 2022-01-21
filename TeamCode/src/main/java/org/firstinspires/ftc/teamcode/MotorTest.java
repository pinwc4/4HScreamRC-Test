package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "MotorTest")


public class MotorTest extends OpMode {


    private double Kcos = 1;

    private DcMotorEx dcmArmMotor;

    private static final double TICKSTOINCHES = (537.6 * 1) / (Math.PI * 3.77953);

    private boolean bolGSToggle = false;
    private boolean bolAWasPressed = false;
    private boolean bolCarouselToggle = false;
    private boolean bolXWasPressed = false;
    private boolean bolRBumperWasPressed = false;
    private boolean bolLBumperWasPressed = false;
    private boolean bolBWasPressed = false;
    private boolean bolYWasPressed = false;

    private int intTargetPosition;

    public void init(){

        dcmArmMotor = hardwareMap.get(DcMotorEx.class, "MagnetArm");
        dcmArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcmArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcmArmMotor.setDirection(DcMotor.Direction.FORWARD);

    }

    public static enum PIDModes {
        Relaxed(new PIDFCoefficients(10, 0.1f, 0f, 0.2f));

        final PIDFCoefficients PIDValues;

        PIDModes (PIDFCoefficients PIDValues){
            this.PIDValues = PIDValues;
        }
        public PIDFCoefficients getPID(){
            return PIDValues;
        }
    }

    public void setShooterPIDMode (MotorTest.PIDModes mode){
        dcmArmMotor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, mode.getPID());
    }

    public double calculateFeedback(double positionReference, double velocityReference, double position, double velocity){
        double velocityError = velocityReference - velocity;
        double positionError = positionReference - position;
        double u = (0 * velocityError) + (0.001 * positionError);
        return u;
    }

    public double calculateFeedForward(double targetVelocity, double targetAcceleration){
        return (targetVelocity * 0) + (targetAcceleration * 0);
    }

    public void commandMotor(double targetPosition, double targetVelocity, double targetAcceleration, double motorPos, double motorVelo, double gravity) {
        double feedbackCommand = calculateFeedback(targetPosition, targetVelocity, motorPos, motorVelo);
        double feedforwardCommand = calculateFeedForward(targetVelocity, targetAcceleration);
        double gravityCommand = gravity;
        dcmArmMotor.setPower(feedbackCommand + feedforwardCommand + gravityCommand);
    }

    public void loop(){

        double reference = ((dcmArmMotor.getCurrentPosition()/5281.1)*360);

        double gravity = Math.cos(Math.toRadians(reference))*0.1;

        commandMotor(2641,1000,1, dcmArmMotor.getCurrentPosition(), dcmArmMotor.getVelocity(), gravity);


        //dcmArmMotor.setPower(gamepad1.left_stick_y/2);

        /*
        setShooterPIDMode(MotorTest.PIDModes.Relaxed);


        if(gamepad1.a){
            intTargetPosition = 2800;
        }
        if(gamepad1.b){
            intTargetPosition = 0;
        }


        dcmArmMotor.setTargetPosition(intTargetPosition);
        dcmArmMotor.setPower(1);
        dcmArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

         */

        telemetry.addData("Position", dcmArmMotor.getCurrentPosition());
        telemetry.addData("Power", dcmArmMotor.getVelocity());
        telemetry.addData("PIDF", dcmArmMotor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));
        telemetry.addData("reference", reference);
        telemetry.addData("gravity", gravity);

        telemetry.update();
    }
}

