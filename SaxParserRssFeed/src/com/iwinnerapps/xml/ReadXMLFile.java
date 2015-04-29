package com.iwinnerapps.xml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.iwinnerapps.xml.service.EmployeeInfoForm;
import com.iwinnerapps.xml.service.EmployeeService;

public class ReadXMLFile {

	public static void main(String argv[]) {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean bfname = false;
				boolean blname = false;
				boolean bnname = false;
				boolean bsalary = false;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					System.out.println("Start Element :" + qName);

					if (qName.equalsIgnoreCase("FIRSTNAME")) {
						bfname = true;
					}

					if (qName.equalsIgnoreCase("LASTNAME")) {
						blname = true;
					}

					if (qName.equalsIgnoreCase("NICKNAME")) {
						bnname = true;
					}

					if (qName.equalsIgnoreCase("SALARY")) {
						bsalary = true;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					System.out.println("End Element :" + qName);

				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
                     EmployeeInfoForm eForm=new EmployeeInfoForm();
					if (bfname) {
						String name=new String(ch, start, length);
						System.out.println("First Name : "	+ new String(ch, start, length));
						
						bfname = false;
					}
					if (blname) {
						System.out.println("Last Name : "
								+ new String(ch, start, length));
						eForm.setlName(new String(ch, start, length));
						blname = false;
					}
					if (bnname) {
						System.out.println("Nick Name : "
								+ new String(ch, start, length));
						eForm.setnName(new String(ch, start, length));
						bnname = false;
					}
					if (bsalary) {
						System.out.println("Salary : "
								+ new String(ch, start, length));
						eForm.setSal(new String(ch, start, length));
						bsalary = false;
					}
					EmployeeService.insertEmployeeInfo(eForm);
				}

			};

			
			saxParser.parse("file.xml", handler);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}