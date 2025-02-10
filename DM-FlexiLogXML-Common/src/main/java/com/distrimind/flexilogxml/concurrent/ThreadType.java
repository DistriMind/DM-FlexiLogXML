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
package com.distrimind.flexilogxml.concurrent;

import com.distrimind.flexilogxml.UtilClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLog-XML 1.3.0
 */
public enum ThreadType {
	/**
	 * Represents a platform thread.
	 */
	PLATFORM_THREAD,
	/**
	 * Represents a java virtual thread (Java 19 and over).
	 */
	VIRTUAL_THREAD,

	VIRTUAL_THREAD_IF_AVAILABLE;
	/**
	 * Represents a java virtual thread (Java 19 and over).
	 * <br>
	 * If virtual threads are not available with the current Java Runtime Environment, the virtual threads are
	 * replaced with platform threads.
	 */
	public ThreadFactory newThreadFactoryInstance()
	{

		switch (this)
		{
			case PLATFORM_THREAD:
				return platformThreadFactory();
			case VIRTUAL_THREAD:
				return virtualThreadFactory(false);
			case VIRTUAL_THREAD_IF_AVAILABLE:
				return virtualThreadFactory(true);
			default:
				throw new IllegalAccessError();
		}
	}
	/**
	 * Represents a java virtual thread (Java 19 and over).
	 * <br>
	 * If virtual threads are not available with the current Java Runtime Environment, the virtual threads are
	 * replaced with platform threads.
	 */
	public ThreadFactory newThreadFactoryInstance(ThreadGroup threadGroup, String name, int stackSize, int priority, boolean daemon)
	{

		switch (this)
		{
			case PLATFORM_THREAD:
				return platformThreadFactory(threadGroup, name, stackSize, priority, daemon);
			case VIRTUAL_THREAD:
				return virtualThreadFactory(threadGroup, name, stackSize, priority, daemon, false);
			case VIRTUAL_THREAD_IF_AVAILABLE:
				return virtualThreadFactory(threadGroup, name, stackSize, priority, daemon, true);
			default:
				throw new IllegalAccessError();
		}
	}
	public Thread startThread(Runnable runnable)
	{
		return startThreadImpl(null, runnable, null, 0);
	}
	public Thread startThread(ThreadGroup group, Runnable runnable, String name, long stackSize)
	{
		if (name==null)
			throw new NullPointerException();
		return startThreadImpl(group, runnable, name, stackSize);
	}
	public Thread startThread(Runnable runnable, String name)
	{
		if (name==null)
			throw new NullPointerException();
		return startThreadImpl(null, runnable, name, 0);
	}
	private Thread startThreadImpl(ThreadGroup group, Runnable runnable, String name, long stackSize)
	{
		if (runnable==null)
			throw new NullPointerException();
		switch (this)
		{
			case PLATFORM_THREAD:
				return startPlatformThread(group, runnable, name, stackSize);
			case VIRTUAL_THREAD:
				return startVirtualThread(group, runnable, name, stackSize, false);
			case VIRTUAL_THREAD_IF_AVAILABLE:
				return startVirtualThread(group, runnable, name,stackSize, true);
			default:
				throw new IllegalAccessError();
		}
	}

