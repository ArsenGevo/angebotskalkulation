package angebotskalkulation;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Vergleicht zwei fertig berechnete Angebote derselben Schulung.
 * Die Differenz ist stets: alternatives Angebot minus ursprüngliches Angebot.
 * Die Kalkulation wird hier bewusst nicht wiederholt.
 */
public final class Angebotsvergleich {
    private final Angebot urspruenglichesAngebot;
    private final Angebot alternativesAngebot;

    public Angebotsvergleich(Angebot urspruenglichesAngebot, Angebot alternativesAngebot) {
        this.urspruenglichesAngebot = Objects.requireNonNull(
                urspruenglichesAngebot, "Das ursprüngliche Angebot darf nicht null sein.");
        this.alternativesAngebot = Objects.requireNonNull(
                alternativesAngebot, "Das alternative Angebot darf nicht null sein.");

        if (urspruenglichesAngebot.getAnfrage().getSchulung()
                != alternativesAngebot.getAnfrage().getSchulung()) {
            throw new IllegalArgumentException(
                    "Es können nur Angebote derselben Schulung verglichen werden.");
        }
    }

    public BigDecimal getGesamtpreisdifferenz() {
        return alternativesAngebot.getAngebotspreis()
                .subtract(urspruenglichesAngebot.getAngebotspreis());
    }

    public BigDecimal getTeilnehmerpreisdifferenz() {
        return alternativesAngebot.getPreisProTeilnehmer()
                .subtract(urspruenglichesAngebot.getPreisProTeilnehmer());
    }

    public Angebot getUrspruenglichesAngebot() {
        return urspruenglichesAngebot;
    }

    public Angebot getAlternativesAngebot() {
        return alternativesAngebot;
    }
}
