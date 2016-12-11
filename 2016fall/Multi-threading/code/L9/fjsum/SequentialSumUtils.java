package fjsum;

public class SequentialSumUtils {
	
	public static Long sequentialSumIterative (int[] elts, int first, int size) {
		int result = 0;
		for (int i = first; i < first+size; i++) {
			result += elts[i];
		}
		return new Long(result);
	}

	public static Long sequentialSumRecursive (int[] elts, int first, int size) {
		if (size == 0) {
			return new Long(0);
		}
		else if (size == 1) {
			return new Long(elts[first]);
		}
		else {
			int size1 = size/2;
			int first2 = first + size1;
			int size2 = size-size1;
			
			return new Long(
				sequentialSumRecursive(elts, first, size1)
				+ sequentialSumRecursive(elts, first2, size2));
		}
	}
	
}
