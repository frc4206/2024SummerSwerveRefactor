// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package robovikes;

import edu.wpi.first.wpilibj.RobotBase;

public final class Main {

    
    private Main() {}
    
    public static void main(String... args) {
        // humans can't perceive much faster than 16 milliseconds
        // set refresh to be about twice as fast
        Robot r = new Robot(0.007d);
        RobotBase.startRobot(() -> r); // pointer hack to have customizeable refresh rate
        r.close(); // remove warning
    }
}