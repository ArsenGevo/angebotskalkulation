# Angebotskalkulation – Schritt 6: Teilnehmervergleich

## Projektstand

Dieser Stand basiert auf dem vollständigen Projekt aus **Schritt 5**. Die optionale Erweiterung `Angebotsvergleich` ist jetzt implementiert und in den Konsolenablauf integriert. Die reguläre Angebotskalkulation bleibt unverändert.

**Neu in Schritt 6:**

- `Angebotsvergleich.java`: vergleicht zwei bereits berechnete Angebote derselben Schulung und ermittelt die Gesamtpreis- sowie die Teilnehmerpreisdifferenz.
- `KonsolenUI.java`: fragt nach jeder regulären Kalkulation, ob ein Teilnehmervergleich gewünscht wird, akzeptiert eine alternative Teilnehmerzahl (1–15) und zeigt beide Preise mit Differenzen an.
- `AngebotsvergleichTest.java`: überprüft Differenzberechnung, Rabattgrenzen, identische Teilnehmerzahlen, unterschiedliche Schulungen sowie fehlende Eingaben.
- `KonsolenUITest.java`: ergänzt um den vollständigen optionalen Vergleichsablauf und Eingabeprüfungen; bestehende Abläufe berücksichtigen jetzt die zusätzliche Ja/Nein-Frage.

**Noch ausstehend:** abschließende fachliche Abnahme, finale Dokumentation, Soll-Ist-Vergleich und mögliche Aktualisierungen der UML-Darstellung. Die vorhandenen UML-Dateien `v3` beschreiben die wesentlichen Klassen und Beziehungen; nicht alle privaten Hilfsmethoden und neuen Getter werden einzeln aufgeführt.

## Architektur und Berechnungsregeln

- `Schulung` (`enum`) – fünf fest vorgegebene Kurse.
- `Anfrage` – validierte, unveränderliche Kundenanfrage mit 1 bis 15 Teilnehmern.
- `Kalkulationsregeln` – vier Kostensätze und Rabattstaffel 0 %, 5 %, 10 %.
- `Angebotsrechner` – berechnet alle neun Ergebniswerte und erzeugt ein `Angebot`.
- `Angebot` – unveränderliches Ergebnisobjekt mit zehn Feldern, ohne eigene Preisberechnungen.
- `Angebotsvergleich` – hält zwei bereits kalkulierte Angebote desselben Kurses und berechnet nur die Differenzen.
- `KonsolenUI` – Menüführung, Eingabevalidierung, Darstellung sowie optionaler Vergleich.
- `Main` – Einstiegspunkt; initialisiert die Komponenten.

Die beiden Differenzen folgen derselben Vorzeichenkonvention:

```text
Differenz = alternatives Angebot - ursprüngliches Angebot
```

Ein **negativer Wert** bedeutet eine geringere Gesamtsumme bzw. einen geringeren Preis pro Teilnehmer beim alternativen Angebot. Gleiche Teilnehmerzahlen dürfen verglichen werden und liefern jeweils 0,00 € Differenz. Unterschiedliche Kurse können nicht verglichen werden.

Die Vergleichsklasse ändert keine Kalkulationswerte. `BigDecimal` wird zur Geldberechnung verwendet; die bereits berechneten Einzelpreise werden mit ihrer festgelegten Cent-Rundung verglichen.

## Anwendung starten (Windows PowerShell)

Voraussetzung: JDK 21 oder kompatible Version. Im entpackten Projektordner ausführen:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
$javaDateien = @(Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
javac -encoding UTF-8 -d out $javaDateien
java -cp out angebotskalkulation.Main
```

**Eingabebeispiel:**

```text
Schulung wählen (1-5): 3
Teilnehmerzahl (1-15): 8
... Angebotspreis: 8.882,50 €
Teilnehmervergleich durchführen? (j/n): j
Alternative Teilnehmerzahl (1-15): 12
... Gesamtpreisdifferenz: -377,50 €
... Teilnehmerpreisdifferenz: -401,56 €
Weitere Anfrage berechnen? (j/n): n
```

Wer keinen Vergleich möchte, gibt auf die neue Frage `n` bzw. `nein` ein. Anschließend kann unabhängig davon eine neue reguläre Kalkulation gestartet werden.

Bei Problemen mit Umlauten oder dem Euro-Zeichen kann eine ältere Windows-Konsole vor dem Programmstart mit `chcp 65001` auf UTF-8 umgestellt werden. Alternativ kann Java mit `-Dstdout.encoding=UTF-8` gestartet werden, wenn die Konsole UTF-8 unterstützt.

## Tests starten

Nach der Kompilierung:

```powershell
java -cp out angebotskalkulation.GrundklassenTest
java -cp out angebotskalkulation.KalkulationsregelnTest
java -cp out angebotskalkulation.AngebotTest
java -cp out angebotskalkulation.AngebotsrechnerTest
java -cp out angebotskalkulation.KonsolenUITest
java -cp out angebotskalkulation.AngebotsvergleichTest
```

**Ergebnis unter OpenJDK 21 (11.11.2026): 175/175 Einzelprüfungen erfolgreich.**

| Testklasse | Prüfungen |
|---|---:|
| GrundklassenTest | 14 |
| KalkulationsregelnTest | 23 |
| AngebotTest | 32 |
| AngebotsrechnerTest | 30 |
| KonsolenUITest | 56 |
| AngebotsvergleichTest | 20 |
| **Gesamt** | **175** |

Es handelt sich um eigenständige Java-Testklassen ohne JUnit. Die GUI-freien Konsolentests speisen simulierte Benutzereingaben ein und prüfen Ausgaben. Zusätzlich wurde `Main` mit einem vollständigen Vergleichsszenario über Standardeingabe gestartet.

## Projektstruktur

```text
src/main/java/angebotskalkulation/
    Schulung.java
    Anfrage.java
    Kalkulationsregeln.java
    Angebot.java
    Angebotsrechner.java
    Angebotsvergleich.java
    KonsolenUI.java
    Main.java
src/test/java/angebotskalkulation/
    GrundklassenTest.java
    KalkulationsregelnTest.java
    AngebotTest.java
    AngebotsrechnerTest.java
    KonsolenUITest.java
    AngebotsvergleichTest.java
docs/
    klassendiagramm_angebot_v3.drawio / .png
    usecase_angebotskalkulation.drawio / .png
    aktivitaetsdiagramm_angebotskalkulation.drawio / .png
    testnachweis_schritt4.md
    testnachweis_schritt5.md
    testnachweis_schritt6.md
    implementierung_erweiterung_schritt6.md
```
