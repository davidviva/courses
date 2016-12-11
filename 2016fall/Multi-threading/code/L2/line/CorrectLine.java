package line;

public class CorrectLine {

	//@Invariant:  p1 and p2 must be different points
	
	private Point p1;
	private Point p2;
	
	// CorrectLine throws exception if points overlap
	CorrectLine (Point p1, Point p2) throws IllegalArgumentException {
		if (!p1.equals(p2)) {
			this.p1 = p1;
			this.p2 = p2;
		}
		else {
			throw new IllegalArgumentException (
					"Points to Line Constructor must be different:  " +
					p1.toString() + "given twice.");
		}
	}
	
	// Precondition:  p1 and p2 do not form a vertical line
	// Postcondition:  returns slope of line formed by p1, p2
	// Exception:  if p1 and p2 form a vertical line, an ArithmeticException
	//             is thrown.
	
	public double slope () throws ArithmeticException {
		return ((p1.getY() - p2.getY()) / (p1.getX() - p2.getX()));
	}

}