package fjmax;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class MaxSolverFlat {
    private final static int THRESHOLD = 500000;
    private final MaxProblem problem;

    public MaxSolverFlat(MaxProblem prob) {
	this.problem = prob;
    }
    
    public int compute(int nThreads, ExecutorService executor) throws Exception {
	int m = problem.size() / nThreads;
	List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();
	for (int i = 0; i< nThreads; i++) {
	    final MaxProblem p =
		(i == (nThreads - 1)) ? 
		problem.subproblem(i*m,problem.size()) :
		problem.subproblem(i*m,(i+1)*m);
	    Callable<Integer> c = new Callable<Integer>() {
		public Integer call() {
		    return p.solveSequentially();
		}
	    };
	    tasks.add(c);
	}
	int max = -Integer.MAX_VALUE;
	List<Future<Integer>> results = executor.invokeAll(tasks);
	for (Future<Integer> result : results)
	    max = Math.max(max,result.get());
	return max;
    }
	
    /**
     * Creates an array of the given size with random contents.
     */
    static private Random rand = new Random();

    public static int[] createRand(int num) {
        int[] result = new int[num];
        for(int i = 0 ; i < num; i++)
            result[i] = rand.nextInt(1000);
        return result;
    }

    public static void main(String args[]) throws Exception{
	int nThreads = 9;
	int arrSz = 100000000;
	int[] array = createRand(arrSz);
	MaxProblem prob = new MaxProblem(array,0,arrSz);
	long startTm = System.currentTimeMillis();
	int result = prob.solveSequentially();
	long endTm = System.currentTimeMillis();
	System.out.println("SEQ: max is "+result+" time ="+(endTm-startTm)+" ms");
	MaxSolverFlat solver = new MaxSolverFlat(prob);
	ExecutorService executor = Executors.newFixedThreadPool(nThreads);
	startTm = System.currentTimeMillis();
	result = solver.compute(nThreads,executor);
	endTm = System.currentTimeMillis();
	executor.shutdown();
	System.out.println("PAR: max is "+result+" time ="+(endTm-startTm)+" ms");
    }
}
