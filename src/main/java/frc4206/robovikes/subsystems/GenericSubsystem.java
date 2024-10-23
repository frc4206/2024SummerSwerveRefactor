package frc4206.robovikes.subsystems;

import frc4206.robovikes.common.LoadableConfig;
import frc4206.robovikes.common.Slot;
import frc4206.robovikes.common.LoadableConfig.LoadableConfigFactory;

public class GenericSubsystem {

    public static final class Config extends LoadableConfig {
        public int i;
        // public boolean bool;
        // public float f;
        // public short s;
        // public long l;
        // public char c;
        // public double d;
        // public String str;
        // public byte b;

        public Slot slot1;

        public Config(String filename) {
            super();
            super.load(this, filename);
            // LoadableConfig.print(this);
        }
    }

}
