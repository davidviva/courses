
public class IntArraySortUtils {
	
	// Swaps elements at positions i, j.
	//
	// Precondition:  i, j are valid indices into array
	// Postcondition:  elements at i, j are swapped
	// Exception:  if indices are invalid an exception may be thrown.
	
	public static void swap (int[] elts, int i, int j) {
		int temp = elts[i];
		elts[i] = elts[j];
		elts[j] = temp;
	}
	
	// Finds position of minimum element in [first ... first+size-1] segment of array
	//
	// Precondition:  first, first+size-1 are valid indices into array
	// Postcondition:  returned value is position of smallest element in the segment
	// Exception:  if precondition is violated, an exception may be thrown
	
	public static int findMinPositionSegment (int[] elts, int first, int size) {
		int min = elts[first];
		int minPosition = first;
		for (int i = first+1; i < first+size; i++) {
			if (elts[i] < min) {
				min = elts[i];
				minPosition = i;
			}
		}
		return minPosition;
	}
	
	// partitionSegment partitions array segment into those <= the
	// initial element and those > the initial element.
	//
	// Precondition:  first, ..., first+size-1 are valid indices
	// Postcondition:  elts[first ... first+size-1] are rearranged so that
	//   original e[first] is preceded by all elements <= to it and followed
	//   by all elements > it.
	// Exception:  if the precondition is violated an exception is thrown
	
	public static int partitionSegment (int[] elts, int first, int size) {
		int pivotPosition = first;
		int firstGreaterPosition = first+size;
		while (pivotPosition  < firstGreaterPosition-1) {
			if (elts[pivotPosition] >= elts[pivotPosition+1]) {
				swap (elts, pivotPosition, pivotPosition+1);
				pivotPosition++;
			}
			else {
				firstGreaterPosition--;
				swap (elts, pivotPosition+1, firstGreaterPosition);
			}
		}
		// System.out.println("PartitionSegment terminates.");
		return pivotPosition;
	}
	
	// Destructive sort of array segment [first, first+1, ... first+size-1].
	//
	// Precondition:  first, first+size-1 are valid indices in the given array
	// Postcondition:  array segment defined by first, size is sorted in ascending order
	// Exception:  if the precondition does not hold then an exception may be thrown
	
	public static void quickSortSegment (int[] elts, int first, int size) {
		if (size == 2) {
			if (elts[first] > elts[first+1])
				swap (elts, first, first+1);
		}
		else if (size > 2) {
			int pivotPosition = partitionSegment(elts, first, size);
			quickSortSegment (elts, first, pivotPosition-first);
			quickSortSegment (elts, pivotPosition+1, first+size-1-pivotPosition);
		}
	}
	
	// Array sorting routine implementing Quicksort algorithm
	
	public static void quickSort (int[] elts) {
		quickSortSegment (elts, 0, elts.length);
	}
	
	// Array sorting routine implementing Selectionsort algorithm
	
	public static void selectionSort (int[] elts) {
		int minPosition;
		int length = elts.length;
		for (int i = 0; i < length-1; i++) {
			minPosition = findMinPositionSegment (elts, i, length-i);
			if (minPosition != i)
				swap (elts, i, minPosition);
		}
	}

}
