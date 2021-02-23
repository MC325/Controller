import org.junit.Test;

import static org.junit.Assert.*;

public class PIDControllerTest {
    @Test
    public void ProportionalTest () {
        PIDController controller = new PIDController();

        //Moves to the left, negative speed value
        controller.setSetPoint(-0.3);
        assertEquals(-0.3, controller.updateControlValue(0.3), 0.00001);

        //Moves to the same location, no value
        controller.setSetPoint(0.3);
        assertEquals(0, controller.updateControlValue(0.3), 0.00001);

        //Moves to the right, positive speed value
        controller.setSetPoint(0.6);
        assertEquals(0.15, controller.updateControlValue(0.3), 0.00001);

        //Movement test
        controller.setSetPoint(-0.3);

        assertEquals(-0.3, controller.updateControlValue(0.3), 0.00001);

        assertEquals(-0.15, controller.updateControlValue(0), 0.00001);

        assertEquals(-0.075, controller.updateControlValue(-0.15), 0.00001);

        //Greater than 1
        controller.setSetPoint(0);

        assertEquals(-0.5, controller.updateControlValue(1.5), 0.00001);

        //Less than -1

        assertEquals(0.5, controller.updateControlValue(-1.5), 0.00001);
    }
}
