package lab;

/**
 * Aufgabe H1 b) und c)
 * 
 * Abgabe von: Philip Jonas Franz (2447302), Julian Imhof (2689225) und Nicolas Petermann (2918103)
 */

import java.util.Random;

import frame.ListNode;
import frame.TableEntry;

public class HashTable {
	
	private int capacity;
	private int hash_a;
	private int hash_b;
	private LinkedList[] entryLists;
	private int entries; 
	
	// add your own variables if you want
	
	private boolean isPrime(int n) {
	    if (n == 2) return true;
		if (n % 2 == 0) return false;
	    for (int i = 3; i * i <= n; i += 2) if(n%i==0) return false;
	    return true;
	}
	
	private void generateAnB() {
		hash_b = new Random().nextInt(capacity - 1);
		if(capacity == 2) {
			hash_a = 1;
			return;
		}
		hash_a = new Random().nextInt(capacity - 2) + 1;
	}
	
	 /**
     * The constructor
     * 
     * @param initialCapacity
     *            represents the initial size of the Hash Table.
     * 
     * The Hash-Table itself should be implemented in the array entryLists. When the
     * load factor exceeds 75%, the capacity of the Hash-Table should be
     * increased as described in the method rehash below.
     */
	public HashTable(int initialCapacity) {
		while(!isPrime(initialCapacity)) initialCapacity++;
		System.out.println(initialCapacity);
		capacity = initialCapacity;
		entryLists = new LinkedList[capacity];
		generateAnB();
	}
	
	/**
	 * Search for an TableEntry with the given key. If no such TableEntry is found, return null.
	 */
	public TableEntry find(String key) {
		int has = hash(key);
		LinkedList list = entryLists[has];
		if (list == null) return null;
		ListNode l = list.head();
		while (l != list.nil()) {
			if(l.entry().getKey() == key) return l.entry();
			l = l.next();
		}
		return null;
	}
	
	/**
	 * Insert the given entry in the hash table. If there exists already an entry with this key,
	 * this entry should be overwritten. After inserting a new element, it might be necessary
	 * to increase the capacity of the hash table.
	 */
	public void insert(TableEntry entry) {
		// TODO
		int has = hash(entry.getKey());
		if (entryLists[has] == null) entryLists[has] = new LinkedList();
		LinkedList list = entryLists[has];
		ListNode l = list.head();
		while (l != list.nil()) {
			if (l.entry().getKey() == entry.getKey()) {
				l.entry().setData(entry.getData());
				return;
			}
			l = l.next();
		}
		entryLists[has].append(entry);
		entries++;
		if (entries > .75 * capacity) rehash();
	}
	
	/**
	 * Delete the TableEntry with the given key from the hash table and return the entry.
	 * Return null if key was not found.  
	 */
	public TableEntry delete(String key) {
		int has = hash(key);
		if (entryLists[has] == null) return null;
		ListNode l = entryLists[has].head();
		while (l != entryLists[has].nil()) {
			if(l.entry().getKey() == key) {
				entryLists[has].delete(l);
				entries--;
				return l.entry();
			}
			l = l.next();
		}
		return null;
	}
	
	/**
	 * The hash function used in the hash table.
	 */
	public int hash(String x) {
		int sum = 0;
		for (int i = 0; i < x.length(); i++) {
			sum += (int)x.charAt(i) * (i+1);
		}
		sum = (hash_a * sum + hash_b) % capacity;
		return sum;
	}
	
	/**
	 * Return the number of TableEntries in this hash table.
	 */
	public int size() {
		return entries;
	}
	
	/**
	 * Increase the capacity of the hash table and reorder all entries.
	 */
	private void rehash() {
		capacity *= 10;
		while (!isPrime(capacity)) capacity++;
		generateAnB();
		LinkedList[] _new = new LinkedList[capacity];
		for(int i = 0; i < entryLists.length; i++) {
			LinkedList list = entryLists[i];
			if (list == null) continue;
			ListNode l = list.head();
			while(l != list.nil()) {
				int has = hash(l.entry().getKey());
				if(_new[has] == null) _new[has] = new LinkedList();
				_new[has].append(l.entry());
				l = l.next();
			}
		}
		entryLists = _new;
	}
	
	/**
	 * Return the current "quality" of the hash table. The quality is measured by calculating
	 * the maximum number of collisions between entries in the table (i.e., the longest linked
	 * list in the hash table).
	 */
	public int quality() {
		int largest = 0;
		for(int i = 0; i < entryLists.length; i++) {
			if (entryLists[i] == null) continue;
			if (entryLists[i].length() > largest) largest = entryLists[i].length();
		}
		return largest;
	}
	
	public int getHash_a() {
		return hash_a;
	}
	
	public int getHash_b() {
		return hash_b;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public LinkedList[] getEntryLists() {
		return entryLists;
	}

}
