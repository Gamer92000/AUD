package lab;

/**
 * Aufgabe H1b)
 * 
 * Abgabe von: Philip Jonas Franz (2447302), Julian Imhof (2689225) und Nicolas Petermann (2918103)
 */

public class Card {
	
	// DO NOT MODIFY
	public enum Suit {
		Hearts, Diamonds, Clubs, Spades
	}
	
	// DO NOT MODIFY
	public int value;
	public Suit suit;
	
	// DO NOT MODIFY
	public Card() {
	}
	
	// DO NOT MODIFY
	public Card(int value, Suit suit) {
		this.value = value;
		this.suit = suit;
	}
	
	// DO NOT MODIFY
	public Card(Card other) {
		this.value = other.value;
		this.suit = other.suit;
	}
	
	public String toString() {
		return value+";"+suit;
	}
	
	/**
	 * Compare two card objects. Return -1 if this is deemed smaller than the object other, 0 if they are
	 * deemed of identical value, and 1 if this is deemed greater than the object other.
	 * @param other The object we compare this to.
	 * @return -1, 0 or 1
	 */
	public int compareTo(Card other) {
		// TODO: implement
			int compThis = this.suit == Suit.Diamonds ? 0 : this.suit == Suit.Hearts ? 1 : this.suit == Suit.Spades ? 2 : 3;
			int compOther = other.suit == Suit.Diamonds ? 0 : other.suit == Suit.Hearts ? 1 : other.suit == Suit.Spades ? 2 : 3;
			return this.value < other.value ? -1
				 : this.value > other.value ?  1
				   : compThis < compOther   ? -1
				   : compThis > compOther   ? 1 : 0;
	}
}
