
public class ListWithLastNode {
	private Node firstNode;
	private Node lastNode;
	private int size;

	public ListWithLastNode () {
		firstNode = null;
		lastNode = null;
		size = 0;
	}
	
	public ListWithLastNode (Node firstNode) {
		this.firstNode = firstNode;
		size = 0;
		if (firstNode != null) {
			Node l = firstNode;
			for (; l.getNext() != null; l = l.getNext()) {
				size++;
			}
			size++;
			this.lastNode = l;
		}
		else
			lastNode = null;
	}

	public Node getFirstNode() {
		return firstNode;
	}

	public Node getLastNode() {
		return lastNode;
	}

	public int getSize() {
		return size;
	}

	public void setFirstNode (Node firstNode) {
		this.firstNode = firstNode;
	}

	public void setLastNode (Node lastNode) {
		this.lastNode = lastNode;
	}

	public void setSize (int size) {
		this.size = size;
	}
	
	public boolean isEmpty () {
		return (size == 0);
	}

	public void addToFront (Node n) {
		if (isEmpty())
			lastNode = n;
		n.setNext(firstNode);
		firstNode = n;
		size++;
	}
	
	public Node removeFromFront () {
		if (isEmpty())
			throw new RuntimeException ("Attempt to remove from empty list");
		Node head = firstNode;
		if (size == 1)
			lastNode = null;
		firstNode = this.firstNode.getNext();
		size --;
		return head;
	}
	
	public void appendToEnd (ListWithLastNode list) {
		if (size == 0) {
			size = list.getSize();
			firstNode = list.getFirstNode();
			lastNode = list.getLastNode();
		}
		else {
			lastNode.setNext(list.getFirstNode());
			lastNode = list.getLastNode();
			size += list.getSize();
		}
	}
}
