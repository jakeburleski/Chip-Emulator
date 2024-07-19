/* note that: 
 * in the array, index 0 is the right most (smallest value) bit of the word,
 * meaning that index 0,1,2,3 in terms of a word are 2,4,8,16
 */

public class Word {
	
	
	Bit[] bitArray = new Bit[32];	// holds array of 32 instances of bit class
	

	
	
	
	// class constructor
	public Word(Bit[] bitArray) {
		
		this.bitArray = bitArray.clone();
	}
	
	public Word() {
		// changes all bits to false
		for(int i=0; i<bitArray.length; i++) {
			this.bitArray[i] = new Bit(false);
		}
	}
	
	// makes a word based of a string of 1's and 0's
	public Word(String data) throws Exception {
		// loops through the string and creates a new word
		for(int j=0; j<this.bitArray.length; j++) {
			if(data.charAt(j)=='0') this.setBit(j, new Bit(false));
			else if(data.charAt(j)=='1') this.setBit(j, new Bit(true));
			else {
				throw new Exception("Error Word(): Unexpected character");
			}
		}
	}
	
	// same as set(), only used for testing
	public Word(int value) throws Exception {
		// changes all bits to false
		for(int i=0; i<bitArray.length; i++) {
			this.bitArray[i] = new Bit(false);
		}
		
		
		
		
		// for positive numbers
		if(value>=0) {
			
			int holdVal = value;
			// for words size-1 while i is >= the amount, right shift the bits by "amount" amount
			for(int i=bitArray.length-1; i>=0; i--) {
				if((Math.pow(2, i))<=holdVal) {
					this.bitArray[i] = new Bit(true);
					holdVal -= Math.pow(2, i);
				}
			}
		}
		
		// for negative numbers
		else if(value<0) {
			int negativeVal = value * (-1);
			// for words size-1 while i is >= the amount, right shift the bits by "amount" amount
			for(int i=bitArray.length-1; i>=0; i--) {
				if((Math.pow(2, i))<=negativeVal) {
					this.bitArray[i] = new Bit(true);
					negativeVal -= Math.pow(2, i);
				}
			}
			
			// flips the bits of the positive number
			for(int i=0; i<bitArray.length; i++) {
				bitArray[i].toggle();
			}

			// adds 1 to the newly flipped word to get the correct positive decimal of the negative word
			for(int i=0; i<bitArray.length; i++) {
				
				if(bitArray[i].getValue()==true) {
					bitArray[i].toggle();
				}
				else if(bitArray[i].getValue()==false) {
					bitArray[i].toggle();
					break;
				}
				
				// should not get here unless something is wrong with the code
				else {
					throw new Exception("Error(1): error occured in getSigned()");
				}
			}
			
		}
	}
	
	
	
	
	// sets all bits to false or true
	public Word(boolean choose) {
		if(choose==false) {
			// changes all bits to false
			for(int i=0; i<bitArray.length; i++) {
				this.bitArray[i] = new Bit(false);
			}
		}
		
		else if(choose==true) {
			// changes all bits to false
			for(int i=0; i<bitArray.length; i++) {
				this.bitArray[i] = new Bit(true);
			}
		}
	}
	
	
	// gets a new bit that has the same value as bit i
	public Bit getBit(int i) {
		Bit returnBit = bitArray[i];
		return returnBit;
	}
	
	// sets bit i's value
	public void setBit(int i, Bit value) {
		bitArray[i] = value;
	}
	
