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

package com.distrimind.flexilogxml.xml;

import com.distrimind.flexilogxml.exceptions.XMLStreamException;

import java.nio.charset.Charset;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public interface IXmlWriter extends IXmlParser {
	default void writeStartDocument(Charset encoding,
									String version) throws XMLStreamException {
		writeStartDocument(encoding.name(), version);
	}
	default void writeStartDocument(String version)
			throws XMLStreamException
	{
		writeStartDocument(Constants.DEFAULT_CHAR_SET, version);
	}
	default void writeSimpleElement(String elementName, String value) throws XMLStreamException {
		writeStartElement(elementName);
		writeCharacters(value);
		writeEndElement();
	}
	void writeStartDocument(String encoding,
								   String version) throws XMLStreamException;
	void writeStartElement(String localName) throws XMLStreamException;
	void writeStartElement(String namespaceURI, String localName) throws XMLStreamException;
	void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException;
	void writeEmptyElement(String namespaceURI, String localName)
			throws XMLStreamException;
	void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException;
	void writeEmptyElement(String localName)
			throws XMLStreamException;
	void writeEndElement()
			throws XMLStreamException;

	void writeEndDocument()
			throws XMLStreamException;

	void close()
			throws XMLStreamException;

	void flush()
			throws XMLStreamException;

	void writeAttribute(String localName, String value)
			throws XMLStreamException;

	void writeAttribute(String prefix,
							   String namespaceURI,
							   String localName,
							   String value)
			throws XMLStreamException;

	void writeAttribute(String namespaceURI,
							   String localName,
							   String value)
			throws XMLStreamException;


	void writeNamespace(String prefix, String namespaceURI)
			throws XMLStreamException;


	void writeDefaultNamespace(String namespaceURI)
			throws XMLStreamException;

	void writeComment(String data)
			throws XMLStreamException;

	void writeProcessingInstruction(String target)
			throws XMLStreamException;

	void writeProcessingInstruction(String target,
										   String data)
			throws XMLStreamException;

	void writeCData(String data)
			throws XMLStreamException;

	void writeDTD(String dtd)
			throws XMLStreamException;

	void writeEntityRef(String name)
			throws XMLStreamException;

	void writeCharacters(String text)
			throws XMLStreamException;

	void writeCharacters(char[] text, int start, int len)
			throws XMLStreamException;

	String getPrefix(String uri)
			throws XMLStreamException;

	void setPrefix(String prefix, String uri)
			throws XMLStreamException;

	void setDefaultNamespace(String uri)
			throws XMLStreamException;


	Object getProperty(java.lang.String name) throws IllegalArgumentException;
}
