package line;
// Class of mutable points
//@author Rance Cleaveland
public class MutablePoint {
	private double x;
	private double y;
	
	MutablePoint (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	double getX () {
		return x;
	}
	
	double getY () {
		return y;
	}
	
	void setX (double newX) {
		x = newX;
	}
	
	void setY (double newY) {
		y = newY;
	}
}