	// performs and on 2 words, and returns its result
	public Word and(Word other) throws Exception {
		Word firstWord = new Word();	// creates new word copying the current
		firstWord.copy(this.bitArray);
		
		Word returnWord = new Word();		// word to be returned
		
		Bit trueBit = new Bit(true);		// true bit used to fill array
		Bit falseBit = new Bit(false);		// false but used to fill array
		// for the length of a word, starting at the smallest bit, perform and on 2 words
		for(int i=0; i<32; i++) {
			
			// if both words bit in given index are true, new word's bit in same index is true
			if(firstWord.bitArray[i].getValue()==true && other.bitArray[i].getValue()==true) {
				returnWord.bitArray[i] = trueBit;
			}
			
			// if both words bits in given index are false, new word's bit in same index is false
			else if(firstWord.bitArray[i].getValue()==false && other.bitArray[i].getValue()==false) {
				returnWord.bitArray[i] = falseBit;
			}

			// if first word's bit is false and second's bit is true in given index, new word's bit in same index is false
			else if(firstWord.bitArray[i].getValue()==false && other.bitArray[i].getValue()==true) {
				returnWord.bitArray[i] = falseBit;
			}

			// if first word's bit is true and second's bit is false in given index, new word's bit in same index is false
			else if(firstWord.bitArray[i].getValue()==true && other.bitArray[i].getValue()==false) {
				returnWord.bitArray[i] = falseBit;
			}
			
			// should not have an error unless code is wrong
			else {
				throw new Exception("Error: error occured in and word operator");
			}
		}
		
		return returnWord;
		
	}

	// performs or on 2 words, and returns its result
	public Word or(Word other) throws Exception {
		Word firstWord = new Word();	// creates new word copying the current
		firstWord.copy(this.bitArray);
		
		Word returnWord = new Word();		// word to be returned
		

		Bit trueBit = new Bit(true);		// true bit used to fill array
		Bit falseBit = new Bit(false);		// false but used to fill array
		
		// for the length of a word, starting at the smallest bit, perform and on 2 words
		for(int i=0; i<32; i++) {
			
			// if either words bit in given index is true, new word's bit in same index is true
			if(firstWord.bitArray[i].getValue()==true || other.bitArray[i].getValue()==true) {
				returnWord.bitArray[i] = trueBit;
			}
			
			// if both words bits in given index are false, new word's bit in same index is false
			else if(firstWord.bitArray[i].getValue()==false && other.bitArray[i].getValue()==false) {
				returnWord.bitArray[i] = falseBit;
			}
			
			// should not have an error unless code is wrong
			else {
				throw new Exception("Error: error occured in or word operator");
			}
		}
		
		return returnWord;
	}

	// performs xor on 2 words, and returns its result
	public Word xor(Word other) throws Exception {
		Word firstWord = new Word();	// creates new word copying the current
		firstWord.copy(this.bitArray);
		
		Word returnWord = new Word();		// word to be returned

		Bit trueBit = new Bit(true);		// true bit used to fill array
		Bit falseBit = new Bit(false);		// false but used to fill array
		
		// for the length of a word, starting at the smallest bit, perform xor on 2 words
		for(int i=0; i<32; i++) {
			
			// if both words bit in given index are true, new word's bit in same index is false
			if(firstWord.bitArray[i].getValue()==true && other.bitArray[i].getValue()==true) {
				returnWord.bitArray[i] = falseBit;
			}
			
			// if both words bits in given index are false, new word's bit in same index is false
			else if(firstWord.bitArray[i].getValue()==false && other.bitArray[i].getValue()==false) {
				returnWord.bitArray[i] = falseBit;
			}

			// if first word's bit is false and second's bit is true in given index, new word's bit in same index is true
			else if(firstWord.bitArray[i].getValue()==false && other.bitArray[i].getValue()==true) {
				returnWord.bitArray[i] = trueBit;
			}

			// if first word's bit is true and second's bit is false in given index, new word's bit in same index is true
			else if(firstWord.bitArray[i].getValue()==true && other.bitArray[i].getValue()==false) {
				returnWord.bitArray[i] = trueBit;
			}
			
			// should not have an error unless code is wrong
			else {
				throw new Exception("Error: error occured in xor word operator");
			}
		}
		
		return returnWord;
	}

	// performs not on a word, and returns its result
	public Word not() throws Exception {
		Word OrigWord = new Word();	// creates new word copying the current
		OrigWord.copy(this.bitArray);
		Word returnWord = new Word();		// word to be returned
		Bit trueBit = new Bit(true);		// true bit used to fill array
		Bit falseBit = new Bit(false);		// false but used to fill array
		
		// for the length of new words bit array, flip every bit in each index
		for(int i=0; i<OrigWord.bitArray.length; i++) {
			if(OrigWord.bitArray[i].getValue()==true) {
				returnWord.bitArray[i] = falseBit;
			}
			else if(OrigWord.bitArray[i].getValue()==false) {
				returnWord.bitArray[i] = trueBit;
			}

			// should not have an error unless code is wrong
			else {
				throw new Exception("Error: error occured in not word operator");
			}
		}
		
		return returnWord;
	}

