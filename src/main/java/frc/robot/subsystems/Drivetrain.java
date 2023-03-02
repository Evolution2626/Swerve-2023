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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.util.EncoderValues;
import frc.util.Range;

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
  
  private SwerveDriveKinematics kinematics;

  public PIDController motorOutputPIDRotation;
  public PIDController motorOutputPIDDrive;

  


  public Drivetrain() {

    motorOutputPIDRotation = new PIDController(0.1, 0, 0.001); 
    motorOutputPIDRotation.enableContinuousInput(-Math.PI, Math.PI);

    motorOutputPIDDrive = new PIDController(0.1, 0, 0); 

   
    
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

    flDriveMotor.setIdleMode(IdleMode.kCoast);
    frDriveMotor.setIdleMode(IdleMode.kCoast);
    blDriveMotor.setIdleMode(IdleMode.kCoast);
    brDriveMotor.setIdleMode(IdleMode.kCoast);

    flRotationMotor.setClosedLoopRampRate(0.1);
    frRotationMotor.setClosedLoopRampRate(0.1);
    blRotationMotor.setClosedLoopRampRate(0.1);
    brRotationMotor.setClosedLoopRampRate(0.1);


    // Locations for the swerve drive modules relative to the robot center.
    Translation2d frontLeftLocation = new Translation2d(0.2, 0.31);  //0.2 0.31
    Translation2d frontRightLocation = new Translation2d(0.2, -0.31); //0.2 -0.31
    Translation2d backLeftLocation = new Translation2d(-0.2, 0.31); //-0.2 0.31
    Translation2d backRightLocation = new Translation2d(-0.2, -0.31); // -0.2 -0.31

    // Creating my kinematics object using the module locations
    kinematics = new SwerveDriveKinematics(
      frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation
    );

    
  }

  double returnEncoderAngle(int encoderNumber){
    switch (encoderNumber) {
      case 0:
        return (EncoderValues.FL_ENCODER_VALUE)/4096*2*Math.PI;
      case 1:
        return (EncoderValues.FR_ENCODER_VALUE)/4096*2*Math.PI;  
      case 2:
        return (EncoderValues.BL_ENCODER_VALUE)/4096*2*Math.PI;        
      case 3:
        return (EncoderValues.BR_ENCODER_VALUE)/4096*2*Math.PI;    
      default:
        return 0;
    }
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
  public double goToAngle(Rotation2d targetAngle, CANSparkMax rotationMotor, int encoderNumber){
    currentAngleRAD = returnEncoderAngle(encoderNumber) - Math.PI;
    targetAngleRAD = targetAngle.getRadians(); 
    SmartDashboard.putNumber("TARGET_POSITION", targetAngleRAD/Math.PI);

    //ajouter un PID qui calcule la vitesse à output dans le moteur pour atteindre le targetAngle de manière optimale
     //peut-être utiliser le PID inclut dans les sparkmax pour plus d'efficacité
    double motorOutput = MathUtil.clamp(motorOutputPIDRotation.calculate(currentAngleRAD, targetAngleRAD), -1, 1);
    motorOutput = Range.threshold(0.05, motorOutput);
    return motorOutput;
  }
//fonction qui va gérer la position et la vitesse d'une roue
  public void driveOneSwerve(SwerveModuleState moduleState, CANSparkMax rotationMotor, CANSparkMax driveMotor, int encoderNumber){
    driveMotor.set(setLinearVelocity(moduleState.speedMetersPerSecond, driveMotor));
    rotationMotor.set(goToAngle(moduleState.angle, rotationMotor, encoderNumber));
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
    SwerveModuleState frontLeftOptimized = SwerveModuleState.optimize(frontLeft, new Rotation2d(returnEncoderAngle(0) - Math.PI));
    // // Front right module state
    SwerveModuleState frontRight = moduleStates[1];
    SwerveModuleState frontRightOptimized = SwerveModuleState.optimize(frontRight, new Rotation2d(returnEncoderAngle(1) - Math.PI));
    // // Back left module state
    SwerveModuleState backLeft = moduleStates[2];
    SwerveModuleState backLeftOptimized = SwerveModuleState.optimize(backLeft, new Rotation2d(returnEncoderAngle(2) - Math.PI));
    // // Back right module state
    SwerveModuleState backRight = moduleStates[3];
    SwerveModuleState backRightOptimized = SwerveModuleState.optimize(backRight, new Rotation2d(returnEncoderAngle(3) - Math.PI));

    driveOneSwerve(frontLeftOptimized, flRotationMotor, flDriveMotor, 0);
    driveOneSwerve(frontRightOptimized, frRotationMotor, frDriveMotor, 1);
    driveOneSwerve(backLeftOptimized, blRotationMotor, blDriveMotor, 2);
    //driveOneSwerve(backRightOptimized, brRotationMotor, brDriveMotor, 3);
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("FL", EncoderValues.FL_ENCODER_VALUE);
    SmartDashboard.putNumber("FR", EncoderValues.FR_ENCODER_VALUE);
    SmartDashboard.putNumber("BL", EncoderValues.BL_ENCODER_VALUE);
    SmartDashboard.putNumber("BR", EncoderValues.BR_ENCODER_VALUE);
    
  }
}
