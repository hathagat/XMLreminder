package de.hathagat.XMLreminder;

import javax.xml.parsers.ParserConfigurationException;

/** Die Klasse Main ist für den Start des Programmes zuständig. Hier wird das Gui-Fenster erzeugt und 
 *  die XML Datei für den Start einmalig ausgelesen.
 * 
 * @author Norman Nusse
 * @author Christoph Manske
 * @version 1.0, 30.06.2014
 *
 */
public class Main {
 
	public static void main(String[] args) {
		
		// draw window
		Gui window = new Gui();
		window.setBounds(100, 100, 800, 600); 
		window.setVisible(true);

		
		ParseXml.readXMLSchema();
		// read XML file
		ParseXml data = new ParseXml();
		try {
			data.readXml();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}
