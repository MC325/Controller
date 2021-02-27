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

        controller.setSetPoint(0.5);

        //Integral proportional to time (fixed sensorValue)
        for (int i = 1; i <= 20; i++) {
            controller.updateControlValue(0,i * 30);
            System.out.println(controller);
        }
        System.out.println("New Loop");
        for (int i = 1; i <= 40; i++) {
            controller.updateControlValue(i * 0.05,600 + (i * 30));
            System.out.println(controller);
        }
    }
}
