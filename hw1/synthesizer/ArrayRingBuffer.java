package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> implements Iterable<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        rb = (T[]) new Object[this.capacity];
        first = last = this.fillCount = 0;
    }

    private int plusOne(int x) {
        return (x + 1) % this.capacity;
    }


    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            //return;
            throw new RuntimeException("Ring Buffer Overflow");
        }
        rb[last] = x;
        fillCount += 1;
        last = plusOne(last);
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            //return null;
            throw new RuntimeException("Ring Buffer Underflow");
        }
        T result = rb[first];
        rb[first] = null;
        first = plusOne(first);
        fillCount -= 1;
        return result;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return rb[first];
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int timesLeft;
        private int index;
        ArrayRingBufferIterator() {
            timesLeft = fillCount;
            index = first;
        }

        @Override
        public boolean hasNext() {
            return timesLeft != 0;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            T result = rb[index];
            index = plusOne(index);
            timesLeft -= 1;
            return result;
        }
    }
}
