package thread.safety;

public class BoundedCounterIncThread implements Runnable {
	private BoundedCounterThreadSafe counter;
	BoundedCounterIncThread (BoundedCounterThreadSafe c){
		this.counter = c;
	}
	public void run () {
		counter.inc();
	}
}
