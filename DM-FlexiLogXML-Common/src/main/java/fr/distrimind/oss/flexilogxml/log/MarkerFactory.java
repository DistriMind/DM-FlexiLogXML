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

package fr.distrimind.oss.flexilogxml.log;

import org.slf4j.IMarkerFactory;

public class MarkerFactory {
	private final IMarkerFactory slf4jMarkerFactory;

	MarkerFactory(IMarkerFactory markerFactory) {
		this.slf4jMarkerFactory = markerFactory;
	}
	private static final MarkerFactory singleton= getNewInstance();
	public static MarkerFactory getSingleton()
	{
		return singleton;
	}
	public static MarkerFactory getNewInstance()
	{
		return new MarkerFactory(org.slf4j.MarkerFactory.getIMarkerFactory());
	}

	public Marker getMarker(String name)
	{
		return new Marker(slf4jMarkerFactory.getMarker(name));
	}

	public boolean detachMarker(String name) {
		return slf4jMarkerFactory.detachMarker(name);
	}

	public Marker getDetachedMarker(String name) {
		return new Marker(slf4jMarkerFactory.getDetachedMarker(name));
	}

	public boolean exists(String name) {
		return slf4jMarkerFactory.exists(name);
	}
}
