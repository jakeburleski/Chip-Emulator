
public class InstructionCache {
	// cache where last index is the address of the first index's word
	public static Word[] cache = new Word[9];
	
	// reads a word from memory array based on the address
	public static Word read(Word address) throws Exception {
		
		// on startup, if cache[8] is null, do same as else		
		if(cache[8]==null) {
			//System.out.println("test 1");
			Processor.currentClockCycle += 350;	// cache miss takes 350 clock cycles
			cache[8] = new Word();
			cache[8].copy(address);
			Word fillCache = new Word();
			fillCache.copy(address);
			for(int i=0; i<cache.length-1; i++) {
				cache[i] = new Word();
				cache[i].copy(MainMemory.read(fillCache));
				//System.out.println(fillCache.getSigned());
				//System.out.println(MainMemory.read(fillCache).getSigned());
				fillCache = fillCache.increment();
			}
			// returns word at address given
			return cache[0];
		}
		
		
		// if the address is somewhere between index 0-7, return the word in that index
		else if(address.getSigned()>=cache[8].getSigned() && address.getSigned()<(cache[8].getSigned()+8)) {
			/*
			System.out.println("Address: " + address.getSigned());
			System.out.println("cache 8: " + cache[8].getSigned());
			System.out.println("cache 8+8: " + (cache[8].getSigned()+8));
			System.out.println("what to return: " + cache[address.getSigned()-cache[8].getSigned()].getSigned());
			*/
			Processor.currentClockCycle += 10;	// cache hit takes 10 clock cycles
			return cache[address.getSigned()-cache[8].getSigned()];
		}
		
		
		
		
		// if L2 is 0 on start up
		else if((((L2Cache.first.add(L2Cache.second)).add(L2Cache.third)).add(L2Cache.forth)).getSigned()==0) {
			Processor.currentClockCycle += 350;	// cache miss takes 350 clock cycles
			
			L2Cache.first.copy(address);
			Word fillCache = new Word();
			fillCache.copy(address);
			for(int i=0; i<8; i++) {
				L2Cache.cache[i] = new Word();
				L2Cache.cache[i].copy(MainMemory.read(fillCache));
				cache[i] = new Word();
				cache[i].copy(MainMemory.read(fillCache));
				fillCache.copy(fillCache.increment());
			}
			cache[8] = new Word();
			cache[8].copy(address);
			
			// returns word at address given
			return L2Cache.cache[0];
		}
		
		
		// else if L2 is not 0
		else if((((L2Cache.first.add(L2Cache.second)).add(L2Cache.third)).add(L2Cache.forth)).getSigned()!=0) {
			
			// checks if address is anywhere in first block in L2 cache
			if(address.getSigned()>=L2Cache.first.getSigned() && address.getSigned()<(L2Cache.first.getSigned()+8)) {
				Processor.currentClockCycle += 50;	// cache hit takes 10 clock cycles for L2
				return L2Cache.cache[address.getSigned()-L2Cache.first.getSigned()];
			}
			
			
			
			// checks if address is anywhere in second block in L2 cache
			else if(address.getSigned()>=L2Cache.second.getSigned() && address.getSigned()<(L2Cache.second.getSigned()+8)) {
				Processor.currentClockCycle += 50;	// cache hit takes 10 clock cycles for L2
				return L2Cache.cache[address.getSigned()-L2Cache.second.getSigned()];
			}
			
			
			// checks if address is anywhere in third block in L2 cache
			else if(address.getSigned()>=L2Cache.third.getSigned() && address.getSigned()<(L2Cache.third.getSigned()+8)) {
				Processor.currentClockCycle += 50;	// cache hit takes 10 clock cycles for L2
				return L2Cache.cache[address.getSigned()-L2Cache.third.getSigned()];
			}
			
			
			// checks if address is anywhere in forth block in L2 cache
			else if(address.getSigned()>=L2Cache.forth.getSigned() && address.getSigned()<(L2Cache.forth.getSigned()+8)) {
				Processor.currentClockCycle += 50;	// cache hit takes 10 clock cycles for L2
				return L2Cache.cache[address.getSigned()-L2Cache.forth.getSigned()];
			}
			
			
			
			
			else {
				Processor.currentClockCycle += 350;	// cache miss takes 350 clock cycles
				L2Cache.first.copy(address);
				Word fillCache = new Word();
				fillCache.copy(address);
				for(int i=0; i<8; i++) {
					L2Cache.cache[i] = new Word();
					L2Cache.cache[i].copy(MainMemory.read(fillCache));
					cache[i] = new Word();
					cache[i].copy(MainMemory.read(fillCache));
					fillCache.copy(fillCache.increment());
				}
				cache[8] = new Word();
				cache[8].copy(address);
				
				// returns word at address given
				return L2Cache.cache[0];
				
			}
			
			
		}
		
		
		// else, change cache[8] to address and fill cache up so that the address is in cache
		else {
			
			//System.out.println("test 3");
			Processor.currentClockCycle += 350;	// cache miss takes 350 clock cycles
			cache[8].copy(address);
			Word fillCache = new Word();
			fillCache.copy(address);
			for(int i=0; i<cache.length-1; i++) {
				cache[i] = new Word();
				cache[i].copy(MainMemory.read(fillCache));
				fillCache.copy(fillCache.increment());
			}
			// returns word at address given
			return cache[0];
		}
	}
}
