import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

@DisplayName("GenericQueue Tests")
class QueueTest {

    GenericQueue<Integer> q;
    int initVal = 1;

    @BeforeEach
    void init() {
        q = new GenericQueue<Integer>(initVal);
    }

    @Test
    @Tag("init")
    @DisplayName("Proper initialization of GenericQueue")
    void testInitGS() {
        assertEquals("GenericQueue", q.getClass().getName(),"init failed on GQ");
    }

    @Test
    @Tag("init")
    @DisplayName("Proper initialization of GenericList Node")
    void testInitNode() {
        assertEquals("GenericList$Node", q.getHead().getClass().getName(), "node not init");
    }

    @Test
    @Tag("init")
    @DisplayName("Proper Initial value")
    void initValue() {
        assertEquals(initVal, q.dequeue(), "Wrong initial value");
    }

    @Test
    @Tag("init")
    @DisplayName("Proper initial length")
    void initLength() {
        assertEquals(1, q.getLength(), "Wrong initial length");
    }

    @Test
    @Tag("length")
    @DisplayName("Pop on empty queue doesn't affect length")
    void lengthAfterDequeueOnEmpty() {
        // Removing initial value
        q.dequeue();

        // Pop on empty
        q.dequeue();
        assertEquals(0, q.getLength(), "dequeue on empty queue changes length");
    }

    @Test
    @Tag("length")
    @DisplayName("Proper length changing")
    void length() {
        // Populating list
        for (int i=2; i<10; i++) {
            q.enqueue(i);
            assertEquals(i, q.getLength(), "Wrong length after enqueue");
        }

        // Dequeue list
        for (int i = q.getLength(); i>0; i--) {
            q.dequeue();
            assertEquals(i-1, q.getLength(), "Wrong length after dequeue");
        }
    }

    @Test
    @Tag("add")
    @DisplayName("Testing enqueue()")
    void enqueueValues() {
        int prevValue = initVal;

        // Populating list with non-sequential values
        for(int i=0; i<10; i++) {
            int valueToEnqueue = i*2+2;
            q.enqueue(valueToEnqueue);
            assertEquals(prevValue, q.dequeue(), "Wrong value on top");
            prevValue = valueToEnqueue;
        }
    }

    @Test
    @Tag("delete")
    @DisplayName("Testing dequeue()")
    void dequeueValues() {
        ArrayList<Integer> list = new ArrayList<Integer>(11);
        list.add(initVal);

        Random rand = new Random();

        // Enqueue random values
        for(int i=0; i<10; i++) {
            int val = rand.nextInt(10000);
            q.enqueue(val);
            list.add(val);
        }

        for(int i=0; i<10; i++) {
            assertEquals(list.get(i), q.dequeue(), "Wrong value after dequeue");
        }
    }

    @Test
    @Tag("delete")
    @DisplayName("Testing dequeue() on empty queue")
    void dequeueOnEmpty() {
        // Removing initial value
        q.dequeue();

        assertEquals(null, q.dequeue(), "Dequeue on empty returns not null");
    }

    @Test
    @Tag("dump")
    @DisplayName("Testing dumpList()")
    void dumpList() {
        ArrayList<Integer> list = new ArrayList<Integer>(11);
        list.add(initVal);

        Random rand = new Random();

        // Pushing random values
        for(int i=0; i<10; i++) {
            int val = rand.nextInt(10000);
            q.enqueue(val);
            list.add(val);
        }

        ArrayList<Integer> queueDumped = q.dumpList();
        for(int i=0; i<list.size(); i++) {
            assertEquals(list.get(i), queueDumped.get(i), "Dump list returns wrong values");
        }
    }

    @Test
    @Tag("dump")
    @DisplayName("dumpList() makes queue empty")
    void emptyListAfterDump() {
        // Populate queue
        for(int i=0; i<5; i++) {
            q.enqueue(i);
        }
        q.dumpList();

        assertEquals(0, q.getLength(), "Length after dump != 0");
        assertEquals(null, q.dequeue(), "Dequeue after dump returns not null");
    }

    @Test
    @Tag("add")
    @Tag("delete")
    @DisplayName("Queue works properly after clearing")
    void clearPopulateClear() {
        // Populating
        for(int i=2; i<10; i++) {
            q.enqueue(i);
            assertEquals(i, q.getTail().data, "Tail value is wrong");
        }

        // Emptying
        for(int i=1; i<10; i++) {
            assertEquals(i, q.dequeue(), "Dequeue after populating returns wrong value");
        }

        // Checking for empty
        assertEquals(null, q.dequeue(), "Queue is not clear");

        // Populating once again
        for(int i=13; i<20; i++) {
            q.enqueue(i);
            assertEquals(i, q.getTail().data, "Populates wrong values after being cleared");
        }
    }

    @Test
    @Tag("generic")
    @DisplayName("Queue supports custom types")
    void genericTest() {
        // Strings
        GenericQueue<String> q = new GenericQueue<>("Hi there");
        assertEquals("Hi there", q.dequeue(), "Dequeue returned the wrong string");

        // Custom type
        class Q {
            private int value;
            Q(int initValue) { value = initValue; }
            int getValue() { return value; }
        }

        GenericQueue<Q> q2 = new GenericQueue<>(new Q(12));
        q2.enqueue(new Q(15));

        assertEquals(12, q2.dequeue().getValue(), "Dequeue returned the wrong custom type object");
        assertEquals(15, q2.dequeue().getValue(), "Dequeue returned the wrong custom type object");
        assertEquals(null, q2.dequeue(), "Pop returned the wrong custom type object");
    }
}
