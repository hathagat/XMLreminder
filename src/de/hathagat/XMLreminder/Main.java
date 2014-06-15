package de.hathagat.XMLreminder;

import javax.xml.parsers.ParserConfigurationException;

public class Main {

	public static void main(String[] args) {
		Gui window = new Gui("XML-Reminder");
		window.setBounds(100, 100, 800, 600); // xPos, yPos, xSize, ySize
		window.show();

		Xml data = new Xml();
		try {
			data.readXml();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
