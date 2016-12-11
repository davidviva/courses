
public class IntNodeSortUtils {
	
	// Class of lists with pointers to last nodes
	

	
	// Computes length of list argument
	// Precondition:  none
	// Postcondition:  computes number of nodes in list
	// Exception:  none
	
	public static int length (Node list) {
		int returnValue = 0;
		for (Node l = list; l != null; l = l.getNext())
			returnValue++;
		return returnValue;
	}

	// copyIntoList() turns an array into a linked list of nodes
	
	public static Node copyIntoList (int[] elts) {
		Node returnList = null;
		Node last = null;
		if (elts.length != 0) {
			returnList = new Node (elts[0]);
			last = returnList;
			for (int i=1; i < elts.length; i++) {
				last.setNext(new Node (elts[i]));
				last = last.getNext();
			}
		}
		return returnList;
	}
	
	// copyIntoArray copies the given list into the given array.  If the
	// list is longer than the array, the list is truncated.  If the list
	// is shorter than the array, the given default element is used to pad
	// the end of the array.
	
	public static void copyIntoArray (Node l, int[] elts, int defaultVal) {
		Node iterator = l;
		for (int i=0; i < elts.length; i++) {
			if (iterator != null) {
				elts[i] = iterator.getContents();
				iterator = iterator.getNext();
			}
			else
				elts[i] = defaultVal;
		}
	}
	
	// splitList splits list destructive into two halves, leaving the first
	// half in list and returning the second half.
	//
	// Precondition:  none
	// Postcondition:  the even-indexed elements (0, 2, 4, ...) are left in list;
	//    the odd-indexed ones (1, 3, 5, ...) are returned
	// Exception:  none
	
	public static Node splitList (Node list) {
		Node secondHalf = null;
		if (list != null) {
			if (list.getNext() != null) {
				
				Node endFirst = list;
				Node endSecond = list.getNext();
				Node rest = endSecond.getNext();
				secondHalf = endSecond;
				
				while (rest != null) {
					// Put first element at end of 1st half-list
					endFirst.setNext(rest);
					endFirst = rest;
					rest = rest.getNext();
					// If there is an element following, put it
					// at end of 2nd half-list
					if (rest != null) {
						endSecond.setNext(rest);
						endSecond = rest;
						rest = rest.getNext();
					}
				}
				// Terminate both half-lists
				endFirst.setNext(null);
				endSecond.setNext(null);
			}
		}
		return secondHalf;
	}
		
	// mergeLists destructively merges the two (sorted) lists, returning the result
	// Precondition:  None
	// Postcondition:  lists are merged, with result stored in list1.
	// Exception:  None
		
	public static Node mergeLists (Node list1, Node list2) {
		if (list1 == null)
			return list2;
		else if (list2 == null)
			return list1;
		else {
			
			// Both lists non-empty; initialize result list
			
			Node returnList;	// List to return
			Node last;			// last node added so far to returnList
			Node rest1;			// Remaining nodes in first list to process
			Node rest2;			// Remaining nodes in second list
			
			// Make the first node in the result list the smaller of the
			// first elements in list1 and list2.
			
			if (list1.getContents() <= list2.getContents()) {
				returnList = list1;
				rest1 = list1.getNext();
				rest2 = list2;
			}
			else {
				returnList = list2;
				rest1 = list1;
				rest2 = list2.getNext();
			}
			last = returnList;

			// Iterate through remainder of lists, adding smaller of two lead
			// elements to end of returnList.
			
			while (rest1 != null && rest2 != null) {
				if (rest1.getContents() <= rest2.getContents()) {
					last.setNext(rest1);
					last = rest1;
					rest1 = rest1.getNext();
				}
				else {
					last.setNext(rest2);
					last = rest2;
					rest2 = rest2.getNext();
				}
			}
			
			// Add remaining elements of the non-empty list to end of return.
			
			if (rest1 == null)
				last.setNext(rest2);
			else
				last.setNext(rest1);
			
			return returnList;
		}
	}

	// Mergesort routine for linked lists
	//
	// Precondition:  none
	// Postcondition:  returned list is sorted in ascending order
	// Exception:  none
	
	public static Node mergeSortList (Node list) {
		Node returnList = list;
		int length = length(list);
		if (length == 2) {
			Node secondNode = returnList.getNext();
			if (returnList.getContents() > secondNode.getContents()) {
				secondNode.setNext(returnList);
				returnList.setNext(null);
				returnList = secondNode;
			}

		}
		else if (length > 2) {
			Node l2 = splitList (returnList);
			Node l1 = mergeSortList (returnList);
			l2 = mergeSortList (l2);
			returnList = mergeLists (l1, l2);
		}
		return returnList;
	}
	
	// Class for holding results of Quicksort partitioning
	
	public static class PartitionResult {
		final public Node pivot;
		final public ListWithLastNode lessThanPivot;
		final public ListWithLastNode greaterThanPivot;
		
		PartitionResult () {
			this.pivot = null;
			this.lessThanPivot = null;
			this.greaterThanPivot = null;
		}
		
		PartitionResult (Node pivot, ListWithLastNode lt, ListWithLastNode gt) {
			this.pivot = pivot;
			this.lessThanPivot = lt;
			this.greaterThanPivot = gt;
		}
	}
	
	// Quicksort partitioning routine for lists with last-node indicators.
	
	public static PartitionResult partition (ListWithLastNode list) {
		if (list.getSize() == 0)
			return (new PartitionResult());
		else if (list.getSize() == 1)
			return (new PartitionResult (list.removeFromFront(), list, new ListWithLastNode()));
		else {
			Node pivot = list.removeFromFront();
			int pivotElt = pivot.getContents();
			ListWithLastNode lt = new ListWithLastNode();
			ListWithLastNode gt = new ListWithLastNode();
			while (!list.isEmpty()) {
				Node head = list.removeFromFront();
				if (head.getContents() < pivotElt) 
					lt.addToFront(head);
				else
					gt.addToFront(head);
			}
			return new PartitionResult (pivot, lt, gt);
		}
	}

	// Version of Quicksort that works with lists with end pointers.
	//
	// Precondition:  list is well-formed
	// Postcondition:  destructively sorts argument, returning result
	// Exception:  unpredictable behavior if list is not well-formed
	
	public static ListWithLastNode quickSort (ListWithLastNode list) {
		if (list.getSize() <= 1) // No sorting needed in this case
			return list;
		else if (list.getSize() == 2) { // Sort list directly if it has 2 elements
			Node first = list.getFirstNode();
			Node second = list.getLastNode();
			if (first.getContents() > second.getContents()) {
				list.setFirstNode(second);
				second.setNext(first);
				first.setNext(null);
				list.setLastNode(first);
			}
			return list;
		}
		else { // size is >= 3
			PartitionResult p = partition (list);
			ListWithLastNode lt = quickSort(p.lessThanPivot);
			ListWithLastNode gt = quickSort(p.greaterThanPivot);
			gt.addToFront(p.pivot);
			lt.appendToEnd(gt);
			return lt;
		}
	}
	
	// Routine for sorting lists without last-node indicators
	
	public static Node quickSort (Node list) {
		if ((list == null) || (list.getNext() == null))
			return list;
		else
			return (quickSort (new ListWithLastNode (list))).getFirstNode();
	}
	
	// Mergesort algorithm for arrays.
	//
	// Precondition:  none
	// Postcondition:  array is left in ascending-sorted order
	// Exception:  none
	
	public static void mergeSort (int[] elts) {
		Node list = copyIntoList (elts);
		list = mergeSortList (list);
		copyIntoArray (list, elts, 0);
	}

}
