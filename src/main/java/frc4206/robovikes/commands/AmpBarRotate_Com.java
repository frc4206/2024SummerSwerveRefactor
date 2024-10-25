// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc4206.robovikes.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc4206.robovikes.subsystems.AmpBar_Sub;

public class AmpBarRotate_Com extends Command {
  /** Creates a new AmpBarRotate_Com. */
  long startTime = System.nanoTime();
  long duration = 0;

  double m_speed;
  AmpBar_Sub m_ampBarSub;
  public AmpBarRotate_Com(double speed, AmpBar_Sub ampBarSub) {
    m_speed = speed;
    m_ampBarSub = ampBarSub;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_ampBarSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    while (duration < 1_000_000_000L) {
      m_ampBarSub.AmpBarOut_func(m_speed);
      duration = System.nanoTime() - startTime;}
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
