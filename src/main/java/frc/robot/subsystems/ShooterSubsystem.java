// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
  private TalonFX shooterMotorLeft;
  private TalonFX shooterMotorRight;

  final VelocityVoltage m_velocity = new VelocityVoltage(0);
  final MotionMagicVoltage m_request = new MotionMagicVoltage(0);


  /** Creates a new shooterSubsystem. */
  public ShooterSubsystem() {
    shooterMotorLeft = new TalonFX(ShooterConstants.kShooterMotorLeftID);
    shooterMotorRight = new TalonFX(ShooterConstants.kShooterMotorRightID);

    shooterMotorLeft.setInverted(false);
    shooterMotorRight.setInverted(false);

    shooterMotorLeft.setNeutralMode(NeutralModeValue.Coast);
    shooterMotorRight.setNeutralMode(NeutralModeValue.Coast);

    var TalonFXConfigsLeft = new TalonFXConfiguration();

    //in init function, set slot 0 gaind
    var slot0ConfigsLeft = TalonFXConfigsLeft.Slot0;
    slot0ConfigsLeft.kP = 0.1; // An error of 0.5 rotations results in 12 V output
    slot0ConfigsLeft.kI = 0; // no output for integrated error
    slot0ConfigsLeft.kD = 0; //A velocity of 1 rps results in 0.1 V output5


    shooterMotorLeft.getConfigurator().apply(slot0ConfigsLeft);

    var TalonFXConfigsRight = new TalonFXConfiguration();
    // in init function, set slot 0 gains
    var slot0ConfigsRight = TalonFXConfigsRight.Slot0;
    slot0ConfigsRight.kP = 0.1; // An error of 0.5 rotations results in 12 V output
    slot0ConfigsRight.kI = 0; // no output for integrated error
    slot0ConfigsRight.kD = 0; //A velocity of 1 rps results in 0.1 V output5


    shooterMotorRight.getConfigurator().apply(slot0ConfigsRight);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_velocity.Slot = 0;

  }

  public void shooterOn(double speed) {
    shooterMotorLeft.set(speed);
    shooterMotorRight.set(speed);
  }

  public void shooterOff() {
    shooterMotorLeft.set(0);
    shooterMotorRight.set(0);
  }


  public StatusSignal<Double> leftShooterVelocity() {
    return shooterMotorLeft.getVelocity();
  }

  public StatusSignal<Double> rightShooterVelocity() {
    return shooterMotorRight.getVelocity();
  }
  
}
