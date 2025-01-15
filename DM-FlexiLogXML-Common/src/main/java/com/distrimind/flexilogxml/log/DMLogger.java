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

package com.distrimind.flexilogxml.log;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public class DMLogger {
	private final Logger logger;
	private ILogFormatter logFormatter;
	private Level logLevel;
	private final Collection<Handler> handlers=new ArrayList<>();

	public DMLogger(Logger logger) {
		this(logger, null, Level.INFO);
	}
	public DMLogger(Logger logger, ILogFormatter logFormatter) {
		this(logger, logFormatter, Level.INFO);
	}
	public DMLogger(Logger logger, Level logLevel) {
		this(logger, null, logLevel);
	}
	public DMLogger(Logger logger, ILogFormatter logFormatter, Level logLevel) {
		if (logger==null)
			throw new NullPointerException();

		this.logger = logger;
		if (logFormatter==null)
			this.logFormatter=new LogFormatter(false, false, false, LogFactory.doNotUseMarker(logger));
		else
			this.logFormatter = logFormatter;
		setLogLevel(logLevel);
	}

	public final void setLogLevel(Level logLevel) {
		this.logLevel = logLevel;
	}

	public Level getLogLevel() {
		return logLevel;
	}

	public void setLogFormatter(ILogFormatter logFormatter) {
		if (logFormatter==null)
			throw new NullPointerException();
		this.logFormatter = logFormatter;
	}



	public void log(Level level, Marker marker, Supplier<String> messageSupplier, Object ...arguments)
	{
		log(level, marker, messageSupplier, null, arguments);
	}
	public void log(Level level, Marker marker, Throwable t)
	{
		if (level!=null)
			log(level, marker, () -> "", t);
	}
	private static LogRecord pushLogToHandlers(LogRecord _r, Collection<Handler> ch, Level level, Marker marker, Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		LogRecord r=_r;
		for (Handler h : ch) {
			if (h.getDefaultLevel().compareTo(level) < 0)
				continue;
			if (r == null)
				r = new LogRecord(level, marker, messageSupplier.get(), t, arguments);
			h.pushNewLog(r);
		}
		return r;
	}
	private String pushLogToHandlers(Level level, Marker marker, Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		LogRecord r=pushLogToHandlers(null, handlers, level, marker, messageSupplier, t, arguments);
		r=pushLogToHandlers(r, LogManager.getHandlers(), level, marker, messageSupplier, t, arguments);

		if (r==null)
			return null;
		else
			return r.getMessage();
	}
	public void log(Level level, Marker marker, Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		if (level!=null)
		{
			String m=pushLogToHandlers(level, marker, messageSupplier, t, arguments);
			if (m==null)
				logFormatter.log(logger, level, marker, messageSupplier, t, System.currentTimeMillis(), arguments);
			else
				logFormatter.log(logger, level, marker, m, t, System.currentTimeMillis(), arguments);
		}
	}
	public void log(Level level, Marker marker, String message, Object ...arguments)
	{
		if (level!=null)
			log(level, marker, () -> message, null, arguments);
	}

	public void log(Level level, Marker marker, String message, Throwable t, Object ...arguments)
	{
		if (level!=null)
		{
			log(level, marker, () -> message, t, arguments);
		}
	}

	public void log(Level level, Supplier<String> messageSupplier, Object ...arguments)
	{
		log(level, null, messageSupplier, arguments);
	}
	public void log(Level level, Throwable t)
	{
		log(level, null, t);
	}
	public void log(Level level, Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		log(level, null, messageSupplier, t, arguments);
	}
	public void log(Level level, String message, Object ...arguments)
	{
		log(level, null, message, arguments);
	}
	public void log(Level level, String message, Throwable t, Object ...arguments)
	{
		log(level, null, message, t, arguments);
	}



	public void log(Supplier<String> messageSupplier, Object ...arguments)
	{
		log(logLevel, messageSupplier, arguments);
	}
	public void log(Throwable t)
	{
		log(logLevel, t);
	}
	public void log(Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		log(logLevel, messageSupplier, t, arguments);
	}
	public void log(String message, Object ...arguments)
	{
		log(logLevel, message, arguments);
	}
	public void log(String message, Throwable t, Object ...arguments)
	{
		log(logLevel, message, t, arguments);
	}


	public void log(Marker marker, Supplier<String> messageSupplier, Object ...arguments)
	{
		log(logLevel, marker, messageSupplier, arguments);
	}
	public void log(Marker marker, Throwable t)
	{
		log(logLevel, marker, t);
	}
	public void log(Marker marker, Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		log(logLevel, marker, messageSupplier, t, arguments);
	}
	public void log(Marker marker, String message, Object ...arguments)
	{
		log(logLevel, marker, message, arguments);
	}
	public void log(Marker marker, String message, Throwable t, Object ...arguments)
	{
		log(logLevel, marker, message, t, arguments);
	}





	public void error(Supplier<String> messageSupplier, Object ...arguments)
	{
		log(Level.ERROR, messageSupplier, arguments);
	}
	public void error(Throwable t)
	{
		log(Level.ERROR, t);
	}
	public void error(Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		log(Level.ERROR, messageSupplier, t, arguments);
	}
	public void error(String message, Object ...arguments)
	{
		log(Level.ERROR, message, arguments);
	}
	public void error(String message, Throwable t, Object ...arguments)
	{
		log(Level.ERROR, message, t, arguments);
	}


	public void error(Marker marker, Supplier<String> messageSupplier, Object ...arguments)
	{
		log(Level.ERROR, marker, messageSupplier, arguments);
	}
	public void error(Marker marker, Throwable t)
	{
		log(Level.ERROR, marker, t);
	}
	public void error(Marker marker, Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		log(Level.ERROR, marker, messageSupplier, t, arguments);
	}
	public void error(Marker marker, String message, Object ...arguments)
	{
		log(Level.ERROR, marker, message, arguments);
	}
	public void error(Marker marker, String message, Throwable t, Object ...arguments)
	{
		log(Level.ERROR, marker, message, t, arguments);
	}




	public void warn(Supplier<String> messageSupplier, Object ...arguments)
	{
		log(Level.WARN, messageSupplier, arguments);
	}
	public void warn(Throwable t)
	{
		log(Level.WARN, t);
	}
	public void warn(Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		log(Level.WARN, messageSupplier, t, arguments);
	}
	public void warn(String message, Object ...arguments)
	{
		log(Level.WARN, message, arguments);
	}
	public void warn(String message, Throwable t, Object ...arguments)
	{
		log(Level.WARN, message, t, arguments);
	}

	public void warn(Marker marker, Supplier<String> messageSupplier, Object ...arguments)
	{
		log(Level.WARN, marker, messageSupplier, arguments);
	}
	public void warn(Marker marker, Throwable t)
	{
		log(Level.WARN, marker, t);
	}
	public void warn(Marker marker, Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		log(Level.WARN, marker, messageSupplier, t, arguments);
	}
	public void warn(Marker marker, String message, Object ...arguments)
	{
		log(Level.WARN, marker, message, arguments);
	}
	public void warn(Marker marker, String message, Throwable t, Object ...arguments)
	{
		log(Level.WARN, marker, message, t, arguments);
	}

	public void info(Supplier<String> messageSupplier, Object ...arguments)
	{
		log(Level.INFO, messageSupplier, arguments);
	}
	public void info(Throwable t)
	{
		log(Level.INFO, t);
	}
	public void info(Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		log(Level.INFO, messageSupplier, t, arguments);
	}
	public void info(String message, Object ...arguments)
	{
		log(Level.INFO, message, arguments);
	}
	public void info(String message, Throwable t, Object ...arguments)
	{
		log(Level.INFO, message, t, arguments);
	}


	public void info(Marker marker, Supplier<String> messageSupplier, Object ...arguments)
	{
		log(Level.INFO, marker, messageSupplier, arguments);
	}
	public void info(Marker marker, Throwable t)
	{
		log(Level.INFO, marker, t);
	}
	public void info(Marker marker, Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		log(Level.INFO, marker, messageSupplier, t, arguments);
	}
	public void info(Marker marker, String message, Object ...arguments)
	{
		log(Level.INFO, marker, message, arguments);
	}
	public void info(Marker marker, String message, Throwable t, Object ...arguments)
	{
		log(Level.INFO, marker, message, t, arguments);
	}




	public void debug(Supplier<String> messageSupplier, Object ...arguments)
	{
		log(Level.DEBUG, messageSupplier, arguments);
	}
	public void debug(Throwable t)
	{
		log(Level.DEBUG, t);
	}
	public void debug(Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		log(Level.DEBUG, messageSupplier, t, arguments);
	}
	public void debug(String message, Object ...arguments)
	{
		log(Level.DEBUG, message, arguments);
	}
	public void debug(String message, Throwable t, Object ...arguments)
	{
		log(Level.DEBUG, message, t, arguments);
	}



	public void debug(Marker marker, Supplier<String> messageSupplier, Object ...arguments)
	{
		log(Level.DEBUG, marker, messageSupplier, arguments);
	}
	public void debug(Marker marker, Throwable t)
	{
		log(Level.DEBUG, marker, t);
	}
	public void debug(Marker marker, Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		log(Level.DEBUG, marker, messageSupplier, t, arguments);
	}
	public void debug(Marker marker, String message, Object ...arguments)
	{
		log(Level.DEBUG, marker, message, arguments);
	}
	public void debug(Marker marker, String message, Throwable t, Object ...arguments)
	{
		log(Level.DEBUG, marker, message, t, arguments);
	}



	public void trace(Supplier<String> messageSupplier, Object ...arguments)
	{
		log(Level.TRACE, messageSupplier, arguments);
	}
	public void trace(Throwable t)
	{
		log(Level.TRACE, t);
	}
	public void trace(Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		log(Level.TRACE, messageSupplier, t, arguments);
	}
	public void trace(String message, Object ...arguments)
	{
		log(Level.TRACE, message, arguments);
	}
	public void trace(String message, Throwable t, Object ...arguments)
	{
		log(Level.TRACE, message, t, arguments);
	}



	public void trace(Marker marker, Supplier<String> messageSupplier, Object ...arguments)
	{
		log(Level.TRACE, marker, messageSupplier, arguments);
	}
	public void trace(Marker marker, Throwable t)
	{
		log(Level.TRACE, marker, t);
	}
	public void trace(Marker marker, Supplier<String> messageSupplier, Throwable t, Object ...arguments)
	{
		log(Level.TRACE, marker, messageSupplier, t, arguments);
	}
	public void trace(Marker marker, String message, Object ...arguments)
	{
		log(Level.TRACE, marker, message, arguments);
	}
	public void trace(Marker marker, String message, Throwable t, Object ...arguments)
	{
		log(Level.TRACE, marker, message, t, arguments);
	}


	public boolean isEnabledForLevel(Level level) {
		return logger.isEnabledForLevel(level);
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public boolean isTraceEnabled(Marker marker) {
		return logger.isTraceEnabled(marker);
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public boolean isDebugEnabled(Marker marker) {
		return logger.isDebugEnabled(marker);
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public boolean isInfoEnabled(Marker marker) {
		return logger.isInfoEnabled(marker);
	}

	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	public boolean isWarnEnabled(Marker marker) {
		return logger.isWarnEnabled(marker);
	}

	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	public boolean isErrorEnabled(Marker marker) {
		return logger.isErrorEnabled(marker);
	}

	public void addHandler(Handler handler)
	{
		handlers.add(handler);
	}

	public boolean removeHandler(Handler handler)
	{
		return handlers.remove(handler);
	}

	public void resetHandlers()
	{
		handlers.clear();
	}

	public String getName() {
		return logger.getName();
	}

}
