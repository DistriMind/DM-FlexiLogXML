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

package com.distrimind.flexilogxml;


import com.distrimind.flexilogxml.exceptions.DMIOException;
import com.distrimind.flexilogxml.systeminfo.OS;
import com.distrimind.flexilogxml.systeminfo.OSVersion;
import org.slf4j.event.Level;

import java.lang.reflect.*;
import java.util.function.Supplier;

/**
 * 
 * 
 * @author Jason Mahdjoub
 * @version 1.0
 * @since Utils 1.7
 */
@SuppressWarnings("PMD")
public class ReflectionTools {
	private static final String INNER_BUG_STRING = ". This is an inner bug. Please contact the developers. Impossible to continue. See the next error :";
	private static final boolean canAccessMethodUsable = OSVersion.getCurrentOSVersion().getOS() != OS.ANDROID;

	@SuppressWarnings("deprecation")
	public static boolean canAccess(AccessibleObject accessibleObject, Object instance) {
		if (canAccessMethodUsable)
			return accessibleObject.canAccess(instance);
		else
			return accessibleObject.isAccessible();

	}

	public static <T> T invoke(Method m, Object o, Object... args) throws InvocationTargetException {
		try {
			return invoke(true, m, o, args);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T invoke(boolean exitOnFail, Method m, Object o, Object... args) throws InvocationTargetException, IllegalAccessException {

		boolean accessible = canAccess(m, o);
		if (!accessible)
			m.setAccessible(true);
		try {
			return (T) m.invoke(o, args);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			if (exitOnFail) {
				FlexiLogXML.log(Level.ERROR, () -> "Impossible to access to the function " + m.getName() + " of the class "
						+ m.getDeclaringClass()
						+ INNER_BUG_STRING, e);
				System.exit(-1);
				return null;
			} else
				throw e;
		} finally {
			if (!accessible)
				m.setAccessible(false);
		}
	}

	public static Method getMethod(final Class<?> c, final String method_name, final Class<?>... parameters) {
		try {
			return getMethod(true, c, method_name, parameters);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public static Method getMethod(boolean exitOnFail, final Class<?> clazz, final String methodName, final Class<?>... parameters) throws NoSuchMethodException {
		if (clazz==null)
			throw new NullPointerException();
		try {
			for (Class<?> superClass = clazz; superClass != null && superClass != Object.class; superClass = superClass.getSuperclass()) {
				try {
					return superClass.getDeclaredMethod(methodName);
				}
				catch (NoSuchMethodException ignored)
				{

				}
			}
			throw new NoSuchMethodException("No such method: " + clazz.getName() + '.' + methodName);
		} catch (SecurityException | NoSuchMethodException e) {
			if (exitOnFail) {
				FlexiLogXML.log(Level.ERROR, () -> "Impossible to access to the function " + methodName + " of the class "
						+ clazz.getCanonicalName()
						+ INNER_BUG_STRING, e);
				System.exit(-1);
				return null;
			} else
				throw e;
		}
	}

	private static String getAccessorName(String prefix, String fieldName) {
		return prefix + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
	}

	public static String getGetterName(String fieldName) {
		return getAccessorName("get", fieldName);
	}

	public static String getSetterName(String fieldName) {
		return getAccessorName("set", fieldName);
	}

	public static Method getGetterMethod(final Class<?> clazz, final String fieldName) {
		return getMethod(clazz, getGetterName(fieldName));
	}

	public static Method getGetterMethod(boolean exitOnFail, final Class<?> clazz, final String fieldName) throws NoSuchMethodException {
		return getAccessorMethod(exitOnFail, "get", clazz, fieldName);
	}

	public static Method getSetterMethod(final Class<?> clazz, final String fieldName) {
		try {
			return getSetterMethod(true, clazz, fieldName);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public static Method getSetterMethod(boolean exitOnFail, final Class<?> clazz, final String fieldName) throws NoSuchMethodException {
		return getAccessorMethod(exitOnFail, "set", clazz, fieldName);
	}

	private static Method getAccessorMethod(boolean exitOnFail, String prefix, final Class<?> clazz, final String fieldName) throws NoSuchMethodException {
		if (clazz == null)
			throw new NullPointerException();
		if (fieldName == null)
			throw new NullPointerException();
		if (fieldName.isEmpty())
			throw new IllegalArgumentException();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith(prefix) && method.getParameterTypes().length == 1) {
				if (methodName.length() == fieldName.length() + 3) {
					boolean ok = true;
					for (int i = 0; i < methodName.length(); i++) {
						if (methodName.charAt(i + 3) != fieldName.charAt(i)) {
							ok = false;
							break;
						}
					}
					if (ok)
						return method;
				}
			}
		}
		Supplier<String> sup = () -> "Impossible to access to the method " + getAccessorName(prefix, fieldName) + " of the class "
				+ clazz.getCanonicalName() + INNER_BUG_STRING;
		if (exitOnFail) {
			FlexiLogXML.log(Level.ERROR, sup);
			System.exit(-1);
			return null;
		} else
			throw new NoSuchMethodException(sup.get());

	}

	@SuppressWarnings("unchecked")
	public static <T> T getValue(Field field, Object instance) throws IllegalAccessException {
		boolean accessible = canAccess(field, instance);
		if (!accessible)
			field.setAccessible(true);
		try {
			return (T) field.get(instance);
		} finally {
			if (!accessible)
				field.setAccessible(false);
		}
	}

	public static void setValue(Field field, Object instance, Object value) throws IllegalAccessException {
		boolean accessible = canAccess(field, instance);
		if (!accessible)
			field.setAccessible(true);
		try {
			field.set(instance, value);
		} finally {
			if (!accessible)
				field.setAccessible(false);
		}
	}

	public static Field getField(final Class<?> c, final String field_name) {
		try {
			return getField(true, c, field_name);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	public static Field getField(boolean exitOnFail, final Class<?> clazz, final String fieldName) throws NoSuchFieldException {
		if (clazz == null)
			throw new NullPointerException();
		try {
			for (Class<?> superClass = clazz; superClass != null && superClass != Object.class; superClass = superClass.getSuperclass()) {
				try {
					return superClass.getDeclaredField(fieldName);
				}
				catch (NoSuchFieldException ignored)
				{

				}
			}
			throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + fieldName);
		} catch (SecurityException | NoSuchFieldException e) {
			if (exitOnFail) {
				FlexiLogXML.log(Level.ERROR, () -> "Impossible to access to the field " + fieldName + " of the class "
						+ clazz.getCanonicalName()
						+ INNER_BUG_STRING, e);
				System.exit(-1);
				return null;
			} else
				throw e;
		}
	}

	@FunctionalInterface
	public interface F<R, AO extends AccessibleObject>
	{
		R execute(AO accessibleObject) throws DMIOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,NoSuchMethodException, SecurityException;
	}
	public static <R, AO extends AccessibleObject> R executeIntoAccessibleContextWithInvocationsExceptions(AO accessibleObject, Object instance, F<R, AO> f) throws DMIOException, InvocationTargetException, InstantiationException, IllegalAccessException,NoSuchMethodException, SecurityException {
		boolean accessible=canAccess(accessibleObject, instance);
		if (!accessible)
			accessibleObject.setAccessible(true);
		try {
			return f.execute(accessibleObject);
		}
		finally {
			if (!accessible)
				accessibleObject.setAccessible(false);
		}
	}
	public static <R, AO extends Constructor<?>> R executeIntoAccessibleContext(AO accessibleObject, F<R, AO> f) throws DMIOException {
		return executeIntoAccessibleContext(accessibleObject, null, f);
	}
	public static <R, AO extends AccessibleObject> R executeIntoAccessibleContext(AO accessibleObject, Object instance, F<R, AO> f) throws DMIOException {
		try {
			return executeIntoAccessibleContextWithInvocationsExceptions(accessibleObject, instance, f);
		}
		catch (InvocationTargetException | InstantiationException e) {
			Throwable t=e.getCause();
			if (t instanceof Error)
				throw (Error)t;
			else if (t instanceof Exception)
				throw DMIOException.getDMIOException((Exception) t);
			else
				throw new DMIOException(t);
		}
		catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException e) {
			throw DMIOException.getDMIOException(e);
		}
	}

	public static <E> Constructor<E> getConstructor(final Class<E> c, final Class<?>... parameters) {
		try {
			return getConstructor(true, c, parameters);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	public static <E> Constructor<E> getConstructor(boolean exitOnFail, final Class<E> c, final Class<?>... parameters) throws NoSuchMethodException {

			try {
				return c.getDeclaredConstructor(parameters);
			} catch (SecurityException | NoSuchMethodException e) {
				if (exitOnFail) {
					FlexiLogXML.log(Level.ERROR, () -> "Impossible to access to the constructor of the class " + c.getCanonicalName()
							+ INNER_BUG_STRING, e);
					System.exit(-1);
					return null;
				}
				else
					throw e;
			}

		
	}
	public static Class<?> loadClass(String class_name){
		try {
			return loadClass(true, class_name);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	public static Class<?> loadClass(boolean exitOnFail, String class_name) throws ClassNotFoundException {
		try {
			return UtilClassLoader.getLoader().loadClass(class_name);
		} catch (SecurityException | ClassNotFoundException e) {
			if (exitOnFail) {
				FlexiLogXML.log(Level.ERROR, () -> "Impossible to access to the class " + class_name
						+ INNER_BUG_STRING, e);
				System.exit(-1);
				return null;
			}
			else
				throw e;
		}
	}

	public static ClassLoader getClassLoader() {
		return UtilClassLoader.getLoader();
	}


}
