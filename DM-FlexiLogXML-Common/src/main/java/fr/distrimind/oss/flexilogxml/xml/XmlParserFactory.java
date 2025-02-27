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

import fr.distrimind.oss.flexilogxml.UtilClassLoader;
import fr.distrimind.oss.flexilogxml.exceptions.XMLStreamException;
import fr.distrimind.oss.flexilogxml.systeminfo.OS;
import fr.distrimind.oss.flexilogxml.systeminfo.OSVersion;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public final class XmlParserFactory {

	@SuppressWarnings({"unchecked", "PMD.PreserveStackTrace"})
	private static <T> T getXmlFactory(String clazz) throws XMLStreamException {		try {
			Class<? extends T> c = (Class<? extends T>)UtilClassLoader.getLoader().loadClass(clazz);
			return c.getConstructor().newInstance();
		} catch (ClassNotFoundException e) {
			throw new XMLStreamException("Class not found : "+clazz, e);
		} catch (InvocationTargetException | InstantiationException e) {
			Throwable t=e.getCause();
			if (t instanceof Exception)
				throw XMLStreamException.getXmlStreamException((Exception)t);
			else
				throw new RuntimeException(t);
		} catch (IllegalAccessException | NoSuchMethodException e) {
			throw XMLStreamException.getXmlStreamException(e);
		}


	}
	public static XmlInputFactory getXmlInputFactory() throws XMLStreamException {
		if (OSVersion.getCurrentOSVersion().getOS()== OS.ANDROID)
			return getXmlFactory("fr.distrimind.oss.flexilogxml.android.xml.XmlInputFactory");
		else
			return getXmlFactory("fr.distrimind.oss.flexilogxml.desktop.xml.XmlInputFactory");
	}
	public static XmlOutputFactory getXmlOutputFactory() throws XMLStreamException {
		if (OSVersion.getCurrentOSVersion().getOS()== OS.ANDROID)
			return getXmlFactory("fr.distrimind.oss.flexilogxml.android.xml.XmlOutputFactory");
		else
			return getXmlFactory("fr.distrimind.oss.flexilogxml.desktop.xml.XmlOutputFactory");
	}

}
