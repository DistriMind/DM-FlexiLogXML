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

package com.distrimind.flexilogxml.xml;

import com.distrimind.flexilogxml.exceptions.XMLStreamException;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public abstract class XmlOutputFactory {
	public IXmlWriter getXMLWriter(boolean enableIndent, OutputStream outputStream) throws XMLStreamException
	{
		return getXMLWriter(enableIndent, outputStream, Constants.DEFAULT_CHAR_SET);
	}
	public IXmlWriter getXMLWriter(boolean enableIndent, OutputStream outputStream, Charset charset) throws XMLStreamException
	{
		return getXMLWriter(enableIndent, new OutputStreamWriter(outputStream, charset));
	}
	public abstract IXmlWriter getXMLWriter(boolean enableIndent, OutputStreamWriter writer) throws XMLStreamException;
	public IXmlWriter getXMLWriter(OutputStream outputStream) throws XMLStreamException
	{
		return getXMLWriter(true, outputStream);
	}
	public IXmlWriter getXMLWriter(OutputStream outputStream, Charset charset) throws XMLStreamException
	{
		return getXMLWriter(true, outputStream);
	}
	public IXmlWriter getXMLWriter(OutputStreamWriter writer) throws XMLStreamException
	{
		return getXMLWriter(true, writer);
	}
}
