public class Palindrome {
    /** Given a String, wordToDeque should return a Deque
     * where the characters appear in the same order
     * as in the String. */
    public Deque<Character> wordToDeque(String word) {
        // Creating array and Storing the array returned by toCharArray()
        char[] chArray = word.toCharArray();
        Deque<Character> chDeque = new ArrayDeque<>();
        for (int i = 0; i < chArray.length; i += 1) {
            chDeque.addLast(chArray[i]);
        }
        return chDeque;
    }

    private boolean isPalindrome(Deque<Character> L) {
        if (L.isEmpty() || L.size() == 1) {
            return true;
        }
        char first = L.removeFirst();
        char last = L.removeLast();
        if (first == last) {
            return isPalindrome(L);
        }
        return false;
    }
    /** Return true if the given word is a palindrome, and false otherwise */
    public boolean isPalindrome(String word) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        Deque<Character> L = wordToDeque(word);
        return isPalindrome(L);
    }

    private boolean isPalindrome(Deque<Character> L, CharacterComparator cc) {
        if (L.isEmpty() || L.size() == 1) {
            return true;
        }
        char first = L.removeFirst();
        char last = L.removeLast();
        if (cc.equalChars(first, last)) {
            return isPalindrome(L, cc);
        }
        return false;
    }
    /** Return true if the word is a palindrome
     * according to the character comparison test
     * provided by the CharacterComparator passed in as argument cc.*/
    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        Deque<Character> L = wordToDeque(word);
        return isPalindrome(L, cc);
    }
}
