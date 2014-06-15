package de.hathagat.XMLreminder;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXml {
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