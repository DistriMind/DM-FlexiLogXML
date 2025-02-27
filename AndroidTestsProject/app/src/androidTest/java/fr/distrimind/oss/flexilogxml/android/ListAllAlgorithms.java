package fr.distrimind.oss.flexilogxml.android;

import fr.distrimind.oss.flexilogxml.FlexiLogXML;
import fr.distrimind.oss.flexilogxml.log.Level;
import fr.distrimind.oss.flexilogxml.log.MarkerFactory;

import org.junit.Test;

import java.security.Provider;
import java.security.Security;

public class ListAllAlgorithms {
    @Test
    public void listAllAlgorithms()
    {
        FlexiLogXML.setMarker(MarkerFactory.getSingleton().getMarker("TestXMLLib"));
        for (Provider provider : Security.getProviders()) {
            FlexiLogXML.log(Level.INFO, "Provider: " + provider.getName());

            for (Provider.Service service : provider.getServices()) {
                FlexiLogXML.log(Level.INFO, "\t"+service.getType()+" -  Algorithm: " + service.getAlgorithm());
            }
        }
    }
}
