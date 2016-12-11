package race;

import static org.junit.Assert.*;

import org.junit.Test;

// Author:  Rance Cleaveland
// Date:  2014-01-29
//
// Test case to highlight data-race issues in IncThread class

public class TestRace {

	private int numRuns = 10; // Number of times to repeat execution
	
	// Test to illustrate interleaving issues when there is a race condition
	@Test
	public void test() throws InterruptedException {
		int passes = 0;  // Number of times correct number of increments occurs
		for (int i=0; i < numRuns; i++) {
			System.out.println ("\nRun "+i);
			System.out.println (  "-----");
			IncThread.resetShared();
			Thread t1 = new Thread (new IncThread ("t1"));
			Thread t2 = new Thread (new IncThread ("t2"));
			t1.start();
			t2.start();
			t1.join();  // Wait for t1 to finish
			t2.join();  // Wait for t2 to finish
			passes = (IncThread.getShared() == 2) ? (passes + 1) : passes;
		}
		System.out.println ("\n------------------------------\n");
		System.out.println ("# of runs = " + numRuns + "; # of passes = " + passes);
//		assertTrue (passes == numRuns);
	}

}
