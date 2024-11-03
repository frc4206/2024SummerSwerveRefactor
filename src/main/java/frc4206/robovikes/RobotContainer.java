// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc4206.robovikes;

import java.security.GeneralSecurityException;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc4206.robovikes.common.DefaultTalonFX;
import frc4206.robovikes.common.TunedJoystick;
import frc4206.robovikes.common.TunedJoystick.ResponseCurve;
import frc4206.robovikes.generated.TunerConstants;
import frc4206.robovikes.subsystems.CommandSwerveDrivetrain;
import frc4206.robovikes.subsystems.GenericSubsystem;

public class RobotContainer {
    // private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
    // private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

    // /* Setting up bindings for necessary control of the swerve drive platform */
    // private final CommandXboxController joystick = new CommandXboxController(0); // My joystick
    // private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

    // public final TunedJoystick tunedJoystick = new TunedJoystick(joystick.getHID())
    //         .useResponseCurve(ResponseCurve.QUADRATIC)
    //         .setDeadzone(0.1d);

    // private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
    //         // .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1)
    //         .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric

    // // driving in open loop
    // private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    // private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    // private final Telemetry logger = new Telemetry(MaxSpeed);

    // private void configureBindings() {

    //     drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
    //             drivetrain.applyRequest(() -> drive.withVelocityX(-tunedJoystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
    //                     .withVelocityY(-tunedJoystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
    //                     .withRotationalRate(-tunedJoystick.getRightX() * MaxAngularRate) // Drive counterclockwise with
    //                                                                                      // negative X (left)
    //             ));

    //     drivetrain.applyRequest(() -> drive.withVelocityX(-tunedJoystick.getLeftY() * MaxSpeed) // Drive forward
    //                                                                                             // with
    //             // negative Y (forward)
    //             .withVelocityY(-tunedJoystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
    //             .withRotationalRate(-tunedJoystick.getRightX() * MaxAngularRate) // Drive counterclockwise with
    //                                                                              // negative X (left)
    //     );

    //     joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
    //     joystick.b().whileTrue(drivetrain
    //             .applyRequest(() -> point
    //                     .withModuleDirection(new Rotation2d(-tunedJoystick.getLeftY(), -tunedJoystick.getLeftX()))));

    //     // reset the field-centric heading on left bumper press
    //     joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    //     if (Utils.isSimulation()) {
    //         drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    //     }
    //     drivetrain.registerTelemetry(logger::telemeterize);
    // }

    public RobotContainer() {
        // configureBindings();

        // DefaultTalonFX.Config cfg = new DefaultTalonFX.Config("motor1/motor1.properties");
        GenericSubsystem.Config gsc = new GenericSubsystem.Config("generic/genericConfigFile.toml");

        // DefaultTalonFX.Config tlnfx_cfg = new DefaultTalonFX.Config("flywheel/flywheel1.toml");
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
