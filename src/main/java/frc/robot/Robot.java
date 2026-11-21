// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import edu.wpi.first.math.controller.LTVUnicycleController;
import edu.wpi.first.math.filter.SlewRateLimiter;
//import edu.wpi.first.math.geometry.Pose2d;
//import edu.wpi.first.math.geometry.Rotation2d;
//import edu.wpi.first.math.kinematics.ChassisSpeeds;
//import edu.wpi.first.math.trajectory.Trajectory;
//import edu.wpi.first.math.trajectory.TrajectoryConfig;
//import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import java.util.List;

public class Robot extends TimedRobot {
  private final XboxController m_controller = new XboxController(0);

  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0
  // to 1.
  private final SlewRateLimiter m_speedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);

  private final Drivetrain m_drive = new Drivetrain();
  //private final LTVUnicycleController m_feedback = new LTVUnicycleController(0.020);
  private final Timer m_timer = new Timer();
  //private final Trajectory m_trajectory;

  /** Called once at the beginning of the robot program. */
  public Robot() {
    /*
      m_trajectory =
        TrajectoryGenerator.generateTrajectory(
            new Pose2d(2, 2, Rotation2d.kZero),
            List.of(),
            new Pose2d(2, 3, Rotation2d.kZero),
            new TrajectoryConfig(2, 2));
    */
  }

  @Override
  public void robotPeriodic() {
    m_drive.periodic();
  }

  @Override
  public void autonomousInit() {
    m_timer.restart();
  }

  @Override
  public void autonomousPeriodic() {
    //Drive at max speed until two seconds is up, then set speed to 0
    if (m_timer.hasElapsed(2))
    {
      m_drive.drive(0, 0);
    }
    else
    {
      m_drive.drive(Drivetrain.kMaxSpeed, 0);
    }
  }

  @Override
  public void teleopPeriodic() {
    // Get the x speed. We are inverting this because Xbox controllers return
    // negative values when we push forward.
    // When Button 4 is pressed (mapped to v), halve forward/backward inputs (w/s).
    double HALF = 0.5;
    double xSpeed;
    if (m_controller.getRawButton(4))
    {
      xSpeed = -m_speedLimiter.calculate(HALF * m_controller.getLeftY()) * Drivetrain.kMaxSpeed;
    }
    else
    {
      xSpeed = -m_speedLimiter.calculate(m_controller.getLeftY()) * Drivetrain.kMaxSpeed;
    }
    SmartDashboard.putBoolean("Slow Mode", m_controller.getRawButton(4));
    // Get the rate of angular rotation. We are inverting this because we want a
    // positive value when we pull to the left (remember, CCW is positive in
    // mathematics). Xbox controllers return positive values when you pull to
    // the right by default.
    double rot = -m_rotLimiter.calculate(m_controller.getRightX()) * Drivetrain.kMaxAngularSpeed;
    m_drive.drive(xSpeed, rot);
  }

  @Override
  public void disabledPeriodic() {
    //No driving during this period
    m_drive.drive(0, 0);
  }

  @Override
  public void simulationPeriodic() {
    m_drive.simulationPeriodic();
  }
}
