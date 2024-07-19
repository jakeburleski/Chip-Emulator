
public class ALU {

	public Word op1;					// first word
	public Word op2;					// second word
	public Word result = new Word();	// result of operation done on first and second word
	
	private Word falseWord;		// will be initialized at numerical value 0 to represent false for result
	private Word trueWord;		// will be initialized at numerical value 1 to represent true for result
	
	// empty constructor
	public ALU() {}
	
	// constructor
	public ALU(Word op1, Word op2) {
		this.op1 = new Word();
		this.op2 = new Word();
		
		this.op1.copy(op1);;
		this.op2.copy(op2);;
	}
	
	// constructor for not
	public ALU(Word op1) {
		this.op1 = new Word();
		this.op1.copy(op1);
	}
	
	
	
	// cell 0 will be the right most bit, moving right from there
	public void doOperation(Bit[] operation) throws Exception {
		
		falseWord = new Word();	// sets all bits to 0
		
		trueWord = new Word();	// sets all bits to 0, then changes first to 1
		trueWord.setBit(0, new Bit(true));
		
		
		String holdBits = "";	// holds the bits as 1's and 0's
		
		// loop that starts at the end of operation and goes down to 0 to make the bits into a string
		for(int i = 3; i>=0; i--) {
			// if value is true add a 1 to the string
			if(operation[i].getValue()==true) {
				holdBits += "1";
			}
			// if value is 0 add a false to string
			else if (operation[i].getValue()==false) {
				holdBits += "0";
			}
			
			// should not have an error unless code is wrong
			else {
				throw new Exception("Error: error occured in doOperation");
			}
		}
		
		// switch to go through cases of operations
		switch (holdBits) {
		
		// and
		case "1000":
			Processor.currentClockCycle += 2;
			result.copy(op1.and(op2));
			break;
		// or
		case "1001":
			Processor.currentClockCycle += 2;
			result.copy(op1.or(op2));
			break;
		// xor
		case "1010":
			Processor.currentClockCycle += 2;
			result.copy(op1.xor(op2));
			break;
		// not
		case "1011":
			Processor.currentClockCycle += 2;
			result.copy(op1.not());
			break;
		// left shift
		case "1100":
			Processor.currentClockCycle += 2;
			result.copy(op1.leftShift(shiftHelper(op2)));
			break;
		// right shift
		case "1101":
			Processor.currentClockCycle += 2;
			result.copy(op1.rightShift(shiftHelper(op2)));
			break;
		// add
		case "1110":
			Processor.currentClockCycle += 2;
			result.copy(op1.add(op2));
			break;
		// subtract	
		case "1111":
			Processor.currentClockCycle += 2;
			result.copy(op1.subtract(op2));
			break;
		// multiply	
		case "0111":
			Processor.currentClockCycle += 10;
			result.copy(op1.multiply(op2));
			break;
		// equals	
		case "0000":
			Processor.currentClockCycle += 2;
			result.copy(op1.eq(op2));
			break;
		// not equals
		case "0001":
			Processor.currentClockCycle += 2;
			result.copy(op1.notEquals(op2));
			break;
		// less than
		case "0010":
			Processor.currentClockCycle += 2;
			result.copy(op1.lessThan(op2));
			break;		
		// greater than or equal to
		case "0011":
			Processor.currentClockCycle += 2;
			result.copy(op1.greaterThanOrEq(op2));
			break;
		// greater than
		case "0100":
			Processor.currentClockCycle += 2;
			result.copy(op1.greaterThan(op2));
			break;
		// less than or equal to
		case "0101":
			Processor.currentClockCycle += 2;
			result.copy(op1.lessThanOrEq(op2));
			break;		
		}
	}

	// helper method to get the amount to left and right shift
	private int shiftHelper(Word amountToShift) {
		
		int returnAmount = 0;	// amount to shift to be returned
		int counter = 0;		// counter
		
		// for the lowest 5 bits, create the decimal number the word represents
		for(int i=0; i<5; i++) {	
			// if bit is true, add the value of that bit to the long to be returned
			if(amountToShift.bitArray[i].value==true) {
				returnAmount = (int) (returnAmount + Math.pow(2, counter));
			}
			counter++;
		}
		return returnAmount;
		
	}
	
}

