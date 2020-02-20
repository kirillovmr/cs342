import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class IteratorIterableTest {

    GenericStack<Integer> s;

    @BeforeEach
    void init() {
        s = new GenericStack<Integer>(0);
        for(int i=1; i<10; i++) { s.push(i); }
    }

    @Test
    void testInitIterator() {
        Iterator it = s.createIterator();
        assertEquals("GenericList$GLIterator", it.getClass().getName(),"init failed on Iterator");
    }

    @Test
    void hasNextNonEmptyStack() {
        Iterator it = s.createIterator();
        assertEquals(true, it.hasNext(), "hasNext() returned the wrong value");
    }

    @Test
    void hasNextEmptyStack() {
        s.dumpList();
        Iterator it = s.createIterator();
        assertEquals(false, it.hasNext(), "hasNext() returned the wrong value");
    }

    @Test
    void nextNonEmptyStack() {
        Iterator it = s.createIterator();
        assertNotEquals(null, it.next(), "Next returned null on non-empty stack");
    }

    @Test
    void nextEmptyStack() {
        s.dumpList();
        Iterator it = s.createIterator();
        assertEquals(null, it.next(), "Next returned not null on empty stack");
    }

    @Test
    void iterateOverList() {
        Iterator it = s.createIterator();
        int i = 9;
        while(it.hasNext()) {
            assertEquals(i--, it.next(), "Next returned wrong value");
        }
    }

    @Test
    void createIteratorThenDump() {
        Iterator it = s.createIterator();
        s.dumpList();
        int i = 9;
        while(it.hasNext()) {
            assertEquals(i--, it.next(), "Next returned wrong value after dump");
        }
    }

    @Test
    void createIteratorThenModifyList() {
        Iterator it = s.createIterator();
        s.getHead().data = 99;
        int i = 9;
        while(it.hasNext()) {
            assertEquals(i == 9 ? 99 : i, it.next(), "Next returned wrong value after modification");
            i--;
        }
    }

    @Test
    void iterateOverEmptyList() {
        s.dumpList();
        Iterator<Integer> it = s.createIterator();
        while(it.hasNext()) {
            it.next();
            fail("It shouldn't iterate over empty list... ");
        }
    }

    @Test
    void iterableNonEmptyList() {
        int i = 9;
        for(int val: s) {
            assertEquals(i--, val, "Enhanced for loop prints wrong values");
        }
    }

    @Test
    void iterableEmptyList() {
        s.dumpList();
        for(int val: s) {
            fail("For loop shouldn't iterate over empty list...");
        }
    }
}
