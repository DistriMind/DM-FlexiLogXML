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

package com.distrimind.flexilogxml.exceptions;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public class XMLStreamException extends DMIOException {
	private static final long serialVersionUID = 0L;
	public XMLStreamException() {
		this(Integrity.FAIL);
	}
	public XMLStreamException(Integrity integrity) {
		super(integrity);
	}
	public XMLStreamException(String message) {
		super(Integrity.FAIL, message);
	}
	public XMLStreamException(Integrity integrity, String message) {
		super(integrity, message);
	}
	public XMLStreamException(Integrity integrity, Throwable cause) {
		super(integrity, cause==null?"":cause.getMessage(), cause);
	}
	public XMLStreamException(String message, Throwable cause) {
		this(Integrity.FAIL, message, cause);
	}
	public XMLStreamException(Integrity integrity, String message, Throwable cause) {
		super(integrity, message, cause);
	}
	public static XMLStreamException getXmlStreamException(Exception e)
	{
		return getXmlStreamException(Integrity.FAIL, e);
	}
	public static XMLStreamException getXmlStreamException(Integrity integrity, Exception e)
	{
		return getDMIOException(integrity, e, (e2, i) -> new XMLStreamException(i, e2), XMLStreamException.class);
	}
}
