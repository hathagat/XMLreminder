XML Reminder
===========

Beim XML Reminder handelt es sich um eine Java Applikation, welche die Organisation im Alltag verbessern bzw. erleichtern soll. Der Benutzer erhält eine Möglichkeit seinen persönlichen Terminkalender in digitaler Form zu erstellen. 
Dieses Projekt wird im Rahmen der Lehrveranstaltung XML und Anwendungen entwickelt, um den Umgang mit dem XML Format zu festigen. Aus diesem Grund wird die komplette Datenverwaltung durch XML repräsentiert.

Die Erweiterung der Programmierkenntisse, insbesondere im Bereich grafische Benutzeroberflächen, sowie der praxisbezogene Umgang mit dem XML Format stellen die Motivation für dieses Projekt dar.

Im Folgenden wird zunächst auf die Rahmenbedingungen des erstellten Programms eingegangen. Darauf wird die Umsetzung detailliert dokumentiert und abschließend ein Fazit gezogen sowie ein Ausblick gewährt.



Die Grundvoraussetzung für das Projekt war, dass die Daten im XML-Format vorliegen und verarbeitet werden sollen. Nach kurzer Beratung war die Idee des XML Reminders geboren. Das Programm wurde in Java umgesetzt um die objektorientierten Programmierkenntnisse im Allgemeinen und bzgl. XML-Parsing im Speziellen zu festigen.

Der XML Reminder verfügt über eine grafische Benutzeroberfläche und verschiedene Optionen zum Bearbeiten von Terminen. Beim Programmstart wird der aktuelle Terminkalender aus der dem Programm zu Grunde liegenden XML Datei generiert und tabellarisch angezeigt. Die Tabelle enthält folgende Spalten:


-	Datum
-	Uhrzeit
-	Titel
-	Beschreibung
-	Kategorie
	

Sobald eine Änderung an den Terminen durchgeführt wurde, aktualisiert das Programm die XML Datei selbstständig und zeigt die aktualisierten Informationen entsprechend in der Tabelle an. Die dem Nutzer zur Verfügung stehenden Optionen sind wie folgt:


-	Hinzufügen eines neuen Termins
-	Bearbeitung eines bestehenden Termins
-	Löschen eines Termins


Jede Spalte der Tabelle entspricht einem Element in der XML Datei. Das Root-Element heißt tasks. Zusätzlich hat jeder Termin in der Datei noch eine eindeutige ID, welche als Attribut vergeben ist. Datum und Uhrzeit werden wegen der besseren Zuordnenbarkeit in einzelne Elemente gegliedert. Der Aufbau der XML Datei ist also wie folgt:


```xml
<tasks>
	<task ID="1">
		<category>		</category>
		<title>			</title>
		<day>			</day>
		<month>			</month>
		<year>			</year>
		<hour>			</hour>
		<minute>		</minute>
		<description>	</description>
	</task>
</tasks>
```


Das XML Reminder Programm besteht aus 3 Java-Klassen: Der Main, die lediglich der Initialisierung der Benutzeroberfläche dient, der Gui, in der das Layout und die Funktionalität der Benutzeroberfläche festgelegt sind und der ParseXml, die dazu dient sämtliche XML basierten Lese- und Schreiboperationen durchzuführen.

Die Benutzeroberfläche wird durch die Java Klassenbibliotheken AWT und Swing realisiert. Swing dient hier hauptsächlich der Visualisierung einzelner Elemente wie Buttons, der Tabelle oder des Fensters selbst, während mittels AWT die Nutzerinteraktion durch verschiedene Events realisiert wird.

Die XML Operationen werden mit Hilfe des Document Object Models (DOM) umgesetzt. Der XML-Zugriff erfolgt über einen Objektbaum, was zwar sehr komfortabel, aber nicht für große Dokumente geeignet ist, da stets der gesamte Inhalt der XML-Datei eingelesen und im Arbeitsspeicher vorgehalten werden muss. Im XML Reminder ist die zu erwartende Größe der Datei allerdings überschaubar. Zudem muss die Datei sowieso jedes Mal bei Programmstart komplett eingelesen werden, um die Termintabelle anzeigen zu können. Daher wurde sich für DOM entschieden, wodurch zudem die grundlegenden Parsing-Mechanismen gut gelernt werden konnten.

Bei der XML Datei wurde nicht nur darauf geachtet, dass sie Well-Formed ist, sondern sie wird auch mittels eines XML Schemas im Venetian Blind Design validiert. Somit ist klar festgelegt welche Elemente ein Termin enthalten darf, bzw. muss und wie der Aufbau eines solchen auszusehen hat. 


Der XML Reminder bietet dem Nutzer komfortable Möglichkeiten zur Verwaltung von Terminen. Die Erstellung des Programms sorgte für ein tieferes Verständnis wie Daten sinnvoll gespeichert werden können, der Struktur von XML-Dateien und vom Parsen ebendieser.

Da sämtliche zum Projekt gehörenden Daten öffentlich auf GitHub zugänglich sind, wird das Team die nun gelegte Basis nutzen, um anhand des XML Reminders fachrelevante Fertigkeiten auch mit anderen Kommilitonen zu verbessern und auszubauen. Das Programm kann so kontinuierlich ausgebaut und um neue, noch zu erlernende Features ergänzt werden.
