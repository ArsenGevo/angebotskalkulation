package angebotskalkulation;

import java.math.BigDecimal;

/** Eigenständige Tests für Kalkulationsregeln ohne zusätzliche Testbibliotheken. */
public final class KalkulationsregelnTest {
    private static int ausgefuehrteTests;

    private static void pruefeBetrag(BigDecimal erwartet, BigDecimal tatsaechlich,
                                    String beschreibung) {
        ausgefuehrteTests++;
        if (erwartet.compareTo(tatsaechlich) != 0) {
            throw new AssertionError(beschreibung + ": erwartet " + erwartet
                    + ", tatsächlich " + tatsaechlich);
        }
    }

    private static void erwartetUngueltigeTeilnehmerzahl(
            Kalkulationsregeln regeln, int teilnehmerzahl) {
        ausgefuehrteTests++;
        try {
            regeln.ermittleRabattsatz(teilnehmerzahl);
        } catch (IllegalArgumentException erwartet) {
            return;
        }
        throw new AssertionError("Ungültige Teilnehmerzahl akzeptiert: " + teilnehmerzahl);
    }

    public static void main(String[] args) {
        Kalkulationsregeln regeln = new Kalkulationsregeln();

        pruefeBetrag(new BigDecimal("650.00"), regeln.getDozentenTagessatz(),
                "Dozentenkosten pro Tag");
        pruefeBetrag(new BigDecimal("250.00"), regeln.getRaumTagessatz(),
                "Raumkosten pro Tag");
        pruefeBetrag(new BigDecimal("25.00"), regeln.getUnterlagenProTeilnehmer(),
                "Unterlagen pro Teilnehmer");
        pruefeBetrag(new BigDecimal("150.00"), regeln.getVerwaltungspauschale(),
                "Verwaltungspauschale");

        // Testet alle 15 zulässigen Teilnehmerzahlen, auch die Rabattgrenzen 5/6 und 10/11.
        for (int teilnehmerzahl = 1; teilnehmerzahl <= 15; teilnehmerzahl++) {
            String satz = teilnehmerzahl <= 5 ? "0.00"
                    : teilnehmerzahl <= 10 ? "0.05" : "0.10";
            pruefeBetrag(new BigDecimal(satz), regeln.ermittleRabattsatz(teilnehmerzahl),
                    "Rabattsatz bei " + teilnehmerzahl + " Teilnehmern");
        }

        erwartetUngueltigeTeilnehmerzahl(regeln, -1);
        erwartetUngueltigeTeilnehmerzahl(regeln, 0);
        erwartetUngueltigeTeilnehmerzahl(regeln, 16);
        erwartetUngueltigeTeilnehmerzahl(regeln, 99);

        System.out.println("Alle " + ausgefuehrteTests
                + " Kalkulationsregeln-Tests bestanden.");
    }
}
