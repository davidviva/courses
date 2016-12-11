package synchronizedLists;
import java.util.List;


public class ListGetLast {

	public static Object getLast (List<Object> l) {
		int lastIndex = l.size() - 1;
		return (l.get(lastIndex));
	}

}
