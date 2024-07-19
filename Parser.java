import java.util.LinkedList;
import java.util.Optional;

public class Parser {
	

	private TokenManager parserHandle; 		// TokenManager object
	private String[] memory;				// memory that MainMemory's load will take
	private int counter;					// counter to keep track of where in memory we are
	
	private String immediate;				// immediate
	private LinkedList<String> registers;	// holds all the registers being used
	private String function;				// function
	private String operationOpCode;			// operation part of Opcode
	
	int counterc = 0;
	// Parser class constructor
	public Parser(LinkedList<Token> tokenList) throws Exception {
		
		parserHandle = new TokenManager(tokenList);		// TokenManager object for parser
		memory = new String[1024];						// initializes memory to 1024 words (instructions)
		counter = 0;
	}
	
	
	// reads through linked list of Tokens provided by Lexer
	public String[] Parse() throws Exception {
		

		// while the LinkedList of tokens is not empty
		while(parserHandle.MoreTokens()) {
			
			Program();
		}
		
		// creates new array for correct size
		String[] outputMemory = new String[counter];
		for(int i=0; i<counter; i++) {
			outputMemory[i] = memory[i];
		}
		
		return outputMemory;
	}
	
	
	// program
	public void Program() throws Exception {
		
		Statements();
	}
	
	// statements
	public void Statements() throws Exception {
		
		// initializes registers, immediate, function for each statement
		registers = new LinkedList<>();
		immediate = "";
		function = "0000";
		
		
		Statement();	// recursively calls for single statement
		
		// checks for a new
		Optional<Token> checkForNewLine;
		checkForNewLine = parserHandle.MatchAndRemove(Token.TokenType.NEWLINE);
		
		
		// loops through statements until end of statements
		if(checkForNewLine.equals(Optional.empty())) {
			throw new Exception("Error Statements() Parser: NEWLINE token not detected after statement.");
		}
		
		// recursively calls same method if there are more tokens
		if(parserHandle.HowManyTokens()>1) {
			Statements();
		}
		
		
		
	}
	
	// each singular statement
	public void Statement() throws Exception {
		
		Token nextToken = parserHandle.Pop();				// pops the first token of the list
		String tokenValue = nextToken.tokenValue;			// has the string value of the token
		
		// checks if tokens string value is bits, but not of the token type number
		if(isBits(tokenValue) && !nextToken.type.equals(Token.TokenType.NUMBER)) {
			operationOpCode = tokenValue;
		}
		
		// special case: calls math if type is shift
		else if(nextToken.type.equals(Token.TokenType.SHIFT)) {
			math();
		}
		
		// else throw an exception
		else throw new Exception("Error(1) Statement() Parser: A nonvalid statement was entered");
		
		

		switch(nextToken.type) {
		
			case MATH:
				math();
				break;
			case BRANCH:
				branch();
				break;
			case HALT:
				halt();
				break;
			case COPY:
				copy();
				break;
			case JUMP:
				jump();
				break;
			case CALL:
				call();
				break;
			case PUSH:
				push();
				break;
			case POP:
				pop();
				break;
			case LOAD:
				load();
				break;
			case STORE:
				store();
				break;
			case RETURN:
				Return();
				break;
			case PEEK:
				peek();
				break;
			case INTERRUPT:
				break;
		default:
			throw new Exception("Error(2) Statement() Parser: A nonvalid statement was entered");
		
		}
		
		fillMemory();
		
	}
	
	
	// math
	/*
	 * 2R: MATH Rs1 MOP Rs2
	 * 3R: MATH RS1 MOP Rs2 Rd
	 * 
	 */
	private void math() throws Exception {

		Token nextToken = parserHandle.Pop();				// pops the first token of the list
		String tokenValue = nextToken.tokenValue;			// has the string value of the token
		
		// throw error if the token is not a MOP
		if(!isBits(tokenValue)) throw new Exception("Error(1) math() Parser: function code is not bits");
		
		function = tokenValue;		// sets the function
		
		registers.addAll(Registers());
		
		// math should never contain less than 2 registers
		if(registers.size()<2)  throw new Exception("Error(2) math() Parser: Math has less than 2 registers");
		
		
	}
	

