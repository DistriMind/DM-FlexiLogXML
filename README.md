DM-FlexiLogXML
================
[![CodeQL](https://github.com/JasonMahdjoub/DM-FlexiLogXML/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/JasonMahdjoub/DM-FlexiLogXML/actions/workflows/codeql-analysis.yml)

This library offer an XML interface that is stream oriented and that works with javax.xml.stream into Java Desktop, and with org.xmlpull.v1 into Android.
It offers also a unique interface for using logs through desktop applications and Android applications.

How to use it ?
---------------
### With Gradle :

Adapt into your build.gradle file, the next code :

 - When using DM-FlexiLogXML into desktop environment, please add this dependency (minimum Java version is 11) :
    ```
	    ...
	    dependencies {
		    ...
		    api(group:'com.distrimind.flexilogxml.desktop', name: 'DM-FlexiLogXML-Desktop', version: '1.3.0-STABLE')
		    ...
	    }
	    ...
    ```

 - When using DM-FlexiLogXML into android environment, please add this dependency (minimum Android API version is 26) :

    ```
	    ...
	    dependencies {
		    ...
		    implementation(group:'com.distrimind.flexilogxml.android', name: 'DM-FlexiLogXML-Android', version: '1.3.0-STABLE')
		    ...
	    }
	    ...
    ```

 - Libraries are available on Maven Central. You can check signatures of dependencies with this [public GPG key](key-2023-10-09.pub). You can also use the next repository : 
    ```
        ...
        repositories {
            ...
            maven {
                    url "https://artifactory.distri-mind.fr/ui/native/gradle-release/"
            }
            ...
        }
        ...
    ```

To know what is the last uploaded version, please refer to versions available here : [this repository](https://artifactory.distri-mind.fr/ui/native/DistriMind-Public/com/distrimind/flexilogxml/DM-FlexiLogXML-Core/)
### With Maven :
Adapt into your pom.xml file, the next code :
 - When using DM-FlexiLogXML into desktop environment, please add this dependency (minimum Java version is 11) :
    ```
        ...
        <project>
            ...
            <dependencies>
                ...
                <dependency>
                    <groupId>com.distrimind.flexilogxml.desktop</groupId>
                    <artifactId>DM-FlexiLogXML-Desktop</artifactId>
                    <version>1.3.0-STABLE</version>
                </dependency>
                ...
            </dependencies>
            ...
        </project>
        ...
    ```
   
 - When using DM-FlexiLogXML into android environment, please add this dependency (minimum Android API version is 26) :
    ```
        ...
        <dependency>
            <groupId>com.distrimind.flexilogxml.android</groupId>
            <artifactId>DM-FlexiLogXML-Android</artifactId>
            <version>1.3.0-STABLE</version>
        </dependency>
        ...
    ```
   
 - Libraries are available on Maven Central. You can check signatures of dependencies with this [public GPG key](key-2023-10-09.pub). You can also use the next repository : 
    ```
        ...
        <repositories>
            ...
            <repository>
                <id>DistriMind-Public</id>
                <url>https://artifactory.distri-mind.fr/ui/native/gradle-release/</url>
            </repository>
            ...
        </repositories>
        ...		
    ```
To know what last version has been uploaded, please refer to versions available into [this repository](https://artifactory.distri-mind.fr/ui/native/DistriMind-Public/com/distrimind/flexilogxml/DM-FlexiLogXML-Core/)

# License

This code is free software: you can redistribute it and/or modify it under the terms of the [GNU Lesser General Public License version 3 only (LGPL-3.0-only)](https://git.distri-mind.fr/DM-FlexiLogXML/~files/main/COPYING), as published by the Free Software Foundation.

