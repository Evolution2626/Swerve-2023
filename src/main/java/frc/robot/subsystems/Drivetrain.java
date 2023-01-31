// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */

  double currentAngleRAD;
  double targetAngleRAD;

  private VictorSP flDriveMotor;
  private VictorSP frDriveMotor;
  private VictorSP blDriveMotor;
  private VictorSP brDriveMotor;

  private VictorSP flRotationMotor;
  private VictorSP frRotationMotor;
  private VictorSP blRotationMotor;
  private VictorSP brRotationMotor;

  private SerialPort serialPort;

  private SwerveDriveKinematics kinematics;

  public PIDController motorOutputPIDRotation;
  public PIDController motorOutputPIDDrive;

  public Drivetrain() {

    motorOutputPIDRotation = new PIDController(0.1, 0.05, 0.001); 
    motorOutputPIDRotation.enableContinuousInput(-Math.PI, Math.PI);

    motorOutputPIDDrive = new PIDController(0.1, 0, 0); //JSP COMMENT FIX L'ERREUR RESSOURCE LEAK

    serialPort = new SerialPort(115200, Port.kMXP);

    flDriveMotor = new VictorSP(Constants.CAN.FL_DRIVE_MOTOR);
    frDriveMotor = new VictorSP(Constants.CAN.FR_DRIVE_MOTOR);
    blDriveMotor = new VictorSP(Constants.CAN.BL_DRIVE_MOTOR);
    brDriveMotor = new VictorSP(Constants.CAN.BR_DRIVE_MOTOR);

    flRotationMotor = new VictorSP(Constants.CAN.FL_ROTATION_MOTOR);
    frRotationMotor = new VictorSP(Constants.CAN.FR_ROTATION_MOTOR);
    blRotationMotor = new VictorSP(Constants.CAN.BL_ROTATION_MOTOR);
    brRotationMotor = new VictorSP(Constants.CAN.BR_ROTATION_MOTOR);

    flDriveMotor.setInverted(false);
    frDriveMotor.setInverted(false);
    blDriveMotor.setInverted(false);
    brDriveMotor.setInverted(false);

    flRotationMotor.setInverted(false);
    frRotationMotor.setInverted(false);
    blRotationMotor.setInverted(false);
    brRotationMotor.setInverted(false);



    
    // Locations for the swerve drive modules relative to the robot center.
    Translation2d frontLeftLocation = new Translation2d(0.3, 0.3);
    Translation2d frontRightLocation = new Translation2d(0, -0);
    Translation2d backLeftLocation = new Translation2d(-0, 0);
    Translation2d backRightLocation = new Translation2d(-0, -0);

    // Creating my kinematics object using the module locations
    kinematics = new SwerveDriveKinematics(
      frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation
    );
  }

  public int readEncoderValue(int encoderNumber){
    String rawSerialValue = serialPort.readString();
    String parsedValue[] = rawSerialValue.split(":");
    parsedValue = parsedValue[parsedValue.length - 1].split(",");
    
    return Integer.parseInt(parsedValue[encoderNumber]);
  }


//fontion qui va faire tourner une roue à une certaine vitesse linéaire en m/s
  public double setLinearVelocity(double targetSpeed){
    double currentMotorSpeed = a;
    //ajouter un PID qui calcule la vitesse à output dans le moteur pour atteindre le targetSpeed de manière optimale
    double motorOutput = MathUtil.clamp(motorOutputPIDDrive.calculate(currentMotorSpeed, targetSpeed), -1, 1);
    
	return motorOutput;
  }
//fontion qui va orienter la roue vers un certain angle en rotation2d
  public double goToAngle(Rotation2d targetAngle, int encoderNumber){
    currentAngleRAD = MathUtil.angleModulus(readEncoderValue(encoderNumber) * 2 * Math.PI / 4096);
    targetAngleRAD = targetAngle.getRadians();
    //ajouter un PID qui calcule la vitesse à output dans le moteur pour atteindre le targetAngle de manière optimale
    double motorOutput = MathUtil.clamp(motorOutputPIDRotation.calculate(currentAngleRAD, targetAngleRAD), -1, 1);
    
    return motorOutput;
  }
//fonction qui va gérer la position et la vitesse d'une roue
  public void driveOneSwerve(SwerveModuleState moduleState, VictorSP rotationMotor, VictorSP driveMotor, int encoderNumber){
    driveMotor.set(setLinearVelocity(moduleState.speedMetersPerSecond));
    rotationMotor.set(goToAngle(moduleState.angle, encoderNumber));
  }

  public void driveSwerve(double x, double y, double r){
    // Example chassis speeds: 1 meter per second forward, 3 meters
    // per second to the left, and rotation at 1.5 radians per second
    // counterclockwise.
    ChassisSpeeds speeds = new ChassisSpeeds(x, y, r); 
    // Convert to module states
    SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(speeds);
    // Front left module state
    SwerveModuleState frontLeft = moduleStates[0];
    SwerveModuleState frontLeftOptimized = SwerveModuleState.optimize(frontLeft, 
		  new Rotation2d(a));
    // Front right module state
    SwerveModuleState frontRight = moduleStates[1];
    SwerveModuleState frontRightOptimized = SwerveModuleState.optimize(frontRight, 
      new Rotation2d(a));
    // Back left module state
    SwerveModuleState backLeft = moduleStates[2];
    SwerveModuleState backLeftOptimized = SwerveModuleState.optimize(backLeft, 
      new Rotation2d(a));
    // Back right module state
    SwerveModuleState backRight = moduleStates[3];
    SwerveModuleState backRightOptimized = SwerveModuleState.optimize(backRight, 
      new Rotation2d(a));

    driveOneSwerve(frontLeftOptimized, flRotationMotor, flDriveMotor, 0);
    driveOneSwerve(frontRightOptimized, frRotationMotor, frDriveMotor, 1);
    driveOneSwerve(backLeftOptimized, blRotationMotor, blDriveMotor, 2);
    driveOneSwerve(backRightOptimized, brRotationMotor, brDriveMotor, 3);
  }

  public void resetEncoders(){
    

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Current angle: ", currentAngleRAD/Math.PI);
    SmartDashboard.putNumber("Target Angle: ", targetAngleRAD/Math.PI);
  }
}
