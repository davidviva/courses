import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Test harness for parallel-sorting routines.

public class SortDriver {
	
	final static int NUMELTS = 1000000;  		// Number of elements in array
	final static int NUMCPUS = Runtime.getRuntime().availableProcessors();
	final static int NUMTHREADS = NUMCPUS + 1;	// Number of threads in thread pool
	
	// toString() utility for integers
	
	public static String toString(long i) {
		String sign = "";
		String result = "";
		String zeros = "";
		long dividend;
		long modulus;
		if (i < 0) {
			sign = "-";
			dividend = -1*i;
		}
		else
			dividend = i;
		while (dividend >= 1000) {
			modulus = dividend % 1000;
			if (modulus < 10) zeros = "00";
			else if (modulus < 100) zeros = "0";
			else zeros = "";
			result = "," + zeros + modulus + result;
			dividend = dividend / 1000;
		}
		result = sign + dividend + result;
		return result;
	}
	
	// toString() utility for integer arrays.
	
	public static String intArrayToString (int[] elts) {
		String result = "";
		for (int i=0; i < NUMELTS; i++)
			result += (toString(elts[i]) + " ");
		return result;
	}
	
	// Routine for running, timing sort routines.  The sort is nondestructive;
	// the original array is not disturbed.
	
	public static int[] runIntSort (String sortName, IntSort s, int[] origElts){

		// Create copy of original array to work on
		int[] elts = new int[origElts.length];
		System.arraycopy(origElts, 0, elts, 0, origElts.length);
		
//		System.out.println ("Initial array:  " + intArrayToString(elts));
		
		System.out.print(sortName + " starts ... ");
		long start = System.nanoTime();
		s.sort(elts);
		long end = System.nanoTime();
		System.out.println("finishes in " + toString(end-start) + " ns.");
		
//		System.out.println ("Sorted array:  " + intArrayToString(elts));
		
		return elts;

	}
	
	// Main

	public static void main(String[] args) {
		
		// Create a random array with NUMELTS elements
		
		final int[] origElts = new int[NUMELTS];
		Random r = new Random();
		for (int i = 0; i < NUMELTS; i++) {
			origElts[i] = r.nextInt();
		}
		
		// Create some executors
		
		ExecutorService threadPool1 = Executors.newFixedThreadPool(NUMTHREADS);
		ExecutorService threadPool2 = Executors.newCachedThreadPool();
		
		// Create sorting objects.
		
		IntSort QS = new IntSort() {
				public void sort (int[] elts) {
					IntArraySortUtils.quickSort(elts);
				}
			};
		IntSort SS = new IntSort() {
				public void sort (int[] elts) {
					IntArraySortUtils.selectionSort(elts);
				}
			};
		IntSort MS = new IntSort() {
				public void sort (int[] elts) {
					IntNodeSortUtils.mergeSort(elts);
				}
			};
		IntSort LQS = new IntSort() {
				public void sort (int[] elts) {
					Node l = IntNodeSortUtils.copyIntoList(elts);
					ListWithLastNode list = new ListWithLastNode (l);
					list = IntNodeSortUtils.quickSort(list);
					IntNodeSortUtils.copyIntoArray(list.getFirstNode(), elts, 0);
				}
			};
		IntSort PQS = new ParallelQuickSort(threadPool1);	
		IntSort PMS = new ParallelMergeSort(threadPool2);
		IntSort PQST = new ParallelQuickSortTunable (
				threadPool1,
				new IntArraySegmentSort () {
					public void sortSegment (int elts[], int first, int size) {
						IntArraySortUtils.quickSortSegment(elts, first, size);
					}
				},
				NUMCPUS
				);
		IntSort PQSnew = new ParallelQuickSortNew ();
		IntSort PQSnewT = new ParallelQuickSortNewTunable ();
		
		// Print number of CPUs, elements
		
		System.out.println("Number of CPUs:  " + NUMCPUS);
		System.out.println("Number of elements to sort:  " + toString(NUMELTS));
//		System.out.println ("Initial array:  " + intArrayToString(origElts));
		System.out.println();
		
		// Run the sorting objects.
		
		runIntSort("Quicksort", QS, origElts);
//		runIntSort("Selectionsort", SS, origElts);
//		runIntSort("Mergesort", MS, origElts);
		runIntSort("Parallel Quicksort", PQS, origElts);
//		runIntSort("Parallel Mergesort", PMS, origElts);
//		runIntSort("List Quicksort", LQS, origElts);
		runIntSort("Parallel Quicksort tuned", PQST, origElts);
		runIntSort("Parallel Quicksort new", PQSnew, origElts);
		runIntSort("Parallel Quicksort new tuned", PQSnewT, origElts);
		
		threadPool1.shutdown();
		threadPool2.shutdown();
	}

}