	// branch
	/*
	 * 2R: BRANCH 2R Rs1 BOP Rd imm
	 * 3R: BRANCH 3R RS1 BOP Rs2 imm
	 * 
	 */
	private void branch() throws Exception {
		
		Token nextToken = parserHandle.Pop();				// pops the first token of the list
		String tokenValue = nextToken.tokenValue;			// has the string value of the token
		
		// throw error if the token is not 2r or 3r
		if(!nextToken.type.equals(Token.TokenType.TWOR) && !nextToken.type.equals(Token.TokenType.THREER)) {
			throw new Exception("Error(1) branch() Parser: Token is not either 2R or 3R");
		}
		
		// if there are two registers
		if(nextToken.type.equals(Token.TokenType.TWOR)) {

			// adds register to linked list
			registers.addAll(Registers());
				
			
			// pops off function token
			nextToken = parserHandle.Pop();			
			tokenValue = nextToken.tokenValue;		

			// throw error if the token is not a function
			if(!isBits(tokenValue) && !nextToken.type.equals(Token.TokenType.NUMBER)) throw new Exception("Error(2) branch() Parser: Token is not a function");
			function = tokenValue;
			

			// adds register to linked list
			registers.addAll(Registers());
				

			// pops off immediate token
			nextToken = parserHandle.Pop();			
			tokenValue = nextToken.tokenValue;		

			
			// throw error if the token is not a number
			if(!nextToken.type.equals(Token.TokenType.NUMBER)) {
				throw new Exception("Error(3) branch() Parser: Token is not a number");
			}
			
			// gets immediate
			immediate = tokenValue;
			
		}
		
		// if there are three regsiters
		else if(nextToken.type.equals(Token.TokenType.THREER)) {

			// adds register to linked list
			registers.addAll(Registers());
				
			
			// pops off function token
			nextToken = parserHandle.Pop();			
			tokenValue = nextToken.tokenValue;		

			// throw error if the token is not a function
			if(!isBits(tokenValue)) throw new Exception("Error(2) branch() Parser: Token is not a function");
			function = tokenValue;
			

			// adds register to linked list
			registers.addAll(Registers());
				

			// pops off immediate token
			nextToken = parserHandle.Pop();			
			tokenValue = nextToken.tokenValue;		


			// throw error if the token is not a number
			if(!nextToken.type.equals(Token.TokenType.NUMBER)) {
				throw new Exception("Error(3) branch() Parser: Token is not a number");
			}
			
			// adds rd register
			registers.add("00000");
			
			// gets immediate
			immediate = tokenValue;
			
		}
		
		
		
		// throw error if less than two registers
		if(registers.size()<2)  throw new Exception("Error(4) branch() Parser: Less than 2 registers");
					
		
	}
	
	// halt
	/*
	 * 0R: HALT
	 */
	private void halt() throws Exception {
		fillMemory("00000000000000000000000000000000");
	}
	

	// copy
	/*
	 * 1R: COPY imm Rd
	 */
	private void copy() throws Exception {

		Token nextToken = parserHandle.Pop();				// pops the first token of the list
		String tokenValue = nextToken.tokenValue;			// has the string value of the token
		
		if(!nextToken.type.equals(Token.TokenType.NUMBER)) throw new Exception("Error(1) copy() Parser: token is not a number");
		
		immediate = tokenValue;		// sets the immediate
		
		registers.addAll(Registers());
		
		if(registers.size()>1) throw new Exception("Error(2) copy() Parser: Copy has more than 1 register");
		
		
	}
	
