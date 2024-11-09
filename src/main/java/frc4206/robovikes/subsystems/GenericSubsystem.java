package frc4206.robovikes.subsystems;

import util.levodex.common.LoadableConfig;

public class GenericSubsystem {

    public static class Slot extends LoadableConfig {
        public double kp; // proportional
        public double ki; // integral
        public double kd; // derivative
        public double ks; // static feedforward
        public double kv; // velocity feedforward
        public double ka; // acceleration feedforward
    
        public Slot(){};
    }

    public static final class Config extends LoadableConfig {
        public int i;
        public boolean bool;
        public float f;
        public short s;
        public long l;
        public char c;
        public double d;
        public String str;
        public byte b;

        public Slot slot1;
        public Slot slot2;

        public Config(String filename) {
            super.load(this, filename);
            LoadableConfig.print(this);
            LoadableConfig.print(this.slot1);
        }
    }

}
