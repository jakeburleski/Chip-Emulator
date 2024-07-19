import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class MainMemoryTest {

	
	@Test
	void generalMainMemoryTest() throws Exception {
		
	
		Random rand = new Random();
		
		
		String[] load = new String[1024];
		
		for(int i=0; i<load.length; i++) {
			load[i] = "";
			for(int j=0; j<32; j++) {
				int randomNum = rand.nextInt(2);
				load[i] += String.valueOf(randomNum);
			}
		}
		
		
		load[0] = "00000000000000000000000000000010";
		
		// tests load and read
		MainMemory.load(load);
		
		System.out.println(MainMemory.read(new Word(0)).getSigned());
		assertEquals(MainMemory.read(new Word(0)).getSigned(), new Word(reverse(load[0])).getSigned());
		assertEquals(MainMemory.read(new Word(432)).getSigned(), new Word(reverse(load[432])).getSigned());
		assertEquals(MainMemory.read(new Word(1023)).getSigned(), new Word(reverse(load[1023])).getSigned());
		assertEquals(MainMemory.read(new Word(987)).getSigned(), new Word(reverse(load[987])).getSigned());
		assertEquals(MainMemory.read(new Word(1000)).getSigned(), new Word(reverse(load[1000])).getSigned());
		assertEquals(MainMemory.read(new Word(111)).getSigned(), new Word(reverse(load[111])).getSigned());
		assertEquals(MainMemory.read(new Word(296)).getSigned(), new Word(reverse(load[296])).getSigned());
		assertEquals(MainMemory.read(new Word(11)).getSigned(), new Word(reverse(load[11])).getSigned());
		assertEquals(MainMemory.read(new Word(99)).getSigned(), new Word(reverse(load[99])).getSigned());
		
		
		
		
		
		// tests write
		MainMemory.write(new Word(0), new Word(123));
		assertEquals(MainMemory.read(new Word(0)).getUnsigned(),  new Word(123).getUnsigned());
		MainMemory.write(new Word(78), new Word(986321));
		assertEquals(MainMemory.read(new Word(78)).getUnsigned(),  new Word(986321).getUnsigned());
		MainMemory.write(new Word(1000), new Word(12345));
		assertEquals(MainMemory.read(new Word(1000)).getUnsigned(),  new Word(12345).getUnsigned());
		MainMemory.write(new Word(1023), new Word(97485));
		assertEquals(MainMemory.read(new Word(1023)).getUnsigned(),  new Word(97485).getUnsigned());
		MainMemory.write(new Word(475), new Word(0));
		assertEquals(MainMemory.read(new Word(475)).getUnsigned(),  new Word(0).getUnsigned());
		
		
		
		Word incrementWord1 = new Word(33);
		Word incrementWord2 = new Word(42);
		Word incrementWord3 = new Word(912);
		Word incrementWord4 = new Word(1);
		
		// tests increment
		assertEquals(new Word(34).getUnsigned(), incrementWord1.increment().getUnsigned());
		assertEquals(new Word(43).getUnsigned(), incrementWord2.increment().getUnsigned());
		assertEquals(new Word(913).getUnsigned(), incrementWord3.increment().getUnsigned());
		assertEquals(new Word(2).getUnsigned(), incrementWord4.increment().getUnsigned());
		
		

		// tests decrement
		assertEquals(new Word(32).getUnsigned(), incrementWord1.decrement().getUnsigned());
		assertEquals(new Word(41).getUnsigned(), incrementWord2.decrement().getUnsigned());
		assertEquals(new Word(911).getUnsigned(), incrementWord3.decrement().getUnsigned());
		assertEquals(new Word(0).getUnsigned(), incrementWord4.decrement().getUnsigned());
		
		
		
		
		
	}

	public String reverse(String word) {
		String reverseString = "";
		for(int i=word.length()-1; i>=0; i--) {
			reverseString += word.charAt(i);
		}
		
		return reverseString;
	}
}
