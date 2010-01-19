package com.googlecode.memwords.facade.cards;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.xerces.parsers.AbstractSAXParser;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;
import org.cyberneko.html.HTMLConfiguration;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Parser used to get the URL of the favIcon declared in an HTML file.
 * @author JB
 */
public class FavIconSaxParser extends AbstractSAXParser {

    private String iconUrl;

    private URL baseUrl;

    public FavIconSaxParser(URL baseUrl) {
        super(new HTMLConfiguration());
        this.baseUrl = baseUrl;
    }

    public String findFavIcon(InputSource source) throws IOException {
        try {
            parse(source);
            return this.iconUrl;
        }
        catch (SAXException e) {
            // ignore
            return null;
        }
    }

    @Override
    public void startElement(QName name,
                             XMLAttributes attributes,
                             Augmentations augmentations) throws XNIException {
        if (name.localpart.equalsIgnoreCase("link")) {
            String relValue = attributes.getValue("rel");
            if ("shortcut icon".equals(relValue)) {
                try {
                    String href = attributes.getValue("href");
                    URI baseUri = baseUrl.toURI();
                    URI uri = baseUri.resolve(href);
                    this.iconUrl = uri.toString();
                }
                catch (URISyntaxException e) {
                    // ignore
                }
                throw new XNIException("icon URL found");
            }
        }
    }
}
