package fjmax;
import java.util.Random;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class MaxSolver extends RecursiveTask<Integer> {
	private final static int THRESHOLD = 500000;
	private final MaxProblem problem;
	
	public MaxSolver(MaxProblem prob) {
		this.problem = prob;
	}
	
	public Integer compute() {
		if (problem.size() < THRESHOLD) {
			return problem.solveSequentially();
		}
		else {
			int m = problem.size() / 2;
			MaxSolver left, right;
			left = new MaxSolver(problem.subproblem(0, m));
			right = new MaxSolver(problem.subproblem(m,problem.size()));
			left.fork();
			return Math.max(right.compute(), left.join());
		}
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

	public static void main(String args[]) {
		int nThreads = 9;
		int arrSz = 100000000;
		int[] array = createRand(arrSz);
		MaxProblem prob = new MaxProblem(array,0,arrSz);
		long startTm = System.currentTimeMillis();
		int result = prob.solveSequentially();
		long endTm = System.currentTimeMillis();
		System.out.println("SEQ: max is "+result+" time ="+(endTm-startTm)+" ms");
		startTm = System.currentTimeMillis();
		ForkJoinPool pool = new ForkJoinPool(nThreads);
		MaxSolver solver = new MaxSolver(prob);
		result = pool.invoke(solver);
		endTm = System.currentTimeMillis();
		System.out.println("FJ: max is "+result+" time ="+(endTm-startTm)+" ms");
	}
}
