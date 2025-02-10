package com.distrimind.flexilogxml.exceptions;/*
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


import java.io.IOException;
import java.util.function.BiFunction;


/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 1.0
 */
public class DMIOException  extends IOException implements IntegrityException {
	private static final long serialVersionUID = 0L;
	private final Integrity integrity;
	public DMIOException() {
		this((Integrity) null);
	}

	public DMIOException(String message) {
		this(null, message);
	}

	public DMIOException(String message, Throwable cause) {
		this(null, message, cause);
	}

	public DMIOException(Throwable cause) {
		this((Integrity) null, cause);
	}
	@SuppressWarnings("PMD")
	public DMIOException(Integrity integrity) {
		this.integrity=getIntegrity(integrity, null);
	}
	@SuppressWarnings("PMD")
	public DMIOException(Integrity integrity, String message) {
		super(integrity==null?message:(integrity+" : "+message));
		this.integrity=getIntegrity(integrity, null);
	}
	@SuppressWarnings("PMD")
	public DMIOException(Integrity integrity, String message, Throwable cause) {
		super(integrity==null?message:(integrity+" : "+message), cause);
		this.integrity=getIntegrity(integrity, cause);
	}
	@SuppressWarnings("PMD")
	public DMIOException(Integrity integrity, Throwable cause) {
		super(cause);
		this.integrity=getIntegrity(integrity, cause);
	}

	private static Integrity getIntegrity(Integrity integrity, Throwable cause)
	{
		return integrity==null?((cause instanceof IntegrityException)?((IntegrityException) cause).getIntegrity():Integrity.FAIL):integrity;
	}
	@Override
	public Integrity getIntegrity() {
		return integrity;
	}
	private static final String xmlStreamExceptionName="javax.xml.stream.XMLStreamException";
	private static BiFunction<Exception, Integrity, DMIOException> specializedFunction=null;

	public static void setSpecializedFunction(BiFunction<Exception, Integrity, DMIOException> specializedFunction)
	{
		DMIOException.specializedFunction=specializedFunction;
	}

	public static void addSpecializedFunction(BiFunction<Exception, Integrity, DMIOException> specializedFunction)
	{
		if (specializedFunction==null)
			throw new NullPointerException();
		if (DMIOException.specializedFunction==null)
			DMIOException.specializedFunction=specializedFunction;
		else {
			final BiFunction<Exception, Integrity, DMIOException> oldDpecializedFunction=DMIOException.specializedFunction;
			DMIOException.specializedFunction = (e, i) -> {
				DMIOException r = oldDpecializedFunction.apply(e, i);
				if (r == null)
					r = specializedFunction.apply(e, i);
				return r;
			};
		}
	}

	@SuppressWarnings("unchecked")
	protected static <E extends DMIOException> E getDMIOException(Integrity integrity, Exception e, BiFunction<Throwable, Integrity, E> f, Class<E> eClass)
	{
		if (e==null)
			throw new NullPointerException();
		DMIOException res=null;
		if (eClass.isAssignableFrom(e.getClass()))
			return (E)e;
		else if (xmlStreamExceptionName.equals(e.getClass().getName()))
			res=new XMLStreamException(integrity, e.getMessage(), e);
		else if (specializedFunction!=null)
			res=specializedFunction.apply(e, integrity);
		if (res==null) {
			if (e instanceof IOException)
				res = new DMIOException(integrity, e);
			else {
				return f.apply(e, integrity);
			}
		}
		if (eClass.isAssignableFrom(res.getClass()))
			return (E)res;
		else {
			Throwable t;
			Throwable r=res;
			while ((t=r.getCause())!=null)
			{
				r = t;
				if (!(t instanceof DMIOException)) {
					break;
				}
			}
			return f.apply(r, integrity);
		}

	}
	public static DMIOException getDMIOException(Exception e)
	{
		return getDMIOException(null, e);
	}
	public static DMIOException getDMIOException(Integrity integrity, Exception e)
	{
		return getDMIOException(integrity, e, (e2, i) -> new DMIOException(i, e2), DMIOException.class);
	}

}
