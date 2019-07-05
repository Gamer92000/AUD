package frame;

import java.io.ByteArrayInputStream;

/**
 * A stream implementation that lets you read the stream bit by bit. Reading full bytes is also possible.
 */
public class BitInputStream {
	
	private ByteArrayInputStream stream;
	private int buffer;
	private int numDigits;
	
	/**
	 * Initialize a BitInputStream with a ByteArrayInputStream
	 */
	public BitInputStream(ByteArrayInputStream stream) {
		this.stream = stream;
	}
	
	/**
	 * Read a bit. If the buffer is empty, -1 is returned.
	 */
	public int read() {
		if (numDigits == 0) {
			fetchNextByte();
		}
		if( buffer == -1 ) {
			return -1;
		}
		int result = buffer >> (numDigits-1);
		if( numDigits >= 2) {
			buffer = buffer % (2<<(numDigits-2));
		} else {
			buffer = 0;
		}
		numDigits -= 1;
		return result;
	}
	
	/**
	 * Read a byte.
	 */
	public byte readByte() {
		int b = 0;
		for (int i=7; i>=0; i--) {
			int bit = read();
			b += bit << i;
		}
		return (byte)(b-128);
	}
	
	/**
	 * Checks whether the buffer is empty.
	 */
	public boolean empty() {
		return (buffer == -1) || (numDigits == 0 && stream.available() == 0);
	}
	
	/**
	 * Close the stream.
	 */
	public void close() {
		stream = null;
	}
	
	private void fetchNextByte() {
		buffer = stream.read();
		numDigits = 8;
	}

}
