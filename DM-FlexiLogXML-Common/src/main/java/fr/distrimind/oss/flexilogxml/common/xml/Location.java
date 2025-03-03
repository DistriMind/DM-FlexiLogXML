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


import java.util.Objects;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public class Location {
	private int lineNumber;
	private int columnNumber;
	private int characterOffset;
	private String publicId;
	private String systemId;

	protected Location() {

	}
	public Location(int lineNumber, int columnNumber, int characterOffset, String publicId, String systemId) {
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
		this.characterOffset = characterOffset;
		this.publicId = publicId;
		this.systemId = systemId;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public int getCharacterOffset() {
		return characterOffset;
	}

	public String getPublicId() {
		return publicId;
	}

	public String getSystemId() {
		return systemId;
	}

	@Override
	public String toString() {
		return "Location{" +
				"lineNumber=" + lineNumber +
				", columnNumber=" + columnNumber +
				", characterOffset=" + characterOffset +
				", publicId='" + publicId + '\'' +
				", systemId='" + systemId + '\'' +
				'}';
	}

	public boolean isPositionEqual(Location location) {
		return lineNumber == location.lineNumber && columnNumber == location.columnNumber && characterOffset == location.characterOffset;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Location location = (Location) o;
		return lineNumber == location.lineNumber && columnNumber == location.columnNumber && characterOffset == location.characterOffset && Objects.equals(publicId, location.publicId) && Objects.equals(systemId, location.systemId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(lineNumber, columnNumber, characterOffset, publicId, systemId);
	}
}
