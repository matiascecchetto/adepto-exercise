package au.com.adepto.demo.exception;

public class ResourceAlreadyExistException extends Exception {

	private static final long serialVersionUID = 9113728189244999621L;

	public ResourceAlreadyExistException() {
	}

	public ResourceAlreadyExistException(String message) {
		super(message);
	}

	public ResourceAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceAlreadyExistException(Throwable cause) {
		super(cause);
	}
}