	//**will change if you change which direction bits go
	// right shift this word by amount bits, creating a new word
	public Word rightShift(int amount) throws Exception {
		Word OrigWord = new Word();	// creates new word copying the current
		OrigWord.copy(this.bitArray);
		
		Word returnWord = new Word();		// word to be returned
		
		// checks through all of words bit to make sure each bit is assigned a value. will throw an error otherwise
		for(int i=0; i<OrigWord.bitArray.length; i++) {
			if(!(OrigWord.bitArray[i].value==false || OrigWord.bitArray[i].value==true)) {
				throw new Exception("Error: error occured in rightShift word operator");
			}
		}
		
		// for words size-1 while i is >= the amount, right shift the bits by "amount" amount
		for(int i=OrigWord.bitArray.length-1; i>=amount; i--) {	
			returnWord.bitArray[i-amount] = OrigWord.bitArray[i];
		}
		
		// sets the leftmost (largest) "amount" bit(s) of index's to false for right shifting
		for(int i=OrigWord.bitArray.length-1; i>OrigWord.bitArray.length-1-amount; i--) {
			returnWord.bitArray[i] = new Bit(false); 
		}
		
		return returnWord;
	}

	//**will change if you change which direction bits go
	// left shift this word by amount bits, creating a new word
	public Word leftShift(int amount) throws Exception {
		Word OrigWord = new Word();	// creates new word copying the current
		OrigWord.copy(this.bitArray);
		
		Word returnWord = new Word();		// word to be returned
		
		// checks through all of words bit to make sure each bit is assigned a value. will throw an error otherwise
		for(int i=0; i<OrigWord.bitArray.length; i++) {
			if(!(OrigWord.bitArray[i].value==false || OrigWord.bitArray[i].value==true)) {
				throw new Exception("Error(1): error occured in leftShift word operator");
			}
		}
		
		// for "amount" less than the length of the size of a word, left shift the bits by "amount" amount
		for(int i=0; i<OrigWord.bitArray.length-amount; i++) {	
			returnWord.bitArray[i+amount] = OrigWord.bitArray[i];
		}
		
		// sets the rightmost (smallest) "amount" bit(s) of index's to false for left shifting
		for(int i=0; i<amount; i++) {
			returnWord.bitArray[i] = new Bit(false); 
		}
		
		return returnWord;
	}
	
	//**will change if you change which direction bits go
	// returns a comma separated string of t's and f's
	public String toString() {
		String returnString = "";	// string to be returned
		
		// for the length of a words bit array, print out a comma separated list of t's, and f's
		for(int i=bitArray.length-1; i>=0; i--) {
			if(bitArray[i].value==true) {
				returnString+="t, ";
			}
			else if(bitArray[i].value==false) {
				returnString+="f, ";
			}

			// should not have an error unless code is wrong
			else {
				return "Error: error occured in not word operator";
			}
		}
		
		return returnString;
	}
	
	//**will change if you change which direction bits go
	// returns the value of this word as a long
	public long getUnsigned() throws Exception {
		
		
		Word copyWord = new Word();	// creates new word copying the current
		copyWord.copy(this.bitArray);
		
		long returnLong = 0;					// value of word stored as a long
		int counter = 0;						// counter
		// for the length of the word, create the decimal number the word represents
		for(int i=0; i<copyWord.bitArray.length; i++) {
			
			// if bit is true, add the value of that bit to the long to be returned
			if(copyWord.bitArray[i].value==true) {
				returnLong = (long) (returnLong + Math.pow(2, counter));
			}
			counter++;
		}
		
		//returnLong = Long.parseLong(WordConvertToString);
		return returnLong;
		
	}
	
