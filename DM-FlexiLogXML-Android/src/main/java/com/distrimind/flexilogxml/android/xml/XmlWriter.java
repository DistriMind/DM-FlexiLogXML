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

package com.distrimind.flexilogxml.android.xml;

import com.distrimind.flexilogxml.exceptions.XMLStreamException;
import com.distrimind.flexilogxml.xml.AbstractXmlWriter;
import com.distrimind.flexilogxml.xml.Constants;
import com.distrimind.flexilogxml.xml.QName;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public class XmlWriter extends AbstractXmlWriter {
	private final XmlSerializer serializer;
	private final OutputStreamWriter output;
	private final List<QName> startedTags=new ArrayList<>();

	XmlWriter(boolean indentEnabled, XmlSerializer serializer, OutputStreamWriter output) {
		super(indentEnabled);
		if (serializer == null) {
			throw new NullPointerException("Serializer cannot be null");
		}
		this.serializer = serializer;
		this.output=output;
	}

	@Override
	protected void writeStartDocumentImpl(String encoding, String version, boolean standalone) throws XMLStreamException {
		try {
			serializer.startDocument(encoding, standalone);
		} catch (IOException e) {
			throw new XMLStreamException("Error starting document", e);
		}
	}

	@Override
	protected void writeEndDocumentImpl() throws XMLStreamException {
		try {
			serializer.endDocument();
		} catch (IOException e) {
			throw new XMLStreamException("Error ending document", e);
		}
	}

	@Override
	protected void writeStartElementImpl(String localName) throws XMLStreamException {
		writeStartElementImpl(null, localName);
	}

	@Override
	protected void writeStartElementImpl(String namespaceURI, String localName) throws XMLStreamException {
		if (namespaceURI!=null)
			writeDefaultNamespace(namespaceURI);
		writeStartElementImpl(Constants.DEFAULT_NS_PREFIX, localName, namespaceURI);

	}

	@Override
	protected void writeStartElementImpl(String prefix, String localName, String namespaceURI) throws XMLStreamException {
		try {
			if (prefix!=null && !Constants.DEFAULT_NS_PREFIX.equals(prefix)) {
				serializer.setPrefix(prefix, namespaceURI);
			}

			serializer.startTag(namespaceURI, localName);
			startedTags.add(new QName(namespaceURI, localName, prefix));
		} catch (IOException e) {
			throw new XMLStreamException("Error starting element with prefix", e);
		}
	}

	@Override
	protected void writeEmptyElementImpl(String namespaceURI, String localName) throws XMLStreamException {
		try {
			serializer.startTag(namespaceURI, localName);
			if (namespaceURI!=null)
				writeDefaultNamespace(namespaceURI);
			serializer.endTag(namespaceURI, localName);
		} catch (IOException e) {
			throw new XMLStreamException("Error writing empty element", e);
		}
	}
	@Override
	protected void writeEmptyElementImpl(String prefix, String localName, String namespaceURI) throws XMLStreamException {
		try {
			if (prefix!=null && !Constants.DEFAULT_NS_PREFIX.equals(prefix)) {
				serializer.setPrefix(prefix, namespaceURI);
			}
			serializer.startTag(namespaceURI, localName);
			if (namespaceURI!=null)
				writeDefaultNamespace(namespaceURI);
			serializer.endTag(namespaceURI, localName);
		} catch (IOException e) {
			throw new XMLStreamException("Error writing empty element", e);
		}
	}

	@Override
	protected void writeEmptyElementImpl(String localName) throws XMLStreamException {
		try {
			serializer.startTag(null, localName);
			serializer.endTag(null, localName);
		} catch (IOException e) {
			throw new XMLStreamException("Error writing empty element", e);
		}
	}

	@Override
	protected void writeEndElementImpl() throws XMLStreamException {
		if (startedTags.isEmpty())
			throw new XMLStreamException("Error ending element");
		try {
			QName q=startedTags.remove(startedTags.size()-1);
			String ns=q.getNamespaceURI();
			serializer.endTag(ns, q.getLocalPart());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() {

	}

	@Override
	public void flush() throws XMLStreamException {

		try {
			output.flush();
		} catch (Exception e) {
			throw new XMLStreamException("Error flushing writer", e);
		}
	}

	@Override
	public void writeAttribute(String localName, String value) throws XMLStreamException {
		try {
			serializer.attribute(null, localName, value);
		} catch (IOException e) {
			throw new XMLStreamException("Error writing attribute", e);
		}
	}

	@Override
	public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
		try {
			//serializer.setPrefix(prefix, namespaceURI);
			serializer.attribute(namespaceURI, localName, value);
		} catch (IOException e) {
			throw new XMLStreamException("Error writing attribute with prefix", e);
		}
	}

	@Override
	public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
		try {
			serializer.attribute(namespaceURI, localName, value);
		} catch (IOException e) {
			throw new XMLStreamException("Error writing attribute with namespace", e);
		}
	}

	@Override
	public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
		try {
			serializer.setPrefix(prefix, namespaceURI);
		} catch (IOException e) {
			throw new XMLStreamException("Error writing namespace", e);
		}
	}

	@Override
	public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
		try {
			serializer.setPrefix("", namespaceURI);
		} catch (IOException e) {
			throw new XMLStreamException("Error writing default namespace", e);
		}
	}

	@Override
	public void writeComment(String data) throws XMLStreamException {
		throw new UnsupportedOperationException("Comments are not supported by XmlSerializer");
	}

	@Override
	public void writeProcessingInstruction(String target) throws XMLStreamException {
		throw new UnsupportedOperationException("Processing instructions are not supported by XmlSerializer");
	}

	@Override
	public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
		throw new UnsupportedOperationException("Processing instructions are not supported by XmlSerializer");
	}

	@Override
	public void writeCData(String data) throws XMLStreamException {
		try {
			serializer.cdsect(data);
		} catch (IOException e) {
			throw new XMLStreamException("Error writing CDATA", e);
		}
	}

	@Override
	public void writeDTD(String dtd) throws XMLStreamException {
		throw new UnsupportedOperationException("DTD writing is not supported by XmlSerializer");
	}

	@Override
	public void writeEntityRef(String name) throws XMLStreamException {
		throw new UnsupportedOperationException("Entity references are not supported by XmlSerializer");
	}

	@Override
	public void writeCharacters(String text) throws XMLStreamException {
		try {
			serializer.text(text);
		} catch (IOException e) {
			throw new XMLStreamException("Error writing characters", e);
		}
	}

	@Override
	public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
		try {
			serializer.text(new String(text, start, len));
		} catch (IOException e) {
			throw new XMLStreamException("Error writing characters", e);
		}
	}

	@Override
	public String getPrefix(String uri) throws XMLStreamException {
		throw new UnsupportedOperationException("Getting prefix is not supported by XmlSerializer");
	}

	@Override
	public void setPrefix(String prefix, String uri) throws XMLStreamException {
		try {
			serializer.setPrefix(prefix, uri);
		} catch (IOException e) {
			throw new XMLStreamException("Error setting prefix", e);
		}
	}

	@Override
	public void setDefaultNamespace(String uri) throws XMLStreamException {
		try {
			serializer.setPrefix("", uri);
		} catch (IOException e) {
			throw new XMLStreamException("Error setting default namespace", e);
		}
	}

	@Override
	public Object getProperty(String name) {
		throw new UnsupportedOperationException("Properties are not supported by XmlSerializer");
	}
}