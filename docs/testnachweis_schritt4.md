# Testnachweis – Zwischenstand nach Schritt 4

Datum: 11.11.2026  
Umgebung: OpenJDK 21, Tests als eigenständige Java-Klassen (ohne JUnit).

Die folgenden Ergebnisse wurden durch tatsächliche Ausführung von `AngebotsrechnerTest` gegen die Implementierung überprüft. Die komplette Konsolenanwendung ist noch nicht Bestandteil dieser Tests.

| ID | Ausgangssituation | Eingabe | Erwartetes Ergebnis | Tatsächliches Ergebnis | Status |
|---|---|---|---|---|---|
| K01 | Kontrollrechnung aus der Aufgabenstellung | Java Grundlagen, 12 TN | 9.450,00 € Zwischensumme; 945,00 € Rabatt; 8.505,00 € Gesamt; 708,75 € je TN | Werte stimmen überein | Bestanden |
| K02 | Schulung mit 5 Tagen, kein Rabatt | Excel Aufbau, 1 TN | 3.250,00 € Dozent; 1.250,00 € Raum; 4.675,00 € Gesamt | Werte stimmen überein | Bestanden |
| K03 | Ende der ersten Rabattstufe | SQL Grundlagen, 5 TN | 0 %; 9.275,00 € Gesamt | 9.275,00 € | Bestanden |
| K04 | Beginn der zweiten Rabattstufe | SQL Grundlagen, 6 TN | 5 %; 8.835,00 € Gesamt | 8.835,00 € | Bestanden |
| K05 | Ende der zweiten Rabattstufe | SQL Grundlagen, 10 TN | 5 %; 8.930,00 € Gesamt | 8.930,00 € | Bestanden |
| K06 | Beginn der dritten Rabattstufe | SQL Grundlagen, 11 TN | 10 %; 8.482,50 € Gesamt | 8.482,50 € | Bestanden |
| K07 | Höchstwert der Teilnehmerzahl | SQL Grundlagen, 15 TN | 10 %; 8.572,50 € Gesamt | 8.572,50 € | Bestanden |
| K08 | Rundung bei der Division | Java Grundlagen, 7 TN | 8.858,75 € Gesamt; 1.265,54 € je TN | Werte stimmen überein | Bestanden |
| K09 | Mehrere aufeinanderfolgende Anfragen | Java 12 TN, danach Excel 12 TN | Java unverändert 8.505,00 €; Excel 4.455,00 € | Werte stimmen überein | Bestanden |
| K10 | Fehlerhafte Referenzen | null als Regeln oder Anfrage | NullPointerException | Erwartete Exception ausgelöst | Bestanden |

Insgesamt wurden **99 Einzelprüfungen** in den vier Testklassen erfolgreich ausgeführt: 14 Grundklassen, 23 Kalkulationsregeln, 32 Angebot und 30 Angebotsrechner.

Hinweis: Dieser Nachweis dokumentiert einen technischen Zwischenstand und ersetzt nicht die abschließenden Konsolen-/Systemtests einschließlich Fehlerbehandlung, Nutzerführung und Erweiterung.
