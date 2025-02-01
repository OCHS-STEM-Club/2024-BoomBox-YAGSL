// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ArmSubsystem. */
  private CANSparkMax armMotorLeft;
  private CANSparkMax armMotorRight;
  private RelativeEncoder armEncoderLeft;
  private RelativeEncoder armEncoderRight;
  private DigitalInput lowerHardStop;
  private DigitalInput upperHardStop;
  private boolean atshootervalue;

  private SparkPIDController m_pidController;
  private AbsoluteEncoder m_encoder;

  private double speed;
  private double value;
  private double armValue;
  public boolean tuningMode;

  public ArmSubsystem() {
    armMotorLeft = new CANSparkMax(Constants.ArmConstants.kArmMotorLeftID, MotorType.kBrushless);
    armMotorRight = new CANSparkMax(Constants.ArmConstants.kArmMotorRightID, MotorType.kBrushless);
    armMotorLeft.setIdleMode(IdleMode.kBrake);
    armMotorRight.setIdleMode(IdleMode.kBrake);
    armMotorLeft.setInverted(false);
    armMotorRight.setInverted(false);

    armMotorRight.setSmartCurrentLimit(30, 20);
    armMotorLeft.setSmartCurrentLimit(30, 20);
  

    m_encoder = armMotorRight.getAbsoluteEncoder(SparkAbsoluteEncoder.Type.kDutyCycle);
    m_encoder.setPositionConversionFactor(360);
    m_encoder.setZeroOffset(ArmConstants.kEncoderZeroOffset);

    m_pidController = armMotorRight.getPIDController();

    m_pidController.setP(ArmConstants.kP);
    m_pidController.setI(ArmConstants.kI);
    m_pidController.setD(ArmConstants.kD);
    m_pidController.setIZone(ArmConstants.kIz);
    m_pidController.setFF(ArmConstants.kFF);
    m_pidController.setOutputRange(ArmConstants.kMinOutput, ArmConstants.kMaxOutput);
    m_pidController.setFeedbackDevice(m_encoder);
    

    armMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 100);
    armMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus4, 100);
    armMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 100);
    armMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 100);

    armMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 100);
    armMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus4, 100);
    armMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 100);
    armMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 100);

    armMotorLeft.follow(armMotorRight);

    armValue = 0;
    tuningMode = false;     

    SmartDashboard.putNumber("Arm PID Value", armValue);
    SmartDashboard.putBoolean("Tuning Mode", tuningMode);
  }

  @Override
  public void periodic() {
    
    // This method will be called once per scheduler run
    Logger.recordOutput("Left Arm Motor", armMotorLeft.get());
    Logger.recordOutput("Right Arm Motor", armMotorRight.get());
    Logger.recordOutput("Arm Encoder", m_encoder.getPosition());

    SmartDashboard.putNumber("Arm Encoder", m_encoder.getPosition());

    double value = SmartDashboard.getNumber("Arm PID Value", 0);
    boolean tuningMode = SmartDashboard.getBoolean("Tuning Mode", false);
    if((value != 0 && tuningMode == true)) { m_pidController.setReference(value,CANSparkMax.ControlType.kPosition); armValue = value; }

  }


  public void armUp() {
    armMotorLeft.set(Constants.ArmConstants.kArmUpSpeed);
    armMotorRight.set(Constants.ArmConstants.kArmUpSpeed);
  }
  public void armDown() {
    armMotorLeft.set(Constants.ArmConstants.kArmDownSpeed);
    armMotorRight.set(Constants.ArmConstants.kArmDownSpeed);
  }
  public void armOff() {
    armMotorLeft.set(0);
    armMotorRight.set(0);
  }
  public void set(double speed) {
      armMotorLeft.set(speed);
      armMotorRight.set(speed);
  }

  public void intakeSetpoint() {
    m_pidController.setReference(Constants.ArmConstants.kIntakeSetpoint, CANSparkMax.ControlType.kPosition);
  }

  public void shooterSetpoint() {
    m_pidController.setReference(Constants.ArmConstants.kShooterSetpoint, CANSparkMax.ControlType.kPosition);
  }

  public void ampSetpoint() {
    m_pidController.setReference(Constants.ArmConstants.kAmpSetpoint, CANSparkMax.ControlType.kPosition);
  }

  public void setReference(double pidReference) {
    m_pidController.setReference(pidReference,CANSparkMax.ControlType.kPosition);
  }

  // public void setReferenceSD() {
  //   if((value != 0)) { m_pidController.setReference(value,CANSparkMax.ControlType.kPosition); armValue = value; }

  // }
  // public void SettuningMode() {
  //   boolean tuningMode = true;
  // }



}