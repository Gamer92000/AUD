package lab;

/**
 * Aufgabe H1 a)
 * 
 * Abgabe von: Philip Jonas Franz (2447302), Julian Imhof (2689225) und Nicolas Petermann (2918103)
 */

import frame.ListNode;
import frame.TableEntry;

public class LinkedList {
	
	private ListNode _head;
	private ListNode _nil;
	
	public LinkedList() {
		_nil = new ListNode(null, null, null);
		_nil.setNext(_nil);
		_nil.setPrev(_nil);
		_head = _nil;
	}
	
	public ListNode head() {
		return _head;
	}
	
	public ListNode nil() {
		return _nil;
	}
	
	/**
	 * Return the number of elements in the linked list.
	 */
	public int length() {
		int result = 0;
		ListNode curr = _head;
		while (curr != _nil) {
			curr = curr.next();
			result++;
		}
		return result;
	}
	
	/**
	 * Insert an entry into the linked list at the position before the given node.
	 */
	public void insertBefore(TableEntry entry, ListNode node) {
		ListNode _new = new ListNode(entry, node.prev(), node);
		if(node.prev() 	!= _nil ) node.prev().setNext(_new);
		if(node 		!= _nil ) node		.setPrev(_new);
		if(node 		== _head) _head = _new;
	}
	
	/**
	 * Append an entry to the end of the list.
	 */
	public void append(TableEntry entry) {
		ListNode curr = _head;
		if(curr == _nil) {
			ListNode _new = new ListNode(entry, _nil, _nil);
			_head = _new;
			return;
		}
		while (curr.next() != _nil) curr = curr.next();
		ListNode _new = new ListNode(entry, curr, _nil);
		curr.setNext(_new);
	}
	
	/**
	 * Delete the given node from the linked list.
	 */
	public void delete(ListNode node) {
		if(_head == node) _head = node.next();
		node.prev().setNext(node.next());
		node.next().setPrev(node.prev());
	}
}
