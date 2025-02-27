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

package fr.distrimind.oss.flexilogxml.xml;

import fr.distrimind.oss.flexilogxml.exceptions.XMLStreamException;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public abstract class AbstractXmlWriter extends AbstractXmlParser implements IXmlWriter {
	private final boolean indentEnabled;
	private int depthBack;

	public AbstractXmlWriter(boolean indentEnabled) {
		this.indentEnabled = indentEnabled;
	}


	private String getIndentation() {
		return "\t".repeat(getCurrentLevel());
	}
	private void writeStartIndent() throws XMLStreamException {
		if (indentEnabled) {
			depthBack=0;
			writeCharacters("\n");
			writeCharacters(getIndentation());
		}
	}

	private void writeEndIndent() throws XMLStreamException {
		if (indentEnabled) {
			if (depthBack>0) {
				writeCharacters("\n");
				writeCharacters(getIndentation());
			}
			++depthBack;
		}
	}

	public boolean isIndentEnabled() {
		return indentEnabled;
	}

	@Override
	public void writeStartDocument(String encoding, String version, boolean standalone) throws XMLStreamException {
		if (getCurrentLevel()>0)
			writeStartIndent();
		incrementDocumentLevel();
		writeStartDocumentImpl(encoding, version, standalone);
	}
	protected abstract void writeStartDocumentImpl(String encoding, String version, boolean standalone) throws XMLStreamException;

	@Override
	public void writeEndDocument() throws XMLStreamException {
		decrementDocumentLevel();
		writeEndIndent();
		writeEndDocumentImpl();

	}
	protected abstract void writeEndDocumentImpl() throws XMLStreamException;


	@Override
	public void writeStartElement(String localName) throws XMLStreamException {
		writeStartIndent();
		incrementLevel();
		writeStartElementImpl(localName);
	}

	@Override
	public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
		writeStartIndent();
		incrementLevel();
		writeStartElementImpl(getNamespaceURI(namespaceURI), localName);
	}

	@Override
	public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
		writeStartIndent();
		incrementLevel();
		writeStartElementImpl(prefix, localName, getNamespaceURI(namespaceURI));
	}

	@Override
	public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
		writeStartIndent();
		writeEmptyElementImpl(getNamespaceURI(namespaceURI), localName);
		writeEndIndent();
	}
	private String getNamespaceURI(String namespaceURI)
	{
		return namespaceURI.trim().isEmpty()?null:namespaceURI;
	}
	@Override
	public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
		writeStartIndent();
		writeEmptyElementImpl(prefix, localName, getNamespaceURI(namespaceURI));
		writeEndIndent();
	}

	@Override
	public void writeEmptyElement(String localName) throws XMLStreamException {
		writeStartIndent();
		writeEmptyElementImpl(localName);
		writeEndIndent();
	}

	@Override
	public void writeEndElement() throws XMLStreamException {
		decrementLevel();
		writeEndIndent();
		writeEndElementImpl();
	}


	protected abstract void writeStartElementImpl(String localName) throws XMLStreamException;

	protected abstract void writeStartElementImpl(String namespaceURI, String localName) throws XMLStreamException;

	protected abstract void writeStartElementImpl(String prefix, String localName, String namespaceURI) throws XMLStreamException;

	protected abstract void writeEmptyElementImpl(String namespaceURI, String localName) throws XMLStreamException;
	protected abstract void writeEmptyElementImpl(String prefix, String localName, String namespaceURI) throws XMLStreamException;


	protected abstract void writeEmptyElementImpl(String localName) throws XMLStreamException;

	protected abstract void writeEndElementImpl() throws XMLStreamException;

}
