public class Bit {

	Boolean value;	// holds boolean value
	
	public Bit() {}
	
	public Bit(boolean value) {
		this.value = value;
	}
	
	// sets value of the bit
	public void set(Boolean value) {
		this.value = value;
	}
	
	// changes bit value from true to false or false to true
	public void toggle() {
		// if false set true
		if(this.value==false) {this.value = true;}
		
		// else if true set false
		else if(this.value==true) {this.value = false;}
	}
	
	// sets bit to true
	public void set() {
		this.value = true;
	}
	
	// sets bit to false
	public void clear() {
		this.value = false;
	}
	
	// returns the current value
	public Boolean getValue() {
		return this.value;
	}
	
	// performs and on two bits and returns result of and operation
	public Bit and(Bit other) throws Exception{
		
		// creates new bit to be returned
		Bit returnBit = new Bit();
		
		// if other bits value is true
		if(other.getValue()==true) {
			
			// and if first bits value is true return true bit
			if(value==true) {
				returnBit.set();
				return returnBit;
			}
			
			// and if first bits value is false return false bit
			else if(value==false) {
				returnBit.clear();
				return returnBit;
			}
		}
		
		// else others value is false, so return false bit
		else if (other.getValue() == false){
			returnBit.clear();
			return returnBit;
		}
		
		// error should not gotten to
		else {
			throw new Exception("Error: error occured in and bit operator");
		}
		
		// satisfies compiler
		return returnBit;
		
		
	}
	
	// performs or on two bits and returns result of or operation
	public Bit or(Bit other) throws Exception {
		
		// creates new bit to be returned
		Bit returnBit = new Bit();
		
		// if other bits value is true, return true because only one true is needed
		if(other.getValue()==true) {
			returnBit.set();
			return returnBit;
		}
		
		// if others boolean is false check to see if first bits is true
		else if(other.getValue()==false) {
			// if first bits value is true, return true bit
			if(value==true) {
				returnBit.set();
				return returnBit;
			}
			
			// if both bits are false, return false
			else if(value==false) {
				returnBit.clear();
				return returnBit;
			}
		}
		
		// error should not gotten to
		else {
			throw new Exception("Error: error occured in or bit operator");
		}
				
		// satisfies compiler
		return returnBit;
	}
	
	// performs xor on two bits and returns result of xor operation
	public Bit xor(Bit other) throws Exception {

		// creates new bit to be returned
		Bit returnBit = new Bit();
		
		// if other bits value is true, checks first bits boolean
		if(other.getValue()==true) {
			// if first bits value is also true, return false bit
			if(value==true) {
				returnBit.clear();
				return returnBit;
			}
			// else if first bits value is false, return true bit
			if(value==false) {
				returnBit.set();
				return returnBit;
			}
		}
		
		
		// if others boolean is false check first bit
		else if(other.getValue()==false) {
			// if first bits value is true, return true bit
			if(value==true) {
				returnBit.set();
				return returnBit;
			}
			
			// if both bits are false, return false
			else if(value==false) {
				returnBit.clear();
				return returnBit;
			}
		}
		
		// error should not gotten to
		else {
			throw new Exception("Error: error occured in or bit operator");
		}
				
		// satisfies compiler
		return returnBit;
	}
	
	// performs not on a bit and returns result of not operation
	public Bit not() throws Exception {

		Bit returnBit = new Bit();	// creates new bit to be returned
		
		// flips boolean from true to false
		if(value==true) {
			returnBit.clear();		
			return returnBit;
		}
		
		// flips boolean from false to true
		else if(value==false) {
			returnBit.set();		
			return returnBit;
		}
		
		// error should not gotten to
		else {
			throw new Exception("Error: error occured in not bit operator");
		}
	}
	
	// returns "t" or "f" for true and false respectively
	public String toString() {
		if(value==true) return "t";
		else if(value==false) return "f";
		
		// error should not gotten to
		else {
			return "error: boolean has no value set";
		}
	}
	
	
}
