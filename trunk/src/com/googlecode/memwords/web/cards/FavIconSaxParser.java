package com.googlecode.memwords.web.cards;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.xerces.parsers.AbstractSAXParser;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;
import org.cyberneko.html.HTMLConfiguration;

public class FavIconSaxParser extends AbstractSAXParser {
	
	private String iconUrl;
	private boolean found = false;
	
	private URL baseUrl;
	
	public FavIconSaxParser(URL baseUrl) {
	    super(new HTMLConfiguration());
	    this.baseUrl = baseUrl;
	}

	@Override
	public void startElement(QName name, XMLAttributes attributes, Augmentations augmentations)
			throws XNIException {
		if (!found) {
			if (name.localpart.equalsIgnoreCase("link")) {
				String relValue = attributes.getValue("rel");
				if ("shortcut icon".equals(relValue)) {
					found = true;
					try {
						String href = attributes.getValue("href");
						URI baseUri = baseUrl.toURI();
						URI uri = baseUri.resolve(href);
						this.iconUrl = uri.toString();
					}
					catch (URISyntaxException e) {
						// ignore
					}
				}
			}
		}
	}

	public String getIconUrl() {
		return iconUrl;
	}
}
