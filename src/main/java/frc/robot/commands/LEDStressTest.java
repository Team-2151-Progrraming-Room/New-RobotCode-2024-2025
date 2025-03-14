
package frc.robot.commands;

import frc.robot.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj2.command.Command;



public class LEDStressTest extends Command {

    private final LEDSubsystem m_ledSubsystem;

    private static final int m_paceFactor              = 4;     // the execute routines runs every 20ms so this is a factor to slow us down
    private static int       m_paceCount;
    private int rVal;
    private int gVal;
    private int bVal;


    public LEDStressTest(LEDSubsystem subsystem) {
        m_ledSubsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }



    @Override
    public void initialize() {

        m_paceCount = m_paceFactor;


        m_ledSubsystem.setAllLedsRGB(0, 0, 0);
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
        rVal = (int)Math.random()*256;
        gVal = (int)Math.random()*256;
        bVal = (int)Math.random()*256;

        m_ledSubsystem.setAllLedsRGB(rVal, gVal, bVal);


        // set bounce colors

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
