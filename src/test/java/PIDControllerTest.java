import org.junit.Test;

import static org.junit.Assert.*;

public class PIDControllerTest {
    @Test
    public void helloTest () {
        PIDController controller = new PIDController();
        assertEquals("Hello world!", controller.hello());
    }
}
