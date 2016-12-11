import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

// Performance-tunable version of parallelized quickSort.  The tuning parameters
// are the thread pool, a sequential sort routine that can be used, and the number
// of CPUs.

public class ParallelQuickSortTunable implements IntSort {
	
	final ExecutorService threadPool;
	final IntArraySegmentSort sequentialSort;
	final int numCPUs;
	
	ParallelQuickSortTunable (
			ExecutorService threadPool,
			IntArraySegmentSort sequentialSort,
			int numCPUs)
	{
		this.threadPool = threadPool;
		this.sequentialSort = sequentialSort;
		this.numCPUs = numCPUs;
	}
	
	// Class of tasks used in sorting.  Each task is responsible for sorting
	// a segment of elts, namely, elts[first ... first+size-1].
	
	private class PQSTask implements Runnable {
		
		private final int[] elts;
		private final int first;
		private final int size;
		private final CountDownLatch numUnsorted;
		
		private void reduceUnsorted (int j) {
			for (int i=0; i < j; i++)
				numUnsorted.countDown();
		}
		
		PQSTask (int[] elts, int first, int size, CountDownLatch numUnsorted) {
			this.elts = elts;
			this.first = first;
			this.size = size;
			this.numUnsorted = numUnsorted;
		}
		
		// run method that will be invoked inside Executor.  If the size of
		// the segment is > 2 then partition and create subtasks for the
		// segments to the left and right of the pivot element (the original
		// elts[first]).  Otherwise, partitioning sorts the segment.
		
		public void run () {
			int sequentializeTarget = elts.length / (numCPUs+2);
			if (size <= sequentializeTarget) {
				sequentialSort.sortSegment (elts, first, size);
				reduceUnsorted (size);
			}
			else {
				final int pivotPosition = IntArraySortUtils.partitionSegment(elts, first, size);
				final int size1 = pivotPosition-first;
				final int size2 = first+size-1-pivotPosition;
				numUnsorted.countDown(); // pivot in correct sorted position
				threadPool.execute(new PQSTask (elts, first, size1, numUnsorted));
				threadPool.execute(new PQSTask (elts, pivotPosition+1, size2, numUnsorted));
			}
		}
	}
	
	// sort() sorts elts by creating a task for sorting the entire array
	// and sending it to the threadPool Executor.  It then waits until all
	// elements have been sorted before terminating.
	
	public void sort (int[] elts) {
		int length = elts.length;
		CountDownLatch numUnsorted = new CountDownLatch(length);  // Used to count remaining unsorted elements
		threadPool.execute(new PQSTask (elts, 0, length, numUnsorted));
		try {
			numUnsorted.await();
		}
		catch (InterruptedException e) {}
	}
}
