public class PIDController {
    double sensorValue;
    double setPoint;

    double proportional;
    double integral = 0;
    double controlValue;
    double error;

    double previousTimeMs;

    //Gain values: change to increase/decrease the influence each part of the PID controller has on the output
    double proportionalGain = 1.0;
    double integralGain = 1.0;
    double derivativeGain = 1.0;

    public void setSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public double updateControlValue(double sensorValue, double currentTimeMs) {

        //Bounds the received sensorValue to between -1.0 and 1.0
        sensorValue = bounds(-1.0, 1.0, sensorValue);

        //The error term: the difference between the setPoint and the current position (sensorValue)
        error = setPoint - sensorValue;

        //The proportional component: the maximum speed from the proportional component is 1.0 * proportionalGain
        proportional = error / 2.0;

        //The integral component: gets the change in time in seconds, multiplies it by the error (to scale the value), and adds it to the running total of the integral.
        double elapsedTimeSec = (currentTimeMs - previousTimeMs) / 1000;
        double integralDelta = error * elapsedTimeSec;

        //Sums up all components of the PID controller and outputs the raw value
        double preclampControlValue = proportional * proportionalGain + (integral + integralDelta) * integralGain;

        //Running checks for the integral component to see if:
        //A) The value is saturating
        //B) The total integral and the added integral are the same sign
        if (Math.abs(preclampControlValue) > 1.0 && Math.signum(error) == Math.signum(preclampControlValue)) {
            integralDelta = 0;
        }

        integral += integralDelta;
        previousTimeMs = currentTimeMs;
        controlValue = bounds(-1.0, 1.0, preclampControlValue);
        return controlValue;
    }

    public double getIntegral() {
        return integral;
    }

    private static double bounds(double min, double max, double value) {
        if (value < min) {
            value = min;
        } else if (value > max) {
            value = max;
        }
        return value;
    }

    public String toString () {
        return String.format(Locale.ENGLISH,
                "PID: E:%.2f C:%.2f P:%.2f I:%.2f D:%.2f",
                error,
                controlValue,
                proportional,
                integral,
                0.0
        );
    }
}
