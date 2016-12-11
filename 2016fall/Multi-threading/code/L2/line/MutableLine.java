package line;

public class MutableLine {
	private Point p1;
	private Point p2;
	
	public synchronized static void Foo () { }
	
	public synchronized static void Bar () {
		synchronized (MutableLine.class) {}
	}
	
	MutableLine (Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Point getP1 () {
		return p1;
	}
	
	public Point getP2() {
		return p2;
	}

	public synchronized void setP1(Point p1) {
		this.p1 = p1;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
	}
	
	public double slope () {
		return ((p1.getY() - p2.getY()) / (p1.getX() + p2.getX()));
	}
	

}
