public class PIDController {
    double sensorValue;
    double setPoint;

    double integralTotal = 0;
    double previousTimeMs;

    //Gain values: change to increase/decrease the influence each part of the PID controller has on the output
    double proportionalGain = 1.0;
    double integralGain = 1.0;
    double derivativeGain = 1.0;

    public void setSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public double updateControlValue(double sensorValue, double currentTimeMs) {

        //Bounds the recieved sensorValue to between -1.0 and 1.0
        this.sensorValue = bounds(-1.0, 1.0, sensorValue);

        //The error term: the difference between the setPoint and the current position (sensorValue)
        double error = setPoint - this.sensorValue;

        //The proportional component: the maximum speed from the proportional component is 1.0 * proportionalGain
        double proportional = error / 2.0;
        proportional *= proportionalGain;

        //The integral component: gets the change in time in seconds, multiplies it by the error (to scale the value), and adds it to the running total of the integral.
        double elapsedTimeSec = (currentTimeMs - previousTimeMs) / 1000;
        integralTotal += error * elapsedTimeSec;
        double integral = integralTotal * integralGain;
        previousTimeMs = currentTimeMs;

        //Sums up all components of the PID controller and outputs the bounded value
        double controlValue = proportional + integral;

        //Running checks for the integral component to see if:
        //A) The value is saturating
        //B) The total integral and the added integral are the same sign
        if (Math.abs(controlValue) > 1.0 && Math.signum(error) == Math.signum(controlValue)) {
            if (Math.signum(controlValue) < 0) {
                controlValue = -1.0;
            } else {
                controlValue = 1.0;
            }
            integralTotal = controlValue - (proportional);
        }

        return bounds(-1.0, 1.0, controlValue);
    }

    public double getIntegral() {
        return integralTotal;
    }

    private static double bounds(double min, double max, double value) {
        if (value < min) {
            value = min;
        } else if (value > max) {
            value = max;
        }
        return value;
    }
}
