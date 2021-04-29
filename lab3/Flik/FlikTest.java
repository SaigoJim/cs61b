import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {
    @Test
    public void testIsSameNumber() {
        int i = 0;
        int j = 0;
        while (i < 100) {
            boolean result = Flik.isSameNumber(i, j);
            assertTrue(result);
            i += 1;
            j += 1;
        }
    }
}
