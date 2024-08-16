// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package robovikes.subsystems;

import static robovikes.resources.Static.CONFIG_DIR;

import java.io.FileInputStream;
import java.util.Properties;
import com.revrobotics.*;
import com.revrobotics.CANSparkLowLevel.MotorType;
import robovikes.common.Subsystem;
import robovikes.common.PrintableConfig;

/** Add your docs here. */
public class Flywheel extends Subsystem {
	private CANSparkFlex motor;
	private RelativeEncoder encdr;
	private SparkPIDController cntrllr;

	public static final class Config extends PrintableConfig {
		public int canID;
        public boolean inverted;
		public double kp;
		public double ki;
		public double kiZone;
		public double kd;
		public int maxVelocity;
		public int minVelocity;
		public int maxAcceleration;
		public int allowedError;

        public Config(String file) {
            try 
            {
                Properties p = new Properties();
                p.load(new FileInputStream(CONFIG_DIR + file));
                this.canID = Integer.parseInt(p.getProperty("canID"));
				this.inverted = Boolean.parseBoolean(p.getProperty("inverted"));
				this.kp = Double.parseDouble(p.getProperty("kp"));
				this.ki = Double.parseDouble(p.getProperty("ki"));
				this.kiZone = Double.parseDouble(p.getProperty("kiZone"));
				this.kd = Double.parseDouble(p.getProperty("kd"));
				this.maxVelocity = Integer.parseInt(p.getProperty("maxVel"));
				this.minVelocity = Integer.parseInt(p.getProperty("minVel"));
				this.allowedError = Integer.parseInt(p.getProperty("allowedError"));
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
                System.exit(-1);
            }
			finally {
				PrintableConfig.print(this);
			}
        }
    }

	public void configureFlywheel(Flywheel.Config cfg){
		motor.setInverted(cfg.inverted);
		cntrllr.setFeedbackDevice(encdr);
		cntrllr.setP(cfg.kp);
		cntrllr.setI(cfg.ki);
		cntrllr.setIZone(cfg.kiZone);
		cntrllr.setD(cfg.kd);
		cntrllr.setOutputRange(-1, 1, 0);
		cntrllr.setSmartMotionMaxVelocity(cfg.maxVelocity, 0);
		cntrllr.setSmartMotionMaxAccel(cfg.minVelocity, 0);
		cntrllr.setSmartMotionAllowedClosedLoopError(cfg.allowedError, 0);
		motor.burnFlash();
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

	@Override
	protected void periodic() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'periodic'");
	}
}
