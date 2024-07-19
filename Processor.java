
public class Processor {
	
	Word PC;		// program counter
	Word SP = new Word();		// stack pointer
	Bit halt;		// haltts the run
	
	Word fetchNextWord = new Word();	// will store the next fetched word
	Word[] Registers = new Word[32];	// array of 32 registers
	Word immediate = new Word();		// word for Immediate segment
	Word Rd = new Word();			// word for Rd segment
	Word function = new Word();		// word for Function segment
	
	Word Rs0 = new Word();			// Rs0 segment
	Word Rs1 = new Word();			// Rs1 segment
	Word Rs2 = new Word();			// Rs2 segment
	
	Word currentRegister = new Word();	// will hold the current register
	
	ALU aluOP = new ALU();			// ALU for execute
	
	static int currentClockCycle;			// keeps tracks of the clock cycle
	
	// processor constructor
	public Processor() throws Exception {
		// initializes array
		for(int i=0; i<Registers.length; i++) {
			Registers[i] = new Word();
		}
		
		PC = new Word();			
		SP.set(1024);	
		halt = new Bit(true);
		currentClockCycle = 0;
	}
	
	
	// loops through processor operations
	public void run() throws Exception {
		
		while(halt.getValue()==true) {
			//System.out.println(PC.getSigned());
			fetch();
			decode();
			
			// gets the function bits
			function.copy(fetchNextWord.rightShift(10).lowestBits(4));
			
			execute();
			store();
			
		}
		
		System.out.println("Clock cycle: " + currentClockCycle);
	}
	
	// fetches the next instructions from cache, then increments PC
	public Word fetch() throws Exception {
		fetchNextWord.copy(InstructionCache.read(PC));	// gets instructions
		PC.copy(PC.increment());						// increments PC
		return fetchNextWord;
	}
	
	// gets the Rd, the index into the Registers array, as well as Registers 1-3 which, the 5 bits are the index of
	// where the registers are stored in the array. Finally it gets the immediate
	public void decode() throws Exception {
		
		// copies fetchNextWord
		Word fetch = new Word();
		fetch.copy(fetchNextWord);
		
		// No Register (00) sets immediate value from instruction
		if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false) {
			// right shifts 5 and sets immediate
			immediate.copy(fetch.rightShift(5));
		}
		
