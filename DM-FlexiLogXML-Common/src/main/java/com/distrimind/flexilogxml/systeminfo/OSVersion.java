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

package com.distrimind.flexilogxml.systeminfo;

import com.distrimind.flexilogxml.XMLLib;
import org.slf4j.event.Level;

import java.util.*;
import java.util.regex.Pattern;

class Literals
{
    static final String ANDROID=".*(android).*";
    static final String UNUSED="unused";
}
/**
 * @author Jason Mahdjoub
 * @version 1.1
 * @since Utils 3.17
 */
public enum OSVersion {

    WINDOWS_3_11(".*(win16).*", OS.WINDOWS),
    WINDOWS_95(".*((windows.*95)|(win95)|(windows_95)).*", OS.WINDOWS, WINDOWS_3_11),
    WINDOWS_98(".*((windows.*98)|(win98)).*", OS.WINDOWS, WINDOWS_95, WINDOWS_3_11),
    WINDOWS_NT_4_0(".*((windows.*nt.*4)|(winnt4.0)|(winnt)).*", OS.WINDOWS, WINDOWS_98, WINDOWS_95, WINDOWS_3_11),
    WINDOWS_ME(".*((windows.*me)|(windows.*millennium)).*", OS.WINDOWS, WINDOWS_NT_4_0, WINDOWS_98, WINDOWS_95, WINDOWS_3_11),
    WINDOWS_2000(".*((windows nt 5.0)|(windows.*2000)|(windows.*5.0)).*", OS.WINDOWS, WINDOWS_ME, WINDOWS_NT_4_0, WINDOWS_98, WINDOWS_95, WINDOWS_3_11),
    WINDOWS_XP(".*((windows nt 5.1)|(windows.*xp)|(windows.*5.1)).*", OS.WINDOWS, WINDOWS_2000, WINDOWS_ME, WINDOWS_NT_4_0, WINDOWS_98, WINDOWS_95, WINDOWS_3_11),
    WINDOWS_SERVER_2003(".*((windows nt 5.2)|(windows.*5.2)|(windows.*2003)).*", OS.WINDOWS, WINDOWS_XP, WINDOWS_2000, WINDOWS_ME, WINDOWS_NT_4_0, WINDOWS_98, WINDOWS_95, WINDOWS_3_11),
    WINDOWS_VISTA(".*((windows nt 6.0)|(windows.*6.0)|(windows.*vista)).*", OS.WINDOWS, WINDOWS_SERVER_2003, WINDOWS_XP, WINDOWS_2000, WINDOWS_ME, WINDOWS_NT_4_0, WINDOWS_98, WINDOWS_95,WINDOWS_3_11),
    WINDOWS_7(".*((windows nt 6.1)|(windows.*7)|(windows.*6.1)).*", OS.WINDOWS, WINDOWS_VISTA, WINDOWS_SERVER_2003, WINDOWS_XP, WINDOWS_2000, WINDOWS_ME, WINDOWS_NT_4_0, WINDOWS_98, WINDOWS_95, WINDOWS_3_11),
    WINDOWS_8(".*((windows nt 6.2)|(windows.*8)|(windows.*6.2)).*", OS.WINDOWS, WINDOWS_7, WINDOWS_VISTA, WINDOWS_SERVER_2003, WINDOWS_XP, WINDOWS_2000, WINDOWS_ME, WINDOWS_NT_4_0, WINDOWS_98, WINDOWS_95, WINDOWS_3_11),
    WINDOWS_10(".*((windows nt 10.0)|(windows.*10)).*", OS.WINDOWS, WINDOWS_8, WINDOWS_7, WINDOWS_VISTA, WINDOWS_SERVER_2003, WINDOWS_XP, WINDOWS_2000, WINDOWS_ME,WINDOWS_NT_4_0, WINDOWS_98,WINDOWS_95, WINDOWS_3_11),
    WINDOWS_UNKNOWN(".*((windows)|(win)).*", OS.WINDOWS),
    OPEN_BSD(".*openbsd.*", OS.OPEN_BSD),
    SUN_OS(".*(sunos).*", OS.SUN_OS),
    Ubuntu( ".*(ubuntu).*",OS.LINUX),
    LINUX( ".*((linux)|(x11)).*", OS.LINUX),
    IOS(".*((iphone)|(ipad)|(ios)).*", OS.IOS),
    MAC_OS_X_10_7( ".*((mac_powerPC)|(macintosh)|(mac)).*10.7.*",OS.MAC_OS_X),
    MAC_OS_X_10_8( ".*((mac_powerPC)|(macintosh)|(mac)).*10.8.*",OS.MAC_OS_X, MAC_OS_X_10_7),
    MAC_OS_X_10_9( ".*((mac_powerPC)|(macintosh)|(mac)).*10.9.*",OS.MAC_OS_X, MAC_OS_X_10_7, MAC_OS_X_10_8),
    MAC_OS_X_10_10( ".*((mac_powerPC)|(macintosh)|(mac)).*10.10.*",OS.MAC_OS_X, MAC_OS_X_10_7, MAC_OS_X_10_8, MAC_OS_X_10_9),
    MAC_OS_X_10_11( ".*((mac_powerPC)|(macintosh)|(mac)).*10.11.*",OS.MAC_OS_X, MAC_OS_X_10_7, MAC_OS_X_10_8, MAC_OS_X_10_9, MAC_OS_X_10_10),
    MAC_OS_X_10_12( ".*((mac_powerPC)|(macintosh)|(mac)).*10.12.*",OS.MAC_OS_X, MAC_OS_X_10_7, MAC_OS_X_10_8, MAC_OS_X_10_9, MAC_OS_X_10_10, MAC_OS_X_10_11),
    MAC_OS_X_10_13( ".*((mac_powerPC)|(macintosh)|(mac)).*10.13.*",OS.MAC_OS_X, MAC_OS_X_10_7, MAC_OS_X_10_8, MAC_OS_X_10_9, MAC_OS_X_10_10, MAC_OS_X_10_11, MAC_OS_X_10_12),
    MAC_OS_X_10_14( ".*((mac_powerPC)|(macintosh)|(mac)).*10.14.*",OS.MAC_OS_X, MAC_OS_X_10_7, MAC_OS_X_10_8, MAC_OS_X_10_9, MAC_OS_X_10_10, MAC_OS_X_10_11, MAC_OS_X_10_12, MAC_OS_X_10_13),
    MAC_OS_X_10_15( ".*((mac_powerPC)|(macintosh)|(mac)).*10.15.*",OS.MAC_OS_X, MAC_OS_X_10_7, MAC_OS_X_10_8, MAC_OS_X_10_9, MAC_OS_X_10_10, MAC_OS_X_10_11, MAC_OS_X_10_12, MAC_OS_X_10_13, MAC_OS_X_10_14),
    MAC_OS_X_UNKNOWN( ".*((mac_powerPC)|(macintosh)|(mac)).*",OS.MAC_OS_X),
    QNX(".*(qnx).*",OS.QNX),
    BeOS(".*(beos).*",OS.BEOS),
    OS_2(".*(os/2).*", OS.OS_2),
    ANDROID_1_BASE(Literals.ANDROID,OS.ANDROID),
    ANDROID_2_BASE_1_1(Literals.ANDROID,OS.ANDROID),
    ANDROID_3_CUPCAKE(Literals.ANDROID,OS.ANDROID),
    ANDROID_4_DONUT(Literals.ANDROID,OS.ANDROID),
    ANDROID_5_ECLAIR(Literals.ANDROID,OS.ANDROID),
    ANDROID_6_ECLAIR_0_1(Literals.ANDROID,OS.ANDROID),
    ANDROID_7_ECLAIR_MR1(Literals.ANDROID,OS.ANDROID),
    ANDROID_8_FROYO(Literals.ANDROID,OS.ANDROID),
    ANDROID_9_GINGERBREAD(Literals.ANDROID,OS.ANDROID),
    ANDROID_10_GINGERBREAD_MR1(Literals.ANDROID,OS.ANDROID),
    ANDROID_11_HONEYCOMB(Literals.ANDROID,OS.ANDROID),
    ANDROID_12_HONEYCOMB_MR1(Literals.ANDROID,OS.ANDROID),
    ANDROID_13_HONEYCOMB_MR2(Literals.ANDROID,OS.ANDROID),
    ANDROID_14_ICE_SCREAM_SANDWICH(Literals.ANDROID,OS.ANDROID),
    ANDROID_15_ICE_SCREAM_SANDWICH_MR1(Literals.ANDROID,OS.ANDROID),
    ANDROID_16_JELLY_BEAN(Literals.ANDROID,OS.ANDROID),
    ANDROID_17_JELLY_BEAN_MR1(Literals.ANDROID,OS.ANDROID),
    ANDROID_18_JELLY_BEAN_MR2(Literals.ANDROID,OS.ANDROID),
    ANDROID_19_KITKAT(Literals.ANDROID,OS.ANDROID),
    ANDROID_20_KITKAT_WATCH(Literals.ANDROID,OS.ANDROID),
    ANDROID_21_LOLLIPOP(Literals.ANDROID,OS.ANDROID),
    ANDROID_22_LOLLIPOP_MR1(Literals.ANDROID,OS.ANDROID),
    ANDROID_23_M(Literals.ANDROID,OS.ANDROID),
    ANDROID_24_N(Literals.ANDROID,OS.ANDROID),
    ANDROID_25_N_MR1(Literals.ANDROID,OS.ANDROID),
    ANDROID_26_O(Literals.ANDROID,OS.ANDROID),
    ANDROID_27_O_MR1(Literals.ANDROID,OS.ANDROID),
    ANDROID_28_P(Literals.ANDROID,OS.ANDROID),
    ANDROID_29_Q(Literals.ANDROID,OS.ANDROID),
    ANDROID_30_R(Literals.ANDROID,OS.ANDROID),
    ANDROID_31_S(Literals.ANDROID,OS.ANDROID),
    ANDROID_32_Sv2(Literals.ANDROID,OS.ANDROID),
    ANDROID_33_Tiramisu(Literals.ANDROID,OS.ANDROID),
    ANDROID_34_UpsideDownCake(Literals.ANDROID,OS.ANDROID),
    ANDROID_35(Literals.ANDROID,OS.ANDROID),
    ANDROID_UNKNOWN(Literals.ANDROID,OS.ANDROID),
    SEARCH_BOT_NUHK(".*(nuhk).*",OS.SEARCH_BOT),
    SEARCH_BOT_GOOGLEBOT(".*(googlebot).*",OS.SEARCH_BOT),
    SEARCH_BOT_YAMMYBOT(".*(yammybot).*",OS.SEARCH_BOT),
    SEARCH_BOT_OPENBOT(".*(openbot).*",OS.SEARCH_BOT),
    SEARCH_BOT_SLURP(".*(slurp).*",OS.SEARCH_BOT),
    SEARCH_BOT_MSNBOT(".*(msnbot).*",OS.SEARCH_BOT),
    SEARCH_BOT_ASK_JEEVES_TEOMA(".*(ask jeeves/teoma).*",OS.SEARCH_BOT),
    SEARCH_BOT_ASK_QWANT(".*(qwant).*",OS.SEARCH_BOT),
    FREE_BSD_UNKNWON(".*(freebsd).*",OS.FREE_BSD);


