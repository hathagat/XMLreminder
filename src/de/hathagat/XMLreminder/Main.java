package de.hathagat.XMLreminder;

import javax.xml.parsers.ParserConfigurationException;

public class Main {

	public static void main(String[] args) {
		
		// draw window
		Gui window = new Gui();
		window.setBounds(100, 100, 800, 600); // xPos, yPos, xSize, ySize
		window.setVisible(true);

		// read XML file
		ParseXml data = new ParseXml();
		try {
			data.readXml();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		data.createXML();
	}
}
