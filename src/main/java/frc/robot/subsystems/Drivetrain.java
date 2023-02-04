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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */

  double currentAngleRAD;
  double targetAngleRAD;

  private CANSparkMax flDriveMotor;
  private CANSparkMax frDriveMotor;
  private CANSparkMax blDriveMotor;
  private CANSparkMax brDriveMotor;

  private CANSparkMax flRotationMotor;
  private CANSparkMax frRotationMotor;
  private CANSparkMax blRotationMotor;
  private CANSparkMax brRotationMotor;

  private DigitalInput flSensor;
  private DigitalInput frSensor;
  private DigitalInput blSensor;
  private DigitalInput brSensor;

  private SwerveDriveKinematics kinematics;

  public PIDController motorOutputPIDRotation;
  public PIDController motorOutputPIDDrive;

  public Drivetrain() {

    motorOutputPIDRotation = new PIDController(0.1, 0.05, 0.001); 
    motorOutputPIDRotation.enableContinuousInput(-Math.PI, Math.PI);

    motorOutputPIDDrive = new PIDController(0.1, 0, 0); //JSP COMMENT FIX L'ERREUR RESSOURCE LEAK

    flSensor = new DigitalInput(Constants.DIGITAL.FL_SENSOR);
    frSensor = new DigitalInput(Constants.DIGITAL.FR_SENSOR);
    blSensor = new DigitalInput(Constants.DIGITAL.BL_SENSOR);
    brSensor = new DigitalInput(Constants.DIGITAL.BR_SENSOR);

    flDriveMotor = new CANSparkMax(Constants.CAN.FL_DRIVE_MOTOR, MotorType.kBrushless);
    frDriveMotor = new CANSparkMax(Constants.CAN.FR_DRIVE_MOTOR, MotorType.kBrushless);
    blDriveMotor = new CANSparkMax(Constants.CAN.BL_DRIVE_MOTOR, MotorType.kBrushless);
    brDriveMotor = new CANSparkMax(Constants.CAN.BR_DRIVE_MOTOR, MotorType.kBrushless);

    flRotationMotor = new CANSparkMax(Constants.CAN.FL_ROTATION_MOTOR, MotorType.kBrushless);
    frRotationMotor = new CANSparkMax(Constants.CAN.FR_ROTATION_MOTOR, MotorType.kBrushless);
    blRotationMotor = new CANSparkMax(Constants.CAN.BL_ROTATION_MOTOR, MotorType.kBrushless);
    brRotationMotor = new CANSparkMax(Constants.CAN.BR_ROTATION_MOTOR, MotorType.kBrushless);

    flDriveMotor.setInverted(false);
    frDriveMotor.setInverted(false);
    blDriveMotor.setInverted(false);
    brDriveMotor.setInverted(false);

    flRotationMotor.setInverted(false);
    frRotationMotor.setInverted(false);
    blRotationMotor.setInverted(false);
    brRotationMotor.setInverted(false);

    flRotationMotor.setIdleMode(IdleMode.kBrake);
    frRotationMotor.setIdleMode(IdleMode.kBrake);
    blRotationMotor.setIdleMode(IdleMode.kBrake);
    brRotationMotor.setIdleMode(IdleMode.kBrake);

    flDriveMotor.setIdleMode(IdleMode.kBrake);
    frDriveMotor.setIdleMode(IdleMode.kBrake);
    blDriveMotor.setIdleMode(IdleMode.kBrake);
    brDriveMotor.setIdleMode(IdleMode.kBrake);


    // Locations for the swerve drive modules relative to the robot center.
    Translation2d frontLeftLocation = new Translation2d(0.3, 0.3);
    Translation2d frontRightLocation = new Translation2d(0.3, -0.3);
    Translation2d backLeftLocation = new Translation2d(-0.3, 0.3);
    Translation2d backRightLocation = new Translation2d(-0.3, -0.3);

    // Creating my kinematics object using the module locations
    kinematics = new SwerveDriveKinematics(
      frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation
    );
  }