    private final Pattern pattern;
    private final OS os;
    private final List<OSVersion> compatibleVersions;

    @SuppressWarnings("PMD")
    OSVersion(String pattern, OS os, OSVersion... compatibleVersions) {
        this.pattern = Pattern.compile(pattern);
        this.os = os;
        this.compatibleVersions = Collections.unmodifiableList(Arrays.asList(compatibleVersions));
    }

    public static OSVersion getFrom(String userAgent) {
        for (OSVersion version : OSVersion.values()) {
            if (version.pattern.matcher(userAgent.toLowerCase(Locale.getDefault())).matches())
                return version;
        }
        return null;
    }

    public OS getOS() {
        return os;
    }

    @SuppressWarnings(Literals.UNUSED)
    public List<OSVersion> getCompatibleVersions() {
        return compatibleVersions;
    }




    static private final OSVersion currentOS;
    static final String OS_VERSION = (System.getProperty("os.name") + " " + System.getProperty("os.version")).toLowerCase(Locale.getDefault());
    static
    {
        OSVersion v=null;
        if (OS.isAndroid()) {
            for (OSVersion osv:OSVersion.values())
            {
                if (osv.getOS()==OS.ANDROID && osv.name().contains("_"+OS.getAndroidVersionInt()+"_"))
                {
                    v=osv;
                }
            }
            if (v==null)
                v=ANDROID_UNKNOWN;
        }
        else {
            for (OS os : OS.values()) {
                if (os.pattern.matcher(OS.OSName).matches()) {
                    v=OSVersion.getFrom(OS_VERSION);
                    break;
                }
            }
        }
        currentOS=v;

    }

