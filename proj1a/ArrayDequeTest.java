public class ArrayDequeTest {
    public static void testAddFirst() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        L.addFirst(1);
        L.addFirst(2);
        L.addFirst(3);
        System.out.println(L.getFirst());
        L.removeFirst();
        System.out.println(L.getFirst());
        L.removeFirst();
        L.removeFirst();
        System.out.println(L.getFirst());
    }

    public static void testAddLast() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        L.addLast(1);
        L.addLast(2);
        L.addLast(3);
        System.out.println(L.getLast());
        L.removeLast();
        System.out.println(L.getLast());
        L.removeLast();
        L.removeLast();
        System.out.println(L.getLast());
    }

    public static void testMix() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        L.addLast(1);
        L.addFirst(2);
        L.addLast(3);
        System.out.println(L.getFirst());
        L.removeLast();
        System.out.println(L.getLast());
        L.addFirst(99);
        System.out.println(L.getFirst());
    }

    public static void testResize() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        for (int i = 0; i < 15; i += 1) {
            L.addLast(i);
        }
        L = null;

        ArrayDeque<Integer> S = new ArrayDeque<>();
        for (int i = 0; i < 32; i += 1) {
            if (i < 5) {
                S.addLast(i);
            } else if (i >= 5 && i < 18) {
                S.addFirst(i);
            } else if (i >= 18) {
                S.addLast(i);
            }
        }
    }

    public static void main(String[] args) {
        //testAddFirst();
        //testAddLast();
        //testMix();
        testResize();
        //int i = -1 % 8;
    }
}
