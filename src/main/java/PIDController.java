public class PIDController {
    double sensorValue;
    double setPoint;

    public String hello () {
        return "Hello world!";
    }

    public PIDController (double sensorValue) {
        if (sensorValue < -1.0) {
            sensorValue = -1.0;
        } else if (sensorValue > 1.0) {
            sensorValue = 1.0;
        }
        this.sensorValue = sensorValue;
    }

    public void setSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public double getControlValue() {
        return (setPoint - sensorValue)/2.0;
    }
}
