import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

@DisplayName("GenericStack Tests")
class ListTest {

	GenericStack<Integer> stack;
	int initVal = 1;

	@BeforeEach
	void init() {
		stack = new GenericStack<Integer>(initVal);
	}

	@Test
	@Tag("init")
	@DisplayName("Proper initialization of GenericStack")
	void testInitGS() {
		assertEquals("GenericStack", stack.getClass().getName(),"init failed on GS");
	}

	@Test
	@Tag("init")
	@DisplayName("Proper initialization of GenericList Node")
	void testInitNode() {
		assertEquals("GenericList$Node", stack.getHead().getClass().getName(), "node not init");
	}

	@Test
	@Tag("init")
	@DisplayName("Proper Initial value")
	void initValue() {
		assertEquals(initVal, stack.pop(), "Wrong initial value");
	}

	@Test
	@Tag("init")
	@DisplayName("Proper initial length")
	void initLength() {
		assertEquals(1, stack.getLength(), "Wrong initial length");
	}

	@Test
	@Tag("length")
	@DisplayName("Pop on empty stack doesn't affect length")
	void lengthAfterPopOnEmpty() {
		// Removing initial value
		stack.pop();

		// Pop on empty
		stack.pop();
		assertEquals(0, stack.getLength(), "Pop on empty stack changes length");
	}

	@Test
	@Tag("length")
	@DisplayName("Proper length changing")
	void length() {
		// Populating list
		for (int i=2; i<10; i++) {
			stack.push(i);
			assertEquals(i, stack.getLength(), "Wrong length after push");
		}

		// Popping list
		for (int i = stack.getLength(); i>0; i--) {
			stack.pop();
			assertEquals(i-1, stack.getLength(), "Wrong length after pop");
		}
	}

	@Test
	@Tag("add")
	@DisplayName("Testing push()")
	void pushValues() {
		// Populating list with non-sequential values
		for(int i=0; i<10; i++) {
			int valueToPush = i*2+2;
			stack.push(valueToPush);
			assertEquals(valueToPush, stack.pop(), "Wrong value on top");
		}
	}

	@Test
	@Tag("delete")
	@DisplayName("Testing pop()")
	void popValues() {
		ArrayList<Integer> list = new ArrayList<Integer>(11);
		list.add(initVal);

		Random rand = new Random();

		// Pushing random values
		for(int i=0; i<10; i++) {
			int val = rand.nextInt(10000);
			stack.push(val);
			list.add(val);
		}

		for(int i=0; i<10; i++) {
			assertEquals(list.get(list.size() - 1 - i), stack.pop(), "Wrong value after pop");
		}
	}

	@Test
	@Tag("delete")
	@DisplayName("Testing pop() on empty stack")
	void popOnEmpty() {
		// Removing initial value
		stack.pop();

		assertEquals(null, stack.pop(), "Pop on empty returns not null");
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
			stack.push(val);
			list.add(val);
		}

		ArrayList<Integer> stackDumped = stack.dumpList();
		for(int i=0; i<list.size(); i++) {
			assertEquals(list.get(list.size()-1-i), stackDumped.get(i), "Dump list returns wrong values");
		}
	}

	@Test
	@Tag("dump")
	@DisplayName("dumpList() makes stack empty")
	void emptyListAfterDump() {
		// Populate stack
		for(int i=0; i<5; i++) {
			stack.push(i);
		}
		stack.dumpList();

		assertEquals(0, stack.getLength(), "Length after dump != 0");
		assertEquals(null, stack.pop(), "Pop after dump returns not null");
	}

	@Test
	@Tag("add")
	@Tag("delete")
	@DisplayName("Stack works properly after clearing")
	void clearPopulateClear() {
		// Populating
		for(int i=2; i<10; i++) {
			stack.push(i);
			assertEquals(i, stack.getHead().data, "Top value is wrong");
		}

		// Emptying
		for(int i=9; i>0; i--) {
			assertEquals(i, stack.pop(), "Pop after populating returns wrong value");
		}

		// Checking for empty
		assertEquals(null, stack.pop(), "Stack is not clear");

		// Populating once again
		for(int i=13; i<20; i++) {
			stack.push(i);
			assertEquals(i, stack.getHead().data, "Populates wrong values after being cleared");
		}
	}

	@Test
	@Tag("generic")
	@DisplayName("Stack supports custom types")
	void genericTest() {
		// Strings
		GenericStack<String> s = new GenericStack<>("Hi there");
		assertEquals("Hi there", s.pop(), "Pop returned the wrong string");

		// Custom type
		class Q {
			private int value;
			Q(int initValue) { value = initValue; }
			int getValue() { return value; }
		}

		GenericStack<Q> s2 = new GenericStack<>(new Q(12));
		s2.push(new Q(15));

		assertEquals(15, s2.pop().getValue(), "Pop returned the wrong custom type object");
		assertEquals(12, s2.pop().getValue(), "Pop returned the wrong custom type object");
		assertEquals(null, s2.pop(), "Pop returned the wrong custom type object");
	}
}
