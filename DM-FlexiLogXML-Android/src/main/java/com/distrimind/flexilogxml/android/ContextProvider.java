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

package com.distrimind.flexilogxml.android;

import android.app.Application;
import android.content.Context;
import com.distrimind.flexilogxml.FlexiLogXML;
import com.distrimind.flexilogxml.log.Level;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public class ContextProvider {
	private static Application application=null;
	public static void initialize(Application application)
	{
		ContextProvider.application=application;
	}
	public static void applicationClosed()
	{
		application=null;
	}

	public static Context getApplicationContext()
	{
		if (application==null)
			throw new RuntimeException();
		Context r=application.getApplicationContext();
		if (r==null) {
			application = null;
			FlexiLogXML.log(Level.ERROR, () -> "Error : context was not defined. Please affect context by calling method com.distrimind.flexilogxml.android.ContextProvider.initialize(Application) according to Android Application instance");
			throw new RuntimeException();
		}
		return r;
	}
}
