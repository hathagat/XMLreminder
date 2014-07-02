package de.hathagat.XMLreminder;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;

/** Diese Klasse erzeugt ein Dialogfenster zum Editieren bereits vorhandener Einträge.
 *  Dabei wird das Hauptfenster solange gesperrt, bis die Änderung vom Nutzer vorgenommen wurde. 
 *  Der Nutzer erhält die aktuellen Informationen über den ausgewählten Eintrag und kann bei Bedarf
 *  beliebige Änderungen vornehmen.
 * 
 * @author Norman Nusse
 * @author Christoph Manske
 * @version 1.0, 29.06.2014
 *
 */
public class EditDialog extends JDialog{
	private static final long serialVersionUID = 352506412681219825L;

	private JComboBox m_category;
	private Object[] m_daten;
	private DefaultTableModel m_model;
	
	private final String[][] kategories = {
			{"Allgemein", "0"},
			{"Feier","1"},
			{"Geburtstag","2"},
			{"Meeting", "3"},
			{"Prüfung","4"},
			{"Urlaub", "5"}
	};
	
	/** Der Standardkonstruktor zum Erstellen des Dialogfensters. Er erwartet die zu bearbeitenden Daten, die Kategoriebox, die Tabelleneinstellungen als auch 
	 *  die Information, ob das Fenster modal ist, also eine höhere Priorität erhält als das Haupfenster. Dadurch kann dieses erst wieder bedient werden, wenn der Nutzer
	 *  die Editierung abschließt.
	 * 
	 * @param daten - Daten der ausgewählten Tabellenzeile
	 * @param categorybox - mögliche Kategorien
	 * @param model - Tabelleneinstellungen
	 * @param modal - Priorität des Fensters
	 */
	public EditDialog(Object[] daten, JComboBox categorybox, DefaultTableModel model, boolean modal) {

		this.setTitle("Bearbeitung");
		Container content = getContentPane();
		content.setLayout(null);
		m_category = categorybox;
		m_daten = daten;
		m_model = model;
		setModal(modal);
		setTask(content);
		
	}
	
	/** positioniert die Elemente und fügt sie dem Dialogfenster hinzu
	 * 
	 * @param content - Verweis auf den Inhalt des Dialogfensters (den Contentbereich)
	 */
	public void setTask(Container content)
	{
		JLabel text = new JLabel("momentaner Termin:");
		text.setBounds(25, 25, 130, 20);
		content.add(text);
		text = new JLabel("Kategorie");
		text.setBounds(50, 50, 100, 20);
		content.add(text);
		
		final String kategorie = (String) m_daten[0];
		for(int i = 0; i < kategories.length; ++i)
		{
			if(kategories[i][0].equals(kategorie))
			{
				m_category.setSelectedIndex(Integer.parseInt(kategories[i][1]));
			}
		}
		m_category.setBounds(50, 75, 100, 20);
		content.add(m_category);
		
		text = new JLabel("Titel");
		text.setBounds(200, 50, 100, 20);
		content.add(text);
		
		text = new JLabel("Beschreibung");
		text.setBounds(200, 110, 100, 20);
		content.add(text);
		
		text = new JLabel("Datum (dd.mm.yyyy)");
		text.setBounds(600, 50, 150, 20);
		content.add(text);
		
		text = new JLabel("Uhrzeit (hh:mm)");
		text.setBounds(600, 110, 150, 20);
		content.add(text);
		
		// Titel
		final JTextField titel = new JTextField();
		titel.setBounds(200, 75, 350, 20);
		titel.setText(m_daten[1].toString());
		content.add(titel);
		
		// Datum
		final JTextField date = new JTextField();
		date.setBounds(600, 75, 100, 20);
		date.setText(m_daten[2].toString());
		content.add(date);
		
		// Uhrzeit
		final JTextField time = new JTextField();
		time.setBounds(600, 135, 100, 20);
		time.setText(m_daten[3].toString());
		content.add(time);
		
		// Beschreibung		
		final JTextField description = new JTextField();
		description.setBounds(200, 135, 350, 20);
		description.setText(m_daten[4].toString());
		content.add(description);
		
		// EditButton
		JButton editButton = new JButton("Editieren");
		editButton.setBounds(640, 175, 100, 30);
		editButton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				String name = e.getActionCommand();
				if (name.equals("Editieren")){
					// Zwischenspeicherung der neuen Daten
					Object[] datenNow = {m_category.getSelectedItem(), titel.getText(), date.getText(), time.getText(), description.getText()};	
					// Editiere den Eintrag 
					ParseXml.editXML(m_daten, datenNow);
					m_model.setRowCount(0);	// clear table
					
					ParseXml data = new ParseXml();
					try {
							data.readXml();		// read new table content
					} catch (ParserConfigurationException err) {
						// TODO Auto-generated catch block
						err.printStackTrace();
					}
					// Beende das Fenster
					setVisible(false);
					dispose();
				}
			}
		});
		content.add(editButton);
		pack();
		
	}
	
}
