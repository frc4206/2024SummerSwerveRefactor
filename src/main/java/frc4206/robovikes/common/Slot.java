package frc4206.robovikes.common;

import frc4206.robovikes.common.LoadableConfig.LoadableConfigFactory;

public class Slot extends LoadableConfig {
    public double kp; // proportional
    public double ki; // integral
    public double kd; // derivative
    public double ks; // static feedforward
    public double kv; // velocity feedforward
    public double ka; // acceleration feedforward

    public Slot(){}
}
