
public class AtomicCounter {
	private volatile int count;
	AtomicCounter () {
		count = 0;
	}
	public int get () {
		return count;
	}
	public void set (int n) {
		count = n;
	}
	public synchronized void add (int n) {
		count += n;
	}
	public synchronized void subtract (int n) {
		count -= n;
	}
	public synchronized void inc () {
		count++;
	}
	public synchronized void dec () {
		count--;
	}
}
