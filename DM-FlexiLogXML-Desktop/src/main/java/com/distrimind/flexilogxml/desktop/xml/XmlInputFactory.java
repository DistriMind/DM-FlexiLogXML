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
import com.distrimind.flexilogxml.xml.IXmlReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.Reader;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public class XmlInputFactory extends com.distrimind.flexilogxml.xml.XmlInputFactory {
	@Override
	public IXmlReader getXMLReader(Reader input) throws XMLStreamException {
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, nameSpaceAware);
			factory.setProperty(XMLInputFactory.IS_VALIDATING, validating);
			factory.setProperty(XMLInputFactory.IS_COALESCING, coalescing);
			factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, replacingEntityReferences);
			factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, supportingExternalEntities);
			factory.setProperty(XMLInputFactory.SUPPORT_DTD, supportDTD);
			XMLStreamReader reader = factory.createXMLStreamReader(input);
			return new XmlReader(reader);
		} catch (javax.xml.stream.XMLStreamException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}
	}
}
