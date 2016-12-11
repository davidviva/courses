package synchronizedLists;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class SynchronizedListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// List<Integer> list = Collections.synchronizedList(new ArrayList<Integer> ());
		List<Integer> l = new ArrayList<Integer> ();
		l.add(2);
		System.out.println("l = " + l);
		List<Integer> syncL = Collections.synchronizedList(l);
		l.add(3);
		System.out.println("syncL = " + syncL);
		System.out.println("l = " + l);
	}

}
