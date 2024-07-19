
public class MainMemory {

	static Word[] memory = new Word[1024];
	
	// reads a word from memory array based on the address
	public static Word read(Word address) throws Exception {
		// throws an exception if there is a null cell in memory
		for(Word i : memory) {
			if(i==null) throw new Exception("Error read(): index " + i + " is null in memory");
		}
		
		Word readFromMemory = new Word();							// creates a new word
		readFromMemory.copy(memory[(int) address.getUnsigned()]);	// gets the word from memory based on the address
		return readFromMemory;										// returns word
	}
	
	// writes value to memory based on the address
	public static void write(Word address, Word value) throws Exception {
		// throws an exception if there is a null cell in memory
		for(Word i : memory) {
			if(i==null) throw new Exception("Error read(): index " + i + " is null in memory");
		}
		memory[(int) address.getUnsigned()].copy(value);
	}
	
	// loads the memory array
	public static void load(String[] data) throws Exception {
		//*** REMINDER: change for loop from i<data.length to incriment after each line instead of after every character
		//*** 			unless when we change to files, each index is a new line
		int counter;
		
		for(int i=0; i<memory.length; i++) {
			memory[i] = new Word();
		}
		
		
		// loops through each line in a file to set to new word and load array
		for(int i=0; i<data.length; i++) {
			Word loadWord = new Word();				// word to be loaded
			
			
			counter = 31;	// counter decrements because humans read left to right, but Word builds bits right to left
			
			// loops through each word then adds new word to memory
			for(int j=0; j<loadWord.bitArray.length; j++) {
				if(data[i].charAt(counter)=='0') loadWord.setBit(j, new Bit(false));
				else if(data[i].charAt(counter)=='1') loadWord.setBit(j, new Bit(true));
				else {
					throw new Exception("Error load(): Unexpected character");
				}
				counter--;
			}
			
			memory[i] = loadWord;
		}
	}
	
}
