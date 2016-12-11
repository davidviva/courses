import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class ParallelMergeSort implements IntSort {
	
	final ExecutorService threadPool;
	ParallelMergeSort (ExecutorService threadPool){
		this.threadPool = threadPool;
	}
	
	// Class of tasks used in sorting.  Each task is responsible for sorting
	// a list of integers.
	
	private class PMSTask implements Callable<Node> {
		
		private Node list;
		
		PMSTask (Node list) {
			this.list = list;
		}
		
		// call method that will be invoked inside Executor.  If the size of
		// the list is >= 3 then split list into two, sort each list, and merge
		// results.  Otherwise, sort directly.
		
		public Node call () {
			// Empty list
			if (list == null) {
				return list;
			}
			// List with one element
			else if (list.getNext() == null){
				return list;				
			}
			// List with > 1 element
			else {
				Node secondNode = list.getNext();
				// List with 2 elements
				if (secondNode.getNext() == null) {
					if (list.getContents() <= secondNode.getContents())
						return list;
					else {
						list.setNext(null);
						secondNode.setNext(list);
						return secondNode;
					}
				}
				// List with >= 3 elements
				else { // at least 3 elements
					Node secondHalf = IntNodeSortUtils.splitList (list);
					// System.out.println("list:  " + list.toString());
					// System.out.println("secondHalf:  " + secondHalf.toString());
					Future<Node> result1 = threadPool.submit(new PMSTask(list));
					Future<Node> result2 = threadPool.submit(new PMSTask(secondHalf));
					try {
						Node list1 = result1.get();
						Node list2 = result2.get();
						return (IntNodeSortUtils.mergeLists(list1, list2));
					}
					catch (InterruptedException e) {}
					catch (ExecutionException e) {}
					return null;
				}
			}
		}
	}
	
	// sort() sorts elts by creating a task for sorting the entire array
	// and sending it to the threadPool Executor.  It then waits until all
	// elements have been sorted before terminating.
	
	public void sort (int[] elts) {
		Node list = IntNodeSortUtils.copyIntoList (elts);
		Future<Node> result = threadPool.submit(new PMSTask (list));
		try {
			list = result.get();
		}
		catch (InterruptedException e) {}
		catch (ExecutionException e) {}
		finally {
			IntNodeSortUtils.copyIntoArray (list, elts, 0);
		}
	}

}
