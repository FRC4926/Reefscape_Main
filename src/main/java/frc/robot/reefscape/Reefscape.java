package frc.robot.reefscape;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ReefscapeState;

public class Reefscape {
    private ReefscapeState currentState = ReefscapeState.Home;
    private ReefscapeState lastLevel = ReefscapeState.Level2;

    private final ElevatorSubsystem elevator = new ElevatorSubsystem();
    private final PivotSubsystem    pivot    = new PivotSubsystem();
    private final IntakeSubsystem   intake   = new IntakeSubsystem();

    public Reefscape() {
    }

    // Applies `state` to elevator, pivot, and intake
    public void applyState(ReefscapeState state) {
        applyState(state, true, true, true);
    }
    // Applies the current state of the subsystem to the elevator, pivot, and/or intake
    public void applyState(boolean applyToElevator, boolean applyToPivot, boolean applyToIntake) {
        applyState(currentState, applyToElevator, applyToPivot, applyToIntake);
    }
    public void applyState(ReefscapeState state, boolean applyToElevator, boolean applyToPivot, boolean applyToIntake) {

    }
    public Command applyStateCommand(ReefscapeState state) {
        return applyStateCommand(state, true, true, true);
    }
    public Command applyStateCommand(Supplier<ReefscapeState> stateSupplier) {
        return applyStateCommand(stateSupplier, true, true, true);
    }
    public Command applyStateCommand(ReefscapeState state, boolean applyToElevator, boolean applyToPivot, boolean applyToIntake) {
        currentState = state;
        if (state.isLevel())
            lastLevel = state;

        ParallelCommandGroup command = new ParallelCommandGroup();
        if (applyToElevator)
            command.addCommands(elevator.setStateCommand(state));
        if (applyToPivot)
            command.addCommands(pivot.setStateCommand(state));
        if (applyToIntake)
            command.addCommands(intake.setStateCommand(state));
        return command;
    }
    public Command applyStateCommand(Supplier<ReefscapeState> stateSupplier, boolean applyToElevator, boolean applyToPivot, boolean applyToIntake) {
        ParallelCommandGroup command = new ParallelCommandGroup();
        if (applyToElevator)
            command.addCommands(elevator.setStateCommand(stateSupplier));
        if (applyToPivot)
            command.addCommands(pivot.setStateCommand(stateSupplier));
        if (applyToIntake)
            command.addCommands(intake.setStateCommand(stateSupplier));
        return command;
    }
    public Command applyStateCommand(boolean applyToElevator, boolean applyToPivot, boolean applyToIntake) {
        return applyStateCommand(currentState, applyToElevator, applyToPivot, applyToIntake);
    }
    public ReefscapeState getState() {
        return currentState;
    }
    public ReefscapeState getElevatorState() {
        return elevator.getState();
    }
    public ReefscapeState getPivotState() {
        return pivot.getState();
    }
    public ReefscapeState getIntakeState() {
        return intake.getState();
    }

    public ReefscapeState getLastLevel() {
        return lastLevel;
    }

    public boolean coralInInnerIntake() {
        return intake.coralInInnerIntake();
    }
    public boolean coralInOuterIntake() {
        return intake.coralInInnerIntake();
    }

    public void toggleElevatorManual() {
        elevator.toggleManualControl();
    }
    public Command toggleElevatorManualCommand() {
        return elevator.toggleManualControlCommand();
    }
    public Trigger elevatorIsManual() {
        return new Trigger(() -> elevator.isManualControl());
    }
    public Command elevatorMoveWithVelocityCommand(double velocity) {
        return elevator.setManualVelocityCommand(() -> velocity);
    }
    public Command elevatorMoveWithVelocityCommand(DoubleSupplier velocity) {
        return elevator.setManualVelocityCommand(velocity);
    }
}
