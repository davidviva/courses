package race;

// @author Rance Cleaveland 8/27/2012
//
// Program to show data race.  Repeated execution should lead to case
// in which both t1 and t2 assign "1" to the shared variable, even
// though both increment the shared variable (which is initially 0).

public class IncRace {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread (new IncThread ("t1"));
		Thread t2 = new Thread (new IncThread ("t2"));
		t1.start ();
//		Thread.sleep (10);
		t2.start ();
	}

}
