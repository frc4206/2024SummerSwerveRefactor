package frc4206.robovikes.resources;

/** Add your docs here. */
public abstract class BitTiming {
    public final int zeroHigh;
    public final int zeroLow;
    public final int oneHigh;
    public final int oneLow;

    public BitTiming(int zeroHigh, int zeroLow, int oneHigh, int oneLow){
        this.zeroHigh = zeroHigh;
        this.zeroLow = zeroLow;
        this.oneHigh = oneHigh;
        this.oneLow = oneLow;
    }
}
