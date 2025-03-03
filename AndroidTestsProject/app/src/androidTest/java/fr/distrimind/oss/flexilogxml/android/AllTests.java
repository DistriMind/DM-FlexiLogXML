package fr.distrimind.oss.flexilogxml.android;


import fr.distrimind.oss.flexilogxml.common.Tests;

public class AllTests extends fr.distrimind.oss.flexilogxml.common.AllTests {

    public static final int DEFAULT_THREAD_COUNT=12;
    public static Tests getTests()
    {
        return fr.distrimind.oss.flexilogxml.common.AllTests.getTests();
    }
}
