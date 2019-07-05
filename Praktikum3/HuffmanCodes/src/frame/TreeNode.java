package frame;

public class TreeNode implements Comparable<TreeNode> {
	
	public TreeNode left;
	public TreeNode right;
	public TreeNode p;
	public double frequency;
	public byte value;
	
	public TreeNode(TreeNode left, TreeNode right, TreeNode p, double frequency, byte value) {
		this.left = left;
		this.right = right;
		this.p = p;
		this.frequency = frequency;
		this.value = value;
	}
	
	public TreeNode() {
		left = null;
		right = null;
		p = null;
		frequency = 0;
		value = 0;
	}

	@Override
	public int compareTo(TreeNode other) {
		return Double.valueOf(frequency).compareTo(other.frequency);
	}
}
