package frc4206.robovikes.subsystems;

import frc4206.robovikes.common.LoadableConfig;

public class GenericSubsystem {

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

        public Config(String filename) {
            super.load(this, filename);
            LoadableConfig.print(this);
        }
    }

}
