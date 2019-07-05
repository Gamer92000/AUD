package lab;

import frame.Process;

/**
 * Aufgabe H1b)
 * 
 * Abgabe von: Philip Jonas Franz (2447302), Julian Imhof (2689225) und Nicolas Petermann (2918103)
 */

import frame.TreeNode;
import frame.TreeNode.NodeColor;

public class CompletelyFairScheduler {
	
	private RedBlackTree tree;
	private int windowMaxLength;
	
	/**
	 * Creating a new CompletelyFairScheduler
	 * @param windowMaxLength is the maximal length of one execution window
	 */
	public CompletelyFairScheduler(int windowMaxLength) {
		tree = new RedBlackTree();
		this.windowMaxLength = windowMaxLength;
	}
	
	/**
	 * Distribute execution windows to the processes.
	 * @param windows The number of execution windows to distribute
	 */
	public void run(int windows) {
		while (windows > 0 && !this.finished()) {
			Process p = this.tree.minimum().value;
			this.tree.delete(this.tree.minimum());
			int l = (windows >= this.windowMaxLength)? this.windowMaxLength : windows;
			windows -= l;
			p.run(l);
			while (this.tree.search(p.executionTime()) != null && !p.finished())p.run(1);
			if (!p.finished())this.add(p);
		}
	}
	
	/**
	 * Add a process to the Scheduler.
	 */
	public void add(Process process) {
		while (this.tree.search(process.executionTime()) != null && !process.finished())process.run(1);
		this.tree.insert(new TreeNode(null, null, null, process.executionTime(), NodeColor.RED, process));
	}
	
	public boolean finished() {
		return this.tree.root() == this.tree.nil();
	}
	
	// DO NOT MODIFY
	// used for the tests
	public RedBlackTree getTree() {
		return tree;
	}

}
