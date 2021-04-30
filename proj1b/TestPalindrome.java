import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome1() {
        String s1 = "";
        String s2 = "z";
        assertTrue(palindrome.isPalindrome(s1));
        assertTrue(palindrome.isPalindrome(s2));
    }

    @Test
    public void testIsPalindrome2() {
        String s = "horse";
        assertFalse(palindrome.isPalindrome(s));
    }

    @Test
    public void testIsPalindrome3() {
        String s1 = "racecar";
        String s2 = "noon";
        assertTrue(palindrome.isPalindrome(s1));
        assertTrue(palindrome.isPalindrome(s2));
    }

    @Test
    public void testIsPalindromeWithCC1() {
        String s1 = "";
        String s2 = "z";
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome(s1, cc));
        assertTrue(palindrome.isPalindrome(s2, cc));
    }

    @Test
    public void testIsPalindromeWithCC2() {
        String s1 = "flake";
        String s2 = "abb";
        String s3 = "xacefdby";
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome(s1, cc));
        assertTrue(palindrome.isPalindrome(s2, cc));
        assertTrue(palindrome.isPalindrome(s3, cc));
    }

    @Test
    public void testIsPalindromeWithCC3() {
        String s1 = "racecar";
        String s2 = "noon";
        CharacterComparator cc = new OffByOne();
        assertFalse(palindrome.isPalindrome(s1, cc));
        assertFalse(palindrome.isPalindrome(s2, cc));
    }
}

