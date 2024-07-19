import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
public class WordTest {

	@Test
	void generalWordTest() throws Exception {
		
	
		
		// creates a mix of diffrent words to test
		Bit[] bitArray1 = new Bit[32];
		for(int i=0; i<32; i++) {
			Bit Bit1 = new Bit();
			Bit1.set();
			bitArray1[i] = Bit1; 
		}
		Word word1 = new Word(bitArray1);
		
		
		
		
		
		

		Bit firstBit2 = new Bit();
		Bit secondBit2 = new Bit();
		firstBit2.set();
		secondBit2.clear();
		Bit[] bitArray2 = new Bit[32];
		for(int i=0; i<32; i++) {
			if(i%2==0) {bitArray2[i] = firstBit2;}
			else {bitArray2[i] = secondBit2;}
		}
		Word word2 = new Word(bitArray2);
		
		
		
		

		Bit[] bitArray3 = new Bit[32];
		for(int i=0; i<32; i++) {
			Bit Bit3 = new Bit();
			Bit3.clear();
			bitArray3[i] = Bit3; 
		}
		Word word3 = new Word(bitArray3);
		
		
		
		
		
		

		Bit firstBit4 = new Bit();
		Bit secondBit4 = new Bit();
		firstBit4.set();
		secondBit4.clear();
		Bit[] bitArray4 = new Bit[32];
		for(int i=0; i<32; i++) {
			if(i%3==0) {bitArray4[i] = firstBit4;}
			else {bitArray4[i] = secondBit4;}
		}
		Word word4 = new Word(bitArray4);
		
		
		
		// tests toString and constructor
		assertEquals(word1.toString(),"t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, ");
		assertEquals(word2.toString(),"f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, ");
		assertEquals(word3.toString(),"f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, ");
		assertEquals(word4.toString(),"f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, ");
		
		
		// tests copy
		Word copy1 = new Word();
		Word copy2 = new Word();
		Word copy3 = new Word();
		Word copy4 = new Word();
		copy1.copy(word1);
		copy2.copy(word2);
		copy3.copy(word3);
		copy4.copy(word4);
		assertEquals(copy1.toString(),"t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, ");
		assertEquals(copy2.toString(),"f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, ");
		assertEquals(copy3.toString(),"f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, ");
		assertEquals(copy4.toString(),"f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, ");
		

		
		
		
		
		
		// rests getBit()
		assertEquals(word1.getBit(3).value, true);
		assertEquals(word2.getBit(9).value, false);
		assertEquals(word3.getBit(31).value, false);
		assertEquals(word4.getBit(26).value, false);
		
		
		// tests setBit()
		word1.setBit(0, new Bit(false));
		word2.setBit(22, new Bit(false));
		word3.setBit(13, new Bit(true));
		word4.setBit(5, new Bit(true));
		assertEquals(word1.getBit(0).value, false);
		assertEquals(word2.getBit(22).value, false);
		assertEquals(word3.getBit(13).value, true);
		assertEquals(word4.getBit(5).value, true);
		
		// changing them back for ease of use
		word1.setBit(0, new Bit(true));
		word2.setBit(22, new Bit(true));
		word3.setBit(13, new Bit(false));
		word4.setBit(5, new Bit(false));
		
		// tests and()
		Word and1 = word1.and(word3);
		Word and2 = word1.and(word4);
		Word and3 = word3.and(word2);
		Word and4 = word4.and(word3);
		assertEquals(and1.toString(),"f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, ");
		assertEquals(and2.toString(),"f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, ");
		assertEquals(and3.toString(),"f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, ");
		assertEquals(and4.toString(),"f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, ");
		

		// tests or()
		Word or1 = word1.or(word3);
		Word or2 = word1.or(word4);
		Word or3 = word3.or(word2);
		Word or4 = word4.or(word3);
		assertEquals(or1.toString(),"t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, ");
		assertEquals(or2.toString(),"t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, ");
		assertEquals(or3.toString(),"f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, ");
		assertEquals(or4.toString(),"f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, ");

		
		// tests xor()
		Word xor1 = word1.xor(word3);
		Word xor2 = word1.xor(word4);
		Word xor3 = word3.xor(word2);
		Word xor4 = word4.xor(word3);
		assertEquals(xor1.toString(),"t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, ");
		assertEquals(xor2.toString(),"t, f, t, t, f, t, t, f, t, t, f, t, t, f, t, t, f, t, t, f, t, t, f, t, t, f, t, t, f, t, t, f, ");
		assertEquals(xor3.toString(),"f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, ");
		assertEquals(xor4.toString(),"f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, ");
		
		

		// tests not()
		Word not1 = word1.not();
		Word not2 = word2.not();
		Word not3 = word3.not();
		Word not4 = word4.not();
		assertEquals(not1.toString(),"f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, ");
		assertEquals(not2.toString(),"t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, ");
		assertEquals(not3.toString(),"t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, ");
		assertEquals(not4.toString(),"t, f, t, t, f, t, t, f, t, t, f, t, t, f, t, t, f, t, t, f, t, t, f, t, t, f, t, t, f, t, t, f, ");

		
		
		
		
		
		// tests right shift
		Word rightShift1 = word1;
		Word rightShift2 = word2;
		Word rightShift3 = word3;
		Word rightShift4 = word4;
		
		rightShift1 = rightShift1.rightShift(6);
		rightShift2 = rightShift2.rightShift(1);
		rightShift3 = rightShift3.rightShift(3);
		rightShift4 = rightShift4.rightShift(2);
		assertEquals(rightShift1.toString(),"f, f, f, f, f, f, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, ");
		assertEquals(rightShift2.toString(),"f, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, ");
		assertEquals(rightShift3.toString(),"f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, ");
		assertEquals(rightShift4.toString(),"f, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, ");
		
		

		// tests left shift
		Word leftShift2 = word2;
		Word leftShift3 = word3;
		Word leftShift4 = word4;
		
		leftShift2 = leftShift2.leftShift(1);
		leftShift3 = leftShift3.leftShift(3);
		leftShift4 = leftShift4.leftShift(2);
		assertEquals(leftShift2.toString(),"t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, ");
		assertEquals(leftShift3.toString(),"f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, ");
		assertEquals(leftShift4.toString(),"f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, t, f, f, ");
		
		
		// tests set
		word1.set(-7);
		word2.set(10);
		word3.set(15);
		word4.set(22);
		assertEquals(word1.toString(),"t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, f, f, t, ");
		assertEquals(word2.toString(),"f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, f, t, f, ");
		assertEquals(word3.toString(),"f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, t, t, t, ");
		assertEquals(word4.toString(),"f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, f, t, t, f, ");
		
		
		
		// because unsigned can't do negative decimal numbers
		word1.set(7);
		
		// tests getUnsigned()
		Long unsigned1 = word1.getUnsigned();
		Long unsigned2 = word2.getUnsigned();
		Long unsigned3 = word3.getUnsigned();
		Long unsigned4 = word4.getUnsigned();
		assertEquals(unsigned1.intValue(),7);
		assertEquals(unsigned2.intValue(),10);
		assertEquals(unsigned3.intValue(),15);
		assertEquals(unsigned4.intValue(),22);
		
		
		// tests getSigned()
		word1.set(-24);
		word2.set(5789);
		word3.set(-678);
		word4.set(17053);
		int signed1 = word1.getSigned();
		int signed2 = word2.getSigned();
		int signed3 = word3.getSigned();
		int signed4 = word4.getSigned();
		assertEquals(signed1,-24);
		assertEquals(signed2,5789);
		assertEquals(signed3,-678);
		assertEquals(signed4,17053);
		
		
		

		// tests add()
		word1.set(-24);
		word2.set(5789);
		word3.set(-678);
		word4.set(17053);
		assertEquals((word1.add(word2)).getSigned(),5765);
		assertEquals((word2.add(word4)).getSigned(),22842);
		assertEquals((word3.add(word3)).getSigned(),-1356);
		assertEquals((word4.add(word1)).getSigned(),17029);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
