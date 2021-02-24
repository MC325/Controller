import org.junit.Test;

import static org.junit.Assert.*;

public class PIDControllerTest {
    @Test
    public void proportionalTest () {
        PIDController controller = new PIDController();

        //Moves to the left, negative speed value
        controller.setSetPoint(-0.3);
        assertEquals(-0.3, controller.updateControlValue(0.3, 0), 0.00001);

        //Moves to the same location, no value
        controller.setSetPoint(0.3);
        assertEquals(0, controller.updateControlValue(0.3, 0), 0.00001);

        //Moves to the right, positive speed value
        controller.setSetPoint(0.6);
        assertEquals(0.15, controller.updateControlValue(0.3, 0), 0.00001);

        //Movement test
        controller.setSetPoint(-0.3);

        assertEquals(-0.3, controller.updateControlValue(0.3,0), 0.00001);

        assertEquals(-0.15, controller.updateControlValue(0,0), 0.00001);

        assertEquals(-0.075, controller.updateControlValue(-0.15,0), 0.00001);

        //Greater than 1
        controller.setSetPoint(0);

        assertEquals(-0.5, controller.updateControlValue(1.5,0), 0.00001);

        //Less than -1

        assertEquals(0.5, controller.updateControlValue(-1.5,0), 0.00001);
    }

    @Test
    public void integralTest () {
        PIDController controller = new PIDController();

        controller.setSetPoint(1);

        //Integral proportional to time (fixed sensorValue)

        for (int i = 0; i < 20; i++) {
            System.out.println(controller.updateControlValue(0,i * 30));
            System.out.println(controller.getIntegral());
        }

        assertEquals(0.5, controller.updateControlValue(0, 0), 0.00001);

        assertEquals(0.0, controller.getIntegral(),0.00001);

        assertEquals(0.53, controller.updateControlValue(0, 30), 0.00001);

        assertEquals(0.03, controller.getIntegral(),0.00001);

        assertEquals(0.53, controller.updateControlValue(0, 10000), 0.00001);

        assertEquals(0.0299999, controller.getIntegral(),0.00001);

        assertEquals(0.530999, controller.updateControlValue(0, 10001), 0.00001);

        assertEquals(0.03099, controller.getIntegral(),0.00001);
    }
}
