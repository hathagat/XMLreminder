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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1470518963314635877L;
	
	// Options for the JComboBox
	String[] categoryOptions;
	
	JLabel taskLabel;		
	JLabel categoryLabel;
	JLabel titleLabel;
	JLabel dateLabel;
	JLabel timeLabel;
	JLabel descriptionLabel;
	
	JComboBox categoryBox;
	JTextField titleText;
	JTextField dateText;
	JTextField timeText;
	JTextField descriptionText;
	
	JButton insertButton = new JButton("Eintragen");
	
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
		setTask();
		xmlTable();

		JButton quitButton = new JButton("Beenden");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		quitButton.setBounds(640, 500, 100, 30);
		this.getContentPane().add(quitButton);

		this.pack();
	}
	
	public void xmlTable() {
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);

		scrollPane.setBounds(15, 250, 725, 200);

		this.getContentPane().add(scrollPane);
	}
	
	public void setTask() {
		// Options for the JComboBox
		String[] categoryOptions = { "Allgemein", "Feier", "Geburtstag", "Meeting", "Prüfung", "Urlaub" };
		
		taskLabel = new JLabel("neuer Termin");		
		categoryLabel = new JLabel("Kategorie");
		titleLabel = new JLabel("Titel");
		dateLabel = new JLabel("Datum");
		timeLabel = new JLabel("Uhrzeit");
		descriptionLabel = new JLabel("Beschreibung");
		
		categoryBox = new JComboBox(categoryOptions);
		titleText = new JTextField();
		dateText = new JTextField();
		timeText = new JTextField();
		descriptionText = new JTextField();
		
		insertButton = new JButton("Eintragen");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertButtonClicked();
			}
		});
		
		taskLabel.setBounds(25, 25, 100, 20);
		categoryLabel.setBounds(50, 50, 100, 20);
		titleLabel.setBounds(200, 50, 100, 20);
		dateLabel.setBounds(600, 50, 100, 20);
		timeLabel.setBounds(600, 110, 100, 20);
		descriptionLabel.setBounds(200, 110, 100, 20);
	
		categoryBox.setBounds(50, 75, 100, 20);
		titleText.setBounds(200, 75, 350, 20);
		dateText.setBounds(600, 75, 100, 20);
		timeText.setBounds(600, 135, 100, 20);
		descriptionText.setBounds(200, 135, 350, 20);

		
		insertButton.setBounds(640, 175, 100, 30);

		this.getContentPane().add(taskLabel);
		this.getContentPane().add(categoryLabel);
		this.getContentPane().add(titleLabel);
		this.getContentPane().add(dateLabel);
		this.getContentPane().add(timeLabel);
		this.getContentPane().add(descriptionLabel);
		
		this.getContentPane().add(categoryBox);
		this.getContentPane().add(titleText);
		this.getContentPane().add(dateText);
		this.getContentPane().add(timeText);
		this.getContentPane().add(descriptionText);
		
		this.getContentPane().add(insertButton);
	}

	public void insertButtonClicked() {
		double number = 0;
		try {
			number = Double.parseDouble(titleText.getText());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			number = -1;
		}
		if (number >= 0) {

			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			String ausgabe = nf.format(number);

			dateText.setText(ausgabe);
		} else {
			dateText.setText("Eingabe ist nicht in Ordnung.");
		}
	}
}
