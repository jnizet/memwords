package com.googlecode.memwords.facade.cards;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.inject.Singleton;
import com.googlecode.memwords.facade.cards.parser.FavIconSaxParser;

/**
 * Implementation of {@link FavIconFinder}
 * @author JB
 */
@Singleton
public class FavIconFinderImpl implements FavIconFinder {

    @Override
    public String findFavIcon(InputSource source, URL baseUrl) throws FavIconException {
        FavIconSaxParser parser = new FavIconSaxParser();
        try {
            URI uri = baseUrl.toURI();
            String potentiallyRelativeUrl = parser.findFavIcon(source);
            if (potentiallyRelativeUrl == null) {
                return null;
            }
            return uri.resolve(potentiallyRelativeUrl).toString();
        }
        catch (URISyntaxException e) {
            throw new FavIconException(e);
        }
        catch (SAXException e) {
            throw new FavIconException(e);
        }
        catch (IOException e) {
            throw new FavIconException(e);
        }

    }

}
