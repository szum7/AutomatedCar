package hu.oe.nik.szfmv.inputinterface;

/**
 * <h1>Class for car component state calculations</h1>
 *
 * @author danijanos
 */
public class CarComponentStateCalculator implements ICalculations {

    // Higher value means less sensitive but quicker rotation
    private static final int STEERING_WHEEL_ROTATION_SENSITIVITY = 1;
    // Higher value means more pressure, hence the pedal faster reaches its maximum state
    private static final int PEDAL_PRESSURE_SENSITIVITY = 1;

    public int turnTheSteeringwheelLeft(int currentSteeringwheelState) {
        int output;
        if (currentSteeringwheelState > MAX_LEFT_STEERING_WHEEL_STATE) {
            output = currentSteeringwheelState - STEERING_WHEEL_ROTATION_SENSITIVITY;
        } else {
            output = MAX_LEFT_STEERING_WHEEL_STATE;
        }
        return output;
    }

    public int turnTheSteeringwheelRight(int currentSteeringwheelState) {
        int output;
        if (currentSteeringwheelState < MAX_RIGHT_STEERING_WHEEL_STATE) {
            output = currentSteeringwheelState + STEERING_WHEEL_ROTATION_SENSITIVITY;
        } else {
            output = MAX_RIGHT_STEERING_WHEEL_STATE;
        }
        return output;
    }

    public int addGas(int currentGaspedalState) {
        return pushPedal(currentGaspedalState);
    }

    public int applyingBreak(int currentBreakpedalState) {
        return pushPedal(currentBreakpedalState);
    }

    private int pushPedal(int currentpedalState) {
        int output;
        if (currentpedalState < MAX_PEDAL_STATE) {
            output = currentpedalState + PEDAL_PRESSURE_SENSITIVITY;
        } else {
            output = MAX_PEDAL_STATE;
        }
        return output;
    }
}
