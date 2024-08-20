package robovikes.subsystems;

import robovikes.common.RobovikesConfig;

public class GenericSubsystem {

    public static final class Config extends RobovikesConfig {
		public int i;
		public boolean bool;
        public float f;
        public short s;
        public long l;
        // public char c;
        public double d;
        public String str;
        public byte b;

		public Config(String filename) {
			super.load(this, filename);
            RobovikesConfig.print(this);
		}
	}
    
}
