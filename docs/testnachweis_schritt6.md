# Testnachweis – Zwischenstand nach Schritt 6

Datum: 11.11.2026  
Umgebung: OpenJDK 21; eigenständige Java-Testklassen ohne externe Bibliotheken.

Die bisherigen Tests aus Schritt 5 wurden an den um eine optionale Vergleichsfrage ergänzten Programmablauf angepasst und erneut ausgeführt. Die Grundberechnungen wurden nicht verändert.

| ID | Ausgangssituation | Eingabe | Erwartetes Ergebnis | Tatsächliches Ergebnis | Status |
|---|---|---|---|---|---|
| V01 | Vergleich nach einer Kalkulation | Java, 8 → 12 TN | 8.882,50 € → 8.505,00 €; Gesamtdifferenz −377,50 €; Teilnehmerpreisdifferenz −401,56 € | Beide Differenzen korrekt berechnet und angezeigt | Bestanden |
| V02 | Umgekehrte Vergleichsrichtung | Java, 12 → 8 TN | +377,50 € Gesamtdifferenz; +401,56 € Teilnehmerpreisdifferenz | Beide Vorzeichen korrekt | Bestanden |
| V03 | Gleiche Teilnehmerzahl | Java, 12 → 12 TN | Beide Differenzen 0,00 € | Beide Werte 0,00 € | Bestanden |
| V04 | Rabattgrenze 10/11 | SQL, 10 → 11 TN | Gesamtdifferenz −447,50 €; Teilnehmerpreisdifferenz −121,86 € | Beide Werte korrekt | Bestanden |
| V05 | Kurzer Kurs mit Extremwerten | Excel, 1 → 15 TN | Gesamtdifferenz −152,50 €; Teilnehmerpreisdifferenz −4.373,50 € | Beide Werte korrekt | Bestanden |
| V06 | Unterschiedliche Kurse | Angebot Java vs. SQL | `IllegalArgumentException` | Exception ausgelöst | Bestanden |
| V07 | Fehlendes Vergleichsangebot | `null` als Original oder Alternative | `NullPointerException` | Beide Nullwerte erkannt | Bestanden |
| UI10 | Optionale Vergleichsfrage abgelehnt | Excel, 1 TN, `nein`, `nein` | Keine Vergleichsausgabe, normale Beendigung | Keine Vergleichsausgabe, Programm beendet | Bestanden |
| UI11 | Fehler bei Eingabe der alternativen Teilnehmerzahl | SQL, 10 TN, `JA`, `0`, `16`, `abc`, `11` | Drei Fehlermeldungen; danach gültiger Vergleich | Drei Fehlermeldungen; Vergleich korrekt | Bestanden |
| UI12 | Ungültige Antwort auf Vergleichsfrage | `vielleicht`, dann `JA` | Fehlermeldung; Vergleich weiterhin möglich | Erwartetes Verhalten | Bestanden |
| UI13 | Neue reguläre Anfrage nach Vergleich | Java, 8 → 12; anschließend Excel, 1 | Ein Vergleich und zwei unabhängige Kalkulationen | Entsprechend ausgeführt | Bestanden |
| UI14 | Eingabeende während des Vergleichs | EOF bei alternativer Teilnehmerzahl | Kontrollierte Beendigung ohne Endlosschleife | Kontrollierte Beendigung | Bestanden |

**Automatisiert ausgeführte Einzelprüfungen:**

| Testklasse | Prüfungen |
|---|---:|
| GrundklassenTest | 14 |
| KalkulationsregelnTest | 23 |
| AngebotTest | 32 |
| AngebotsrechnerTest | 30 |
| KonsolenUITest | 56 |
| AngebotsvergleichTest | 20 |
| **Gesamt** | **175** |

**Ergebnis: 175/175 Einzelprüfungen erfolgreich.** Ein vollständiger Konsolendurchlauf über `Main` mit Java Grundlagen, 8 und 12 Teilnehmern wurde zusätzlich ausgeführt.

Dies ist ein technischer Zwischenstand. Eine fachliche Abnahme durch die Auftraggeberin und eine abschließende Soll-Ist-Bewertung liegen noch nicht vor.
