package com.jjpeople.dao.exceptions;

@SuppressWarnings("serial")
public class ExistsException extends Exception{
	ExistsException(String message) {
        super(message);
    }

    public ExistsException(Throwable cause) {
        super(cause);
    }

    public ExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistsException() {
        super();
    }
}
