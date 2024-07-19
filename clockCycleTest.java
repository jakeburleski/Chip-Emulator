import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

public class clockCycleTest {

	@Test
	void arrayTest() throws Exception{

		var lexerTestOne = new Lexer(
				"COPY 100 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 101 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 102 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 103 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 104 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 105 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 106 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 107 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 108 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 109 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 110 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 111 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 112 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 113 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 114 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 115 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 116 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 117 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 118 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 119 R1\n"
				+ "STORE 25 R1\n"
				
				+ "LOAD 100 R0 R2\n"		// loads index from array
				+ "MATH add R2 R3 R3\n"	// adds index from array + 0 into new register
				+ "LOAD 101 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 102 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 103 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 104 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 105 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 106 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 107 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 108 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 109 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 110 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 111 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 112 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 113 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 114 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 115 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 116 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 117 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 118 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				+ "LOAD 119 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"
				
	
				
				
				+ "HALT\n");
		
		LinkedList<Token> tokenList = lexerTestOne.Lex();
		System.out.println(tokenList);
		Parser parser = new Parser(tokenList);
		String[] loadArray = parser.Parse();
		
		MainMemory.load(loadArray);
		Processor process = new Processor();
		System.out.print("array: ");
		process.run();
		
		// tests that the result is 350
		assertEquals(process.Registers[3].getSigned(), new Word(350).getSigned());
		
	}

	
	@Test
	void linkedListTest() throws Exception{

		var lexerTestOne = new Lexer(
				"COPY 439 R1\n"
				+ "COPY 1 R3\n"
				
				
				
				+ "STORE 10 R1\n"			// stores 10 into memory at 439
				+ "MATH ADD R0 R1 R2\n"		// stores the pointer of R1 into R2
				+ "MATH SUBTRACT R1 R3 R1\n"// decrements R1 by 1
				+ "STORE 0 R2 R1\n"			// stores the pointer into the index in memory before the value
				+ "MATH SUBTRACT R1 R3 R1\n"// moves to next open index
				
				+ "STORE 25 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"		
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 10 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"			
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 25 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 10 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"			
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 25 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 10 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"			
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 25 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 10 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"			
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 25 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 10 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"			
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 25 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 10 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"			
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 25 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 10 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"			
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 25 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 10 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"			
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 25 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 10 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"			
				+ "MATH SUBTRACT R1 R3 R1\n"

				+ "STORE 25 R1\n"
				+ "MATH ADD R0 R1 R2\n"	
				+ "MATH SUBTRACT R1 R3 R1\n"
				+ "STORE 0 R2 R1\n"
				+ "MATH SUBTRACT R1 R3 R1\n"

				
			
				
				+ "LOAD 400 R0 R4\n"	// gets pointer to first element
				+ "LOAD R0 R4 R5\n"		// loads element pointer is pointing at
				+ "MATH add R5 R6 R6\n"	// adds element to new register
				+ "MATH add R4 R3 R4\n"	// goes to next index in linked list
				
				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				+ "MATH add R4 R3 R4\n"

				+ "LOAD R4 R0 R4\n"
				+ "LOAD R0 R4 R5\n"	
				+ "MATH add R5 R6 R6\n"	
				
				
				
				+ "HALT\n");
		
		LinkedList<Token> tokenList = lexerTestOne.Lex();
		System.out.println(tokenList);
		Parser parser = new Parser(tokenList);
		String[] loadArray = parser.Parse();
		
		MainMemory.load(loadArray);
		Processor process = new Processor();
		System.out.print("LinkedList: ");
		process.run();
		
		// tests that the result is 350
		assertEquals(process.Registers[6].getSigned(), new Word(350).getSigned());
		
	}
	
	@Test
	void arrayBackwards() throws Exception{

		var lexerTestOne = new Lexer(
				"COPY 400 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 401 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 402 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 403 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 404 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 405 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 406 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 407 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 408 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 409 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 410 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 411 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 412 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 413 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 414 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 415 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 416 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 417 R1\n"
				+ "STORE 25 R1\n"
				+ "COPY 418 R1\n"
				+ "STORE 10 R1\n"
				+ "COPY 419 R1\n"
				+ "STORE 25 R1\n"
				
				+ "COPY 419 R4\n"
				+ "COPY 1 R5\n"
				
				//419
				+ "LOAD R4 R0 R2\n"		// loads index from array
				+ "MATH add R2 R3 R3\n"	// adds index from array + 0 into new register
				+ "MATH subtract R4 R5 R4\n"
				
				//418
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//417
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//416
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//415
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//414
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//413
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//412
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//411
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//410
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//409
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//408
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//407
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//406
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//405
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//404
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//403
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"

				//402
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"
				
				//401
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				+ "MATH subtract R4 R5 R4\n"
				
				//400
				+ "LOAD R4 R0 R2\n"		
				+ "MATH add R2 R3 R3\n"	
				
				
				+ "HALT\n");
		
		LinkedList<Token> tokenList = lexerTestOne.Lex();
		System.out.println(tokenList);
		Parser parser = new Parser(tokenList);
		String[] loadArray = parser.Parse();
		
		MainMemory.load(loadArray);
		Processor process = new Processor();
		System.out.print("Backwards array: ");
		process.run();
		
		// tests that the result is 350
		assertEquals(process.Registers[3].getSigned(), new Word(350).getSigned());
		
	}
		
	
}
