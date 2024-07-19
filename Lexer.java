import java.util.HashMap;

import java.util.LinkedList;
import java.util.Map;



/*
Notes for self:
fix decimal in processnumber
*/


public class Lexer {

	
	
	private StringHandler handle;     // StringHandler object
	private int position=0;           // Holds what character position a character is in
	
	// Linked List of tokens
	LinkedList<Token> tokenValues = new LinkedList<Token>();
	
	
	private HashMap<String, Token.TokenType> keyWords = new HashMap<>();
	
	
	// Constructor
	public Lexer(String assemblyFile) throws Exception {
		
		
		// Creates StringHandler object
		handle = new StringHandler(assemblyFile);
		
		
		
		// adds key and value pairs to keyWords HashMap
		keyWords.put("math", Token.TokenType.MATH);
		keyWords.put("add", Token.TokenType.ADD);
		keyWords.put("subtract", Token.TokenType.SUBTRACT);
		keyWords.put("multiply", Token.TokenType.MULTIPLY);
		keyWords.put("and", Token.TokenType.AND);
		keyWords.put("or", Token.TokenType.OR);
		keyWords.put("not", Token.TokenType.NOT);
		keyWords.put("xor", Token.TokenType.XOR);
		keyWords.put("copy", Token.TokenType.COPY);
		keyWords.put("halt", Token.TokenType.HALT);
		keyWords.put("branch", Token.TokenType.BRANCH);
		keyWords.put("jump", Token.TokenType.JUMP);
		keyWords.put("call", Token.TokenType.CALL);
		keyWords.put("push", Token.TokenType.PUSH);
		keyWords.put("load", Token.TokenType.LOAD);
		keyWords.put("return", Token.TokenType.RETURN);
		keyWords.put("store", Token.TokenType.STORE);
		keyWords.put("peek", Token.TokenType.PEEK);
		keyWords.put("pop", Token.TokenType.POP);
		keyWords.put("interrupt", Token.TokenType.INTERRUPT);
		keyWords.put("eq", Token.TokenType.EQUAL);
		keyWords.put("neq", Token.TokenType.UNEQUAL);
		keyWords.put("gt", Token.TokenType.GREATER);
		keyWords.put("lt", Token.TokenType.LESS);
		keyWords.put("ge", Token.TokenType.GREATEROREQUAL);
		keyWords.put("le", Token.TokenType.LESSOREQUAL);
		keyWords.put("shift", Token.TokenType.SHIFT);
		keyWords.put("left", Token.TokenType.LEFT);
		keyWords.put("right", Token.TokenType.RIGHT);
		keyWords.put("r", Token.TokenType.REGISTER);
		keyWords.put("\n", Token.TokenType.NEWLINE);
		keyWords.put("2r", Token.TokenType.TWOR);
		keyWords.put("3r", Token.TokenType.THREER);
		keyWords.put("1r", Token.TokenType.DESTONLY);
		
		
		//LinkedList<Token> tokenList = Lex();
		//Parser parser = new Parser(tokenList);
		
	}
	
	
	// Method that uses StringHandler to read through AWK file
	public LinkedList<Token> Lex() throws Exception {
		
		
		// While there is still text left to read
		while (handle.IsDone() == false){
		
			
			
			// if character is a space or tab, increment position
			if (handle.Peek(0) == (' ') || handle.Peek(0) == '	') {
				
				position++;
				handle.GetChar();
			}
			
			// If the character is a letter, call ProcessWord and add word to linked list
			else if (Character.isLowerCase(handle.Peek(0)) || Character.isUpperCase(handle.Peek(0)) ) {
				
				// changes char to String, Calls ProcessWord, then adds the WORD token to linked list
				tokenValues.add(ProcessWord(Character.toString(handle.GetChar())));
				
			}
			
			
			// If the character is a number, call ProcessNumber and add number to linked list
			else if (Character.isDigit(handle.Peek(0))) {
				
				// changes char to String, Calls ProcessNumber, then adds the NUMBER token to linked list
				tokenValues.add(ProcessNumber(Character.toString(handle.GetChar())));
				
			}
			

			// if peek sees a comment starting
			else if (handle.Peek(0) == ('#')) {
				
				// loop through rest of line
				while(handle.Peek(0) != '\n' && handle.IsDone() == false ) {
					position++;
					handle.GetChar();
				}
			}
			

			// if character is \n, add a NEWLINE token to Linked List 
			else if (handle.Peek(0) == ('\n')) {
				
				
				handle.GetChar();
				Token.TokenType newline = Token.TokenType.NEWLINE;
				
				tokenValues.add(new Token(newline, position));
				position=0;
			}
			
			// if symbol not recognized
			else {
				position++;
				handle.GetChar();
				tokenValues.add(new Token(Token.TokenType.NOTRECOGNIZED, position));
			}
			
	}

		// Adds a NEWLINE to the end of the input
		tokenValues.add(new Token(Token.TokenType.NEWLINE, position));
		
		
		return tokenValues;
	}
	
	
	
