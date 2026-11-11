# Testnachweis – Zwischenstand nach Schritt 5

Datum: 11.11.2026  
Umgebung: OpenJDK 21; eigenständige Java-Testklassen ohne externe Bibliotheken.

Die bestehenden Tests für `Schulung`, `Anfrage`, `Kalkulationsregeln`, `Angebot` und `Angebotsrechner` wurden erneut ausgeführt. Hinzu kommen Integrationstests mit simulierten Konsoleneingaben.

| ID | Ausgangssituation | Eingabe | Erwartetes Ergebnis | Tatsächliches Ergebnis | Status |
|---|---|---|---|---|---|
| UI01 | Normaler Kalkulationsdurchlauf | Auswahl 3, 12 TN, `n` | Java Grundlagen; 10 Tage; 8.505,00 € Gesamt; 708,75 € je TN | Alle geforderten Werte korrekt ausgegeben | Bestanden |
| UI02 | Fehlerhafte Schulungsauswahl | `abc`, `0`, `6`, danach `3` | Drei Fehlermeldungen; anschließend gültige Auswahl | Drei Fehlermeldungen; danach Kalkulation | Bestanden |
| UI03 | Fehlerhafte Teilnehmerzahl | `0`, `16`, `7.5`, `xyz`, danach `12` | Vier Fehlermeldungen; vor gültiger Eingabe kein Angebot | Vier Fehlermeldungen; ein gültiges Angebot | Bestanden |
| UI04 | Mehrere Anfragen | Excel, 1 TN, `ja`; SQL, 6 TN, `nein` | Zwei verschiedene Berechnungen: 4.675,00 € und 8.835,00 € | Beide Werte korrekt ausgegeben | Bestanden |
| UI05 | Ungültige Antwort auf Wiederholungsfrage | `vielleicht`, dann `j` | Fehlermeldung; neue Anfrage möglich | Wiederholungsfrage korrekt behandelt | Bestanden |
| UI06 | Höchstwert und Rabatt | Windows Server Grundlagen, 15 TN | 10 % Rabatt, 8.572,50 € Angebotspreis | Entsprechender Gesamtbetrag ausgegeben | Bestanden |
| UI07 | Unerwartetes Ende der Eingabe | Eingabe endet nach einer vollständigen Berechnung | Programm beendet kontrolliert und ohne Endlosschleife | Kontrollierter Abbruch mit Meldung | Bestanden |
| UI08 | Text-, Überlauf- und Leerwerte | Sehr große Menünummer, -1 und leere Teilnehmerzahl | Ungültige Eingaben erkannt | Erwartete Fehlermeldungen | Bestanden |
| UI09 | Fehlende Abhängigkeiten | `null` als Scanner bzw. Angebotsrechner | Konstruktor wirft `NullPointerException` | Beide Exceptions erkannt | Bestanden |

**Automatisiert ausgeführte Einzelprüfungen:**

- 14 Grundklassen-Prüfungen
- 23 Kalkulationsregeln-Prüfungen
- 32 Angebot-Prüfungen
- 30 Angebotsrechner-Prüfungen
- 34 KonsolenUI-Prüfungen

**Insgesamt: 133/133 erfolgreich.**

Die Tabellen beschreiben einen technischen Zwischenstand. Die optionale Erweiterung `Angebotsvergleich` und ihre Integrations-/Abnahmetests sind noch nicht umgesetzt und deshalb nicht als bestanden ausgewiesen.
