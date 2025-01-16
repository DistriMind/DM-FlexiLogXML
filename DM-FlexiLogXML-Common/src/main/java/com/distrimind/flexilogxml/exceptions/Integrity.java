package com.distrimind.flexilogxml.exceptions;
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


/**
 * @author Jason Mahdjoub
 * @version 1.1
 * @since Utils 4.4.0
 */
public enum Integrity {
	/**
	 * The data integrity is good.
	 */
	OK,

	/**
	 * Anomalies occur during the data integrity check.
	 */
	FAIL,

	/**
	 * Anomalies of security occur during the data integrity check.
	 */
	FAIL_AND_CANDIDATE_TO_BAN
}
