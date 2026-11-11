package angebotskalkulation;

import java.util.Objects;

/**
 * Eine fachlich gültige Kundenanfrage für eine Schulung.
 * Die Teilnehmerzahl muss zwischen 1 und 15 liegen.
 */
public final class Anfrage {
    private final Schulung schulung;
    private final int teilnehmerzahl;

    public Anfrage(Schulung schulung, int teilnehmerzahl) {
        this.schulung = Objects.requireNonNull(
                schulung, "Die Schulung darf nicht null sein.");
        if (teilnehmerzahl < 1 || teilnehmerzahl > 15) {
            throw new IllegalArgumentException(
                    "Die Teilnehmerzahl muss zwischen 1 und 15 liegen.");
        }
        this.teilnehmerzahl = teilnehmerzahl;
    }

    public Schulung getSchulung() {
        return schulung;
    }

    public int getTeilnehmerzahl() {
        return teilnehmerzahl;
    }
}
