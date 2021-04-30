import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void randomizedTestAddRemove() {
        StudentArrayDeque<Integer> S = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> L = new ArrayDequeSolution<>();

        int N = 5000;
        String message = "";
        for (int i = 0; i < N; i += 1) {
            int operationNum = StdRandom.uniform(0, 4);
            if (operationNum == 0) {
                int randVal = StdRandom.uniform(0, 100);
                S.addFirst(randVal);
                L.addFirst(randVal);
                message += "addFirst(" + randVal + ")\n";
            } else if (operationNum == 1) {
                int randVal = StdRandom.uniform(0, 100);
                S.addLast(randVal);
                L.addLast(randVal);
                message += "addLast(" + randVal + ")\n";
            } else if (L.size() != 0 && operationNum == 2) {
                Integer iS = S.removeFirst();
                Integer iL = L.removeFirst();
                message += "removeFirst()\n";
                assertEquals(message, iS, iL);
            } else if (L.size() != 0 && operationNum == 3) {

                Integer iS = S.removeLast();
                Integer iL = L.removeLast();
                message += "removeLast()\n";
                assertEquals(message, iS, iL);
            }
        }
    }
}
