package fjsum;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// Fork-join implementation of sum routine for array of integers.  It is tunable in the sense that a threshold
// can be used to determine when a subproblem should be solved sequentially rather by spawning new tasks.

public class ForkJoinSumTunable implements SumArray {
	
	private static int THRESHOLD = 1;
	
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
			if (size <= THRESHOLD) {
				return SequentialSumUtils.sequentialSumIterative(elts, first, size);
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
		int NUMCPUS = Runtime.getRuntime().availableProcessors();
		ForkJoinPool pool = new ForkJoinPool();
		THRESHOLD = elts.length / (NUMCPUS);
		Long result = pool.invoke(new SumTask(elts, 0, elts.length));
		pool.shutdown();
		return result;
	}
}
