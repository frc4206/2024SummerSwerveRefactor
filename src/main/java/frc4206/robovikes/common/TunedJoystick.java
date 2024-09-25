package frc4206.robovikes.common;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public final class TunedJoystick {

    private ResponseCurveFunction rcf;
    private double EXPONENT = 1.0;
    private double DEADZONE = 0.1d;
    private static final double _DEADZONE_MAX = 0.2d;

    private XboxController cntrllr;

    public static enum ResponseCurve {
        LINEAR,
        VERYSOFT,
        SOFT,
        QUADRATIC,
        CUBIC,
        CIRCULAR // 1 - sqrt(1 - x^(2))
    }

    public interface ResponseCurveFunction {
        double applyCurve(double i);
    }

    public TunedJoystick(XboxController c) {
        this.cntrllr = c;
        this.rcf = this::linear;
        this.DEADZONE = 0.1d;
    }

    public static double map(double val, double in_min, double in_max, double out_min, double out_max) {
        return ((val - in_min) * (out_max - out_min) / (in_max - in_min)) + out_min;
    }

    public double applyDeadzone(double val) {
        double dead_zoned = (Math.abs(val) >= DEADZONE ? map(Math.abs(val), DEADZONE, 1.0d, 0.0d, 1.0d) : 0.0d);
        return val >= 0.0d ? dead_zoned : -dead_zoned;
    }

    private double linear(double val) {
        return val;
    }

    private double exponential(double val) {
        return val >= 0.0d ? Math.pow(val, EXPONENT) : -Math.pow(val, EXPONENT);
    }

    private double quadratic(double val) {
        return val >= 0.0d ? (val * val) : -(val * val);
    }

    private double cubic(double val) {
        return (val * val * val);
    }

    private void useExponential(double exponent){
        rcf = this::exponential;
        this.EXPONENT = exponent;
    }

    public void useResponseCurve(ResponseCurve rc) {
        switch (rc) {
            case VERYSOFT -> useExponential(1.48d);
            case SOFT -> useExponential(1.64d);
            case QUADRATIC -> rcf = this::quadratic;
            case CUBIC -> rcf = this::cubic;
            default -> rcf = this::linear;
        }
    }

    public void setDeadzone(double d) {
        // Check for negative just in case
        DEADZONE = Math.abs(d);
    }

    private double applyTune(double i) {
        // Note that the deadzone must be applied
        // BEFORE the curve function
        return rcf.applyCurve(applyDeadzone(i));
    }

    public double getLeftX() {
        return applyTune(cntrllr.getLeftX());
    }

    public double getLeftY() {
        return applyTune(cntrllr.getLeftY());
    }

    public double getRightX() {
        return applyTune(cntrllr.getRightX());
    }

    public double getRightY() {
        return applyTune(cntrllr.getRightY());
    }

}
