package de.hathagat.XMLreminder;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
 
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;

/** Die Klasse Gui enthält alle Funktionen, die zum Erzeugen des Hauptfensters notwendig sind.
 *  Auch die logische Komponente wird in dieser Klasse mittels Eventhandling realisiert.
 *  
 *  Im wesentlichen ist diese Klasse wie folgt aufgebaut:
 *  
 *  <ol>
 *  	<li>Sie enthält eine Checkbox mit der eine Kategorie ausgewählt werden kann</li>
 *  	<li>Es gibt 4 Eingabe Fenster, die wichtig für die Erstellung von Terminen sind:</li>
 *  	<ul>
 *  		<li>Titel</li>
 *  		<li>Datum</li>
 *  		<li>Uhrzeit</li>
 *  		<li>Beschreibung</li>
 *  	</ul>
 *  	<li>Zur Darstellung wird mit Hilfe einer Tabelle die XML Datei dargestellt.</li>
 *  </ol>
 *  
 *  Im unteren Teil des Fensters sind 3 Buttons zu finden, um den Terminkalender anzupassen.
 *  Es ist möglich bereits erstellte Termine zu löschen, bzw. zu editieren.
 * 
 * @author Norman Nusse
 * @author Christoph Manske
 * @version 1.0, 29.06.2014
 */
public class Gui extends JFrame implements ActionListener, FocusListener{
	private static final long serialVersionUID = 1470518963314635877L;
	
	private String[] categoryOptions = { "Allgemein", "Feier", "Geburtstag", "Meeting", "Prüfung", "Urlaub" };
	private Object[] column = new Object[5];
	
	private JLabel taskLabel;		
	private JLabel categoryLabel;
	private JLabel titleLabel;
	private JLabel dateLabel;
	private JLabel timeLabel;
	private JLabel descriptionLabel;
	
	private JComboBox categoryBox;
	private JTextField titleText;
	private JTextField dateText;
	private JTextField timeText;
	private JTextField descriptionText;
	
	private JButton quitButton = new JButton("Beenden");
	private JButton insertButton = new JButton("Eintragen");
	private JButton deleteButton = new JButton("Löschen");
	private JButton editButton = new JButton("Editieren");
	
	private static String[] titles = new String[]{ "Kategorie", "Titel", "Datum", "Uhrzeit", "Beschreibung" };	// titles for table columns
    final static DefaultTableModel model = new DefaultTableModel( titles, 0 );
   
    /** Der Standardkonstruktor konfiguriert das Hauptfenster mit einem Titel und fügt einen WindowListener hinzu, um
     *  das Fenster mit den am rechten oberen Rand vorhandenen Button (rote X) schließen zu können. 
     */
	public Gui() {
		super("XML-Reminder");
		Container contentPane = getContentPane();
		contentPane.setLayout(null);
		addWindowListener(new WindowClosing());
		initWindow(contentPane);
	}
	 /** Ordnet die Elemente im Fenster an. Jedes Element erhält dabei eine fest zugewiesene Position und gegebenfalls Eventhandler, um auf 
	  *  Ereignisse zu reagieren. 
	  * 
	  * @param content - Verweis auf den Inhalt des Hauptfensters (den Contentbereich)
	  */
	protected void initWindow(Container content) {
		setTask(content);
		xmlTable(content);
	}
	
	/**	Teilfunktion zur Positionierung der Elemente im Hauptfenster 
	 * 
	 * @param content - Verweis auf den Inhalt des Hauptfensters (den Contentbereich)
	 */
	public void setTask(Container content) {
		
		taskLabel = new JLabel("neuer Termin:");		
		categoryLabel = new JLabel("Kategorie");
		titleLabel = new JLabel("Titel");
		dateLabel = new JLabel("Datum (dd.mm.yyyy)");
		timeLabel = new JLabel("Uhrzeit (hh:mm)");
		descriptionLabel = new JLabel("Beschreibung");
		
		categoryBox = new JComboBox(categoryOptions);
		titleText = new JTextField();
		titleText.addFocusListener(this);
		dateText = new JTextField();
		dateText.addFocusListener(this);
		timeText = new JTextField();
		timeText.addFocusListener(this);
		descriptionText = new JTextField();
		descriptionText.addFocusListener(this);
		
		insertButton = new JButton("Eintragen");
		insertButton.addActionListener(this);
		insertButton.setEnabled(false);
		quitButton.addActionListener(this);
		
		
		// Positionierung der Buttons und Texte
		taskLabel.setBounds(25, 25, 100, 20);
		categoryLabel.setBounds(50, 50, 100, 20);
		titleLabel.setBounds(200, 50, 100, 20);
		dateLabel.setBounds(600, 50, 150, 20);
		timeLabel.setBounds(600, 110, 150, 20);
		descriptionLabel.setBounds(200, 110, 100, 20);
	
		categoryBox.setBounds(50, 75, 100, 20);
		titleText.setBounds(200, 75, 350, 20);
		dateText.setBounds(600, 75, 100, 20);
		timeText.setBounds(600, 135, 100, 20);
		descriptionText.setBounds(200, 135, 350, 20);

		insertButton.setBounds(640, 175, 100, 30);
		deleteButton.setBounds(30, 500, 100, 30);
		editButton.setBounds(330, 500, 100, 30);
		quitButton.setBounds(640, 500, 100, 30);

		// Anhängen der Elemente an das Hauptfenster
		content.add(taskLabel);
		content.add(categoryLabel);
		content.add(titleLabel);
		content.add(dateLabel);
		content.add(timeLabel);
		content.add(descriptionLabel);
		
		content.add(categoryBox);
		content.add(titleText);
		content.add(dateText);
		content.add(timeText);
		content.add(descriptionText);
		
		content.add(quitButton);
		content.add(insertButton);
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(this);
		content.add(deleteButton);
		editButton.setEnabled(false);
		editButton.addActionListener(this);
		content.add(editButton);
		
		pack();
	}
	
