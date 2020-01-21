/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;


/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private Joystick m_alphaJoystick;
  private Double joystickYvalue;
  private Double joystickXvalue;
  private Double joystickZvalue;

  

  private PWMVictorSPX frontRightSPX;

  //private Solenoid armLED;
  //private Solenoid arm2LED; 

  //climbing solenoids

  @Override
  public void robotInit() {
    System.out.println("Robot is Initializing...");
    frontRightSPX = new PWMVictorSPX(2);
    m_alphaJoystick= new Joystick(0);

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

    //c.setClosedLoopControl(true);

    joystickYvalue = -m_alphaJoystick.getY();
    System.out.print("Joystick Y Value: ");
    System.out.println(joystickYvalue);

    joystickXvalue = m_alphaJoystick.getX();
    System.out.print("Joystick X Value: ");
    System.out.println(joystickXvalue);

    joystickZvalue = m_alphaJoystick.getZ();
    System.out.print("Joystick Z Value: ");
    System.out.println(joystickZvalue);
  
    //m_alphaRobotDrive.tankDrive(joystickYvalue, joystickYvalue);
    frontRightSPX.set(10.0);

  }
}
