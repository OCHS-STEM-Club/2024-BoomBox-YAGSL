// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.util.PIDConstants;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean constants. This
 * class should not be used for any other purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{

  public static final double ROBOT_MASS = (148 - 20.3) * 0.453592; // 32lbs * kg per pound
  public static final Matter CHASSIS    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
  public static final double LOOP_TIME  = 0.13; //s, 20ms + 110ms sprk max velocity lag

  public static final class AutonConstants
  {

    public static final PIDConstants TRANSLATION_PID = new PIDConstants(0.7, 0, 0);
    public static final PIDConstants ANGLE_PID   = new PIDConstants(1, 0, 0.01);
  }

  public static final class DrivebaseConstants
  {

    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds
  }

  public static class OperatorConstants
  {

    // Joystick Deadband
    public static final double LEFT_X_DEADBAND  = 0.1;
    public static final double LEFT_Y_DEADBAND  = 0.1;
    public static final double RIGHT_X_DEADBAND = 0.2;

    public static final double ROTATION_CONSTANT = 6;
    public static final double TRANSLATION_X_CONSTANT = 0.9;
    public static final double TRANSLATION_Y_CONSTANT = 0.9;

    public static final int kOperatorControllerPort = 2;
    public static final int kDriverControllerPort = 0;
  }

  public static class ShooterConstants {
    public static final int kShooterMotorLeftID = 21;
    public static final int kShooterMotorRightID = 22;
    public static final double kShooterSpeed = 0.4;
  }

  public static class IntakeConstants {
    public static final int kIntakeMotorID = 18;
    public static final double kIntakeInSpeed = 0.4;
    public static final double kIntakeOutSpeed = -0.45;
    public static final int kBeamBreakPort = 0;
  }

  public static class ArmConstants {
    public static final int kArmMotorLeftID = 19;
    public static final int kArmMotorRightID = 20;
    public static final double kEncoderZeroOffset = 295;

    public static final double kP = 0.02;
    public static final double kI = 0;
    public static final double kD = 0;
    public static final int kIz = 0;
    public static final int kFF = 0;
    public static final int kMaxOutput = 1;
    public static final double kMinOutput = -1;

    public static final double kIntakeSetpoint = 0;
    public static final double kShooterSetpoint = 12;
    public static final double kAmpSetpoint = 94;

    public static final double kArmUpSpeed = 0.2;
    public static final double kArmDownSpeed = -0.2;

  }
}
