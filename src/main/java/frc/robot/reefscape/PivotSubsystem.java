package frc.robot.reefscape;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants.PivotConstants;
import frc.robot.subsystems.ThroughboreEncoder;

public class PivotSubsystem extends ReefscapeBaseSubsystem {
    private final TalonFX motor = new TalonFX(PivotConstants.motorId);
    // TODO use throughbore encoder for pivot!!!
    // private final ThroughboreEncoder pivotEncoder = new ThroughboreEncoder(
    //     new DigitalInput(PivotConstants.motorEncoderAChannel),
    //     new DigitalInput(PivotConstants.motorEncoderBChannel),
    //     new DigitalInput(PivotConstants.motorEncoderPWMChannel), 1);

    public PivotSubsystem() {
        super(false, true);

        Slot0Configs motorConf = new Slot0Configs()
            .withGravityType(GravityTypeValue.Arm_Cosine)
            .withKG(PivotConstants.motorkG)
            .withKP(PivotConstants.motorPidConstants.kP)
            .withKI(PivotConstants.motorPidConstants.kI)
            .withKD(PivotConstants.motorPidConstants.kD);
        motor.getConfigurator().apply(motorConf);
    }

    @Override
    void setVoltage(Voltage voltage) {
        motor.setControl(new VoltageOut(voltage));
    }
    @Override
    Voltage getVoltage() {
        return motor.getMotorVoltage().getValue();
    }
    @Override
    double getParameterFromArray(int stateIdx) {
        return PivotConstants.anglesDegrees[stateIdx];
    }
    @Override
    void setReferencePosition(double position) {
        motor.setControl(new PositionVoltage(position));
    }
    @Override
    void setReferenceVelocity(double velocity) {
        motor.setControl(new VelocityVoltage(velocity));
    }
    @Override
    double getPosition() {
        return motor.getPosition().getValueAsDouble();
    }
    @Override
    double getVelocity() {
        return motor.getVelocity().getValueAsDouble();
    }
}
