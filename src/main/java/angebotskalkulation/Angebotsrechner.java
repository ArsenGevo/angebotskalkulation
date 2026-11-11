package angebotskalkulation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Berechnet die Kosten einer Firmenschulung auf Basis einer gültigen Anfrage.
 *
 * Fachliche Kalkulation und Konsoleninteraktion bleiben getrennt:
 * Der Rechner erzeugt ein unveränderliches Angebot und übernimmt keine Ausgabe.
 */
public final class Angebotsrechner {
    private static final int GELD_NACHKOMMASTELLEN = 2;
    private static final RoundingMode RUNDUNG = RoundingMode.HALF_UP;

    private final Kalkulationsregeln regeln;

    public Angebotsrechner(Kalkulationsregeln regeln) {
        this.regeln = Objects.requireNonNull(
                regeln, "Die Kalkulationsregeln dürfen nicht null sein.");
    }

    /**
     * Berechnet die neun Ergebniswerte und erstellt daraus ein Angebot.
     *
     * Der Rabatt wird auf die gesamte Zwischensumme angewendet. Zuerst wird
     * der Rabattbetrag auf Cent gerundet, danach vom Zwischenbetrag abgezogen.
     * Der Preis pro Teilnehmer wird erst bei der abschließenden Division gerundet.
     *
     * @param anfrage eine zuvor validierte Kundenanfrage
     * @return ein vollständiges, unveränderliches Berechnungsergebnis
     * @throws NullPointerException wenn die Anfrage null ist
     */
    public Angebot berechne(Anfrage anfrage) {
        Objects.requireNonNull(anfrage, "Die Anfrage darf nicht null sein.");

        int tage = anfrage.getSchulung().getDauerTage();
        int teilnehmer = anfrage.getTeilnehmerzahl();

        BigDecimal dozentenkosten = regeln.getDozentenTagessatz()
                .multiply(BigDecimal.valueOf(tage));
        BigDecimal raumkosten = regeln.getRaumTagessatz()
                .multiply(BigDecimal.valueOf(tage));
        BigDecimal unterlagenkosten = regeln.getUnterlagenProTeilnehmer()
                .multiply(BigDecimal.valueOf(teilnehmer));
        BigDecimal verwaltungspauschale = regeln.getVerwaltungspauschale();

        BigDecimal zwischensumme = dozentenkosten
                .add(raumkosten)
                .add(unterlagenkosten)
                .add(verwaltungspauschale);

        BigDecimal rabattsatz = regeln.ermittleRabattsatz(teilnehmer);
        BigDecimal rabattbetrag = zwischensumme.multiply(rabattsatz)
                .setScale(GELD_NACHKOMMASTELLEN, RUNDUNG);
        BigDecimal angebotspreis = zwischensumme.subtract(rabattbetrag)
                .setScale(GELD_NACHKOMMASTELLEN, RUNDUNG);
        BigDecimal preisProTeilnehmer = angebotspreis.divide(
                BigDecimal.valueOf(teilnehmer), GELD_NACHKOMMASTELLEN, RUNDUNG);

        return new Angebot(
                anfrage,
                dozentenkosten,
                raumkosten,
                unterlagenkosten,
                verwaltungspauschale,
                zwischensumme,
                rabattsatz,
                rabattbetrag,
                angebotspreis,
                preisProTeilnehmer);
    }
}
