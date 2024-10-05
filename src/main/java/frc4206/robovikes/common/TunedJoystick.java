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
         * It's critical that the output has the same sign
         * as the input, and even numbered exponents (or most
         * fractional exponents) do not preserve sign
         * after their operation.
         */
        public double applyCurve(double val) {
            return val >= 0.0d ? Math.pow(val, exponent) : -Math.pow(val, exponent);
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
     * Returns 0 if (absolute value of) input is lower than deadzone
     * Otherwise, deadzone is the new '0' and scales to the max value (of 1.0)
     * Also, this implementation uses a square deadzone since using a circular
     * deadzone requires the x AND y values to calculate vector magnitude, hence x
     * AND y.
     */
    public double applyDeadzone(double val) {
        double dead_zoned = (Math.abs(val) >= deadzone ? map(Math.abs(val), deadzone, 1.0d, 0.0d, 1.0d) : 0.0d);
        return val >= 0.0d ? dead_zoned : -dead_zoned;
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
     * Note that the deadzone must be applied BEFORE the
     * reponse curve. This is a critical order of operations,
     * so it deserves it's own function for scrutiny.
     */
    private double tune(double i) {
        return rc.applyCurve(applyDeadzone(i));
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