/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

/**
 * This 2020-2021 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Freight Frenzy game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@TeleOp(name = "Object Detect V2", group = "Concept")

public class ConceptTensorFlowObjectDetectionWebcamV2 extends LinearOpMode {

    //private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String TFOD_MODEL_ASSET = "V2model_20211204_165120.tflite";
    private static final String[] LABELS = {
      "Green TSE"
    };

    public float strNumber = 0;

    private static final String VUFORIA_KEY =
            "AftxbQr/////AAABmQvhWKx8Ok8fiwDdGWphaPsqGLOjlBaX5uyms1XLxF4mODVA3S3WH8/rgWV4hXAMV0cFMid2OOnFgiki5bwjmVNnyd9aPQnt8F6ajQN2epL1407zvC01z+TK5Dm3fLomAJMdnNbq31l+QlnDED9GMsftphNqNchUnI/QtDPU0/qzPohbEfui6sXzOoc6H+iJcg2gyrDwcen544nIDVH7tDEx72xjaSdYf96f7i6vAm6UyXlegp0HjvBFd3aqyhVC6PcG+HS+PHW2iFwsTL5PZ5Gme9oK6rjzfHxAoyHgxZoMdHuCbD59mZAJcwyGTELLXxOWXCuDGhyZP/nxnQEK+Hckg3D8QeY921oQLaKmru3M";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {

        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(2.5, 14.0/11.0);
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {

                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        float strNewNumber = 0;
                        String strNewName = "no duck";
                      telemetry.addData("# Object Detected", updatedRecognitions.size());
                      // step through the list of recognitions and display boundary info.
                      int i = 0;
                      for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        i++;
                          strNewNumber = recognition.getLeft();
                          strNewName = recognition.getLabel();
                      }
                        if (strNewName.equals("Green TSE") && strNewNumber > 500) {
                            strNumber = 1;
                            telemetry.addLine("1");
                        } else if (strNewName.equals("Green TSE") && strNewNumber < 500) {
                            strNumber = 2;
                            telemetry.addLine("2");
                        } else {
                            strNumber = 3;
                            telemetry.addLine("3");
                        }
                        telemetry.addData("recognition v", strNewNumber);
                        telemetry.addData("recognition v", strNumber);
                      telemetry.update();
                    }
                }
            }
        }
    }

    private void initVuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
       tfodParameters.minResultConfidence = 0.7f;
       tfodParameters.isModelTensorFlow2 = true;
       tfodParameters.inputSize = 320;
       tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
       tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
}
