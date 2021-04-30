import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    @Test
    public void testEqualChars1() {
        CharacterComparator offByN = new OffByN(1);
        assertTrue(offByN.equalChars('a', 'b'));
        assertTrue(offByN.equalChars('%', '&'));
        assertTrue(offByN.equalChars('x', 'y'));
    }

    @Test
    public void testEqualChars2() {
        CharacterComparator offByN = new OffByN(5);
        assertFalse(offByN.equalChars('a', 'a'));
        assertFalse(offByN.equalChars('a', 'c'));
        assertFalse(offByN.equalChars('%', '^'));
        assertFalse(offByN.equalChars('x', 'z'));
    }

    @Test
    public void testEqualChars3() {
        CharacterComparator offByN = new OffByN(5);
        assertTrue(offByN.equalChars('a', 'f'));
        assertTrue(offByN.equalChars('b', 'g'));
        assertTrue(offByN.equalChars('e', 'j'));
    }
}
