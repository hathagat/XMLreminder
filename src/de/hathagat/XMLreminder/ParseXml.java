package de.hathagat.XMLreminder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/** Zuständig für die komplette Verarbeitung der XML Datei.
 *  Hier wird die XML Datei gelesen und bearbeitet (löschen/editieren/hinzufügen) 
 * 
 * @author Norman Nusse
 * @author Christoph Manske
 * @version 1.0, 29.06.2014
 *
 */
public class ParseXml {
	String content[][] = null;
	
	/** liest die XML Datei ein und fügt die Einträge der Tabelle hinzu.
	 * 
	 * @throws ParserConfigurationException
	 */
	public void readXml() throws ParserConfigurationException {
		File xmlFile = new File("data.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = null;
		try {
			doc = dBuilder.parse(xmlFile);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();

		// erzeugt eine Liste mit allen "task" Tags
		NodeList nList = doc.getElementsByTagName("task");	
		
		for (int i=0; i < nList.getLength(); i++) {
			// wählt den i-ten Task aus
			Node nNode = nList.item(i);	
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element currentTask = (Element) nNode;	
				// erzeugt eine Testausgabe des Knoten auf der Konsole
				//xmlToConsole(currentTask);	
				// schreibt Knoten in einen Vector
				Vector xmlData = xmltoTable(currentTask);
				// fügt den Vector der Tabelle hinzu
				Gui.model.addRow(xmlData);	
			}
		}
	}
	
	/** ermittelt die Inhalte eines Notes aus der XML Datei und trägt diese in einen Vektor ein.
	 * 
	 * @param task - aktueller Note
	 * @return vector - der Vektor mit den Inhalten eines Notes "task"
	 */
    public static Vector xmltoTable(Element task) {
        Vector vector = new Vector( Gui.model.getColumnCount() );

        vector.add( task.getElementsByTagName("category").item(0).getTextContent() );
        vector.add( task.getElementsByTagName("title").item(0).getTextContent() );
        vector.add( task.getElementsByTagName("day").item(0).getTextContent() + "."
				+ task.getElementsByTagName("month").item(0).getTextContent() + "."
				+ task.getElementsByTagName("year").item(0).getTextContent() );
        vector.add( task.getElementsByTagName("hour").item(0).getTextContent() + ":"
				+ task.getElementsByTagName("minute").item(0).getTextContent() );
        vector.add( task.getElementsByTagName("description").item(0).getTextContent() );
       
        return vector;
    }
	
	/** dient zum Testen der Funktion xmltoTable und schreibt die Inhalte eines Notes "task" in die Konsole.
	 * 
	 * @param task - aktueller Note
	 */
	public void xmlToConsole(Element task) {
	    System.out.println("\nTask ID:\t" + task.getAttribute("ID"));
	    System.out.println("Kategorie:\t" + task.getElementsByTagName("category").item(0).getTextContent());
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
	
	/** überträgt einen eingebenen Eintrag in die XML Datei. Dabei wird stets darauf geachtet, dass die Struktur der XML Datei erhalten bleibt (wellformed)
	 * 
	 * @param id - Identifikationsnummer
	 * @param category - Kategorie
	 * @param title - Titel
	 * @param date - Datum
	 * @param time - Zeit
	 * @param description - Beschreibung
	 */
	public static void createXML(int id, Object category, String title, String date, String time, String description){
		
		String day, month, year, hour, minute;
		
        final StringTokenizer dateToken = new StringTokenizer(date, "./");
        day = dateToken.nextToken();
        month = dateToken.nextToken();
        year = dateToken.nextToken();
        
        final StringTokenizer timeToken = new StringTokenizer(time, ":");
        hour = timeToken.nextToken();
        minute = timeToken.nextToken();
        
        
		File xmlFile = new File("data.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document document = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			document = dBuilder.parse(xmlFile);
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		document.getDocumentElement().normalize();
			
		try {			
			// define root element
			Element rootElement = document.getDocumentElement();
			
			// define task element
			Element task = document.createElement("task");
			rootElement.appendChild(task);

			// add ID to task
			Attr attribute = document.createAttribute("ID");
			attribute.setValue(String.valueOf(id));
			task.setAttributeNode(attribute);
			
			Element categoryXml = document.createElement("category");
			categoryXml.appendChild(document.createTextNode((String)category));
			task.appendChild(categoryXml);

			Element titleXml = document.createElement("title");
			titleXml.appendChild(document.createTextNode(title));
			task.appendChild(titleXml);

			Element dayXml = document.createElement("day");
			dayXml.appendChild(document.createTextNode(day));
			task.appendChild(dayXml);

			Element monthXml = document.createElement("month");
			monthXml.appendChild(document.createTextNode(month));
			task.appendChild(monthXml);

			Element yearXml = document.createElement("year");
			yearXml.appendChild(document.createTextNode(year));
			task.appendChild(yearXml);
			
			Element hourXml = document.createElement("hour");
			hourXml.appendChild(document.createTextNode(hour));
			task.appendChild(hourXml);
			
			Element minuteXml = document.createElement("minute");
			minuteXml.appendChild(document.createTextNode(minute));
			task.appendChild(minuteXml);
			
			Element descriptionXml = document.createElement("description");
			descriptionXml.appendChild(document.createTextNode(description));
			task.appendChild(descriptionXml);

			// write to XML file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");		// add newlines to output
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File("data.xml"));

			transformer.transform(domSource, streamResult);
			
			System.out.println("\nDatei gespeichert!");

		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
	
	/** löscht den gewünschten Tabelleneintrag aus der XML Datei. 
	 *  Es wird in der XML Datei nach einem Knoten gesucht der die selben Einträge hat, wie die ausgewählte
	 *  Tabellenzeile. Nur wenn dies zutrifft wird der Knoten aus der XML Struktur gelöscht. 
	 * 
	 * @param daten - Daten der Tabellenzeile
	 */
	public static void deleteXML(Object[] daten)
	{
		
		String day, month, year, hour, minute;
		
		try {
			
			File xmlFile = new File("data.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(xmlFile);
			document.getDocumentElement().normalize();
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer tFormer = tFactory.newTransformer();
			
			NodeList nList = document.getElementsByTagName("task");	 //take a task
			for(int i = 0; i < nList.getLength(); ++i){
				Element task = (Element)nList.item(i);

				if(task.hasAttribute("ID")){
					Element kat = (Element)task.getElementsByTagName("category").item(0);
					String kat_name = kat.getTextContent();
					Element title = (Element)task.getElementsByTagName("title").item(0);
					String title_name = title.getTextContent();
					
					final StringTokenizer dateToken = new StringTokenizer(daten[2].toString(), "./");
			        day = dateToken.nextToken();
			        month = dateToken.nextToken();
			        year = dateToken.nextToken();
			        
			        final StringTokenizer timeToken = new StringTokenizer(daten[3].toString(), ":");
			        hour = timeToken.nextToken();
			        minute = timeToken.nextToken();

			        Element day_xml = (Element)task.getElementsByTagName("day").item(0);
			        String day_name = day_xml.getTextContent();

			        Element month_xml = (Element)task.getElementsByTagName("month").item(0);
			        String month_name = month_xml.getTextContent();
			        
			        Element year_xml = (Element)task.getElementsByTagName("year").item(0);
			        String year_name = year_xml.getTextContent();
			        
			        Element hour_xml = (Element)task.getElementsByTagName("hour").item(0);
			        String hour_name = hour_xml.getTextContent();
			        
			        Element minute_xml = (Element)task.getElementsByTagName("minute").item(0);
			        String minute_name = minute_xml.getTextContent();
			        
			        Element description_xml = (Element)task.getElementsByTagName("description").item(0);
			        String description_name = description_xml.getTextContent();
			        
					if(
					   (kat_name.equals(daten[0])) &&
					   (title_name.equals(daten[1])) &&
					   (day_name.equals(day)) &&
					   (month_name.equals(month)) &&
					   (year_name.equals(year)) &&
					   (hour_name.equals(hour)) &&
					   (minute_name.equals(minute)) &&
					   (description_name.equals(daten[4]))){

						task.getParentNode().removeChild(task);
					}
					
					
				}
				
				DOMSource domSource = new DOMSource(document);
				StreamResult streamResult = new StreamResult(new File("data.xml"));

				tFormer.transform(domSource, streamResult);
			}
					
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** editiert die XML Datei, falls ein Eintrag geändert wurde. 
	 *  Die Kinder des Knotens werden durch die "Neuen" Daten ersetzt und somit editiert.
	 * 
	 * @param daten - Daten der Tabellenzeile
	 * @param now - zu ersetzende Informationen
	 */
	public static void editXML(Object[] daten, Object[] now)
	{
		String day, month, year, hour, minute;
		
		try {
			
			File xmlFile = new File("data.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(xmlFile);
			document.getDocumentElement().normalize();
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer tFormer = tFactory.newTransformer();
			
			NodeList nList = document.getElementsByTagName("task");	 //take a task
			for(int i = 0; i < nList.getLength(); ++i){
				Element task = (Element)nList.item(i);

				if(task.hasAttribute("ID")){
					Element kat = (Element)task.getElementsByTagName("category").item(0);
					String kat_name = kat.getTextContent();
					Element title = (Element)task.getElementsByTagName("title").item(0);
					String title_name = title.getTextContent();
					
					final StringTokenizer dateToken = new StringTokenizer(daten[2].toString(), "./");
			        day = dateToken.nextToken();
			        month = dateToken.nextToken();
			        year = dateToken.nextToken();
			        
			        final StringTokenizer timeToken = new StringTokenizer(daten[3].toString(), ":");
			        hour = timeToken.nextToken();
			        minute = timeToken.nextToken();

			        Element day_xml = (Element)task.getElementsByTagName("day").item(0);
			        String day_name = day_xml.getTextContent();

			        Element month_xml = (Element)task.getElementsByTagName("month").item(0);
			        String month_name = month_xml.getTextContent();
			        
			        Element year_xml = (Element)task.getElementsByTagName("year").item(0);
			        String year_name = year_xml.getTextContent();
			        
			        Element hour_xml = (Element)task.getElementsByTagName("hour").item(0);
			        String hour_name = hour_xml.getTextContent();
			        
			        Element minute_xml = (Element)task.getElementsByTagName("minute").item(0);
			        String minute_name = minute_xml.getTextContent();
			        
			        Element description_xml = (Element)task.getElementsByTagName("description").item(0);
			        String description_name = description_xml.getTextContent();
			        
					if(
					   (kat_name.equals(daten[0])) &&
					   (title_name.equals(daten[1])) &&
					   (day_name.equals(day)) &&
					   (month_name.equals(month)) &&
					   (year_name.equals(year)) &&
					   (hour_name.equals(hour)) &&
					   (minute_name.equals(minute)) &&
					   (description_name.equals(daten[4]))){

						// Diesen Task editieren
						kat.setTextContent(now[0].toString());
						title.setTextContent(now[1].toString());

						// Datum zerlegen
						final StringTokenizer dateTokenNow = new StringTokenizer(now[2].toString(), "./");
				        day = dateTokenNow.nextToken();
				        month = dateTokenNow.nextToken();
				        year = dateTokenNow.nextToken();
				        day_xml.setTextContent(day);
				        month_xml.setTextContent(month);
				        year_xml.setTextContent(year);

				        // Zeit zerlegen
				        final StringTokenizer timeTokenNow = new StringTokenizer(now[3].toString(), ":");
				        hour = timeTokenNow.nextToken();
				        minute = timeTokenNow.nextToken();
				        
				        hour_xml.setTextContent(hour);
				        minute_xml.setTextContent(minute);
				        description_xml.setTextContent(now[4].toString());
				        
					}
					
					
				}
				
				DOMSource domSource = new DOMSource(document);
				StreamResult streamResult = new StreamResult(new File("data.xml"));

				tFormer.transform(domSource, streamResult);
			}
					
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/** überprüft ob die data.xml valide ist.
	 *  Das definierte XML Schema ist in der data.xsd zu finden.
	 * 
	 */
	public static void readXMLSchema()
	{
		try {
		
			File xmlFile = new File("data.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			// Validierung wird aktiviert, in den folgenden Zeilen wird überprüft, ob das XML valide ist
			dbFactory.setValidating(true);
			dbFactory.setNamespaceAware(true);
			dbFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			dBuilder.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId, String systemId)
						throws SAXException, IOException {

					if(systemId.endsWith("data.xsd"))
					{
						return new InputSource(getClass().getResourceAsStream("data.xsd"));
					}
					return null;
				}
			});
			
			// Exception Handling falls ein Fehler auftritt
			dBuilder.setErrorHandler(new ErrorHandler(){
				public void error(SAXParseException exception) throws SAXException {
					System.out.println(exception.getMessage());
				}
				public void fatalError(SAXParseException exception) throws SAXException {
					System.out.println(exception.getMessage());
				}
				public void warning(SAXParseException exception) throws SAXException {
					System.out.println(exception.getMessage());
				}
				
			});
			
			Document document = dBuilder.parse(xmlFile);
			document.getDocumentElement().normalize();
			 
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
