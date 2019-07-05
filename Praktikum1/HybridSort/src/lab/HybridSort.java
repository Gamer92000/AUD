package lab;

/**
 * Aufgabe H1b)
 * 
 * Abgabe von: Philip Jonas Franz (2447302), Julian Imhof (2689225) und Nicolas Petermann (2918103)
 */

import frame.SortArray;

public class HybridSort {
	
	protected int selectPiv(int p, int r) {
		return p;
	}
	
	private void ins(SortArray array, int l, int r) {
		//System.out.println("insertion");
		for (int j = l + 1; j <= r; j++) {
			Card key = array.getElementAt(j);
			int i = j-1;
			while(i >= l && array.getElementAt(i).compareTo(key) == 1) {
				array.setElementAt(i+1, array.getElementAt(i));
				i--;
			}
			array.setElementAt(i+1, key);
		}
	}
	
	private void realSort(SortArray array, int k, int l, int r) {
		if (l >= r) return;
		if (r-l < k) {
			ins(array, l, r);
		}
		else {
			int q = partition(array, l, r);
     		realSort(array, k, l, q-1);
     		realSort(array, k, q+1, r);
		}
	}
	
	private int partition(SortArray array, int p, int r) {
		
		int piv = selectPiv(p, r);
		
		Card pivot = new Card(array.getElementAt(piv));
		
		array.setElementAt(piv, array.getElementAt(r));
		array.setElementAt(r, pivot);
		
		int i = p-1;
		
		for (int j = p; j <= r-1; j++) {
			if(array.getElementAt(j).compareTo(pivot) != 1) {
				i++;
				Card tmp = new Card(array.getElementAt(j));
				array.setElementAt(j, array.getElementAt(i));
				array.setElementAt(i, tmp);
			}
		}
		
 		array.setElementAt(r, array.getElementAt(i+1));
 		array.setElementAt(i+1, pivot);
 		return i+1;
	}
	
	/**
	 * Sort the given array using a hybrid method of Quick Sort and Insertion Sort.
	 * 
	 * @param array The array to sort.
	 * @param k Parameter k when we switch from Quick Sort to Insertion Sort: If the size of the subset which should be sorted is less than k, use Insertion Sort,
	 * 			otherwise keep on using Quick Sort.
	 */
	public void sort(SortArray array, int k) {
		assert(k>=0);
		// TODO: Implement
		realSort(array, k, 0, array.getNumberOfItems() -1);
	}
	
}