	// jump
	/*
	 * 0R: JUMP 0R imm
	 * 1R: JUMP 1R imm
	 * 
	 */
	private void jump() throws Exception {

		Token nextToken = parserHandle.Pop();				// pops the first token of the list
		String tokenValue = nextToken.tokenValue;			// has the string value of the token
		
		// throw error if the token is not destonly(1r) or a number
		if(!nextToken.type.equals(Token.TokenType.DESTONLY) && !nextToken.type.equals(Token.TokenType.NUMBER) && !nextToken.type.equals(Token.TokenType.ZEROR)) {
			throw new Exception("Error(1) jump() Parser: Token is not either destonly, 0r or immediate value");
		}
		
		// if destonly
		if(nextToken.type.equals(Token.TokenType.DESTONLY)) {
			
			// pops off immediate token
			nextToken = parserHandle.Pop();			
			tokenValue = nextToken.tokenValue;		
			
			// if number token not found, throw exception
			if (!nextToken.type.equals(Token.TokenType.NUMBER))	throw new Exception("Error(2) jump() Parser: Token is not a number");
			immediate = tokenValue;
			
			
		}
		
		// else if immediate value (0r)
		else if (nextToken.type.equals(Token.TokenType.NUMBER)) {
			immediate = tokenValue;
		}
		
		// else if 0r with 0r token
		else if (nextToken.type.equals(Token.TokenType.ZEROR)){

			// pops off immediate token
			nextToken = parserHandle.Pop();			
			tokenValue = nextToken.tokenValue;	
			
			if (nextToken.type.equals(Token.TokenType.NUMBER)) {
				immediate = tokenValue;
			}
			
		}
		
	}
	
	// call
	/*
	 * 2R: CALL 2R Rs1 BOP Rd imm
	 * 3R: CALL 3R RS1 BOP Rs2 imm
	 * 
	 */
	private void call() throws Exception {
		
		Token nextToken = parserHandle.Pop();				// pops the first token of the list
		String tokenValue = nextToken.tokenValue;			// has the string value of the token
		
		// throw error if the token is not 2r or 3r
		if(!nextToken.type.equals(Token.TokenType.TWOR) && !nextToken.type.equals(Token.TokenType.THREER)) {
			throw new Exception("Error(1) call() Parser: Token is not either 2R or 3R");
		}
		
		// if there are two registers (call 2r)
		if(nextToken.type.equals(Token.TokenType.TWOR)) {

			// adds register to linked list
			registers.addAll(Registers());
				
			
			// pops off function token
			nextToken = parserHandle.Pop();			
			tokenValue = nextToken.tokenValue;		

			// throw error if the token is not a function
			if(!isBits(tokenValue)) throw new Exception("Error(2) call() Parser: Token is not a function");
			function = tokenValue;
			

			// adds register to linked list
			registers.addAll(Registers());
				

			// pops off immediate token
			nextToken = parserHandle.Pop();			
			tokenValue = nextToken.tokenValue;		

			
			// throw error if the token is not a number
			if(!nextToken.type.equals(Token.TokenType.NUMBER)) {
				throw new Exception("Error(3) call() Parser: Token is not a function");
			}
			
			// gets immediate
			immediate = tokenValue;
			
		}
		
		// if there are three regsiters (call 3r)
		if(nextToken.type.equals(Token.TokenType.THREER)) {

			// adds register to linked list
			registers.addAll(Registers());
				
			
			// pops off function token
			nextToken = parserHandle.Pop();			
			tokenValue = nextToken.tokenValue;		

			// throw error if the token is not a function
			if(!isBits(tokenValue)) throw new Exception("Error(2) call() Parser: Token is not a function");
			function = tokenValue;
			

			// adds register to linked list
			registers.addAll(Registers());
				

			// pops off immediate token
			nextToken = parserHandle.Pop();			
			tokenValue = nextToken.tokenValue;		


			// throw error if the token is not a number
			if(!nextToken.type.equals(Token.TokenType.NUMBER)) {
				throw new Exception("Error(3) call() Parser: Token is not a function");
			}
			
			// adds rd register
			registers.add("00000");
			
			// gets immediate
			immediate = tokenValue;
			
		}
		
		
		
		// throw error if less than two registers
		if(registers.size()<2)  throw new Exception("Error(4) call() Parser: Less than 2 registers");
					
		
	}
	
