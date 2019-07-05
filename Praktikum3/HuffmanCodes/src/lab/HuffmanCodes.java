package lab;

/**
 * Aufgabe H1
 * 
 * Abgabe von: Philip Jonas Franz (2447302), Julian Imhof (2689225) und Nicolas Petermann (2918103)
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.PriorityQueue;

import frame.BitInputStream;
import frame.BitOutputStream;
import frame.TreeNode;

public class HuffmanCodes {

	private int c = 0;
	
	private double [] frequencyTable = new double [256];
	private ArrayList<Integer>[] codeTable = new ArrayList [256];
	private TreeNode huffmanTreeRoot, huffmanTreeNil, l0, l1, n, n0;
	
	public HuffmanCodes() {
		huffmanTreeNil = new TreeNode();
		huffmanTreeRoot = huffmanTreeNil;
	}
	
	public double [] getFrequencyTable() {
		return frequencyTable;
	}
	
	public ArrayList<Integer> [] getHuffmanTable() {
		return codeTable;
	}
	
	public void buildFrequencyTable(byte [] data) {
		for (byte b : data)this.frequencyTable [b + 128] += 1.0 / data.length;
	}
	
	public void buildHuffmanTree() {
		PriorityQueue<TreeNode> queue = new PriorityQueue();
		for (int i = 0; i < 256; i++)queue.add(new TreeNode(this.huffmanTreeNil, this.huffmanTreeNil, this.huffmanTreeNil, this.frequencyTable [i], (byte) i));
		while (queue.size() > 1)queue.add(new TreeNode(l0 = queue.remove(), l1 = queue.remove(), this.huffmanTreeNil, l0.frequency + l1.frequency, (byte) 0));
		this.huffmanTreeRoot = queue.remove();
	}
	
	public void buildHuffmanTable() {
		this.buildHuffmanRec(huffmanTreeRoot.left, new ArrayList(), 0);
		this.buildHuffmanRec(huffmanTreeRoot.right, new ArrayList(), 1);
	}
	
	private void buildHuffmanRec(TreeNode node, ArrayList<Integer> l, int add) {
		l = (ArrayList) l.clone();
		l.add(add);
		if (this.isLeaf(node))this.codeTable [node.value + 128] = (ArrayList) l.clone();
		else {
			this.buildHuffmanRec(node.left, l, 0);
			this.buildHuffmanRec(node.right, l, 1);
		}
	}
	
	public void compress(ByteArrayInputStream inputStream, BitOutputStream outputStream) {
		while ((c = inputStream.read()) != -1)for (int out : this.codeTable [c])outputStream.write(out);
		outputStream.writeByte((byte) 0);
	}
	
	public void decompress(BitInputStream inputStream, ByteArrayOutputStream outputStream, int bytesToRead) {
		for (int i = 0; i < bytesToRead; i++) {
			while (!this.isLeaf(n))n = (n == null)? this.huffmanTreeRoot : (inputStream.read() == 0)? n.left : n.right;
			outputStream.write(n.value + 128);
			n = this.huffmanTreeRoot;
		}
	}
	
	public void saveHuffmanTree(BitOutputStream stream) {
		this.saveReq(stream, this.huffmanTreeRoot);
	}
	
	private void saveReq(BitOutputStream stream, TreeNode n) {
		if (this.isLeaf(n)) {
			stream.write(1);
			stream.writeByte(n.value);
		}
		else {
			stream.write(0);
			this.saveReq(stream, n.left);
			this.saveReq(stream, n.right);
		}
	}
	
	public void loadHuffmanTree(BitInputStream stream) {
		while (c < 256) {
			while (n0 != null && !n0.right.equals(this.huffmanTreeNil))n0 = n0.p;
			if (stream.read() == 0) {
				TreeNode n = new TreeNode(this.huffmanTreeNil, this.huffmanTreeNil, n0, 0, (byte) 0);
				if (n0 == null) {
					n0 = n;
					this.huffmanTreeRoot = n0;
				}
				else {
					if (n0.left.equals(this.huffmanTreeNil))n0.left = n;
					else n0.right = n;
					n0 = n;
				}
			}
			else {
				TreeNode n = new TreeNode(this.huffmanTreeNil, this.huffmanTreeNil, n0, 0, stream.readByte());
				if (n0.left.equals(this.huffmanTreeNil))n0.left = n;
				else n0.right = n;
				c++;
			}
		}
	}
	
	public boolean isLeaf(TreeNode node) {
		return node != null && node.left.equals(this.huffmanTreeNil) && node.right.equals(this.huffmanTreeNil);
	}
	
}
