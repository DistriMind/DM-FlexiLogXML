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

package fr.distrimind.oss.flexilogxml;


import fr.distrimind.oss.flexilogxml.log.Level;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class TestNGListener implements InvocationHandler {


    private String getStringDuration(Object result) throws Exception {
        Method getStartMillis = result.getClass().getMethod("getStartMillis");
        Method getEndMillis = result.getClass().getMethod("getEndMillis");
        long duration = (long) getEndMillis.invoke(result) - (long) getStartMillis.invoke(result);
        return " (" + duration + " ms)";
    }

    @Override
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Object result = (args!=null && args.length>0)?args[0]:null;

        switch (methodName) {
            case "onTestStart":
                if (result==null)
                    throw new NullPointerException();
                FlexiLogXML.log(Level.INFO, "Test begin: " + getTestName(result));
                break;
            case "onTestSuccess":
                if (result==null)
                    throw new NullPointerException();
                FlexiLogXML.log(Level.INFO,  "Test success: " + getTestName(result) + getStringDuration(result));
                break;
            case "onTestFailure":
                if (result==null)
                    throw new NullPointerException();
                FlexiLogXML.log(Level.ERROR,  "Test failed: " + getTestName(result) + getStringDuration(result), getThrowable(result));
                break;
            case "onTestSkipped":
                if (result==null)
                    throw new NullPointerException();
                FlexiLogXML.log(Level.WARN,  "Test skipped: " + getTestName(result) + getStringDuration(result));
                break;
            case "onTestFailedButWithinSuccessPercentage":
                if (result==null)
                    throw new NullPointerException();
                FlexiLogXML.log(Level.ERROR,  "Test failed but within percentage: " + getTestName(result), getThrowable(result));
                break;
            case "onTestFailedWithTimeout":
                if (result==null)
                    throw new NullPointerException();
                FlexiLogXML.log(Level.ERROR, "Test failed with timeout: " + getTestName(result) + getStringDuration(result), getThrowable(result));
                break;
            case "equals":
                return proxy==result;
            case "hashCode":
                return System.identityHashCode(proxy);
            case "toString":
                return "TestNGLogger";
            case "isEnabled":
                return true;
            default:
                return null;
        }
        return null;
    }

    private String getTestName(Object result) throws Exception {
        Method getName = result.getClass().getMethod("getName");
        String testName=(String) getName.invoke(result);
        Method getTestClass = result.getClass().getMethod("getTestClass");
        Object testClass=getTestClass.invoke(result);
        Method getTestClassName = testClass.getClass().getMethod("getName");
        String testClassName=(String) getTestClassName.invoke(testClass);
        Method getParameters = result.getClass().getMethod("getParameters");
        Object[] parameters=(Object[]) getParameters.invoke(result);
        return testClassName+"."+testName+" "+ Arrays.toString(parameters);
    }

    private Throwable getThrowable(Object result) throws Exception {
        Method getThrowable = result.getClass().getMethod("getThrowable");
        return (Throwable) getThrowable.invoke(result);
    }

    public static Object createTestListenerProxy() {
        try {
            return createTestListenerProxy(UtilClassLoader.getLoader().loadClass("org.testng.ITestListener", true));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static Object createTestListenerProxy(Class<?> listenerInterface) {
        return Proxy.newProxyInstance(
                UtilClassLoader.getLoader(),
                new Class<?>[]{listenerInterface},
                new TestNGListener()
        );
    }
}