	// push
	/*
	 * push:
	 * 0R: unused
	 * 1R: PUSH Rd MOP imm
	 * 2R: PUSH 2R Rd MOP Rs0
	 * 3R: PUSH 3R Rs1 MOP Rs2
	 * 
	 * call:
	 * 0R: PUSH imm
	 * 1R: PUSH	Rd imm
	 * 
	 */
	private void push() throws Exception {

		Token nextToken = parserHandle.Pop();				// pops the first token of the list
		String tokenValue = nextToken.tokenValue;			// has the string value of the token
		
		Token registerNum = null;	// either destonly(1r) 2r or 3r
		
		// if there is an amount of registers specified, save the string value, and pop next token off
		if(nextToken.type.equals(Token.TokenType.DESTONLY) || nextToken.type.equals(Token.TokenType.TWOR) || nextToken.type.equals(Token.TokenType.THREER)) {
			registerNum = nextToken;

			nextToken = parserHandle.Pop();			
			tokenValue = nextToken.tokenValue;			
		}
		
		
		// throw error if the token is not register or a number
		if(!nextToken.type.equals(Token.TokenType.REGISTER) && !nextToken.type.equals(Token.TokenType.NUMBER)) {
			throw new Exception("Error(2) push() Parser: Token is not either a register or number");
		}
		
		// if token is a register (push 3r, 2r, 1r, call push 1r)
		if(nextToken.type.equals(Token.TokenType.REGISTER)) {
			registers.add(intToBinary(tokenValue, 5));	// adds register to register list
			
			// pops off function or immediate token
			nextToken = parserHandle.Pop();			
			tokenValue = nextToken.tokenValue;		

			// if next token is a MOP, set function (Push 3r, 2r, 1r)
			if(isBits(tokenValue) && !nextToken.type.equals(Token.TokenType.NUMBER)) {
				function = tokenValue;
				
				// pops off register or immediate token
				nextToken = parserHandle.Pop();			
				tokenValue = nextToken.tokenValue;		

				// if register, add to linked list (Push 3r, 2r)
				if(nextToken.type.equals(Token.TokenType.REGISTER)) {
					registers.add(intToBinary(tokenValue, 5));	// adds register to register list
					
					// if push is 3r, set rd to 0's (Push 3r)
					if(registerNum.type.equals(Token.TokenType.THREER)) {
						registers.add("00000");
					}
					
				
				}
			
				// else if push is destonly and encounters a number (Push 1r)
				else if(nextToken.type.equals(Token.TokenType.NUMBER)) {
					immediate = tokenValue;
				}
				
				else throw new Exception("Error(3) push() Parser: Token was not either a register or number");
			}
			
			// else if next token is a number (Call push 1r)
			else if(nextToken.type.equals(Token.TokenType.NUMBER)) {
				operationOpCode = "010";					// becomes a call operation
				immediate = tokenValue;
			}
			
			// else throw
			else throw new Exception("Error(4) push() Parser: Token is not either a MOP or a number");
			
			
		}
		
		// else if token is a number (Call push 0r)
		else if(nextToken.type.equals(Token.TokenType.NUMBER)) {
			operationOpCode = "010";					// becomes a call operation
			immediate = tokenValue;
		}	
	}
	
	// pop
	/*
	 * 1R: POP Rd
	 */
	private void pop() throws Exception {
		registers.addAll(Registers());
		if(registers.size()>1) throw new Exception("Error pop() Parser: More than one register used");
	}
	
	// load
	/*
	 * 1R: LOAD imm Rd
	 * 2R: LOAD imm Rs0 Rd
	 * 3R: LOAD Rs1 Rs2 Rd
	 * 
	 */
	private void load() throws Exception {

		Token nextToken = parserHandle.Pop();				// pops the first token of the list
		String tokenValue = nextToken.tokenValue;			// has the string value of the token
		
		// for 3R, adds register popped off, then adds the rest to register linked list
		if(nextToken.type.equals(Token.TokenType.REGISTER)) {
			registers.add(intToBinary(tokenValue, 5));
			registers.addAll(Registers());
		}
		
		// for destonly and 2R
		else if(nextToken.type.equals(Token.TokenType.NUMBER)) {
			registers.addAll(Registers());
			
			immediate = tokenValue;
		}
		
		else throw new Exception("Error(2) load() Parser: Token is neither a register or a number. TokenType: " + nextToken);
	}
	
	// store
	/*
	 * 1R: STORE imm Rd
	 * 2R: STORE imm Rs0 Rd
	 * 3R: STORE Rs1 Rs2 Rd
	 * 
	 */
	private void store() throws Exception {

		Token nextToken = parserHandle.Pop();				// pops the first token of the list
		String tokenValue = nextToken.tokenValue;			// has the string value of the token
		
		// for 3R, adds register popped off, then adds the rest to register linked list
		if(nextToken.type.equals(Token.TokenType.REGISTER)) {
			registers.add(intToBinary(tokenValue, 5));
			registers.addAll(Registers());
		}
		
		// for destonly and 2R
		else if(nextToken.type.equals(Token.TokenType.NUMBER)) {
			registers.addAll(Registers());
			immediate = tokenValue;
		}
		
		else throw new Exception("Error(2) store() Parser: Token is not either a register or a number");
	}
	
