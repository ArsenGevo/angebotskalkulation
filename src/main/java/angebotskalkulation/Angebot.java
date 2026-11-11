package angebotskalkulation;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Unveränderliches Ergebnis einer Angebotskalkulation.
 *
 * Alle Beträge werden vom Angebotsrechner ermittelt und hier lediglich
 * gespeichert. Die Klasse führt bewusst keine eigenen Berechnungen durch.
 * Die rechnerische Konsistenz der Werte verantwortet der Angebotsrechner.
 */
public final class Angebot {
    private final Anfrage anfrage;
    private final BigDecimal dozentenkosten;
    private final BigDecimal raumkosten;
    private final BigDecimal unterlagenkosten;
    private final BigDecimal verwaltungspauschale;
    private final BigDecimal zwischensumme;
    private final BigDecimal rabattsatz;
    private final BigDecimal rabattbetrag;
    private final BigDecimal angebotspreis;
    private final BigDecimal preisProTeilnehmer;

    /**
     * Übernimmt eine Anfrage und neun bereits berechnete Werte.
     * Nullwerte sind nicht erlaubt. Die Berechnung selbst findet hier nicht statt.
     */
    public Angebot(
            Anfrage anfrage,
            BigDecimal dozentenkosten,
            BigDecimal raumkosten,
            BigDecimal unterlagenkosten,
            BigDecimal verwaltungspauschale,
            BigDecimal zwischensumme,
            BigDecimal rabattsatz,
            BigDecimal rabattbetrag,
            BigDecimal angebotspreis,
            BigDecimal preisProTeilnehmer) {
        this.anfrage = Objects.requireNonNull(anfrage, "Die Anfrage darf nicht null sein.");
        this.dozentenkosten = Objects.requireNonNull(dozentenkosten, "Dozentenkosten dürfen nicht null sein.");
        this.raumkosten = Objects.requireNonNull(raumkosten, "Raumkosten dürfen nicht null sein.");
        this.unterlagenkosten = Objects.requireNonNull(unterlagenkosten, "Unterlagenkosten dürfen nicht null sein.");
        this.verwaltungspauschale = Objects.requireNonNull(verwaltungspauschale, "Verwaltungspauschale darf nicht null sein.");
        this.zwischensumme = Objects.requireNonNull(zwischensumme, "Zwischensumme darf nicht null sein.");
        this.rabattsatz = Objects.requireNonNull(rabattsatz, "Rabattsatz darf nicht null sein.");
        this.rabattbetrag = Objects.requireNonNull(rabattbetrag, "Rabattbetrag darf nicht null sein.");
        this.angebotspreis = Objects.requireNonNull(angebotspreis, "Angebotspreis darf nicht null sein.");
        this.preisProTeilnehmer = Objects.requireNonNull(preisProTeilnehmer, "Preis pro Teilnehmer darf nicht null sein.");
    }

    public Anfrage getAnfrage() {
        return anfrage;
    }

    public BigDecimal getDozentenkosten() {
        return dozentenkosten;
    }

    public BigDecimal getRaumkosten() {
        return raumkosten;
    }

    public BigDecimal getUnterlagenkosten() {
        return unterlagenkosten;
    }

    public BigDecimal getVerwaltungspauschale() {
        return verwaltungspauschale;
    }

    public BigDecimal getZwischensumme() {
        return zwischensumme;
    }

    public BigDecimal getRabattsatz() {
        return rabattsatz;
    }

    public BigDecimal getRabattbetrag() {
        return rabattbetrag;
    }

    public BigDecimal getAngebotspreis() {
        return angebotspreis;
    }

    public BigDecimal getPreisProTeilnehmer() {
        return preisProTeilnehmer;
    }
}
