package lab;

/**
 * Aufgabe H1
 * 
 * Abgabe von: Philip Jonas Franz (2447302), Julian Imhof (2689225) und Nicolas Petermann (2918103)
 */

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import frame.SortArray;

/**
 * Use this class to implement your own tests.
 */
class YourTests {

	private ArrayList<Card> generateRandomDeck(int n) {
		ArrayList<Card> result = new ArrayList<Card>();
		for (int i = 0; i < n; i++) {
			result.add(new Card((int)(Math.random()*200), Card.Suit.values()[(int)(Math.random()*3)]));
		}
		return result;
	}
	
	@Test
	void test() {
		System.out.println("Testing complexity - this takes some time!");
		System.out.println("k = 0 (only QuickSort)");
		for (int i = 0; i < 5000; i++) {
			ArrayList<Card> testMeArr = generateRandomDeck(2);
			SortArray testMe = new SortArray(testMeArr);
			HybridSort hs = new HybridSort();
			hs.sort(testMe, 0);
			int testBool = (testMeArr.get(0).compareTo(testMeArr.get(1)) == -1 || testMeArr.get(0).compareTo(testMeArr.get(1)) == 0) ? 1 : 0;
			assertEquals(testBool, 1);
		}
		System.out.println("k = 10 (only Insertion)");
		for (int i = 0; i < 5000; i++) {
			ArrayList<Card> testMeArr = generateRandomDeck(2);
			SortArray testMe = new SortArray(testMeArr);
			HybridSort hs = new HybridSort();
			hs.sort(testMe, 10);
			int testBool = (testMeArr.get(0).compareTo(testMeArr.get(1)) == -1 || testMeArr.get(0).compareTo(testMeArr.get(1)) == 0) ? 1 : 0;
			assertEquals(testBool, 1);
		}
		System.out.println("k = 5 (Hybrid)");
		for (int i = 0; i < 5000; i++) {
			ArrayList<Card> testMeArr = generateRandomDeck(10);
			SortArray testMe = new SortArray(testMeArr);
			HybridSort hs = new HybridSort();
			hs.sort(testMe, 5);
			for (int j = 0; j < 9; j++) {
				int testBool = (testMeArr.get(j).compareTo(testMeArr.get(j+1)) == -1 || testMeArr.get(j).compareTo(testMeArr.get(j+1)) == 0) ? 1 : 0;
				assertEquals(testBool, 1);
			}
		}
	}
}
