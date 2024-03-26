// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCommand extends Command {
  /** Creates a new ShooterCommand. */
  private ShooterSubsystem m_shooterSubsystem;

  public ShooterCommand(ShooterSubsystem shooterSubsystem) {
  m_shooterSubsystem = shooterSubsystem;
 
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooterSubsystem.shooterOn(Constants.ShooterConstants.kShooterSpeed);
    // if(YValue > 13 || YValue == 0) {
    //   m_shooterSubsystem.shooterOn(0.4);
    // } else m_shooterSubsystem.shooterOn(0.5);
    }

    // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
   m_shooterSubsystem.shooterOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}