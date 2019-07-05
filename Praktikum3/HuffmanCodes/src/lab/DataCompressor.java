package lab;

/**
 * Aufgabe H1
 * 
 * Abgabe von: Philip Jonas Franz (2447302), Julian Imhof (2689225) und Nicolas Petermann (2918103)
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import frame.BitInputStream;
import frame.BitOutputStream;
import frame.EncodingException;

public class DataCompressor {
	
	/**
	 * Compress the inputData using HuffmanCodes and write the output to the outputStream.
	 */
	public void compress(byte [] inputData, ByteArrayOutputStream outputStream) {
		HuffmanCodes huffman = new HuffmanCodes();
		huffman.buildFrequencyTable(inputData);
		huffman.buildHuffmanTree();
		huffman.buildHuffmanTable();
		
		outputStream.write(65);
		outputStream.write(85);
		outputStream.write(68);
		
		BitOutputStream bOut = new BitOutputStream(outputStream);
		huffman.saveHuffmanTree(bOut);
		for (byte b : this.intToBytes(inputData.length))bOut.writeByte(b);
		huffman.compress(new ByteArrayInputStream(inputData), bOut);
	}
	
    private byte [] intToBytes(int i) {
        ByteBuffer b = ByteBuffer.allocate(4); 
        b.putInt(i); 
        return b.array();
    }
	
	/**
	 * Decompress the inputStream and write the result to the outputStream.
	 */
	public void decompress(ByteArrayInputStream inputStream, ByteArrayOutputStream outputStream) {
		BitInputStream bIn = new BitInputStream(inputStream);
		if (bIn.readByte() + 128 == 65 && bIn.readByte() + 128 == 85 && bIn.readByte() + 128 == 68) {
			HuffmanCodes huffman = new HuffmanCodes();
			huffman.loadHuffmanTree(bIn);
			huffman.decompress(bIn, outputStream, ByteBuffer.wrap(new byte [] {bIn.readByte(), bIn.readByte(), bIn.readByte(), bIn.readByte()}).getInt());
		}
		else throw new EncodingException("Fehler bei Dekomprimierung!");
	}
}
