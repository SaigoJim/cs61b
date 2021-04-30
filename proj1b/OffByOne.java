public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        if (x >= 'A' && x <= 'Z') {
            x = Character.toLowerCase(x);
        }
        if (y >= 'A' && y <= 'Z') {
            y = Character.toLowerCase(y);
        }
        return Math.abs(x - y) == 1;
    }
}
