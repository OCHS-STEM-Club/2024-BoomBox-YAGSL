// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Arm.ArmCommand;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Intake.IntakeOverrideCommand;
import frc.robot.commands.Setpoints.IntakeSetpoint;
import frc.robot.commands.Shooter.ShooterCommand;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDrive;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDriveAdv;
import frc.robot.commands.swervedrive.drivebase.AbsoluteFieldDrive;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;

import javax.management.OperationsException;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
  // private SwerveSubsystem m_swerveSubsystem = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),"swerve"));

  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve/neo"));
  private final ArmSubsystem m_armSubsystem = new ArmSubsystem();
  private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  final CommandXboxController driverXbox = new CommandXboxController(0);
  public CommandXboxController m_buttonBox = new CommandXboxController(OperatorConstants.kOperatorControllerPort);
  private final SendableChooser<Command> autoChooser;

  //Commands
  IntakeSetpoint m_intakeSetpoint = new IntakeSetpoint(m_armSubsystem);
  ArmCommand m_manualArmUpCommand = new ArmCommand(m_armSubsystem, 0.2);
  ArmCommand m_manualArmDownCommand = new ArmCommand(m_armSubsystem, -0.2);
  ShooterCommand m_shooterCommand = new ShooterCommand(m_shooterSubsystem);
  IntakeCommand m_intakeCommand = new IntakeCommand(m_intakeSubsystem);
  IntakeOverrideCommand m_intakeOverrideCommand = new IntakeOverrideCommand(m_intakeSubsystem);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {

    NamedCommands.registerCommand("Intake in Override", Commands.runOnce(() -> m_intakeSubsystem.intakeSpeed(0.4)));
    NamedCommands.registerCommand("Intake Out", Commands.runOnce(() -> m_intakeSubsystem.intakeSpeed(-0.4)));
    NamedCommands.registerCommand("Intake in BB", new IntakeCommand(m_intakeSubsystem).withTimeout(3));
    NamedCommands.registerCommand("Shooter On", Commands.runOnce(() -> m_shooterSubsystem.shooterOn(0.4)));
    NamedCommands.registerCommand("Shooter Off", Commands.runOnce(() -> m_shooterSubsystem.shooterOff()));
    NamedCommands.registerCommand("Intake Off", Commands.runOnce(() -> m_intakeSubsystem.intakeOff()));
    NamedCommands.registerCommand("Arm to Shooter 1st Middle", Commands.runOnce(() -> m_armSubsystem.setReference(27)));
    NamedCommands.registerCommand("Arm to Intake", Commands.runOnce(() -> m_armSubsystem.intakeSetpoint()));
    NamedCommands.registerCommand("Arm to Shooter Middle Subwoofer", Commands.runOnce(() -> m_armSubsystem.shooterSetpoint()));

    
    autoChooser = AutoBuilder.buildAutoChooser();
    // Configure the trigger bindings
    configureBindings();

    

    Command driveFieldOrientedDirectAngleSim = drivebase.simDriveCommand(
        () -> MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
        () -> driverXbox.getRawAxis(2));


    AbsoluteFieldDrive absoluteFieldDrive = new AbsoluteFieldDrive(
      drivebase, 
      () -> MathUtil.applyDeadband(-driverXbox.getLeftY()*0.9, OperatorConstants.LEFT_Y_DEADBAND), 
      () -> MathUtil.applyDeadband(-driverXbox.getLeftX()*0.9, OperatorConstants.LEFT_X_DEADBAND), 
      () -> MathUtil.applyDeadband(-driverXbox.getRightX()*OperatorConstants.TURN_CONSTANT, OperatorConstants.RIGHT_X_DEADBAND));


    drivebase.setDefaultCommand(!RobotBase.isSimulation() ? absoluteFieldDrive : driveFieldOrientedDirectAngleSim);
    
    SmartDashboard.putData("Autos", autoChooser);

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    driverXbox.a().onTrue((Commands.runOnce(drivebase::zeroGyro)));
    // driverXbox.x().onTrue(Commands.runOnce(drivebase::addFakeVisionReading));
    // driverXbox.b().whileTrue(
    //     Commands.deferredProxy(() -> drivebase.driveToPose(
    //                                new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
    //                           ));


    // driverXbox.x().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
    // driverXbox.y().whileTrue(Commands.deferredProxy(() -> drivebase.sysIdDriveMotorCommand()), drivebase.sysIdAngleMotorCommand());

    m_buttonBox.button(1).whileTrue(
      m_manualArmDownCommand
    );

    m_buttonBox.button(3).whileTrue(
      m_manualArmUpCommand
    );

    m_buttonBox.button(4).whileTrue(
      m_intakeSetpoint
    );

    m_buttonBox.button(6).whileTrue(
      Commands.runOnce(() -> m_armSubsystem.shooterSetpoint())
    );
      
    m_buttonBox.button(5).whileTrue(
      Commands.runOnce(() -> m_armSubsystem.ampSetpoint())
    );
      
    m_buttonBox.leftTrigger().whileTrue(
      Commands.runOnce(() -> m_armSubsystem.setReference(27))
    );

    driverXbox.rightTrigger().whileTrue(
      m_shooterCommand
    );

    driverXbox.leftTrigger().whileTrue(
      m_intakeCommand
    );

    driverXbox.b().whileTrue(
      m_intakeOverrideCommand
    );
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }

  public void setDriveMode() {
    //drivebase.setDefaultCommand();
  }

  public void setMotorBrake(boolean brake) {
    drivebase.setMotorBrake(brake);
  }

  public void zeroGyro() {
    drivebase.zeroGyro();
  }
}
