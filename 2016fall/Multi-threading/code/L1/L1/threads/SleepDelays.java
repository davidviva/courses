package threads;
// @author Mitch Kokar (based on a UMd program)
// A simple program to demonstrate the sleep() functionality
// Useful for making a thread wait to give time for another thread to do something

public class SleepDelays {

    public static void main(String args[]) 
            throws InterruptedException {

        String someMessages[] = {
            "Wait a bit ...",
            "Wait a bit more ...",
            "Be patient!",
            "Sorry about the delays ..."
        };
        
        System.out.println("Testing your patience.");
        
        for (int i = 0;
             i < someMessages.length;
             i++) {
            //Pause for roughly 4 seconds
            //sleep() may cause exception and thus must have some of try/catch/finally
            Thread.sleep(4000);
            //Print next message
            System.out.println(someMessages[i]);
             }
            }
}