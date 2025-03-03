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

import java.util.List;
import java.util.Objects;

public class TestGroup {
    private final String name;
    private final List<Class<?>> tests;

    public TestGroup(String name, List<Class<?>> tests) {
        if (name==null)
            throw new NullPointerException();
        if (name.trim().isEmpty())
            throw new IllegalArgumentException();
        if (tests==null)
            throw new NullPointerException();
        if (tests.isEmpty())
            throw new IllegalArgumentException();
        if (tests.stream().anyMatch(Objects::isNull))
            throw new NullPointerException();
        this.name=name;
        this.tests = List.copyOf(tests);
    }

    public List<Class<?>> getTests() {
        return tests;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (tests.size()==1)
            return tests.iterator().next().getSimpleName();
        else {
            StringBuilder r = new StringBuilder("TestGroup{name='" + name + "', tests={");
            int i=0;
            for (Class<?> c : tests) {
                if (i++>0)
                    r.append(", ");
                r.append(c.getSimpleName());
            }
            r.append("}");
            return r.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestGroup testGroup = (TestGroup) o;
        return Objects.equals(name, testGroup.name) && Objects.equals(tests, testGroup.tests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tests);
    }
}
