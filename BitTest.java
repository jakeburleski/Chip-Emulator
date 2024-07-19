import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
public class BitTest {

	@Test
	void generalBitTest() throws Exception {
		
		Bit bitOne = new Bit();
		
		// tests set()
		bitOne.set();
		assertEquals(bitOne.getValue(), true);
		
		// tests set(boolean value)
		bitOne.set(false);
		assertEquals(bitOne.getValue(), false);
		
		
		// tests toggle when false
		bitOne.toggle();
		assertEquals(bitOne.getValue(), true);
		
		// tests toggle when true
		bitOne.toggle();
		assertEquals(bitOne.getValue(), false);
		
		// tests clear
		bitOne.clear();
		assertEquals(bitOne.getValue(), false);
		
		// tests getValue()
		assertEquals(bitOne.getValue(), false);
		
		
		
		
		Bit bitTwo = new Bit();
		
		
		// tests false and false
		bitOne.clear();
		bitTwo.clear();
		assertEquals(bitOne.and(bitTwo).value, false);
		
		// tests true and true
		bitOne.set();
		bitTwo.set();
		assertEquals(bitOne.and(bitTwo).value, true);

		// tests true and false
		bitOne.set();
		bitTwo.clear();
		assertEquals(bitOne.and(bitTwo).value, false);

		// tests false and true
		bitOne.clear();
		bitTwo.set();
		assertEquals(bitOne.and(bitTwo).value, false);
		
		
		

		// tests false or false
		bitOne.clear();
		bitTwo.clear();
		assertEquals(bitOne.or(bitTwo).value, false);
		
		// tests true or true
		bitOne.set();
		bitTwo.set();
		assertEquals(bitOne.or(bitTwo).value, true);

		// tests true or false
		bitOne.set();
		bitTwo.clear();
		assertEquals(bitOne.or(bitTwo).value, true);

		// tests false or true
		bitOne.clear();
		bitTwo.set();
		assertEquals(bitOne.or(bitTwo).value, true);
		
		
		
		

		// tests false xor false
		bitOne.clear();
		bitTwo.clear();
		assertEquals(bitOne.xor(bitTwo).value, false);
		
		// tests true xor true
		bitOne.set();
		bitTwo.set();
		assertEquals(bitOne.xor(bitTwo).value, false);

		// tests true xor false
		bitOne.set();
		bitTwo.clear();
		assertEquals(bitOne.xor(bitTwo).value, true);

		// tests false xor true
		bitOne.clear();
		bitTwo.set();
		assertEquals(bitOne.xor(bitTwo).value, true);
		
		

		// tests not()
		bitOne.set();
		bitTwo = bitOne.not();
		assertEquals(bitTwo.getValue(), false);
		
		// tests not()
		bitOne.clear();
		bitTwo = bitOne.not();
		assertEquals(bitTwo.getValue(), true);
		
		// tests toString()
		bitOne.set();
		bitTwo.clear();
		assertEquals(bitOne.toString(),"t");
		assertEquals(bitTwo.toString(),"f");
		
		
		
	}
	
}
