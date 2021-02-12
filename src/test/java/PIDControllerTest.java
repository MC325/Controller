import org.junit.Test;

import static org.junit.Assert.*;

public class PIDControllerTest {
    @Test
    public void helloTest () {
        PIDController controller = new PIDController(1);
        assertEquals("Hello world!", controller.hello());
    }

    @Test
    public void ProportionalTest () {
        PIDController controller = new PIDController(1);
        controller.setSetPoint(0.3);
        assertEquals(-0.35, controller.getControlValue(), 0.00001);
    }
}
