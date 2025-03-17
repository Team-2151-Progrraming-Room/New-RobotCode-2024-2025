package frc.robot.subsystems;

import java.util.concurrent.atomic.DoubleAccumulator;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//our constants
import frc.robot.Constants.VisionConstants;


public class VisionSubsystem extends SubsystemBase{
    
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry ty = table.getEntry("ty");

    double targetOffsetAngle_Vertical;
    double limelightMountAngleDegrees;
    double limelightLensHeightInches;

    double goalHeightInches;
    double angleToGoalDegrees;
    double angleToGoalRadians;

    double distanceFromLimelightToGoalInches;

    public VisionSubsystem(){
      
        targetOffsetAngle_Vertical = ty.getDouble(0.0);

        // how many degrees back is your limelight rotated from perfectly vertical?
        limelightMountAngleDegrees = VisionConstants.klimelightMountAngleDegrees; 

        // distance from the center of the Limelight lens to the floor
        limelightLensHeightInches = VisionConstants.klimelightLensHeightInches; 

        // distance from the target to the floor
        goalHeightInches = VisionConstants.kBargeHeight; 
    }

    public double getDistanceFromTarget(){
        targetOffsetAngle_Vertical = ty.getDouble(0.0);

        angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        angleToGoalRadians = angleToGoalDegrees * (Math.PI / 180.0);

        distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);

        return distanceFromLimelightToGoalInches;
    }

    public double getAngleOffset(){
        targetOffsetAngle_Vertical = ty.getDouble(0.0);
        return targetOffsetAngle_Vertical;
    }

    public boolean isTargetWithinRange() {

        double range = getDistanceFromTarget();
        
        if (range >= VisionConstants.kMinShootRange && range <= VisionConstants.kMaxShootRange) {
            return true;
        }

        return false;              // out of range
    }

}
