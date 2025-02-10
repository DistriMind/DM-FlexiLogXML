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


package com.distrimind.flexilogxml.systeminfo;

import com.distrimind.flexilogxml.UtilClassLoader;
import com.distrimind.flexilogxml.FlexiLogXML;
import org.slf4j.event.Level;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.distrimind.flexilogxml.systeminfo.OSVersion.getCurrentOSVersion;

/**
 * Set of functions giving information about the current running OS
 * 
 * @author Jason Mahdjoub
 * @version 1.0
 * @since DM-FlexiLogXML 1.0
 *
 */
@SuppressWarnings("PMD")
public enum OS {
	LINUX(".*((linux)|(x11)).*"),
    OPEN_BSD(".*(openbsd).*"),
    SUN_OS(".*sunos.*"),
    BEOS(".*beos.*"),
    QNX(".*qnx.*"),
    IOS(".*((iphone)|(ipad)|(ios)).*"),
	MAC_OS_X(".*mac.*"),
    OS_2(".*os/2.*"),
	WINDOWS(".*win.*"),
	ANDROID(".*(android).*"),
    SEARCH_BOT(".*((nuhk)|(googlebot)|(yammybot)|(openbot)|(slurp)|(mnsbot)|(ssk jeeves/teoma)).*"),
	FREE_BSD(".*freebsd.*");

	final Pattern pattern;
	private static final String UNUSED="unused";

	OS(String regex)
	{
		this.pattern = Pattern.compile(regex);
	}

	@SuppressWarnings(UNUSED)
    public static OS getFrom(String userAgent) {
		for (OS os : OS.values()) {
			if (os.pattern.matcher(userAgent.toLowerCase(FlexiLogXML.getLocale())).matches())
				return os;
		}
		return null;
	}

	static final String OSName = System.getProperty("os.name").toLowerCase(Locale.getDefault());


	

	
	private static final double currentJREVersion;
	private static final byte currentJREVersionByte;
	private static final Integer androidVersion;

	static
	{
		Integer av=null;
		String s=System.getProperty("http.agent");
		if (s!=null) {
			if (s.toLowerCase(Locale.ENGLISH).contains(" android "))
			{
				try {
					UtilClassLoader.getLoader().loadClass("android.os.Build$VERSION");
					Class<?> versionClass= UtilClassLoader.getLoader().loadClass("android.os.Build$VERSION");
					av=(Integer)versionClass.getDeclaredField("SDK_INT").get(null);
				} catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException ignored) {

				}
			}
		}
		androidVersion=av;
		double d;
		if (androidVersion==null) {
			try {
				d = Double.parseDouble(System.getProperty("java.specification.version"));
			} catch (Throwable t) {
				d = 0.0;
			}
		}
		else
		{
			if (androidVersion<34)
				d=11.0;
			else
				d=17.0;
		}
		
		currentJREVersion=d;
		currentJREVersionByte=currentJREVersion>2.0?(byte)currentJREVersion:(byte)((currentJREVersion-1.0)*10.0);
	}

	public static double getCurrentJREVersionDouble()
	{
		return currentJREVersion;
	}
	public static byte getCurrentJREVersionByte()
	{
		return currentJREVersionByte;
	}
	


    @SuppressWarnings(UNUSED)
    public boolean isUnix() {

        return (OSName.contains("nix") || OSName.contains("nux") || OSName.indexOf("aix") > 0);

    }





	static boolean isAndroid()
	{
		return androidVersion!=null;
	}
	static int getAndroidVersionInt()
	{
		return androidVersion;
	}

	
	public static String getJVMLocation()
	{
		if (getCurrentOSVersion()!=null && getCurrentOSVersion().getOS()==WINDOWS) {
		    return System.getProperties().getProperty("java.home") + File.separator + "bin" + File.separator + "java.exe";
		} 
		else if (isAndroid())
			return "java";
		else {
		    return System.getProperties().getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		}
	}
	
	private static volatile Boolean aesNIAcceleration=null;
	
	public static boolean supportAESIntrinsicsAcceleration() 
	{
		if (aesNIAcceleration==null)
		{
			try
			{
				Process p=Runtime.getRuntime().exec(new String[]{getJVMLocation(), "-XX:+PrintFlagsFinal", "-version"});
				
				try(InputStream is=p.getInputStream();InputStreamReader isr=new InputStreamReader(is); BufferedReader br=new BufferedReader(isr))
				{
					String line=br.readLine();
					while (line!=null)
					{
						line=line.toLowerCase(Locale.ENGLISH);
						if (line.contains("useaesintrinsics"))
						{
							aesNIAcceleration= line.contains("true");
						}
						line=br.readLine();
					}
				}
				p.destroy();
			}
			catch(IOException e)
			{
				FlexiLogXML.log(Level.WARN, e);
			}
			if (aesNIAcceleration==null)
				aesNIAcceleration= Boolean.FALSE;
		}
		return aesNIAcceleration;
		
	}

	@SuppressWarnings(UNUSED)
    public boolean SIPrefixAreUnderstoodAsBinaryPrefixForByteMultiples()
	{
		return this == WINDOWS || (this == MAC_OS_X && Double.parseDouble(OSVersion.OS_VERSION) < 10.1);
	}

	@SuppressWarnings(UNUSED)
    List<OSVersion> getVersions()
    {
        List<OSVersion> res=new ArrayList<>();
        for (OSVersion v : OSVersion.values())
            if (v.getOS()==this)
                res.add(v);
        return res;
    }

    public static void main(String[] args)
	{
		FlexiLogXML.log(Level.INFO, () -> Double.toString(getCurrentJREVersionDouble()));
		FlexiLogXML.log(Level.INFO, () -> Byte.toString(getCurrentJREVersionByte()));
		FlexiLogXML.log(Level.INFO, () -> getCurrentOSVersion().toString());

	}
}
