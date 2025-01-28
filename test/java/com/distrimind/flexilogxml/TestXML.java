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

import com.distrimind.flexilogxml.xml.IXmlReader;
import com.distrimind.flexilogxml.xml.IXmlWriter;
import com.distrimind.flexilogxml.xml.XmlParserFactory;

import org.slf4j.event.Level;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

/**
 * @author Jason Mahdjoub
 * @version 1.0
 * @since com.distrimind.flexilogxml
 */
public class TestXML {
	private static final String androidStartDoc="<?xml version='1.0' encoding='UTF-8' standalone='no' ?>";
	private static final String startDoc="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private static final String xmlContentWithStart="<root xmlns=\"urn:schemas-upnp-org:device-1-0\"><specVersion><major>1</major><minor>0</minor></specVersion><device><deviceType>urn:schemas-upnp-org:device:MY-DEVICE-TYPE:1</deviceType><friendlyName>My Testdevice</friendlyName><manufacturer>4th Line</manufacturer><manufacturerURL>http://www.4thline.org/</manufacturerURL><modelDescription>TEST Device</modelDescription><modelName>MYMODEL</modelName><modelNumber>ONE</modelNumber><modelURL>http://www.4thline.org/foo</modelURL><serialNumber>000da201238c</serialNumber><UDN>uuid:MY-DEVICE-123</UDN><presentationURL>http://www.4thline.org/some_user_interface/</presentationURL><UPC>100000000001</UPC><dlna:X_DLNADOC xmlns:dlna=\"urn:schemas-dlna-org:device-1-0\">DMS-1.50</dlna:X_DLNADOC><dlna:X_DLNADOC xmlns:dlna=\"urn:schemas-dlna-org:device-1-0\">M-DMS-1.50</dlna:X_DLNADOC><dlna:X_DLNACAP xmlns:dlna=\"urn:schemas-dlna-org:device-1-0\">av-upload,image-upload,audio-upload</dlna:X_DLNACAP><iconList><icon><mimetype>image/png</mimetype><width>32</width><height>32</height><depth>8</depth><url>/dev/MY-DEVICE-123/icon.png</url></icon><icon><mimetype>image/png</mimetype><width>32</width><height>32</height><depth>8</depth><url>/dev/MY-DEVICE-123/icon2.png</url></icon></iconList><serviceList><service><serviceType>urn:schemas-upnp-org:service:MY-SERVICE-TYPE-ONE:1</serviceType><serviceId>urn:upnp-org:serviceId:MY-SERVICE-123</serviceId><SCPDURL>/dev/MY-DEVICE-123/svc/upnp-org/MY-SERVICE-123/desc</SCPDURL><controlURL>/dev/MY-DEVICE-123/svc/upnp-org/MY-SERVICE-123/action</controlURL><eventSubURL>/dev/MY-DEVICE-123/svc/upnp-org/MY-SERVICE-123/event</eventSubURL></service></serviceList><deviceList><device><deviceType>urn:schemas-upnp-org:device:MY-DEVICE-TYPE-TWO:2</deviceType><friendlyName>My Testdevice Second</friendlyName><manufacturer>4th Line</manufacturer><manufacturerURL>http://www.4thline.org/</manufacturerURL><modelDescription>TEST Device</modelDescription><modelName>MYMODEL</modelName><modelNumber>ONE</modelNumber><modelURL>http://www.4thline.org/this_is_the_embedded_model</modelURL><serialNumber>000da201238d</serialNumber><UDN>uuid:MY-DEVICE-456</UDN><presentationURL>http://www.4thline.org/some_other_user_interface</presentationURL><UPC>100000000002</UPC><iconList><icon><mimetype>image/png</mimetype><width>32</width><height>32</height><depth>8</depth><url>/dev/MY-DEVICE-456/icon3.png</url></icon></iconList><serviceList><service><serviceType>urn:schemas-upnp-org:service:MY-SERVICE-TYPE-TWO:2</serviceType><serviceId>urn:upnp-org:serviceId:MY-SERVICE-456</serviceId><SCPDURL>/dev/MY-DEVICE-456/svc/upnp-org/MY-SERVICE-456/desc</SCPDURL><controlURL>/dev/MY-DEVICE-456/svc/upnp-org/MY-SERVICE-456/action</controlURL><eventSubURL>/dev/MY-DEVICE-456/svc/upnp-org/MY-SERVICE-456/event</eventSubURL></service></serviceList><deviceList><device><deviceType>urn:schemas-upnp-org:device:MY-DEVICE-TYPE-THREE:3</deviceType><friendlyName>My Testdevice Third</friendlyName><manufacturer>4th Line</manufacturer><manufacturerURL>http://www.4thline.org/</manufacturerURL><modelDescription>TEST Device</modelDescription><modelName>MYMODEL</modelName><modelNumber>ONE</modelNumber><modelURL>http://www.4thline.org/another_embedded_model</modelURL><serialNumber>000da201238d</serialNumber><UDN>uuid:MY-DEVICE-789</UDN><presentationURL>http://www.4thline.org/some_third_user_interface</presentationURL><UPC>100000000003</UPC><serviceList><service><serviceType>urn:schemas-upnp-org:service:MY-SERVICE-TYPE-THREE:3</serviceType><serviceId>urn:upnp-org:serviceId:MY-SERVICE-789</serviceId><SCPDURL>/dev/MY-DEVICE-789/svc/upnp-org/MY-SERVICE-789/desc</SCPDURL><controlURL>/dev/MY-DEVICE-789/svc/upnp-org/MY-SERVICE-789/action</controlURL><eventSubURL>/dev/MY-DEVICE-789/svc/upnp-org/MY-SERVICE-789/event</eventSubURL></service></serviceList></device></deviceList></device></deviceList></device></root>";
	private static final String xmlContent=startDoc+xmlContentWithStart;
	@Test
	public void testXMLConvert() {
		try(ByteArrayOutputStream out=new ByteArrayOutputStream()) {
			IXmlReader xmlReader=XmlParserFactory.getXmlInputFactory().getXMLReader(new StringReader(xmlContent));
			IXmlWriter xmlWriter=XmlParserFactory.getXmlOutputFactory().getXMLWriter(true, out);
			xmlReader.transferTo(xmlWriter);
			xmlReader.close();
			xmlWriter.close();
			out.flush();
			final String withIndent=new String(out.toByteArray(), StandardCharsets.UTF_8);

			try(ByteArrayOutputStream out2=new ByteArrayOutputStream()) {
				xmlReader=XmlParserFactory.getXmlInputFactory().getXMLReader(new StringReader(withIndent));
				xmlWriter=XmlParserFactory.getXmlOutputFactory().getXMLWriter(false, out2);
				xmlReader.transferTo(xmlWriter);
				xmlReader.close();
				xmlWriter.close();
				out2.flush();
				final String withoutIndent=new String(out2.toByteArray(), StandardCharsets.UTF_8);
				final String withoutIndent2=withoutIndent.startsWith(androidStartDoc)?
						withoutIndent.substring(androidStartDoc.length()):
						(withoutIndent.startsWith(startDoc)?withoutIndent.substring(startDoc.length()):withoutIndent);
				Assert.assertEquals(withoutIndent2.length(), xmlContentWithStart.length(), withoutIndent2);
				Assert.assertEquals(withoutIndent2, xmlContentWithStart);

			}
		}
		catch (IOException e)
		{
			FlexiLogXML.log(Level.WARN, e);
		}
	}
}
