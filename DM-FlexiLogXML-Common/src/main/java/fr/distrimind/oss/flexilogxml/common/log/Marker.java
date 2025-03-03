/*
 *
 * DM-FlexiLogXML (package fr.distrimind.oss.flexilogxml)
 * Copyright (C) 2024 Jason Mahdjoub (author, creator and contributor) (Distrimind)
 * The project was created on January 11, 2025
 *
 * jason.mahdjoub@distri-mind.fr
 *
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License only.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * /
 */

package fr.distrimind.oss.flexilogxml.common.log;

import java.util.Iterator;
import java.util.Objects;

public class Marker {
	private final org.slf4j.Marker slf4jMarker;

	public Marker(org.slf4j.Marker marker) {
		this.slf4jMarker = marker;
	}

	org.slf4j.Marker getMarker() {
		return slf4jMarker;
	}

	public String getName() {
		return slf4jMarker.getName();
	}

	public boolean remove(org.slf4j.Marker reference) {
		return slf4jMarker.remove(reference);
	}

	public Iterator<org.slf4j.Marker> iterator() {
		return slf4jMarker.iterator();
	}

	public boolean contains(String name) {
		return slf4jMarker.contains(name);
	}

	public boolean hasReferences() {
		return slf4jMarker.hasReferences();
	}

	@Deprecated
	public boolean hasChildren() {
		return slf4jMarker.hasChildren();
	}

	public boolean contains(org.slf4j.Marker other) {
		return slf4jMarker.contains(other);
	}

	public void add(org.slf4j.Marker reference) {
		slf4jMarker.add(reference);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Marker marker1 = (Marker) o;
		return Objects.equals(slf4jMarker, marker1.slf4jMarker);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(slf4jMarker);
	}
}
