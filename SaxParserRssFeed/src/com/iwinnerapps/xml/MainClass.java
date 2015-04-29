package com.iwinnerapps.xml;

import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class MainClass {
public static void main(String[] args) {
	try {
		XMLReader xmlRead=XMLReaderFactory.createXMLReader();
		xmlRead.setContentHandler(new AcademyHandler());
		try {
			xmlRead.parse("zacademy.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	} catch (SAXException e) {
		e.printStackTrace();
	}
	
}
}
