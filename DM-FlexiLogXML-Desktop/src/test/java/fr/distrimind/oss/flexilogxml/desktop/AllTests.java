/*
 *
 * DM-FlexiLogXML (package fr.distrimind.oss.flexilogxml)
 * Copyright (C) 2024 Jason Mahdjoub (author, creator and contributor) (DistriMind)
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

package fr.distrimind.oss.flexilogxml.desktop;

import fr.distrimind.oss.flexilogxml.common.Tests;
import fr.distrimind.oss.flexilogxml.common.FlexiLogXML;
import fr.distrimind.oss.flexilogxml.common.log.Level;

import java.io.File;
import java.io.IOException;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public class AllTests extends fr.distrimind.oss.flexilogxml.common.AllTests {

	public static void main(String[] args) throws IOException {
		Tests t=getTests();
		File f=new File("./DM-FlexiLogXML-Desktop/src/test/resources/fr/distrimind/oss/flexilogxml/desktop/AllTestsNG.xml");
		t.saveTestNGToXML(f);
		FlexiLogXML.log(Level.INFO, "XML Test NG file saved into: "+f.getCanonicalPath());
	}
	public static Tests getTests()
	{
		return fr.distrimind.oss.flexilogxml.common.AllTests.getTests();
	}
}
