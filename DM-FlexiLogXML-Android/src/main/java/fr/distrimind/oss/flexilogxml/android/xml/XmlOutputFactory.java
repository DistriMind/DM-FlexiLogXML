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

import android.util.Xml;
import fr.distrimind.oss.flexilogxml.ReflectionTools;
import fr.distrimind.oss.flexilogxml.exceptions.XMLStreamException;
import fr.distrimind.oss.flexilogxml.xml.IXmlWriter;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public class XmlOutputFactory extends fr.distrimind.oss.flexilogxml.xml.XmlOutputFactory {
	private static final Method newSerializer= ReflectionTools.getMethod(Xml.class, "newSerializer");
	@Override
	public IXmlWriter getXMLWriter(boolean enableIndent, OutputStreamWriter writer) throws XMLStreamException {
		try {
			XmlSerializer xmlSerializer = ReflectionTools.invoke(newSerializer, null);
			xmlSerializer.setOutput(writer);

			return new XmlWriter(enableIndent, xmlSerializer, writer);
		} catch (IOException e) {
			throw new XMLStreamException("Error initializing XmlSerializer", e);
		} catch (InvocationTargetException e) {
			throw new XMLStreamException("Error initializing XmlSerializer", e.getCause());
		}
	}
}
