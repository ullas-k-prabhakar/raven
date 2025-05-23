package io.github.ullas.k.prabhakar.openai.exception;

public class RavenException extends RuntimeException {

	private static final long serialVersionUID = 1627686036719446376L;

	public RavenException(String message) {
		super(message);
	}

	public RavenException(String message, Throwable cause) {
		super(message, cause);
	}
}
