package com.googlecode.memwords.facade.cards;

import java.net.URL;

import org.xml.sax.InputSource;

import com.google.inject.ImplementedBy;

/**
 * Service which allows finding a fav icon URL inside an HTML document
 * @author JB
 */
@ImplementedBy(FavIconFinderImpl.class)
public interface FavIconFinder {
    /**
     * Finds the fav icon URL inside the given HTML document
     * @param source the source of the HTML document
     * @param baseUrl the base URL used to resolve the relative URL of the fav icon
     * @return the absolute fav icon URL found, or <code>null</code> if not found
     * @throws FavIconException if an exception occurred while finding the fav icon
     */
    String findFavIcon(InputSource source, URL baseUrl) throws FavIconException;
}
