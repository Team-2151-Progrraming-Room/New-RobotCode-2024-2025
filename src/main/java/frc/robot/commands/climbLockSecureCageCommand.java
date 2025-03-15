package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimbLockSubsystem;

public class climbLockSecureCageCommand extends Command{
    
    ClimbLockSubsystem lockSubsystem;

    public climbLockSecureCageCommand(ClimbLockSubsystem lockSubsytem){
        this.lockSubsystem = lockSubsytem;
        addRequirements(lockSubsystem);
    }

    //Gets the climb lock to start moving
    @Override
    public void initialize(){
        System.out.println("Locking cage...");

        lockSubsystem.climbLockStartUp();
    }

    //Lowers the current limits of the climb lock once it is engaged.
    @Override
    public void execute(){
        lockSubsystem.climbLocking();
    }

    //Causes the command stop once the climb lock is engaged, the motor will stay stalled.
    @Override
    public boolean isFinished(){
        return lockSubsystem.climbLockEngageCheck();
    }
}