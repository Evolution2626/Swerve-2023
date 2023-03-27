// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.util;

import java.util.ArrayList;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;

/** Add your docs here. */
public class EncoderThread extends Thread{

    private DigitalInput flEncoder;
    private DigitalInput frEncoder;
    private DigitalInput blEncoder;
    private DigitalInput brEncoder;

    private DigitalOutput clock;
    private DigitalOutput chipSelect;

    private Timer timer;

    double bitToInteger[] = new double[4];

    private ArrayList<Double> flPreviousValue;
    private ArrayList<Double> frPreviousValue;
    private ArrayList<Double> blPreviousValue;
    private ArrayList<Double> brPreviousValue;

    public EncoderThread(){
        timer = new Timer();
        timer.start();
        timer.stop();
        timer.reset();
    
        flPreviousValue = new ArrayList<>();
        frPreviousValue = new ArrayList<>();
        blPreviousValue = new ArrayList<>();
        brPreviousValue = new ArrayList<>();
    
        flEncoder = new DigitalInput(Constants.DIGITAL.FL_ENCODER);
        frEncoder = new DigitalInput(Constants.DIGITAL.FR_ENCODER);
        blEncoder = new DigitalInput(Constants.DIGITAL.BL_ENCODER);
        brEncoder = new DigitalInput(Constants.DIGITAL.BR_ENCODER);
    
        clock = new DigitalOutput(Constants.DIGITAL.CLOCK);
        chipSelect = new DigitalOutput(Constants.DIGITAL.CHIP_SELECT);
    
        chipSelect.set(false);
        clock.set(false);
    
        for (int i = 0; i < bitToInteger.length; i++) {
            bitToInteger[i] = 0;
        }
    }

   


   public void run(){
    while (true) {
        
    
        for (int i = 0; i < bitToInteger.length; i++) {
            bitToInteger[i] = 0;
        }
        
        
    
        chipSelect.set(false);
        timer.start();
        if (timer.hasElapsed(0.001)){
            timer.stop();
            timer.reset();

            for (int j = 0; j < 12; j++) {

                clock.set(true);

                int value[] = {0,0,0,0};
                
                value[0] = flEncoder.get() ? 1 : 0;
                value[1] = frEncoder.get() ? 1 : 0;
                value[2] = blEncoder.get() ? 1 : 0;
                value[3] = brEncoder.get() ? 1 : 0;
                
                bitToInteger[0] += Math.pow(2, 11-j)*(value[0]);
                bitToInteger[1] += Math.pow(2, 11-j)*(value[1]);
                bitToInteger[2] += Math.pow(2, 11-j)*(value[2]);
                bitToInteger[3] += Math.pow(2, 11-j)*(value[3]);

                clock.set(false);
            }

            chipSelect.set(true);
            
            if (flPreviousValue.size() >= 10) {
                flPreviousValue.remove(0);
            }
            flPreviousValue.add(bitToInteger[0]);

            if (frPreviousValue.size() >= 10) {
                frPreviousValue.remove(0);
            }
            frPreviousValue.add(bitToInteger[1]);

            if (blPreviousValue.size() >= 10) {
                blPreviousValue.remove(0);
            }
            blPreviousValue.add(bitToInteger[2]);

            if (brPreviousValue.size() >= 10) {
                brPreviousValue.remove(0);
            }
            brPreviousValue.add(bitToInteger[3]);

            double flAverageValue = 0;
            for (int i = 0; i < flPreviousValue.size(); i++) {
                flAverageValue += flPreviousValue.get(i);
            }
            flAverageValue /= flPreviousValue.size();

            double frAverageValue = 0;
            for (int i = 0; i < frPreviousValue.size(); i++) {
                frAverageValue += frPreviousValue.get(i);
            }
            frAverageValue /= frPreviousValue.size();

            double blAverageValue = 0;
            for (int i = 0; i < blPreviousValue.size(); i++) {
                blAverageValue += blPreviousValue.get(i);
            }
            blAverageValue /= blPreviousValue.size();

            double brAverageValue = 0;
            for (int i = 0; i < brPreviousValue.size(); i++) {
                brAverageValue += brPreviousValue.get(i);
            }
            brAverageValue /= brPreviousValue.size();
           
            EncoderValues.FL_ENCODER_VALUE = (flAverageValue + 4096 - 2750)%4096;
            EncoderValues.FR_ENCODER_VALUE = (frAverageValue + 4096 - 3155)%4096;
            EncoderValues.BL_ENCODER_VALUE = (blAverageValue + 4096 - 3082)%4096;
            EncoderValues.BR_ENCODER_VALUE = (brAverageValue + 4096 - 2153)%4096;

            
            }
        }
    }
}
