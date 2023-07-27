package com.example.transport.exception;

public class TransportException extends RuntimeException {
	
	static final long serialVersionUID = -3387516993124229948L;

	public TransportException() {
		super();
	}
	
	public TransportException(String message) {
		super(message);
	}
	
	public TransportException(String message, Throwable cause) {
		super(message, cause);
	}
}
