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

package fr.distrimind.oss.flexilogxml.log;
import static org.slf4j.event.EventConstants.DEBUG_INT;
import static org.slf4j.event.EventConstants.ERROR_INT;
import static org.slf4j.event.EventConstants.INFO_INT;
import static org.slf4j.event.EventConstants.TRACE_INT;
import static org.slf4j.event.EventConstants.WARN_INT;
/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public enum Level {
	ERROR(ERROR_INT),
	WARN(WARN_INT),
	INFO(INFO_INT),
	DEBUG(DEBUG_INT),
	TRACE(TRACE_INT);
	private final int levelInt;

	Level(int levelInt) {
		this.levelInt = levelInt;
	}
	public int getLevelInt()
	{
		return levelInt;
	}
	public static Level intToLevel(int levelInt)
	{
		switch (levelInt)
		{
			case ERROR_INT:
				return ERROR;
			case WARN_INT:
				return WARN;
			case INFO_INT:
				return INFO;
			case DEBUG_INT:
				return DEBUG;
			case TRACE_INT:
				return TRACE;
			default:
				throw new IllegalArgumentException();
		}
	}
	static Level from(org.slf4j.event.Level level)
	{
		return intToLevel(level.toInt());
	}

	org.slf4j.event.Level toSLF4JLevel()
	{
		return org.slf4j.event.Level.intToLevel(levelInt);
	}

	@Override
	public String toString() {
		return name();
	}

	int compareTo(org.slf4j.event.Level level)
	{
		return level.toInt()-levelInt;
	}
}
