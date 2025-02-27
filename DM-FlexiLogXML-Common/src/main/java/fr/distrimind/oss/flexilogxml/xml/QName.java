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

import java.util.Objects;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public class QName {


	private final String namespaceURI;

	private final String localPart;

	private final String prefix;
	protected QName() {
		this.namespaceURI=null;
		this.localPart=null;
		this.prefix=null;
	}
	public QName(String namespaceURI, String localPart) {
		this(namespaceURI, localPart, Constants.DEFAULT_NS_PREFIX);
	}
	public QName(String namespaceURI, String localPart, String prefix) {
		if (localPart==null)
			throw new NullPointerException();
		if (prefix==null)
			throw new NullPointerException();
		this.namespaceURI = Objects.requireNonNullElse(namespaceURI, Constants.NULL_NS_URI);
		this.localPart = localPart;
		this.prefix = prefix;
	}

	public String getNamespaceURI() {
		return namespaceURI;
	}

	public String getLocalPart() {
		return localPart;
	}

	public String getPrefix() {
		return prefix;
	}

	/*@Override
	public int getInternalSerializedSize() {
		return SerializationTools.getInternalSize(namespaceURI, Constants.MAX_NS_URI_LENGTH)
				+SerializationTools.getInternalSize(localPart, Constants.MAX_LOCAL_PART_LENGTH)
				+SerializationTools.getInternalSize(prefix, Constants.MAX_PREFIX_LENGTH);
	}

	@Override
	public void writeExternal(SecuredObjectOutputStream out) throws DMIOException {
		out.writeString(namespaceURI, false, Constants.MAX_NS_URI_LENGTH);
		out.writeString(localPart, false, Constants.MAX_LOCAL_PART_LENGTH);
		out.writeString(prefix, false, Constants.MAX_PREFIX_LENGTH);
	}

	@Override
	public void readExternal(SecuredObjectInputStream in) throws DMIOException {
		namespaceURI=in.readString(false, Constants.MAX_NS_URI_LENGTH);
		localPart=in.readString(false, Constants.MAX_LOCAL_PART_LENGTH);
		prefix=in.readString(false, Constants.MAX_PREFIX_LENGTH);
	}*/

	@Override
	public String toString() {
		return "QName{" +
				"namespaceURI='" + namespaceURI + '\'' +
				", localPart='" + localPart + '\'' +
				", prefix='" + prefix + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		QName qName = (QName) o;
		return Objects.equals(namespaceURI, qName.namespaceURI) && Objects.equals(localPart, qName.localPart) && Objects.equals(prefix, qName.prefix);
	}

	@Override
	public int hashCode() {
		return Objects.hash(namespaceURI, localPart, prefix);
	}
}
