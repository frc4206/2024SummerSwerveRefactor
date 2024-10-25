// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc4206.robovikes.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Flywheel_Sub extends SubsystemBase {
  /** Creates a new Flywheel_sub. */
  public CANSparkFlex UpperFlyFlex = new CANSparkFlex(1, MotorType.kBrushless);
  public CANSparkFlex lowerFlyFlex = new CANSparkFlex(2, MotorType.kBrushless);

  public Flywheel_Sub() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void spinFlyWheels_func(double speed) {
    UpperFlyFlex.set(speed + 0.15);
    lowerFlyFlex.set(-(speed));
  }
}
