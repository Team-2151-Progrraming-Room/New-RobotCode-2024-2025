package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimbLockSubsystem;

public class climbLockSecureCageCommand extends Command{

    ClimbLockSubsystem lockSubsystem;

    public climbLockSecureCageCommand(ClimbLockSubsystem lockSubsytem){
        this.lockSubsystem = lockSubsytem;
        addRequirements(lockSubsystem);
    }

    @Override
    public void initialize(){
        System.out.println("Locking cage...");

        lockSubsystem.climbLockStartUp();
    }

    @Override
    public void execute(){
        lockSubsystem.climbLocking();
    }


    @Override
    public boolean isFinished(){
        return lockSubsystem.climbLockEngageCheck();
    }
}