	//**will change if you change which direction bits go
	// returns the value of this world as an int
	public int getSigned() throws Exception {

		Word copyWord = new Word();	// creates new word copying the current
		copyWord.copy(this.bitArray);
		
		Word returnWord = new Word();
		
		int returnInt = 0;					// value of word stored as a long
		int counter = 0;						// counter
		
		// if not a negative number
		if(copyWord.bitArray[31].getValue()==false) {
			// for the length of the word, create the decimal number the word represents
			for(int i=0; i<copyWord.bitArray.length; i++) {
				
				// if bit is true, add the value of that bit to the long to be returned
				if(copyWord.bitArray[i].value==true) {
					returnInt = (int) (returnInt + Math.pow(2, counter));
				}
				counter++;
			}
			return returnInt;
		}
		
		// if the word is a negative decimal number
		else if(copyWord.bitArray[31].getValue()==true) {
			// for the length of the word, first flip all the bits
			for(int i=0; i<copyWord.bitArray.length; i++) {
				if(copyWord.bitArray[i].getValue()==true) returnWord.bitArray[i].value=false;
				if(copyWord.bitArray[i].getValue()==false) returnWord.bitArray[i].value=true;
			}
			
			// adds 1 to the newly flipped word to get the correct positive decimal of the negative word
			for(int i=0; i<returnWord.bitArray.length; i++) {
				
				if(returnWord.bitArray[i].getValue()==true) {
					returnWord.bitArray[i].value = false;
				}
				else if(returnWord.bitArray[i].getValue()==false) {
					returnWord.bitArray[i].value = true;
					break;
				}
				
				// should not get here unless something is wrong with the code
				else {
					throw new Exception("Error(1): error occured in getSigned()");
				}
			}
			
			counter = 0;

			// for the length of the word, create the decimal number the word represents
			for(int i=0; i<returnWord.bitArray.length; i++) {
				
				// if bit is true, add the value of that bit to the int to be returned
				if(returnWord.bitArray[i].value==true) {
					returnInt = (int) (returnInt + Math.pow(2, counter));
				}
				counter++;
			}
			
			return (returnInt*-1);
			
		}
		
		// should not get here unless something is wrong with the code in the method
		else {
			throw new Exception("Error(2): error occured in getSigned()");
		}
	}
	
	// copies the values of another word into this one
	public void copy(Word other) {
		for(int i=0; i<other.bitArray.length; i++) {
			if(other.bitArray[i].getValue()==true) bitArray[i] = new Bit(true);
			if(other.bitArray[i].getValue()==false) bitArray[i] = new Bit(false);
		}
	}
	
	// copies the values of another word into this one
	public void copy(Bit[] other) {
		for(int i=0; i<other.length; i++) {
			if(other[i].getValue()==true) bitArray[i] = new Bit(true);
			if(other[i].getValue()==false) bitArray[i] = new Bit(false);
		}
	}
	
	//**will change if you change which direction bits go
	// sets the value of the bits of this word (used for testing)
	public void set(int value) throws Exception {
		
		// changes all bits to false
		for(int i=0; i<bitArray.length; i++) {
			this.bitArray[i] = new Bit(false);
		}
		
		
		
		
		// for positive numbers
		if(value>=0) {
			
			int holdVal = value;
			// for words size-1 while i is >= the amount, right shift the bits by "amount" amount
			for(int i=bitArray.length-1; i>=0; i--) {
				if((Math.pow(2, i))<=holdVal) {
					this.bitArray[i] = new Bit(true);
					holdVal -= Math.pow(2, i);
				}
			}
		}
		
		// for negative numbers
		else if(value<0) {
			int negativeVal = value * (-1);
			// for words size-1 while i is >= the amount, right shift the bits by "amount" amount
			for(int i=bitArray.length-1; i>=0; i--) {
				if((Math.pow(2, i))<=negativeVal) {
					this.bitArray[i] = new Bit(true);
					negativeVal -= Math.pow(2, i);
				}
			}
			
			// flips the bits of the positive number
			for(int i=0; i<bitArray.length; i++) {
				bitArray[i].toggle();
			}

			// adds 1 to the newly flipped word to get the correct positive decimal of the negative word
			for(int i=0; i<bitArray.length; i++) {
				
				if(bitArray[i].getValue()==true) {
					bitArray[i].toggle();
				}
				else if(bitArray[i].getValue()==false) {
					bitArray[i].toggle();
					break;
				}
				
				// should not get here unless something is wrong with the code
				else {
					throw new Exception("Error(1): error occured in getSigned()");
				}
			}
			
		}
	}
	

