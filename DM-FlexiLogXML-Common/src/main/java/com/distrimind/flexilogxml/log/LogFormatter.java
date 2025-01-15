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
import com.distrimind.flexilogxml.systeminfo.OS;
import com.distrimind.flexilogxml.systeminfo.OSVersion;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.MessageFormatter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since Utils 5.26.0
 */
public class LogFormatter implements ILogFormatter {
	private static final int HEADER_SIZE=56;
	private int maxLevelSize;
	private boolean addHeader;
	private boolean addDateIntoHeader;
	private boolean addLevelIntoHeader;
	private boolean addMarkerIntoHeaderIfNotSupported;
	private DateTimeFormatter dateTimeFormat =DateTimeFormatter.ISO_LOCAL_TIME;
	private int maxHeaderSize =HEADER_SIZE;

	private ZoneId zoneId=ZoneId.systemDefault();
	public LogFormatter(boolean addHeader, boolean addDateIntoHeader, boolean addLevelIntoHeader, boolean addMarkerIntoHeaderIfNotSupported)
	{
		this.addHeader=addHeader;
		this.addDateIntoHeader =addDateIntoHeader;
		this.addMarkerIntoHeaderIfNotSupported = addMarkerIntoHeaderIfNotSupported;
		setAddLevelIntoHeader(addLevelIntoHeader);
	}

	public int getMaxHeaderSize() {
		if (!addHeader)
			return 0;
		else
			return maxHeaderSize;
	}

	public void setMaxHeaderSize(int maxHeaderSize) {
		if (maxHeaderSize <1)
			throw new IllegalArgumentException();
		this.maxHeaderSize = maxHeaderSize;
	}
	private String getMessage(Logger logger, Level level, Marker marker, Supplier<String> messageSupplier, Throwable t, long timestamp, Object ... arguments)
	{
		StringBuilder s = new StringBuilder();

		setHeader(s, logger, marker);
		int rl= maxLevelSize;
		if (addLevelIntoHeader) {
			String levelStr = level.toString();
			s.append(levelStr);
			rl-=levelStr.length();
		}


		while (rl-- > 0)
			s.append(" ");

		int dateFormatSize = 0;
		if (addDateIntoHeader) {
			dateFormatSize = s.length();
			s.append(" - ").append(
					LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId)
							.format(dateTimeFormat)
			);
			dateFormatSize = s.length() - dateFormatSize;
		}
		if (marker!=null && addMarkerIntoHeaderIfNotSupported && !supportMarkers(logger)) {
			s.append(marker.getName());
		}
		String m=messageSupplier.get();
		if (s.length()>0)
			s.append(" : ");
		s.append((arguments==null || arguments.length==0)?m:MessageFormatter.arrayFormat(m, arguments).getMessage()).append("\n");

		if (t != null) {
			appendThrowableStackTrace(s, t, dateFormatSize, maxHeaderSize, maxLevelSize);
		}
		return s.toString();
	}
	private static final boolean usePersonalExceptionFormatter= OSVersion.getCurrentOSVersion().getOS()!= OS.ANDROID;
	@Override
	public void log(Logger logger, Level level, Marker marker, Supplier<String> messageSupplier, Throwable t, long timestamp, Object ...arguments) {

		if (level==null)
			return;
		log(logger, level, addMarkerIntoHeaderIfNotSupported?null:marker, () -> getMessage(logger, level, marker, messageSupplier, usePersonalExceptionFormatter?t:null, timestamp, arguments), usePersonalExceptionFormatter?null:t);
	}


	private static void appendThrowableStackTrace(StringBuilder stringBuilder, Throwable t, int dateFormatSize, int maxHeaderSize, int MAX_LEVEL_SIZE) {

		final String spaces = " ".repeat(Math.max(0, maxHeaderSize + MAX_LEVEL_SIZE + dateFormatSize + 7))+"\t";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		String[] lines = sw.toString().split(System.lineSeparator());
		for (String line : lines) {
			stringBuilder.append(spaces).append(line).append(System.lineSeparator());
		}
	}


	public DateTimeFormatter getDateTimeFormat() {
		return dateTimeFormat;
	}

	public void setDateTimeFormat(DateTimeFormatter dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

	public boolean isAddDateIntoHeader() {
		return addDateIntoHeader;
	}

	public void setAddDateIntoHeader(boolean addDateIntoHeader) {
		this.addDateIntoHeader = addDateIntoHeader;
	}
	protected void setHeaderContent(StringBuilder s, final Logger logger, Marker marker) {
		s.append(marker==null?logger.getName():marker.getName());
	}
	protected void setHeader(StringBuilder s, final Logger logger, Marker marker) {
		int maxHeaderSize=getMaxHeaderSize();
		if (maxHeaderSize>0) {
			setHeaderContent(s, logger, marker);

			if (s.length() < maxHeaderSize) {
				while (s.length() < maxHeaderSize)
					s.append(" ");
			} else if (s.length() > maxHeaderSize)
				s.delete(maxHeaderSize, s.length());
			s.append(" ");
		}
	}

	@Override
	public ZoneId getZoneId() {
		return zoneId;
	}
	@Override
	public void setZoneId(ZoneId zoneId) {
		if (zoneId==null)
			throw new NullPointerException();
		this.zoneId = zoneId;
	}

	public boolean isAddLevelIntoHeader() {
		return addLevelIntoHeader;
	}

	public final void setAddLevelIntoHeader(boolean addLevelIntoHeader) {
		this.addLevelIntoHeader = addLevelIntoHeader;
		if (addLevelIntoHeader) {
			int m = 0;
			for (Level l : Level.values())
				m = Math.max(m, l.toString().length());
			maxLevelSize = m;
		}
		else
			maxLevelSize =0;

	}

	public boolean isAddHeader() {
		return addHeader;
	}

	public void setAddHeader(boolean addHeader) {
		this.addHeader = addHeader;
	}

	public boolean isAddMarkerIntoHeaderIfNotSupported() {
		return addMarkerIntoHeaderIfNotSupported;
	}

	public void setAddMarkerIntoHeaderIfNotSupported(boolean addMarkerIntoHeaderIfNotSupported) {
		this.addMarkerIntoHeaderIfNotSupported = addMarkerIntoHeaderIfNotSupported;
	}

	public int getMaxLevelSize() {
		return maxLevelSize;
	}

}
