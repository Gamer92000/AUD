package frame;

import java.io.ByteArrayOutputStream;

public class BitOutputStream {
	
	private ByteArrayOutputStream stream;
	private int buffer;
	private int numDigits;
	
	/**
	 * Initializes a BitOutputStream with a ByteArrayOutputStream. Remember to call close() after usage!
	 */
	public BitOutputStream(ByteArrayOutputStream stream) {
		this.stream = stream;
		buffer = 0;
		numDigits = 0;
	}
	
	/**
	 * Write a single bit to the stream.
	 */
	public void write(int b) {
		if (b<0 || b>1) {
			throw new RuntimeException("Not a bit!");
		}
		buffer += b << (7-numDigits);
		numDigits += 1;
		if( numDigits == 8 ) {
			flush();
		}
	}
	
	/**
	 * Write a byte to the stream.
	 */
	public void writeByte(byte b) {
		int ub = ((int) b)+128;
		for (int i=7; i>=0; i--) {
			write((ub >> i) % 2);
		}
	}
	
	/**
	 * Write the current bit-buffer to the underlying byte-stream (missing bits are filled with zeros).
	 */
	public void flush() {
		if( numDigits >0) {
			stream.write(buffer);
			buffer = 0;
			numDigits = 0;
		}
	}
	
	/**
	 * Close the stream. You must call this in the end, otherwise bits may not be written to the underlying stream!
	 */
	public void close() {
		flush();
		stream = null;
	}

}
