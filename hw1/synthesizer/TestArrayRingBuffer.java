package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        //ArrayRingBuffer arb = new ArrayRingBuffer(10);
    }

    @Test
    public void test() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer(10);
        arb.enqueue(33.3);
        arb.enqueue(33.4);
        arb.enqueue(33.5);
        arb.enqueue(33.6);

        assertEquals(33.3, (Object) arb.peek());
        assertEquals(33.3, (Object) arb.dequeue());
        assertEquals(33.4, (Object) arb.peek());
        double num = 33.7;
        for (int i = 0; i < 10; i += 1, num = num + 0.1) {
            arb.enqueue(num);
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
