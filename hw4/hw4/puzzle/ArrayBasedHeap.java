package hw4.puzzle;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class ArrayBasedHeap<T> implements MinPQ<T> {
    private Comparator<T> comparator;
    private int size;
    private int capacity = 16;
    private T[] items;

    public ArrayBasedHeap(Comparator<T> cmp) {
        comparator = cmp;
        size = 0;
        items = (T[]) new Object[capacity];
        items[0] = null;
    }

    /**
     * Returns the index of the node to the left of the node at i.
     */
    private static int leftIndex(int i) {
        return 2 * i;
    }

    /**
     * Returns the index of the node to the right of the node at i.
     */
    private static int rightIndex(int i) {
        return 2 * i + 1;
    }

    /**
     * Returns the index of the node that is the parent of the node at i.
     */
    private static int parentIndex(int i) {
        return i / 2;
    }

    @Override
    public void insert(T item) {
        if (size + 1 == items.length) {
            resize(items.length * 2);
        }

        int mostLeftPosition = size + 1;
        items[mostLeftPosition] = item;
        size += 1;
        swim(mostLeftPosition);
    }
    /**
     * Bubbles up the node currently at the given index.
     */
    private void swim(int index) {
        // Throws an exception if index is invalid. DON'T CHANGE THIS LINE.
        validateSinkSwimArg(index);

        while (index != 1 && index == smaller(index, parentIndex(index))) {
            swap(index, parentIndex(index));
            index = parentIndex(index);
        }
        return;
    }
    /**
     * Bubbles down the node currently at the given index.
     */
    private void sink(int index) {
        // Throws an exception if index is invalid. DON'T CHANGE THIS LINE.
        validateSinkSwimArg(index);

        while (index != smaller(index, smaller(leftIndex(index), rightIndex(index)))) {
            int swapIndex = smaller(leftIndex(index), rightIndex(index));
            swap(index, swapIndex);
            index = swapIndex;
        }
        return;
    }
    private boolean isInBound(int index) {
        if (index >= 1 && index <= size) {
            return true;
        }
        return false;
    }
    private int smaller(int index1, int index2) {
        if (!isInBound(index1) || items[index1] == null) {
            return index2;
        }
        if (!isInBound(index2) || items[index2] == null) {
            return index1;
        }
        if (comparator.compare(items[index1], items[index2]) < 0) {
            return index1;
        }
        return index2;
    }
    private void swap(int index1, int index2) {
        T temp = items[index1];
        items[index1] = items[index2];
        items[index2] = temp;
    }
    /**
     * Throws an exception if the index is invalid for sinking or swimming.
     */
    private void validateSinkSwimArg(int index) {
        if (index < 1) {
            throw new IllegalArgumentException("Cannot sink or swim nodes with index 0 or less");
        }
        if (index > size) {
            throw new IllegalArgumentException(
                    "Cannot sink or swim nodes with index greater than current size.");
        }
        if (items[index] == null) {
            throw new IllegalArgumentException("Cannot sink or swim a null node.");
        }
    }
    /** Helper function to resize the backing array when necessary. */
    private void resize(int cap) {
        T[] temp = (T[]) new Object[cap];
        for (int i = 1; i < this.items.length; i++) {
            temp[i] = this.items[i];
        }
        this.items = temp;
    }
    @Override
    public T min() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return items[1];
    }

    @Override
    public T delMin() {
        if (size == 0) {
            return null;
        }
        T min = min();
        if (size == 1) {
            size -= 1;
            items[1] = null;
            return min;
        }
        int rightMostSpot = size;
        swap(1, rightMostSpot);
        items[rightMostSpot] = null;
        size -= 1;
        sink(1);
        return min;
    }

    @Override
    public void changePriority(T item) {
        for (int i = 1; i <= size; i += 1) {
            if (items[i].equals(item)) {
                swim(i);
                sink(i);
                break;
            }
        }
        return;
    }

    @Override
    public int size() {
        return size;
    }
}
