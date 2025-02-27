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

package fr.distrimind.oss.flexilogxml.android.log;

import android.util.Log;
import fr.distrimind.oss.flexilogxml.log.Level;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 1.0.0
 */
public class Logger implements org.slf4j.Logger {
	private final String name;

	public Logger(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isTraceEnabled() {
		return isLoggable(null, Log.VERBOSE, Level.TRACE);
	}

	@Override
	public void trace(String msg) {
		log(null, Log.VERBOSE, msg, (Throwable) null);
	}
	private void log(Marker marker, int level, String format, Object... arg)
	{
		FormattingTuple ft = MessageFormatter.arrayFormat(format, arg);
		log(marker, level, ft.getMessage(), ft.getThrowable());
	}
	private String getTag(Marker marker)
	{
		return marker==null?null:marker.getName();
	}
	private void log(Marker marker, int level, String msg, Throwable t) {
		String tag=getTag(marker);
		switch (level)
		{
			case Log.VERBOSE:
				if (t ==null)
					Log.v(tag, msg);
				else
					Log.v(tag, msg, t);
				break;
			case Log.INFO:
				if (t ==null)
					Log.i(tag, msg);
				else
					Log.i(tag, msg, t);
				break;
			case Log.ERROR:
				if (t ==null)
					Log.e(tag, msg);
				else
					Log.e(tag, msg, t);
				break;
			case Log.DEBUG:
				if (t ==null)
					Log.d(tag, msg);
				else
					Log.d(tag, msg, t);
				break;
			case Log.ASSERT:
				if (t ==null)
					Log.wtf(tag, msg);
				else
					Log.wtf(tag, msg, t);
				break;
			case Log.WARN:
			default:
				if (t ==null)
					Log.w(tag, msg);
				else
					Log.w(tag, msg, t);
		}
	}

	@Override
	public void trace(String format, Object arg) {
		log(null, Log.VERBOSE, format, arg);
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		log(null, Log.VERBOSE, format, arg1, arg2);
	}

	@Override
	public void trace(String format, Object... arguments) {
		log(null, Log.VERBOSE, format, arguments);
	}

	@Override
	public void trace(String msg, Throwable t) {
		log(null, Log.VERBOSE, msg, t);
	}

	@Override
	public boolean isTraceEnabled(Marker marker) {
		return isLoggable(marker, Log.VERBOSE, Level.TRACE);
	}

	@Override
	public void trace(Marker marker, String msg) {
		log(marker, Log.VERBOSE, msg, (Throwable) null);
	}

	@Override
	public void trace(Marker marker, String format, Object arg) {
		log(marker, Log.VERBOSE, format, arg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		log(marker, Log.VERBOSE, format, arg1, arg2);
	}

	@Override
	public void trace(Marker marker, String format, Object... argArray) {
		log(marker, Log.VERBOSE, format, argArray);
	}

	@Override
	public void trace(Marker marker, String msg, Throwable t) {
		log(marker, Log.VERBOSE, msg, t);
	}

	@Override
	public boolean isDebugEnabled() {
		return isLoggable(null, Log.DEBUG, Level.DEBUG);
	}
	private boolean isLoggable(Marker marker, int intLevel, Level level)
	{
		String tag=getTag(marker);
		return Log.isLoggable(tag, intLevel) || LoggerConfig.isLogEnabled(getName(), level);
	}

	@Override
	public void debug(String msg) {
		log(null, Log.DEBUG, msg, (Throwable) null);
	}

	@Override
	public void debug(String format, Object arg) {
		log(null, Log.DEBUG, format, arg);
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		log(null, Log.DEBUG, format, arg1, arg2);
	}

	@Override
	public void debug(String format, Object... arguments) {
		log(null, Log.DEBUG, format, arguments);
	}

	@Override
	public void debug(String msg, Throwable t) {
		log(null, Log.DEBUG, msg, t);
	}

	@Override
	public boolean isDebugEnabled(Marker marker) {
		return isLoggable(marker, Log.DEBUG, Level.DEBUG);
	}

	@Override
	public void debug(Marker marker, String msg) {
		log(marker, Log.DEBUG, msg, (Throwable) null);
	}

	@Override
	public void debug(Marker marker, String format, Object arg) {
		log(marker, Log.DEBUG, format, arg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		log(marker, Log.DEBUG, format, arg1, arg2);
	}

	@Override
	public void debug(Marker marker, String format, Object... arguments) {
		log(marker, Log.DEBUG, format, arguments);
	}

	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		log(marker, Log.DEBUG, msg, t);
	}

	@Override
	public boolean isInfoEnabled() {
		return isLoggable(null, Log.INFO, Level.INFO);
	}

	@Override
	public void info(String msg) {
		log(null, Log.INFO, msg, (Throwable) null);
	}

	@Override
	public void info(String format, Object arg) {
		log(null, Log.INFO, format, arg);
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		log(null, Log.INFO, format, arg1, arg2);
	}

	@Override
	public void info(String format, Object... arguments) {
		log(null, Log.INFO, format, arguments);
	}

	@Override
	public void info(String msg, Throwable t) {
		log(null, Log.INFO, msg, t);
	}

	@Override
	public boolean isInfoEnabled(Marker marker) {
		return isLoggable(marker, Log.INFO, Level.INFO);
	}

	@Override
	public void info(Marker marker, String msg) {
		log(marker, Log.INFO, msg, (Throwable) null);
	}

	@Override
	public void info(Marker marker, String format, Object arg) {
		log(marker, Log.INFO, format, arg);
	}

	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		log(marker, Log.INFO, format, arg1, arg2);
	}

	@Override
	public void info(Marker marker, String format, Object... arguments) {
		log(marker, Log.INFO, format, arguments);
	}

	@Override
	public void info(Marker marker, String msg, Throwable t) {
		log(marker, Log.INFO, msg, t);
	}

	@Override
	public boolean isWarnEnabled() {
		return isLoggable(null, Log.WARN, Level.WARN);
	}

	@Override
	public void warn(String msg) {
		log(null, Log.WARN, msg, (Throwable) null);
	}

	@Override
	public void warn(String format, Object arg) {
		log(null, Log.WARN, format, arg );
	}

	@Override
	public void warn(String format, Object... arguments) {
		log(null, Log.WARN, format, arguments );
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		log(null, Log.WARN, format, arg1, arg2 );
	}

	@Override
	public void warn(String msg, Throwable t) {
		log(null, Log.WARN, msg, t );
	}

	@Override
	public boolean isWarnEnabled(Marker marker) {
		return isLoggable(marker, Log.WARN, Level.WARN);
	}

	@Override
	public void warn(Marker marker, String msg) {
		log(marker, Log.WARN, msg, (Throwable) null);
	}

	@Override
	public void warn(Marker marker, String format, Object arg) {
		log(marker, Log.WARN, format, arg);
	}

	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		log(marker, Log.WARN, format, arg1, arg2);
	}

