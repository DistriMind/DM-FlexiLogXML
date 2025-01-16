package com.distrimind.flexilogxml.systeminfo;
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


import com.distrimind.flexilogxml.FlexiLogXML;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since Utils 5.24.1
 */
public class SystemInfo {

	private final State timingAttackState;
	private final State frequencyAttackMitigationState;
	private final State powerMonitoringAttackMitigationState;

	private static State getState(String _s)
	{
		if (_s!=null)
		{
			String s=_s.toUpperCase(FlexiLogXML.getLocale());
			for (State st : State.values())
			{
				if (st.name().equals(s))
					return st;
			}
		}
		return State.DEFAULT;
	}

	SystemInfo()
	{
		timingAttackState=getState(System.getProperty("timingAttackState"));
		frequencyAttackMitigationState=getState(System.getProperty("frequencyAttackMitigationState"));
		powerMonitoringAttackMitigationState=getState(System.getProperty("powerMonitoringAttackMitigationState"));

	}
	private static final SystemInfo instance=new SystemInfo();
	public static SystemInfo getInstance()
	{
		return instance;
	}
	public boolean isFrequencyAttackPossibleIntoCurrentCPU()
	{
		return true;
	}
	public boolean isPowerMonitoringAttackPossibleIntoCurrentCPU()
	{
		return true;
	}
	public boolean isTimingAttackPossibleIntoCurrentCPU()
	{
		return true;
	}

	public long totalMemory() {
		return Runtime.getRuntime().totalMemory();
	}

	public long usedMemory() {
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}

	public State getFrequencyAttackMitigationState() {
		return frequencyAttackMitigationState;
	}

	public State getPowerMonitoringAttackMitigationState() {
		return powerMonitoringAttackMitigationState;
	}

	public State getTimingAttackMitigationState() {
		return timingAttackState;
	}
}
