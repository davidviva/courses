import java.util.ArrayList;

public class BoundedBufferWait {

	// Invariant:  number of elements is <= maxSize

	private final int maxSize;
	private ArrayList<Object> elements;

	BoundedBufferWait (int maxSize) {
		this.maxSize = maxSize;
		elements = new ArrayList<Object> ();
	}

	// Pre:  number of elements is below maxSize
	// Post:  elt is added to end of list of elements, waiting threads notified
	// Exception:  If number of elements is too high, suspend.
	public synchronized void put (Object elt) throws InterruptedException {
		while (elements.size() == maxSize) wait();
		elements.add(elt);
		notifyAll();
	}

	// Pre:  there is at least one element in list
	// Post:  first element is removed, returned, waiting threads notified
	// Exception:  If there are no elements in the list, suspend
	public synchronized Object take () throws InterruptedException {
		while (elements.size() == 0)
			wait();
		Object elt = elements.get(0);
		elements.remove(0);
		notifyAll();
		return elt;
	}
}
