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

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public enum XMLType {
	START_ELEMENT(1),
	END_ELEMENT(2),
	PROCESSING_INSTRUCTION(3),
	CHARACTERS(4),
	COMMENT(5),
	SPACE(6),
	START_DOCUMENT(7),
	END_DOCUMENT(8),
	ENTITY_REFERENCE(9),
	ATTRIBUTE(10),
	DTD(11),
	CDATA(12),
	NAMESPACE(13),
	NOTATION_DECLARATION(14),
	ENTITY_DECLARATION(15);
	private final int code;
	XMLType(int code) {
		this.code=code;
	}

	public int getCode() {
		return code;
	}

	public static XMLType fromCode(int code)
	{
		for (XMLType t : XMLType.values())
		{
			if (t.code==code)
				return t;
		}
		return null;
	}


}