	@Override
	public void warn(Marker marker, String format, Object... arguments) {
		log(marker, Log.WARN, format, arguments);
	}

	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		log(marker, Log.WARN, msg, t);
	}

	@Override
	public boolean isErrorEnabled() {
		return isLoggable(null, Log.ERROR, Level.ERROR);
	}

	@Override
	public void error(String msg) {
		log(null, Log.ERROR, msg, (Throwable) null);
	}

	@Override
	public void error(String format, Object arg) {
		log(null, Log.ERROR, format, arg);
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		log(null, Log.ERROR, format, arg1, arg2);
	}

	@Override
	public void error(String format, Object... arguments) {
		log(null, Log.ERROR, format, arguments);
	}

	@Override
	public void error(String msg, Throwable t) {
		log(null, Log.ERROR, msg, t);
	}

	@Override
	public boolean isErrorEnabled(Marker marker) {
		return isLoggable(marker, Log.ERROR, Level.ERROR);
	}

	@Override
	public void error(Marker marker, String msg) {
		log(marker, Log.ERROR, msg, (Throwable) null);
	}

	@Override
	public void error(Marker marker, String format, Object arg) {
		log(marker, Log.ERROR, format, arg);
	}

	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		log(marker, Log.ERROR, format, arg1, arg2);
	}

	@Override
	public void error(Marker marker, String format, Object... arguments) {
		log(marker, Log.ERROR, format, arguments);
	}

	@Override
	public void error(Marker marker, String msg, Throwable t) {
		log(marker, Log.ERROR, msg, t);
	}
}
