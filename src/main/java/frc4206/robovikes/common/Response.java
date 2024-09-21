package frc4206.robovikes.common;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public final class Response {

    // setting up defaults
    private Curve rspcscrv = Curve.LINEAR;
    private DeadzoneType ddzn = DeadzoneType.SQUARE;
    private double deadzone = 0.1d;

    private XboxController cntrllr;

    public static enum Curve {
        LINEAR, // x
        VERYSOFT, // x^(1.48)
        SOFT, // x^(1.64)
        QUADRATIC, // x^2
        CUBIC, // x^3
        CIRCULAR // 1 - sqrt(1 - x^(2))
    }

    public static enum DeadzoneType {
        SQUARE,
        CIRCULAR
    }

    public Response(XboxController c){
        this.cntrllr = c;
    }

    public void setResponseCurve(Curve r){
        this.rspcscrv = r;
    }

    // public void setDeadzone(double d, Deadzone ddzn){
    //     this.d
    // }

    public static double map(double val, double in_min, double in_max, double out_min, double out_max) {
        return ((val - in_min) * (out_max - out_min) / (in_max - in_min)) + out_min;
    }
    
}
