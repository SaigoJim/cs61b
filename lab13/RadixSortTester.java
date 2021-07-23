import static org.junit.Assert.*;
import org.junit.Test;

public class RadixSortTester {
    @Test
    public void testLSD() {
        String[] asciis = new String[]{"abcd", "zsdf", "zzz", "asdggh"};
        String[] sorted = RadixSort.sort(asciis);
    }
}