		// Dest Only (01) sets the Rd value from registers and immediate value from instruction
		if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
			Rd.copy(fetch.rightShift(5).lowestBits(5));				// right shifts 5, and sets Rd to 5 lowest bits
			immediate.copy(fetch.rightShift(14).lowestBits(18));	// right shifts 14, and sets immediate to 18 lowest bits
		}
		
		// 3 Register (10) sets the Rs and Rd values from registers and immediate value from instruction
		if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {			
			Rd.copy(fetch.rightShift(5).lowestBits(5));					// right shifts 5, and sets Rd to 5 lowest bits
			Rs2.copy(fetch.rightShift(14).lowestBits(5));		// right shifts 14, and sets Rs2 to 5 lowest bits
			Rs1.copy(fetch.rightShift(19).lowestBits(5));		// right shifts 19, and sets Rs1 to 5 lowest bits
			immediate.copy(fetch.rightShift(24).lowestBits(8));			// right shifts 24, and sets immediate to 8 lowest bits
		}

		// 2 Register (11) sets the Rs1, Rs2, and Rd values from registers and immediate value from instruction
		if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
			Rd.copy(fetch.rightShift(5).lowestBits(5));				// right shifts 5, and sets Rd to 5 lowest bits
			Rs0.copy(fetch.rightShift(14).lowestBits(5));	// right shifts 14, and sets Rs to 5 lowest bits
			immediate.copy(fetch.rightShift(19).lowestBits(13));	// right shifts 19, and sets immediate to 13 lowest bits
		}
		
		
		
		
		
	}
	
	// executes decoded code
	public void execute() throws Exception {
		// gets the 3 bits for the math opcode
		Word executionOpCode = new Word();
		executionOpCode.copy(fetchNextWord.rightShift(2).lowestBits(3));
		// calls a method to do executes work to make execute() look cleaner
		executeOpBit(executionOpCode);
	}
	
	public void store() throws Exception {
		// copies fetchNextWord
		Word fetch = new Word();
		fetch.copy(fetchNextWord);
		
		
		// gets the 3 bits for the math opcode
		Word opCode = new Word();
		opCode.copy(fetchNextWord.rightShift(2).lowestBits(3));
		
		
		// Math(000)
		if(opCode.getBit(0).getValue()==false && opCode.getBit(1).getValue()==false && opCode.getBit(2).getValue()==false) {
						
			
			// No R (00) do nothing if both bits are false
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false) /*System.out.println(fetch.getSigned()+"halted")*/;
			
	
			// Dest Only (01) copies immediate's value into the Registers array in index Rd
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				//System.out.println("--Dest--fetch: "+fetch+"\nRd: "+Rd+"\nimmediate: "+aluOP.result);
				Registers[Rd.getIndex()].copy(aluOP.result);
			}
				
			// 3 Registers (10) copies Rs1 MOP Rs2 value into the Registers array in index Rd
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				//System.out.println("++3 Registers++fetch: "+fetch+"\nRd: "+Rd+"\nimmediate: "+aluOP.result);
				Registers[Rd.getIndex()].copy(aluOP.result);
			}
			
			// 2 Registers (11) copies Rd MOP Rs value into the Registers array in index Rd
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				//System.out.println(">>2 Registers<<fetch: "+fetch+"\nRd: "+Rd+"\nimmediate: "+aluOP.result);
				Registers[Rd.getIndex()].copy(aluOP.result);
			}
		}
		

		// Branch(001)
		if(opCode.getBit(0).getValue()==true && opCode.getBit(1).getValue()==false && opCode.getBit(2).getValue()==false) {				
			
			// No R (00) jumps to immediates value
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false) {
				PC.copy(aluOP.result);
			}
			
			// Dest Only (01) jumps to PC + immediate value
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				PC.copy(aluOP.result);
			}
				
			// 3 Registers (10) if Rs1 BOP Rs2 is false, PC jumps to PC + immediate value
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				if(aluOP.result.getBit(0).getValue()==true) PC.copy(PC.add(immediate));
			}
			
			// 2 Registers (11) if Rs0 BOP Rd is false, PC jumps to PC + immediate value
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				if(aluOP.result.getBit(0).getValue()==true) PC.copy(PC.add(immediate));
			}
		}
		
		

		// Call(010)
		if(opCode.getBit(0).getValue()==false && opCode.getBit(1).getValue()==true && opCode.getBit(2).getValue()==false) {

			// No R (00) pushes old PC on the stack, new PC becomes immediate
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false) {
				SP.copy(SP.decrement());
				MainMemory.write(SP, PC);
				Processor.currentClockCycle += 300;		// accessing memory
				PC.copy(PC.add(aluOP.result));
				

			}
			
			// Dest Only (01) pushes old PC on the stack, new PC becomes Rd + immediate
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				SP.copy(SP.decrement());
				MainMemory.write(SP, PC);
				Processor.currentClockCycle += 300;		// accessing memory
				PC.copy(PC.add(aluOP.result));
			}
				
			// 3 Registers (10) if BOP is false pushes old PC on the stack, new PC becomes Rd + immediate
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				SP.copy(SP.decrement());
				MainMemory.write(SP, PC);
				Processor.currentClockCycle += 300;		// accessing memory
				if(aluOP.result.getBit(0).getValue()==true) {
					PC.copy(Rd.add(immediate));
				}
			}
			
			// 2 Registers (11) if BOP is false pushes old PC on the stack, new PC becomes PC + immediate
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				SP.copy(SP.decrement());
				MainMemory.write(SP, PC);
				Processor.currentClockCycle += 300;		// accessing memory
				if(aluOP.result.getBit(0).getValue()==true) {
					PC.copy(PC.add(immediate));
				}
			}
		}
		
		
		

		// Push(011)
		if(opCode.getBit(0).getValue()==true && opCode.getBit(1).getValue()==true && opCode.getBit(2).getValue()==false) {			
			
			// No R (00) unused
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false);
			
			
			// Dest Only (01)
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				SP.copy(SP.decrement());
				MainMemory.write(SP, aluOP.result);
				Processor.currentClockCycle += 300;		// accessing memory
			}
				
			// 3 Registers (10)
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				SP.copy(SP.decrement());
				MainMemory.write(SP, aluOP.result);
				Processor.currentClockCycle += 300;		// accessing memory
			}
			
			// 2 Registers (11)
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				SP.copy(SP.decrement());
				MainMemory.write(SP, aluOP.result);
				Processor.currentClockCycle += 300;		// accessing memory
			}
		}
		

		// Load(100)
		if(opCode.getBit(0).getValue()==false && opCode.getBit(1).getValue()==false && opCode.getBit(2).getValue()==true) {	
			
			// No R (00)
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false) {
				PC.copy(aluOP.result);
			}
			
			// Dest Only (01)
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				Registers[Rd.getIndex()].copy(aluOP.result);
			}
				
			// 3 Registers (10)
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				Registers[Rd.getIndex()].copy(aluOP.result);
			}
			
			// 2 Registers (11)
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				Registers[Rd.getIndex()].copy(aluOP.result);
			}
		}
		
		

		// Store(101)
		if(opCode.getBit(0).getValue()==true && opCode.getBit(1).getValue()==false && opCode.getBit(2).getValue()==true) {
			
			// No R (00) unused
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false);
			
			// Dest Only (01)
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				MainMemory.write(Registers[Rd.getIndex()], aluOP.result);
				Processor.currentClockCycle += 300;		// accessing memory
			}
				
			// 3 Registers (10)
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				MainMemory.write(Registers[Rd.getIndex()].add(Registers[Rs1.getIndex()]), aluOP.result);
				Processor.currentClockCycle += 300;		// accessing memory
			}
			
			// 2 Registers (11)
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				MainMemory.write(Registers[Rd.getIndex()].add(immediate), aluOP.result);
				Processor.currentClockCycle += 300;		// accessing memory
			}
		}
		
		// Pop/interrupt(110)
		if(opCode.getBit(0).getValue()==false && opCode.getBit(1).getValue()==true && opCode.getBit(2).getValue()==true) {

			// No R (00)
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false) {
				
			}
			
			// Dest Only (01) Stores result in Rd
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				Registers[Rd.getIndex()].copy(aluOP.result);
			}
				
			// 3 Registers (10) Stores result in Rd
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				Registers[Rd.getIndex()].copy(aluOP.result);
			}
			
			// 2 Registers (11) Stores result in Rd
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				Registers[Rd.getIndex()].copy(aluOP.result);
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// does executes work
	private void executeOpBit(Word opCode) throws Exception {
		
		// copies fetchNextWord
		Word fetch = new Word();
		fetch.copy(fetchNextWord);
		
		
		// Math(000)
		if(opCode.getBit(0).getValue()==false && opCode.getBit(1).getValue()==false && opCode.getBit(2).getValue()==false) {
			
			
			// No Register (00) halts the processors loop
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false) {
				halt.set(false);
			}
			
			// Dest Only (01) Rd copies immediate
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				aluOP.result.copy(immediate);
			}
			
			// 3 Register (10) Rs1 MOP Rs2
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				aluOP = new ALU(Registers[Rs1.getIndex()], Registers[Rs2.getIndex()]);
				aluOP.doOperation(function.bitArray);
			}
			
			// 2 Register (11) Rd MOP Rs
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				// creates an ALU 
				aluOP = new ALU(Registers[Rd.getIndex()], Registers[Rs0.getIndex()]);
				aluOP.doOperation(function.bitArray);
			}
		}
		
		
		
		
		// Branch(001)
		if(opCode.getBit(0).getValue()==true && opCode.getBit(1).getValue()==false && opCode.getBit(2).getValue()==false) {
					

			// No Register (00) PC is moved to the value of immediate
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false) {
				aluOP.result.copy(immediate);
			}
			
			// Dest Only (01) PC is moved to the value of PC + immediate
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				aluOP.result.copy(PC.add(immediate));	// adds PC and immediate
			}
			
			// 3 Register (10) performs Rs1 BOP Rs2
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				
				aluOP = new ALU(Registers[Rs1.getIndex()], Registers[Rs2.getIndex()]);
				aluOP.doOperation(function.bitArray);
			}
			
			// 2 Register (11) performs Rs0 BOP Rd
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				aluOP = new ALU(Registers[Rs0.getIndex()], Registers[Rd.getIndex()]);
				aluOP.doOperation(function.bitArray);
			}
			
			
		}
		


		// Call(010)
		if(opCode.getBit(0).getValue()==false && opCode.getBit(1).getValue()==true && opCode.getBit(2).getValue()==false) {
					

			// No Register (00) gets immediates value
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false) {
				aluOP.result.copy(immediate);
			}
			
			// Dest Only (01) gets RD + immediates value
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				aluOP.result.copy(Registers[Rd.getIndex()].add(immediate));
			}
			
			// 3 Register (10) performs BOP on Rs1 and Rs2
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				aluOP = new ALU(Registers[Rs1.getIndex()], Registers[Rs2.getIndex()]);
				aluOP.doOperation(function.bitArray);
			}
			
			// 2 Register (11) performs BOP on Rs and Rd
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				aluOP = new ALU(Registers[Rs0.getIndex()], Registers[Rd.getIndex()]);
				aluOP.doOperation(function.bitArray);
			}
			
			
		}
		

		// Push(011)
		if(opCode.getBit(0).getValue()==true && opCode.getBit(1).getValue()==true && opCode.getBit(2).getValue()==false) {
					

			// No Register (00) unused
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false);
			
			
			// Dest Only (01) Rd MOP immediate
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				aluOP = new ALU(Registers[Rd.getIndex()], immediate);
				aluOP.doOperation(function.bitArray);
			}
			
			// 3 Register (10) Rs1 MOP Rs2
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				aluOP = new ALU(Registers[Rs1.getIndex()], Registers[Rs2.getIndex()]);
				aluOP.doOperation(function.bitArray);
			}
			
			// 2 Register (11) Rd MOP Rs
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				aluOP = new ALU(Registers[Rd.getIndex()], Registers[Rs0.getIndex()]);
				aluOP.doOperation(function.bitArray);
			}
			
			
		}

		// Load(100)
		if(opCode.getBit(0).getValue()==false && opCode.getBit(1).getValue()==false && opCode.getBit(2).getValue()==true) {
					

			// No Register (00) RETURN, pop and set PC
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false) {
				aluOP.result.copy(MainMemory.read(SP));
				Processor.currentClockCycle += 300;		// accessing memory
			}
			
			// Dest Only (01) read main memory at Rd + immediate
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				aluOP.result.copy(MainMemory.read(Registers[Rd.getIndex()].add(immediate)));
				Processor.currentClockCycle += 300;		// accessing memory
			}
			
			// 3 Register (10) read main memory at Rs1 + Rs2
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				aluOP.result.copy(MainMemory.read(Registers[Rs1.getIndex()].add(Registers[Rs2.getIndex()])));
				Processor.currentClockCycle += 300;		// accessing memory
			}
			
			// 2 Register (11) read main memory at Rs0 + immediate
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				aluOP.result.copy(MainMemory.read(Registers[Rs0.getIndex()].add(immediate)));
				Processor.currentClockCycle += 300;		// accessing memory
			}
			
			
		}

		// Store(101)
		if(opCode.getBit(0).getValue()==true && opCode.getBit(1).getValue()==false && opCode.getBit(2).getValue()==true) {
				

			// No Register (00) unused
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false);
			
			// Dest Only (01) gets immediate
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				aluOP.result.copy(immediate);
			}
			
			// 3 Register (10) gets value in registers stored at index Rs2
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				aluOP.result.copy(Registers[Rs2.getIndex()]);
			}
			
			// 2 Register (11) gets value in registers stored at index Rs0
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				aluOP.result.copy(Registers[Rs0.getIndex()]);
			}
			
			
		}

		// Pop/interrupt(110)
		if(opCode.getBit(0).getValue()==false && opCode.getBit(1).getValue()==true && opCode.getBit(2).getValue()==true) {
			

			// No Register (00) 
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==false) {
				
			}
			
			// Dest Only (01)copies Word in main memory at location, removes SP from stack, and then increments SP
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==false) {
				aluOP.result.copy(MainMemory.read(SP));
				MainMemory.write(SP, new Word());
				Processor.currentClockCycle += 300;		// accessing memory
				SP.copy(SP.increment());
			}
			
			// 3 Register (10) copies Word in main memory at SP - (Rs1+Rs2) 
			if(fetch.getBit(0).getValue()==false && fetch.getBit(1).getValue()==true) {
				aluOP.result.copy(MainMemory.read(SP.minus(Registers[Rs1.getIndex()].add(Registers[Rs2.getIndex()]))));
				Processor.currentClockCycle += 300;		// accessing memory
			}
			
			// 2 Register (11) copies Word in main memory at SP - (Rs0+immediate) 
			if(fetch.getBit(0).getValue()==true && fetch.getBit(1).getValue()==true) {
				aluOP.result.copy(MainMemory.read(SP.minus(Registers[Rs0.getIndex()].add(immediate))));
				Processor.currentClockCycle += 300;		// accessing memory
			}
			
			
		}
		
		
		
	}
	
}
