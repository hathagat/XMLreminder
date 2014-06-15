package de.hathagat.XMLreminder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1470518963314635877L;

	JTextField textField1;
	JTextField textField2;
	JButton button1;
	
    static String[] titles = new String[]{ "Termin ID", "Titel", "Datum", "Uhrzeit", "Beschreibung" };	// titles for table columns
    final static DefaultTableModel model = new DefaultTableModel( titles, 0 );

	public Gui() {
		this.setTitle("XML-Reminder");
		this.getContentPane().setLayout(null);
		this.initWindow();
		this.addWindowListener(new WindowListener() {
			public void windowClosed(WindowEvent arg0) {
			}
			public void windowActivated(WindowEvent e) {
			}
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			public void windowDeactivated(WindowEvent e) {
			}
			public void windowDeiconified(WindowEvent e) {
			}
			public void windowIconified(WindowEvent e) {
			}
			public void windowOpened(WindowEvent e) {
			}
		});
	}
	
	protected void initWindow() {
		tasks();
		readText();
		table();

		JButton quitButton = new JButton("Beenden");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		quitButton.setBounds(640, 500, 110, 30);
		this.getContentPane().add(quitButton);

		this.pack();
	}
	
	public void table() {
		 JTable table = new JTable( model );          
      JScrollPane scrollPane = new JScrollPane(table);

      scrollPane.setBounds(15, 250, 600, 200);
      
      this.getContentPane().add(scrollPane);
	}
	
	public void tasks() {
		// Options for the JComboBox
		String[] taskOptions = { "Allgemein", "Feier", "Geburtstag", "Meeting",
				"Prüfung", "Urlaub" };

		// Options for the JList
		String[] days = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				"21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
				"31" };

		// The first JPanel contains a JLabel and JCombobox
		final JPanel comboPanel = new JPanel();
		JLabel comboLbl = new JLabel("Terminart");
		JComboBox task = new JComboBox(taskOptions);

		comboPanel.add(comboLbl);
		comboPanel.add(task);

		// Create the second JPanel. Add a JLabel and JList and
		// make use the JPanel is not visible.
		final JPanel listPanel = new JPanel();

		JLabel listLbl = new JLabel("Tage");
		JList dayCal = new JList(days);
		dayCal.setLayoutOrientation(JList.HORIZONTAL_WRAP);

		listPanel.add(listLbl);
		listPanel.add(dayCal);

		comboPanel.setBounds(400, 15, 150, 60);
		listPanel.setBounds(550, 15, 150, 200);

		this.getContentPane().add(comboPanel);
		this.getContentPane().add(listPanel);
	}

	public void readText() {

		textField1 = new JTextField();
		textField2 = new JTextField();

		button1 = new JButton("Eintragen");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				button1Clicked();
			}
		});

		textField1.setBounds(15, 15, 200, 25);
		textField2.setBounds(15, 85, 200, 25);
		button1.setBounds(250, 110, 100, 30);

		this.getContentPane().add(textField1);
		this.getContentPane().add(textField2);
		this.getContentPane().add(button1);
	}

	public void button1Clicked() {
		double number = 0;
		try {
			number = Double.parseDouble(textField1.getText());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			number = -1;
		}
		if (number >= 0) {

			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			String ausgabe = nf.format(number);

			textField2.setText(ausgabe);
		} else {
			textField2.setText("Eingabe ist nicht in Ordnung.");
		}
	}
}
