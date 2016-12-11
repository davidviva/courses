package fjsum;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

// Test harness for sum routines.

public class SumDriver {
	
	final static int NUMELTS = 10000000;  		// Number of elements in array
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
	
	// Routine for running sum routines.  The sort is nondestructive;
	// the original array is not disturbed.
	
	public static int[] runSum (String sumName, SumArray s, int[] elts){
		
//		System.out.println ("Initial array:  " + intArrayToString(elts));
		
		System.out.print(sumName + " starts ... ");
		long start = System.nanoTime();
		Long result = s.sum(elts);
		long end = System.nanoTime();
		System.out.println("finishes in " + toString(end-start) + " ns.");
		
//		System.out.println ("sum:  " + toString(result));
		
		return elts;

	}
	
	// Main

	public static void main(String[] args) {
		
		// Create a random array with NUMELTS elements
		
		final int[] elts = new int[NUMELTS];
		Random r = new Random();
		for (int i = 0; i < NUMELTS; i++) {
			elts[i] = r.nextInt();
		}
		
		// Create summing objects.
		
		SumArray SI = new SumArray () {
			public Long sum (int[] elts) {
				return SequentialSumUtils.sequentialSumIterative(elts,0,elts.length);
			}
		};
		SumArray FJS = new ForkJoinSum();
		SumArray FJST = new ForkJoinSumTunable();
		
		// Print number of CPUs, elements
		
		System.out.println("Number of CPUs:  " + NUMCPUS);
		System.out.println("Number of elements to sum:  " + toString(NUMELTS));
//		System.out.println ("Initial array:  " + intArrayToString(origElts));
		System.out.println();
		
		// Run the sorting objects.

		runSum("Sequential recursive sum", SI, elts);
		runSum("Fork/Join sum", FJS, elts);
		runSum("Fork/Join sum tuned", FJST, elts);
	}

}
