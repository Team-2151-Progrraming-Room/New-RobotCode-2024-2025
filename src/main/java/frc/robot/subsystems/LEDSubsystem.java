package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase; 

//our imports
import frc.robot.Constants.LEDConstants;

public class LEDSubsystem extends SubsystemBase{
    
    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;

    private static final int m_hueRange = LEDConstants.kLedShooterSpinupStartH - LEDConstants.kLedShooterSpinupEndH;
    private static final int m_hueStep  = m_hueRange / LEDConstants.kNumLEDs;
    private static final int m_offsetStep = LEDConstants.kNumLEDs / 20 + 1;       // march through al of them in about 20 cycles (1 sec)
    private int m_shootSpinupCurrentHue;
    private int m_shootSpinupLedCount;

    public LEDSubsystem(){

        m_led = new AddressableLED(LEDConstants.kLedPwmPort);

        m_ledBuffer = new AddressableLEDBuffer(LEDConstants.kNumLEDs);
        m_led.setLength(LEDConstants.kNumLEDs);

        // setLedsMaroon();
   
        m_led.start();
    }

    public int getNumLEDs(){
        return m_ledBuffer.getLength();
    }

    public void setLedsOff() {

        for (int i = 0 ; i < m_ledBuffer.getLength() ; i++) {
   
            m_ledBuffer.setRGB(i, 0, 0, 0);
        }
    }



    public void setLedsWhite() {

        for (int i = 0 ; i < m_ledBuffer.getLength() ; i++) {
   
            m_ledBuffer.setRGB(i, 180, 180, 180);
        }
    }



    public void setLedsMaroon() {

        for (int i = 0 ; i < m_ledBuffer.getLength() ; i++) {
   
            m_ledBuffer.setHSV(i, 2, 64, 22);       // determined with google colo picker at https://www.google.com/search?q=color+picker
        }
    }



    public void setAllLedsRGB(int red, int green, int blue) {

/**
 * sets all of the LEDs in the string to the same passed RGB colors
 */ 

        for (int i = 0 ; i < m_ledBuffer.getLength() ; i++) {
   
            m_ledBuffer.setRGB(i, red, green, blue);
        }
    }



    public void setRangeLedsRGB(int index, int count, int red, int green, int blue) {

/**
 * sets a subset of the LEDs in the string to the same passed RGB colors starting at the 0-based index and setting count number of LEDs
 */ 
 
        for (int i = 0 ; i < count ; i++) {
   
            m_ledBuffer.setRGB(index + i, red, green, blue);
        }
    }



    public void setLedRGB(int index, int red, int green, int blue) {

/**
 * sets a specific LED in the string based on the 0-based index to the same passed RGB colors
 */ 
 
        m_ledBuffer.setRGB(index, red, green, blue);
    }



    public void setAllLedsHSV(int hue, int sat, int value) {

/**
 * sets all of the LEDs in the string to the same passed HSV colors
 */ 

        for (int i = 0 ; i < m_ledBuffer.getLength() ; i++) {
   
            m_ledBuffer.setHSV(i, hue, sat, value);
        }
    }



    public void setRangeLedsHSV(int index, int count, int hue, int sat, int value) {

/**
 * sets a subset of the LEDs in the string to the same passed HSV colors starting at the 0-based index and setting count number of LEDs
 */ 
 
        for (int i = 0 ; i < count ; i++) {
   
            m_ledBuffer.setHSV(index + i, hue, sat, value);
        }
    }



    public void setLedHSV(int index, int hue, int sat, int value) {

    /**
     * sets a specific LED in the string based on the 0-based index to the same passed HSV colors
     */ 
 
        m_ledBuffer.setHSV(index, hue, sat, value);
    }



    public void ledPostShootSequence() {

    /**
     * turns off one random LED each time through as part of the post shootiung LED sequence
     */

        int offLed = (int)(Math.random() * LEDConstants.kNumLEDs);       // pick a random LED to turn off (0 to kNumOfLeds - 1)

        setLedHSV(offLed, LEDConstants.kLedGeneralBackgroundH, LEDConstants.kLedGeneralBackgroundS, LEDConstants.kLedGeneralBackgroundV);
    }



    private void initLedShootSequence() {

        m_shootSpinupCurrentHue  = LEDConstants.kLedShooterSpinupStartH;

        m_shootSpinupLedCount = 1;

        setAllLedsHSV(0, 0, 0);     // turn all the LEDs off - the pre-shoot seq will turn them back on
    }



    public void ledPreShootSequence() {

        // we use the previously set range and hue
        setRangeLedsHSV(0, m_shootSpinupLedCount, m_shootSpinupCurrentHue, 255, 255);

        m_shootSpinupLedCount   += m_offsetStep;
        m_shootSpinupCurrentHue -= m_hueStep;               // hue goes backwards from orange-ish to yellow red-ish

        // don't go past the end of the list
        if (m_shootSpinupLedCount > LEDConstants.kNumLEDs) {
            m_shootSpinupLedCount = LEDConstants.kNumLEDs;
        }

        // stop at our ending hue
        if (m_shootSpinupCurrentHue < LEDConstants.kLedShooterSpinupEndH) {
            m_shootSpinupCurrentHue = LEDConstants.kLedShooterSpinupEndH;
        }
    }



/* Commands *************************************************************************
 ************************************************************************************/



  public Command LedIntakeRunningCommand() {
    /**
     * Set the pattern indicating the intake has loaded a note
     */

    return runOnce(
        () -> {
          setAllLedsHSV(LEDConstants.kLedIntakePrimaryH, LEDConstants.kLedIntakePrimaryS, LEDConstants.kLedIntakePrimaryV);
        });
  }



  public Command LedShootCommand() {
    /**
     * Set the LEDs to the shoot color
     */

    return runOnce(
        () -> {
          setAllLedsHSV(LEDConstants.kLedShooterShotH, LEDConstants.kLedShooterShotS, LEDConstants.kLedShooterShotV);
        });
  }



  public Command LedPreShootInitCommand() {
    /**
     * setup for the shooting spinup
     */

     return runOnce(
             () -> {
          initLedShootSequence();
        });
  }



  public Command LedPreShootCommand() {
    /**
     * runs the pre-shoot sequnce until cancelled
     */

     return run(
             () -> {
          ledPreShootSequence();
        });
  }



  public Command LedPostShootCommand() {
    /**
     * cleans up from the shooting sequence until cancelled
     */

     return run(
             () -> {
          ledPostShootSequence();
        });
  }

  

/* Periodics ************************************************************************************
 ************************************************************************************************/

  @Override
  public void periodic() {

    m_led.setData(m_ledBuffer);
 }



  @Override
  public void simulationPeriodic() {

  }

}
