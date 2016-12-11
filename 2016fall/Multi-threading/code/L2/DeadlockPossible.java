package L2;

public class DeadlockPossible {

	public static void main(String[] args) {
		Object lockA = new Object ();
		Object lockB = new Object ();
		
		Thread t1 = new Thread (new RunnableAB (lockA, lockB));
		Thread t2 = new Thread (new RunnableBA (lockA, lockB));

		t1.start ();
		t2.start ();
	}

}
