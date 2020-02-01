/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// Getting the arcade drive in, assign gamepad values (eletric)
/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private Joystick m_alphaGamepad;
  private Double joystickYvalue;
  private Double joystickXvalue;
  private boolean squareInputs; // Boolean can only be true or false. (has to be lowercase)
  private int yAxisChannel;
  private int xAxisChannel;
  
  private DifferentialDrive m_alphaRobot;

  private Spark frontRightSPX;
  private Spark rearRightSPX;
  private Spark frontLeftSPX;
  private Spark rearLeftSPX;
  private SpeedControllerGroup rightGroupSPX;
  private SpeedControllerGroup leftGroupSPX;

  @Override
  public void robotInit() {
    System.out.println("Robot is Initializing...");
    frontRightSPX = new Spark(0);
    rearRightSPX = new Spark(1);
    frontLeftSPX = new Spark(2);
    rearLeftSPX = new Spark(3);
    leftGroupSPX = new SpeedControllerGroup(frontLeftSPX, rearLeftSPX);
    rightGroupSPX = new SpeedControllerGroup(frontRightSPX, rearRightSPX);
    m_alphaGamepad = new Joystick(0);
    yAxisChannel = 1; // These numbers are ints, when we definite it, it's just 1
    xAxisChannel = 5;
    squareInputs = true; // What type are squareInputs? Answer: Can be true or false. If we want to modify the senitivity, we say true or false
    m_alphaRobot = new DifferentialDrive(leftGroupSPX, rightGroupSPX);

    System.out.println("!!! AlphaBlue Robot READY !!!");
   }

  @Override
  public void autonomousInit() {
    //super.autonomousInit();
  }

  @Override
  public void autonomousPeriodic() {
    super.autonomousPeriodic();
  }

  @Override
  public void teleopPeriodic() {

    joystickYvalue = -m_alphaGamepad.getRawAxis(yAxisChannel);
    System.out.print("Joystick Y Value: ");
    System.out.println(joystickYvalue);

    joystickXvalue = m_alphaGamepad.getRawAxis(xAxisChannel);
    System.out.print("Joystick X Value: ");
    System.out.println(joystickXvalue);

    m_alphaRobot.arcadeDrive(joystickYvalue, joystickXvalue, squareInputs);
  }
}
