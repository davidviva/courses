
public class Node {
	final private int contents;
	private Node next;
	
	Node (int contents) {
		this.contents = contents;
		this.next = null;
	}
	
	public int getContents() { return contents; }
	public Node getNext() { return next; }
	public void setNext(Node n) { next = n; }
	
	public String toString() {
		String returnString = "";
		for (Node l = this; l != null; l = l.getNext()) {
			returnString += (l.getContents() + " ");
		}
		return returnString;
	}
	
	
}