	/** stellt die Tabelle dar und implementiert die Logik zur Auswahl von Tabellenzeilen.
	 *  Die ausgewählte Zeile wird spaltenweise betrachtet und die aktuellen Werte in das Array column gespeichert.
	 * 
	 * @param content - Verweis auf den Inhalt des Hauptfensters (den Contentbereich)
	 */
	public void xmlTable(Container content) {
		JTable table = new JTable(model);
		table.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e) {

				if(e.getClickCount() == 1)
				{
					JTable tmp = (JTable)e.getSource();
					int rowPos = tmp.getSelectedRow();
					for(int i = 0; i < column.length; ++i)
					{
						column[i] = tmp.getValueAt(rowPos, i);
					}
					deleteButton.setEnabled(true);
					editButton.setEnabled(true);
				}
			}
			
			
		});
			
		// Tabelle bekommt bei großer Anzahl an Inhalten einen vertikalen Scrollbalken
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(15, 250, 725, 200);
		content.add(scrollPane);
	}

	/** beim Klicken des Eintrage Buttons wird der Inhalt der Textfelder analysiert und in die XML Datei geschrieben.
	 *  Im Anschluss wird die XML Datei erneut gelesen und der Inhalt in die Tabelle geschrieben.
	 * 
	 */
	public void insertButtonClicked() {
		ParseXml.createXML(model.getRowCount()+1,
				categoryBox.getSelectedItem(),
				titleText.getText(),
				dateText.getText(),
				timeText.getText(),
				descriptionText.getText());
		
		model.setRowCount(0);	
		
		// Löschen des soeben eingegebenen Eintrags
		titleText.setText("");
		dateText.setText("");
		timeText.setText("");
		descriptionText.setText("");
		
		ParseXml data = new ParseXml();
			try {
				data.readXml();		
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

	}
	
	/** bei Auswahl einer Zeile kann diese durch klicken auf den Button löschen aus der XML Datei entfernt werden.
	 *  Die Tabelle wird anschließend aktualisiert.
	 * 
	 */
	public void deleteText(){
		ParseXml.deleteXML(column);
		
		model.setRowCount(0);	// clear table
		
		ParseXml data = new ParseXml();
		try {
				data.readXml();		// read new table content
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
    // ======================= EVENTHANDLING ============================
	
	/** Diese Funktion analysiert, ob ein Button benutzt wurde.
	 *  Gegebenfalls wird anschließend darauf reagiert und z.B das Löschen eines Eintrages eingeleitet.
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if(name.equals("Beenden")){
			setVisible(false);
			dispose();
			System.exit(0);
			
		}else if(name.equals("Eintragen")){
			insertButtonClicked();
			
		}else if(name.equals("Löschen")){
			deleteText();
			deleteButton.setEnabled(false);
			editButton.setEnabled(false);
			
		}else if(name.equals("Editieren")){
			Point positionGui = new Point(this.getX(), this.getY());
			EditDialog edit = new EditDialog(column , categoryBox, model, true);
			edit.setSize(this.getWidth(), this.getHeight());
			edit.setLocation(positionGui);
			edit.setVisible(true);
			deleteButton.setEnabled(false);
			editButton.setEnabled(false);
		}
	}

	/** FocusHandler um festzustellen, ob eine Zeile aus der Tabelle ausgewählt wurde.
	 *  Erst dann ist es möglich den Löschen-, als auch den Editier Button zu verwenden.
	 * 
	 */
	public void focusGained(FocusEvent e) {
		if(e.getSource() instanceof JTable){
			deleteButton.setEnabled(true);
			editButton.setEnabled(true);
		}
	}

	/** FocusHandler um festzustellen, dass alle benötigten Felder zum Eintragen neuer Termine bereits besucht wurden und
	 * über Inhalt verfügen.
	 * 
	 */
	public void focusLost(FocusEvent e) {
		if(e.getSource() instanceof JTextField){
			if((titleText.getText().length() > 2) &&
			   (dateText.getText().length() == 10) &&
			   (timeText.getText().length() == 5) &&
			   (descriptionText.getText().length() > 5)){
					insertButton.setEnabled(true);
			}
				
		}
	}

}

class WindowClosing extends WindowAdapter{
	public void windowClosing(WindowEvent e) {
		e.getWindow().setVisible(false);
		e.getWindow().dispose();
		System.exit(0);
	
	}
}