//fontion qui va faire tourner une roue à une certaine vitesse linéaire en m/s
  public double setLinearVelocity(double targetSpeed, CANSparkMax driveMotor){
    double currentMotorSpeed = driveMotor.getEncoder().getVelocity() / 6.67 * Math.PI * 4 * 2.54 / 100 / 60;
    //ajouter un PID qui calcule la vitesse à output dans le moteur pour atteindre le targetSpeed de manière optimale
    //peut-être utiliser le PID inclut dans les sparkmax pour plus d'efficacité
    double motorOutput = MathUtil.clamp(motorOutputPIDDrive.calculate(currentMotorSpeed, targetSpeed), -1, 1);
    
	return motorOutput;
  }
//fontion qui va orienter la roue vers un certain angle en rotation2d
  public double goToAngle(Rotation2d targetAngle, CANSparkMax rotationMotor){
    currentAngleRAD = MathUtil.angleModulus(rotationMotor.getEncoder().getPosition() / 10 * 6 / 5 * 2 * Math.PI);
    targetAngleRAD = targetAngle.getRadians(); 
    //ajouter un PID qui calcule la vitesse à output dans le moteur pour atteindre le targetAngle de manière optimale
     //peut-être utiliser le PID inclut dans les sparkmax pour plus d'efficacité
    double motorOutput = MathUtil.clamp(motorOutputPIDRotation.calculate(currentAngleRAD, targetAngleRAD), -1, 1);
    
    return motorOutput;
  }
//fonction qui va gérer la position et la vitesse d'une roue
  public void driveOneSwerve(SwerveModuleState moduleState, CANSparkMax rotationMotor, CANSparkMax driveMotor){
    driveMotor.set(setLinearVelocity(moduleState.speedMetersPerSecond, driveMotor));
    rotationMotor.set(goToAngle(moduleState.angle, rotationMotor));
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
		  new Rotation2d(MathUtil.angleModulus(flRotationMotor.getEncoder().getPosition() / 10 * 6 / 5 * 2 * Math.PI)));
    // Front right module state
    SwerveModuleState frontRight = moduleStates[1];
    SwerveModuleState frontRightOptimized = SwerveModuleState.optimize(frontRight, new Rotation2d(MathUtil.angleModulus(frRotationMotor.getEncoder().getPosition() / 10 * 6 / 5 * 2 * Math.PI)));
    // Back left module state
    SwerveModuleState backLeft = moduleStates[2];
    SwerveModuleState backLeftOptimized = SwerveModuleState.optimize(backLeft, new Rotation2d(MathUtil.angleModulus(blRotationMotor.getEncoder().getPosition() / 10 * 6 / 5 * 2 * Math.PI)));
    // Back right module state
    SwerveModuleState backRight = moduleStates[3];
    SwerveModuleState backRightOptimized = SwerveModuleState.optimize(backRight, new Rotation2d(MathUtil.angleModulus(brRotationMotor.getEncoder().getPosition() / 10 * 6 / 5 * 2 * Math.PI)));

    driveOneSwerve(frontLeftOptimized, flRotationMotor, flDriveMotor);
    driveOneSwerve(frontRightOptimized, frRotationMotor, frDriveMotor);
    driveOneSwerve(backLeftOptimized, blRotationMotor, blDriveMotor);
    driveOneSwerve(backRightOptimized, brRotationMotor, brDriveMotor);
  }

  public void resetEncoders(int encoder){
    flRotationMotor.getEncoder().setPosition(0);
    frRotationMotor.getEncoder().setPosition(0);
    blRotationMotor.getEncoder().setPosition(0);
    brRotationMotor.getEncoder().setPosition(0);
    switch (encoder) {
      case 0:
        flRotationMotor.getEncoder().setPosition(0);
        break;
      case 1:
      frRotationMotor.getEncoder().setPosition(0);
        break;
      case 2:
      blRotationMotor.getEncoder().setPosition(0);
        break;
      case 3:
      brRotationMotor.getEncoder().setPosition(0);
        break;
      default:
        break;
    }
  }

  public boolean getDigitalInputs(int sensor){
    boolean[] inputs = {flSensor.get(), frSensor.get(), blSensor.get(),brSensor.get()};
    return inputs[sensor];
  }

  public void setRotationMotorSpeed(int motor, double speed){
    switch (motor) {
      case 0:
        flRotationMotor.set(speed);
        break;
      case 1:
        frRotationMotor.set(speed);
        break;
      case 2:
        blRotationMotor.set(speed);
        break;
      case 3:
        brRotationMotor.set(speed);
        break;
      default:
        break;
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }
}
