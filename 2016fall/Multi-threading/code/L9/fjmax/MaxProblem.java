package fjmax;
public class MaxProblem {
	private final int[] array;
	private final int start, end;
	public MaxProblem(int[] array, int start, int end) {
		int sz = array.length;
		if (start < 0 || start > sz || end <= start || end > sz)
			throw new IllegalArgumentException("Illegal problem");
		this.array = array;
		this.start = start;
		this.end = end;
	}
	public int solveSequentially() {
		int max = 0;
		for (int i = start; i<end; i++) {
			max = Math.max(max,array[i]);
		}
		return max;
	}
	public int size() {
		return (end - start);
	}
	public MaxProblem subproblem(int newStart, int newEnd) {
		newStart += start;
		newEnd += start;
		if (newStart > end || newEnd <= newStart || newEnd > end)
			throw new IllegalArgumentException("Illegal subproblem");
		return new MaxProblem(array,newStart,newEnd);
	}
}
