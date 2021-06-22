package hw4.puzzle;

/**
 * Priority queue where objects have no intrinsic priority. Instead,
 * priorities are supplied as an argument during insertion and can be
 * changed.
 */
public interface MinPQ<T> {
    /* Inserts an item with the given priority value.
    This is also known as "enqueue", or "offer". */
    void insert(T item);
    /* Returns the minimum item. Also known as "min". */
    T min();
    /* Removes and returns the minimum item. Also known as "dequeue". */
    T delMin();
    /* Changes the priority of the given item.
    The behavior if the item doesn't exist is undefined. */
    void changePriority(T item);
    /* Returns the number of items in the PQ. */
    int size();
    default boolean isEmpty() {
        return size() == 0;
    }
}
