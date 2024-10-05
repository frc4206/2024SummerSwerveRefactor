package frc4206.robovikes.common;

import edu.wpi.first.wpilibj.XboxController;

public final class TunedJoystick {

    private double deadzone;
    private XboxController cntrllr;
    private ResponseCurve rc;

    /*
     * In this design, only exponential reponse curves
     * are implemented so as to simplify implementation.
     */
    public static enum ResponseCurve {
        LINEAR(1.0d),
        VERYSOFT(1.48d),
        SOFT(1.64d),
        QUADRATIC(2.0d),
        CUBIC(3.0d);

        private double exponent;

        private ResponseCurve(double d) {
            this.exponent = d;
        }

        /*
         * This function assumes that the input is
         * an absolute value since fractional exponents,
         * and non-odd integer exponents, do not preserve the
         * sign of the input. Some fractional exponents are
         * not reflective.
         */
        public double applyCurve(double val) {
            return Math.pow(val, exponent);
        }
    }

    public TunedJoystick(XboxController c) {
        this.cntrllr = c;
        this.rc = ResponseCurve.LINEAR; // why default to anything except regular?
        this.deadzone = 0.01d; // default should be pretty small, in Christian's opinion
    }

    /* Epic math that scales one domain to a new domain */
    public static double map(double val, double in_min, double in_max, double out_min, double out_max) {
        return ((val - in_min) * (out_max - out_min) / (in_max - in_min)) + out_min;
    }

    /*
     * Returns 0 if input is lower than deadzone
     * Otherwise, deadzone is the new '0' and scales to the max value (of 1.0)
     * This implementation uses a square deadzone since using a circular
     * deadzone requires the x AND y values to calculate vector magnitude
     */
    public double applyDeadzone(double val) {
        return val >= deadzone ? map(val, deadzone, 1.0d, 0.0d, 1.0d) : 0.0d;
    }

    public TunedJoystick useResponseCurve(ResponseCurve rc) {
        this.rc = rc;
        return this;
    }

    public TunedJoystick setDeadzone(double d) {
        // Check for negative just in case
        this.deadzone = Math.abs(d);
        return this;
    }

    /*
     * The input to the sequence of operations MUST
     * be the absolute since the deadzone and response curve
     * functions have to sense of sign to reduce branching,
     * and thus overall performance.
     * Note that the deadzone must be applied BEFORE the
     * reponse curve. This is a critical order of operations,
     * so it deserves it's own function for scrutiny.
     */
    private double tune(double i) {
        double result = rc.applyCurve(applyDeadzone(Math.abs(i)));
        return (i >= 0.0d) ? result : result * -1.0d;
    }

    public double getLeftX() {
        return tune(cntrllr.getLeftX());
    }

    public double getLeftY() {
        return tune(cntrllr.getLeftY());
    }

    public double getRightX() {
        return tune(cntrllr.getRightX());
    }

    public double getRightY() {
        return tune(cntrllr.getRightY());
    }

}