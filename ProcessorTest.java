import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ProcessorTest {

	@Test
	void generalProcessorTest() throws Exception {
		

		
		// tests processor
		String[] load = new String[5];
		
		load[0] = "00000000000000010111100000100001"; // MATH DestOnly 5, R1
        load[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
        load[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
        load[3] = "00000000000100000111100001100010"; // MATH ADD R2 R1 R3
        load[4] = "00000000000000000000000000000000"; // HALT
	
		
		MainMemory.load(load);
		
		Processor process = new Processor();

		process.run();
	
		
		// tests that the result is 25
		assertEquals(process.Registers[3].getSigned(), new Word(25).getSigned());
		
		

		
		// tests Branch
		load = new String[6];
		
		load[0] = "00000000000000010111100000100001"; // MATH DestOnly 5, R1
        load[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
        load[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
        load[3] = "00000000000100000111100001100010"; // MATH ADD R2 R1 R3
        load[4] = "000000000000000000001000000"+"00100"; // moves PC to 64
        load[5] = "00000000000000000000000000000000"; // HALT
	
		
		MainMemory.load(load);
		
		process = new Processor();
		
		process.run();
		
		// tests that the result is 65 after getting incremented once
		assertEquals(process.PC.getSigned(), new Word(65).getSigned());
		
		
		

		
		load = new String[6];
		
		load[0] = "00000000000000010111100000100001"; // MATH DestOnly 5, R1
        load[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
        load[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
        load[3] = "00000000000100000111100001100010"; // MATH ADD R2 R1 R3
        load[4] = "000000000001000000"+"0000"+"00000"+"00101"; // moves PC to PC + 64
        load[5] = "00000000000000000000000000000000"; // HALT
	
		
		MainMemory.load(load);
		
		process = new Processor();
		
		process.run();
		
		// tests that the result is 70
		assertEquals(process.PC.getSigned(), new Word(70).getSigned());
		
		
		

		load = new String[6];
		
		load[0] = "00000000000000010111100000100001"; // MATH DestOnly 5, R1
        load[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
        load[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
        load[3] = "00000000000100000111100001100010"; // MATH ADD R2 R1 R3
        load[4] = "0000001000000"+"00011"+"0100"+"00010"+"00111"; // moves PC to PC + 64 if BOP is true
        load[5] = "00000000000000000000000000000000"; // HALT
	
		
		MainMemory.load(load);
		
		process = new Processor();
		
		process.run();
		
		// tests that the result is 70
		assertEquals(process.PC.getSigned(), new Word(70).getSigned());
		
		
		

		load = new String[6];
		
		load[0] = "00000000000000010111100000100001"; // MATH DestOnly 5, R1
        load[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
        load[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
        load[3] = "00000000000100000111100001100010"; // MATH ADD R2 R1 R3
        load[4] = "01000000"+"00011"+"00001"+"0100"+"00000"+"00110"; // moves PC to PC + 64 if BOP is true
        load[5] = "00000000000000000000000000000000"; // HALT
	
		
		MainMemory.load(load);
		
		process = new Processor();
		
		process.run();
		
		// tests that the result is 70
		assertEquals(process.PC.getSigned(), new Word(70).getSigned());
		
		
		
		
		
		
		
		
		
		

		// tests Call
		load = new String[6];
		
		load[0] = "00000000000000010111100000100001"; // MATH DestOnly 5, R1
        load[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
        load[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
        load[3] = "00000000000100000111100001100010"; // MATH ADD R2 R1 R3
        load[4] = "000000000000000000001000000"+"01000"; // moves PC to 64
        load[5] = "00000000000000000000000000000000"; // HALT
	
		
		MainMemory.load(load);
		
		process = new Processor();
		
		process.run();
		
		// tests that PC is 70 and old PC is 5
		assertEquals(process.PC.getSigned(), new Word(70).getSigned());
		assertEquals(MainMemory.read(process.SP).getSigned(), new Word(5).getSigned());
		
		

		
		load = new String[6];
		
		load[0] = "00000000000000010111100000100001"; // MATH DestOnly 5, R1
        load[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
        load[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
        load[3] = "00000000000100000111100001100010"; // MATH ADD R2 R1 R3
        load[4] = "000000000001000000"+"0000"+"00001"+"01001"; // moves PC to PC + 64
        load[5] = "00000000000000000000000000000000"; // HALT
	
		
		MainMemory.load(load);
		
		process = new Processor();
		
		process.run();
		
		// tests that the result is 70
		assertEquals(process.PC.getSigned(), new Word(75).getSigned());
		assertEquals(MainMemory.read(process.SP).getSigned(), new Word(5).getSigned());
		
		

		load = new String[6];
		
		load[0] = "00000000000000010111100000100001"; // MATH DestOnly 5, R1
        load[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
        load[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
        load[3] = "00000000000100000111100001100010"; // MATH ADD R2 R1 R3
        load[4] = "0000001000000"+"00011"+"0100"+"00010"+"01011"; // moves PC to PC + 64 if BOP is true
        load[5] = "00000000000000000000000000000000"; // HALT
	
		
		MainMemory.load(load);
		
		process = new Processor();
		
		process.run();
		
		// tests that the result is 70
		assertEquals(process.PC.getSigned(), new Word(70).getSigned());
		assertEquals(MainMemory.read(process.SP).getSigned(), new Word(5).getSigned());
		
		

		load = new String[6];
		
		load[0] = "00000000000000010111100000100001"; // MATH DestOnly 5, R1
        load[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
        load[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
        load[3] = "00000000000100000111100001100010"; // MATH ADD R2 R1 R3
        load[4] = "01000000"+"00011"+"00001"+"0100"+"00000"+"01010"; // moves PC to PC + 64 if BOP is true
        load[5] = "00000000000000000000000000000000"; // HALT
	
		
		MainMemory.load(load);
		
		process = new Processor();
		
		process.run();
		
		// tests that the result is 65
		assertEquals(process.PC.getSigned(), new Word(65).getSigned());
		assertEquals(MainMemory.read(process.SP).getSigned(), new Word(5).getSigned());
		
		
		
		
		// tests push
		load = new String[6];
		
		load[0] = "00000000000000010111100000100001"; // MATH DestOnly 5, R1
        load[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
        load[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
        load[3] = "00000000000100000111100001100010"; // MATH ADD R2 R1 R3
        load[4] = "00000000"+"00011"+"00001"+"1110"+"00000"+"01110"; // moves PC to PC + 64 if BOP is true
        load[5] = "00000000000000000000000000000000"; // HALT
	
		
		MainMemory.load(load);
		
		process = new Processor();
		
		process.run();
		
		// tests that the memory at --sp is 25+5
		assertEquals(process.Registers[3].getSigned(), new Word(25).getSigned());
		assertEquals(MainMemory.read(process.SP).getSigned(), new Word(30).getSigned());
		
		

		// tests load
		load = new String[6];
		
		load[0] = "00000000000000010111100000100001"; // MATH DestOnly 5, R1
        load[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
        load[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
        load[3] = "00000000000100000111100001100010"; // MATH ADD R2 R1 R3
        load[4] = "0000001000000"+"00011"+"0100"+"00010"+"10011"; // moves PC to PC + 64 if BOP is true
        load[5] = "00000000000000000000000000000000"; // HALT
	
		
		MainMemory.load(load);
		
		process = new Processor();
		
		process.run();
		
		// tests that Rd had 0 stored in it from memory T Rs + immediate
		assertEquals(process.Registers[process.Rd.getIndex()].getSigned(), new Word(0).getSigned());
		
		
		

		// tests store
		load = new String[6];
		
		load[0] = "00000000000000010111100000100001"; // MATH DestOnly 5, R1
        load[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
        load[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
        load[3] = "00000000000100000111100001100010"; // MATH ADD R2 R1 R3
        load[4] = "000000000001000000"+"0000"+"00001"+"10101"; // moves PC to PC + 64 if BOP is true
        load[5] = "00000000000000000000000000000000"; // HALT
	
		
		MainMemory.load(load);
		
		process = new Processor();
		
		process.run();
		
		// tests that main memory at index of what is the Registers at index Rd is immediate value 64
		assertEquals(MainMemory.read(process.Registers[process.Rd.getIndex()]).getSigned(), new Word(64).getSigned());
		
		
		
	}
		
}