	// negates a number via 2's compliment
	public Word twosCompliment() throws Exception {
		
		// creates new word copying the current
		Word currentWord = new Word(bitArray);
		Word copyWord = new Word();
		copyWord.copy(currentWord);
		
		
		// for the length of the word,  flip all the bits
		for(int i=0; i<copyWord.bitArray.length; i++) {
			copyWord.bitArray[i].toggle();
		}
					
		// adds 1 to the newly flipped word to get the correct positive decimal of the negative word
		for(int i=0; i<copyWord.bitArray.length; i++) {
						
			if(copyWord.bitArray[i].getValue()==true) {
				copyWord.bitArray[i].toggle();
			}
			else if(copyWord.bitArray[i].getValue()==false) {
				copyWord.bitArray[i].toggle();
				break;
			}
						
			// should not get here unless something is wrong with the code
			else {
				throw new Exception("Error(1): error occured in getSigned()");
			}
			
		}
		return copyWord;
	}
	
	// increments a Word by 1 decimal number
	public Word increment() throws Exception {
		Word copyWord = new Word();	// copies word 
		copyWord.copy(this.bitArray);
		Bit falseBit = new Bit(false);		// false bit to compare to
		
		// loops over the word from smallest to largest bit, flipping every bit until reaching false bit
		for(int i=0; i<copyWord.bitArray.length; i++) {
			
			if(falseBit.xor(copyWord.getBit(i)).getValue()==true) {
				copyWord.bitArray[i].toggle();
			}
			
			else if((falseBit.and(copyWord.getBit(i))).not().getValue()==true) {
				copyWord.bitArray[i].toggle();
				break;
			}
		}
		
		return copyWord;
	}
	
	

	// increments a Word by 1 decimal number
	public Word decrement() throws Exception {
		Word copyWord = new Word();	// copies word 
		copyWord.copy(this.bitArray);
		Bit trueBit = new Bit(true);		// false bit to compare to
		
		// loops over the word from smallest to largest bit, flipping every bit until reaching false
		for(int i=0; i<copyWord.bitArray.length; i++) {
			
			if(trueBit.xor(copyWord.getBit(i)).getValue()==true) {
				copyWord.bitArray[i].toggle();
			}
			
			else if((trueBit.and(copyWord.getBit(i))).getValue()==true) {
				copyWord.bitArray[i].toggle();
				break;
			}
		}
		return copyWord;
	}
	

