package angebotskalkulation;

import java.math.BigDecimal;

/** Einzelprüfungen für die optionale Erweiterung ohne externe Testbibliothek. */
public final class AngebotsvergleichTest {
    private static int pruefungen;
    private static final Angebotsrechner RECHNER =
            new Angebotsrechner(new Kalkulationsregeln());

    private static Angebot angebot(Schulung schulung, int teilnehmer) {
        return RECHNER.berechne(new Anfrage(schulung, teilnehmer));
    }

    private static void istGleich(String erwartet, BigDecimal betrag, String beschreibung) {
        pruefungen++;
        if (new BigDecimal(erwartet).compareTo(betrag) != 0) {
            throw new AssertionError(beschreibung + ": erwartet " + erwartet
                    + ", tatsächlich " + betrag);
        }
    }

    private static void istDasselbe(Object erwartet, Object tatsaechlich, String beschreibung) {
        pruefungen++;
        if (erwartet != tatsaechlich) {
            throw new AssertionError(beschreibung);
        }
    }

    private static void erwartetException(Class<? extends RuntimeException> typ,
            Runnable aktion, String beschreibung) {
        pruefungen++;
        try {
            aktion.run();
        } catch (RuntimeException ausnahme) {
            if (typ.isInstance(ausnahme)) {
                return;
            }
            throw new AssertionError(beschreibung + ": falsche Exception " + ausnahme);
        }
        throw new AssertionError(beschreibung + ": keine Exception");
    }

    public static void main(String[] args) {
        Angebot java8 = angebot(Schulung.JAVA_GRUNDLAGEN, 8);
        Angebot java12 = angebot(Schulung.JAVA_GRUNDLAGEN, 12);
        Angebotsvergleich vergleich = new Angebotsvergleich(java8, java12);

        istGleich("8882.50", java8.getAngebotspreis(), "A: 8 TN Gesamtpreis");
        istGleich("1110.31", java8.getPreisProTeilnehmer(), "A: 8 TN Einzelpreis");
        istGleich("8505.00", java12.getAngebotspreis(), "B: 12 TN Gesamtpreis");
        istGleich("708.75", java12.getPreisProTeilnehmer(), "B: 12 TN Einzelpreis");
        istGleich("-377.50", vergleich.getGesamtpreisdifferenz(), "B - A Gesamtpreis");
        istGleich("-401.56", vergleich.getTeilnehmerpreisdifferenz(), "B - A Einzelpreis");
        istDasselbe(java8, vergleich.getUrspruenglichesAngebot(), "Ausgangsobjekt beibehalten");
        istDasselbe(java12, vergleich.getAlternativesAngebot(), "Alternatives Objekt beibehalten");

        Angebotsvergleich umgekehrt = new Angebotsvergleich(java12, java8);
        istGleich("377.50", umgekehrt.getGesamtpreisdifferenz(), "Umgekehrte Gesamtdifferenz");
        istGleich("401.56", umgekehrt.getTeilnehmerpreisdifferenz(), "Umgekehrte Einzeldifferenz");

        Angebot java12Nochmals = angebot(Schulung.JAVA_GRUNDLAGEN, 12);
        Angebotsvergleich identisch = new Angebotsvergleich(java12, java12Nochmals);
        istGleich("0.00", identisch.getGesamtpreisdifferenz(), "Gleiche Teilnehmerzahl Gesamtpreis");
        istGleich("0.00", identisch.getTeilnehmerpreisdifferenz(), "Gleiche Teilnehmerzahl Einzelpreis");

        Angebotsvergleich grenze = new Angebotsvergleich(
                angebot(Schulung.SQL_GRUNDLAGEN, 10),
                angebot(Schulung.SQL_GRUNDLAGEN, 11));
        istGleich("-447.50", grenze.getGesamtpreisdifferenz(), "Rabattgrenze 10/11 Gesamtpreis");
        istGleich("-121.86", grenze.getTeilnehmerpreisdifferenz(), "Rabattgrenze 10/11 Einzelpreis");

        Angebotsvergleich kurzerKurs = new Angebotsvergleich(
                angebot(Schulung.EXCEL_AUFBAU, 1),
                angebot(Schulung.EXCEL_AUFBAU, 15));
        istGleich("-152.50", kurzerKurs.getGesamtpreisdifferenz(), "Excel 1/15 Gesamtpreis");
        istGleich("-4373.50", kurzerKurs.getTeilnehmerpreisdifferenz(), "Excel 1/15 Einzelpreis");

        erwartetException(NullPointerException.class,
                () -> new Angebotsvergleich(null, java12), "Ausgangsangebot null");
        erwartetException(NullPointerException.class,
                () -> new Angebotsvergleich(java8, null), "Alternatives Angebot null");
        erwartetException(IllegalArgumentException.class,
                () -> new Angebotsvergleich(java8, angebot(Schulung.SQL_GRUNDLAGEN, 12)),
                "Verschiedene Schulungen ausgeschlossen");
        erwartetException(IllegalArgumentException.class,
                () -> new Angebotsvergleich(angebot(Schulung.EXCEL_AUFBAU, 1), java8),
                "Verschiedene Schulungsdauern ausgeschlossen");

        System.out.println("Alle " + pruefungen + " Angebotsvergleich-Tests bestanden.");
    }
}
