package L2;

public class RunnableAB implements Runnable {
	
	private Object firstLock;
	private Object secondLock;
	
	public RunnableAB (Object a, Object b) {
		firstLock = a;
		secondLock = b;
	}
	
	public void run () {
		synchronized (firstLock) {
			try {Thread.sleep (50);} catch (InterruptedException e) {}
			synchronized (secondLock) {
				System.out.println ("AB succeeds");
			}
		}
	}
}