	// method to copy the lowest 5 bits of a Word onto a new Word
	public Word lowestBits(int numOfBits) {
		// copies current word
		Word other = new Word();
		other.copy(this.bitArray);
		
		Word lowestFiveBits = new Word();	// word to be returned which is the current words lowest 5 bits
		
		// for the lowest 5 bits, copy them into a new word
		for(int i=0; i<numOfBits; i++) {	
			lowestFiveBits.setBit(i, new Bit(other.getBit(i).getValue()));
		}
		return lowestFiveBits;
		
	}
	
	
	// used to get index for Register array
	public int getIndex() throws Exception {
		
		

		
		Word other = new Word();
		other.copy(this.bitArray);

		String holdBits = "";	// holds the bits as 1's and 0's
		
		// loop that starts at the end of operation and goes down to 0 to make the bits into a string
		for(int i = 4; i>=0; i--) {
			// if value is true add a 1 to the string
			if(other.getBit(i).getValue()==true) {
				holdBits += "1";
			}
			// if value is 0 add a false to string
			else if (other.getBit(i).getValue()==false) {
				holdBits += "0";
			}
			
			// should not have an error unless code is wrong
			else {
				throw new Exception("Error: error occured in doOperation");
			}
		}
		
		

		// switch to go through cases of operations
		switch (holdBits) {
		
		// 0
		case "00000":
			return 0;
		// 1
		case "00001":
			return 1;
		// 2
		case "00010":
			return 2;
		// 3
		case "00011":
			return 3;
		// 4
		case "00100":
			return 4;
		// 5
		case "00101":
			return 5;
		// 6
		case "00110":
			return 6;
		// 7	
		case "00111":
			return 7;
		// 8	
		case "01000":
			return 8;
		// 9
		case "01001":
			return 0;
		// 10
		case "01010":
			return 10;
		// 11
		case "01011":
			return 11;
		// 12
		case "01100":
			return 12;
		// 13
		case "01101":
			return 13;
		// 14
		case "01110":
			return 14;
		// 15
		case "01111":
			return 15;
		// 16	
		case "10000":
			return 16;
		// 17	
		case "10001":
			return 17;
		// 18	
		case "10010":
			return 18;
		// 19	
		case "10011":
			return 19;
		// 20	
		case "10100":
			return 20;
		// 21	
		case "10101":
			return 21;
		// 22
		case "10110":
			return 22;
		// 23
		case "10111":
			return 23;
		// 24
		case "11000":
			return 24;
		// 25
		case "11001":
			return 25;
		// 26
		case "11010":
			return 26;
		// 27
		case "11011":
			return 27;
		// 28
		case "11100":
			return 28;
		// 29	
		case "11101":
			return 29;
		// 30	
		case "11110":
			return 30;
		// 31	
		case "11111":
			return 31;
		
		default : throw new Exception("Error Word getIndex(): error in switch case");
		}
	}
	
	// method using ALU to add 2 words together
	public Word add(Word b) throws Exception {
		
		// copies current word object
		Word a = new Word();
		a.copy(this.bitArray);
		
		return add2(a, b);
	}

	// method using ALU to subtract 2 words
	public Word minus(Word b) throws Exception {

		// copies current word object
		Word a = new Word();
		a.copy(this.bitArray);
		
		return subtract(a, b);
	}
	

	

	// performs current word - b and returns result
	public Word subtract(Word b) throws Exception {
		// copies current word object
		Word a = new Word();
		a.copy(this.bitArray);
		
		
		return add2(a, (b.twosCompliment()));
	}
	
	


	// performs a - b and returns result
	public Word subtract(Word a, Word b) throws Exception {
		return add2(a, (b.twosCompliment()));
	}
	
	
	// method that adds 2 words together
	private Word add2(Word a, Word b) throws Exception {
		
		Word add2Final = new Word();	// word to return
		
		// gets the initial add where you start with no carry and sets first bit
		Bit[] firstHalfAdd = halfAdder(a.bitArray[0], b.bitArray[0]);
		add2Final.setBit(0, firstHalfAdd[0]);
		
		// does the next operation which requires the carry from the half adder
		Bit firstCarry = firstHalfAdd[1];
		Bit[] fullAdd = fullAdder(firstCarry, a.bitArray[1], b.bitArray[1]);
		add2Final.setBit(1, fullAdd[0]);
		
		// for the rest of the size for Word, continue full adder operation to add bits
		for (int i=2; i<a.bitArray.length; i++) {
			Bit carry = fullAdd[1];
			fullAdd = fullAdder(carry, a.bitArray[i], b.bitArray[i]);
			
			Bit sum = fullAdd[0];
			add2Final.setBit(i, sum);
		}
		
		return add2Final;
		
	}
	
	// method that adds 4 words together
	private Word add4(Word a, Word b, Word c, Word d) {
		
		Word add4Final = new Word();	// word to return
		int sumOfBits=0;				// sum of bits for each column
		int carry;						// carry
		
		// loops through the Words
		for(int i=0; i<a.bitArray.length; i++) {
			
			// if statements that goes through all 4 words and increments the sum if a bit is true
			if(a.getBit(i).getValue()==true) sumOfBits++;
			if(b.getBit(i).getValue()==true) sumOfBits++;
			if(c.getBit(i).getValue()==true) sumOfBits++;
			if(d.getBit(i).getValue()==true) sumOfBits++;
			
			// if sum of bits is odd, bit is true
			if(sumOfBits%2==1) {
				add4Final.setBit(i, new Bit(true));
			}
			
			
			carry = sumOfBits/2;	// for every two 1's there is a carry
			sumOfBits = 0;			// resets the sum
			// as long as there is a carry, add it to the next columns sum
			if(carry>0) {
				sumOfBits+=carry;
			}
		}
		return add4Final;
	}
	
	

