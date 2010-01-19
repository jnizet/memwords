package com.googlecode.memwords.facade.cards;

/**
 * Exception used to signal a problem while fetching the favIcon of a web site
 * @author JB
 */
@SuppressWarnings("serial")
public class FavIconException extends Exception {

    public FavIconException() {
    }

    public FavIconException(String message, Throwable cause) {
        super(message, cause);
    }

    public FavIconException(String message) {
        super(message);
    }

    public FavIconException(Throwable cause) {
        super(cause);
    }
}