	private static final Method ofVirtual;
	private static final Method ofPlatform;
	private static final Method factoryM;
	private static final Method ofPlatformDaemon;
	private static final Method ofPlatformPriority;
	private static final Method ofPlatformStackSize;
	private static final Method ofPlatformGroup;
	private static final Method ofPlatformName;
	private static final Method startVirtualThreadMethod;
	private static final boolean supportedInPreviewMode;
	static
	{
		Method mv;
		Method mp;
		Method fm;
		Method opda;
		Method oppr;
		Method opst;
		Method opgr;
		Method opn;
		Method svt;
		boolean b=false;
		try {
			mv=Thread.class.getMethod("ofVirtual");
			mp=Thread.class.getMethod("ofPlatform");
			Class<?> classBuilder= UtilClassLoader.getLoader().loadClass("java.lang.Thread$Builder");
			fm=classBuilder.getMethod("factory");
			Class<?> platformClassBuilder= UtilClassLoader.getLoader().loadClass("java.lang.Thread$Builder$OfPlatform");
			opda=platformClassBuilder.getMethod("daemon", boolean.class);
			oppr=platformClassBuilder.getMethod("priority", int.class);
			opst=platformClassBuilder.getMethod("stackSize", long.class);
			opgr=platformClassBuilder.getMethod("group", ThreadGroup.class);
			opn=classBuilder.getMethod("name", String.class, long.class);
			svt=Thread.class.getMethod("startVirtualThread", Runnable.class);
			b=true;
			//if (OS.getCurrentJREVersionByte()<=20)
			{
				Object o = mv.invoke(null);
				fm.invoke(o);
			}

		} catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
			mv=null;
			mp=null;
			fm=null;
			svt=null;
			opda=null;
			oppr=null;
			opst=null;
			opgr=null;
			opn=null;
		}
		ofVirtual=mv;
		ofPlatform=mp;
		factoryM=fm;
		ofPlatformDaemon=opda;
		ofPlatformGroup=opgr;
		ofPlatformName=opn;
		ofPlatformPriority=oppr;
		ofPlatformStackSize=opst;
		startVirtualThreadMethod =svt;
		supportedInPreviewMode=b;
	}
	private static ThreadFactory platformThreadFactory()
	{
		if (factoryM==null)
			return Executors.defaultThreadFactory();
		else
		{
			try {
				Object o=ofPlatform.invoke(null);
				return (ThreadFactory) factoryM.invoke(o);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}
	private static class DefaultThreadFactory implements ThreadFactory
	{
		private final ThreadGroup group;
		private final AtomicInteger threadNumber;
		private final String namePrefix;
		private final int stackSize;
		private final int priority;
		private final boolean daemon;

		private DefaultThreadFactory(ThreadGroup group, String namePrefix, int stackSize, int priority, boolean daemon) {
			if (priority > Thread.MAX_PRIORITY || priority < Thread.MIN_PRIORITY) {
				throw new IllegalArgumentException();
			}

			this.group=group;
			this.namePrefix=namePrefix;
			this.stackSize=stackSize;
			this.priority=priority;
			this.daemon=daemon;
			if (this.namePrefix==null)
				threadNumber=null;
			else
				threadNumber=new AtomicInteger(1);
		}
		@Override
		public Thread newThread(Runnable r) {
			Thread t;
			if (namePrefix==null)
				t=new Thread(group, r);
			else {
				assert threadNumber != null;
				t=new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), stackSize);
			}
			if (t.isDaemon() && !daemon)
				t.setDaemon(false);
			else if (t.isDaemon() && daemon)
				t.setDaemon(true);
			if (t.getPriority() != priority)
				t.setPriority(priority);

			return t;
		}
	}

	private static ThreadFactory platformThreadFactory(ThreadGroup threadGroup, String name, int stackSize, int priority, boolean daemon)
	{
		if (priority > Thread.MAX_PRIORITY || priority < Thread.MIN_PRIORITY) {
			throw new IllegalArgumentException();
		}
		if (factoryM==null)
			return new DefaultThreadFactory(threadGroup, name,stackSize, priority, daemon);
		else
		{
			try {
				Object o=ofPlatform.invoke(null);
				if (threadGroup!=null)
					ofPlatformGroup.invoke(o, threadGroup);
				if (name!=null)
					ofPlatformName.invoke(o, name, 1);
				ofPlatformStackSize.invoke(o, stackSize);
				ofPlatformPriority.invoke(o, priority);
				ofPlatformDaemon.invoke(o, daemon);
				return (ThreadFactory) factoryM.invoke(o);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}
	private static ThreadFactory virtualThreadFactory(ThreadGroup threadGroup, String name, int stackSize, int priority, boolean daemon, boolean replaceWithPlatformThreadIfNotAvailable)
	{
		if (factoryM==null)
		{
			if (replaceWithPlatformThreadIfNotAvailable)
				return platformThreadFactory(threadGroup, name, stackSize, priority, daemon);
			else
				throw new UnsupportedClassVersionError("Minimum Java Version must be Java 19 and preview mode is perhaps necessary !");
		}
		else
		{
			if (daemon)
				throw new IllegalArgumentException("Virtual thread cannot be a daemon thread");
			try {
				Object o=ofVirtual.invoke(null);
				ofPlatformName.invoke(o, name, 1);
				return (ThreadFactory) factoryM.invoke(o);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}
	private static ThreadFactory virtualThreadFactory(boolean replaceWithPlatformThreadIfNotAvailable)
	{
		if (factoryM==null)
		{
			if (replaceWithPlatformThreadIfNotAvailable)
				return platformThreadFactory();
			else
				throw new UnsupportedClassVersionError("Minimum Java Version must be Java 19 and preview mode is perhaps necessary !");
		}
		else
		{
			try {
				Object o=ofVirtual.invoke(null);
				return (ThreadFactory) factoryM.invoke(o);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}
	private static final AtomicInteger threadNumber=new AtomicInteger(1);
	private static String generateThreadName()
	{
		return "DM-Thread-"+threadNumber.getAndIncrement();
	}
	private static Thread startPlatformThread(ThreadGroup group, Runnable runnable, String name, long stackSize)
	{
		Thread t;
		t = new Thread(group, runnable, Objects.requireNonNullElseGet(name, ThreadType::generateThreadName), stackSize);
		t.start();
		return t;
	}
	private static Thread startVirtualThread(ThreadGroup group, Runnable runnable, String name, long stackSize, boolean replaceWithPlatformThreadIfNotAvailable)
	{
		if (startVirtualThreadMethod ==null)
		{
			if (replaceWithPlatformThreadIfNotAvailable)
				return startPlatformThread(group, runnable, name, stackSize);
			else
				throw new UnsupportedClassVersionError("Minimum Java Version must be Java 19 and preview mode is perhaps necessary !");
		}
		else
		{
			try {
				Thread t=(Thread) startVirtualThreadMethod.invoke(null, runnable);
				if (name!=null)
					t.setName(name);
				return t;
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public boolean isVirtualIntoTheCurrentJRE()
	{
		return (this==VIRTUAL_THREAD || this==VIRTUAL_THREAD_IF_AVAILABLE) && factoryM!=null;
	}
	public boolean isVirtualIntoTheCurrentJREIfPreviewModeIsEnabled()
	{
		return (this==VIRTUAL_THREAD || this==VIRTUAL_THREAD_IF_AVAILABLE) && supportedInPreviewMode;
	}
}
