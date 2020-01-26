import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StatMethodTest {
	
	static Statistics s1;
	static Statistics s2;
	
	@BeforeAll
	static void setup() {
		s1 = new Statistics("values.txt", 5);
		s2 = new Statistics("values2.txt", 6);
	}
	
	
	@Test
	void mean5valsTest() {
		
		assertEquals(37.0, Math.round(StatFormulas.mean(s1.getValues())), "wrong mean for array size 5");
	}
	
	@Test
	void mean6valsTest() {
		
		assertEquals(35.0, Math.round(StatFormulas.mean(s2.getValues())), "wrong mean for array size 6");
		
	}
	
	@Test
	void median5valsTest() {
		
		assertEquals(22.3, StatFormulas.median(s1.getValues()), "wrong median for array size 5");
		
	}
	
	@Test
	void median6valsTest() {
		
		assertEquals(30, Math.round(StatFormulas.median(s2.getValues())), "wrong median for array size 6");
		
	}
	
	@Test
	void std5valsTest() {
		
		assertEquals(33, Math.round(StatFormulas.std(s1.getValues())), "STD wrong for array size 5");
	}
	
	@Test
	void std6valsTest() {
		
		assertEquals(28, Math.round(StatFormulas.std(s2.getValues())), "STD wrong for array size 6");
	}

}
