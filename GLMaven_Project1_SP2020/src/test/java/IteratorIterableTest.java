import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Iterator and Iterable implementation Tests")
class IteratorIterableTest {

    GenericStack<Integer> s;

    @BeforeEach
    void init() {
        s = new GenericStack<Integer>(0);
        for(int i=1; i<10; i++) { s.push(i); }
    }

    @Test
    @Tag("init")
    @DisplayName("Proper Iterator initialization")
    void testInitIterator() {
        Iterator it = s.createIterator();
        assertEquals("GenericList$GLIterator", it.getClass().getName(),"init failed on Iterator");
    }

    @Test
    @Tag("hasNext")
    @DisplayName("hasNext() returns true on non empty list")
    void hasNextNonEmptyStack() {
        Iterator it = s.createIterator();
        assertTrue(it.hasNext(), "hasNext() returned the wrong value");
    }

    @Test
    @Tag("hasNext")
    @DisplayName("hasNext() returns false on empty list")
    void hasNextEmptyStack() {
        s.dumpList();
        Iterator it = s.createIterator();
        assertFalse(it.hasNext(), "hasNext() returned the wrong value");
    }

    @Test
    @Tag("next")
    @DisplayName("next() returns value on non empty list")
    void nextNonEmptyStack() {
        Iterator it = s.createIterator();
        assertNotEquals(null, it.next(), "Next returned null on non-empty stack");
    }

    @Test
    @Tag("next")
    @DisplayName("next() returns null on empty list")
    void nextEmptyStack() {
        s.dumpList();
        Iterator it = s.createIterator();
        assertEquals(null, it.next(), "Next returned not null on empty stack");
    }

    @Test
    @Tag("iterator")
    @DisplayName("Iterator goes through the list correctly")
    void iterateOverList() {
        Iterator it = s.createIterator();
        int i = 9;
        while(it.hasNext()) {
            assertEquals(i--, it.next(), "Next returned wrong value");
        }
    }

    @Test
    @Tag("iterator")
    @DisplayName("Iterator works properly after dumpList()")
    void createIteratorThenDump() {
        Iterator it = s.createIterator();
        s.dumpList();
        int i = 9;
        while(it.hasNext()) {
            assertEquals(i--, it.next(), "Next returned wrong value after dump");
        }
    }

    @Test
    @Tag("iterator")
    @DisplayName("Iterator works properly after list is modified")
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
    @Tag("iterator")
    @DisplayName("Iterator doesn't go through empty list")
    void iterateOverEmptyList() {
        s.dumpList();
        Iterator<Integer> it = s.createIterator();
        while(it.hasNext()) {
            it.next();
            fail("It shouldn't iterate over empty list... ");
        }
    }

    @Test
    @Tag("iterable")
    @DisplayName("Enhanced for loop iterates correctly")
    void iterableNonEmptyList() {
        int i = 9;
        for(int val: s) {
            assertEquals(i--, val, "Enhanced for loop prints wrong values");
        }
    }

    @Test
    @Tag("iterable")
    @DisplayName("Enhanced for loop doesn't iterate through empty list")
    void iterableEmptyList() {
        s.dumpList();
        for(int val: s) {
            fail("For loop shouldn't iterate over empty list...");
        }
    }
}
