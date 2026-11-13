package angebotskalkulation;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/** Einstiegspunkt der Anwendung; enthält keine Geschäfts- oder Konsolenlogik. */
public final class Main {
    private Main() {
        // Kein Instanzobjekt erforderlich.
        // throw new UnsupportedOperationException("Entry point class cannot be instantiated");
    }

    public static void main(String[] args) {
        Kalkulationsregeln regeln = new Kalkulationsregeln();
        Angebotsrechner rechner = new Angebotsrechner(regeln);
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            new KonsolenUI(scanner, rechner).starten();
        }
    }
}
