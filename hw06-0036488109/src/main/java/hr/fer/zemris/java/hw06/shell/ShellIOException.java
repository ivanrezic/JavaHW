package hr.fer.zemris.java.hw06.shell;

public class ShellIOException extends RuntimeException {


	private static final long serialVersionUID = -1491587332828227666L;

	public ShellIOException() {
	}
	
	public ShellIOException(String message){
		super(message);
	}

}
