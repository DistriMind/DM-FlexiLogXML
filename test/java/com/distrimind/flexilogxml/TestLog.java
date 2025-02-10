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
Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.*/

package com.distrimind.flexilogxml;

import com.distrimind.flexilogxml.log.DMLogger;
import com.distrimind.flexilogxml.log.Level;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 1.0
 */
public class TestLog {
	private static final DMLogger log=FlexiLogXML.getLoggerInstance(TestLog.class);
	private void testCausedBy() throws IllegalAccessException {
		throw new IllegalAccessException();
	}
	private boolean debugMessage1=false;
	private boolean debugMessage2=false;
	@Test
	@SuppressWarnings("PMD.GuardLogStatement")
	public void testLog()
	{
		FlexiLogXML.log(Level.INFO, "One message");
		FlexiLogXML.log(Level.INFO, "One message with parameter {}", "param1");
		FlexiLogXML.log(Level.TRACE, "One message with parameter {}", "param1");
		log.debug(() -> {
			debugMessage2=true;
			return "debug message message2";
		});
		Assert.assertTrue(debugMessage2);

		FlexiLogXML.log(Level.DEBUG, () -> {
			debugMessage1=true;
			return "debug message message1";
		});
		Assert.assertTrue(debugMessage1);

		FlexiLogXML.log(Level.INFO, () -> "message1");
		FlexiLogXML.log(Level.WARN, () -> "message2");
		FlexiLogXML.log(Level.ERROR, () -> "message3");
		FlexiLogXML.log(Level.WARN, new IllegalAccessError());
		FlexiLogXML.log(Level.WARN, ()-> "message5", new IllegalAccessError("message6"));
		FlexiLogXML.log(Level.INFO, () -> "message4");
		try {
			testCausedBy();
		} catch (IllegalAccessException e) {
			FlexiLogXML.log(Level.ERROR, "message6", new IllegalArgumentException(e));
		}
		Assert.assertTrue(true, "Test OK");
	}
}
