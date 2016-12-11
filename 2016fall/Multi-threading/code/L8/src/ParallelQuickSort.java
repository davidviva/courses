import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

// Parallelized Quicksort implementation
//
// @author Rance Cleaveland
// @date 2012-10-13

// The class creates an object consisting of a sort method that uses an
// Executor passed in as an argument to the constructor.  An inner class
// defining tasks to be run by the executor is also given.  Threads work on
// part of the array to be sorted:  they partition it in the classical Quicksort
// fashion and create new tasks for each of the two blocks resulting from the
// partition.

public class ParallelQuickSort implements IntSort {
	
	final ExecutorService threadPool;
	
	ParallelQuickSort (ExecutorService threadPool){
		this.threadPool = threadPool;
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
			if (size == 1)
				numUnsorted.countDown();
			else if (size == 2) {
				if (elts[first] > elts[first+1])
					IntArraySortUtils.swap (elts, first, first+1);
				reduceUnsorted(size);	
			}
			else if (size > 2) {
				final int pivotPosition = IntArraySortUtils.partitionSegment(elts, first, size);
				numUnsorted.countDown(); // pivot in correct sorted position
				threadPool.execute(new PQSTask (elts, first, pivotPosition-first, numUnsorted));
				threadPool.execute(new PQSTask (elts, pivotPosition+1, first+size-1-pivotPosition, numUnsorted));
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
