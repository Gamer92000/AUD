package frame;

/**
 * Throw this exception whenever the compressed data can't be decompressed.
 */
public class EncodingException extends RuntimeException {
	private static final long serialVersionUID = 2374271869006654638L;

	public EncodingException(String message) {
		super(message);
	}
}
