/*
DM-FlexiLogXML (package com.distrimind.flexilogxml)
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

package com.distrimind.flexilogxml.log;

import org.slf4j.Logger;
import org.slf4j.Marker;

import java.time.ZoneId;
import java.util.function.Supplier;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public interface ILogFormatter {
	default void log(Logger logger, Level level, Marker marker, String message, Throwable t, long timestamp, Object ...arguments)
	{
		log(logger, level,marker, () -> message, t, timestamp, arguments);
	}
	void log(Logger logger, Level level, Marker marker, Supplier<String> messageSupplier, Throwable t, long timestamp, Object ...arguments);
	@SuppressWarnings("PMD.GuardLogStatement")
	default void log(Logger logger, Level level, Marker marker, Supplier<String> messageSupplier, Throwable t)
	{
		if (marker==null) {
			switch (level) {
				case ERROR:
					if (logger.isErrorEnabled()) {
						if (t==null)
							logger.error(messageSupplier.get());
						else
							logger.error(messageSupplier.get(), t);
					}
					break;
				case WARN:
					if (logger.isWarnEnabled()) {
						if (t==null)
							logger.warn(messageSupplier.get());
						else
							logger.warn(messageSupplier.get(), t);
					}
					break;
				case INFO:
					if (logger.isInfoEnabled()) {
						if (t==null)
							logger.info(messageSupplier.get());
						else
							logger.info(messageSupplier.get(), t);
					}
					break;
				case DEBUG:
					if (logger.isDebugEnabled())
					{
						if (t==null)
							logger.debug(messageSupplier.get());
						else
							logger.debug(messageSupplier.get(), t);
					}
					break;
				case TRACE:
					if (logger.isTraceEnabled())
					{
						if (t==null)
							logger.trace(messageSupplier.get());
						else
							logger.trace(messageSupplier.get(), t);
					}
					break;
			}
		}
		else
		{
			switch (level) {
				case ERROR:
					if (logger.isErrorEnabled(marker)) {
						if (t==null)
							logger.error(marker, messageSupplier.get());
						else
							logger.error(marker, messageSupplier.get(), t);
					}
					break;
				case WARN:
					if (logger.isWarnEnabled(marker)) {
						if (t==null)
							logger.warn(marker, messageSupplier.get());
						else
							logger.warn(marker, messageSupplier.get(), t);
					}
					break;
				case INFO:
					if (logger.isInfoEnabled(marker)) {
						if (t==null)
							logger.info(marker, messageSupplier.get());
						else
							logger.info(marker, messageSupplier.get(), t);
					}
					break;
				case DEBUG:
					if (logger.isDebugEnabled(marker))
					{
						if (t==null)
							logger.debug(marker, messageSupplier.get());
						else
							logger.debug(marker, messageSupplier.get(), t);
					}
					break;
				case TRACE:
					if (logger.isTraceEnabled(marker)) {
						if (t==null)
							logger.trace(marker, messageSupplier.get());
						else
							logger.trace(marker, messageSupplier.get(), t);
					}
					break;
			}
		}
	}
	ZoneId getZoneId();
	void setZoneId(ZoneId zoneId);
	default boolean supportMarkers(org.slf4j.Logger logger)
	{
		return !logger.getClass().equals(LogFactory.androidLoggerClass) && !logger.getClass().equals(LogFactory.jdk14LoggerAdapterClass);

	}
}
