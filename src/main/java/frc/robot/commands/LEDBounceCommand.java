
package frc.robot.commands;

import frc.robot.Constants.LEDConstants;
import frc.robot.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj2.command.Command;



public class LEDBounceCommand extends Command {

    private final LEDSubsystem m_ledSubsystem;

    private static final int m_paceFactor              = 4;     // the execute routines runs every 20ms so this is a factor to slow us down
    private static int       m_paceCount;
    private static int       m_bounceIndex;
    private static int       m_bounceDirection;


    public LEDBounceCommand(LEDSubsystem subsystem) {
        m_ledSubsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }



    @Override
    public void initialize() {

        m_paceCount       = m_paceFactor;
        m_bounceIndex     = 0;
        m_bounceDirection = 1;

        m_ledSubsystem.setAllLedsHSV(LEDConstants.kLedGeneralBackgroundH,
                                     LEDConstants.kLedGeneralBackgroundS,
                                     LEDConstants.kLedGeneralBackgroundV);

        m_ledSubsystem.setLedHSV(0, LEDConstants.kLedBounceShadowH,
                                          LEDConstants.kLedBounceShadowS,
                                          LEDConstants.kLedBounceShadowV);

        m_ledSubsystem.setLedHSV(1, LEDConstants.kLedBouncePrimaryH,
                                          LEDConstants.kLedBouncePrimaryS,
                                          LEDConstants.kLedBouncePrimaryV);

        m_ledSubsystem.setLedHSV(2, LEDConstants.kLedBounceShadowH,
                                          LEDConstants.kLedBounceShadowS,
                                          LEDConstants.kLedBounceShadowV);
    }



    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        // slow us down

        m_paceCount--;

        if (m_paceCount > 0 ) {
            return;                     // don't do anyting - yet...
        }

        m_paceCount = m_paceFactor;     // reset counter and do our thing

        // we just bounce a color back and forth from one end to the other

        // reset from what we're showing now

        m_ledSubsystem.setRangeLedsHSV(m_bounceIndex, 3,
                                       LEDConstants.kLedGeneralBackgroundH,
                                       LEDConstants.kLedGeneralBackgroundS,
                                       LEDConstants.kLedGeneralBackgroundV);

        m_bounceIndex += m_bounceDirection;     // handles either direction because direction changes sign

        if (m_bounceIndex < 0) {
            m_bounceIndex     = 1;
            m_bounceDirection = 1;
        }

        if (m_bounceIndex > (m_ledSubsystem.getNumLEDs() - 3)) {
            m_bounceIndex     = m_ledSubsystem.getNumLEDs() - 3 - 1;
            m_bounceDirection = -1;
        }

        // set bounce colors

        m_ledSubsystem.setLedHSV(m_bounceIndex,
                                 LEDConstants.kLedBounceShadowH,
                                 LEDConstants.kLedBounceShadowS,
                                 LEDConstants.kLedBounceShadowV);

        m_ledSubsystem.setLedHSV(m_bounceIndex + 1,
                                 LEDConstants.kLedBouncePrimaryH,
                                 LEDConstants.kLedBouncePrimaryS,
                                 LEDConstants.kLedBouncePrimaryV);

        m_ledSubsystem.setLedHSV(m_bounceIndex + 2,
                                 LEDConstants.kLedBounceShadowH,
                                 LEDConstants.kLedBounceShadowS,
                                 LEDConstants.kLedBounceShadowV);
    }



    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}



    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
