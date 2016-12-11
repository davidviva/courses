package threads;
// @author Mitch Kokar; modification of the UMd code
// A simple thread to print "Hello World!"

public class HelloRunnable implements Runnable {

static Runnable r;
static Thread t;

// Implement the run() mehtod of Runnable
    public void run() {
        System.out.println("Hello from a thread! (Via Runnable) ");
    }

	     public static void main(String args[]) {
//        (new Thread(new HelloRunnable())).start();

		 r = new HelloRunnable();
		 t = new Thread(r); //So you can access Thread methods
		 t.start();

    }

}