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
 * SAX parser used to get the URL of the favIcon declared in an HTML file.
 * @author JB
 */
public class FavIconSaxParser extends AbstractSAXParser {

    /**
     * The found icon URL, if any (<code>null</code> if not found)
     */
    private String iconUrl;

    /**
     * Constructor
     */
    public FavIconSaxParser() {
        super(new HTMLConfiguration());
    }

    /**
     * Finds the fav icon by parsing the given source
     * @param source the source to parse
     * @return the found fav icon URL, or <code>null</code> if not found. The
     * URL is the one found in the source, as is, and may thus be relative to the source
     * URL
     * @throws SAXException if a SAX exception occurs during the parsing
     * @throws IOException if an IO exception occurs during the parsing
     */
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
     * Parses an element of the input source, and initializes the fav icon URL if the
     * element is a &lt;link rel="shortcut icon" href="..."&gt; element.
     * @throws FavIconFoundException as soon as the fav icon is found, in order to stop the parsing
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
