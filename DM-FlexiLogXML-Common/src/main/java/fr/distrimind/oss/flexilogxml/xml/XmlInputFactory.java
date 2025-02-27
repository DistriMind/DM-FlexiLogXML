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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public abstract class XmlInputFactory {
	protected boolean nameSpaceAware =false;
	protected boolean validating =false;
	protected boolean coalescing =true;
	protected boolean replacingEntityReferences =false;
	protected boolean supportingExternalEntities =false;
	protected boolean supportDTD=false;

	public boolean isNameSpaceAware() {
		return nameSpaceAware;
	}

	public void setNameSpaceAware(boolean nameSpaceAware) {
		this.nameSpaceAware = nameSpaceAware;
	}

	public boolean isValidating() {
		return validating;
	}

	public void setValidating(boolean validating) {
		this.validating = validating;
	}

	public boolean isCoalescing() {
		return coalescing;
	}

	public void setCoalescing(boolean coalescing) {
		this.coalescing = coalescing;
	}

	public boolean isReplacingEntityReferences() {
		return replacingEntityReferences;
	}

	public void setReplacingEntityReferences(boolean replacingEntityReferences) {
		this.replacingEntityReferences = replacingEntityReferences;
	}


	public boolean isSupportingExternalEntities() {
		return supportingExternalEntities;
	}

	public void setSupportingExternalEntities(boolean supportingExternalEntities) {
		this.supportingExternalEntities = supportingExternalEntities;
	}

	public boolean isSupportDTD() {
		return supportDTD;
	}

	public void setSupportDTD(boolean supportDTD) {
		this.supportDTD = supportDTD;
	}
	public IXmlReader getXMLReader(InputStream input) throws XMLStreamException
	{
		return getXMLReader(input, Constants.DEFAULT_CHAR_SET);
	}
	public IXmlReader getXMLReader(InputStream input, Charset charset) throws XMLStreamException
	{
		return getXMLReader(new InputStreamReader(input, charset));
	}
	public abstract IXmlReader getXMLReader(Reader reader) throws XMLStreamException;
}

