public class LinkedListDeque<T> {
    private class StuffNode {
        T item;
        StuffNode prev;
        StuffNode next;

        public StuffNode(T i, StuffNode p, StuffNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private StuffNode sentinel;
    private int size;

    private void setSentinel(T i) {
        sentinel = new StuffNode(i, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public LinkedListDeque(T i) {
        setSentinel(i);
        size = 1;
    }

    public LinkedListDeque() {
        size = 0;
    }

    public void addFirst(T item) {
        if (isEmpty()) {
            setSentinel(item);
            size += 1;
            return;
        }
        sentinel = new StuffNode(item, sentinel.prev, sentinel);
        sentinel.next.prev = sentinel;
        sentinel.prev.next = sentinel;
        size += 1;
    }

    public void addLast(T item) {
        if (isEmpty()) {
            setSentinel(item);
            size += 1;
            return;
        }
        sentinel.prev = new StuffNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T first = sentinel.item;
        if (size == 1) {
            sentinel = null;
        } else {
            sentinel = sentinel.next;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
        }
        size -= 1;
        return first;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T last = sentinel.prev.item;
        if (size == 1) {
            sentinel = null;
        } else {
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
        }
        size -= 1;
        return last;
    }

    public T get(int index) {
        if (isEmpty() || index >= size) {
            return null;
        }
        StuffNode P = sentinel;
        for (int i = 0; i < size; i += 1) {
            if (i == index) {
                break;
            }
            P = P.next;
        }
        return P.item;
    }

    public void printDeque() {
        if (isEmpty()) {
            System.out.println("");
        }
        StuffNode P = sentinel;
        do {
            System.out.print(P.item + " ");
            P = P.next;
        } while (P != sentinel);
        System.out.println();
    }

    private T getRecursive(StuffNode P, int index) {
        if (P == null) {
            return null;
        } else if (index == 0) {
            return P.item;
        }
        return getRecursive(P.next, index - 1);

    }
    public T getRecursive(int index) {
        if (isEmpty() || index >= size) {
            return null;
        }
        return getRecursive(sentinel, index);
    }
}
