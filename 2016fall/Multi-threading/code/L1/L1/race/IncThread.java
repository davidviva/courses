package race;
// Class of threads that increment a shared variable.
// @author Rance Cleaveland

public class IncThread implements Runnable {
	
	private static int shared = 0;	// Shared variable among threads
	private String name = "";		// Name of thread
	
	// Constructor.  Argument is name.
	
	IncThread (String name) {
		this.name = name;
	}
	
	// Return value of shared
	public static int getShared() {
		return shared;
	}

	// Reset shared variable to 0
	public static void resetShared() {
		shared = 0;
	}
	
	// run method reads shared variable into private variable, prints
	// value, increments private variable, then writes back.
	public void run () {
		int myShared = shared;
		System.out.println (name + " read shared = " + myShared);
		myShared++;
		shared = myShared;
		System.out.println (name + " assigned to shared: " + myShared);
	}
}
