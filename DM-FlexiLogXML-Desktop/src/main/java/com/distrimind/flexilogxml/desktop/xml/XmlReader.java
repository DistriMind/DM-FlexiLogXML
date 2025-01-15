/*
DM-FlexiLogXML (package com.distrimind.flexilogxml)
Copyright (C) 2024 Jason Mahdjoub (author, creator and contributor) (Distrimind)
The project was created on January 11, 2025

jason.mahdjoub@distri-mind.fr


This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.distrimind.flexilogxml.desktop.xml;

import com.distrimind.flexilogxml.exceptions.XMLStreamException;
import com.distrimind.flexilogxml.xml.AbstractXmlReader;
import com.distrimind.flexilogxml.xml.Location;
import com.distrimind.flexilogxml.xml.QName;
import com.distrimind.flexilogxml.xml.XMLType;

import javax.xml.stream.XMLStreamReader;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public class XmlReader extends AbstractXmlReader {
	private final XMLStreamReader reader;

	XmlReader(XMLStreamReader reader) {
		this.reader = reader;
	}
	private XMLType get(int code) throws XMLStreamException {
		XMLType t = XMLType.fromCode(code);
		if (t==null)
			throw new XMLStreamException();
		return t;
	}

	@Override
	public XMLType nextImpl() throws XMLStreamException {
		try {
			return get(reader.next());
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public Object getProperty(String name) throws IllegalArgumentException {
		return reader.getProperty(name);
	}

	@Override
	public void require(XMLType type, String namespaceURI, String localName) throws XMLStreamException {
		try {
			reader.require(type.getCode(), namespaceURI, localName);

		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	protected String getElementTextImpl() throws XMLStreamException {
		try {
			return reader.getElementText();

		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public XMLType nextTag() throws XMLStreamException {
		try {
			return get(reader.nextTag());

		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public boolean hasNext() throws XMLStreamException {
		try {
			return reader.hasNext();
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void close() throws XMLStreamException {
		try {
			reader.close();
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public String getNamespaceURI(String prefix) {
		return reader.getNamespaceURI(prefix);
	}

	@Override
	public boolean isStartElement() {
		return reader.isStartElement();
	}

	@Override
	public boolean isEndElement() {
		return reader.isEndElement();
	}

	@Override
	public boolean isCharacters() {
		return reader.isCharacters();
	}

	@Override
	public boolean isWhiteSpace() {
		return reader.isWhiteSpace();
	}

	@Override
	public String getAttributeValue(String namespaceURI, String localName) {
		return reader.getAttributeValue(namespaceURI, localName);
	}

	@Override
	public int getAttributeCount() {
		return reader.getAttributeCount();
	}

	@Override
	public String getAttributeNamespace(int index) {
		return reader.getAttributeNamespace(index);
	}

	@Override
	public String getAttributeLocalName(int index) {
		return reader.getAttributeLocalName(index);
	}

	@Override
	public String getAttributePrefix(int index) {
		return reader.getAttributePrefix(index);
	}

	@Override
	public String getAttributeType(int index) {
		return reader.getAttributeType(index);
	}

	@Override
	public String getAttributeValue(int index) {
		return reader.getAttributeValue(index);
	}

	@Override
	public boolean isAttributeSpecified(int index) {
		return reader.isAttributeSpecified(index);
	}

	@Override
	public int getNamespaceCount() {
		return reader.getNamespaceCount();
	}

	@Override
	public String getNamespacePrefix(int index) {
		return reader.getNamespacePrefix(index);
	}

	@Override
	public String getNamespaceURI(int index) {
		return reader.getNamespaceURI(index);
	}

	@Override
	public XMLType getEventType() throws XMLStreamException {
		return get(reader.getEventType());
	}

	@Override
	public String getText() {
		return reader.getText();
	}

	@Override
	public char[] getTextCharacters() {
		return reader.getTextCharacters();
	}

	@Override
	public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
		try {
			return reader.getTextCharacters(sourceStart, target, targetStart, length);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public int getTextStart() {
		return reader.getTextStart();
	}

	@Override
	public int getTextLength() {
		return reader.getTextLength();
	}

	@Override
	public String getEncoding() {
		return reader.getEncoding();
	}

	@Override
	public boolean hasText() {
		return reader.hasText();
	}

	@Override
	public Location getLocation() {
		javax.xml.stream.Location l=reader.getLocation();
		return new Location(l.getLineNumber(), l.getColumnNumber(), l.getCharacterOffset(), l.getPublicId(), l.getSystemId());
	}

	@Override
	public QName getName() {
		javax.xml.namespace.QName q=reader.getName();
		return new QName(q.getNamespaceURI(), q.getLocalPart(), q.getPrefix());
	}

	@Override
	public String getLocalName() {
		return reader.getLocalName();
	}

	@Override
	public boolean hasName() {
		return reader.hasName();
	}

	@Override
	public String getNamespaceURI() {
		return reader.getNamespaceURI();
	}

	@Override
	public String getPrefix() {
		return reader.getPrefix();
	}

	@Override
	public String getVersion() {
		return reader.getVersion();
	}

	@Override
	public boolean isStandalone() {
		return reader.isStandalone();
	}

	@Override
	public boolean standaloneSet() {
		return reader.standaloneSet();
	}

	@Override
	public String getCharacterEncodingScheme() {
		return reader.getCharacterEncodingScheme();
	}

	@Override
	public String getPITarget() {
		return reader.getPITarget();
	}

	@Override
	public String getPIData() {
		return reader.getPIData();
	}
}
