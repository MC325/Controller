public class PIDController {
    double sensorValue;
    double setPoint;

    public PIDController() {
    }

    public void setSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    private void setSensorValue(double sensorValue) {
        //Sets prevents sensorValue from going over or under the maximum/minimum value
        if (sensorValue < -1.0) {
            sensorValue = -1.0;
        } else if (sensorValue > 1.0) {
            sensorValue = 1.0;
        }
        this.sensorValue = sensorValue;
    }

    public double updateControlValue(double sensorValue) {
        setSensorValue(sensorValue);
        return (setPoint - this.sensorValue) / 2.0;
    }
}
