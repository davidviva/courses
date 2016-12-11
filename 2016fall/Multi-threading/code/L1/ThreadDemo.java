package threads;

import java.lang.*;

public class ThreadDemo implements Runnable {

   ThreadDemo() {
      // main thread
      Thread currThread = Thread.currentThread();
      // thread created
      Thread t = new Thread(this, "Admin Thread");
   
      System.out.println("current thread = " + currThread);
      System.out.println("thread created = " + t);
      
            int pr = t.getPriority();
      System.out.println("Priority = " + pr);
      
      // this will call run() function
      t.start();
   }

   public void run() {
      System.out.println("This is run() method");
   }

   public static void main(String args[]) {
      new ThreadDemo();
      
   }
} 