# Angebotskalkulation – Projektphase 4 / Schritt 5

## Projektstand

Dieser Stand basiert auf dem vollständigen Archiv aus **Schritt 4**. Die bisherigen Fachklassen, Tests und UML-Dateien wurden beibehalten.

**Neu in Schritt 5:**

- `KonsolenUI.java`: Schulungsmenü, Eingabe einer Teilnehmerzahl, Fehlerbehandlung, Darstellung der vollständigen Kalkulation und wiederholte Anfragen.
- `Main.java`: startet die Anwendung und verbindet `Kalkulationsregeln`, `Angebotsrechner` und `KonsolenUI`.
- `KonsolenUITest.java`: automatisierte Tests des Konsolenablaufs, der Eingabeprüfung und der formatierten Ausgabe.

**Noch ausstehend:** `Angebotsvergleich` und seine Einbindung als optionale Erweiterung, vollständige Abnahmetests und die abschließende Projektdokumentation.

Die Klassendiagramm-Datei `v3` enthält bereits die **geplante** Erweiterung `Angebotsvergleich` und die Methode `zeigeVergleich`, die erst im nächsten Schritt implementiert werden. Bis dahin ist das Diagramm eine Zielarchitektur und kein exaktes Abbild des aktuellen Quellcodes.

## Architektur

- `Schulung` (`enum`): fünf feste Kurse mit Schulungsdauer.
- `Anfrage`: gültige, unveränderliche Kundenanfrage (1–15 Teilnehmer).
- `Kalkulationsregeln`: feste Kostensätze und Rabattstaffel.
- `Angebotsrechner`: vollständige Kalkulationslogik, keine Benutzeroberfläche.
- `Angebot`: unveränderliches Objekt mit zehn Feldern, hält die berechneten Ergebnisse.
- `KonsolenUI`: Benutzerinteraktion, Darstellung und Wiederholung; keine Preisformeln.
- `Main`: Einstiegspunkt und Zusammenbau der Komponenten.

## Anwendung starten – Windows PowerShell

Voraussetzung: installiertes JDK (getestet mit OpenJDK 21). Im entpackten Ordner `Angebotskalkulation_Schritt5` ausführen:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
$javaDateien = @(Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
javac -encoding UTF-8 -d out $javaDateien
java -cp out angebotskalkulation.Main
```

Das Menü zeigt die Schulungen 1–5. Danach wird eine ganze Teilnehmerzahl von 1 bis 15 abgefragt. Nach der Berechnung kann mit `j` oder `ja` eine neue Anfrage gestartet oder mit `n` beziehungsweise `nein` das Programm beendet werden.

**Kontrollbeispiel:** `3` (Java Grundlagen) → `12` Teilnehmer → `n`. Erwartetes Ergebnis: 8.505,00 € Gesamtpreis und 708,75 € je Teilnehmer.

Wenn Umlaute oder das Euro-Zeichen in einer älteren Windows-Konsole falsch dargestellt werden, kann vor dem Start `chcp 65001` (UTF-8) aktiviert werden. Die Datei- und Quelltextkodierung ist UTF-8.

## Tests starten – Windows PowerShell

Nach der Kompilierung:

```powershell
java -cp out angebotskalkulation.GrundklassenTest
java -cp out angebotskalkulation.KalkulationsregelnTest
java -cp out angebotskalkulation.AngebotTest
java -cp out angebotskalkulation.AngebotsrechnerTest
java -cp out angebotskalkulation.KonsolenUITest
```

**Testergebnis (OpenJDK 21): 133 erfolgreiche Einzelprüfungen:**

| Testklasse | Prüfungen |
|---|---:|
| GrundklassenTest | 14 |
| KalkulationsregelnTest | 23 |
| AngebotTest | 32 |
| AngebotsrechnerTest | 30 |
| KonsolenUITest | 34 |
| **Gesamt** | **133** |

Die Tests sind eigenständige Java-Klassen und benötigen kein JUnit. Das Konsolentestprogramm verwendet künstliche Benutzereingaben und prüft die Ausgabetexte. Eine produktive Bedienung mit echter Tastatur ist zusätzlich über `Main` möglich.

## Projektstruktur

```text
src/main/java/angebotskalkulation/
    Schulung.java
    Anfrage.java
    Kalkulationsregeln.java
    Angebot.java
    Angebotsrechner.java
    KonsolenUI.java
    Main.java
src/test/java/angebotskalkulation/
    GrundklassenTest.java
    KalkulationsregelnTest.java
    AngebotTest.java
    AngebotsrechnerTest.java
    KonsolenUITest.java
docs/
    klassendiagramm_angebot_v3.drawio / .png
    usecase_angebotskalkulation.drawio / .png
    aktivitaetsdiagramm_angebotskalkulation.drawio / .png
    testnachweis_schritt4.md
    testnachweis_schritt5.md
```

## Nächster Schritt

Die Erweiterung `Angebotsvergleich` implementieren: Zwei Angebote für dieselbe Schulung mit unterschiedlichen oder identischen Teilnehmerzahlen vergleichen, Gesamtpreis- und Teilnehmerpreisdifferenz berechnen und das Ergebnis optional nach einer normalen Kalkulation über `KonsolenUI` anzeigen.