    public static OSVersion getCurrentOSVersion()
    {
        return currentOS;
    }

    @SuppressWarnings(Literals.UNUSED)
    public List<OSVersion> getLowerVersions()
    {
        List<OSVersion> res=new ArrayList<>();
        for (OSVersion v : OSVersion.values())
            if (v.getOS()==this.getOS())
                if (v.ordinal()<this.ordinal())
                    res.add(v);
        return res;
    }

    @SuppressWarnings(Literals.UNUSED)
    public List<OSVersion> getLowerOrEqualsVersions()
    {
        List<OSVersion> res=new ArrayList<>();
        for (OSVersion v : OSVersion.values())
            if (v.getOS()==this.getOS())
                if (v.ordinal()<=this.ordinal())
                    res.add(v);
        return res;
    }

    @SuppressWarnings(Literals.UNUSED)
    public List<OSVersion> getGreaterVersions()
    {
        List<OSVersion> res=new ArrayList<>();
        for (OSVersion v : OSVersion.values())
            if (v.getOS()==this.getOS())
                if (v.ordinal()>this.ordinal())
                    res.add(v);
        return res;
    }

    @SuppressWarnings(Literals.UNUSED)
    public List<OSVersion> getGreaterOrEqualVersions()
    {
        List<OSVersion> res=new ArrayList<>();
        for (OSVersion v : OSVersion.values())
            if (v.getOS()==this.getOS())
                if (v.ordinal()>=this.ordinal())
                    res.add(v);
        return res;
    }
    
    public static void main(String[] args)
    {
        XMLLib.log(Level.INFO, () -> OS_VERSION);
        XMLLib.log(Level.INFO, () -> getCurrentOSVersion().toString());
        XMLLib.log(Level.INFO, () -> getCurrentOSVersion().getOS().toString());
    }
}
