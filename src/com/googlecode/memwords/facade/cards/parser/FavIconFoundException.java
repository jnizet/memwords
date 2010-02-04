package com.googlecode.memwords.facade.cards.parser;

/**
 * Runtime exception throws as soon as the favIcon is found by the parser, in order
 * to stop the parsing
 * @author JB
 */
@SuppressWarnings("serial")
public class FavIconFoundException extends RuntimeException {

    /**
     * Constructor
     */
    public FavIconFoundException() {
    }
}