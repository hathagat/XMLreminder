package de.hathagat.XMLreminder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseXml {
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
	
	public void createXML() {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			
			// define root element
			Document document = documentBuilder.newDocument();
			Element rootElement = document.createElement("tasks");
			document.appendChild(rootElement);

			// define task element
			Element task = document.createElement("task");
			rootElement.appendChild(task);

			// add ID to task
			Attr attribute = document.createAttribute("ID");
			attribute.setValue("1");
			task.setAttributeNode(attribute);

			Element title = document.createElement("title");
			title.appendChild(document.createTextNode("ich bin der Titel"));
			task.appendChild(title);

			Element day = document.createElement("day");
			day.appendChild(document.createTextNode("01"));
			task.appendChild(day);

			Element month = document.createElement("month");
			month.appendChild(document.createTextNode("11"));
			task.appendChild(month);

			Element year = document.createElement("year");
			year.appendChild(document.createTextNode("2011"));
			task.appendChild(year);

			// creating and writing to XML file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");		// add newlines to output
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File("new_data.xml"));

			transformer.transform(domSource, streamResult);

			System.out.println("\nDatei gespeichert!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
	
}
