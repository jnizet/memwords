package com.googlecode.memwords.facade.cards.parser;

import java.io.IOException;

import org.apache.xerces.parsers.AbstractSAXParser;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.cyberneko.html.HTMLConfiguration;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Parser used to get the URL of the favIcon declared in an HTML file.
 * @author JB
 */
public class FavIconSaxParser extends AbstractSAXParser {

    private String iconUrl;

    /**
     * Constructor
     */
    public FavIconSaxParser() {
        super(new HTMLConfiguration());
    }

    public String findFavIcon(InputSource source) throws SAXException, IOException {
        try {
            parse(source);
        }
        catch (FavIconFoundException e) {
            // ignore : it's normal
        }
        return this.iconUrl;
    }

    /**
     * @throws FavIconFoundException as soon as the FavIcon is found
     */
    @Override
    public void startElement(QName name,
                             XMLAttributes attributes,
                             Augmentations augmentations) throws FavIconFoundException {
        if (name.localpart.equalsIgnoreCase("link")) {
            String relValue = attributes.getValue("rel");
            if ("shortcut icon".equals(relValue)) {
                String href = attributes.getValue("href");
                this.iconUrl = href;
                throw new FavIconFoundException();
            }
        }
    }
}
