public class Token {

	
	
	
	// Creates the different types of tokens
	enum TokenType {WORD, NUMBER, REGISTER, NEWLINE, MATH, ADD, SUBTRACT, MULTIPLY, AND, OR, NOT, XOR, COPY, HALT, BRANCH, JUMP, CALL, PUSH, 
		LOAD, RETURN, STORE, PEEK, POP, INTERRUPT, EQUAL, UNEQUAL, GREATER, LESS, GREATEROREQUAL, LESSOREQUAL, SHIFT, LEFT, RIGHT, NOTRECOGNIZED,
		TWOR, THREER, DESTONLY, ZEROR};
	
	
	
	
	String tokenValue;  // the actual tokens string value is
	int position;       // position of the character in terms of the current token its reading
	TokenType type;     // Differed token types a token can be labeled as
	
	
	// class constructor for new line
	public Token(TokenType type, int position) {
		
		this.type = type;
		
		this.position = position;
	}
	
	// class constructor for all other tokens with value
	public Token (TokenType type, int position, String value) {
		
		this.type = type;
		
		this.position = position;
		
		tokenValue = value;
		
		
	}
	
	
	// toString method override
	@SuppressWarnings("static-access")
	public String toString() {
		
		
		if(type == TokenType.WORD) {
			return "WORD: " + tokenValue;
		}
		
		if(type == TokenType.REGISTER) {
			return "REGISTER: " + tokenValue;
		}
		
		if(type == TokenType.NUMBER) {
			return "NUMBER: " + tokenValue;
		}
		
		if (type == TokenType.NOTRECOGNIZED) {
			return "String not recognized";
		}
		
		
		
		else if (type != null) {
			
			return "" + type;
		}
		
		
		else {
			
			return "This message should never show. if it does, fix it stupid";
		}
		
		
	}
	
	
	
	
}
