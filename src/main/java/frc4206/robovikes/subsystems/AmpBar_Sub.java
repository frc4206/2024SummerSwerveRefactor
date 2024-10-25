// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc4206.robovikes.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AmpBar_Sub extends SubsystemBase {
  /** Creates a new AmpBar_Sub. */
  public CANSparkMax ampBarMotor = new CANSparkMax(3, MotorType.kBrushed);
  public DigitalInput limitSwitch = new DigitalInput(0);

  public AmpBar_Sub() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void AmpBarOut_func(double speed) {
    ampBarMotor.set(speed);
  }
}
