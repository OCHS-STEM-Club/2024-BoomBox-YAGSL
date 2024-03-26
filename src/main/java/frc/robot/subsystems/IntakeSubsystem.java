// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */

        private CANSparkMax intakeMotor;
        private DigitalInput intakeSensor;
        private double speed;

    public IntakeSubsystem() {
        intakeMotor = new CANSparkMax(Constants.IntakeConstants.kIntakeMotorID, MotorType.kBrushless);
        intakeSensor = new DigitalInput(Constants.IntakeConstants.kBeamBreakPort);
        intakeMotor.setInverted(false);

        

        intakeMotor.setSmartCurrentLimit(30,30);

        intakeMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 100);
        intakeMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus4, 100);
        intakeMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 100);
        intakeMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 100);
        
    }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void intakeSpeed(double speed){
    intakeMotor.set(speed);
  }

  public void intakeIn() {
    intakeMotor.set(Constants.IntakeConstants.kIntakeInSpeed);
  }

  public void intakeOff() {
    intakeMotor.set(0);
  }

  public void intakeOut() {
    intakeMotor.set(Constants.IntakeConstants.kIntakeOutSpeed);
  }

  public boolean beamBreakSensor() {
    return intakeSensor.get();
  }
}

