/*
 *
 * DM-FlexiLogXML (package com.distrimind.flexilogxml)
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

package com.distrimind.flexilogxml.log;

import org.slf4j.IMarkerFactory;

public class MarkerFactory {
	private final IMarkerFactory markerFactory;

	MarkerFactory(IMarkerFactory markerFactory) {
		this.markerFactory = markerFactory;
	}
	private static final MarkerFactory singleton=getInstance();
	public static MarkerFactory getSingleton()
	{
		return singleton;
	}
	public static MarkerFactory getInstance()
	{
		return new MarkerFactory(org.slf4j.MarkerFactory.getIMarkerFactory());
	}

	public Marker getMarker(String name)
	{
		return new Marker(markerFactory.getMarker(name));
	}

	public boolean detachMarker(String name) {
		return markerFactory.detachMarker(name);
	}

	public Marker getDetachedMarker(String name) {
		return new Marker(markerFactory.getDetachedMarker(name));
	}

	public boolean exists(String name) {
		return markerFactory.exists(name);
	}
}
