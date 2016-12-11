package fjsum;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// Fork/Join implementation of summing array of integers.

public class ForkJoinSum implements SumArray {

	private static class SumTask extends RecursiveTask<Long>{
		private final int[] elts;
		private final int first;
		private final int size;
		
		SumTask (int[] elts, int first, int size) {
			this.elts = elts;
			this.first = first;
			this.size = size;
		}
		
		public Long compute () {
			if (size == 0) {
				return new Long(0);
			}
			else if (size == 1) {
				return new Long(elts[first]);
			}
			else {
				int size1 = (size/2);
				int first2 = first + size1;
				int size2 = size - size1;
				SumTask subtask1 = new SumTask(elts, first, size1);
				SumTask subtask2 = new SumTask(elts, first2, size2);
				subtask1.fork();
				return (subtask2.compute() + subtask1.join());
			}
		}
	}
	
	public Long sum (int[] elts) {
		ForkJoinPool pool = new ForkJoinPool();
		Long result = pool.invoke(new SumTask(elts, 0, elts.length));
		pool.shutdown();
		return result;
	}
}
