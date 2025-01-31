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

package com.distrimind.flexilogxml.android.log;


import com.distrimind.flexilogxml.FlexiLogXML;
import com.distrimind.flexilogxml.android.ContextProvider;

import org.slf4j.event.Level;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

public class LoggerConfig {
	private static final Properties properties;
	private static final Level defaultLogLevel;
	private static final String headerProperty="log.level.";
	private static volatile Map<String, Level> cache=new HashMap<>();

	static {
		Level d;
		Properties p;
		try (InputStream is=ContextProvider.getApplicationContext().getAssets().open("flexilogxml.android.logger.properties")){
			if (is==null) {
				p=null;
				d = null;
			}
			else {
				p=new Properties();
				p.load(is);
				d = getLevelOr("log.level.root", s -> getLevelOr(headerProperty, s2 -> null));
			}
		} catch (IOException ignored) {
			d = null;
			p=null;
		}
		properties=p;
		defaultLogLevel = d;
	}
	private static Level getDefaultLogLevel()
	{
		if (defaultLogLevel==null)
			return FlexiLogXML.getDefaultLogLevel();
		else
			return defaultLogLevel;
	}

	private static Level getLevelOr(String property, Function<String, Level> subLevelProducer) {
		if (properties==null)
			return getDefaultLogLevel();
		else {
			String level=properties.getProperty(property, null);
			if (level != null && !level.isEmpty()) {
				for (Level l : Level.values()) {
					if (l.toString().equalsIgnoreCase(level))
						return l;
				}
			}
			int i = property.lastIndexOf('.');
			if (i <= headerProperty.length()) {
				return subLevelProducer.apply(null);
			}
			else {
				return subLevelProducer.apply(property.substring(0, i));
			}
		}
	}
	private static class R implements Function<String, Level> {

		@Override
		public Level apply(String s) {
			if (s == null || s.isEmpty())
				return getDefaultLogLevel();

			return getLevelOr(s, this);
		}
	}
	public static Level getLogLevel(String loggerName) {
		if (properties==null)
			return getDefaultLogLevel();
		Map<String, Level> m=cache;
		Level l=m.get(loggerName);
		if (l==null) {

			l=new R().apply(headerProperty+loggerName);
			m=new HashMap<>(m);
			m.put(loggerName, l);
			cache=m;
		}
		return l;
	}
	public static boolean isLogEnabled(String loggerName, Level level)
	{
		return getLogLevel(loggerName).toInt()<=level.toInt();
	}

}