import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Tuned version of parallel quick sort routine given in ParallelQuickSortTunable.  The difference
// with that routine is that a threshold is used to shift sorting from parallel to sequential.

public class ParallelQuickSortNewTunable implements IntSort {
	
	private ExecutorService exec;
	
	private int THRESHOLD = 2;
	
	// Inner private class of sorting tasks.  Each task is responsible for sorting the segment
	// elts[first, first+1, ..., first+size-1] using the sequential quick sort routine.
	
	private class PQSTask implements Runnable {
		private int elts[];
		private int first;
		private int size;
		
		public PQSTask (int elts[], int first, int size) {
			this.elts = elts;
			this.first = first;
			this.size = size;
		}
		
		public void run () {
			IntArraySortUtils.quickSortSegment (elts, first, size);
		}
	}
	
	// This routine is responsible for sorting segment elts[first, first+1, ..., first+size-1].
	// If the size is below the threshold, create a (sequential) sorting task and submit it to
	// the executor.  Otherwise, recurse.  Note that tasks are only submitted in  the "base"
	// case of the recursion.
	
	public void parallelQuickSortSegment (int[] elts, int first, int size) {
		if (size <= THRESHOLD) {
			exec.execute(new PQSTask (elts, first, size));
		}
		else {
			int pivotPosition = IntArraySortUtils.partitionSegment(elts, first, size);
			parallelQuickSortSegment (elts, first, pivotPosition-first);
			parallelQuickSortSegment (elts, pivotPosition+1, first+size-1-pivotPosition);
		}
	}
	
	// Parallel quick-sorting routine.  First, the number of threads is determined, a
	// new executor created and assigned to field exec, and a threshold value for changing
	// over to sequential sorting defined.  Then the segment-sorting routine is called;
	// when this terminates, no new tasks can be submitted, so we shut down the executor
	// and await its termination in order to determine when sorting is finished.
	
	public void sort (int[] elts) {
		int NUMTHREADS = Runtime.getRuntime().availableProcessors();
		exec = Executors.newFixedThreadPool(NUMTHREADS);
		THRESHOLD = elts.length / NUMTHREADS;
		parallelQuickSortSegment (elts, 0, elts.length);
		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) { }
	}
}
