/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;

//import java.io.*;
//import java.nio.file.Path;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// Getting the arcade drive in, assign gamepad values (eletric)
/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
  public class Robot extends TimedRobot {
  private Joystick m_alphaGamepad; 
  private Joystick m_alphajoystick;
  private Double joystickYvalue;
  private Double joystickXvalue;
  private boolean squareInputs; // Boolean can only be true or false. (has to be lowercase)
  private int yAxisChannel;
  private int xAxisChannel;
  
  private DifferentialDrive m_alphaRobot;

  // Sparks used to control drivetrain
  private Spark frontRightSPX;
  private Spark rearRightSPX;
  private Spark frontLeftSPX;
  private Spark rearLeftSPX;
  private SpeedControllerGroup rightGroupSPX;
  private SpeedControllerGroup leftGroupSPX;

  // Sparks used to control intake and shooter
  private Spark inTakeSPX;
  private Spark flyWheelSPX;

  // DoubleSolniod
  private DoubleSolenoid actuateDoubleSolenoid;
  private Compressor m_Compressor;
  
 private UsbCamera camera1;
 private UsbCamera camera2;
 private NetworkTableEntry cameraSelection;
 private Encoder encoderRight;
 private Encoder encoderLeft;

  private double startTime;

  @Override
  public void robotInit() {
    System.out.println("Robot is Initializing...");
    frontRightSPX = new Spark(0);// For movement
    rearRightSPX = new Spark(1);
    frontLeftSPX = new Spark(2);
    rearLeftSPX = new Spark(3);
    leftGroupSPX = new SpeedControllerGroup(frontLeftSPX, rearLeftSPX);
    rightGroupSPX = new SpeedControllerGroup(frontRightSPX, rearRightSPX);
    m_alphajoystick = new Joystick(0);
    m_alphaGamepad = new Joystick(1);
    yAxisChannel = 1; // These numbers are ints, when we definite it, it's just 1. It's 1 because it's for the joystick input.
    xAxisChannel = 0;
    squareInputs = true; // What type are squareInputs? Answer: Can be true or false. If we want to modify the senitivity, we say true or false
    m_alphaRobot = new DifferentialDrive(leftGroupSPX, rightGroupSPX);

    inTakeSPX = new Spark(4);
    flyWheelSPX = new Spark(5);
    actuateDoubleSolenoid = new DoubleSolenoid(0, 1);
    //m_Compressor = new Compressor(0);

    Encoder encoderRight = new Encoder(0, 1, false, Encoder.EncodingType.k2X);
    Encoder encoderLeft = new Encoder(2, 3, false, Encoder.EncodingType.k2X);
    // Extra encoder configureation 
    //Wheel Diameter = 6in and Ecoder PPR = 1000 C = PI*D
    encoderRight.setDistancePerPulse(18.84/1000);
    encoderLeft.setDistancePerPulse(18.84/1000);
   // camera1 = CameraServer.getInstance().startAutomaticCapture(0);
   // camera2 = CameraServer.getInstance().startAutomaticCapture(1);
   // CvSink cvSink = CameraServer.getInstance().getVideo();
   // CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);

    cameraSelection = NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection");
    System.out.println("!!! AlphaBlue Robot READY !!!");
   }

  @Override
  public void autonomousInit() {
    //super.autonomousInit();
    startTime = Timer.getFPGATimestamp();
   // String trajectoryJSON = "paths/YourPath.wpilib.json";
   // try {
    //  Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
     // Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
   // } catch (IOException ex) {
   //   DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
    }

  //}

  @Override
  public void autonomousPeriodic() {
    final double time = Timer.getFPGATimestamp();
    
  if (time - startTime < 1) { 
    frontRightSPX.set(-0.6);
    rearRightSPX.set(-0.6);
    frontLeftSPX.set(0.6);
    rearLeftSPX.set(0.6);
  } else {
    //frontRightSPX.set(0);
    //rearRightSPX.set(0);
    //frontLeftSPX.set(0);
    //rearLeftSPX.set(0);
    disabledPeriodic();
  }  
    super.autonomousPeriodic();
  }

  @Override
  public void teleopPeriodic() {
     // turn on compressor
    // m_Compressor.setClosedLoopControl(true);

    joystickYvalue = -m_alphajoystick.getRawAxis(yAxisChannel);
    System.out.print("Joystick Y Value: ");
    System.out.println(joystickYvalue);

    joystickXvalue = m_alphajoystick.getRawAxis(xAxisChannel);
    System.out.print("Joystick X Value: ");
    System.out.println(joystickXvalue);

    m_alphaRobot.arcadeDrive(joystickYvalue, joystickXvalue, squareInputs);
    
    System.out.print("Total distance: ");
    System.out.println(encoderRight.getDistance());
   // if (m_alphajoystick.getTriggerPressed()) {
     // System.out.println("Setting camera 2");
     // cameraSelection.setString(camera2.getName());
   // } else if (m_alphajoystick.getTriggerPressed()) {
    //  System.out.println("Setting camera 1");
    //  cameraSelection.setString(camera1.getName());
   //}

    //actuateIntake();
    
   // actuateFlywheel();

   // actuateOutTake();

   // actuateDoubleSolenoid();

  }

  public void actuateIntake() { // Controls PWM for inTake clockwise 
  
  if(m_alphaGamepad.getRawButtonPressed(6)) {
    inTakeSPX.set(1.0);
   } else if(m_alphaGamepad.getRawButtonReleased(6)) {
     inTakeSPX.set(0);
     // Left bumper button
   }
  }

  public void actuateFlywheel() { // Controls PWM for shooter

  if(m_alphaGamepad.getRawButtonPressed(5)) {
    flyWheelSPX.set(1.0);
   } else if(m_alphaGamepad.getRawButtonReleased(5)) {
    flyWheelSPX.set(0);
    // right bumper button
   }

  }

  public void actuateOutTake() { // This will rotate PWM counter-clockwise

    if(m_alphaGamepad.getRawButtonPressed(1)) {
      inTakeSPX.set(-1.0);
    } else if (m_alphaGamepad.getRawButtonReleased(1)) {
      inTakeSPX.set(0);
    }
  }

  public void actuateDoubleSolenoid() {

    if(m_alphaGamepad.getRawButtonPressed(2)) {
      actuateDoubleSolenoid.set(DoubleSolenoid.Value.kForward); 
    } else if (m_alphaGamepad.getRawButtonPressed(3)) {
      actuateDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }
}