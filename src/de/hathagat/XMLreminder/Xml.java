package de.hathagat.XMLreminder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Xml {
	public void readXml() throws ParserConfigurationException {
		File fXmlFile = new File("data.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = null;
		try {
			doc = dBuilder.parse(fXmlFile);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("task");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				System.out.println("\nTitel:\t\t" + eElement.getElementsByTagName("title").item(0).getTextContent());
				System.out.println("Datum:\t\t"
						+ eElement.getElementsByTagName("day").item(0).getTextContent()
						+ "."
						+ eElement.getElementsByTagName("month").item(0).getTextContent()
						+ "."
						+ eElement.getElementsByTagName("year").item(0).getTextContent());
				System.out.println("Uhrzeit:\t"
						+ eElement.getElementsByTagName("hour").item(0).getTextContent()
						+ ":"
						+ eElement.getElementsByTagName("minute").item(0).getTextContent());
				System.out.println("Beschreibung:\t" + eElement.getElementsByTagName("description").item(0).getTextContent());
			}
		}
	}
}
