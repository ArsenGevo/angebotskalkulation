### 3.2 Implementierung – Ergänzung: Teilnehmervergleich

Als zusätzliche Funktion wurde der `Angebotsvergleich` implementiert. Nach einer regulären Kalkulation kann der Benutzer optional eine alternative Teilnehmerzahl für dieselbe Schulung eingeben. Beide Angebote werden mit dem bereits vorhandenen `Angebotsrechner` erstellt und anschließend verglichen.

Die Klasse `Angebotsvergleich` enthält Referenzen auf das ursprüngliche und das alternative Angebot. Sie prüft, ob beide Angebote dieselbe Schulung betreffen, und ermittelt die Differenzen der Gesamtpreise sowie der Preise pro Teilnehmer. Eine negative Differenz kennzeichnet einen niedrigeren Preis des alternativen Angebots. Gleiche Teilnehmerzahlen sind zugelassen und ergeben eine Differenz von null Euro.

Die Erweiterung wurde in die `KonsolenUI` integriert. Ungültige Eingaben werden wie bei der regulären Kalkulation zurückgewiesen. Nach der Vergleichsausgabe kann der Benutzer eine weitere Anfrage bearbeiten oder das Programm beenden. Die bestehende Berechnungslogik musste dafür nicht verändert werden.

### 3.4 Tests – Ergänzung: Teilnehmervergleich

Für die Erweiterung wurden zusätzliche automatisierte Prüfungen durchgeführt. Diese umfassen die Vergleichsrichtungen, gleiche Teilnehmerzahlen, die Rabattgrenze zwischen zehn und elf Teilnehmern, unzulässige Kurskombinationen und fehlerhafte Benutzereingaben. Außerdem wurden der optionale Ablauf sowie die Bearbeitung einer weiteren Anfrage nach einem Vergleich überprüft.

Bis zum Stand Schritt 6 wurden insgesamt 175 Einzelprüfungen erfolgreich durchgeführt. Die Ergebnisse sind im `testnachweis_schritt6.md` dokumentiert. Eine abschließende fachliche Abnahme steht noch aus.
