// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package robovikes.common;

/** Add your docs here. */
public abstract class Subsystem {

    protected boolean _useTiming = true;
    private double _defaultRefreshRate = 0.02d;
    public double refreshRate;

    

    public Subsystem() {
        // Default set refresh rate to 20ms
        this.refreshRate = _defaultRefreshRate;
    }

    public Subsystem(double refreshRate) {
        if(refreshRate <= 0.001)
        {
            System.err.println("ERROR: Can not schedule a subsystem faster than 1 millisecond");
            System.exit(-1);
        }

        this.refreshRate = refreshRate;
    }

    public void timestamp(){
        
    }

    protected abstract void periodic(); 

}
