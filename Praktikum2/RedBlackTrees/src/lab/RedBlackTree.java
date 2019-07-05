package lab;

/**
 * Aufgabe H1a)
 * 
 * Abgabe von: Philip Jonas Franz (2447302), Julian Imhof (2689225) und Nicolas Petermann (2918103)
 */

import frame.TreeNode;
import frame.TreeNode.NodeColor;

/**
 * An implementation of a Black-Red-Tree
 */
public class RedBlackTree {
	
	private TreeNode _root;
	private TreeNode _nil;
	
	public RedBlackTree() {
		_nil = new TreeNode();
		
		this._nil.left = this._nil;
		this._nil.right = this._nil;
		this._nil.p = this._nil;
		
		_root = _nil;
	}
	
	public TreeNode root() {
		return this._root;
	}
	
	public TreeNode nil() {
		return this._nil;
	}
	
	/**
	 * Return the node with the given key, or nil, if no such node exists.
	 */
	public TreeNode search(int key) {
		TreeNode n = this._root;
		while (n != this._nil) {
			if (n.key == key)return n;
			
			if (key < n.key)n = n.left;
			else n = n.right;
		}
		return null;
	}
	
	/**
	 * Return the node with the smallest key
	 */
	public TreeNode minimum() {
		return minimumSubtree(_root);
	}
	
	/**
	 * Return the node with the smallest key in the subtree that has x as root node.
	 */
	public TreeNode minimumSubtree(TreeNode x) {
		if (x.left == this._nil)return x;
		else {
			while (x.left != this._nil)x = x.left;
			return x;
		}
	}
	
	/**
	 * Return the node with the greatest key.
	 */
	public TreeNode maximum() {
		return maximumSubtree(_root);
	}
	
	/**
	 * Return the node with the greatest key in the subtree that has x as root node.
	 */
	public TreeNode maximumSubtree(TreeNode x) {
		if (x.right == this._nil)return x;
		else {
			while (x.right != this._nil)x = x.right;
			return x;
		}
	}

	/**
	 * Insert newNode into the tree.
	 */
	public void insert(TreeNode newNode) {
		newNode.p = this.nil();
		newNode.left = this.nil();
		newNode.right = this.nil();
		
		if (this.root() == this.nil()) {
			this._root = newNode;
			newNode.color = NodeColor.BLACK;
			return;
		}
		else {
			TreeNode x = this._root;
			while (true) {
				if (newNode.key < x.key) {
					if (x.left == this.nil()) {
						x.left = newNode;
						newNode.p = x;
						break;
					}
					else x = x.left;
				}
				else {
					if (x.right == this.nil()) {
						x.right = newNode;
						newNode.p = x;
						break;
					}
					else x = x.right;
				}
			}
		}
		
		newNode.color = NodeColor.RED;
		this.fixColors(newNode);
	}
	
	private void fixColors(TreeNode z) {
		while (z.p.color == NodeColor.RED) {
			if (z.p == z.p.p.left) {
				TreeNode y = z.p.p.right;
				if (y.color == NodeColor.RED) {
					z.p.color = NodeColor.BLACK;
					y.color = NodeColor.BLACK;
					z.p.p.color = NodeColor.RED;
					z = z.p.p;
				}
				else {
					if (z == z.p.right) {
						z = z.p;
						this.rotateLeft(z);;
					}
					z.p.color = NodeColor.BLACK;
					z.p.p.color = NodeColor.RED;
					this.rotateRight(z.p.p);
				}
			}
			else {
				TreeNode y = z.p.p.left;
				if (y.color == NodeColor.RED) {
					z.p.color = NodeColor.BLACK;
					y.color = NodeColor.BLACK;
					z.p.p.color = NodeColor.RED;
					z = z.p.p;
				}
				else {
					if (z == z.p.left) {
						z = z.p;
						this.rotateRight(z);;
					}
					z.p.color = NodeColor.BLACK;
					z.p.p.color = NodeColor.RED;
					this.rotateLeft(z.p.p);
				}
			}
		}
		
		this._root.color = NodeColor.BLACK;
	}
	
