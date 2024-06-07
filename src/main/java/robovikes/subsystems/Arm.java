// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package robovikes.subsystems;

import robovikes.common.Subsystem;

/** Add your docs here. */
public class Arm extends Subsystem {

    public Arm(){
        super(0.0001);
    } 

    public Arm(double refreshRate){
        super(refreshRate);
    }

    @Override
    protected void periodic() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'periodic'");
    }

}
