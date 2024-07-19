import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class aluTest {

	@Test
	void generalAluTest() throws Exception {
		
		Bit t = new Bit(true);		// true bit
		Bit f = new Bit(false);		// false bit
		
		Word wordOne = new Word();	// word1 to test
		Word wordTwo = new Word();	// word2 to test
		Word wordThree = new Word();// word3 to test
		Word wordFour = new Word();	// word4 to test
		ALU alu1;					// ALU1 object
		ALU alu2;					// ALU2 object
		ALU alu3;					// ALU3 object
		ALU alu4;					// ALU4 object
		
		Bit[] andOP = {f,f,f,t};		// and operation
		Bit[] orOP = {t,f,f,t};			// or operation
		Bit[] xorOP = {f,t,f,t};		// xor operation
		Bit[] notOP = {t,t,f,t};		// not operation
		Bit[] leftOP = {f,f,t,t};	// left shift operation
		Bit[] rightOP = {t,f,t,t};	// right shift operation
		Bit[] addOP = {f,t,t,t};		// add operation
		Bit[] subOP = {t,t,t,t};		// subtract operation
		Bit[] multiplyOP = {t,t,t,f};	// subtract operation
		Bit[] equalsOP = {f,f,f,f};		// equals operation
		Bit[] notEqualsOP = {t,f,f,f};		// not equals operation
		Bit[] lessThanOP = {f,t,f,f};		// less than operation
		Bit[] greaterThanOrEqOP = {t,t,f,f};		// greater than or equals operation
		Bit[] greaterThanOP = {f,f,t,f};		// greater than operation
		Bit[] lessThanOrEqOP = {t,f,t,f};		// less than or equals operation
		
		
		// sets words
		wordOne.set(-8);
		wordTwo.set(2);
		wordThree.set(6497);
		wordFour.set(-3242);
		
		
		

		// tests and
		alu1 = new ALU(wordOne, wordTwo);
		alu2 = new ALU(wordTwo, wordFour);
		alu3 = new ALU(wordThree, wordFour);
		alu4 = new ALU(wordOne, wordFour);
		
		alu1.doOperation(andOP);
		alu2.doOperation(andOP);
		alu3.doOperation(andOP);
		alu4.doOperation(andOP);
		
		

		assertEquals(alu1.result.getSigned(), 0);
		assertEquals(alu2.result.getSigned(), 2);
		assertEquals(alu3.result.getSigned(), 4416);
		assertEquals(alu4.result.getSigned(), -3248);
		
		
		
		

		// tests or
		alu1 = new ALU(wordOne, wordTwo);
		alu2 = new ALU(wordTwo, wordFour);
		alu3 = new ALU(wordThree, wordFour);
		alu4 = new ALU(wordOne, wordFour);
		
		alu1.doOperation(orOP);
		alu2.doOperation(orOP);
		alu3.doOperation(orOP);
		alu4.doOperation(orOP);
		
		

		assertEquals(alu1.result.getSigned(), -6);
		assertEquals(alu2.result.getSigned(), -3242);
		assertEquals(alu3.result.getSigned(), -1161);
		assertEquals(alu4.result.getSigned(), -2);
		
		
		
		
		
		

		// tests xor
		alu1 = new ALU(wordOne, wordTwo);
		alu2 = new ALU(wordTwo, wordFour);
		alu3 = new ALU(wordThree, wordFour);
		alu4 = new ALU(wordOne, wordFour);
		
		alu1.doOperation(xorOP);
		alu2.doOperation(xorOP);
		alu3.doOperation(xorOP);
		alu4.doOperation(xorOP);
		
		

		assertEquals(alu1.result.getSigned(), -6);
		assertEquals(alu2.result.getSigned(), -3244);
		assertEquals(alu3.result.getSigned(), -5577);
		assertEquals(alu4.result.getSigned(), 3246);
		
		
		
		// tests not
		Word wordFive = new Word();
		Word wordSix = new Word(true);
		wordFive.set(-59);
		alu1 = new ALU(wordOne);
		alu2 = new ALU(wordFive);
		alu3 = new ALU(wordSix);
		alu4 = new ALU(wordFour);
		
		alu1.doOperation(notOP);
		alu2.doOperation(notOP);
		alu3.doOperation(notOP);
		alu4.doOperation(notOP);
		
		assertEquals(alu1.result.getSigned(), 7);
		assertEquals(alu2.result.getSigned(), 58);
		assertEquals(alu3.result.getSigned(), 0);
		assertEquals(alu4.result.getSigned(), 3241);
		
		
		
		
		
		

		// sets words
		wordOne = new Word();
		wordOne.setBit(3, new Bit(true));
		wordTwo.set(4);
		wordThree.set(34);
		wordFour.set(5);
		
		
		

		// tests left shift
		alu1 = new ALU(wordOne, wordTwo);
		alu2 = new ALU(wordTwo, wordFour);
		alu3 = new ALU(wordThree, wordFour);
		alu4 = new ALU(wordOne, wordFour);
		
		alu1.doOperation(leftOP);
		alu2.doOperation(leftOP);
		alu3.doOperation(leftOP);
		alu4.doOperation(leftOP);
		
		assertEquals(alu1.result.getSigned(), 128);
		assertEquals(alu2.result.getSigned(), 128);
		assertEquals(alu3.result.getSigned(), 1088);
		assertEquals(alu4.result.getSigned(), 256);
		
		
		
		
		
		

		// sets words
		wordOne = new Word();
		wordOne.setBit(31, new Bit(true));
		wordTwo = new Word();
		wordTwo.setBit(27, new Bit(true));
		wordThree = new Word();
		wordThree.setBit(16, new Bit(true));
		wordFour = new Word();
		wordFour.setBit(8, new Bit(true));
		
		// sets words for right shift
		Word shift1 = new Word();
		shift1.set(22);
		Word shift2 = new Word();
		shift2.set(14);
		Word shift3 = new Word();
		shift3.set(4);
		Word shift4 = new Word();
		shift4.set(8);
		

		// tests right shift
		alu1 = new ALU(wordOne, shift1);
		alu2 = new ALU(wordTwo, shift2);
		alu3 = new ALU(wordThree, shift3);
		alu4 = new ALU(wordFour, shift4);
		
		alu1.doOperation(rightOP);
		alu2.doOperation(rightOP);
		alu3.doOperation(rightOP);
		alu4.doOperation(rightOP);
		
		assertEquals(alu1.result.getSigned(), 512);
		assertEquals(alu2.result.getSigned(), 8192);
		assertEquals(alu3.result.getSigned(), 4096);
		assertEquals(alu4.result.getSigned(), 1);
		
		
		

		// resets words
		wordOne.set(-8);
		wordTwo.set(2);
		wordThree.set(6497);
		wordFour.set(-3242);
		
		
		// tests add
		alu1 = new ALU(wordOne, wordTwo);
		alu2 = new ALU(wordTwo, wordFour);
		alu3 = new ALU(wordThree, wordFour);
		alu4 = new ALU(wordOne, wordFour);
		
		alu1.doOperation(addOP);
		alu2.doOperation(addOP);
		alu3.doOperation(addOP);
		alu4.doOperation(addOP);
		
		assertEquals(alu1.result.getSigned(), -6);
		assertEquals(alu2.result.getSigned(), -3240);
		assertEquals(alu3.result.getSigned(), 3255);
		assertEquals(alu4.result.getSigned(), -3250);
		
		
		
		

		// tests subtract
		alu1 = new ALU(wordOne, wordTwo);
		alu2 = new ALU(wordTwo, wordFour);
		alu3 = new ALU(wordThree, wordFour);
		alu4 = new ALU(wordOne, wordFour);
		
		alu1.doOperation(subOP);
		alu2.doOperation(subOP);
		alu3.doOperation(subOP);
		alu4.doOperation(subOP);
		
		assertEquals(alu1.result.getSigned(), -10);
		assertEquals(alu2.result.getSigned(), 3244);
		assertEquals(alu3.result.getSigned(), 9739);
		assertEquals(alu4.result.getSigned(), 3234);
		
		
		
		
		

		// sets words
		wordOne.set(-8);
		wordTwo.set(2);
		wordThree.set(6497);
		wordFour.set(-32);
		

		// tests multiply
		alu1 = new ALU(wordOne, wordTwo);
		alu2 = new ALU(wordTwo, wordThree);
		alu3 = new ALU(wordThree, wordFour);
		alu4 = new ALU(wordOne, wordFour);
		
		alu1.doOperation(multiplyOP);
		alu2.doOperation(multiplyOP);
		alu3.doOperation(multiplyOP);
		alu4.doOperation(multiplyOP);
		
		assertEquals(alu1.result.getSigned(), -16);
		assertEquals(alu2.result.getSigned(), 12994);
		assertEquals(alu3.result.getSigned(), -207904);
		assertEquals(alu4.result.getSigned(), 256);
		
		
		
		// sets words
		wordOne.set(42134);
		wordTwo.set(42134);
		wordThree.set(15);
		wordFour.set(15);

		// tests equals
		// Word 1 for true, 0 for false
		alu1 = new ALU(wordOne, wordTwo);
		alu2 = new ALU(wordTwo, wordThree);
		alu3 = new ALU(wordThree, wordFour);
		alu4 = new ALU(wordOne, wordFour);
		
		alu1.doOperation(equalsOP);
		alu2.doOperation(equalsOP);
		alu3.doOperation(equalsOP);
		alu4.doOperation(equalsOP);
		
		assertEquals(alu1.result.getSigned(), 1);
		assertEquals(alu2.result.getSigned(), 0);
		assertEquals(alu3.result.getSigned(), 1);
		assertEquals(alu4.result.getSigned(), 0);

		
		
		
		// tests not equals
		// Word 1 for true, 0 for false
		alu1 = new ALU(wordOne, wordTwo);
		alu2 = new ALU(wordTwo, wordThree);
		alu3 = new ALU(wordThree, wordFour);
		alu4 = new ALU(wordOne, wordFour);
		
		alu1.doOperation(notEqualsOP);
		alu2.doOperation(notEqualsOP);
		alu3.doOperation(notEqualsOP);
		alu4.doOperation(notEqualsOP);
		
		assertEquals(alu1.result.getSigned(), 0);
		assertEquals(alu2.result.getSigned(), 1);
		assertEquals(alu3.result.getSigned(), 0);
		assertEquals(alu4.result.getSigned(), 1);
		
		
		
		
		
		// sets words
		wordOne.set(42134);
		wordTwo.set(42134);
		wordThree.set(155);
		wordFour.set(156);

		// tests less than
		// Word 1 for true, 0 for false
		alu1 = new ALU(wordOne, wordTwo);
		alu2 = new ALU(wordTwo, wordThree);
		alu3 = new ALU(wordThree, wordFour);
		alu4 = new ALU(wordThree, wordTwo);
				
		alu1.doOperation(lessThanOP);
		alu2.doOperation(lessThanOP);
		alu3.doOperation(lessThanOP);
		alu4.doOperation(lessThanOP);
				
		assertEquals(alu1.result.getSigned(), 0);
		assertEquals(alu2.result.getSigned(), 0);
		assertEquals(alu3.result.getSigned(), 1);
		assertEquals(alu4.result.getSigned(), 1);

		
		
		

		// sets words
		wordOne.set(42134);
		wordTwo.set(42134);
		wordThree.set(155);
		wordFour.set(156);

		// tests greater than or equal to
		// Word 1 for true, 0 for false
		alu1 = new ALU(wordOne, wordTwo);
		alu2 = new ALU(wordTwo, wordThree);
		alu3 = new ALU(wordThree, wordFour);
		alu4 = new ALU(wordThree, wordTwo);
				
		alu1.doOperation(greaterThanOrEqOP);
		alu2.doOperation(greaterThanOrEqOP);
		alu3.doOperation(greaterThanOrEqOP);
		alu4.doOperation(greaterThanOrEqOP);
				
		assertEquals(alu1.result.getSigned(), 1);
		assertEquals(alu2.result.getSigned(), 1);
		assertEquals(alu3.result.getSigned(), 0);
		assertEquals(alu4.result.getSigned(), 0);

		
		
		

		// sets words
		wordOne.set(42134);
		wordTwo.set(42134);
		wordThree.set(155);
		wordFour.set(156);

		// tests greater than
		// Word 1 for true, 0 for false
		alu1 = new ALU(wordOne, wordTwo);
		alu2 = new ALU(wordTwo, wordThree);
		alu3 = new ALU(wordThree, wordFour);
		alu4 = new ALU(wordFour, wordThree);
				
		alu1.doOperation(greaterThanOP);
		alu2.doOperation(greaterThanOP);
		alu3.doOperation(greaterThanOP);
		alu4.doOperation(greaterThanOP);
				
		assertEquals(alu1.result.getSigned(), 0);
		assertEquals(alu2.result.getSigned(), 1);
		assertEquals(alu3.result.getSigned(), 0);
		assertEquals(alu4.result.getSigned(), 1);
		
		
		
		

		// sets words
		wordOne.set(42134);
		wordTwo.set(42134);
		wordThree.set(155);
		wordFour.set(156);

		// tests less than or equals to
		// Word 1 for true, 0 for false
		alu1 = new ALU(wordOne, wordTwo);
		alu2 = new ALU(wordTwo, wordThree);
		alu3 = new ALU(wordThree, wordFour);
		alu4 = new ALU(wordFour, wordThree);
				
		alu1.doOperation(lessThanOrEqOP);
		alu2.doOperation(lessThanOrEqOP);
		alu3.doOperation(lessThanOrEqOP);
		alu4.doOperation(lessThanOrEqOP);
				
		assertEquals(alu1.result.getSigned(), 1);
		assertEquals(alu2.result.getSigned(), 0);
		assertEquals(alu3.result.getSigned(), 1);
		assertEquals(alu4.result.getSigned(), 0);
		
		
		
		
		
	}
	
	
}
