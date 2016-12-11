package L2;

public class RunnableBA implements Runnable {
	
	private Object firstLock;
	private Object secondLock;
	
	public RunnableBA (Object a, Object b) {
		firstLock = b;
		secondLock = a;
	}
	
	public void run () {
		synchronized (firstLock) {
			try {Thread.sleep (50);} catch (InterruptedException e) {}
			synchronized (secondLock) {
				System.out.println ("BA succeeds");
			}
		}
	}
}
