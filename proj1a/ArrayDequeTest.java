public class ArrayDequeTest {
    public static void testAddFirst() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        L.addFirst(1);
        L.addFirst(2);
        L.addFirst(3);
        //System.out.println(L.getFirst());
        L.removeFirst();
        //System.out.println(L.getFirst());
        L.removeFirst();
        L.removeFirst();
        //System.out.println(L.getFirst());
    }

    public static void testAddLast() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        L.addLast(1);
        L.addLast(2);
        L.addLast(3);
        //System.out.println(L.getLast());
        L.removeLast();
        //System.out.println(L.getLast());
        L.removeLast();
        L.removeLast();
        //System.out.println(L.getLast());
    }

    public static void testMix() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        L.addLast(1);
        L.addFirst(2);
        L.addLast(3);
        //System.out.println(L.getFirst());
        L.removeLast();
        //System.out.println(L.getLast());
        L.addFirst(99);
        //System.out.println(L.getFirst());
    }

    public static void testResize() {
        /*ArrayDeque<Integer> L = new ArrayDeque<>();
        for (int i = 0; i < 15; i += 1) {
            L.addLast(i);
        }
        L = null;*/

        ArrayDeque<Integer> S = new ArrayDeque<>();
        for (int i = 0; i < 80; i += 1) {
            S.addLast(i);
        }

        S.printDeque();
        for (int i = 80; i > 0; i -= 1) {
            S.removeLast();
        }

        for (int i = 0; i < 80; i += 1) {
            S.addLast(i);
        }
    }

    public static void testResize2() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        for (int i = 0; i < 32; i += 1) {
            L.addLast(i);
        }

        for (int i = 0; i < 6; i += 1) {
            L.removeFirst();
        }

        for (int i = 32; i >= 6; i -= 1) {
            L.removeLast();
        }
    }
    public static void main(String[] args) {
        //testAddFirst();
        //testAddLast();
        //testMix();
        testResize2();
        //int i = -1 % 8;
    }
}
