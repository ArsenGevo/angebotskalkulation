package angebotskalkulation;

import java.math.BigDecimal;

/**
 * Testet tatsächliche Angebotsberechnungen ohne externe Bibliotheken.
 * Die Erwartungswerte stammen aus der Aufgabenstellung bzw. sind manuell berechnet.
 */
public final class AngebotsrechnerTest {
    private static int ausgefuehrteTests;

    private static void pruefeBetrag(String erwartet, BigDecimal tatsaechlich, String beschreibung) {
        ausgefuehrteTests++;
        BigDecimal soll = new BigDecimal(erwartet);
        if (tatsaechlich == null || soll.compareTo(tatsaechlich) != 0) {
            throw new AssertionError(beschreibung + ": erwartet " + soll
                    + ", tatsächlich " + tatsaechlich);
        }
    }

    private static void pruefen(boolean bedingung, String beschreibung) {
        ausgefuehrteTests++;
        if (!bedingung) {
            throw new AssertionError("Fehlgeschlagen: " + beschreibung);
        }
    }

    private static void erwartetNullPointer(Runnable aktion, String beschreibung) {
        ausgefuehrteTests++;
        try {
            aktion.run();
        } catch (NullPointerException erwartet) {
            return;
        }
        throw new AssertionError("NullPointerException erwartet: " + beschreibung);
    }

    private static Angebot angebot(Angebotsrechner rechner, Schulung schulung, int teilnehmer) {
        return rechner.berechne(new Anfrage(schulung, teilnehmer));
    }

    public static void main(String[] args) {
        Kalkulationsregeln regeln = new Kalkulationsregeln();
        Angebotsrechner rechner = new Angebotsrechner(regeln);

        // Exakte Kontrollrechnung aus der Aufgabenstellung: Java, 12 Personen.
        Anfrage referenz = new Anfrage(Schulung.JAVA_GRUNDLAGEN, 12);
        Angebot java12 = rechner.berechne(referenz);
        pruefen(java12.getAnfrage() == referenz, "Anfrage im Ergebnis erhalten");
        pruefeBetrag("6500.00", java12.getDozentenkosten(), "Dozent: 10 x 650");
        pruefeBetrag("2500.00", java12.getRaumkosten(), "Raum: 10 x 250");
        pruefeBetrag("300.00", java12.getUnterlagenkosten(), "Unterlagen: 12 x 25");
        pruefeBetrag("150.00", java12.getVerwaltungspauschale(), "Verwaltung pro Auftrag");
        pruefeBetrag("9450.00", java12.getZwischensumme(), "Zwischensumme");
        pruefeBetrag("0.10", java12.getRabattsatz(), "Rabatt bei 12 Personen");
        pruefeBetrag("945.00", java12.getRabattbetrag(), "Rabattbetrag");
        pruefeBetrag("8505.00", java12.getAngebotspreis(), "Angebotspreis");
        pruefeBetrag("708.75", java12.getPreisProTeilnehmer(), "Preis pro Person");

        // Fünf Tage und kein Rabatt: anderes Schulungsformat.
        Angebot excel1 = angebot(rechner, Schulung.EXCEL_AUFBAU, 1);
        pruefeBetrag("3250.00", excel1.getDozentenkosten(), "Excel Dozenten 5 Tage");
        pruefeBetrag("1250.00", excel1.getRaumkosten(), "Excel Raum 5 Tage");
        pruefeBetrag("25.00", excel1.getUnterlagenkosten(), "Excel Unterlagen 1 Person");
        pruefeBetrag("4675.00", excel1.getZwischensumme(), "Excel Zwischensumme");
        pruefeBetrag("0.00", excel1.getRabattbetrag(), "Excel ohne Rabatt");
        pruefeBetrag("4675.00", excel1.getAngebotspreis(), "Excel Angebotspreis");
        pruefeBetrag("4675.00", excel1.getPreisProTeilnehmer(), "Excel pro Person");

        // Grenzwerte aller Rabattstaffeln, bei zehn Schulungstagen.
        pruefeBetrag("9275.00", angebot(rechner, Schulung.SQL_GRUNDLAGEN, 5)
                .getAngebotspreis(), "5 Personen: 0 Prozent");
        pruefeBetrag("8835.00", angebot(rechner, Schulung.SQL_GRUNDLAGEN, 6)
                .getAngebotspreis(), "6 Personen: 5 Prozent");
        pruefeBetrag("8930.00", angebot(rechner, Schulung.SQL_GRUNDLAGEN, 10)
                .getAngebotspreis(), "10 Personen: 5 Prozent");
        pruefeBetrag("8482.50", angebot(rechner, Schulung.SQL_GRUNDLAGEN, 11)
                .getAngebotspreis(), "11 Personen: 10 Prozent");
        pruefeBetrag("8572.50", angebot(rechner, Schulung.SQL_GRUNDLAGEN, 15)
                .getAngebotspreis(), "15 Personen: 10 Prozent");

        // Geteilte Preise müssen auf volle Cent gerundet werden (HALF_UP).
        Angebot java7 = angebot(rechner, Schulung.JAVA_GRUNDLAGEN, 7);
        pruefeBetrag("8858.75", java7.getAngebotspreis(), "7 Personen Gesamtpreis");
        pruefeBetrag("1265.54", java7.getPreisProTeilnehmer(), "7 Personen Rundung");
        pruefen(java7.getPreisProTeilnehmer().scale() == 2, "Teilnehmerpreis: zwei Nachkommastellen");
        pruefen(java7.getRabattbetrag().scale() == 2, "Rabattbetrag: zwei Nachkommastellen");

        // Ein Rechner muss Anfragen getrennt behandeln (keine Restdaten).
        Angebot erneut = angebot(rechner, Schulung.EXCEL_AUFBAU, 12);
        pruefeBetrag("4455.00", erneut.getAngebotspreis(), "Excel 12 Personen nach Java");
        pruefeBetrag("8505.00", java12.getAngebotspreis(), "Früheres Angebot unverändert");

        erwartetNullPointer(() -> new Angebotsrechner(null), "fehlende Kalkulationsregeln");
        erwartetNullPointer(() -> rechner.berechne(null), "fehlende Anfrage");

        System.out.println("Alle " + ausgefuehrteTests + " Angebotsrechner-Tests bestanden.");
    }
}
