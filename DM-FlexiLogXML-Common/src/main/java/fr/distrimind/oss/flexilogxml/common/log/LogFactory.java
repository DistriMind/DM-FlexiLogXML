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

package fr.distrimind.oss.flexilogxml.common.log;

import fr.distrimind.oss.flexilogxml.common.UtilClassLoader;
import fr.distrimind.oss.flexilogxml.common.systeminfo.OS;
import fr.distrimind.oss.flexilogxml.common.systeminfo.OSVersion;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 7.0.0
 */
public class LogFactory {
	final static String androidLoggerClassName="fr.distrimind.oss.flexilogxml.android.log.Logger";
	final static String jdk14LoggerAdapterClassName="org.slf4j.jul.JDK14LoggerAdapter";
	final static Class<?> androidLoggerClass;
	final static Constructor<?> androidLoggerConstructor;
	final static Class<?> jdk14LoggerAdapterClass;
	private static final ILoggerFactory loggerFactory;
	static
	{
		Class<?> clazz;
		Constructor<?> c=null;
		try {
			clazz=UtilClassLoader.getLoader().loadClass(androidLoggerClassName);
			c=clazz.getConstructor(String.class);
		} catch (ClassNotFoundException | NoSuchMethodException ignored) {
			clazz=null;
		}
		androidLoggerClass=clazz;
		androidLoggerConstructor=c;

		clazz=null;
		try {
			clazz=UtilClassLoader.getLoader().loadClass(jdk14LoggerAdapterClassName);
		} catch (ClassNotFoundException ignored) {

		}

		jdk14LoggerAdapterClass=clazz;
		if (OSVersion.getCurrentOSVersion().getOS()!= OS.ANDROID)
		{
			loggerFactory=LoggerFactory.getILoggerFactory();

		}
		else
			loggerFactory=null;
	}
	public static Logger getLogger(String name) {
		if (OSVersion.getCurrentOSVersion().getOS()== OS.ANDROID)
		{
			if (androidLoggerConstructor==null)
				throw new RuntimeException("Please use DM-FlexiLogXML-Android under android");
			try {
				return (Logger)androidLoggerConstructor.newInstance(name);
			} catch (InstantiationException | IllegalAccessException |
					 InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
		else {
			return loggerFactory.getLogger(name);
		}
	}
	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}
	public static boolean doNotUseMarker(Logger logger)
	{
		return "org.slf4j.impl.AndroidLoggerAdapter".equals(logger.getClass().getName());
	}
}
