import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//Parallelized Quicksort implementation
//
//@author Rance Cleaveland
//@date 2014-04-02

//The class creates an object consisting of a sort method that creates an executor,
//feeds it tasks, then shuts it down to determine when sorting is done.  An inner class
//defining tasks to be run by the executor is also given.  Threads work on
//part of the array to be sorted:  they partition it in the classical Quicksort
//fashion and create new tasks for each of the two blocks resulting from the
//partition.

public class ParallelQuickSortNew implements IntSort {
	
	private ExecutorService exec;
	
	// Inner class of tasks.  In this case, each tasks consists of swapping two elements.
	
	private class PQSTask implements Runnable {
		private int elts[];
		private int i;
		private int j;
		
		public PQSTask (int elts[], int i, int j) {
			this.elts = elts;
			this.i = i;
			this.j = j;
		}
		
		public void run () {
			IntArraySortUtils.swap (elts, i, j);
		}
	}
	
	// Sorts the array segment elts[first, first+1, ... first+size-1].  The structure
	// follows very closely that of sequential quick sort.  Note that new tasks are only
	// added into the executor in the "base case" of the recursion.
	
	public void parallelQuickSortSegment (int[] elts, int first, int size) {
		if (size == 2) {
			if (elts[first] > elts[first+1])
				exec.execute(new PQSTask (elts, first, first+1));
		}
		else if (size > 2) {
			int pivotPosition = IntArraySortUtils.partitionSegment(elts, first, size);
			parallelQuickSortSegment (elts, first, pivotPosition-first);
			parallelQuickSortSegment (elts, pivotPosition+1, first+size-1-pivotPosition);
		}
	}
	
	// The main sort routine.  Note that an executor is created and assigned to the "exec"
	// field.  This executor is used by the segment-sorting routine above.  When the call
	// to the segment-sorting method returns, there can be no more tasks submitted.  In this
	// case, we shut down the executor and wait for it to terminate; this signals that all
	// sorting is done.
	
	public void sort (int[] elts) {
		int NUMTHREADS = Runtime.getRuntime().availableProcessors();
		exec = Executors.newFixedThreadPool(NUMTHREADS);
		parallelQuickSortSegment (elts, 0, elts.length);
		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) { }
	}
}
