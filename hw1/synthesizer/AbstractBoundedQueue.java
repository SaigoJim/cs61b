package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T>, Iterable<T>{
    protected int fillCount;
    protected int capacity;

    @Override
    public int fillCount() {
        return fillCount;
    }

    @Override
    public int capacity() {
        return capacity;
    }
}