	// half adder where the Bit[] being returned, Bit[0] is the sum and Bit[1] is the carry
	private Bit[] halfAdder(Bit x, Bit y) throws Exception {
		
		Bit sum;	// sum
		Bit carry;	// carry
		Bit[] halfAdder = new Bit[2];	// array to be returned
		
		// performs carry and sum operations
		carry = x.and(y);
		sum = x.xor(y);
		
		// puts carry and sum into array
		halfAdder[0] = sum;
		halfAdder[1] = carry;
		
		return halfAdder;
	}
	
	// full adder where the Bit[] being returned, Bit[0] is the sum and Bit[1] is the carry
	private Bit[] fullAdder (Bit x, Bit y, Bit z) throws Exception {
		
		Bit[] fullAdder = new Bit[2];	// array to be returned
		
		Bit[] halfAddOne = halfAdder(x,y);	// does first half adder operation
		Bit sumOne = halfAddOne[0];			// first sum
		Bit carryOne = halfAddOne[1];		// first carry
		
		Bit[] halfAddTwo = halfAdder(sumOne, z);	// does second half adder operation	
		Bit finalSum = halfAddTwo[0];					// final sum
		Bit carryTwo = halfAddTwo[1];				// second carry
		
		Bit finalCarry = carryOne.or(carryTwo);		// gets final carry
		
		// sets array's sum and carry and returns array
		fullAdder[0] = finalSum;
		fullAdder[1] = finalCarry;
		return fullAdder;
	}
	

	// multiplies 2 words
	public Word multiply(Word b) throws Exception {
		

		// copies current word object
		Word a = new Word();
		a.copy(this.bitArray);
		
		
		Word bitMultiply = new Word();			// used the words resulted from multiplying each bit by a word
		Word[] holdSumsOfMult = new Word[32];	// array to hold all 32 words
		Word[] eight4way = new Word[8];			// array to hold the second round of words
		Word[] two4way = new Word[2];			// array to hold the final round of words
		Word result = new Word();				// final word resulted from multiplication
		
		// first for loop iterates through Word b, then at each bit for b bit b 
		// is then multiplied by every bit in Word a which is done in the second for loop
		for(int B=0; B<32; B++) {
			for(int A=0; A<32; A++) {
				bitMultiply.bitArray[A] = b.bitArray[B].and(a.bitArray[A]);
			}
			
			// after each bit in b is multiplied by a word in a, leftshift 1 space, 
			// then add the new resulting word to the array, then reset the new word
			bitMultiply = bitMultiply.leftShift(B);
			holdSumsOfMult[B] = new Word(bitMultiply.bitArray);
			bitMultiply = new Word();
			
		}
		
		// piping the additions to get the final multiplication result
		eight4way = MultiplyHelper(holdSumsOfMult);
		two4way = MultiplyHelper(eight4way);
		result = add2(two4way[0], two4way[1]);
		
		return result;
		
		
	}
	
	// helper for multiply, add4s neccesary number of words to lessen work load
	private Word[] MultiplyHelper(Word[] a) throws Exception {
		int counter=0;								// counter for indexing correctly in returnWord
		Word[] returnWord = new Word[a.length/4];	// word to be returned (is /4 because of add4)
		
		// loops over the length of the given array and increments by 4 because of add4
		for(int i=0; i<a.length; i+=4) {
			returnWord[counter] = add4(a[i],a[i+1],a[i+2],a[i+3]);
			counter++;	
		}
		return returnWord;
		
		
	}
	
	
	

