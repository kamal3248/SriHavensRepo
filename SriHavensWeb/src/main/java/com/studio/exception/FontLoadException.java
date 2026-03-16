package com.studio.exception;

public class FontLoadException extends RuntimeException {
    
	private static final long serialVersionUID = 7199920262597704679L;

	public FontLoadException(String message) {
        super(message);
    }

    public FontLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
