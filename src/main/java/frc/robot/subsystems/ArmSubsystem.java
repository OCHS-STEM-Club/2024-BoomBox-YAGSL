// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

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
  // private SparkAbsoluteEncoder throughboreAbsoluteEncoder;

  private SparkPIDController m_pidController;
  private AbsoluteEncoder m_encoder;

  //private DutyCycleEncoder thru = new DutyCycleEncoder(3);

  private double speed;

  //private SparkPIDController armPIDController;
  // private static final SparkMaxAlternateEncoder.Type kAltEncType = SparkMaxAlternateEncoder.Type.kQuadrature;
  // private double m_setpoint = 0;
  // private double pidReference = 0;
  // private double armPValue = 0;
  // private double armDValue = 0;

  //private PIDController pid;

  public ArmSubsystem() {
    armMotorLeft = new CANSparkMax(Constants.ArmConstants.kArmMotorLeftID, MotorType.kBrushless);
    armMotorRight = new CANSparkMax(Constants.ArmConstants.kArmMotorRightID, MotorType.kBrushless);
    //thru = new DutyCycleEncoder(3);

    //armEncoderLeft = armMotorLeft.getEncoder();
   // armEncoderRight = armMotorRight.getEncoder();
    armMotorLeft.setIdleMode(IdleMode.kBrake);
    armMotorRight.setIdleMode(IdleMode.kBrake);
    armMotorLeft.setInverted(false);
    armMotorRight.setInverted(false);

    //thru = armMotorLeft.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle);
    // armMotorRight.setSmartCurrentLimit(30, 15);
    // armMotorLeft.setSmartCurrentLimit(30, 15);
    
    

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
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
    //  System.out.println(thru.getAbsolutePosition());
    //SmartDashboard.putNumber("PID shooter setpoint value", pid.calculate(thru.getAbsolutePosition()));

    SmartDashboard.putNumber("Arm Encoder", m_encoder.getPosition());

    SmartDashboard.putNumber("Built-In Right Encoder", armMotorRight.getEncoder().getPosition());
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
    //armPIDController.setReference(-3.75, ControlType.kPosition);
    m_pidController.setReference(0, CANSparkMax.ControlType.kPosition);
   // armMotorLeft.set(m_pidController.calculate(thru.getAbsolutePosition(), 0.9));
   // armMotorLeft.set(m_pidController.calculate(thru.getAbsolutePosition(), 0.9));
   // m_pidController.setSetpoint(0.9);
  }

  public void shooterSetpoint() {
    m_pidController.setReference(12, CANSparkMax.ControlType.kPosition);
   // armMotorRight.set(pid.calculate(thru.getAbsolutePosition(),-0.36));
  }

  public void ampSetpoint() {
    m_pidController.setReference(94, CANSparkMax.ControlType.kPosition);
   // armMotorRight.set(pid.calculate(thru.getAbsolutePosition(),0));
  }


  //   public void manualShooterSetpoint() {
  //    armPIDController.setReference(-2.7, ControlType.kPosition);
  //  // armMotorRight.set(pid.calculate(thru.getAbsolutePosition(),0));
  // }

  public void setReference(double pidReference) {
    m_pidController.setReference(pidReference,CANSparkMax.ControlType.kPosition);
  }



}