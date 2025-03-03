/*
 *
 * DM-FlexiLogXML (package com.distrimind.flexilogxml)
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


package fr.distrimind.oss.flexilogxml.common;


import fr.distrimind.oss.flexilogxml.common.log.Level;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * The MadkitClassLoader is the class loader used by MaDKit. It enables some
 * specific features such as class hot reloading, jar loading, etc.
 * 
 * @author Fabien Michel
 * @author Jacques Ferber
 * @author Jason Mahdjoub
 * @version 1.0
 * 
 */
public class UtilClassLoader extends URLClassLoader{ // NO_UCD

	private Collection<String> classesToReload;

	private static UtilClassLoader currentCL;

	static {
		init();
	}

	public static void init() {
		init(Thread.currentThread().getContextClassLoader());
	}
	public static void init(ClassLoader classLoader) {
		currentCL = new UtilClassLoader(classLoader, null);
	}

	private static URL[] getInitialURLs()
	{
		final URL[] urls;
		final String[] urlsName = System.getProperty("java.class.path").split(File.pathSeparator);
		urls = new URL[urlsName.length];
		for (int i = 0; i < urlsName.length; i++) {
			try {
				urls[i] = new File(urlsName[i]).toURI().toURL();
			} catch (MalformedURLException e) {
				FlexiLogXML.log(Level.WARN,e);
			}
			// }
		}
		return urls;
	}
	protected UtilClassLoader(final ClassLoader parent, Collection<String> toReload) {
		this(getInitialURLs(), parent, toReload);
	}
	protected UtilClassLoader(URL[] urls, final ClassLoader parent, Collection<String> toReload) {
		super(urls, parent);
		if (toReload != null)
			classesToReload = new HashSet<>(toReload);
	}

	/**
	 * Returns the last class loader, thus having all the loaded jars on the
	 * classpath.
	 * 
	 * @return the last class loader.
	 */
	public static UtilClassLoader getLoader() {
		return currentCL;
	}

	@Override
	public synchronized Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
		Class<?> c;
		// synchronized (getClassLoadingLock(name)) {
		if (classesToReload != null && classesToReload.contains(name)) {
			c = findLoadedClass(name);
			if (c != null) {

				try(UtilClassLoader mcl = new UtilClassLoader(getURLs(), this, classesToReload)) {
					classesToReload.remove(name);
					c = mcl.loadClass(name, resolve);
				}
				catch (IOException e)
				{
					FlexiLogXML.log(Level.WARN, e);
				}

			} else {// Never defined nor reloaded : go for defining
				addUrlAndLoadClasses(name);
				// findClass(name);
				classesToReload = null;
				return loadClass(name, resolve);// I should now find it on this next try
			}
		} else {
			c = findLoadedClass(name);
		}
		if (c == null) {
			return super.loadClass(name, resolve);
		}
		if (resolve)
			resolveClass(c);
		// }
		return c;
	}

	/**
	 * Schedule the reloading of the byte code of a class for its next loading. So
	 * new instances, created using {@link java.lang.reflect.Constructor#newInstance(Object...)}} on a class object
	 * obtained with {@link #loadClass(String)}, will reflect compilation changes
	 * during run time.
	 * <p>
	 * In fact, using {@link #loadClass(String)} on the current MDK class loader
	 * obtained with {@link #getLoader()} returns the class object corresponding to
	 * the last compilation of the java code available on the class path.
	 * Especially, this may return a different version than
	 * {@link Class#forName(String)} because {@link Class#forName(String)} uses the
	 * {@link ClassLoader} of the caller's current class which could be different
	 * from the current one (i.e. the one obtained {@link #getLoader()}) if several
	 * reloads have been done.
	 * <p>
	 *
	 * 
	 * @param name
	 *            The fully qualified class name of the class
	 * @throws ClassNotFoundException
	 *             if the class cannot be found on the class path
	 */
	public void reloadClass(String name) throws ClassNotFoundException {// TODO return false and return code
		if (getResource(name.replace('.', '/') + ".class") == null)
			throw new ClassNotFoundException(name);
		if (classesToReload == null) {
			classesToReload = new HashSet<>();
		}
		classesToReload.add(name);
	}



	/**
	 * used to reload classes from the target's package, ensuring accessibility
	 * 
	 * @param name
	 *            full class's name
	 */
	private void addUrlAndLoadClasses(final String name) {
		if (name.startsWith("madkit.kernel."))
			return;
		final URL url = this.getResource(name.replace('.', '/') + ".class");
		if (url != null && "file".equals(url.getProtocol())) {
			String packageName = getClassPackageName(name);
			packageName = packageName == null ? "" : packageName + '.';// need this to rebuild
			final String urlPath = url.getPath();
			final File packageDir = new File(urlPath.substring(0, urlPath.lastIndexOf('/')));
			for (final String fileName : Objects.requireNonNull(packageDir.list())) {
				if (fileName.endsWith(".class")) {
					try {
						final String className = packageName + fileName.substring(0, fileName.length() - 6);
						if (findLoadedClass(className) == null) {// because it could be already loaded by loading
																	// another class that depends on it
							findClass(className);
						}
					} catch (ClassNotFoundException | ClassCircularityError e) {
						FlexiLogXML.log(Level.WARN, e);
					} // FIXME just a reminder

				}
			}
		}
	}

	/**
	 * Returns the package name for this class name. E.g.
	 * <code>java.lang.Object</code> as input gives <code>java.lang</code> as
	 * output.
	 * 
	 * @param classFullName
	 *            the full name of a class
	 * @return the package name or <code>null</code> if no package is defined
	 */
	public static String getClassPackageName(final String classFullName) {
		final int index = classFullName.lastIndexOf('.');
		return index > 0 ? classFullName.substring(0, index) : null;
	}

	/**
	 * Returns the simple name for a full class name. E.g.
	 * <code>java.lang.Object</code> as input gives <code>Object</code> as output.
	 * 
	 * @param classFullName
	 *            the full name of a class
	 * @return the simple name of a class name
	 */
	public static String getClassSimpleName(final String classFullName) {
		final int index = classFullName.lastIndexOf('.');
		return index > 0 ? classFullName.substring(index + 1) : classFullName;
	}



	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" CP : " + Arrays.deepToString(getURLs())
		/* +"\nmains="+getAgentsWithMain() */;// TODO check why this error occurs
	}
	public boolean isClassAvailable(String name) {
		try {
			loadClass(name, false);
		}
		catch (ClassNotFoundException ignored) {
			return false;
		}
		return true;
	}
}
