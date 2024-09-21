// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc4206.robovikes;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc4206.robovikes.subsystems.Flywheel;
import frc4206.robovikes.subsystems.GenericSubsystem;

public class Robot extends TimedRobot {

    private Command m_autonomousCommand;
    
    private RobotContainer m_robotContainer;
    
    public Robot(double refreshRate) {
        super(refreshRate);
    }
    
    @Override
    public void robotInit() {
        m_robotContainer = new RobotContainer();

        Flywheel.Config fc = new Flywheel.Config("flywheel/flywheel1.properties");
        // Flywheel fw = new Flywheel(fc);

        Flywheel.Config fc2 = new Flywheel.Config("flywheel/flywheel2.properties");
        // Flywheel fw2 = new Flywheel(fc2);

        GenericSubsystem.Config gc = new GenericSubsystem.Config("generic/generic.properties");
    }
    
    @Override
    public void robotPeriodic() {
        //CommandScheduler.getInstance().run(); 
    }
    
    @Override
    public void disabledInit() {}
    
    @Override
    public void disabledPeriodic() {}
    
    @Override
    public void disabledExit() {}
    
    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();
        
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }
    
    @Override
    public void autonomousPeriodic() {}
    
    @Override
    public void autonomousExit() {}
    
    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
    }
    
    @Override
    public void teleopPeriodic() {
        // teleop 
    }
    
    @Override
    public void teleopExit() {}
    
    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }
    
    @Override
    public void testPeriodic() {}
    
    @Override
    public void testExit() {}
    
    @Override
    public void simulationPeriodic() {}
}
