public class OffByN implements CharacterComparator {
    private int offValue;
    public OffByN(int N) {
        offValue = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        x = Character.toLowerCase(x);
        y = Character.toLowerCase(y);
        return Math.abs(x - y) == offValue;
    }
}
