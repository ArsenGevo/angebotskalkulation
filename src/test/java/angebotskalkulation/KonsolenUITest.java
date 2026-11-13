package angebotskalkulation;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

/** Integrationstests für Eingabe, Ausgabe und den Wiederholungsablauf ohne JUnit. */
public final class KonsolenUITest {
    private static int pruefungen;

    private static void enthaelt(String ausgabe, String erwartet, String beschreibung) {
        pruefungen++;
        if (!ausgabe.contains(erwartet)) {
            throw new AssertionError(beschreibung + " — nicht gefunden: " + erwartet
                    + "\nAusgabe:\n" + ausgabe);
        }
    }

    private static void istGleich(int erwartet, int tatsaechlich, String beschreibung) {
        pruefungen++;
        if (erwartet != tatsaechlich) {
            throw new AssertionError(beschreibung + ": erwartet " + erwartet
                    + ", tatsächlich " + tatsaechlich);
        }
    }

    private static void erwartetNullPointer(Runnable aktion, String beschreibung) {
        pruefungen++;
        try {
            aktion.run();
        } catch (NullPointerException erwartet) {
            return;
        }
        throw new AssertionError("NullPointerException erwartet: " + beschreibung);
    }

    private static int zaehle(String text, String muster) {
        int anzahl = 0;
        for (int pos = 0; (pos = text.indexOf(muster, pos)) != -1; pos += muster.length()) {
            anzahl++;
        }
        return anzahl;
    }

