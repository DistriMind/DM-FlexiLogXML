/*
DM-FlexiLogXML (package com.distrimind.flexilogxml)
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

package com.distrimind.flexilogxml.desktop.xml;

import com.distrimind.flexilogxml.exceptions.XMLStreamException;
import com.distrimind.flexilogxml.xml.AbstractXmlWriter;

import javax.xml.stream.XMLStreamWriter;


/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public class XmlWriter extends AbstractXmlWriter {
	private final XMLStreamWriter writer;

	XmlWriter(boolean indentEnabled, XMLStreamWriter writer)
	{
		super(indentEnabled);
		if (writer==null)
			throw new NullPointerException();
		this.writer=writer;
	}

	@Override
	protected void writeStartDocumentImpl(String encoding, String version, boolean standalone) throws XMLStreamException {
		try {
			if (standalone)
				writer.writeDTD("<?xml version='1.0' encoding='"+encoding+"' standalone='yes' ?>");
			else
				writer.writeStartDocument(encoding, version);

		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	protected void writeEndDocumentImpl() throws XMLStreamException {
		try {
			writer.writeEndDocument();
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	protected void writeStartElementImpl(String localName) throws XMLStreamException {
		try {
			writer.writeStartElement(localName);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	protected void writeStartElementImpl(String namespaceURI, String localName) throws XMLStreamException {
		try {
			writer.writeStartElement("", localName, namespaceURI);
			if (namespaceURI!=null)
				writeDefaultNamespace(namespaceURI);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	protected void writeStartElementImpl(String prefix, String localName, String namespaceURI) throws XMLStreamException {
		try {
			writer.writeStartElement(prefix, localName, namespaceURI);
			writeNamespacePriv(prefix, namespaceURI);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}
	private void writeNamespacePriv(String prefix, String namespaceURI) throws XMLStreamException {
		if (namespaceURI!=null) {
			if (prefix == null || prefix.isEmpty())
				writeDefaultNamespace(namespaceURI);
			else
				writeNamespace(prefix, namespaceURI);
		}
	}

	@Override
	protected void writeEmptyElementImpl(String namespaceURI, String localName) throws XMLStreamException {
		try {
			writer.writeEmptyElement("", localName, namespaceURI);
			if (namespaceURI!=null)
				writeDefaultNamespace(namespaceURI);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	protected void writeEmptyElementImpl(String prefix, String localName, String namespaceURI) throws XMLStreamException {
		try {
			writer.writeEmptyElement("", localName, namespaceURI);
			writeNamespacePriv(prefix, namespaceURI);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	protected void writeEmptyElementImpl(String localName) throws XMLStreamException {
		try {
			writer.writeEmptyElement(localName);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	protected void writeEndElementImpl() throws XMLStreamException {
		try {
			writer.writeEndElement();
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void close() throws XMLStreamException {
		try {
			writer.close();
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void flush() throws XMLStreamException {
		try {
			writer.flush();
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void writeAttribute(String localName, String value) throws XMLStreamException {
		try {
			writer.writeAttribute(localName, value);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
		try {
			writer.writeAttribute(prefix, namespaceURI, localName, value);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
		try {
			writer.writeAttribute(namespaceURI, localName, value);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
		try {
			writer.writeNamespace(prefix, namespaceURI);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
		try {
			writer.writeDefaultNamespace(namespaceURI);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void writeComment(String data) throws XMLStreamException {
		try {
			writer.writeComment(data);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void writeProcessingInstruction(String target) throws XMLStreamException {
		try {
			writer.writeProcessingInstruction(target);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
		try {
			writer.writeProcessingInstruction(target, data);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void writeCData(String data) throws XMLStreamException {
		try {
			writer.writeCData(data);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void writeDTD(String dtd) throws XMLStreamException {
		try {
			writer.writeDTD(dtd);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void writeEntityRef(String name) throws XMLStreamException {
		try {
			writer.writeEntityRef(name);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void writeCharacters(String text) throws XMLStreamException {
		try {
			writer.writeCharacters(text);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
		try {
			writer.writeCharacters(text, start, len);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public String getPrefix(String uri) throws XMLStreamException {
		try {
			return writer.getPrefix(uri);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void setPrefix(String prefix, String uri) throws XMLStreamException {
		try {
			writer.setPrefix(prefix, uri);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public void setDefaultNamespace(String uri) throws XMLStreamException {
		try {
			writer.setDefaultNamespace(uri);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}

	@Override
	public Object getProperty(String name) throws IllegalArgumentException {
		return writer.getProperty(name);
	}
}
