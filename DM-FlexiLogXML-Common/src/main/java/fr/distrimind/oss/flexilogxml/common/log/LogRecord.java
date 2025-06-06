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

package fr.distrimind.oss.flexilogxml.common.log;

import fr.distrimind.oss.flexilogxml.common.FlexiLogXML;

import java.util.Arrays;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 1.1.0
 */
public class LogRecord {
	private final Level level;
	private final Marker marker;
	private final String message;
	private final Throwable t;
	private final Object[] arguments;
	private final long createdOnUTC;
	public LogRecord(Level level, String message) {
		this(level, FlexiLogXML.getMarker(), message, null, null);
	}
	public LogRecord(Level level,  Throwable t) {
		this(level, FlexiLogXML.getMarker(), "", t, null);
	}
	public LogRecord(Level level, String message, Throwable t) {
		this(level, FlexiLogXML.getMarker(), message, t, null);
	}
	public LogRecord(Level level, String message, Throwable t, Object[] arguments) {
		this(level, FlexiLogXML.getMarker(), message, t, arguments);
	}
	public LogRecord(Level level, Marker marker, String message, Throwable t, Object[] arguments) {
		if (level==null)
			throw new NullPointerException();
		if (message==null)
			throw new NullPointerException();
		this.level = level;
		this.marker = marker;
		this.message = message;
		this.t = t;
		this.arguments = arguments==null?null:arguments.clone();
		createdOnUTC =System.currentTimeMillis();
	}

	public Level getLevel() {
		return level;
	}

	public Marker getMarker() {
		return marker;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getT() {
		return t;
	}

	public Object[] getArguments() {
		return arguments==null?null:arguments.clone();
	}

	public long getCreatedOnUTC() {
		return createdOnUTC;
	}

	@Override
	public String toString() {
		return "LogRecord{" +
				"level=" + level +
				", marker=" + marker +
				", message='" + message + '\'' +
				", t=" + t +
				", arguments=" + Arrays.toString(arguments) +
				", createdOnUTC=" + createdOnUTC +
				'}';
	}
}
