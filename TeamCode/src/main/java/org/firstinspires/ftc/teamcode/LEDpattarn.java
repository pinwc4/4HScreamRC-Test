/*
 * Copyright (c) 2018 Craig MacFarlane
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * (subject to the limitations in the disclaimer below) provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of Craig MacFarlane nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;


//@Disabled
public class LEDpattarn extends Object {

    private Gamepad gmpGamepad1;
    private Gamepad gmpGamepad2;
    private Telemetry telTelemetry;

    private int intTimespan = 4;
    private int intGetRunTime;

    private boolean bolX2WasPressed;
    private boolean bolSDToggle;
    private boolean bolGM1LBWasPressed = false;
    private boolean bolGM1LBToggle = false;
    private double dblGetRunTime;
    private boolean on = false;

    /*
     * Change the pattern every 10 seconds in AUTO mode.
     */
    private final static int LED_PERIOD = 10;

    /*
     * Rate limit gamepad button presses to every 500ms.
     */
    private final static int GAMEPAD_LOCKOUT = 500;

    RevBlinkinLedDriver blinkinLedDriver;
    RevBlinkinLedDriver.BlinkinPattern pattern;

    Telemetry.Item patternName;
    Telemetry.Item display;
    DisplayKind displayKind;
    Deadline ledCycleDeadline;
    Deadline gamepadRateLimit;

    protected enum DisplayKind {
        MANUAL,
        AUTO
    }

    public LEDpattarn (Gamepad gmpGamepad1, Gamepad gmpGamepad2, HardwareMap hmpHardwareMap, Telemetry telTelemetry)
    {
        this.gmpGamepad1 = gmpGamepad1;
        this.gmpGamepad2 = gmpGamepad2;
        this.telTelemetry = telTelemetry;

        displayKind = DisplayKind.AUTO;

        blinkinLedDriver = hmpHardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        pattern = RevBlinkinLedDriver.BlinkinPattern.RAINBOW_RAINBOW_PALETTE;
        blinkinLedDriver.setPattern(pattern);

        display = telTelemetry.addData("Display Kind: ", displayKind.toString());
        patternName = telTelemetry.addData("Pattern: ", pattern.toString());

        ledCycleDeadline = new Deadline(LED_PERIOD, TimeUnit.SECONDS);
        gamepadRateLimit = new Deadline(GAMEPAD_LOCKOUT, TimeUnit.MILLISECONDS);
/*
        srvLightRow1 = hmpHardwareMap.servo.get("RedLights");
        srvLightRow1.setPosition(0);
        srvlightRow2 = hmpHardwareMap.servo.get("WhiteLights");
        srvlightRow2.setPosition(2200);

*/
    }

    public void lights(double dblGetRunTime) {
        handleGamepad();

        this.dblGetRunTime = dblGetRunTime;


        if (gmpGamepad2.x && !bolX2WasPressed) {
            bolX2WasPressed = true;
            bolSDToggle = !bolSDToggle;

        } else if (!gmpGamepad2.x) {
            bolX2WasPressed = false;
        }



        telTelemetry.addData("toggle", bolSDToggle);
        //telTelemetry.addData("on", on);
        //telTelemetry.addData("Funtion", dblFunction);
        telTelemetry.update();

        if (bolSDToggle == true) {
            if (dblGetRunTime < 60) {
                pattern = RevBlinkinLedDriver.BlinkinPattern.BLUE;
                displayPattern();
            }

            else if(dblGetRunTime >= 60 && dblGetRunTime < 90){
                pattern = RevBlinkinLedDriver.BlinkinPattern.VIOLET;
                displayPattern();
            }

            else if(dblGetRunTime >= 75 && dblGetRunTime < 90){
                if(on == true){
                    pattern = RevBlinkinLedDriver.BlinkinPattern.VIOLET;
                    displayPattern();
                }
                if(on == false){
                    pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;
                    displayPattern();
                }
            }



            else if(dblGetRunTime >= 90){
                pattern = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
                displayPattern();
            }

            else {
                if(on == true){
                    pattern = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
                    displayPattern();
                }
                if(on == false){
                    pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;
                    displayPattern();
                }
            }

        }
/*
        if (gmpGamepad1.left_bumper && !bolGM1LBWasPressed) {
            bolGM1LBWasPressed = true;
            bolGM1LBToggle = !bolGM1LBToggle;

        } else if (!gmpGamepad1.left_bumper) {
            bolGM1LBWasPressed = false;
        }
*/
        boolean on = true;

        //double dblFunction = Math.sin(dblGetRunTime*(intTimespan*(180)));
/*
        intGetRunTime = ((int) dblGetRunTime);

        if (intGetRunTime % 2 == 0){
            on = true;
        }
        else {
            on = false;
        }

        if(intGetRunTime < 105){
            if(bolGM1LBToggle == true){
                srvlightRow2.setPosition(2200);
                srvLightRow1.setPosition(0);
            }
            else{
                srvLightRow1.setPosition(2200);
                srvlightRow2.setPosition(0);
            }
        }
        else{
            if(on == true) {
                if (bolGM1LBToggle == true) {
                    srvlightRow2.setPosition(2200);
                    srvLightRow1.setPosition(0);
                } else {
                    srvLightRow1.setPosition(2200);
                    srvlightRow2.setPosition(0);
                }
            }
            else if(on == false){
                if (bolGM1LBToggle == true) {
                    srvlightRow2.setPosition(0);
                    srvLightRow1.setPosition(0);
                } else {
                    srvLightRow1.setPosition(0);
                    srvlightRow2.setPosition(0);
                }
            }
        }
*/




        if (bolSDToggle == false) {
            if (dblGetRunTime < 60) {
                pattern = RevBlinkinLedDriver.BlinkinPattern.GREEN;
                displayPattern();
            }

            else if(dblGetRunTime >= 60 && dblGetRunTime < 90){
                pattern = RevBlinkinLedDriver.BlinkinPattern.ORANGE;
                displayPattern();
            }

            else if(dblGetRunTime >= 75 && dblGetRunTime < 90){
                if(on == true){
                    pattern = RevBlinkinLedDriver.BlinkinPattern.ORANGE;
                    displayPattern();
                }
                if(on == false){
                    pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;
                    displayPattern();
                }
            }



            else if(dblGetRunTime >= 90){
                pattern = RevBlinkinLedDriver.BlinkinPattern.RED;
                displayPattern();
            }

            else {
                if(on == true){
                    pattern = RevBlinkinLedDriver.BlinkinPattern.RED;
                    displayPattern();
                }
                if(on == false){
                    pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;
                    displayPattern();
                }
            }


        }

        telTelemetry.addData("runtime", dblGetRunTime);

    }
    /*
     * handleGamepad
     *
     * Responds to a gamepad button press.  Demonstrates rate limiting for
     * button presses.  If loop() is called every 10ms and and you don't rate
     * limit, then any given button press may register as multiple button presses,
     * which in this application is problematic.
     *
     * A: Manual mode, Right bumper displays the next pattern, left bumper displays the previous pattern.
     * B: Auto mode, pattern cycles, changing every LED_PERIOD seconds.
     */
    public int flashLights(int a){
        return a;
    }
    protected void handleGamepad()
    {

    }

    protected void setDisplayKind(DisplayKind displayKind)
    {
        this.displayKind = displayKind;
        display.setValue(displayKind.toString());
    }

    protected void doAutoDisplay()
    {
        if (ledCycleDeadline.hasExpired()) {
            pattern = pattern.next();
            displayPattern();

            ledCycleDeadline.reset();
        }
    }

    protected void displayPattern()
    {
        blinkinLedDriver.setPattern(pattern);
        patternName.setValue(pattern.toString());
    }
}
