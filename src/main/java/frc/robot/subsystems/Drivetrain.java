// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */

  private CANSparkMax flDriveMotor;
  private CANSparkMax frDriveMotor;
  private CANSparkMax blDriveMotor;
  private CANSparkMax brDriveMotor;

  private CANSparkMax flRotationMotor;
  private CANSparkMax frRotationMotor;
  private CANSparkMax blRotationMotor;
  private CANSparkMax brRotationMotor;

  private SwerveDriveKinematics kinematics;

  public Drivetrain() {

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

    // Locations for the swerve drive modules relative to the robot center.
    Translation2d frontLeftLocation = new Translation2d(0.381, 0.381);
    Translation2d frontRightLocation = new Translation2d(0.381, -0.381);
    Translation2d backLeftLocation = new Translation2d(-0.381, 0.381);
    Translation2d backRightLocation = new Translation2d(-0.381, -0.381);

    // Creating my kinematics object using the module locations
    kinematics = new SwerveDriveKinematics(
      frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation
    );
  }
//fontion qui va faire tourner une roue à une certaine vitesse linéaire en m/s
  public double setLinearVelocity(double targetSpeed, CANSparkMax driveMotor){
    double currentMotorSpeed = driveMotor.getEncoder().getVelocity() / 6.67 * Math.PI * 4 * 2.54 / 100 / 60;
    //ajouter un PID qui calcule la vitesse à output dans le moteur pour atteindre le targetSpeed de manière optimale

    PIDController motorOutputPID = new PIDController(0, 0, 0); //JSP COMMENT FIX L'ERREUR RESSOURCE LEAK
    //AUCUNE IDÉE SI CE CODE LÀ VA MARCHER, JSP CE QUE JE FAIS
    double motorOutput = MathUtil.clamp(motorOutputPID.calculate(currentMotorSpeed, targetSpeed), -1, 1);
    
    return motorOutput;
  }
//fontion qui va orienter la roue vers un certain angle en rotation2d
  public double goToAngle(Rotation2d targetAngle, CANSparkMax rotationMotor){
    double currentAngleRAD = Math.asin(Math.sin(rotationMotor.getEncoder().getPosition() / 10 * 6 / 5 * 2 * Math.PI));
    double targetAngleRAD = targetAngle.getRadians(); //Vérifier l'échelle de radians de rotations2d si c'est 0 à 2π ou -π à π
    //si l'échelle prend de -π à π utiliser MathUtil.angleModulus pour convertir le currentAngleRAD au bon range
    //ajouter un PID qui calcule la vitesse à output dans le moteur pour atteindre le targetAngle de manière optimale
    PIDController motorOutputPID = new PIDController(0, 0, 0); //JSP COMMENT FIX L'ERREUR RESSOURCE LEAK
    motorOutputPID.enableContinuousInput(0, Math.PI * 2); //valider le range avec l'échelle de radians de rotations2d
    //VÉRIFIER LES MATHS POUR VOIR S'IL N'Y AURA PAS DE PROBLÈMES LORSQUE LE TARGET EST 7π/4 ET QUE LE CURRENT EST À π/4
    double motorOutput = MathUtil.clamp(motorOutputPID.calculate(currentAngleRAD, targetAngleRAD), -1, 1);

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
    ChassisSpeeds speeds = new ChassisSpeeds(x, y, r);   //VÉRIFIER S'IL N'Y A PAS UN MEILLEUR ENDROIT POUR CRÉER LES OBJETS
    // Convert to module states
    SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(speeds);
    // Front left module state
    SwerveModuleState frontLeft = moduleStates[0];
    SwerveModuleState frontLeftOptimized = SwerveModuleState.optimize(frontLeft, new Rotation2d(m_turningEncoder.getDistance()));
    // Front right module state
    SwerveModuleState frontRight = moduleStates[1];
    SwerveModuleState frontRightOptimized = SwerveModuleState.optimize(frontRight, new Rotation2d(m_turningEncoder.getDistance()));
    // Back left module state
    SwerveModuleState backLeft = moduleStates[2];
    SwerveModuleState backLeftOptimized = SwerveModuleState.optimize(backLeft, new Rotation2d(m_turningEncoder.getDistance()));
    // Back right module state
    SwerveModuleState backRight = moduleStates[3];
    SwerveModuleState backRightOptimized = SwerveModuleState.optimize(backRight, new Rotation2d(m_turningEncoder.getDistance()));

    driveOneSwerve(frontLeftOptimized, flRotationMotor, flDriveMotor);
    driveOneSwerve(frontRightOptimized, frRotationMotor, frDriveMotor);
    driveOneSwerve(backLeftOptimized, blRotationMotor, blDriveMotor);
    driveOneSwerve(backRightOptimized, brRotationMotor, brDriveMotor);
  }

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
