/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_alphaRobotDrive;
  private Joystick m_alphaGamepad;
  private Joystick m_alphaJoystick;
  private Double joystickYvalue;
  private Double joystickXvalue;
  private Double joystickZvalue;

  private int x;
  private int y;
  private int result;

  private PWMVictorSPX frontLeftSPX;
  private PWMVictorSPX rearLeftSPX;
  private SpeedControllerGroup leftSPX;
  private SpeedControllerGroup rightSPX;
  private PWMVictorSPX frontRightSPX;
  private PWMVictorSPX rearRightSPX;

  private DoubleSolenoid armActuatingCylinder;
  private DoubleSolenoid plungerActuatingCylinder;
  //private Solenoid armLED;
  //private Solenoid arm2LED; 

  //climbing solenoids
  private Solenoid rearSingleSolenoid;
  private DoubleSolenoid frontDoubleSolenoid;

  private Compressor c;

  private Timer alphaTimer;

  @Override
  public void robotInit() {
    System.out.println("Robot is Initializing...");
    CameraServer.getInstance().startAutomaticCapture();
    frontLeftSPX = new PWMVictorSPX(0);
    rearLeftSPX = new PWMVictorSPX(1);
    frontRightSPX = new PWMVictorSPX(2);
    rearRightSPX = new PWMVictorSPX(3);
    leftSPX = new SpeedControllerGroup(frontLeftSPX, rearLeftSPX);
    rightSPX = new SpeedControllerGroup(frontRightSPX, rearRightSPX);
    m_alphaRobotDrive = new DifferentialDrive(leftSPX, rightSPX);
    m_alphaGamepad = new Joystick(1);
    m_alphaJoystick= new Joystick(0);
    c = new Compressor(0);
    armActuatingCylinder = new DoubleSolenoid(0,1);
    plungerActuatingCylinder = new DoubleSolenoid(2,3);
    //armLED = new Solenoid(4);
    //arm2LED = new Solenoid(5);
    rearSingleSolenoid = new Solenoid(4);
    frontDoubleSolenoid = new DoubleSolenoid(5,6);

    alphaTimer = new Timer();
    System.out.println("!!! AlphaBlue Robot READY !!!");
   }

  private void solenoidJoystickAction(){

    if(m_alphaGamepad.getRawButtonPressed(1)){
      System.out.println("Move Plunger Out");
      plungerActuatingCylinder.set(DoubleSolenoid.Value.kForward);
    }

    if(m_alphaGamepad.getRawButtonPressed(2)){
      System.out.println("Move Plunger Back");
      plungerActuatingCylinder.set(DoubleSolenoid.Value.kReverse);
    }

    if(m_alphaGamepad.getRawButtonPressed(3)){
      System.out.println("Move Arm Back");
      armActuatingCylinder.set(DoubleSolenoid.Value.kReverse);
      //armLED.set(false);
      //arm2LED.set(false);
    }

    if(m_alphaGamepad.getRawButtonPressed(4)){
      System.out.println("Move Arm Out");
      armActuatingCylinder.set(DoubleSolenoid.Value.kForward);
      //armLED.set(true);
      //arm2LED.set(true);
    }

    //CLIMB BUTTONS
    if(m_alphaGamepad.getRawButtonPressed(5)){
      rearSingleSolenoid.set(true);
    } else {
      rearSingleSolenoid.set(false);
    }

    if(m_alphaGamepad.getRawButtonPressed(6)){
      frontDoubleSolenoid.set(DoubleSolenoid.Value.kForward);
    } else {
      frontDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }


    /*
    if(m_alphaGamepad.getRawButtonPressed(11)){
      System.out.println("Kill Arm Cylinder");
      armActuatingCylinder.set(DoubleSolenoid.Value.kOff);
    }

    if(m_alphaGamepad.getRawButtonPressed(11)){
      System.out.println("Kill Plunger Cylinder");
      plungerActuatingCylinder.set(DoubleSolenoid.Value.kOff);
    }*/
  
  }

  @Override
  public void autonomousInit() {
    //super.autonomousInit();
    alphaTimer.reset();
    alphaTimer.start();
  }

  @Override
  public void autonomousPeriodic() {
    super.autonomousPeriodic();
    c.setClosedLoopControl(true);
    m_alphaRobotDrive.arcadeDrive(-m_alphaJoystick.getY(), m_alphaJoystick.getX());


    try {
      solenoidJoystickAction();
    } catch (Exception e) {
      System.out.println("Solenoid Error" + e);
    }

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

    m_alphaRobotDrive.arcadeDrive(joystickYvalue, joystickXvalue);

    try {
      solenoidJoystickAction();
    } catch (Exception e) {
      System.out.println("Solenoid Error" + e);
    }
  }
}