	// processes word tokens
	private Token ProcessWord(String x) {
		
		String word = x;
		position++;
		
		
		
		// while recognizable letters are seen, create a word token
		while(Character.isLowerCase(handle.Peek(0)) || Character.isUpperCase(handle.Peek(0))) {
			
			word+= handle.GetChar();
			position++;
		}
		
		word = word.toLowerCase();
		
		
		// Creates a WORD TokenType
		Token.TokenType finalWord = Token.TokenType.WORD;
		
		
		// for each loop that iterates through keyWords HashMap
		for (Map.Entry<String, Token.TokenType> i : keyWords.entrySet()) {
		
			String key = i.getKey();    // Holds the value of the key
			
			
			// handles register tokens
			if(word.equals("r") && Character.isDigit(handle.Peek(0))) {
				
				word = "";
				// loops through numbers following register to get which register
				while(Character.isDigit(handle.Peek(0))) {
					word+= handle.GetChar();
					position++;
				}
				
				return new Token(Token.TokenType.REGISTER, position, word);
			}
			
			// if word made in ProcessWord is a keyword, return keyword token
			else if (word.equals(key)) {
				
				// sets all MOP and BOP string to their 4 bit counterpart
				switch(word) {
					case "and":
						return new Token(i.getValue(), position, "1000");
					case "or":
						return new Token(i.getValue(), position, "1001");
					case "xor":
						return new Token(i.getValue(), position, "1010");
					case "not":
						return new Token(i.getValue(), position, "1011");
					case "left":
						return new Token(i.getValue(), position, "1100");
					case "right":
						return new Token(i.getValue(), position, "1101");
					case "add":
						return new Token(i.getValue(), position, "1110");
					case "subtract":
						return new Token(i.getValue(), position, "1111");
					case "multiply":
						return new Token(i.getValue(), position, "0111");
					
					
					case "eq":
						return new Token(i.getValue(), position, "0000");
					case "neq":
						return new Token(i.getValue(), position, "0001");
					case "lt":
						return new Token(i.getValue(), position, "0010");
					case "ge":
						return new Token(i.getValue(), position, "0011");
					case "gt":
						return new Token(i.getValue(), position, "0100");
					case "le":
						return new Token(i.getValue(), position, "0101");
					
						
					case "math":
						return new Token(i.getValue(), position, "000");
					case "branch":
						return new Token(i.getValue(), position, "001");
					case "call":
						return new Token(i.getValue(), position, "010");
					case "push":
						return new Token(i.getValue(), position, "011");
					case "pop":
						return new Token(i.getValue(), position, "110");
					case "load":
						return new Token(i.getValue(), position, "100");
					case "store":
						return new Token(i.getValue(), position, "101");
						
				
					case "jump":
						return new Token(i.getValue(), position, "001");
					case "peek":
						return new Token(i.getValue(), position, "110");
					

					case "halt":
						return new Token(i.getValue(), position, "000");
					case "copy":
						return new Token(i.getValue(), position, "000");
					case "return":
						return new Token(i.getValue(), position, "100");
					case "interrupt":
						return new Token(i.getValue(), position, "110");
						
						
				}
				
				
				return new Token(i.getValue(), position);
				
			}
			
			
			
		}
		
		return new Token(finalWord, position, word);
		
	}
	
	
	
	// processes number tokens
	private Token ProcessNumber(String x) throws Exception {
		
		String number = x;
		position++;
		
		if(handle.Peek(0) == 'r' || handle.Peek(0) == 'R') {
			position++;
			handle.GetChar();
			
			switch(number) {
			
				case "0":
					return new Token(Token.TokenType.ZEROR, position, "0r");
				case "1":
					return new Token(Token.TokenType.DESTONLY, position, "1r");
				case "2":
					return new Token(Token.TokenType.TWOR, position, "2r");
				case "3":
					return new Token(Token.TokenType.THREER, position, "3r");
			}
			
		}
		
		while(Character.isDigit(handle.Peek(0)) || handle.Peek(0) == '.') {
			
			// counts how many decimals are in the string
			if (handle.Peek(0) == '.') {
				throw new Exception("Error ProccesNumber() Lexer: Entered floating number.");
			}
			
			
			number += handle.GetChar();
			position++;
			
		}
		
		// Creates a NUMBER TokenType
		 Token newToken = new Token(Token.TokenType.NUMBER, position, number);
		 return newToken;
		 
		
	}
}