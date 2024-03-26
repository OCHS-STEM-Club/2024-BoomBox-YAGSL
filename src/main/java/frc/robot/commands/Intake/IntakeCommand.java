// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command {
  /** Creates a new IntakeCommand. */
  IntakeSubsystem m_intakeSubsystem;
  // LimelightSubsystem m_limelightSubsystem;
  /** Creates a new IntakeCommand. */
  public IntakeCommand(IntakeSubsystem intakeSubsystem) {
  m_intakeSubsystem = intakeSubsystem;
  // m_limelightSubsystem = limelight;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   if (m_intakeSubsystem.beamBreakSensor() == true) {
     m_intakeSubsystem.intakeSpeed(0.4);
     LimelightHelpers.setLEDMode_ForceOff("limelight-boombox");
   } else  m_intakeSubsystem.intakeOff();
        LimelightHelpers.setLEDMode_ForceBlink("limelight-boombox");

  // if (m_intakeSubsystem.beamBreakSensor() == true) {
  //    m_intakeSubsystem.intakeSpeed(0.4);
  //    LimelightHelpers.setLEDMode_ForceOff("limelight-boombox");
  //  } else if (m_intakeSubsystem.beamBreakSensor() == false) {
  //   m_intakeSubsystem.intakeOff();
  //   LimelightHelpers.setLEDMode_ForceBlink("limelight-boombox");
    
  //  }
   
        
   
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intakeSubsystem.intakeOff();
    LimelightHelpers.setLEDMode_ForceOff("limelight-boombox");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