	// return
	/*
	 * 0R: RETURN
	 * 
	 */
	private void Return() throws Exception {
		fillMemory("00000000000000000000000000010000");
	}
	
	// peek
	/*
	 * 2R: PEEK Rd Rs0 imm
	 * 3R: PEEK Rd Rs1 Rs2
	 * 
	 */
	private void peek() throws Exception {

		// adds all registers
		registers.addAll(Registers());
		
		// for 2R, gets immediate
		if(registers.size()==2) {
			Token nextToken = parserHandle.Pop();			
			String tokenValue = nextToken.tokenValue;		
			
			if(nextToken.type.equals(Token.TokenType.NUMBER)) {
				immediate = tokenValue;
			}
			else throw new Exception("Error(1) peek() Parser: Expected token was not a number");
		}
		else if (registers.size()>3)throw new Exception("Error(2) peek() Parser: Number of registers exceed limit");
		
	}
	
	
	
	// registers
	private LinkedList<String> Registers() throws Exception {
		
		Token nextToken = parserHandle.Pop();				// pops the first token of the list
		LinkedList<String> checkRegisters = new LinkedList<>();
		
		
		// throws error if a non register token is seen
		if(!nextToken.type.equals(Token.TokenType.REGISTER)) throw new Exception("Error Registers() Parser: Expected Token was not a register.");
		
		
		// loops through statement to get registers 
		while(nextToken.type.equals(Token.TokenType.REGISTER)) {
			
			// gets register number as binary
			String registerAsBinary = intToBinary(nextToken.tokenValue, 5);
			
			checkRegisters.add(registerAsBinary);
			nextToken = parserHandle.MatchAndRemove(Token.TokenType.REGISTER).orElse(new Token(Token.TokenType.NOTRECOGNIZED,0));
		}
		
		return checkRegisters;
		
	}
	
	
	// helper method that fills memory
	private void fillMemory(String word) {
		memory[counter] = word;
		counter++;
	}
	
	// helper method that fills memory
	private void fillMemory() throws Exception {
		
		String word = "";

		
		// if there is an immediate value
		if(!immediate.equals("")) {
		
			// constructs word
			switch(this.registers.size()) {
			
				case(0):
					word = intToBinary(immediate, 27) + operationOpCode + "00";
					break;
				case(1):
					word = intToBinary(immediate, 18) + function + registers.pop() + operationOpCode + "01";
					break;
				case(2):
					word = intToBinary(immediate, 13) + registers.pop() + function + registers.pop() + operationOpCode + "11";
					break;
				case(3):
					word = intToBinary(immediate, 8) + registers.pop() + registers.pop() + function + registers.pop() + operationOpCode + "10";
					break;
			}
		}
		
		else if(immediate.equals("")) {

			// constructs word
			switch(this.registers.size()) {
			
				case(0):
					word = "000000000000000000000000000" + operationOpCode + "00";
					break;
				case(1):
					word = "000000000000000000" + function + registers.pop() + operationOpCode + "01";
					break;
				case(2):
					word = "0000000000000" + registers.pop() + function + registers.pop() + operationOpCode + "11";
					break;
				case(3):
					word = "00000000" + registers.pop() + registers.pop() + function + registers.pop() + operationOpCode + "10";
					break;
			}
		}
		
		else throw new Exception("Error fillMemory() Parser: Immediate not recognized.");
		
		
		fillMemory(word);
		
	}
	
	
	// checks if a string consists of all bits
	// returns false if it does not consist of bits
	private boolean isBits(String check) {
		
		for(int i=0; i<check.length(); i++) {
			if(check.charAt(i)!='1' && check.charAt(i)!='0') {
				return false;
			}
		}
		
		return true;
	}
	
	
	// converts an int to binary 
	private String intToBinary(String number, int length) {
		
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
		
		
		return numberAsBits;
	}
	
	
	
	
}
