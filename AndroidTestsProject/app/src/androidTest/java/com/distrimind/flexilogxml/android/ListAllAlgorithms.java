package com.distrimind.flexilogxml.android;

import com.distrimind.flexilogxml.XMLLib;

import org.junit.Test;
import org.slf4j.MarkerFactory;
import org.slf4j.event.Level;

import java.security.Provider;
import java.security.Security;

public class ListAllAlgorithms {
    @Test
    public void listAllAlgorithms()
    {
        XMLLib.setMarker(MarkerFactory.getMarker("TestXMLLib"));
        for (Provider provider : Security.getProviders()) {
            XMLLib.log(Level.INFO, "Provider: " + provider.getName());

            for (Provider.Service service : provider.getServices()) {
                XMLLib.log(Level.INFO, "\t"+service.getType()+" -  Algorithm: " + service.getAlgorithm());
            }
        }
    }
}
