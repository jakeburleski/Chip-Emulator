import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;

public class LexParseTest {

	@Test
	//Tests awk code
	void awkTest() throws Exception {
		//fail("Not yet implemented");
		
		
		var lexerTestOne = new Lexer("push R1 R3 EQ R3 r2 5 \n"
				+ "branch r30 ");
		
		
		
		System.out.println(lexerTestOne.Lex());
		
	}
		
		
	@Test	
	// tests underscore and numbers after word
	void lexerWordUnderScoreNumberTest() throws Exception {
		var lexerWordUnderScoreNumberTest = new Lexer("hello_342");
			
		System.out.println(lexerWordUnderScoreNumberTest.Lex());
		}
		
		
		
	@Test
	// tests a number after a word
	void lexerWordNumberTest() throws Exception {
		var lexerWordNumberTest = new Lexer("goodmorning4542");
		
		System.out.println(lexerWordNumberTest.Lex());
	}
		
		
	@Test
	// tests numbers than word
	void lexerNumberThenWordTest() throws Exception {
		var lexerNumberThenWordTest = new Lexer("5674Goodbye");
		
		System.out.println(lexerNumberThenWordTest.Lex());
	}
		
	@Test
	// tests symbols both one and 2 characters as well as unrecognized symbol
	void lexerSymbolsTest() throws Exception {
		var lexerSymbolsTest = new Lexer("hello<<<=+-:$*===<\\");
				
		System.out.println(lexerSymbolsTest.Lex());
	}
		
	@Test
	// tests string literals with quotation marks in string
	void lexerStringLiteralTest() throws Exception {
		var lexerStringLiteralTest = new Lexer("The cat says \"meow\" then he ran \"away\" before saying \"merry \\\"christmas\\\" to all and to all a good night\"");
						
		System.out.println(lexerStringLiteralTest.Lex());
	}
		
		
	@Test
	// tests string literals with quotation marks in string
	void lexerRegularExpressionsTest() throws Exception {
		var lexerRegularExpressionsTest = new Lexer("The cat says `meow` then he ran `away` before saying `merry \\`christmas\\` to all and to all a good night`");
								
		System.out.println(lexerRegularExpressionsTest.Lex());
	}
		
		
		
	@Test
	// tests intToBinary
	void intToBinary() throws Exception {
		
		String number = "9";
		int length = 5;
		
		int value = Integer.valueOf(number);	// gets integer value of string
		String numberAsBits = "";				// string to be returned
		
		// converts it into binary
		while(value > 0) {
			numberAsBits = value % 2 + numberAsBits;
			value = value/2;
		}
		
		// adds correct number of zeros in front of bits
		int lengthWithoutZeros = numberAsBits.length();
		for(int i=0; i<length-lengthWithoutZeros; i++) {
			numberAsBits = "0" + numberAsBits;	
		}
		
		
		System.out.println(numberAsBits);
		
	}
	
	@Test
	void testParserMath() throws Exception {

		var lexerTestOne = new Lexer("COPY 10 R1\n"
				+ "MATH add R1 R1 R2\n"
				+ "MATH multiply R2 R2\n");
		
		LinkedList<Token> tokenList = lexerTestOne.Lex();
		Parser parser = new Parser(tokenList);
		String[] loadArray = parser.Parse();
		
		MainMemory.load(loadArray);
		Processor process = new Processor();
		process.run();
		

		// tests that the result is 400
		assertEquals(process.Registers[2].getSigned(), new Word(400).getSigned());
	}
	

	@Test
	void testParserPush() throws Exception {

		var lexerTestOne = new Lexer("COPY 10 R1\n"
				+ "PUSH 1R R1 ADD 5\n");
		
		LinkedList<Token> tokenList = lexerTestOne.Lex();
		System.out.println(tokenList);
		Parser parser = new Parser(tokenList);
		String[] loadArray = parser.Parse();
		
		MainMemory.load(loadArray);
		Processor process = new Processor();
		process.run();
		
		// tests that 15 is pushed on the stack
		assertEquals(MainMemory.read(process.SP).getSigned(), new Word(15).getSigned());
	}
		
		

	@Test
	void testParserCall() throws Exception {

		var lexerTestOne = new Lexer("COPY 15 R1\n"
				+ "COPY 5 R2\n"
				+ "STORE 200 R1\n"
				+ "CALL 2R R2 le R1 10\n");
		
		LinkedList<Token> tokenList = lexerTestOne.Lex();
		System.out.println(tokenList);
		Parser parser = new Parser(tokenList);
		String[] loadArray = parser.Parse();
		
		MainMemory.load(loadArray);
		Processor process = new Processor();
		process.run();
		
		assertEquals(MainMemory.read(process.SP).getSigned(), new Word(4).getSigned());
		assertEquals(MainMemory.read(process.PC).getSigned(), new Word(200).getSigned());
	}
	
	

