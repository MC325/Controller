public class PIDController {
    double sensorValue;
    double setPoint;

    double integral = 0;
    double previousTimeMs;

    public PIDController() {
    }

    public void setSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }

//    private void setSensorValue(double sensorValue) {
//        //Sets prevents sensorValue from going over or under the maximum/minimum value
//        bounds(-1.0,1.0,sensorValue);
//        this.sensorValue = sensorValue;
//    }

    public double updateControlValue(double sensorValue, double currentTimeMs) {
        this.sensorValue = bounds(-1.0, 1.0, sensorValue);

        double elapsedTimeSec = (currentTimeMs - previousTimeMs) / 1000;
        double error = setPoint - this.sensorValue;
        integral += error * elapsedTimeSec;
        previousTimeMs = currentTimeMs;

        double proportional = error / 2.0;

        double controlValue = proportional + integral;

        return bounds(-1.0, 1.0, controlValue);
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
}
