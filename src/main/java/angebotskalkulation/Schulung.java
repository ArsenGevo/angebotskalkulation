package angebotskalkulation;

/**
 * Die fünf vorgegebenen Firmenschulungen und ihre Dauer.
 * Die Schulungsdaten sind unveränderlich.
 */
public enum Schulung {
    EXCEL_AUFBAU("Excel Aufbau", 5),
    SQL_GRUNDLAGEN("SQL Grundlagen", 10),
    JAVA_GRUNDLAGEN("Java Grundlagen", 10),
    NETZWERKTECHNIK("Netzwerktechnik", 10),
    WINDOWS_SERVER_GRUNDLAGEN("Windows Server Grundlagen", 10);

    private final String bezeichnung;
    private final int dauerTage;

    Schulung(String bezeichnung, int dauerTage) {
        this.bezeichnung = bezeichnung;
        this.dauerTage = dauerTage;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public int getDauerTage() {
        return dauerTage;
    }
}
