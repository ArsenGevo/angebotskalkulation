package angebotskalkulation;

import java.math.BigDecimal;

/**
 * Feste Kalkulationswerte und Rabattstaffel der Projektsimulation.
 *
 * Die Klasse liefert ausschließlich Kostensätze und den Rabattsatz.
 * Die eigentlichen Angebotsbeträge berechnet später der Angebotsrechner.
 */
public final class Kalkulationsregeln {
    private final BigDecimal dozentenTagessatz = new BigDecimal("650.00");
    private final BigDecimal raumTagessatz = new BigDecimal("250.00");
    private final BigDecimal unterlagenProTeilnehmer = new BigDecimal("25.00");
    private final BigDecimal verwaltungspauschale = new BigDecimal("150.00");

    /** Die vorgegebenen Regeln benötigen keine externen Parameter. */
    public Kalkulationsregeln() {
    }

    public BigDecimal getDozentenTagessatz() {
        return dozentenTagessatz;
    }

    public BigDecimal getRaumTagessatz() {
        return raumTagessatz;
    }

    public BigDecimal getUnterlagenProTeilnehmer() {
        return unterlagenProTeilnehmer;
    }

    public BigDecimal getVerwaltungspauschale() {
        return verwaltungspauschale;
    }

    /**
     * Liefert den Rabatt als Dezimalwert: 0.00, 0.05 oder 0.10.
     * Zum Beispiel steht 0.05 für 5 Prozent.
     *
     * @throws IllegalArgumentException wenn die Teilnehmerzahl nicht zwischen 1 und 15 liegt
     */
    public BigDecimal ermittleRabattsatz(int teilnehmerzahl) {
        if (teilnehmerzahl < 1 || teilnehmerzahl > 15) {
            throw new IllegalArgumentException(
                    "Die Teilnehmerzahl muss zwischen 1 und 15 liegen.");
        }
        if (teilnehmerzahl <= 5) {
            return new BigDecimal("0.00");
        }
        if (teilnehmerzahl <= 10) {
            return new BigDecimal("0.05");
        }
        return new BigDecimal("0.10");
    }
}