	public void rotateLeft(TreeNode x) {
		TreeNode y = x.right;
		x.right = y.left;
		if (y.left != this._nil)y.left.p = x;
		y.p = x.p;
		if (x.p == this._nil)this._root = y;
		else {
			if (x == x.p.left)x.p.left = y;
			else x.p.right = y;
		}
		y.left = x;
		x.p = y;
	}
	
	public void rotateRight(TreeNode x) {
		TreeNode y = x.left;
		x.left = y.right;
		if (y.right != this._nil)y.right.p = x;
		y.p = x.p;
		if (x.p == this._nil)this._root = y;
		else {
			if (x == x.p.right)x.p.right = y;
			else x.p.left = y;
		}
		y.right = x;
		x.p = y;
	}
	
	/**
	 * Delete node toDelete from the tree.
	 */
	public void delete(TreeNode v) {
		this.deleteCase1(v);
	}
	
	public void deleteCase1(TreeNode n) {
		if (n != null && n.p != this._nil)this.deleteCase2(n);
	}
	
	public void deleteCase2(TreeNode n) {
		TreeNode s = this.sib(n);
		
		if (s.color == NodeColor.RED) {
			n.p.color = NodeColor.RED;
			s.color = NodeColor.BLACK;
			if (n == n.p.left)this.rotateLeft(n.p);
			else this.rotateRight(n.p);
		}
		else this.deleteCase3(n);
	}
	
	public void deleteCase3(TreeNode n) {
		TreeNode s = this.sib(n);
		if (s.left != null && s.right != null && n.p.color == NodeColor.BLACK && 
		    s.color == NodeColor.BLACK && 
		    s.left.color == NodeColor.BLACK && 
		    s.right.color == NodeColor.BLACK) {
			s.color = NodeColor.RED;
			this.deleteCase1(n.p);
		}
		else this.deleteCase4(n);
	}
	
	public void deleteCase4(TreeNode n) {
		TreeNode s = this.sib(n);
		
		if (s.left != null && s.right != null && n.p.color == NodeColor.RED &&
			s.color == NodeColor.BLACK &&
			s.left.color == NodeColor.BLACK &&
			s.right.color == NodeColor.BLACK) {
			s.color = NodeColor.RED;
			n.p.color = NodeColor.BLACK;
		}
		else this.deleteCase5(n);
	}
	
	public void deleteCase5(TreeNode n) {
		TreeNode s = this.sib(n);
		
		if (s.color == NodeColor.BLACK) {
			if (s.left != null && s.right != null && n == n.p.left && 
				s.right.color == NodeColor.BLACK &&
				s.left.color == NodeColor.RED) {
				s.color = NodeColor.RED;
				s.left.color = NodeColor.BLACK;
				this.rotateRight(s);
			}
			else if (s.left != null && s.right != null && n == n.p.right &&
					 s.left.color == NodeColor.BLACK &&
					 s.right.color == NodeColor.RED) {
				s.color = NodeColor.RED;
				s.right.color = NodeColor.BLACK;
				this.rotateLeft(s);
			}
		}
		this.deleteCase6(n);
	}
	
	public void deleteCase6(TreeNode n) {
		TreeNode s = this.sib(n);
		
		s.color = n.p.color;
		n.p.color = NodeColor.BLACK;
		
		if (n == n.p.left && s.right != null) {
			s.right.color = NodeColor.BLACK;
			this.rotateLeft(n.p);
		}
		else if (s.left != null) {
			s.left.color = NodeColor.BLACK;
			this.rotateRight(n.p);
		}
	}
	
	public TreeNode sib(TreeNode x) {
		TreeNode p = x.p;
		if (p.left == x)return p.right;
		else return p.left;
	}
}
