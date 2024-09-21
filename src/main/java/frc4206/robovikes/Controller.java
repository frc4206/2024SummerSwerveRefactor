// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc4206.robovikes;

import java.util.Arrays;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Controller extends SubsystemBase {

  public final static double DEADZONE = 0.2d; // 10% of sticks yields naught, account for joystick settle imperfections

  private final Joystick joystick = new Joystick(0);

  // private final int jystk_lft_x_axs = XboxController.Axis.kLeftX.value;
  // private final int jystk_lft_y_axs = XboxController.Axis.kLeftY.value;

  /** Creates a new Controller. */
  public Controller() {
  }

  public double magnitude(double x_val, double y_val) {
    double x_abs = Math.abs(x_val);
    double y_abs = Math.abs(y_val);
    return Math.sqrt((x_abs * x_abs) + (y_abs * y_abs));
  }

  public double map(double val, double in_min, double in_max, double out_min, double out_max) {
    return ((val - in_min) * (out_max - out_min) / (in_max - in_min)) + out_min;
  }

  public double[] circular_deadzone(double x_val, double y_val, double deadzone) {
    double magnitude = magnitude(x_val, y_val);
    double angle = Math.atan2(x_val, y_val);

    magnitude = magnitude >= deadzone ? map(magnitude, deadzone, 1.0, 0.0, 1.0) : 0.0d;

    // usually, x would be the cos and y would be the sin,
    // the XBox controllers Y input in inverted (by default I think)
    // so effectively, the coordinate system is rotated 90 degrees clockwise
    // this is why sin is x and cos is y
    double x = magnitude * Math.sin(angle);
    double y = magnitude * Math.cos(angle);
    double[] coordinates = {x, y};

    return coordinates;
  }

  public double square_deadzone(double val, double deadzone){
    double dead_zoned = (Math.abs(val) >= deadzone ? map(Math.abs(val), deadzone, 1.0d, 0.0d, 1.0d) : 0.0d);
    return val >= 0.0d ? dead_zoned : -dead_zoned;
  }

  public double quadratic(double val){
    return val >= 0.0d ? (val * val) : -(val * val);
  }

  public double cubic(double val){
    return (val * val * val);
  }

  public double square_root(double val){
    double result = Math.sqrt(Math.abs(val));
    return val >= 0.0d ? result : -result;
  }

  public double logarithmic(double val){
    double result = 1.0d + Math.log(Math.abs(val));
    result = result >= 0.1d ? result : 0.0d; // log is a special case, low numbers lead to high negative values
    return val >= 0.0d ? result : -result;
  }

  public double spherical(double val){
    return 1.0d - Math.sqrt(1.0d - (val * val));
  }

  public double cosinosoidal(double val){
    return (Math.cos(Math.PI*(val + 1.0d)) / 2.0d) + 0.5d;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double x_val = joystick.getRawAxis(XboxController.Axis.kLeftX.value);
    double y_val = joystick.getRawAxis(XboxController.Axis.kLeftY.value);

    double[] coordinates = circular_deadzone(x_val, y_val, DEADZONE);

    double x_cdz = coordinates[0];
    double y_cdz = coordinates[1];

    double x_sdz = square_deadzone(x_val, DEADZONE);
    double y_sdz = square_deadzone(y_val, DEADZONE);

    double x_qud = quadratic(x_val);
    double y_qud = quadratic(y_val);

    double x_cbc = cubic(x_val);
    double y_cbc = cubic(y_val);
    
    double x_sqr = square_root(x_val);
    double y_sqr = square_root(y_val);

    double x_log = logarithmic(x_val);
    double y_log = logarithmic(y_val);

    double x_sphr = spherical(x_val);
    double y_sphr = spherical(x_val);

    double x_cos = cosinosoidal(x_val);
    double y_cos = cosinosoidal(y_val);

    String prev_line_code = "\033[F\r\033[F\r"; // goes back up two lines
    String clear_line = "\033[2K"; // clears current line

    System.out.format("%s\n", clear_line);
    System.out.format("Joystick X | Raw: % 1.2f   Sqr-Dz: % 1.2f   Cir-Dz: % 1.2f   Quad: % 1.2f   Cubic: % 1.2f   Sqrt: % 1.2f   Log: % 1.2f   Sphr: % 1.2f   Cos: % 1.2f\n", x_val, x_sdz, x_cdz, x_qud, x_cbc, x_sqr, x_log, x_sphr, x_cos);
    System.out.format("Joystick Y | Raw: % 1.2f   Sqr-Dz: % 1.2f   Cir-Dz: % 1.2f   Quad: % 1.2f   Cubic: % 1.2f   Sqrt: % 1.2f   Log: % 1.2f   Sphr: % 1.2f   Cos: % 1.2f%s", y_val, y_sdz, y_cdz, y_qud, y_cbc, y_sqr, y_log, y_sphr, y_cos, prev_line_code);
  }
}
