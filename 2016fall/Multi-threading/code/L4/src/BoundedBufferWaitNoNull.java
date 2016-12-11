// Attempt to filter out null objects from being inserted.
// THIS DOES NOT WORK!! IT SUFFERS FROM NESTED MONITOR LOCKOUT!

public class BoundedBufferWaitNoNull {

	private final BoundedBufferWait buffer;
	
	BoundedBufferWaitNoNull (int capacity) {
		buffer = new BoundedBufferWait(capacity);
	}
	
	public synchronized boolean put (Object elt) throws InterruptedException {
		if (elt != null) {
			buffer.put(elt);
			return true;
		}
		else return false;
	}
	
	public synchronized Object take () throws InterruptedException {
		return buffer.take();
	}
}
