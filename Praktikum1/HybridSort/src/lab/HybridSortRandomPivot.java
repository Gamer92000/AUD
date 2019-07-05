package lab;

import java.util.Random;

/**
 * Aufgabe H1c)
 * 
 * Abgabe von: Philip Jonas Franz (2447302), Julian Imhof (2689225) und Nicolas Petermann (2918103)
 */

/**
 * Use a random pivot within Quick Sort.
 */
public class HybridSortRandomPivot extends HybridSort {
	// TODO: Implement
	protected int selectPiv(int p, int r) {
		return (int) (new Random().nextInt((r - p) + 1) + p);
	}
}
