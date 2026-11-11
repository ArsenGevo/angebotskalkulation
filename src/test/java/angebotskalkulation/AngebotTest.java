package angebotskalkulation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;

/** Tests des Ergebnisobjekts ohne externe Testbibliothek. */
public final class AngebotTest {
    private static int ausgefuehrteTests;

    private static void pruefen(boolean bedingung, String beschreibung) {
        ausgefuehrteTests++;
        if (!bedingung) {
            throw new AssertionError("Fehlgeschlagen: " + beschreibung);
        }
    }

    private static void pruefeBetrag(String erwartet, BigDecimal tatsaechlich, String beschreibung) {
        pruefen(new BigDecimal(erwartet).compareTo(tatsaechlich) == 0, beschreibung);
    }

    private static void erwartetNullPointer(Runnable aktion, String beschreibung) {
        ausgefuehrteTests++;
        try {
            aktion.run();
        } catch (NullPointerException erwartet) {
            return;
        }
        throw new AssertionError("Fehlende Nullprüfung: " + beschreibung);
    }

    public static void main(String[] args) {
        Anfrage anfrage = new Anfrage(Schulung.JAVA_GRUNDLAGEN, 12);
        BigDecimal[] werte = {
                new BigDecimal("6500.00"), new BigDecimal("2500.00"),
                new BigDecimal("300.00"), new BigDecimal("150.00"),
                new BigDecimal("9450.00"), new BigDecimal("0.10"),
                new BigDecimal("945.00"), new BigDecimal("8505.00"),
                new BigDecimal("708.75")
        };
        Angebot angebot = erstellen(anfrage, werte);
        pruefen(angebot.getAnfrage() == anfrage, "Anfragereferenz");
        pruefeBetrag("6500.00", angebot.getDozentenkosten(), "Dozentenkosten");
        pruefeBetrag("2500.00", angebot.getRaumkosten(), "Raumkosten");
        pruefeBetrag("300.00", angebot.getUnterlagenkosten(), "Unterlagenkosten");
        pruefeBetrag("150.00", angebot.getVerwaltungspauschale(), "Verwaltungspauschale");
        pruefeBetrag("9450.00", angebot.getZwischensumme(), "Zwischensumme");
        pruefeBetrag("0.10", angebot.getRabattsatz(), "Rabattsatz");
        pruefeBetrag("945.00", angebot.getRabattbetrag(), "Rabattbetrag");
        pruefeBetrag("8505.00", angebot.getAngebotspreis(), "Angebotspreis");
        pruefeBetrag("708.75", angebot.getPreisProTeilnehmer(), "Preis pro Teilnehmer");

        // Der Konstruktor weist die Anfrage und jeden der neun Werte einzeln zurück, wenn er null ist.
        erwartetNullPointer(() -> erstellen(null, werte), "Anfrage");
        for (int i = 0; i < werte.length; i++) {
            final int position = i;
            BigDecimal[] mitNull = werte.clone();
            mitNull[position] = null;
            erwartetNullPointer(() -> erstellen(anfrage, mitNull), "Wert an Position " + position);
        }

        pruefen(Modifier.isFinal(Angebot.class.getModifiers()), "final class");
        Field[] felder = Angebot.class.getDeclaredFields();
        pruefen(felder.length == 10, "exakt zehn Felder");
        for (Field feld : felder) {
            pruefen(Modifier.isPrivate(feld.getModifiers()) && Modifier.isFinal(feld.getModifiers()),
                    "private final: " + feld.getName());
        }

        System.out.println("Alle " + ausgefuehrteTests + " Angebot-Tests bestanden.");
    }

    private static Angebot erstellen(Anfrage anfrage, BigDecimal[] w) {
        return new Angebot(anfrage, w[0], w[1], w[2], w[3], w[4], w[5], w[6], w[7], w[8]);
    }
}
