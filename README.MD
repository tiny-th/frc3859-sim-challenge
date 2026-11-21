# frc3859-sim-challenge

WPILib: v2026.2.1

Run Steps:
- Open frc3859-sim-challenge folder in WPILib
- Search for ">WPILib: Simulate Robot Code" and run command
- Select "Sim GUI" and click "OK"
- If the simulation does not appear, select "NetworkTables" -> "SmartDashboard" -> "Field"
- Choose between "Disabled," "Autonomous," and 'Teleoperated" states in the "Robot State" window
- To see values displayed in the simulation, select "NetworkTables" -> "NetworkTables View" -> "SmartDashboard"

Keyboard Mapping:
- Axis 1: Increase - S, Decrease - W
- Axis 2: Increaes - R, Decrease - E
- Axis 4: Increase - D, Decrease - A
- Button 1: Z
- Button 2: X
- Button 3: C
- Button 4: V
- POV 0:
    - 0 deg: 8
    - 45 deg: 9
    - 90 deg: 6
    - 135 deg: 3
    - 100 deg: 2
    - 225 deg: 1
    - 270 deg: 4
    - 315 deg: 7

Changes:
- Added functionality to button 4 (key V): holding it down halves forward/backward inputs
- Enabling autonomous starts a timer. For two seconds, the robot moves in the forward direction. Can be stopped at any point by switching to "Disabled" or "Teleoperated" state.

Sources:
- https://docs.wpilib.org/en/stable/docs/software/vscode-overview/creating-robot-program.html
- https://docs.wpilib.org/en/stable/docs/software/wpilib-tools/robot-simulation/drivesim-tutorial/simulation-instance.html
- https://frcsoftware.org/learning-course/stage1/stage1a/kitbot-drivetrain/
- https://docs.wpilib.org/en/stable/docs/software/wpilib-tools/robot-simulation/simulation-gui.html
- https://docs.wpilib.org/en/stable/docs/software/dashboards/glass/field2d-widget.html

Bugs Encountered:
- When trying to stop autonomous/teleoperated motion by switching to "Disabled" state, the robot moves forward in the last direction indefinitely until it is switched to another state. Fixed by overriding disabledPeriodic() to drive the robot at zero velocity while it is disabled.


| Action  | Expected | Observed  | Pass/Fail |
| ------------- | ------------- | ------------- | ------------- |
| Normal drive/turn | Robot is able to move forward, backward, and turn left/right in motion and in place | Robot moves forward, backward, and turn left/right in motion and in place | Pass |
| Slow mode held/released | When held, inputs are halved and robot moves slower. Releasing it returns back to normal speed | Slows down as expected and can return to normal speed | Pass |
| Autonomous driving then stopping | Robot commands forward movement for two seconds and then stops for the rest of the autonomous period | Robot moves forward for two seconds and then stops for the rest of the autonomous period | Pass |
| Disabling during motion | Switching to "Disabled" state stops teleoperated and autonomous movement | Robot stops moving when switching to "Disabled", even when movement keys are held or previously in autonomous motion | Pass |
| Disabling/re-enabling autonomous | Switching to "Disabled" or "Teleoperated" state stops autonomous movement, switching back to "Autonomous" should move the robot for the whole two seconds | Robot abruptly stops movement before two seconds has passed, then resumes movement for the whole two seconds when switched back to "Autonomous" | Pass |