import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;

public class RadixSortTester {
    @Test
    public void testLSD() {
        String[] asciis = new String[]{"abcd", "zsdf", "zzz", "asdggh"};
        String[] sorted = RadixSort.sort(asciis);
        Arrays.sort(asciis);
        assertArrayEquals(asciis, sorted);
    }
}
