package de.hathagat.XMLreminder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadXml {
	String content[][] = null;
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

		NodeList nList = doc.getElementsByTagName("task");	//take a task
		
		for (int i=0; i < nList.getLength(); i++) {

			Node nNode = nList.item(i);	// take next task node

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element currentTask = (Element) nNode;	
				
				xmlToConsole(currentTask);	// test output
				
				Vector xmlData = xmltoTable(currentTask);	// write nodes of current task in vector
				Gui.model.addRow( xmlData );	// add vector to table

			}
		}
	}
	
    public static Vector xmltoTable(Element task) {
        Vector vector = new Vector( Gui.model.getColumnCount() );

        vector.add( task.getAttribute("ID") );
        vector.add( task.getElementsByTagName("title").item(0).getTextContent() );
        vector.add( task.getElementsByTagName("day").item(0).getTextContent() + "."
				+ task.getElementsByTagName("month").item(0).getTextContent() + "."
				+ task.getElementsByTagName("year").item(0).getTextContent() );
        vector.add( task.getElementsByTagName("hour").item(0).getTextContent() + ":"
				+ task.getElementsByTagName("minute").item(0).getTextContent() );
        vector.add( task.getElementsByTagName("description").item(0).getTextContent() );
       
        return vector;
    }
	
	// test output
	public void xmlToConsole(Element task) {
	    System.out.println("\nTask ID:\t" + task.getAttribute("ID")); 
		System.out.println("Titel:\t\t" + task.getElementsByTagName("title").item(0).getTextContent());
		System.out.println("Datum:\t\t"
				+ task.getElementsByTagName("day").item(0).getTextContent() + "."
				+ task.getElementsByTagName("month").item(0).getTextContent() + "."
				+ task.getElementsByTagName("year").item(0).getTextContent());
		System.out.println("Uhrzeit:\t"
				+ task.getElementsByTagName("hour").item(0).getTextContent() + ":"
				+ task.getElementsByTagName("minute").item(0).getTextContent());
		System.out.println("Beschreibung:\t" + task.getElementsByTagName("description").item(0).getTextContent());
	}
}
