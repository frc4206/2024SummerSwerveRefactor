// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc4206.robovikes.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc4206.robovikes.common.Subsystem;
import frc4206.robovikes.common.LoadableConfig;

/** Add your docs here. */
public class Flywheel extends Subsystem {
	private CANSparkFlex motor;
	private RelativeEncoder encdr;
	private SparkPIDController cntrllr;

	public static final class Config extends LoadableConfig {
		public int canID;
		public boolean inverted;
		public double kp;
		public double ki;
		public double kiZone;
		public double kd;
		public String value;
		public int maxVel;
		public int minVel;
		public int maxAccel;
		public int allowedError;

		public Config(String filename) {
			super.load(this, filename);
			LoadableConfig.print(this);
		}
	}

	public Flywheel(Flywheel.Config cfg) {
		CANSparkFlex mtr = new CANSparkFlex(cfg.canID, MotorType.kBrushless);
		RelativeEncoder encdr = mtr.getEncoder();
		SparkPIDController cntrllr = mtr.getPIDController();

		this.motor = mtr;
		this.encdr = encdr;
		this.cntrllr = cntrllr;

		this.configureFlywheel(cfg);
	}

	public void configureFlywheel(Flywheel.Config cfg) {
		motor.setInverted(cfg.inverted);
		cntrllr.setFeedbackDevice(encdr);
		cntrllr.setP(cfg.kp);
		cntrllr.setI(cfg.ki);
		cntrllr.setIZone(cfg.kiZone);
		cntrllr.setD(cfg.kd);
		cntrllr.setOutputRange(-1, 1, 0);
		cntrllr.setSmartMotionMaxVelocity(cfg.maxVel, 0);
		cntrllr.setSmartMotionMaxAccel(cfg.minVel, 0);
		cntrllr.setSmartMotionAllowedClosedLoopError(cfg.allowedError, 0);
		motor.burnFlash();
	}

	@Override
	protected void periodic() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'periodic'");
	}
}
