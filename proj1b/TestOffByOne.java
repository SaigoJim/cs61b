import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars1() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('%', '&'));
        assertTrue(offByOne.equalChars('x', 'y'));
    }

    @Test
    public void testEqualChars2() {
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('a', 'c'));
        assertFalse(offByOne.equalChars('%', '^'));
        assertFalse(offByOne.equalChars('x', 'z'));
    }

    @Test
    public void testEqualChars3() {
        assertFalse(offByOne.equalChars('A', 'b'));
        assertFalse(offByOne.equalChars('d', 'C'));
        assertFalse(offByOne.equalChars('Z', 'y'));
    }
}
