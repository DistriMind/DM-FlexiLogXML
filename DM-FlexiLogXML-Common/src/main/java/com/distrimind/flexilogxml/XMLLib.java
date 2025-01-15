/*
DM-FlexiLogXML (package com.distrimind.flexilogxml)
Copyright (C) 2024 Jason Mahdjoub (author, creator and contributor) (Distrimind)
The project was created on January 11, 2025

jason.mahdjoub@distri-mind.fr


This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.distrimind.flexilogxml;

import com.distrimind.flexilogxml.log.DMLogger;
import com.distrimind.flexilogxml.log.ILogFormatter;
import com.distrimind.flexilogxml.log.LogFactory;
import com.distrimind.flexilogxml.log.LogFormatter;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 
 * @author Jason Mahdjoub
 * @version 3.9
 */
@SuppressWarnings("PMD")
public class XMLLib {
	private static Function<String, Logger> defaultLoggerSupplier= (LogFactory::getLogger);
	private static DMLogger logger=null;
	private static Marker marker= null;

	static
	{

		setLogLevel(Level.INFO);
	}

	public static void setMarker(Marker marker)
	{
		XMLLib.marker=marker;
	}

	public static Marker getMarker() {
		return marker;
	}

	public static void setLogger(Logger logger)
	{
		XMLLib.logger=new DMLogger(logger);
	}
	public static void setLogger(DMLogger logger)
	{
		XMLLib.logger=logger;
	}

	public static void setLogLevel(Level level)
	{
		if (logger == null) {
			logger = getLoggerInstance("[Utils]", 10, DateTimeFormatter.ofPattern("HH:mm:ss.SSSS"), level);
		}
		else
			logger.setLogLevel(level);
	}

	public static void resetLogger()
	{
		if (logger!=null) {
			Level currentLevel=logger.getLogLevel();
			logger=null;
			setLogLevel(currentLevel);
		}
	}

	public static void setDefaultLoggerSupplier(Function<String, Logger> defaultLoggerSupplier, boolean resetLogger)
	{
		if (defaultLoggerSupplier==null)
			throw new NullPointerException();
		XMLLib.defaultLoggerSupplier=defaultLoggerSupplier;
		if (resetLogger) {
			resetLogger();
		}
	}


	public static Function<String, Logger> getDefaultLoggerSupplier() {
		return defaultLoggerSupplier;
	}

	public static DMLogger getLoggerInstance(String header, int maxHeaderSize, DateTimeFormatter dateTimeFormat, Level level)
	{
		Logger logger=defaultLoggerSupplier.apply(header);
		return configureLogger(logger, maxHeaderSize, dateTimeFormat, level);
	}

	public static DMLogger configureLogger(Logger logger, int maxHeaderSize, DateTimeFormatter dateTimeFormat, Level level)
	{
		return configureLogger(logger, maxHeaderSize, dateTimeFormat, level, new LogFormatter(false, false, false, LogFactory.doNotUseMarker(logger)));
	}

	public static DMLogger configureLogger(Logger logger, int maxHeaderSize, DateTimeFormatter dateTimeFormat, Level level, ILogFormatter logFormatter)
	{
		if (logFormatter instanceof LogFormatter) {
			LogFormatter lf=(LogFormatter)logFormatter;
			lf.setMaxHeaderSize(maxHeaderSize);
			lf.setDateTimeFormat(dateTimeFormat);
		}
		return new DMLogger(logger, logFormatter, level);
	}

	public static DMLogger getLogger() {
		return logger;
	}
	public static void log(Level level, Throwable throwable)
	{
		if (logger!=null)
			logger.log(level, marker, throwable);
	}
	public static void log(Level level, Supplier<String> message, Object ... parameters)
	{
		if (logger!=null)
			logger.log(level, marker, message, parameters);
	}
	public static void log(Level level, String message, Object ... parameters)
	{
		if (logger!=null)
			logger.log(level, marker, message, parameters);
	}
	public static void log(Level level, Supplier<String> message, Throwable throwable, Object ... parameters)
	{
		if (logger!=null)
		{
			if (throwable != null)
				logger.log(level, marker, message, throwable, parameters);
			else
				logger.log(level, marker, message, parameters);
		}
	}
	public static void log(Level level, String message, Throwable throwable, Object ... parameters)
	{
		if (logger!=null)
		{
			if (throwable != null)
				logger.log(level, marker, message, throwable, parameters);
			else
				logger.log(level, marker, message, parameters);
		}
	}


	public static void log(Level level, Marker marker, Throwable throwable)
	{
		if (logger!=null)
			logger.log(level, marker, throwable);
	}
	public static void log(Level level, Marker marker, Supplier<String> message, Object ... parameters)
	{
		if (logger!=null)
			logger.log(level, marker, message, parameters);
	}
	public static void log(Level level, Marker marker, String message, Object ... parameters)
	{
		if (logger!=null)
			logger.log(level, marker, message, parameters);
	}
	public static void log(Level level, Marker marker, Supplier<String> message, Throwable throwable, Object ... parameters)
	{
		if (logger!=null)
		{
			if (throwable != null)
				logger.log(level, marker, message, throwable, parameters);
			else
				logger.log(level, marker, message, parameters);
		}
	}
	public static void log(Level level, Marker marker, String message, Throwable throwable, Object ... parameters)
	{
		if (logger!=null)
		{
			if (throwable != null)
				logger.log(level, marker, message, throwable, parameters);
			else
				logger.log(level, marker, message, parameters);
		}
	}

	private static volatile Locale locale=null;

	public static Locale getLocale() {
		if (locale==null)
			return Locale.getDefault();
		return locale;
	}

	public static void setLocale(Locale locale) {
		XMLLib.locale = locale;
	}
}