	@Test
	void testParserBranch() throws Exception {

		var lexerTestOne = new Lexer("COPY 15 R1\n"
				+ "COPY 5 R2\n"
				+ "STORE 200 R1\n"
				+ "BRANCH 2R R2 le R1 10\n");
		
		LinkedList<Token> tokenList = lexerTestOne.Lex();
		System.out.println(tokenList);
		Parser parser = new Parser(tokenList);
		String[] loadArray = parser.Parse();
		
		MainMemory.load(loadArray);
		Processor process = new Processor();
		process.run();
		
		assertEquals(MainMemory.read(process.PC).getSigned(), new Word(200).getSigned());
	}
	
	

	@Test
	void testParserHalt() throws Exception {

		var lexerTestOne = new Lexer("COPY 16 R1\n"
				+ "COPY 5 R2\n"
				+ "STORE 200 R1\n"
				+ "HALT\n"
				+ "BRANCH 2R R2 le R1 10\n");
		
		LinkedList<Token> tokenList = lexerTestOne.Lex();
		System.out.println(tokenList);
		Parser parser = new Parser(tokenList);
		String[] loadArray = parser.Parse();
		
		MainMemory.load(loadArray);
		Processor process = new Processor();
		process.run();
		
		// checks that branch is not called
		assertEquals(process.PC.getSigned(), new Word(4).getSigned());
	}
	

	@Test
	void testParserJump() throws Exception {

		var lexerTestOne = new Lexer("COPY 15 R1\n"
				+ "COPY 5 R2\n"
				+ "STORE 200 R1\n"
				+ "JUMP 0R 10\n");
		
		LinkedList<Token> tokenList = lexerTestOne.Lex();
		System.out.println(tokenList);
		Parser parser = new Parser(tokenList);
		String[] loadArray = parser.Parse();
		
		MainMemory.load(loadArray);
		Processor process = new Processor();
		process.run();
		
		assertEquals(process.PC.getSigned(), new Word(11).getSigned());
	}
	
	
	
	


	@Test
	void testParserPop() throws Exception {

		var lexerTestOne = new Lexer("COPY 10 R1\n"
				+ "COPY 6 R2\n"
				+ "PUSH 1R R1 ADD 5\n"
				+ "POP R2\n");
		
		LinkedList<Token> tokenList = lexerTestOne.Lex();
		System.out.println(tokenList);
		Parser parser = new Parser(tokenList);
		String[] loadArray = parser.Parse();
		
		MainMemory.load(loadArray);
		Processor process = new Processor();
		process.run();

		
		assertEquals(process.Registers[2].getSigned(), new Word(15).getSigned());
	}
	
		

	@Test
	void testParserLoad() throws Exception {

		var lexerTestOne = new Lexer("COPY 10 R1\n"
				+ "COPY 6 R2\n"
				+ "PUSH 1R R1 ADD 5\n"
				+ "LOAD 1017 R2\n");
		
		LinkedList<Token> tokenList = lexerTestOne.Lex();
		System.out.println(tokenList);
		Parser parser = new Parser(tokenList);
		String[] loadArray = parser.Parse();
		
		MainMemory.load(loadArray);
		Processor process = new Processor();
		process.run();
		
		
		assertEquals(process.Registers[2].getSigned(), new Word(15).getSigned());
	}
		


	@Test
	void testParserReturn() throws Exception {

		var lexerTestOne = new Lexer("COPY 10 R1\n"
				+ "COPY 6 R2\n"
				+ "PUSH 1R R1 ADD 5\n"
				+ "RETURN\n");
		
		LinkedList<Token> tokenList = lexerTestOne.Lex();
		System.out.println(tokenList);
		Parser parser = new Parser(tokenList);
		String[] loadArray = parser.Parse();
		
		MainMemory.load(loadArray);
		Processor process = new Processor();
		process.run();

		
		assertEquals(process.PC.getSigned(), new Word(16).getSigned());
	}

	

	@Test
	void testParserPeek() throws Exception {

		var lexerTestOne = new Lexer("COPY 10 R1\n"
				+ "COPY 6 R2\n"
				+ "PUSH 1R R1 ADD 5\n"
				+ "PEEK R1 R2 1013\n");
		
		LinkedList<Token> tokenList = lexerTestOne.Lex();
		System.out.println(tokenList);
		Parser parser = new Parser(tokenList);
		String[] loadArray = parser.Parse();
		
		MainMemory.load(loadArray);
		Processor process = new Processor();
		process.run();
		
		assertEquals(process.Registers[2].getSigned(), new Word(163873).getSigned());
	}
		

	

	
		
	
	
}