    private static String laufeMitEingaben(String eingaben) {
        PrintStream original = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (PrintStream ausgabe = new PrintStream(bytes, true, StandardCharsets.UTF_8);
                Scanner scanner = new Scanner(eingaben)) {
            System.setOut(ausgabe);
            new KonsolenUI(scanner,
                    new Angebotsrechner(new Kalkulationsregeln())).starten();
        } finally {
            System.setOut(original);
        }
        return bytes.toString(StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        String java12 = laufeMitEingaben("3\n12\nn\nn\n");
        enthaelt(java12, "Java Grundlagen", "Schulung aus dem Menü gewählt");
        enthaelt(java12, "Schulungsdauer: 10 Tage", "Dauer angezeigt");
        enthaelt(java12, "Teilnehmerzahl: 12", "Teilnehmerzahl angezeigt");
        enthaelt(java12, "6.500,00 €", "Dozentenkosten formatiert");
        enthaelt(java12, "2.500,00 €", "Raumkosten formatiert");
        enthaelt(java12, "300,00 €", "Unterlagenkosten formatiert");
        enthaelt(java12, "150,00 €", "Verwaltungspauschale formatiert");
        enthaelt(java12, "9.450,00 €", "Zwischensumme formatiert");
        enthaelt(java12, "Rabattsatz: 10 %", "Rabatt angezeigt");
        enthaelt(java12, "945,00 €", "Rabattbetrag formatiert");
        enthaelt(java12, "8.505,00 €", "Angebotspreis formatiert");
        enthaelt(java12, "708,75 €", "Preis pro Teilnehmer formatiert");
        enthaelt(java12, "Programm beendet.", "Normales Ende");
        istGleich(1, zaehle(java12, "--- Angebotskalkulation ---"), "Genau ein Angebot");

        String fehler = laufeMitEingaben("abc\n0\n6\n3\n0\n16\n7.5\nxyz\n12\nn\nn\n");
        istGleich(3, zaehle(fehler, "Ungültige Schulungsauswahl"),
                "Ungültige Schulungen werden wiederholt");
        istGleich(4, zaehle(fehler, "Ungültige Teilnehmerzahl"),
                "Ungültige Teilnehmerzahlen werden wiederholt");
        enthaelt(fehler, "8.505,00 €", "Nach Fehleingaben richtige Kalkulation");
        istGleich(1, zaehle(fehler, "--- Angebotskalkulation ---"),
                "Keine Kalkulation mit ungültigen Zahlen");

        String wiederholung = laufeMitEingaben("1\n1\nn\nja\n2\n6\nn\nnein\n");
        istGleich(2, zaehle(wiederholung, "--- Angebotskalkulation ---"),
                "Neue Anfrage im selben Programmlauf");
        enthaelt(wiederholung, "Excel Aufbau", "Erste Anfrage Excel");
        enthaelt(wiederholung, "4.675,00 €", "Excel ohne Rabatt");
        enthaelt(wiederholung, "SQL Grundlagen", "Zweite Anfrage SQL");
        enthaelt(wiederholung, "8.835,00 €", "SQL sechs Personen mit Rabatt");
        enthaelt(wiederholung, "Rabattsatz: 0 %", "Nullrabatt angezeigt");
        enthaelt(wiederholung, "Rabattsatz: 5 %", "Fünfprozent-Rabatt angezeigt");

        String antwort = laufeMitEingaben("1\n1\nn\nvielleicht\nj\n5\n15\nn\nn\n");
        istGleich(1, zaehle(antwort, "Ungültige Eingabe. Bitte j oder n"),
                "Wiederholungsfrage prüft Antworten");
        istGleich(2, zaehle(antwort, "--- Angebotskalkulation ---"),
                "Nach falscher Antwort geht Wiederholung");
        enthaelt(antwort, "8.572,50 €", "15 Teilnehmer mit zehn Prozent Rabatt");

        String ende = laufeMitEingaben("1\n1\nn\n");
        enthaelt(ende, "Keine weitere Eingabe verfügbar", "EOF beendet sauber");
        istGleich(1, zaehle(ende, "--- Angebotskalkulation ---"), "EOF keine Extraschleife");

        String grosseZahl = laufeMitEingaben("99999999999999\n1\n-1\n\n1\nn\nn\n");
        istGleich(1, zaehle(grosseZahl, "Ungültige Schulungsauswahl"),
                "Überlauf in Menüeingabe behandelt");
        istGleich(2, zaehle(grosseZahl, "Ungültige Teilnehmerzahl"),
                "Negative und leere Eingabe behandelt");

        String vergleich = laufeMitEingaben("3\n8\nj\n12\nn\n");
        enthaelt(vergleich, "--- Teilnehmervergleich ---", "Vergleichsausgabe gestartet");
        enthaelt(vergleich, "Ursprüngliche Teilnehmerzahl: 8", "Ausgangszahl im Vergleich");
        enthaelt(vergleich, "Alternative Teilnehmerzahl: 12", "Alternative Zahl im Vergleich");
        enthaelt(vergleich, "8.882,50 €", "Ausgangs-Gesamtpreis im Vergleich");
        enthaelt(vergleich, "8.505,00 €", "Alternativer Gesamtpreis im Vergleich");
        enthaelt(vergleich, "1.110,31 €", "Ausgangs-Einzelpreis im Vergleich");
        enthaelt(vergleich, "708,75 €", "Alternativer Einzelpreis im Vergleich");
        enthaelt(vergleich, "-377,50 €", "Negative Gesamtpreisdifferenz");
        enthaelt(vergleich, "-401,56 €", "Negative Teilnehmerpreisdifferenz");
        enthaelt(vergleich, "Programm beendet.", "Programmende nach Vergleich");
        istGleich(1, zaehle(vergleich, "--- Angebotskalkulation ---"),
                "Alternative wird nicht als regulärer Durchlauf ausgegeben");

        String vergleichFehler = laufeMitEingaben("2\n10\nvielleicht\nJA\n0\n16\nabc\n11\nn\n");
        istGleich(1, zaehle(vergleichFehler, "Ungültige Eingabe. Bitte j oder n"),
                "Vergleichsfrage validiert Antwort");
        istGleich(3, zaehle(vergleichFehler, "Ungültige Teilnehmerzahl"),
                "Alternative Teilnehmerzahl validiert");
        enthaelt(vergleichFehler, "-447,50 €", "Rabattgrenze 10/11 im Vergleich");

        String gleicherVergleich = laufeMitEingaben("3\n12\nja\n12\nn\n");
        enthaelt(gleicherVergleich, "0,00 €", "Gleiche Teilnehmerzahl ergibt Nulldifferenz");
        istGleich(1, zaehle(gleicherVergleich, "--- Teilnehmervergleich ---"),
                "Gleiche Teilnehmerzahl erlaubt");

        String ohneVergleich = laufeMitEingaben("1\n1\nnein\nnein\n");
        istGleich(0, zaehle(ohneVergleich, "--- Teilnehmervergleich ---"),
                "Vergleich ist optional");
        enthaelt(ohneVergleich, "Programm beendet.", "Normaler Ablauf ohne Vergleich");

        String weitereNachVergleich = laufeMitEingaben("3\n8\nj\n12\nja\n1\n1\nn\nn\n");
        istGleich(1, zaehle(weitereNachVergleich, "--- Teilnehmervergleich ---"),
                "Vergleich nur bei erster Anfrage");
        istGleich(2, zaehle(weitereNachVergleich, "--- Angebotskalkulation ---"),
                "Weitere reguläre Anfrage nach Vergleich");
        enthaelt(weitereNachVergleich, "Excel Aufbau", "Neue Schulung nach Vergleich möglich");

        String endeVergleich = laufeMitEingaben("3\n8\nj\n");
        enthaelt(endeVergleich, "Keine weitere Eingabe verfügbar",
                "EOF bei alternativer Teilnehmerzahl beendet sicher");

        erwartetNullPointer(
                () -> new KonsolenUI(null,
                        new Angebotsrechner(new Kalkulationsregeln())),
                "Scanner fehlt");
        try (Scanner scanner = new Scanner("")) {
            erwartetNullPointer(() -> new KonsolenUI(scanner, null), "Rechner fehlt");
        }

        System.out.println("Alle " + pruefungen + " KonsolenUI-Tests bestanden.");
    }
}
