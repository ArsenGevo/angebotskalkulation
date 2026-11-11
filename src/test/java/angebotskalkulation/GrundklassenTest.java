package angebotskalkulation;

/**
 * Unabhängige Basistests ohne zusätzliche Testbibliothek.
 * Später können diese Tests durch JUnit ergänzt werden.
 */
public final class GrundklassenTest {
    private static int ausgefuehrteTests;

    private static void pruefen(boolean bedingung, String beschreibung) {
        ausgefuehrteTests++;
        if (!bedingung) {
            throw new AssertionError("Fehlgeschlagen: " + beschreibung);
        }
    }

    private static void erwartetException(Class<? extends Throwable> typ,
                                           Runnable aktion, String beschreibung) {
        ausgefuehrteTests++;
        try {
            aktion.run();
        } catch (Throwable fehler) {
            if (typ.isInstance(fehler)) {
                return;
            }
            throw new AssertionError(
                    "Falscher Fehlertyp bei " + beschreibung, fehler);
        }
        throw new AssertionError("Keine Exception bei " + beschreibung);
    }

    public static void main(String[] args) {
        pruefen(Schulung.values().length == 5, "fünf Schulungen");
        pruefen(Schulung.EXCEL_AUFBAU.getDauerTage() == 5, "Excel: 5 Tage");
        pruefen(Schulung.SQL_GRUNDLAGEN.getDauerTage() == 10, "SQL: 10 Tage");
        pruefen(Schulung.JAVA_GRUNDLAGEN.getDauerTage() == 10, "Java: 10 Tage");
        pruefen(Schulung.NETZWERKTECHNIK.getDauerTage() == 10, "Netzwerk: 10 Tage");
        pruefen(Schulung.WINDOWS_SERVER_GRUNDLAGEN.getDauerTage() == 10,
                "Windows Server: 10 Tage");

        Anfrage anfrage = new Anfrage(Schulung.JAVA_GRUNDLAGEN, 12);
        pruefen(anfrage.getSchulung() == Schulung.JAVA_GRUNDLAGEN,
                "Schulungsreferenz");
        pruefen(anfrage.getTeilnehmerzahl() == 12, "Teilnehmerzahl");
        pruefen(new Anfrage(Schulung.EXCEL_AUFBAU, 1).getTeilnehmerzahl() == 1,
                "untere Grenze 1");
        pruefen(new Anfrage(Schulung.SQL_GRUNDLAGEN, 15).getTeilnehmerzahl() == 15,
                "obere Grenze 15");

        erwartetException(IllegalArgumentException.class,
                () -> new Anfrage(Schulung.JAVA_GRUNDLAGEN, 0), "0 Teilnehmer");
        erwartetException(IllegalArgumentException.class,
                () -> new Anfrage(Schulung.JAVA_GRUNDLAGEN, -1), "-1 Teilnehmer");
        erwartetException(IllegalArgumentException.class,
                () -> new Anfrage(Schulung.JAVA_GRUNDLAGEN, 16), "16 Teilnehmer");
        erwartetException(NullPointerException.class,
                () -> new Anfrage(null, 5), "keine Schulung");

        System.out.println("Alle " + ausgefuehrteTests + " Grundklassentests bestanden.");
    }
}
