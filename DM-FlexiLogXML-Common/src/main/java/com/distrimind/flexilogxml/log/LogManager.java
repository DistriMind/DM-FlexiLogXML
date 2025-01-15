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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-UtilsLib 1.0.0
 */
public class LogManager {
	private static volatile Collection<Handler> handlers= Collections.emptyList();

	public static void addHandler(Handler handler)
	{
		Collection<Handler> h=new ArrayList<>(handlers.size()+1);
		h.addAll(handlers);
		h.add(handler);
		handlers=h;
	}
	public static void addHandlers(Handler... handlers)
	{
		Collection<Handler> res=new ArrayList<>(LogManager.handlers.size()+handlers.length);
		res.addAll(LogManager.handlers);
		res.addAll(Arrays.asList(handlers));
		LogManager.handlers=res;
	}

	public static boolean removeHandler(Handler handler)
	{
		Collection<Handler> h=new ArrayList<>(handlers.size());
		h.addAll(handlers);
		boolean r=h.remove(handler);
		handlers=h;
		return r;
	}

	public static void resetHandlers()
	{
		handlers=Collections.emptyList();
	}
	public static void resetHandlers(Handler... handlers)
	{
		Collection<Handler> res=new ArrayList<>(handlers.length);
		res.addAll(Arrays.asList(handlers));
		LogManager.handlers=res;
	}
	static Collection<Handler> getHandlers()
	{
		return handlers;
	}

}
