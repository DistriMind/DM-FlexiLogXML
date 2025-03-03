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

package fr.distrimind.oss.flexilogxml.common.xml;


import fr.distrimind.oss.flexilogxml.common.exceptions.XMLStreamException;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public interface IXmlReader extends IXmlParser{
	Object getProperty(java.lang.String name) throws java.lang.IllegalArgumentException;

	XMLType next() throws XMLStreamException;

	default void reachEndElement() throws XMLStreamException {
		int level=getCurrentLevel()-1;
		if (level>0) {
			while (hasNext())
			{
				next();
				if (getCurrentLevel()==level)
					return;
			}
			throw new XMLStreamException();
		}
	}

	void require(XMLType type, String namespaceURI, String localName) throws XMLStreamException;

	String getElementText() throws XMLStreamException;

	XMLType nextTag() throws XMLStreamException;

	boolean hasNext() throws XMLStreamException;

	void close() throws XMLStreamException;

	String getNamespaceURI(String prefix);

	boolean isStartElement();

	boolean isEndElement();

	boolean isCharacters();

	boolean isWhiteSpace();


	String getAttributeValue(String namespaceURI,
									String localName);
	default String getAttributeValue(String localName)
	{
		return getAttributeValue(null, localName);
	}


	int getAttributeCount();

	String getAttributeNamespace(int index);

	String getAttributeLocalName(int index);

	String getAttributePrefix(int index);

	String getAttributeType(int index);

	String getAttributeValue(int index);

	boolean isAttributeSpecified(int index);

	int getNamespaceCount() throws XMLStreamException;

	String getNamespacePrefix(int index) throws XMLStreamException;

	String getNamespaceURI(int index) throws XMLStreamException;

	XMLType getEventType() throws XMLStreamException;

    String getText();

	char[] getTextCharacters();

	int getTextCharacters(int sourceStart, char[] target, int targetStart, int length)
			throws XMLStreamException;

	int getTextStart();

	int getTextLength();

	String getEncoding();

	boolean hasText();

	Location getLocation();

	QName getName();

	String getLocalName();

	boolean hasName();

	String getNamespaceURI();

	String getPrefix();

	String getVersion();

	boolean isStandalone();

	boolean standaloneSet();

	String getCharacterEncodingScheme();

	String getPITarget();

	String getPIData();

	default void transferTo(IXmlWriter xmlWriter) throws XMLStreamException {
		final int level = getCurrentLevel();
		boolean writeEndDoc=xmlWriter.getCurrentLevel()==0;
		if (writeEndDoc)
			xmlWriter.writeStartDocument(getVersion());
		for (; ; ) {
			if (hasNext()) {
				XMLType event = next();
				if (getCurrentLevel() < level)
					break;
				switch (event) {
					case START_ELEMENT: {
						String prefix = getPrefix();
						String localName = getLocalName();
						String namespaceURI = getNamespaceURI();
						if (namespaceURI==null)
						{
							xmlWriter.writeStartElement(localName);
						}
						else
						{
							if (prefix==null)
								xmlWriter.writeStartElement(namespaceURI, localName);
							else
								xmlWriter.writeStartElement(prefix, localName, namespaceURI);
						}
						for (int i=0;i<getAttributeCount();i++)
						{
							prefix = getAttributePrefix(i);
							localName = getAttributeLocalName(i);
							namespaceURI = getAttributeNamespace(i);
							String value = getAttributeValue(i);
							if (namespaceURI == null) {
								xmlWriter.writeAttribute(localName, value);
							} else {
								if (prefix == null)
									xmlWriter.writeAttribute(namespaceURI, localName, value);
								else
									xmlWriter.writeAttribute(prefix, namespaceURI, localName,value);
							}
						}
					}
						break;
					case END_ELEMENT:
						xmlWriter.writeEndElement();
						break;
					case PROCESSING_INSTRUCTION:
						xmlWriter.writeProcessingInstruction(getPITarget());
						break;
					case CHARACTERS:
						xmlWriter.writeCharacters(getText().trim());
						break;
					case COMMENT:
						xmlWriter.writeComment(getText());
						break;
					case ATTRIBUTE: {
						String prefix = getPrefix();
						String localName = getLocalName();
						String namespaceURI = getNamespaceURI();
						String value = getElementText();
						if (namespaceURI == null) {
							xmlWriter.writeAttribute(localName, value);
						} else {
							if (prefix == null)
								xmlWriter.writeAttribute(namespaceURI, localName, value);
							else
								xmlWriter.writeAttribute(prefix, namespaceURI, localName,value);
						}
					}
						break;
					case NAMESPACE: {
						String prefix = getPrefix();
						String namespaceURI = getNamespaceURI();
						if (prefix==null)
							xmlWriter.writeDefaultNamespace(namespaceURI);
						else
							xmlWriter.writeNamespace(prefix, namespaceURI);
					}
						break;
					case SPACE:
					case DTD:
					case CDATA:
					case NOTATION_DECLARATION:
					case ENTITY_DECLARATION:
					case START_DOCUMENT:
					case END_DOCUMENT:
					default:
				}
			} else
				break;

		}
		if (writeEndDoc)
			xmlWriter.writeEndDocument();
	}

}
