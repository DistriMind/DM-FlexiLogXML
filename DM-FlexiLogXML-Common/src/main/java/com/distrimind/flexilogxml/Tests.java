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

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.event.Level;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Tests {
    public static final int DEFAULT_THREAD_COUNT=24;
    private final List<TestGroup> testsList;
    private final List<Class<?>> flattenedTestClasses;
    private final List<Tests> flattenedTests;
    private int threadCount;

    public static Tests of(Class<?> clazz)
    {
        return of(clazz.getSimpleName(), List.of(clazz));
    }
    public static Tests of(String testsName, Class<?>... classes)
    {
        List<Class<?>> l=new ArrayList<>(classes.length);
        Collections.addAll(l, classes);
        return of(testsName, l);
    }
    public static Tests of(String testsName, List<Class<?>> classes)
    {
        return new Tests(List.of(new TestGroup(testsName, classes)));
    }

    public Tests(List<TestGroup> tests) {
        if (tests==null)
            throw new NullPointerException();
        if (tests.isEmpty())
            throw new IllegalArgumentException();
        if (tests.stream().anyMatch(Objects::isNull))
            throw new IllegalArgumentException();
        this.testsList = List.copyOf(tests);
        this.threadCount=DEFAULT_THREAD_COUNT;
        List<Class<?>> ftc=new ArrayList<>();
        List<Tests> ft=new ArrayList<>();
        for (TestGroup tg : tests) {
            ftc.addAll(tg.getTests());
            for (Class<?> c : tg.getTests()) {
                ft.add(new Tests(this.threadCount, new TestGroup(tg.getName(), List.of(c))));
            }
        }
        this.flattenedTestClasses = Collections.unmodifiableList(ftc);
        this.flattenedTests=Collections.unmodifiableList(ft);
    }
    private Tests(int threadCount, TestGroup testGroup) {
        if (testGroup==null)
            throw new NullPointerException();
        this.testsList = List.of(testGroup);
        this.threadCount=threadCount;
        this.flattenedTestClasses=testGroup.getTests();
        this.flattenedTests=List.of(this);
    }

    public List<TestGroup> getTests() {
        return testsList;
    }

    public List<Class<?>> getFattenedTestClasses()
    {
        return this.flattenedTestClasses;
    }

    public List<Tests> getFlattenedTests() {
        return flattenedTests;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
        for (Tests t : flattenedTests)
            t.threadCount=threadCount;
    }

    private static Class<?> TestNGClass=null;
    private static Class<?> ITestNGListenerInterface =null;
    static Class<?> ITestListenerInterface =null;
    private static Class<?> XmlSuiteClass=null;
    private static Class<?> XmlCLassClass=null;
    private static Class<?> XmlTestClass=null;
    private static Class<?> IReporterClass=null;
    private static Class<?> JunitAssertClass=null;

    private static Method addListenerMethod=null;
    private static Method setTestNGThreadCountMethod =null;
    private static Method setSuiteThreadCountMethod=null;
    private static Method setXmlSuitesMethod=null;
    private static Method setTestNameMethod=null;
    private static Method setClassesMethod=null;
    private static Method runMethod=null;
    private static Method toXmlMethod=null;

    private static Method hasSkipMethod=null;
    private static Method hasFailureWithinSuccessPercentageMethod=null;
    private static Method hasFailureMethod=null;


    private static Method junitAssertFalseMethod=null;

    private static Constructor<?> xmlClassConstructor=null;
    private static Constructor<?> xmlTestConstructor=null;


    static void checkTestNGClassesLoaded()
    {
        if (TestNGClass==null)
        {
            try {
                TestNGClass = UtilClassLoader.getLoader().loadClass("org.testng.TestNG", true);
                ITestNGListenerInterface = UtilClassLoader.getLoader().loadClass("org.testng.ITestNGListener", true);
                ITestListenerInterface = UtilClassLoader.getLoader().loadClass("org.testng.ITestListener", true);
                XmlSuiteClass = UtilClassLoader.getLoader().loadClass("org.testng.xml.XmlSuite", true);
                XmlCLassClass = UtilClassLoader.getLoader().loadClass("org.testng.xml.XmlClass", true);
                XmlTestClass = UtilClassLoader.getLoader().loadClass("org.testng.xml.XmlTest", true);
                IReporterClass = UtilClassLoader.getLoader().loadClass("org.testng.IReporter", true);

                addListenerMethod = TestNGClass.getMethod("addListener", ITestNGListenerInterface);
                setTestNGThreadCountMethod = TestNGClass.getMethod("setThreadCount", int.class);
                setSuiteThreadCountMethod = XmlSuiteClass.getMethod("setThreadCount", int.class);
                setXmlSuitesMethod = TestNGClass.getMethod("setXmlSuites", List.class);
                setTestNameMethod = XmlTestClass.getMethod("setName", String.class);
                setClassesMethod = XmlTestClass.getMethod("setClasses", List.class);
                runMethod = TestNGClass.getMethod("run");
                toXmlMethod = XmlSuiteClass.getMethod("toXml");

                hasSkipMethod = TestNGClass.getMethod("hasSkip");
                hasFailureWithinSuccessPercentageMethod = TestNGClass.getMethod("hasFailureWithinSuccessPercentage");
                hasFailureMethod = TestNGClass.getMethod("hasFailure");

                xmlClassConstructor = XmlCLassClass.getConstructor(Class.class, boolean.class);
                xmlTestConstructor = XmlTestClass.getConstructor(XmlSuiteClass);
            }
            catch (ClassNotFoundException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static void checkJUnitClassesLoaded()
    {
        if (JunitAssertClass==null)
        {
            try {
                JunitAssertClass = UtilClassLoader.getLoader().loadClass("org.junit.Assert", true);

                junitAssertFalseMethod = JunitAssertClass.getMethod("assertFalse", String.class, boolean.class);
            }
            catch (ClassNotFoundException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Object getSuite()
    {
        checkTestNGClassesLoaded();
        try {
            Object suite=XmlSuiteClass.getConstructor().newInstance();
            setSuiteThreadCountMethod.invoke(suite, threadCount);
            for (TestGroup tg : testsList) {
                List<Object> xmlClasses=new ArrayList<>(testsList.size());
                for (Class<?> c : tg.getTests()) {
                    Object xmlClass=xmlClassConstructor.newInstance(c, true);
                    xmlClasses.add(xmlClass);
                }
                Object xmlTest=xmlTestConstructor.newInstance(suite);
                setTestNameMethod.invoke(xmlTest, tg.getName());
                setClassesMethod.invoke(xmlTest, xmlClasses);
            }
            return suite;
        } catch (NoSuchMethodException | IllegalAccessException |
                 InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveTestNGToXML(String outputFilePath) throws DMIOException {
        saveTestNGToXML(new File(outputFilePath));
    }
    public void saveTestNGToXML(File file) throws DMIOException {

        try (FileWriter writer = new FileWriter(file)) {
            Object suite=getSuite();
            writer.write((String)toXmlMethod.invoke(suite));
            writer.write(System.lineSeparator());
            FlexiLogXML.log(Level.INFO, "TestNG instance has been saved to: " + file);
        } catch (IllegalAccessException | IOException e) {
            throw new DMIOException(e);
        }
        catch (InvocationTargetException e)
        {
            throw new DMIOException(e.getCause());
        }
    }

    /** @noinspection ArraysAsListWithZeroOrOneArgument*/
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    public Object getTestNG()
    {
        checkTestNGClassesLoaded();
        try {

            Object testNG=TestNGClass.getConstructor(boolean.class).newInstance(false);
            addListenerMethod.invoke(testNG, TestNGListener.createTestListenerProxy(ITestListenerInterface));
            addListenerMethod.invoke(testNG, Proxy.newProxyInstance(
                    UtilClassLoader.getLoader(),
                    new Class<?>[]{IReporterClass},
                    (proxy, method, args) -> {
                        switch (method.getName()) {
                            case "hashCode":
                                return System.identityHashCode(proxy);
                            case "equals" :
                                if (args != null && args.length == 1)
                                    return proxy==args[0];
                                break;
                            case "toString":
                                return "EmptyReporter";
                            case "isEnabled":
                                return true;
                            default:
                                return null;
                        }
                        return null;
                    }
            ));
            setTestNGThreadCountMethod.invoke(testNG, threadCount);
            Object suite=getSuite();
            setXmlSuitesMethod.invoke(testNG, Arrays.asList(suite));

            return testNG;
        } catch (NoSuchMethodException | IllegalAccessException |
                 InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    public Object runTestNG() throws Throwable {
        Object testNG=getTestNG();
        try {
            runTestNG(testNG);
        }
        catch (InvocationTargetException e)
        {
            throw e.getCause();
        }
        return testNG;
    }
    public void runTestNGWithJunit() throws Throwable
    {
        runTestNGWithJunit(null);
    }
    public void runTestNGWithJunit(final String markerName) throws Throwable
    {
        checkJUnitClassesLoaded();
        Marker oldMarker= FlexiLogXML.getMarker();
        try {
            if (markerName!=null)
                FlexiLogXML.setMarker(MarkerFactory.getMarker(markerName));
            try {
                Object testNG = runTestNG();
                junitAssertFalseMethod.invoke(null, "TestNG method(s) skipped into "+this, hasSkipMethod.invoke(testNG));
                junitAssertFalseMethod.invoke(null, "TestNG method(s) failed into "+this, hasFailureWithinSuccessPercentageMethod.invoke(testNG));
                junitAssertFalseMethod.invoke(null, "TestNG method(s) failed into "+this, hasFailureMethod.invoke(testNG));
            } catch (InvocationTargetException e) {
                FlexiLogXML.log(Level.ERROR, e.getCause());
                throw e.getCause();
            } catch (Exception e) {
                FlexiLogXML.log(Level.ERROR, e);
                throw e;
            }
        }
        finally {
            if (markerName!=null)
                FlexiLogXML.setMarker(oldMarker);
        }
    }
    public void runTestNG(Object testNG) throws Throwable {
        checkTestNGClassesLoaded();
        try {
            runMethod.invoke(testNG);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e)
        {
            throw e.getCause();
        }
    }

    @Override
    public String toString() {
        if (testsList.size()==1) {
            return testsList.iterator().next().toString();
        }
        else {
            return "Tests{" +
                    testsList +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tests tests1 = (Tests) o;
        return threadCount == tests1.threadCount && Objects.equals(testsList, tests1.testsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testsList, threadCount);
    }
    public Collection<Tests[]> getDataForJunitTests()
    {
        List<Tests> ts=getFlattenedTests();
        List<Tests[]> r=new ArrayList<>(ts.size());
        for (Tests t : ts)
            r.add(new Tests[]{t});

        return r;
    }
    public Collection<Tests[]> getDataForJunitTests(int threadCount)
    {
        int oldThreadCount=getThreadCount();
        setThreadCount(threadCount);
        try
        {
            return getDataForJunitTests();
        }
        finally {
            setThreadCount(oldThreadCount);
        }
    }
}
