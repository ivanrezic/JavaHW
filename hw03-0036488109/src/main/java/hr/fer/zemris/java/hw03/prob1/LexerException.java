package hr.fer.zemris.java.hw03.prob1;

public class LexerException extends RuntimeException {
	
	private static final long serialVersionUID = -1173741942168406063L;

	public LexerException() {
		super();
	}

	public LexerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

	public LexerException(String message) {
		super(message);
	}

	public LexerException(Throwable cause) {
		super(cause);
	}
	
}