	// is current word < Word b (return Word with numerical value 1 if true, 0 if false)
	// if current word-b is negative then return true
	public Word lessThan(Word b) throws Exception {
		// copies current word object
		Word a = new Word();
		a.copy(this.bitArray);

		Word trueWord = new Word();		// sets all bits to 0, then changes first to 1
		trueWord.setBit(0, new Bit(true));
		
		
		
		// if the leftmost bit is true, meaning the word is negative return true, otherwise return false
		if(subtract(a,b).getBit(31).getValue()==true) return trueWord;
		else return new Word();
	}

	// is current word >= Word b (return Word with numerical value 1 if true, 0 if false)
	// if b-current word is negative or its 0, return true
	public Word greaterThanOrEq(Word b) throws Exception {
		// copies current word object
		Word a = new Word();
		a.copy(this.bitArray);

		Word trueWord = new Word();		// sets all bits to 0, then changes first to 1
		trueWord.setBit(0, new Bit(true));
		
		
		Word subtractWord = new Word();
		subtractWord.copy(subtract(b,a));
		
		// if the leftmost bit is true or the Word is numerical value 0 return true, otherwise return false
		if(subtractWord.getBit(31).getValue()==true || eq(subtractWord,new Word()).getBit(0).getValue()==true) return trueWord;
		else return new Word();
	}

	// is current word > Word b (return Word with numerical value 1 if true, 0 if false)
	// if b-current word is negative then return true
	public Word greaterThan(Word b) throws Exception {

		// copies current word object
		Word a = new Word();
		a.copy(this.bitArray);

		Word trueWord = new Word();		// sets all bits to 0, then changes first to 1
		trueWord.setBit(0, new Bit(true));
		
		
		// if the leftmost bit is true, meaning the word is negative return true, otherwise return false
		if(subtract(b,a).getBit(31).getValue()==true) return trueWord;
		else return new Word();
	}

	// is current word <= Word b (return Word with numerical value 1 if true, 0 if false)
	// if current word-b is negative or its 0, return true
	public Word lessThanOrEq(Word b) throws Exception {

		// copies current word object
		Word a = new Word();
		a.copy(this.bitArray);
		
		Word subtractWord = new Word();
		subtractWord.copy(subtract(a,b));

		Word trueWord = new Word();		// sets all bits to 0, then changes first to 1
		trueWord.setBit(0, new Bit(true));
		
		
		// if the leftmost bit is true or the Word is numerical value 0 return true, otherwise return false
		if(subtractWord.getBit(31).getValue()==true || eq(subtractWord,new Word()).getBit(0).getValue()==true) return trueWord;
		else return new Word();
	}
	
	
	
	// is current word == Word b (return Word with numerical value 1 if true, 0 if false)
	public Word eq(Word b) {

		// copies current word object
		Word a = new Word();
		a.copy(this.bitArray);

		Word trueWord = new Word();		// sets all bits to 0, then changes first to 1
		trueWord.setBit(0, new Bit(true));
		
		
		// loops through words and returns false if any bits aren't the same, returns true otherwise
		for(int i=0; i<a.bitArray.length; i++) {
			if(a.getBit(i).getValue()!=b.getBit(i).getValue()) return new Word();
		}
		return trueWord;
	}
	

	// is Word a == Word b (return Word with numerical value 1 if true, 0 if false)
	public Word eq(Word a, Word b) {

		Word trueWord = new Word();		// sets all bits to 0, then changes first to 1
		trueWord.setBit(0, new Bit(true));
		
		
		// loops through words and returns false if any bits aren't the same, returns true otherwise
		for(int i=0; i<a.bitArray.length; i++) {
			if(a.getBit(i).getValue()!=b.getBit(i).getValue()) return new Word();
		}
		return trueWord;
	}

	

	// is current word != Word b (return Word with numerical value 1 if true, 0 if false)
	public Word notEquals(Word b) {

		// copies current word object
		Word a = new Word();
		a.copy(this.bitArray);

		Word trueWord = new Word();		// sets all bits to 0, then changes first to 1
		trueWord.setBit(0, new Bit(true));
		
		
		// loops through words and returns true if any bits aren't the same, returns false otherwise
		for(int i=0; i<a.bitArray.length; i++) {
			if(a.getBit(i).getValue()!=b.getBit(i).getValue()) return trueWord;
		}
		return new Word();
		
		
	}

	
	
	
	
	
	
}
