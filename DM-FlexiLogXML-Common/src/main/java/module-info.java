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

module fr.distrimind.oss.flexilogxml.common {
	requires org.slf4j;

	exports fr.distrimind.oss.flexilogxml.common;
	exports fr.distrimind.oss.flexilogxml.common.systeminfo;
	exports fr.distrimind.oss.flexilogxml.common.exceptions;
	exports fr.distrimind.oss.flexilogxml.common.xml;
	exports fr.distrimind.oss.flexilogxml.common.log;
	exports fr.distrimind.oss.flexilogxml.common.concurrent;

}