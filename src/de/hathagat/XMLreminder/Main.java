package de.hathagat.XMLreminder;

import javax.xml.parsers.ParserConfigurationException;

public class Main {

	public static void main(String[] args) {
		
		// draw window
		Gui window = new Gui("XML-Reminder");
		window.setBounds(100, 100, 800, 600); // xPos, yPos, xSize, ySize
		window.show();

		// read XML file
		ReadXml data = new ReadXml();
		try {
			data.readXml();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// create XML file
		CreateXml createXml = new CreateXml();
		createXml.createXML();
	}
}
