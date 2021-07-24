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
    @Test
    public void testLSD1() {
        String[] asciis = new String[]{"56", "112", "94", "4", "9", "82", "394", "80"};
        String[] sorted = RadixSort.sort(asciis);
        Arrays.sort(asciis);
        assertArrayEquals(asciis, sorted);
    }
    @Test
    public void testLSD2() {
        String[] asciis = new String[]{"94", "9"};
        String[] sorted = RadixSort.sort(asciis);
        Arrays.sort(asciis);
        assertArrayEquals(asciis, sorted);
    }
    @Test
    public void testLSD3() {
        String[] asciis = new String[]{"  ", "      ", "    ", " "};
        String[] sorted = RadixSort.sort(asciis);
        Arrays.sort(asciis);
        assertArrayEquals(asciis, sorted);
    }
    @Test
    public void testStringCompareTo() {
        int r = "zzz".compareTo("zsdf");
        String a = "zzz";
        String b = "zsdf";
        int d = a.compareTo(b);
        assertTrue(r > 0);
    }
    @Test
    public void testMSD() {
        String[] asciis = new String[]{"abcd", "zsdf", "zzz", "asdggh"};
        String[] sorted = RadixSort.msd(asciis);
        Arrays.sort(asciis);
        assertArrayEquals(asciis, sorted);
    }
    @Test
    public void testMSD1() {
        String[] asciis = new String[]{"56", "112", "94", "4", "9", "82", "394", "80"};
        String[] sorted = RadixSort.msd(asciis);
        Arrays.sort(asciis);
        assertArrayEquals(asciis, sorted);
    }
    @Test
    public void testMSD2() {
        String[] asciis = new String[]{"94", "9"};
        String[] sorted = RadixSort.msd(asciis);
        Arrays.sort(asciis);
        assertArrayEquals(asciis, sorted);
    }
}
