/*
DM-FlexiLogXML (package fr.distrimind.oss.flexilogxml)
Copyright (C) 2024 Jason Mahdjoub (author, creator and contributor) (Distrimind)
The project was created on January 11, 2025

jason.mahdjoub@distri-mind.fr


This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License only.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package fr.distrimind.oss.flexilogxml.android.xml;

import fr.distrimind.oss.flexilogxml.common.exceptions.XMLStreamException;
import fr.distrimind.oss.flexilogxml.common.xml.AbstractXmlReader;
import fr.distrimind.oss.flexilogxml.common.xml.Location;
import fr.distrimind.oss.flexilogxml.common.xml.QName;
import fr.distrimind.oss.flexilogxml.common.xml.XMLType;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public class XmlReader extends AbstractXmlReader {
	private final XmlPullParser parser;

	XmlReader(XmlPullParser parser) {
		this.parser = parser;
	}

	private XMLType get(int eventType) throws XMLStreamException {
		switch (eventType)
		{
			case XmlPullParser.START_DOCUMENT:
				return XMLType.START_DOCUMENT;
			case XmlPullParser.START_TAG:
				return XMLType.START_ELEMENT;
			case XmlPullParser.END_DOCUMENT:
				return XMLType.END_DOCUMENT;
			case XmlPullParser.END_TAG:
				return XMLType.END_ELEMENT;
			case XmlPullParser.COMMENT:
				return XMLType.COMMENT;
			case XmlPullParser.TEXT:
				return XMLType.CHARACTERS;
			case XmlPullParser.IGNORABLE_WHITESPACE:
				return XMLType.SPACE;
			case XmlPullParser.CDSECT:
			case XmlPullParser.DOCDECL:
				return XMLType.CDATA;
			default:
				throw new XMLStreamException("Unknown event type: " + eventType);
		}

	}
	private int get(XMLType eventType) throws XMLStreamException {
		switch (eventType)
		{
			case START_DOCUMENT:
				return XmlPullParser.START_DOCUMENT;
			case START_ELEMENT:
				return XmlPullParser.START_TAG;
			case END_DOCUMENT:
				return XmlPullParser.END_DOCUMENT;
			case END_ELEMENT:
				return XmlPullParser.END_TAG;
			case COMMENT:
				return XmlPullParser.COMMENT;
			case CHARACTERS:
				return XmlPullParser.TEXT;
			case SPACE:
				return XmlPullParser.IGNORABLE_WHITESPACE;
			case CDATA:
				return XmlPullParser.CDSECT;
			default:
				throw new XMLStreamException("Unknown event type: " + eventType);
		}

	}

	@Override
	public XMLType nextImpl() throws XMLStreamException {
		try {
			return get(parser.next());
		} catch (XmlPullParserException | IOException e) {
			throw new XMLStreamException("Error while parsing XML", e);
		}
	}

	@Override
	public Object getProperty(String name) {
		return parser.getProperty(name);
	}

	@Override
	public void require(XMLType type, String namespaceURI, String localName) throws XMLStreamException {
		try {
			parser.require(get(type), namespaceURI, localName);
		} catch (XmlPullParserException | IOException e) {
			throw new XMLStreamException("Error while requiring specific element", e);
		}
	}

	@Override
	protected String getElementTextImpl() throws XMLStreamException {
		try {
			return parser.nextText();
		} catch (XmlPullParserException | IOException e) {
			throw new XMLStreamException("Error while getting element text", e);
		}
	}

	@Override
	public XMLType nextTag() throws XMLStreamException {
		try {
			int eventType;
			do {
				eventType = parser.next();
			} while (eventType != XmlPullParser.START_TAG && eventType != XmlPullParser.END_TAG && hasNext());
			return get(eventType);
		} catch (XmlPullParserException | IOException e) {
			throw new XMLStreamException("Error while moving to next tag", e);
		}
	}

	@Override
	public boolean hasNext() throws XMLStreamException {
		try {
			return parser.getEventType() != XmlPullParser.END_DOCUMENT;
		} catch (XmlPullParserException e) {
			throw new XMLStreamException("Error while checking next element", e);
		}
	}

	@Override
	public void close() {

	}

	@Override
	public String getNamespaceURI(String prefix) {
		return parser.getNamespace(prefix);
	}

	@Override
	public boolean isStartElement() {
		try {
			return parser.getEventType() == XmlPullParser.START_TAG;
		} catch (XmlPullParserException e) {
			return false;
		}
	}

	@Override
	public boolean isEndElement() {
		try {
			return parser.getEventType() == XmlPullParser.END_TAG;
		} catch (XmlPullParserException e) {
			return false;
		}
	}

	@Override
	public boolean isCharacters() {
		try {
			int eventType = parser.getEventType();
			return eventType == XmlPullParser.TEXT;
		} catch (XmlPullParserException e) {
			return false;
		}
	}

	@Override
	public boolean isWhiteSpace() {
		try {
			return parser.isWhitespace();
		} catch (XmlPullParserException e) {
			return false;
		}
	}

	@Override
	public String getAttributeValue(String namespaceURI, String localName) {
		return parser.getAttributeValue(namespaceURI, localName);
	}

	@Override
	public int getAttributeCount() {
		return parser.getAttributeCount();
	}

	@Override
	public String getAttributeNamespace(int index) {
		return parser.getAttributeNamespace(index);
	}

	@Override
	public String getAttributeLocalName(int index) {
		return parser.getAttributeName(index);
	}

	@Override
	public String getAttributePrefix(int index) {
		return parser.getAttributePrefix(index);
	}

	@Override
	public String getAttributeType(int index) {
		return "CDATA";
	}

	@Override
	public String getAttributeValue(int index) {
		return parser.getAttributeValue(index);
	}

	@Override
	public boolean isAttributeSpecified(int index) {
		return false;
	}

	@Override
	public int getNamespaceCount() throws XMLStreamException {
		try {
			return parser.getNamespaceCount(parser.getDepth());
		} catch (XmlPullParserException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public String getNamespacePrefix(int index) throws XMLStreamException {
		try {
			return parser.getNamespacePrefix(index);
		} catch (XmlPullParserException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public String getNamespaceURI(int index) throws XMLStreamException {
		try {
			return parser.getNamespaceUri(index);
		} catch (XmlPullParserException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public XMLType getEventType() throws XMLStreamException {
		try {
			return get(parser.getEventType());
		} catch (XmlPullParserException e) {
			throw new XMLStreamException("Error while getting event type", e);
		}
	}

	@Override
	public String getText() {
		return parser.getText();
	}

	@Override
	public char[] getTextCharacters() {
		String text = parser.getText();
		return text != null ? text.toCharArray() : new char[0];
	}

	@Override
	public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) {
		char[] textChars = getTextCharacters();
		int copyLength = Math.min(length, textChars.length - sourceStart);
		System.arraycopy(textChars, sourceStart, target, targetStart, copyLength);
		return copyLength;
	}

	@Override
	public int getTextStart() {
		return 0;
	}

	@Override
	public int getTextLength() {
		String text = parser.getText();
		return text != null ? text.length() : 0;
	}

	@Override
	public String getEncoding() {
		return StandardCharsets.UTF_8.name();
	}

	@Override
	public boolean hasText() {
		try {
			return parser.getEventType() == XmlPullParser.TEXT;
		} catch (XmlPullParserException e) {
			return false;
		}
	}

	@Override
	public Location getLocation() {
		return new Location(parser.getLineNumber(), parser.getColumnNumber(), -1, null, null);
	}

	@Override
	public QName getName() {
		return new QName(parser.getNamespace(), parser.getName(), parser.getPrefix());
	}

	@Override
	public String getLocalName() {
		return parser.getName();
	}

	@Override
	public boolean hasName() {
		return parser.getName() != null;
	}

	@Override
	public String getNamespaceURI() {
		return parser.getNamespace();
	}

	@Override
	public String getPrefix() {
		return parser.getPrefix();
	}

	@Override
	public String getVersion() {
		return "";
	}

	@Override
	public boolean isStandalone() {
		return false;
	}

	@Override
	public boolean standaloneSet() {
		return false;
	}

	@Override
	public String getCharacterEncodingScheme() {
		return null;
	}

	@Override
	public String getPITarget() {
		return null;
	}

	@Override
	public String getPIData() {
		return null;
	}
}
