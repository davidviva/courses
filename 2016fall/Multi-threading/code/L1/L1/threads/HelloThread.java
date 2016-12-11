package threads;
// @author Mitch Kokar
// A simple thread to print "Hello World!"
// Here it is introduced via subclassing of the class Thread

public class HelloThread extends Thread {


// Implement the run() mehtod of Runnable
    public void run() {
        System.out.println("Hello from a thread! (directly) ");
    }

	     public static void main(String args[]) {
         (new HelloThread()).start();    //start() invokes run()

    }

}