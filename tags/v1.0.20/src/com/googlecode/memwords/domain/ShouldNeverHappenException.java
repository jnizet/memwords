package com.googlecode.memwords.domain;

/**
 * Runtime exception used to signal something that should never happen. It's
 * often used to transform a checked exception into a runtime one.
 * @author JB
 */
@SuppressWarnings("serial")
public class ShouldNeverHappenException extends RuntimeException {

    public ShouldNeverHappenException() {
    }

    public ShouldNeverHappenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShouldNeverHappenException(String message) {
        super(message);
    }

    public ShouldNeverHappenException(Throwable cause) {
        super(cause);
    }
}
