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

import android.util.Xml;

import com.distrimind.flexilogxml.ReflectionTools;
import com.distrimind.flexilogxml.exceptions.XMLStreamException;
import com.distrimind.flexilogxml.xml.IXmlReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public final class XmlInputFactory extends com.distrimind.flexilogxml.xml.XmlInputFactory {
	private static final Method newPullParser= ReflectionTools.getMethod(Xml.class, "newPullParser");

	public XmlInputFactory() {
		super.setSupportingExternalEntities(false);
		super.setSupportDTD(false);
	}

	@Override
	public void setSupportingExternalEntities(boolean supportingExternalEntities) {
		if (supportingExternalEntities)
			throw new IllegalArgumentException("Not supported option");
		super.setSupportingExternalEntities(false);
	}

	@Override
	public void setSupportDTD(boolean supportDTD) {
		if (supportDTD)
			throw new IllegalArgumentException("Not supported option");
		super.setSupportDTD(false);
	}

	@Override
	public IXmlReader getXMLReader(Reader reader) throws XMLStreamException {
		try {

			XmlPullParser parser = ReflectionTools.invoke(newPullParser, null);
			parser.setInput(reader);
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, nameSpaceAware);


			return new XmlReader(parser);
		} catch (InvocationTargetException e) {
			throw new XMLStreamException("Error creating XML parser", e.getCause());
		} catch (XmlPullParserException e) {
			throw new XMLStreamException("Error creating XML parser", e);
		}
	}
}
