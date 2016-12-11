package safety;

//Class of non-thread-safe bounded-counter objects
//@author Rance Cleaveland 2014-02-05

public class BoundedCounter {
	private int value = 0;
	private int upperBound = 0;
	
	//INVARIANT:  in all instances 0 <= value <= upperBound
	
	//Precondition:  argument must be >= 0
	//Postcondition:  object created
	//Exception:  If argument < 0, IllegalArgumentException thrown
	BoundedCounter (int upperBound) throws IllegalArgumentException {
		if (upperBound >= 0) this.upperBound = upperBound;
		else throw new IllegalArgumentException (
				"Bad argument to BoundedCounter: " + upperBound + "; must be >= 0");
	}
	
	//Precondition:  none
	//Postcondition:  current value returned
	//Exception:  none	
	public int current () {
		return value;
	}
	
	//Precondition:  none
	//Postcondition:  value reset to 0
	//Exception:  none
	public void reset () {
		value = 0;
	}
	
	//Precondition:  none
	//Postcondition:  returns boolean indicating whether or not value is maxed out
	//Exception:  none
	public boolean isMaxed () {
		return (value == upperBound);
	}
	
	//This method is incorrect, as it breaks the invariant
	//public void inc () {
	//	++value;
	//}
	
	//Precondition:  none
	//Postcondition:  increment value if not maxed; otherwise, do nothing.
	//Exception:  none
	public void inc () {
		if (!isMaxed()) ++value;
	}
}
